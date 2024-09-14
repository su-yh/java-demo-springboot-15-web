package com.eb.mp.mysql.mapper.business;

import com.eb.business.dto.sms.req.SmartSmsQueryReqDto;
import com.eb.business.dto.sms.req.SmartSmsStatisticQueryReqDto;
import com.eb.business.dto.sms.rsp.SmartSmsStatisticRspDto;
import com.eb.mp.mybatis.BaseMapperX;
import com.eb.mp.mybatis.LambdaQueryWrapperX;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.SmartSmsRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author suyh
 * @since 2024-09-07
 */
@Mapper
public interface SmartSmsRecordMapper extends BaseMapperX<SmartSmsRecordEntity> {

    default PageResult<SmartSmsRecordEntity> listPage(
            PageParam pageParam, @NonNull SmartSmsQueryReqDto queryReqDto) {
        LambdaQueryWrapperX<SmartSmsRecordEntity> queryWrapperX = build();
        queryWrapperX.eqIfPresent(SmartSmsRecordEntity::getDates, queryReqDto.getDates())
                .likeIfPresent(SmartSmsRecordEntity::getPnum, queryReqDto.getPnumLike())
                .likeIfPresent(SmartSmsRecordEntity::getUtr, queryReqDto.getUtrLike())
                .likeIfPresent(SmartSmsRecordEntity::getCardNo, queryReqDto.getCardNoLike())
                .geIfPresent(SmartSmsRecordEntity::getAmount, queryReqDto.getAmountLower())
                .leIfPresent(SmartSmsRecordEntity::getAmount, queryReqDto.getAmountUpper());

        queryWrapperX.orderByDesc(SmartSmsRecordEntity::getCreated);
        queryWrapperX.orderByAsc(SmartSmsRecordEntity::getId);

        return selectPage(pageParam, queryWrapperX);
    }

    long listPageStatisticCount(
            @Param("queryReqDto") @NonNull SmartSmsStatisticQueryReqDto queryReqDto);

    List<SmartSmsStatisticRspDto> listPageStatistic(
            @Param("pageStart") int pageStart,
            @Param("pageSize") int pageSize,
            @Param("queryReqDto") @NonNull SmartSmsStatisticQueryReqDto queryReqDto);
}
