package com.leomaster.mp.mapper.mysql;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.leomaster.constant.DataSourceNames;
import com.leomaster.mp.entity.mysql.TbRechargeEntity;
import com.leomaster.mp.mybatis.BaseMapperX;
import com.leomaster.mp.mybatis.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
@DS(DataSourceNames.CDS_MYSQL)
public interface TbRechargeMapper extends BaseMapperX<TbRechargeEntity> {

    default List<TbRechargeEntity> queryListByConditional(int date, List<String> channelList) {
        LambdaQueryWrapperX<TbRechargeEntity> queryWrapperX = build();
        queryWrapperX.eq(TbRechargeEntity::getDay, date)
                .inIfPresent(TbRechargeEntity::getChannel, channelList);

        return selectList(queryWrapperX);
    }

    // final String sqlFormat3 = "select `day` , channel from %s where uid = '%s' and channel = '%s' and day<= '%d' order by `day`  limit 1";
    // 首充
    default TbRechargeEntity queryNewRecharge(String uid, String channel, Integer maxDay) {
        LambdaQueryWrapperX<TbRechargeEntity> queryWrapperX = build();
        queryWrapperX.eq(TbRechargeEntity::getUid, uid)
                .eq(TbRechargeEntity::getChannel, channel)
                .le(TbRechargeEntity::getDay, maxDay);

        queryWrapperX.orderByAsc(TbRechargeEntity::getDay);
        queryWrapperX.last("limit 1");

        return selectOne(queryWrapperX);
    }
}
