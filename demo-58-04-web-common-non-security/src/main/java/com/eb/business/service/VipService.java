package com.eb.business.service;

import com.eb.business.dto.vip.req.VipAccountQueryReqDto;
import com.eb.business.dto.vip.req.VipAccountReqDto;
import com.eb.business.dto.vip.rsp.VipAccountRspDto;
import com.eb.constant.ErrorCodeConstants;
import com.eb.constant.enums.EnableStatusEnums;
import com.eb.constant.enums.FinancialCategoryEnums;
import com.eb.event.MqMessageEvent;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.VipBankAccountEntity;
import com.eb.mp.mysql.entity.business.VipInfoEntity;
import com.eb.mp.mysql.entity.business.VipTppEntity;
import com.eb.mp.mysql.mapper.custom.VipMapper;
import com.eb.mq.product.dto.MqMessageProductDto;
import com.eb.mq.product.dto.business.FinancialAccountModifyMqDto;
import com.eb.mq.product.dto.enums.MqMessageEventCategoryEnums;
import com.eb.mq.product.dto.enums.OptActionEnums;
import com.eb.mvc.authentication.LoginUser;
import com.eb.mvc.exception.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author suyh
 * @since 2024-09-03
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VipService {
    private final ApplicationContext applicationContext;

    private final VipMapper baseMapper;
    private final VipInfoService vipInfoService;
    private final VipTppService vipTppService;
    private final VipBankAccountService vipBankAccountService;

//    public static void mapEntityToDto(
//            @NonNull VipInfoEntity entity,
//            @NonNull VipAccountRspDto rspDto,
//            FinancialCategoryEnums financialCategory) {
//        rspDto.setId(entity.getId())
//                .setPnum(entity.getPnum())
//                .setCode(entity.getCode());
//
//        String financialAccount = null;
//        if (financialCategory != null) {
//            switch (financialCategory) {
//                case TPP:
//                    financialAccount = entity.getTpp();
//                    break;
//                case BANK:
//                    financialAccount = entity.getBankAccount();
//                    break;
//                default:
//                    log.error("UNKNOWN {}: {}", FinancialCategoryEnums.class.getSimpleName(), financialCategory);
//                    break;
//            }
//        }
//        rspDto.setFinancialCategory(financialCategory).setFinancialAccount(financialAccount);
//    }

    public List<VipAccountRspDto> accountList(@NonNull VipAccountQueryReqDto queryDto) {
        return baseMapper.listQueryByConditional(queryDto);
    }

//    @Nullable
//    private List<VipAccountRspDto> vipAccountListConvertEntityToDto(
//            @NonNull VipInfoQueryReqDto reqDto, List<VipInfoEntity> entityList) {
//        if (entityList == null) {
//            return null;
//        }
//
//        List<VipAccountRspDto> vipAccountRspDtoList = new ArrayList<>();
//        for (VipInfoEntity entity : entityList) {
//            VipAccountRspDto rspDto = new VipAccountRspDto();
//            VipService.mapEntityToDto(entity, rspDto, reqDto.getFinancialCategory());
//
//            // 补充银行卡状态属性
//            populateVipAccountProperties(reqDto.getFinancialCategory(), entity, rspDto);
//
//            vipAccountRspDtoList.add(rspDto);
//        }
//
//        return vipAccountRspDtoList;
//    }

    @Transactional
    public PageResult<VipAccountRspDto> accountListPage(
            PageParam pageParam, VipAccountQueryReqDto queryDto) {
        long total = baseMapper.pageQueryByConditionalCount(queryDto);
        List<VipAccountRspDto> listVipAccount = null;
        if (total > 0) {
            listVipAccount = baseMapper.pageQueryByConditional(
                    pageParam.getPageStart(), pageParam.getPageSize(), queryDto);
        }

        return new PageResult<>(listVipAccount, total);
    }

    public List<String> codeList() {
        return vipInfoService.codeList();
    }

    public void accountUpdateById(@NonNull LoginUser loginUser, @NonNull VipAccountReqDto updateDto) {
        FinancialCategoryEnums financialCategory = updateDto.getFinancialCategory();
        String code = updateDto.getCode();
        switch (financialCategory) {
            case TPP: {
                VipTppEntity updateEntity = new VipTppEntity();
                updateEntity.setId(updateDto.getId()).setTpp(updateDto.getFinancialAccount())
                        .setPnum(updateDto.getPnum())
                        .setReview(updateDto.getReview()).setEnable(EnableStatusEnums.DISABLE);
                updateEntity.setCode(updateDto.getCode());
                vipTppService.updateById(updateEntity);
                if (!StringUtils.hasText(code)) {
                    VipTppEntity entity = vipTppService.queryById(updateDto.getId());
                    if (entity != null) {
                        code = entity.getCode();
                    }
                }
            }

            break;
            case BANK: {
                VipBankAccountEntity updateEntity = new VipBankAccountEntity();
                updateEntity.setId(updateDto.getId()).setCardNo(updateDto.getFinancialAccount())
                        .setAccount(updateDto.getBankAccountName()).setSwift(updateDto.getSwiftCode())
                        .setPnum(updateDto.getPnum())
                        .setReview(updateDto.getReview()).setEnable(EnableStatusEnums.DISABLE);
                updateEntity.setCode(updateDto.getCode());
                vipBankAccountService.updateById(updateEntity);
                if (!StringUtils.hasText(code)) {
                    VipBankAccountEntity entity = vipBankAccountService.queryById(updateDto.getId());
                    if (entity != null) {
                        code = entity.getCode();
                    }
                }
            }
            break;
            default:
                log.error("UNKNOWN enum {}: {}", FinancialCategoryEnums.class.getSimpleName(), financialCategory);
                throw ExceptionUtil.business(ErrorCodeConstants.SERVICE_ERROR);
        }

        String pnum = null;
        VipInfoEntity vipInfoEntity = vipInfoService.queryEntityByCode(code);
        if (vipInfoEntity != null) {
            pnum = vipInfoEntity.getPnum();
        }

        FinancialAccountModifyMqDto mqDto = new FinancialAccountModifyMqDto(OptActionEnums.UPDATE.getCode());
        mqDto.setLoginUserId(loginUser.getId()).setLoginNickName(loginUser.getNickname()).setPnum(pnum);
        mqDto.setFinancialCategory(financialCategory.getCodeEnum()).setFinancialAccount(updateDto.getFinancialAccount())
                .setBankAccountName(updateDto.getBankAccountName()).setSwiftCode(updateDto.getSwiftCode());

        MqMessageProductDto mqMessage = new MqMessageProductDto();
        mqMessage.setMessageCategoryCode(MqMessageEventCategoryEnums.FINANCIAL_ACCOUNT_MODIFY.getCode());
        mqMessage.setMessageFinancialAccountModify(mqDto);
        applicationContext.publishEvent(new MqMessageEvent(mqMessage));
    }

    public Long accountCreate(@NonNull LoginUser loginUser, VipAccountReqDto createDto) {
        Long id = null;
        FinancialCategoryEnums financialCategory = createDto.getFinancialCategory();
        String code = createDto.getCode();
        switch (financialCategory) {
            case TPP: {
                VipTppEntity createEntity = new VipTppEntity();
                createEntity.setTpp(createDto.getFinancialAccount())
                        .setPnum(createDto.getPnum())
                        .setReview(createDto.getReview()).setEnable(EnableStatusEnums.DISABLE);
                createEntity.setCode(createDto.getCode());
                id = vipTppService.create(createEntity);
                if (!StringUtils.hasText(code)) {
                    VipTppEntity entity = vipTppService.queryById(createDto.getId());
                    if (entity != null) {
                        code = entity.getCode();
                    }
                }
            }

            break;
            case BANK: {
                VipBankAccountEntity createEntity = new VipBankAccountEntity();
                createEntity.setCardNo(createDto.getFinancialAccount())
                        .setAccount(createDto.getBankAccountName()).setSwift(createDto.getSwiftCode())
                        .setPnum(createDto.getPnum())
                        .setReview(createDto.getReview()).setEnable(EnableStatusEnums.DISABLE);
                createEntity.setCode(createDto.getCode());
                id = vipBankAccountService.create(createEntity);
                if (!StringUtils.hasText(code)) {
                    VipBankAccountEntity entity = vipBankAccountService.queryById(createDto.getId());
                    if (entity != null) {
                        code = entity.getCode();
                    }
                }
            }
            break;
            default:
                log.error("UNKNOWN enum {}: {}", FinancialCategoryEnums.class.getSimpleName(), financialCategory);
                throw ExceptionUtil.business(ErrorCodeConstants.SERVICE_ERROR);
        }

        String pnum = null;
        VipInfoEntity vipInfoEntity = vipInfoService.queryEntityByCode(code);
        if (vipInfoEntity != null) {
            pnum = vipInfoEntity.getPnum();
        }

        FinancialAccountModifyMqDto mqDto = new FinancialAccountModifyMqDto(OptActionEnums.CREATE.getCode());
        mqDto.setLoginUserId(loginUser.getId()).setLoginNickName(loginUser.getNickname()).setPnum(pnum);
        mqDto.setFinancialCategory(financialCategory.getCodeEnum()).setFinancialAccount(createDto.getFinancialAccount())
                .setBankAccountName(createDto.getBankAccountName()).setSwiftCode(createDto.getSwiftCode());

        MqMessageProductDto mqMessage = new MqMessageProductDto();
        mqMessage.setMessageCategoryCode(MqMessageEventCategoryEnums.FINANCIAL_ACCOUNT_MODIFY.getCode());
        mqMessage.setMessageFinancialAccountModify(mqDto);
        applicationContext.publishEvent(new MqMessageEvent(mqMessage));
        return id;
    }

//    // 补充银行卡状态属性
//    private void populateVipAccountProperties(
//            @Nullable FinancialCategoryEnums financialCategory, @NonNull VipInfoEntity entity,
//            @NonNull VipAccountRspDto rspDto) {
//        if (financialCategory == null) {
//            return;
//        }
//
//        EnableStatusEnums enableStatus = null;
//        switch (financialCategory) {
//            case TPP:
//                enableStatus = obtainTppEnableByTpp(entity.getTpp());
//
//                break;
//            case BANK:
//                enableStatus = obtainBankAccountEnableByTpp(entity.getTpp());
//
//                break;
//            default:
//                log.error("UNKNOWN {}: {}", FinancialCategoryEnums.class.getSimpleName(), financialCategory);
//                break;
//        }
//
//        rspDto.setEnable(enableStatus);
//    }

//    private EnableStatusEnums obtainTppEnableByTpp(@Nullable String tpp) {
//        if (tpp == null) {
//            log.warn("obtain tpp enable status by tpp failed, tpp is null.");
//            return null;
//        }
//        VipTppEntity entity = vipTppService.queryByTpp(tpp);
//        if (entity == null) {
//            log.warn("obtain tpp enable status by tpp failed, {} is null, tpp: {}.",
//                    VipTppEntity.class.getSimpleName(), tpp);
//            return null;
//        }
//
//        return entity.getEnable();
//    }

//    private EnableStatusEnums obtainBankAccountEnableByTpp(@Nullable String cardNo) {
//        if (cardNo == null) {
//            log.warn("obtain bankAccount enable status by cardNo failed, cardNo is null.");
//            return null;
//        }
//
//        VipBankAccountEntity entity = vipBankAccountService.queryByCardNo(cardNo);
//        if (entity == null) {
//            log.warn("obtain tpp enable status by cardNo failed, {} is null, cardNo: {}.",
//                    VipBankAccountEntity.class.getSimpleName(), cardNo);
//            return null;
//        }
//
//        return entity.getEnable();
//    }
}
