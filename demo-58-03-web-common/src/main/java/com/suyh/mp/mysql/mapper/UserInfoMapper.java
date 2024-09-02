package com.suyh.mp.mysql.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.suyh.constant.DataSourceNames;
import com.suyh.mp.mybatis.BaseMapperX;
import com.suyh.mp.mybatis.LambdaQueryWrapperX;
import com.suyh.mp.mysql.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author suyh
 * @since 2024-08-31
 */
@Mapper
@DS(DataSourceNames.CDS_MYSQL)
public interface UserInfoMapper extends BaseMapperX<UserInfoEntity> {

    default UserInfoEntity selectByUni(String username) {
        LambdaQueryWrapperX<UserInfoEntity> queryWrapperX = build();
        queryWrapperX.eq(UserInfoEntity::getUsername, username);

        return selectOne(queryWrapperX);
    }
}
