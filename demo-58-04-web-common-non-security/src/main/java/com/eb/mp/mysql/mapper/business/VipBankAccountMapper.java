package com.eb.mp.mysql.mapper.business;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.eb.constant.DataSourceNames;
import com.eb.constant.enums.EnableStatusEnums;
import com.eb.mp.mybatis.BaseMapperX;
import com.eb.mp.mybatis.LambdaQueryWrapperX;
import com.eb.mp.mysql.entity.business.VipBankAccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.NonNull;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Mapper
@DS(DataSourceNames.CDS_MYSQL)
public interface VipBankAccountMapper extends BaseMapperX<VipBankAccountEntity> {
    default VipBankAccountEntity queryEntityByCardNo(@NonNull String cardNo) {
        LambdaQueryWrapperX<VipBankAccountEntity> queryWrapperX = build();
        queryWrapperX.eq(VipBankAccountEntity::getCardNo, cardNo);

        return selectOne(queryWrapperX);
    }

    default VipBankAccountEntity queryEntityByCodeAndEnable(@NonNull String code) {
        LambdaQueryWrapperX<VipBankAccountEntity> queryWrapperX = build();
        queryWrapperX.eq(VipBankAccountEntity::getCode, code)
                .eq(VipBankAccountEntity::getEnable, EnableStatusEnums.ENABLE);

        // 表中没有限制，只能在这里限制一下。
        queryWrapperX.last("limit 1");

        return selectOne(queryWrapperX);
    }

    default void disableByCode(String code) {
        LambdaQueryWrapperX<VipBankAccountEntity> queryWrapperX = build();
        queryWrapperX.eq(VipBankAccountEntity::getCode, code);

        VipBankAccountEntity entity = new VipBankAccountEntity();
        entity.setEnable(EnableStatusEnums.DISABLE);
        update(entity, queryWrapperX);
    }


}
