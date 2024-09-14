package com.eb.business.service;

import com.eb.business.dto.DatesRangeQueryReqDto;
import com.eb.business.dto.exceptionorder.req.ExceptionOrderAuditByCpOrderReviseUpdateReqDto;
import com.eb.business.dto.exceptionorder.req.ExceptionOrderAuditByIdUpdateReqDto;
import com.eb.business.dto.exceptionorder.req.ExceptionOrderQueryReqDto;
import com.eb.business.dto.exceptionorder.req.ExceptionOrderStatisticByMchntNoQueryReqDto;
import com.eb.business.dto.exceptionorder.req.ExceptionOrderStatisticByUserQueryReqDto;
import com.eb.business.dto.exceptionorder.req.SchedulingOrderQueryReqDto;
import com.eb.business.dto.exceptionorder.rsp.ExceptionOrderStatisticByDatesRspDto;
import com.eb.business.dto.exceptionorder.rsp.ExceptionOrderStatisticByMchntNoRspDto;
import com.eb.business.dto.exceptionorder.rsp.ExceptionOrderStatisticByUserRspDto;
import com.eb.business.dto.exceptionorder.rsp.SchedulingTransferRecordAuditRspDto;
import com.eb.constant.ErrorCodeConstants;
import com.eb.constant.enums.AuditRecordTypeEnums;
import com.eb.constant.enums.ExceptionOrderSceneEnums;
import com.eb.constant.enums.OrderAuditStatusEnums;
import com.eb.constant.enums.OrderMatchStatusEnums;
import com.eb.constant.enums.TransferStatusEnums;
import com.eb.event.MqMessageEvent;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.SchedulingTransferRecordEntity;
import com.eb.mp.mysql.entity.business.UtrUploadRecordEntity;
import com.eb.mp.mysql.entity.custom.ExceptionOrderEntity;
import com.eb.mp.mysql.mapper.custom.ExceptionOrderMapper;
import com.eb.mq.product.dto.business.ExceptionOrderAuditPassMqDto;
import com.eb.mq.product.dto.MqMessageProductDto;
import com.eb.mq.product.dto.enums.MqMessageEventCategoryEnums;
import com.eb.mvc.authentication.LoginUser;
import com.eb.mvc.exception.ExceptionUtil;
import com.eb.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author suyh
 * @since 2024-09-09
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ExceptionOrderService {
    private final ApplicationContext applicationContext;

    private final ExceptionOrderMapper baseMapper;
    private final SchedulingTransferRecordService schedulingTransferRecordService;
    private final UtrUploadRecordService utrUploadRecordService;

    @Transactional
    public Long utrRecordCreate(UtrUploadRecordEntity entity) {
        SchedulingTransferRecordEntity schedulingTransferRecordEntity
                = schedulingTransferRecordService.queryEntityByCpOrderUnique(entity.getCpOrder());
        Integer dates = DateUtils.convertToInteger(LocalDate.now());
        OrderMatchStatusEnums hit = (schedulingTransferRecordEntity == null
                ? OrderMatchStatusEnums.MISMATCH : OrderMatchStatusEnums.MATCH);
        entity.setHit(hit);
        entity.setType(AuditRecordTypeEnums.BACKEND);
        entity.setDates(dates);
        return utrUploadRecordService.createEntity(entity);
    }

    public List<ExceptionOrderEntity> listUtrRecord(ExceptionOrderQueryReqDto queryReqDto) {
        return baseMapper.listUtrRecord(queryReqDto);
    }

    public PageResult<ExceptionOrderEntity> listPageUtrRecord(
            PageParam pageParam, ExceptionOrderQueryReqDto queryReqDto) {
        long total = baseMapper.listPageUtrRecordCount(queryReqDto);
        List<ExceptionOrderEntity> entities = null;
        if (total > 0) {
            entities = baseMapper.listPageUtrRecord(
                    pageParam.getPageStart(), pageParam.getPageSize(), queryReqDto);
        }

        return new PageResult<>(entities, total);
    }

    /**
     * 按or 连接进行查询
     */
    public PageResult<SchedulingTransferRecordEntity> listPageSchedulingRecordOr(
            PageParam pageParam, SchedulingOrderQueryReqDto queryReqDto) {
        return schedulingTransferRecordService.listPageOr(pageParam, queryReqDto);
    }

    @Transactional
    public ExceptionOrderSceneEnums updateAuditStatueByCpOrder(
            LoginUser loginUser, @NonNull String cpOrder, @NonNull OrderAuditStatusEnums auditStatus) {

        SchedulingTransferRecordEntity historyEntity = schedulingTransferRecordService.queryEntityByCpOrderUnique(cpOrder);
        if (historyEntity == null) {
            return ExceptionOrderSceneEnums.MISMATCH;
        }

        TransferStatusEnums status = historyEntity.getStatus();
        if (status != null && status.equals(TransferStatusEnums.SUCCESS)) {
            throw ExceptionUtil.business(ErrorCodeConstants.SCHEDULING_TRANSFER_RECORD_STATUS_SUCCESS);
        }

        OrderAuditStatusEnums orderAuditStatus = historyEntity.getOrderAuditStatus();
        if (orderAuditStatus != null && !orderAuditStatus.equals(OrderAuditStatusEnums.NORMAL)) {
            throw ExceptionUtil.business(ErrorCodeConstants.SCHEDULING_TRANSFER_RECORD_STATUS_MATCH);
        }

        historyEntity.setOrderAuditStatus(auditStatus);
        historyEntity.setAuditUserId(loginUser.getId())
                .setAuditUserNick(loginUser.getNickname());
        schedulingTransferRecordService.updateEntityById(historyEntity);

        return ExceptionOrderSceneEnums.MATCH;
    }

    @Transactional
    public void updateAuditStatueByCpOrderRevise(
            LoginUser loginUser, ExceptionOrderAuditByCpOrderReviseUpdateReqDto updateReqDto, OrderAuditStatusEnums auditStatus) {
        Long id = updateReqDto.getId();
        String cpOrderRevise = updateReqDto.getCpOrderRevise();
        SchedulingTransferRecordEntity historyEntity = schedulingTransferRecordService.queryEntityByCpOrderUnique(cpOrderRevise);
        if (historyEntity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.SCHEDULING_TRANSFER_RECORD_CP_ORDER_REVISE_NOT_MATCH, cpOrderRevise);
        }

        UtrUploadRecordEntity utrUploadRecordEntity = utrUploadRecordService.queryEntityById(id);
        if (utrUploadRecordEntity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.UTR_UPLOAD_RECORD_NOT_EXISTS, id);
        }

        TransferStatusEnums status = historyEntity.getStatus();
        if (status != null && status.equals(TransferStatusEnums.SUCCESS)) {
            throw ExceptionUtil.business(ErrorCodeConstants.SCHEDULING_TRANSFER_RECORD_STATUS_SUCCESS);
        }

        historyEntity.setOrderAuditStatus(auditStatus);
        historyEntity.setAuditUserId(loginUser.getId())
                .setAuditUserNick(loginUser.getNickname());
        schedulingTransferRecordService.updateEntityById(historyEntity);

        utrUploadRecordEntity.setCpOrderRevise(cpOrderRevise).setPayAmount(updateReqDto.getAmount()).setPnum(updateReqDto.getPnum());
        utrUploadRecordService.updateById(utrUploadRecordEntity);
    }

    @Transactional
    public PageResult<SchedulingTransferRecordAuditRspDto> listPageSchedulingRecordAudit(
            PageParam pageParam, ExceptionOrderQueryReqDto queryReqDto) {

        PageResult<SchedulingTransferRecordEntity> pageResult
                = schedulingTransferRecordService.listPageSchedulingRecordAudit(pageParam, queryReqDto);

        Long total = pageResult.getTotal();
        List<SchedulingTransferRecordEntity> orderList = pageResult.getList();
        List<SchedulingTransferRecordAuditRspDto> dtoList = null;
        if (orderList != null) {
            dtoList = new ArrayList<>();
            // 补充utr 信息
            for (SchedulingTransferRecordEntity orderEntity : orderList) {
                UtrUploadRecordEntity utrEntity = utrUploadRecordService.queryEntityByCpOrder(orderEntity.getCpOrder());

                SchedulingTransferRecordAuditRspDto dto = new SchedulingTransferRecordAuditRspDto();
                dto.setOrderEntity(orderEntity);
                dto.setUtrEntity(utrEntity);
                dtoList.add(dto);
            }
        }

        return new PageResult<>(dtoList, total);
    }

    @Transactional
    public void schedulingRecordAuditById(LoginUser loginUser, ExceptionOrderAuditByIdUpdateReqDto updateReqDto) {
        OrderAuditStatusEnums orderAuditStatus = updateReqDto.getOrderAuditStatus();
        switch (orderAuditStatus) {
            case SUCCESS:
            case REJECTED:
                break;
            case NORMAL:
            case WAITING_PROCESS:
            case DOUBT:
            default:
                throw ExceptionUtil.business(ErrorCodeConstants.SCHEDULING_TRANSFER_RECORD_ORDER_AUDIT_ERROR, orderAuditStatus);
        }

        schedulingTransferRecordService.schedulingRecordAuditById(updateReqDto);

        if (orderAuditStatus.equals(OrderAuditStatusEnums.SUCCESS)) {
            ExceptionOrderAuditPassMqDto mqDto = new ExceptionOrderAuditPassMqDto();
            mqDto.setLoginUserId(loginUser.getId()).setLoginNickName(loginUser.getNickname());
            mqDto.setCpOrder(updateReqDto.getCpOrder()).setAmount(updateReqDto.getAmount())
                    .setPnum(updateReqDto.getPnum()).setRemark(updateReqDto.getRemark());

            MqMessageProductDto mqMessage = new MqMessageProductDto();
            mqMessage.setMessageCategoryCode(MqMessageEventCategoryEnums.EXCEPTION_ORDER_AUDIT_SUCCESS.getCode());
            mqMessage.setExceptionOrderAuditPass(mqDto);
            applicationContext.publishEvent(new MqMessageEvent(mqMessage));
        }
    }

    public PageResult<ExceptionOrderStatisticByDatesRspDto> listPageStatisticExceptionOrderByDates(
            PageParam pageParam, DatesRangeQueryReqDto queryReqDto) {
        return schedulingTransferRecordService.listPageStatisticExceptionOrderByDates(pageParam, queryReqDto);
    }

    public PageResult<ExceptionOrderStatisticByUserRspDto> listPageStatisticExceptionByUser(
            PageParam pageParam, ExceptionOrderStatisticByUserQueryReqDto queryReqDto) {
        return schedulingTransferRecordService.listPageStatisticExceptionByUser(pageParam, queryReqDto);
    }

    public PageResult<ExceptionOrderStatisticByMchntNoRspDto> listPageStatisticExceptionByMchntNo(
            PageParam pageParam, ExceptionOrderStatisticByMchntNoQueryReqDto queryReqDto) {
        return schedulingTransferRecordService.listPageStatisticExceptionByMchntNo(pageParam, queryReqDto);
    }
}
