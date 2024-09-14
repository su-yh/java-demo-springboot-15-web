package com.eb.business.service;

import com.eb.business.dto.vip.req.VipInfoQueryReqDto;
import com.eb.business.dto.vip.req.VipInfoUpdateReqDto;
import com.eb.business.dto.vip.rsp.VipInfoRspDto;
import com.eb.constant.ErrorCodeConstants;
import com.eb.constant.enums.FinancialCategoryEnums;
import com.eb.constant.enums.OnlineStateEnums;
import com.eb.constant.enums.OrderEnums;
import com.eb.constant.enums.TransferStatusEnums;
import com.eb.event.MqMessageEvent;
import com.eb.mp.mybatis.LambdaQueryWrapperX;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.SchedulingTransferRecordEntity;
import com.eb.mp.mysql.entity.business.VipBankAccountEntity;
import com.eb.mp.mysql.entity.business.VipInfoEntity;
import com.eb.mp.mysql.entity.business.VipTppEntity;
import com.eb.mp.mysql.mapper.business.VipInfoMapper;
import com.eb.mq.product.dto.MqMessageProductDto;
import com.eb.mq.product.dto.business.VipInfoSvipAppOnlineMqDto;
import com.eb.mq.product.dto.enums.MqMessageEventCategoryEnums;
import com.eb.mvc.authentication.LoginUser;
import com.eb.mvc.exception.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VipInfoService {
    private final ApplicationContext applicationContext;

    private final VipInfoMapper baseMapper;
    private final SchedulingTransferRecordService schedulingTransferRecordService;
    private final VipTppService vipTppService;
    private final VipBankAccountService vipBankAccountService;

    public VipInfoEntity queryEntityByCode(String code) {
        return baseMapper.queryEntityByCode(code);
    }

    public VipInfoEntity queryEntityByTgUsername(@Nullable String tgUsername) {
        if (tgUsername == null) {
            return null;
        }

        return baseMapper.queryEntityByTgUsername(tgUsername);
    }

    public String queryAgentPhoneByCode(@Nullable String code) {
        if (null == code) {
            return null;
        }

        return baseMapper.queryAgentPhoneByCode(code);
    }

    public PageResult<VipInfoEntity> pageQueryByConditional(
            @NonNull PageParam pageParam, @NonNull VipInfoQueryReqDto reqDto) {
        return baseMapper.pageQueryByConditional(pageParam, reqDto);
    }

    public List<VipInfoEntity> queryByConditional(@NonNull VipInfoQueryReqDto reqDto) {
        return baseMapper.queryByConditional(reqDto);
    }

    public List<VipInfoEntity> pageByConditional(@NonNull VipInfoQueryReqDto reqDto) {
        return baseMapper.queryByConditional(reqDto);
    }

    // 根据tg username去更新表里面的chatid
    public void updateTgUsernameById(@NonNull Long chatID, @Nullable String tgUsername) {
        LambdaQueryWrapperX<VipInfoEntity> queryWrapperX = baseMapper.build();
        queryWrapperX.eq(VipInfoEntity::getTgUsername, tgUsername);

        // 创建要更新的实体
        VipInfoEntity entity = new VipInfoEntity();
        entity.setTgChatId(chatID);

        // 执行更新操作
        baseMapper.update(entity, queryWrapperX);
    }

    public List<String> codeList() {
        List<VipInfoEntity> entities = baseMapper.selectList();
        if (entities == null) {
            return null;
        }

        List<String> list = new ArrayList<>();
        for (VipInfoEntity entity : entities) {
            list.add(entity.getCode());
        }
        return list;
    }

    @Transactional
    public PageResult<VipInfoRspDto> infoListPage(
            @NonNull PageParam pageParam, @NonNull VipInfoQueryReqDto reqDto) {
        if (reqDto.getOrderTransferRate() != null
                || reqDto.getOrderTransferSeconds() != null) {
            // 基于内存排序分页
            return doInfoListPageByMember(pageParam, reqDto);
        }

        return doInfoListPageByDb(pageParam, reqDto);
    }

    // 基于内存排序
    @Transactional
    public PageResult<VipInfoRspDto> doInfoListPageByMember(
            @NonNull PageParam pageParam, @NonNull VipInfoQueryReqDto reqDto) {
        // 内存中分页
        List<VipInfoEntity> vipInfoEntities = queryByConditional(reqDto);
        List<VipInfoRspDto> vipInfoRspDtoList = null;
        long total = 0;
        if (vipInfoEntities != null) {
            List<VipInfoRspDto> dtoList = new ArrayList<>();
            for (VipInfoEntity entity : vipInfoEntities) {
                VipInfoRspDto rspDto = new VipInfoRspDto();
                mapEntityToDto(entity, rspDto);

                // 补充发货率和平均发货时间
                populateVipInfoProperties(entity.getCode(), rspDto);

                // 补充tpp 和银行卡号
                populateFinancialAccount(rspDto, entity.getCode());

                dtoList.add(rspDto);
            }

            total = dtoList.size();

            // 基于内存排序
            vipInfoRspSort(dtoList, reqDto);

            // 基于内存分页
            vipInfoRspDtoList = PageParam.doPageList(pageParam, dtoList);
        }

        return new PageResult<>(vipInfoRspDtoList, total);
    }

    // 仅处理无法基于数据库排序的排序分页字段
    private static void vipInfoRspSort(List<VipInfoRspDto> dtoList, VipInfoQueryReqDto reqDto) {
        Comparator<VipInfoRspDto> comparator = null;

        OrderEnums orderTransferRate = reqDto.getOrderTransferRate();
        if (orderTransferRate != null) {
            comparator = (left, right) -> {
                BigDecimal leftValue = left.getTransferRate();
                BigDecimal rightValue = right.getTransferRate();
                if (leftValue == null) {
                    leftValue = BigDecimal.ZERO;
                }
                if (rightValue == null) {
                    rightValue = BigDecimal.ZERO;
                }

                int direction = orderTransferRate.equals(OrderEnums.ascend) ? 1 : -1;
                return direction * leftValue.compareTo(rightValue);
            };
        }

        OrderEnums orderTransferSeconds = reqDto.getOrderTransferSeconds();
        if (orderTransferSeconds != null) {
            comparator = (left, right) -> {
                Integer leftValue = left.getTransferSeconds();
                Integer rightValue = right.getTransferSeconds();
                if (leftValue == null) {
                    leftValue = 0;
                }
                if (rightValue == null) {
                    rightValue = 0;
                }

                int direction = orderTransferSeconds.equals(OrderEnums.ascend) ? 1 : -1;
                return direction * leftValue.compareTo(rightValue);
            };
        }

        dtoList.sort(comparator);
    }

    @Transactional
    public PageResult<VipInfoRspDto> doInfoListPageByDb(
            @NonNull PageParam pageParam, @NonNull VipInfoQueryReqDto reqDto) {
        PageResult<VipInfoEntity> vipInfoPageResult = pageQueryByConditional(pageParam, reqDto);

        List<VipInfoEntity> entityList = vipInfoPageResult.getList();
        List<VipInfoRspDto> vipInfoRspDtoList = vipInfoListConvertEntityToDto(entityList);

        return new PageResult<>(vipInfoRspDtoList, vipInfoPageResult.getTotal());
    }

    @Transactional
    public List<VipInfoRspDto> infoList(@NonNull VipInfoQueryReqDto reqDto) {
        return doInfoListByDb(reqDto);
    }

    @Transactional
    public List<VipInfoRspDto> doInfoListByDb(@NonNull VipInfoQueryReqDto reqDto) {
        List<VipInfoEntity> entityList = pageByConditional(reqDto);
        return vipInfoListConvertEntityToDto(entityList);
    }


    @Nullable
    private List<VipInfoRspDto> vipInfoListConvertEntityToDto(@Nullable List<VipInfoEntity> entityList) {
        if (entityList == null) {
            return null;
        }
        List<VipInfoRspDto> vipInfoRspDtoList = new ArrayList<>();
        for (VipInfoEntity entity : entityList) {
            VipInfoRspDto rspDto = new VipInfoRspDto();
            mapEntityToDto(entity, rspDto);

            // 补充发货率和平均发货时间
            populateVipInfoProperties(entity.getCode(), rspDto);

            // 补充tpp 和银行卡号
            populateFinancialAccount(rspDto, entity.getCode());

            vipInfoRspDtoList.add(rspDto);
        }

        return vipInfoRspDtoList;
    }

    private void populateFinancialAccount(VipInfoRspDto rspDto, String code) {
        VipTppEntity tppEntity = vipTppService.queryEntityByCode(code);
        VipBankAccountEntity bankEntity = vipBankAccountService.queryEntityByCode(code);
        if (tppEntity == null && bankEntity == null) {
            return;
        }

        FinancialCategoryEnums financialCategory = null;
        String financialAccount = null;

        if (tppEntity != null && bankEntity != null) {
            Date tppUpdated = tppEntity.getUpdated();
            Date bankUpdated = bankEntity.getUpdated();
            if (tppUpdated == null) {
                tppUpdated = new Date(0L);
            }
            if (bankUpdated == null) {
                bankUpdated = new Date(0L);
            }

            if (tppUpdated.after(bankUpdated)) {
                financialCategory = FinancialCategoryEnums.TPP;
                financialAccount = tppEntity.getTpp();
            } else {
                financialCategory = FinancialCategoryEnums.BANK;
                financialAccount = bankEntity.getCardNo();
            }
        } else {
            if (tppEntity != null) {
                financialCategory = FinancialCategoryEnums.TPP;
                financialAccount = tppEntity.getTpp();
            } else {
                financialCategory = FinancialCategoryEnums.BANK;
                financialAccount = bankEntity.getCardNo();
            }
        }

        rspDto.setFinancialCategory(financialCategory).setFinancialAccount(financialAccount);
    }

    /**
     * 补充发货率和平均发货时间
     */
    private void populateVipInfoProperties(String code, VipInfoRspDto rspDto) {
        if (code == null) {
            log.warn("vip info populate properties failed, vip code is null.");
            return;
        }

        List<SchedulingTransferRecordEntity> recordEntities
                = schedulingTransferRecordService.queryListByCode(code);
        if (recordEntities == null || recordEntities.isEmpty()) {
            log.warn("{} list is empty, by vip code: {}",
                    SchedulingTransferRecordEntity.class.getSimpleName(), code);
            return;
        }

        int normalTransferCount = 0;    // 正常发货数量
        int totalCount = recordEntities.size();
        int totalTransferSeconds = 0;   // 总发货时间，单位：秒
        for (SchedulingTransferRecordEntity recordEntity : recordEntities) {
            TransferStatusEnums status = recordEntity.getStatus();
            if (status != null && status.equals(TransferStatusEnums.SUCCESS)) {
                normalTransferCount++;
            }

            Date created = recordEntity.getCreated();
            Date mtime = recordEntity.getMtime();
            if (mtime != null && created != null) {
                long diffMs = mtime.getTime() - created.getTime();
                totalTransferSeconds += (int) (Math.abs(diffMs) / 1000);
            }
        }

        BigDecimal transferRate = BigDecimal.valueOf(normalTransferCount / totalCount);
        Integer transferSeconds = totalTransferSeconds / totalCount;
        rspDto.setTransferRate(transferRate).setTransferSeconds(transferSeconds);
    }

    public static void mapEntityToDto(
            @NonNull VipInfoEntity entity,
            @NonNull VipInfoRspDto rspDto) {
        rspDto.setId(entity.getId())
                .setPnum(entity.getPnum())
                .setSvipAppOnline(entity.getSvipAppOnline())
                .setSvipAuditStatus(entity.getSvipAuditStatus())
                .setSvipSystemOnline(entity.getSvipSystemOnline())
                .setName(entity.getName())
                .setBalance(entity.getBalance())
                .setTransferBalance(entity.getTransferBalance())
                .setTransferCount(entity.getTransferCount())
                .setContactDetails(entity.getContactDetails())
                .setTgUsername(entity.getTgUsername())
                .setEmail(entity.getEmail());
    }

    @Transactional
    public void infoUpdateById(LoginUser loginUser, @NonNull VipInfoUpdateReqDto updateReqDto) {
        Long id = updateReqDto.getId();
        OnlineStateEnums svipAppOnline = updateReqDto.getSvipAppOnline();
        String email = updateReqDto.getEmail();
        VipInfoEntity historyEntity = baseMapper.selectById(id);
        if (historyEntity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.RECORD_NOT_EXISTS, id);
        }

        String tgUsername = updateReqDto.getTgUsername();
        if (StringUtils.hasText(tgUsername)) {
            VipInfoEntity tmpHistoryEntity = baseMapper.queryEntityByTgUsername(tgUsername);
            if (tmpHistoryEntity != null && !tmpHistoryEntity.getId().equals(id)) {
                throw ExceptionUtil.business(ErrorCodeConstants.VIP_INFO_UPDATE_FAILED_TG_USERNAME_EXISTS, tgUsername);
            }
        }

        VipInfoEntity updateEntity = new VipInfoEntity();
        updateEntity.setId(id).setSvipAppOnline(svipAppOnline).setTgUsername(tgUsername).setEmail(email);

        baseMapper.updateById(updateEntity);

        if (svipAppOnline == null) {
            return;
        }

        OnlineStateEnums historySvipAppOnline = historyEntity.getSvipAppOnline();
        if (svipAppOnline.equals(historySvipAppOnline)) {
            return;
        }

        VipInfoSvipAppOnlineMqDto mqDto = new VipInfoSvipAppOnlineMqDto();
        mqDto.setLoginUserId(loginUser.getId()).setLoginNickName(loginUser.getNickname()).setPnum(updateEntity.getPnum());
        mqDto.setVipInfoId(id).setSvipAppOnline(svipAppOnline.getCode());

        MqMessageProductDto mqMessage = new MqMessageProductDto();
        mqMessage.setMessageCategoryCode(MqMessageEventCategoryEnums.VIP_INFO_SVIP_APP_ONLINE.getCode());
        mqMessage.setMessageVipInfoSvipAppOnline(mqDto);
        applicationContext.publishEvent(new MqMessageEvent(mqMessage));
    }
}
