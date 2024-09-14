package com.eb.mp.mysql.mapper.business;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eb.business.dto.exceptionorder.req.ExceptionOrderQueryReqDto;
import com.eb.business.dto.exceptionorder.req.SchedulingOrderQueryReqDto;
import com.eb.business.dto.exceptionorder.rsp.ExceptionOrderStatisticByMchntNoRspDto;
import com.eb.business.dto.exceptionorder.rsp.ExceptionOrderStatisticByUserRspDto;
import com.eb.business.dto.order.req.SchedulingTransferRecordQueryReqDto;
import com.eb.constant.DataSourceNames;
import com.eb.constant.enums.OrderAuditStatusEnums;
import com.eb.constant.enums.TransferStatusEnums;
import com.eb.mp.mybatis.BaseMapperX;
import com.eb.mp.mybatis.LambdaQueryWrapperX;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.SchedulingTransferRecordEntity;
import com.eb.mp.mysql.entity.custom.code.BigDecimalCodeStatisticEntity;
import com.eb.mp.mysql.entity.custom.code.IntegerCodeStatisticEntity;
import com.eb.mp.mysql.entity.custom.code.LongCodeStatisticEntity;
import com.eb.mp.mysql.entity.custom.dates.BigDecimalDatesStatisticEntity;
import com.eb.mp.mysql.entity.custom.dates.IntegerDatesStatisticEntity;
import com.eb.mp.mysql.entity.custom.dates.LongDatesStatisticEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Mapper
@DS(DataSourceNames.CDS_MYSQL)
public interface SchedulingTransferRecordMapper extends BaseMapperX<SchedulingTransferRecordEntity> {
    default PageResult<SchedulingTransferRecordEntity> listPageByCpOrSvipOrderLike(
            PageParam pageParam, @Nullable String orderLike) {
        LambdaQueryWrapperX<SchedulingTransferRecordEntity> queryWrapperX = build();
        if (orderLike != null) {
            queryWrapperX.or(w -> w.like(SchedulingTransferRecordEntity::getCpOrder, orderLike));
            queryWrapperX.or(w -> w.like(SchedulingTransferRecordEntity::getSvipOrderNo, orderLike));
        }

        queryWrapperX.orderByDesc(SchedulingTransferRecordEntity::getCreated);
        return selectPage(pageParam, queryWrapperX);
    }

    default List<SchedulingTransferRecordEntity> queryListByCode(@NonNull String code) {
        LambdaQueryWrapperX<SchedulingTransferRecordEntity> queryWrapperX = build();
        queryWrapperX.eq(SchedulingTransferRecordEntity::getCode, code);

        return selectList(queryWrapperX);
    }

    default PageResult<SchedulingTransferRecordEntity> listPage(
            @NonNull PageParam pageParam, @NonNull SchedulingTransferRecordQueryReqDto queryDto) {
        LambdaQueryWrapperX<SchedulingTransferRecordEntity> queryWrapperX = buildByQueryDto(queryDto);

        return selectPage(pageParam, queryWrapperX);
    }

    default List<SchedulingTransferRecordEntity> list(SchedulingTransferRecordQueryReqDto queryDto) {
        LambdaQueryWrapperX<SchedulingTransferRecordEntity> queryWrapperX = buildByQueryDto(queryDto);
        return selectList(queryWrapperX);
    }

    default LambdaQueryWrapperX<SchedulingTransferRecordEntity> buildByQueryDto(
            @NonNull SchedulingTransferRecordQueryReqDto queryDto) {
        LambdaQueryWrapperX<SchedulingTransferRecordEntity> queryWrapperX = build();

        queryWrapperX.geIfPresent(SchedulingTransferRecordEntity::getDates, queryDto.getDatesStart())
                .leIfPresent(SchedulingTransferRecordEntity::getDates, queryDto.getDatesLast());

        queryWrapperX.eqIfPresent(SchedulingTransferRecordEntity::getStatus, queryDto.getStatus())
                .likeIfPresent(SchedulingTransferRecordEntity::getPn, queryDto.getMchntNoLike())
                .likeIfPresent(SchedulingTransferRecordEntity::getCpOrder, queryDto.getCpOrderLike())
                .likeIfPresent(SchedulingTransferRecordEntity::getSvipOrderNo, queryDto.getSvipOrderNoLike())
                .likeIfPresent(SchedulingTransferRecordEntity::getMchntOrderNo, queryDto.getMchntOrderNoLike())
                .likeIfPresent(SchedulingTransferRecordEntity::getReceiverUid, queryDto.getUidLike())
                .likeIfPresent(SchedulingTransferRecordEntity::getPnum, queryDto.getPnumLike())
                .likeIfPresent(SchedulingTransferRecordEntity::getUtr, queryDto.getUtrLike());
        if (queryDto.getFinancialAccountLike() != null) {
            queryWrapperX.and(wrapper -> {
                wrapper.or(w -> w.like(SchedulingTransferRecordEntity::getTpp, queryDto.getFinancialAccountLike()))
                        .or(w -> w.like(SchedulingTransferRecordEntity::getCardNo, queryDto.getFinancialAccountLike()));
            });
        }

        queryWrapperX.orderByDesc(SchedulingTransferRecordEntity::getCreated);
        queryWrapperX.orderByAsc(SchedulingTransferRecordEntity::getId);
        return queryWrapperX;
    }

