package com.eb.business.service;

import com.eb.business.dto.DatesRangeQueryReqDto;
import com.eb.business.dto.exceptionorder.req.ExceptionOrderAuditByIdUpdateReqDto;
import com.eb.business.dto.exceptionorder.req.ExceptionOrderQueryReqDto;
import com.eb.business.dto.exceptionorder.req.ExceptionOrderStatisticByMchntNoQueryReqDto;
import com.eb.business.dto.exceptionorder.req.ExceptionOrderStatisticByUserQueryReqDto;
import com.eb.business.dto.exceptionorder.req.SchedulingOrderQueryReqDto;
import com.eb.business.dto.exceptionorder.rsp.ExceptionOrderStatisticByDatesRspDto;
import com.eb.business.dto.exceptionorder.rsp.ExceptionOrderStatisticByMchntNoRspDto;
import com.eb.business.dto.exceptionorder.rsp.ExceptionOrderStatisticByUserRspDto;
import com.eb.business.dto.order.req.SchedulingTransferRecordQueryReqDto;
import com.eb.business.dto.statistic.req.StatisticByCodeQueryReqDto;
import com.eb.business.dto.statistic.req.StatisticByDayQueryReqDto;
import com.eb.business.dto.statistic.rsp.StatisticByCodeQueryRspDto;
import com.eb.business.dto.statistic.rsp.StatisticByDatesDQaueryRspDto;
import com.eb.constant.ErrorCodeConstants;
import com.eb.constant.enums.OrderAuditStatusEnums;
import com.eb.constant.enums.TransferStatusEnums;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.SchedulingTransferRecordEntity;
import com.eb.mp.mysql.entity.business.UtrUploadRecordEntity;
import com.eb.mp.mysql.entity.custom.code.BigDecimalCodeStatisticEntity;
import com.eb.mp.mysql.entity.custom.code.IntegerCodeStatisticEntity;
import com.eb.mp.mysql.entity.custom.code.LongCodeStatisticEntity;
import com.eb.mp.mysql.entity.custom.dates.BigDecimalDatesStatisticEntity;
import com.eb.mp.mysql.entity.custom.dates.IntegerDatesStatisticEntity;
import com.eb.mp.mysql.entity.custom.dates.LongDatesStatisticEntity;
import com.eb.mp.mysql.mapper.business.SchedulingTransferRecordMapper;
import com.eb.mvc.exception.ExceptionUtil;
import com.eb.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulingTransferRecordService {
    private final SchedulingTransferRecordMapper baseMapper;

    private final UtrUploadRecordService utrUploadRecordService;

    public SchedulingTransferRecordEntity queryEntityByCpOrderUnique(String cpOrder) {
        return baseMapper.queryEntityByCpOrderUnique(cpOrder);
    }

    public List<SchedulingTransferRecordEntity> queryListByCode(@NonNull String code) {
        return baseMapper.queryListByCode(code);
    }

    public PageResult<SchedulingTransferRecordEntity> listOrderPage(
            @NonNull PageParam pageParam, @NonNull SchedulingTransferRecordQueryReqDto queryDto) {
        return baseMapper.listPage(pageParam, queryDto);
    }

    @NonNull
    public List<SchedulingTransferRecordEntity> listOrder(
            SchedulingTransferRecordQueryReqDto queryDto) {
        return baseMapper.list(queryDto);
    }

    @Transactional
    public PageResult<StatisticByCodeQueryRspDto> listPageStatisticByCode(
            PageParam pageParam,
            @NonNull StatisticByCodeQueryReqDto queryDto) {
        List<String> codeList = new ArrayList<>();

        long total;
        if (queryDto.getCode() != null) {
            codeList.add(queryDto.getCode());
            total = 1L;
        } else {
            PageResult<String> codePage = baseMapper.pageQueryDistinctCode(pageParam);
            total = codePage.getTotal();
            codeList = codePage.getList();
        }

        List<StatisticByCodeQueryRspDto> dtoList = listStatisticByCode(queryDto.getDates(), codeList);

        return new PageResult<>(dtoList, total);
    }

    public List<StatisticByCodeQueryRspDto> listStatisticByCode(
            int dates, List<String> codeList) {

        if (codeList == null || codeList.isEmpty()) {
            return new ArrayList<>();
        }

        List<StatisticByCodeQueryRspDto> rspList = new ArrayList<>();
        for (String code : codeList) {
            StatisticByCodeQueryRspDto rspDto = new StatisticByCodeQueryRspDto();
            rspDto.setDates(dates);
            rspDto.setCode(code);
            rspList.add(rspDto);
        }

        populateOrderTotalNumberByCode(rspList, dates);
        populateOrderFailedNumberByCode(rspList, dates);
        populateOrderSuccessNumberByCode(rspList, dates);
        populateOrderTotalAmountByCode(rspList, dates);
        populateMsTotalByCode(rspList, dates);

        // 后续补充计算
        listStatisticPostByCode(rspList);
        return rspList;
    }

    @Transactional
    @NonNull
    public List<StatisticByCodeQueryRspDto> listStatisticByCode(
            @NonNull StatisticByCodeQueryReqDto queryDto) {

        List<String> codeList = new ArrayList<>();

        if (queryDto.getCode() != null) {
            codeList.add(queryDto.getCode());
        } else {
            codeList = baseMapper.distinctCode();
        }

        return listStatisticByCode(queryDto.getDates(), codeList);
    }

    private static void listStatisticPostByCode(@NonNull List<StatisticByCodeQueryRspDto> rspList) {
        for (StatisticByCodeQueryRspDto rspDto : rspList) {
            Integer orderTotalNumber = rspDto.getOrderTotalNumber();
            Integer orderSuccessNumber = rspDto.getOrderSuccessNumber();
            Integer orderFailedNumber = rspDto.getOrderFailedNumber();
            Long msTotal = rspDto.getMsTotal();

            if (orderTotalNumber == null) {
                orderTotalNumber = 0;
            }
            if (orderSuccessNumber == null) {
                orderSuccessNumber = 0;
            }
            if (orderFailedNumber == null) {
                orderFailedNumber = 0;
            }
            if (msTotal == null) {
                msTotal = 0L;
            }

            // 收货订单数
            rspDto.setOrderReceiveNumber(orderSuccessNumber);

            if (orderTotalNumber != 0) {
                // 发货率
                BigDecimal transferRate = BigDecimal.valueOf(orderSuccessNumber)
                        .divide(BigDecimal.valueOf(orderTotalNumber), 4, RoundingMode.HALF_UP);
                rspDto.setTransferRate(transferRate);

                // 平均响应时间（单位s）
                int ms = msTotal.intValue() / orderTotalNumber;
                rspDto.setTransferSeconds(ms / 1000);
            }
        }
    }

    @NonNull
    public List<StatisticByDatesDQaueryRspDto> listStatisticByDates(
            @NonNull StatisticByDayQueryReqDto queryDto) {
        // 日期，总订单数，已确认订单数，已确认订单比例，收货订单数，销售额，发货率，平均响应时间（单位s）
        // 已确认订单数：总订单数 - 失败订单数
        List<StatisticByDatesDQaueryRspDto> rspList = new ArrayList<>();
        for (int dates = queryDto.getDatesStart(); dates <= queryDto.getDatesLast(); dates = DateUtils.plusDays(dates, 1)) {
            StatisticByDatesDQaueryRspDto rspDto = new StatisticByDatesDQaueryRspDto();
            rspDto.setDates(dates);
            rspList.add(rspDto);
        }

        populateOrderTotalNumberByDates(rspList, queryDto);
        populateOrderFailedNumberByDates(rspList, queryDto);
        populateOrderSuccessNumberByDates(rspList, queryDto);
        populateOrderTotalAmountByDates(rspList, queryDto);
        populateMsTotalByDates(rspList, queryDto);

        listStatisticPostByDates(rspList);

        return rspList;
    }

    private static void listStatisticPostByDates(@NonNull List<StatisticByDatesDQaueryRspDto> rspList) {
        for (StatisticByDatesDQaueryRspDto rspDto : rspList) {
            Integer orderTotalNumber = rspDto.getOrderTotalNumber();
            Integer orderSuccessNumber = rspDto.getOrderSuccessNumber();
            Integer orderFailedNumber = rspDto.getOrderFailedNumber();
            Long msTotal = rspDto.getMsTotal();

            if (orderTotalNumber == null) {
                orderTotalNumber = 0;
            }
            if (orderSuccessNumber == null) {
                orderSuccessNumber = 0;
            }
            if (orderFailedNumber == null) {
                orderFailedNumber = 0;
            }
            if (msTotal == null) {
                msTotal = 0L;
            }

            // 收货订单数
            rspDto.setOrderReceiveNumber(orderSuccessNumber);

            // 已确认订单数
            int orderConfirmNumber = orderTotalNumber - orderFailedNumber;
            rspDto.setOrderConfirmNumber(orderConfirmNumber);

            if (orderTotalNumber != 0) {
                // 发货率
                BigDecimal transferRate = BigDecimal.valueOf(orderSuccessNumber)
                        .divide(BigDecimal.valueOf(orderTotalNumber), 4, RoundingMode.HALF_UP);
                rspDto.setTransferRate(transferRate);

                // 已确认订单比例
                BigDecimal confirmRate = BigDecimal.valueOf(orderConfirmNumber)
                        .divide(BigDecimal.valueOf(orderTotalNumber), 4, RoundingMode.HALF_UP);
                rspDto.setOrderConfirmRate(confirmRate);

                // 平均响应时间（单位s）
                int ms = msTotal.intValue() / orderTotalNumber;
                rspDto.setTransferSeconds(ms / 1000);
            }
        }
    }

    // 补充订单总数
    private void populateOrderTotalNumberByDates(
            @NonNull List<StatisticByDatesDQaueryRspDto> rspList, @NonNull StatisticByDayQueryReqDto queryDto) {
        List<IntegerDatesStatisticEntity> datesStatisticEntities = baseMapper.orderTotalNumberByDates(
                queryDto.getDatesStart(), queryDto.getDatesLast(), queryDto.getMchntNo());
        if (datesStatisticEntities == null) {
            return;
        }

        for (IntegerDatesStatisticEntity datesStatisticEntity : datesStatisticEntities) {
            for (StatisticByDatesDQaueryRspDto rspDto : rspList) {
                if (datesStatisticEntity.getDates().equals(rspDto.getDates())) {
                    rspDto.setOrderTotalNumber(datesStatisticEntity.getStatistic());
                }
            }
        }
    }

    private void populateOrderFailedNumberByDates(
            @NonNull List<StatisticByDatesDQaueryRspDto> rspList, @NonNull StatisticByDayQueryReqDto queryDto) {
        List<IntegerDatesStatisticEntity> datesStatisticEntities = baseMapper.orderStatusNumberByDates(
                queryDto.getDatesStart(), queryDto.getDatesLast(), TransferStatusEnums.FAILED, queryDto.getMchntNo());
        if (datesStatisticEntities == null) {
            return;
        }

        for (IntegerDatesStatisticEntity datesStatisticEntity : datesStatisticEntities) {
            for (StatisticByDatesDQaueryRspDto rspDto : rspList) {
                if (datesStatisticEntity.getDates().equals(rspDto.getDates())) {
                    rspDto.setOrderFailedNumber(datesStatisticEntity.getStatistic());
                }
            }
        }
    }

    private void populateOrderSuccessNumberByDates(
            @NonNull List<StatisticByDatesDQaueryRspDto> rspList, @NonNull StatisticByDayQueryReqDto queryDto) {
        List<IntegerDatesStatisticEntity> datesStatisticEntities = baseMapper.orderStatusNumberByDates(
                queryDto.getDatesStart(), queryDto.getDatesLast(), TransferStatusEnums.SUCCESS, queryDto.getMchntNo());
        if (datesStatisticEntities == null) {
            return;
        }

        for (IntegerDatesStatisticEntity datesStatisticEntity : datesStatisticEntities) {
            for (StatisticByDatesDQaueryRspDto rspDto : rspList) {
                if (datesStatisticEntity.getDates().equals(rspDto.getDates())) {
                    rspDto.setOrderSuccessNumber(datesStatisticEntity.getStatistic());
                }
            }
        }
    }

    private void populateOrderTotalAmountByDates(
            @NonNull List<StatisticByDatesDQaueryRspDto> rspList, @NonNull StatisticByDayQueryReqDto queryDto) {
        List<BigDecimalDatesStatisticEntity> datesStatisticEntities = baseMapper.orderTotalAmountByDates(
                queryDto.getDatesStart(), queryDto.getDatesLast(), queryDto.getMchntNo());
        if (datesStatisticEntities == null) {
            return;
        }

        for (BigDecimalDatesStatisticEntity datesStatisticEntity : datesStatisticEntities) {
            for (StatisticByDatesDQaueryRspDto rspDto : rspList) {
                if (datesStatisticEntity.getDates().equals(rspDto.getDates())) {
                    rspDto.setOrderTotalAmount(datesStatisticEntity.getStatistic());
                }
            }
        }
    }

    private void populateMsTotalByDates(
            @NonNull List<StatisticByDatesDQaueryRspDto> rspList, @NonNull StatisticByDayQueryReqDto queryDto) {
        List<LongDatesStatisticEntity> datesStatisticEntities = baseMapper.msTotalByDates(
                queryDto.getDatesStart(), queryDto.getDatesLast(), queryDto.getMchntNo());
        if (datesStatisticEntities == null) {
            return;
        }

        for (LongDatesStatisticEntity datesStatisticEntity : datesStatisticEntities) {
            for (StatisticByDatesDQaueryRspDto rspDto : rspList) {
                if (datesStatisticEntity.getDates().equals(rspDto.getDates())) {
                    rspDto.setMsTotal(datesStatisticEntity.getStatistic());
                }
            }
        }
    }


    // 补充订单总数
    private void populateOrderTotalNumberByCode(
            @NonNull List<StatisticByCodeQueryRspDto> rspList, @NonNull Integer dates) {
        List<IntegerCodeStatisticEntity> codeStatisticEntities = baseMapper.orderTotalNumberByCode(dates);
        if (codeStatisticEntities == null) {
            return;
        }

        for (IntegerCodeStatisticEntity codeStatisticEntity : codeStatisticEntities) {
            for (StatisticByCodeQueryRspDto rspDto : rspList) {
                if (codeStatisticEntity.getCode().equals(rspDto.getCode())) {
                    rspDto.setOrderTotalNumber(codeStatisticEntity.getStatistic());
                }
            }
        }
    }

    private void populateOrderFailedNumberByCode(
            @NonNull List<StatisticByCodeQueryRspDto> rspList, @NonNull Integer dates) {
        List<IntegerCodeStatisticEntity> codeStatisticEntities
                = baseMapper.orderStatusNumberByCode(dates, TransferStatusEnums.FAILED);
        if (codeStatisticEntities == null) {
            return;
        }

        for (IntegerCodeStatisticEntity codeStatisticEntity : codeStatisticEntities) {
            for (StatisticByCodeQueryRspDto rspDto : rspList) {
                if (codeStatisticEntity.getCode().equals(rspDto.getCode())) {
                    rspDto.setOrderFailedNumber(codeStatisticEntity.getStatistic());
                }
            }
        }
    }

    private void populateOrderSuccessNumberByCode(
            @NonNull List<StatisticByCodeQueryRspDto> rspList, @NonNull Integer dates) {
        List<IntegerCodeStatisticEntity> codeStatisticEntities
                = baseMapper.orderStatusNumberByCode(dates, TransferStatusEnums.SUCCESS);
        if (codeStatisticEntities == null) {
            return;
        }

        for (IntegerCodeStatisticEntity codeStatisticEntity : codeStatisticEntities) {
            for (StatisticByCodeQueryRspDto rspDto : rspList) {
                if (codeStatisticEntity.getCode().equals(rspDto.getCode())) {
                    rspDto.setOrderSuccessNumber(codeStatisticEntity.getStatistic());
                }
            }
        }
    }

    private void populateOrderTotalAmountByCode(
            @NonNull List<StatisticByCodeQueryRspDto> rspList, @NonNull Integer dates) {
        List<BigDecimalCodeStatisticEntity> codeStatisticEntities = baseMapper.orderTotalAmountByCode(dates);
        if (codeStatisticEntities == null) {
            return;
        }

        for (BigDecimalCodeStatisticEntity codeStatisticEntity : codeStatisticEntities) {
            for (StatisticByCodeQueryRspDto rspDto : rspList) {
                if (codeStatisticEntity.getCode().equals(rspDto.getCode())) {
                    rspDto.setOrderTotalAmount(codeStatisticEntity.getStatistic());
                }
            }
        }
    }

    private void populateMsTotalByCode(
            @NonNull List<StatisticByCodeQueryRspDto> rspList, @NonNull Integer dates) {
        List<LongCodeStatisticEntity> codeStatisticEntities = baseMapper.msTotalByCode(dates);
        if (codeStatisticEntities == null) {
            return;
        }

        for (LongCodeStatisticEntity codeStatisticEntity : codeStatisticEntities) {
            for (StatisticByCodeQueryRspDto rspDto : rspList) {
                if (codeStatisticEntity.getCode().equals(rspDto.getCode())) {
                    rspDto.setMsTotal(codeStatisticEntity.getStatistic());
                }
            }
        }
    }

    public List<String> mchntNoList() {
        return baseMapper.mchntNoList();
    }

    public PageResult<SchedulingTransferRecordEntity> listPageByUtrLike(
            PageParam pageParam, @Nullable String utrLike) {
        SchedulingTransferRecordQueryReqDto queryDto = new SchedulingTransferRecordQueryReqDto();
        queryDto.setUtrLike(utrLike);
        return baseMapper.listPage(pageParam, queryDto);
    }

    /**
     * @param orderLike cp 订单号或者我方订单号
     */
    public PageResult<SchedulingTransferRecordEntity> listPageByCpOrSvipOrderLike(
            PageParam pageParam, @Nullable String orderLike) {
        return baseMapper.listPageByCpOrSvipOrderLike(pageParam, orderLike);
    }

    @Transactional
    public void updateEntityById(@NonNull SchedulingTransferRecordEntity updateEntity) {
        if (updateEntity.getId() == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.PARAMETER_ERROR);
        }

        SchedulingTransferRecordEntity historyEntity = baseMapper.selectById(updateEntity.getId());
        if (historyEntity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.RECORD_NOT_EXISTS, updateEntity.getId());
        }

        updateEntity.setUpdated(new Date());
        baseMapper.updateById(updateEntity);
    }

    public PageResult<SchedulingTransferRecordEntity> listPageOr(
            PageParam pageParam, SchedulingOrderQueryReqDto queryReqDto) {
        return baseMapper.listPageOr(pageParam, queryReqDto);
    }

    /**
     * 分页查询需要审核的订单
     */
    public PageResult<SchedulingTransferRecordEntity> listPageSchedulingRecordAudit(
            PageParam pageParam, ExceptionOrderQueryReqDto queryReqDto) {
        return baseMapper.listPageSchedulingRecordAudit(pageParam, queryReqDto);
    }

    /**
     * 订单审核，同时修改部分属性
     */
    @Transactional
    public void schedulingRecordAuditById(ExceptionOrderAuditByIdUpdateReqDto updateReqDto) {
        if (updateReqDto.getId() == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.PARAMETER_ERROR);
        }

        SchedulingTransferRecordEntity historyEntity = baseMapper.selectById(updateReqDto.getId());
        if (historyEntity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.RECORD_NOT_EXISTS, updateReqDto.getId());
        }

        String cpOrder = updateReqDto.getCpOrder();
        if (cpOrder != null && !cpOrder.equals(historyEntity.getCpOrder())) {
            SchedulingTransferRecordEntity cpOrderEntity = queryEntityByCpOrderUnique(cpOrder);
            if (cpOrderEntity != null) {
                throw ExceptionUtil.business(ErrorCodeConstants.SCHEDULING_TRANSFER_RECORD_CP_ORDER_EXISTS, cpOrder);
            }
        }

        OrderAuditStatusEnums orderAuditStatus = updateReqDto.getOrderAuditStatus();
        // 当审批通过之后，需要将utr 表中的utr 的值回填到订单表中。
        if (orderAuditStatus != null && orderAuditStatus.equals(OrderAuditStatusEnums.SUCCESS)) {
            // 到utr 表中去查询utr 的数据并写入到调度表
            // 通过cpOrder 查询到一条utr 记录
            UtrUploadRecordEntity utrEntity = utrUploadRecordService.queryEntityByAuditOrder(cpOrder);
            if (utrEntity != null && StringUtils.hasText(utrEntity.getUtr())) {
                // 回填utr
                historyEntity.setUtr(utrEntity.getUtr());
            }

            // 审核通过时，修改状态。
            historyEntity.setStatus(TransferStatusEnums.PAY_WAITING_DELIVER);
        }

        // suyh - 特别大写的红字提示了，不要记录审核人。
        historyEntity.setOrderAuditStatus(orderAuditStatus)
                .setCpOrder(cpOrder).setRealAmount(updateReqDto.getAmount())
                .setPnum(updateReqDto.getPnum()).setAuditRemark(updateReqDto.getRemark());

        historyEntity.setUpdated(new Date());
        baseMapper.updateById(historyEntity);
    }

    public PageResult<ExceptionOrderStatisticByDatesRspDto> listPageStatisticExceptionOrderByDates(
            PageParam pageParam, DatesRangeQueryReqDto queryReqDto) {
        // 按天统计，每天一条记录，分页就按天数计算。
        List<ExceptionOrderStatisticByDatesRspDto> dtoList = new ArrayList<>();
        for (int dates = queryReqDto.getDatesStart(); dates <= queryReqDto.getDatesLast(); dates = DateUtils.plusDays(dates, 1)) {
            ExceptionOrderStatisticByDatesRspDto dto = new ExceptionOrderStatisticByDatesRspDto();
            dto.setDates(dates);
            dto.setExceptionOrderCount(0).setAuditSuccessOrderCount(0);

            dtoList.add(dto);
        }
        long total = dtoList.size();
        List<ExceptionOrderStatisticByDatesRspDto> rspList = PageParam.doPageList(pageParam, dtoList);
        if (rspList.isEmpty()) {
            return new PageResult<>(rspList, total);
        }

        DatesRangeQueryReqDto queryDto = new DatesRangeQueryReqDto();
        Integer datesStart = rspList.get(0).getDates();
        Integer datesLast = rspList.get(rspList.size() - 1).getDates();
        queryDto.setDatesStart(datesStart).setDatesLast(datesLast);

        // 补充统计属性
        populateExceptionOrderTotalCountByDates(rspList, queryDto);
        populateExceptionOrderAuditSuccessCountByDates(rspList, queryDto);

        return new PageResult<>(rspList, total);
    }

    public PageResult<ExceptionOrderStatisticByUserRspDto> listPageStatisticExceptionByUser(
            PageParam pageParam, ExceptionOrderStatisticByUserQueryReqDto queryReqDto) {
        Integer datesStart = queryReqDto.getDatesStart();
        Integer datesLast = queryReqDto.getDatesLast();
        String nickNameLike = queryReqDto.getNickNameLike();
        long total = baseMapper.listExceptionOrderStatisticCountByNickNameCount(
                datesStart, datesLast, nickNameLike);
        List<ExceptionOrderStatisticByUserRspDto> dtoList
                = baseMapper.listExceptionOrderStatisticCountByNickName(
                pageParam.getPageStart(), pageParam.getPageSize(), datesStart, datesLast, nickNameLike);

        return new PageResult<>(dtoList, total);
    }

    public PageResult<ExceptionOrderStatisticByMchntNoRspDto> listPageStatisticExceptionByMchntNo(
            PageParam pageParam, ExceptionOrderStatisticByMchntNoQueryReqDto queryReqDto) {
        Integer datesStart = queryReqDto.getDatesStart();
        Integer datesLast = queryReqDto.getDatesLast();
        String mchntNoLike = queryReqDto.getMchntNoLike();
        long total = baseMapper.listExceptionOrderStatisticCountByMchntCount(
                datesStart, datesLast, mchntNoLike);
        List<ExceptionOrderStatisticByMchntNoRspDto> dtoList
                = baseMapper.listExceptionOrderStatisticCountByMchnt(
                pageParam.getPageStart(), pageParam.getPageSize(), datesStart, datesLast, mchntNoLike);

        return new PageResult<>(dtoList, total);
    }

    private void populateExceptionOrderTotalCountByDates(
            List<ExceptionOrderStatisticByDatesRspDto> rspList, DatesRangeQueryReqDto queryDto) {
        // 异常订单总数
        List<IntegerDatesStatisticEntity> datesStatisticEntities
                = baseMapper.exceptionOrderStatisticCountByDates(queryDto.getDatesStart(), queryDto.getDatesLast());
        if (datesStatisticEntities == null) {
            return;
        }

        for (IntegerDatesStatisticEntity datesStatisticEntity : datesStatisticEntities) {
            for (ExceptionOrderStatisticByDatesRspDto rspDto : rspList) {
                if (datesStatisticEntity.getDates().equals(rspDto.getDates())) {
                    rspDto.setExceptionOrderCount(datesStatisticEntity.getStatistic());
                }
            }
        }
    }

    private void populateExceptionOrderAuditSuccessCountByDates(
            List<ExceptionOrderStatisticByDatesRspDto> rspList, DatesRangeQueryReqDto queryDto) {
        // 异常订单审核通过计数
        List<IntegerDatesStatisticEntity> datesStatisticEntities
                = baseMapper.exceptionOrderStatisticAuditStatusCountByDates(
                queryDto.getDatesStart(), queryDto.getDatesLast(), OrderAuditStatusEnums.SUCCESS);
        if (datesStatisticEntities == null) {
            return;
        }

        for (IntegerDatesStatisticEntity datesStatisticEntity : datesStatisticEntities) {
            for (ExceptionOrderStatisticByDatesRspDto rspDto : rspList) {
                if (datesStatisticEntity.getDates().equals(rspDto.getDates())) {
                    rspDto.setAuditSuccessOrderCount(datesStatisticEntity.getStatistic());
                }
            }
        }
    }
}
