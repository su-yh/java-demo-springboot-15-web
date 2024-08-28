package com.leomaster.mp.mapper.mysql;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.leomaster.constant.DataSourceNames;
import com.leomaster.mp.entity.mysql.TbWithdrawalEntity;
import com.leomaster.mp.mybatis.BaseMapperX;
import com.leomaster.mp.mybatis.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author suyh
 * @since 2024-04-25
 */
@Mapper
@DS(DataSourceNames.CDS_MYSQL)
public interface TbWithdrawalMapper extends BaseMapperX<TbWithdrawalEntity> {

    default List<TbWithdrawalEntity> queryListByConditional(int date, List<String> channelList) {
        LambdaQueryWrapperX<TbWithdrawalEntity> queryWrapperX = build();
        queryWrapperX.eq(TbWithdrawalEntity::getDay, date)
                .inIfPresent(TbWithdrawalEntity::getChannel, channelList);

        return selectList(queryWrapperX);
    }
}
