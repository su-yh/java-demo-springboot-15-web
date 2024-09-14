package com.eb.mp.mysql.mapper.business;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.eb.constant.DataSourceNames;
import com.eb.constant.enums.EnableStatusEnums;
import com.eb.mp.mybatis.BaseMapperX;
import com.eb.mp.mybatis.LambdaQueryWrapperX;
import com.eb.mp.mysql.entity.business.VipTppEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.NonNull;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Mapper
@DS(DataSourceNames.CDS_MYSQL)
public interface VipTppMapper extends BaseMapperX<VipTppEntity> {
    default VipTppEntity queryByTpp(@NonNull String tpp) {
        LambdaQueryWrapperX<VipTppEntity> queryWrapperX = build();
        queryWrapperX.eq(VipTppEntity::getTpp, tpp);
        return selectOne(queryWrapperX);
    }

    default VipTppEntity queryEntityByCodeAndEnable(@NonNull String code) {
        LambdaQueryWrapperX<VipTppEntity> queryWrapperX = build();
        queryWrapperX.eq(VipTppEntity::getCode, code)
                .eq(VipTppEntity::getEnable, EnableStatusEnums.ENABLE);

        // 表中没有限制，只能在这里限制一下。
        queryWrapperX.last("limit 1");

        return selectOne(queryWrapperX);
    }

    default void disableByCode(String code) {
        LambdaQueryWrapperX<VipTppEntity> queryWrapperX = build();
        queryWrapperX.eq(VipTppEntity::getCode, code);

        VipTppEntity entity = new VipTppEntity();
        entity.setEnable(EnableStatusEnums.DISABLE);
        update(entity, queryWrapperX);
    }
}
