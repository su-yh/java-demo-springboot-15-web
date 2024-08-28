package com.leomaster.mp.mapper.mysql;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.leomaster.constant.DataSourceNames;
import com.leomaster.mp.entity.mysql.TbUserLoginEntity;
import com.leomaster.mp.mybatis.BaseMapperX;
import com.leomaster.mp.mybatis.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
@DS(DataSourceNames.CDS_MYSQL)
public interface TbUserLoginMapper extends BaseMapperX<TbUserLoginEntity> {
    default List<TbUserLoginEntity> queryListByConditional(int date, List<String> channelList) {
        LambdaQueryWrapperX<TbUserLoginEntity> queryWrapperX = build();
        queryWrapperX.eq(TbUserLoginEntity::getDay, date)
                .inIfPresent(TbUserLoginEntity::getChannel, channelList);

        return selectList(queryWrapperX);
    }
}
