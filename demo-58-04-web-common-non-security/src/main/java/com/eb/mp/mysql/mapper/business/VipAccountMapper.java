package com.eb.mp.mysql.mapper.business;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.eb.constant.DataSourceNames;
import com.eb.mp.mybatis.BaseMapperX;
import com.eb.mp.mysql.entity.business.VipAccountEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Mapper
@DS(DataSourceNames.CDS_MYSQL)
public interface VipAccountMapper extends BaseMapperX<VipAccountEntity> {
}
