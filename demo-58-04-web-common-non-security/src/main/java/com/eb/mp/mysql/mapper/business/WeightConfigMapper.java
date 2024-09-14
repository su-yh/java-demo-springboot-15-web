package com.eb.mp.mysql.mapper.business;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.eb.constant.DataSourceNames;
import com.eb.mp.mybatis.BaseMapperX;
import com.eb.mp.mybatis.LambdaQueryWrapperX;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.WeightConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Mapper
@DS(DataSourceNames.CDS_MYSQL)
public interface WeightConfigMapper extends BaseMapperX<WeightConfigEntity> {
    default PageResult<WeightConfigEntity> queryPage(
            PageParam pageParam, WeightConfigEntity queryEntity) {
        LambdaQueryWrapperX<WeightConfigEntity> queryWrapperX = build();

        queryWrapperX.eqIfPresent(WeightConfigEntity::getId, queryEntity.getId())
                .eqIfPresent(WeightConfigEntity::getWeightDimension, queryEntity.getWeightDimension())
                .eqIfPresent(WeightConfigEntity::getCompareStatus, queryEntity.getCompareStatus())
                .eqIfPresent(WeightConfigEntity::getScore, queryEntity.getScore())
                .eqIfPresent(WeightConfigEntity::getWeightValue, queryEntity.getWeightValue());

        return selectPage(pageParam, queryWrapperX);
    }
}