    default PageResult<String> pageQueryDistinctCode(PageParam pageParam) {
        QueryWrapper<SchedulingTransferRecordEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT code");
        queryWrapper.orderByAsc("code");
        PageResult<SchedulingTransferRecordEntity> pageResult = selectPage(pageParam, queryWrapper);
        List<SchedulingTransferRecordEntity> entities = pageResult.getList();
        List<String> codeList = null;
        if (entities != null) {
            codeList = new ArrayList<>();
            for (SchedulingTransferRecordEntity entity : entities) {
                codeList.add(entity.getCode());
            }
        }

        return new PageResult<>(codeList, pageResult.getTotal());
    }

    default SchedulingTransferRecordEntity queryEntityByCpOrderUnique(String cpOrder) {
        if (cpOrder == null) {
            return null;
        }

        LambdaQueryWrapperX<SchedulingTransferRecordEntity> queryWrapperX = build();
        queryWrapperX.eq(SchedulingTransferRecordEntity::getCpOrder, cpOrder);

        return selectOne(queryWrapperX);
    }

    default PageResult<SchedulingTransferRecordEntity> listPageOr(
            PageParam pageParam, @NonNull SchedulingOrderQueryReqDto queryReqDto) {
        LambdaQueryWrapperX<SchedulingTransferRecordEntity> queryWrapperX = build();

        queryWrapperX.geIfPresent(SchedulingTransferRecordEntity::getDates, queryReqDto.getDatesStart())
                .leIfPresent(SchedulingTransferRecordEntity::getDates, queryReqDto.getDatesLast());

        AtomicBoolean flag = new AtomicBoolean(false);
        Consumer<LambdaQueryWrapper<SchedulingTransferRecordEntity>> consumer = wrapper -> {
            if (queryReqDto.getCpOrder() != null) {
                flag.set(true);
                wrapper.or(w -> w.eq(SchedulingTransferRecordEntity::getCpOrder, queryReqDto.getCpOrder()));
            }
            // suyh - 专门跟wt 确认过的，这里就是精确匹配
            if (queryReqDto.getAmount() != null) {
                flag.set(true);
                wrapper.or(w -> w.eq(SchedulingTransferRecordEntity::getRealAmount, queryReqDto.getAmount()));
            }
            // suyh - 专门跟wt 确认过的，这里就是精确匹配
            if (queryReqDto.getCreated() != null) {
                flag.set(true);
                wrapper.or(w -> w.eq(SchedulingTransferRecordEntity::getCreated, queryReqDto.getCreated()));
            }
            if (queryReqDto.getCode() != null) {
                flag.set(true);
                wrapper.or(w -> w.eq(SchedulingTransferRecordEntity::getCode, queryReqDto.getCode()));
            }
            if (queryReqDto.getReceiverUid() != null) {
                flag.set(true);
                wrapper.or(w -> w.eq(SchedulingTransferRecordEntity::getReceiverUid, queryReqDto.getReceiverUid()));
            }
            if (queryReqDto.getPnum() != null) {
                flag.set(true);
                wrapper.or(w -> w.eq(SchedulingTransferRecordEntity::getPnum, queryReqDto.getPnum()));
            }
        };

        if (flag.get()) {
            queryWrapperX.and(consumer);
        }

        // 排序
        queryWrapperX.orderByAsc(SchedulingTransferRecordEntity::getCpOrder)
                .orderByAsc(SchedulingTransferRecordEntity::getRealAmount)
                .orderByDesc(SchedulingTransferRecordEntity::getCreated)
                .orderByAsc(SchedulingTransferRecordEntity::getReceiverUid)
                .orderByAsc(SchedulingTransferRecordEntity::getCode)
                .orderByAsc(SchedulingTransferRecordEntity::getPnum);

        return selectPage(pageParam, queryWrapperX);
    }

