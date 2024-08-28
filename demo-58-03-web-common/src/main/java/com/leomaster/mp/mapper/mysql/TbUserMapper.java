package com.leomaster.mp.mapper.mysql;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.leomaster.constant.DataSourceNames;
import com.leomaster.mp.entity.mysql.TbUserEntity;
import com.leomaster.mp.mybatis.BaseMapperX;
import com.leomaster.mp.mybatis.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
@DS(DataSourceNames.CDS_MYSQL)
public interface TbUserMapper extends BaseMapperX<TbUserEntity> {
    default List<TbUserEntity> queryListByConditional(int date, List<String> channelList) {
        LambdaQueryWrapperX<TbUserEntity> queryWrapperX = build();
        queryWrapperX.eq(TbUserEntity::getDay, date).inIfPresent(TbUserEntity::getChannel, channelList);

        return selectList(queryWrapperX);
    }
}
