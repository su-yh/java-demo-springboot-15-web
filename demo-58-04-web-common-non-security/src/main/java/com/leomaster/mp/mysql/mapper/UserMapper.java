package com.leomaster.mp.mysql.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.leomaster.constant.DataSourceNames;
import com.leomaster.mp.mybatis.BaseMapperX;
import com.leomaster.mp.mybatis.LambdaQueryWrapperX;
import com.leomaster.mp.mysql.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author suyh
 * @since 2024-08-31
 */
@Mapper
@DS(DataSourceNames.CDS_MYSQL)
public interface UserMapper extends BaseMapperX<UserEntity> {

    default UserEntity selectByUni(String username) {
        LambdaQueryWrapperX<UserEntity> queryWrapperX = build();
        queryWrapperX.eq(UserEntity::getUsername, username);

        return selectOne(queryWrapperX);
    }
}