    /**
     * 分页查询需要审核的订单
     */
    default PageResult<SchedulingTransferRecordEntity> listPageSchedulingRecordAudit(
            PageParam pageParam, ExceptionOrderQueryReqDto queryReqDto) {
        LambdaQueryWrapperX<SchedulingTransferRecordEntity> queryWrapperX = build();

        List<OrderAuditStatusEnums> auditStatusList = Arrays.asList(
                OrderAuditStatusEnums.WAITING_PROCESS, OrderAuditStatusEnums.DOUBT,
                OrderAuditStatusEnums.SUCCESS, OrderAuditStatusEnums.REJECTED);
        queryWrapperX.in(SchedulingTransferRecordEntity::getOrderAuditStatus, auditStatusList);
        queryWrapperX.likeIfPresent(SchedulingTransferRecordEntity::getPn, queryReqDto.getMchntNoLike())
                .likeIfPresent(SchedulingTransferRecordEntity::getCpOrder, queryReqDto.getCpOrderLike())
                .likeIfPresent(SchedulingTransferRecordEntity::getCode, queryReqDto.getCodeLike())
                .likeIfPresent(SchedulingTransferRecordEntity::getPnum, queryReqDto.getPnumLike())
                .likeIfPresent(SchedulingTransferRecordEntity::getReceiverUid, queryReqDto.getBuyerUidLike())
                .geIfPresent(SchedulingTransferRecordEntity::getRealAmount, queryReqDto.getAmountLowerBound())
                .leIfPresent(SchedulingTransferRecordEntity::getRealAmount, queryReqDto.getAmountUpperBound())
                .likeIfPresent(SchedulingTransferRecordEntity::getUtr, queryReqDto.getUtrLike());

        if (queryReqDto.getFinancialAccountLike() != null) {
            queryWrapperX.and(wrapper -> {
                wrapper.or(w -> w.like(SchedulingTransferRecordEntity::getTpp, queryReqDto.getFinancialAccountLike()));
                wrapper.or(w -> w.like(SchedulingTransferRecordEntity::getCardNo, queryReqDto.getFinancialAccountLike()));
            });
        }

        return selectPage(pageParam, queryWrapperX);
    }

    // 总订单数
    List<IntegerDatesStatisticEntity> orderTotalNumberByDates(
            @NonNull @Param("datesStart") Integer datesStart,
            @NonNull @Param("datesLast") Integer datesLast,
            @Param("mchntNo") String mchntNo);

    List<IntegerDatesStatisticEntity> orderStatusNumberByDates(
            @NonNull @Param("datesStart") Integer datesStart,
            @NonNull @Param("datesLast") Integer datesLast,
            @NonNull @Param("status") TransferStatusEnums status,
            @Param("mchntNo") String mchntNo);

    List<BigDecimalDatesStatisticEntity> orderTotalAmountByDates(
            @NonNull @Param("datesStart") Integer datesStart,
            @NonNull @Param("datesLast") Integer datesLast,
            @Param("mchntNo") String mchntNo);

    List<LongDatesStatisticEntity> msTotalByDates(
            @NonNull @Param("datesStart") Integer datesStart,
            @NonNull @Param("datesLast") Integer datesLast,
            @Param("mchntNo") String mchntNo);

    // 总订单数
    @Select("select code, count(1) as statistic from scheduling_transfer_record " +
            "where dates = #{dates} " +
            "group by code ")
    List<IntegerCodeStatisticEntity> orderTotalNumberByCode(@Param("dates") Integer dates);

    @Select("select code, count(1) as statistic from scheduling_transfer_record " +
            "where dates = #{dates} and status = #{status} " +
            "group by code")
    List<IntegerCodeStatisticEntity> orderStatusNumberByCode(
            @Param("dates") Integer dates,
            @NonNull @Param("status") TransferStatusEnums status);

    @Select("select code, SUM(real_amount) as statistic from scheduling_transfer_record " +
            "where dates = #{dates} " +
            "group by code")
    List<BigDecimalCodeStatisticEntity> orderTotalAmountByCode(@Param("dates") Integer dates);

    @Select("select code, SUM(mtime - created) as statistic " +
            "from scheduling_transfer_record " +
            "where dates = #{dates} and created is not null and mtime is not NULL " +
            "group by code")
    List<LongCodeStatisticEntity> msTotalByCode(@Param("dates") Integer dates);

    // TODO: suyh - 商户号不确定是这个字段，说是要另外找个字段，可能 是pn，到时候要处理。
    @Select("select distinct pn from scheduling_transfer_record")
    List<String> mchntNoList();

