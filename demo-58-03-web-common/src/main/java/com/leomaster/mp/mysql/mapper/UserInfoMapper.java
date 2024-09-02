package com.leomaster.mp.mysql.mapper;

import com.leomaster.mp.mybatis.BaseMapperX;
import com.leomaster.mp.mybatis.LambdaQueryWrapperX;
import com.leomaster.mp.mysql.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author suyh
 * @since 2024-08-31
 */
@Mapper
public interface UserInfoMapper extends BaseMapperX<UserInfoEntity> {

    default UserInfoEntity selectByUni(String username) {
        LambdaQueryWrapperX<UserInfoEntity> queryWrapperX = build();
        queryWrapperX.eq(UserInfoEntity::getUsername, username);

        return selectOne(queryWrapperX);
    }
}