    @Select("select distinct code from scheduling_transfer_record")
    List<String> distinctCode();

    // 正常订单: order_audit_status = 0
    @Select("select dates, count(1) as statistic " +
            "from scheduling_transfer_record " +
            "where order_audit_status != 0 " +
            "group by dates")
    List<IntegerDatesStatisticEntity> exceptionOrderStatisticCountByDates(
            @Param("datesStart") Integer datesStart,
            @Param("datesLast") Integer datesLast);

    @Select("select dates, count(1) as statistic " +
            "from scheduling_transfer_record " +
            "where order_audit_status = #{auditStatus} " +
            "group by dates")
    List<IntegerDatesStatisticEntity> exceptionOrderStatisticAuditStatusCountByDates(
            @Param("datesStart") Integer datesStart,
            @Param("datesLast") Integer datesLast,
            @Param("auditStatus") OrderAuditStatusEnums auditStatus);

    default long listExceptionOrderStatisticCountByNickNameCount(
            Integer datesStart, Integer datesLast, String nickNameLike) {
        return listExceptionOrderStatisticCountByNickNameCountXml(
                OrderAuditStatusEnums.NORMAL, OrderAuditStatusEnums.SUCCESS,
                datesStart, datesLast, nickNameLike);
    }

    default List<ExceptionOrderStatisticByUserRspDto> listExceptionOrderStatisticCountByNickName(
            int pageStart, int pageSize,
            Integer datesStart, Integer datesLast, String nickNameLike) {
        return listExceptionOrderStatisticCountByNickNameXml(
                OrderAuditStatusEnums.NORMAL, OrderAuditStatusEnums.SUCCESS,
                pageStart, pageSize,
                datesStart, datesLast, nickNameLike);
    }

    long listExceptionOrderStatisticCountByNickNameCountXml(
            @Param("orderAuditStatusNormal") OrderAuditStatusEnums orderAuditStatusNormal,
            @Param("orderAuditStatusSuccess") OrderAuditStatusEnums orderAuditStatusSuccess,
            @Param("datesStart") Integer datesStart,
            @Param("datesLast") Integer datesLast,
            @Param("nickNameLike") String nickNameLike);

    List<ExceptionOrderStatisticByUserRspDto> listExceptionOrderStatisticCountByNickNameXml(
            @Param("orderAuditStatusNormal") OrderAuditStatusEnums orderAuditStatusNormal,
            @Param("orderAuditStatusSuccess") OrderAuditStatusEnums orderAuditStatusSuccess,
            @Param("pageStart") int pageStart,
            @Param("pageSize") int pageSize,
            @Param("datesStart") Integer datesStart,
            @Param("datesLast") Integer datesLast,
            @Param("nickNameLike") String nickNameLike);

    default long listExceptionOrderStatisticCountByMchntCount(
            Integer datesStart, Integer datesLast, String mchntNoLike) {
        return listExceptionOrderStatisticCountByMchntCountXml(
                OrderAuditStatusEnums.NORMAL, OrderAuditStatusEnums.SUCCESS,
                datesStart, datesLast, mchntNoLike);
    }

    default List<ExceptionOrderStatisticByMchntNoRspDto> listExceptionOrderStatisticCountByMchnt(
            int pageStart, Integer pageSize, Integer datesStart, Integer datesLast, String mchntNoLike) {
        return listExceptionOrderStatisticCountByMchntXml(
                OrderAuditStatusEnums.NORMAL, OrderAuditStatusEnums.SUCCESS,
                pageStart, pageSize,
                datesStart, datesLast, mchntNoLike);
    }

    long listExceptionOrderStatisticCountByMchntCountXml(
            @Param("orderAuditStatusNormal") OrderAuditStatusEnums orderAuditStatusNormal,
            @Param("orderAuditStatusSuccess") OrderAuditStatusEnums orderAuditStatusSuccess,
            @Param("datesStart") Integer datesStart,
            @Param("datesLast") Integer datesLast,
            @Param("mchntNoLike") String mchntNoLike);

    List<ExceptionOrderStatisticByMchntNoRspDto> listExceptionOrderStatisticCountByMchntXml(
            @Param("orderAuditStatusNormal") OrderAuditStatusEnums orderAuditStatusNormal,
            @Param("orderAuditStatusSuccess") OrderAuditStatusEnums orderAuditStatusSuccess,
            @Param("pageStart") int pageStart,
            @Param("pageSize") int pageSize,
            @Param("datesStart") Integer datesStart,
            @Param("datesLast") Integer datesLast,
            @Param("mchntNoLike") String mchntNoLike);
}
