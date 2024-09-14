package com.eb.mp.mysql.mapper.business;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.eb.business.dto.vip.req.VipInfoQueryReqDto;
import com.eb.constant.DataSourceNames;
import com.eb.mp.mybatis.BaseMapperX;
import com.eb.mp.mybatis.LambdaQueryWrapperX;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.VipInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Mapper
@DS(DataSourceNames.CDS_MYSQL)
public interface VipInfoMapper extends BaseMapperX<VipInfoEntity> {
    default LambdaQueryWrapperX<VipInfoEntity> condition(VipInfoQueryReqDto reqDto) {
        LambdaQueryWrapperX<VipInfoEntity> queryWrapperX = build();

        queryWrapperX.eqIfPresent(VipInfoEntity::getPnum, reqDto.getPnum())
                .eqIfPresent(VipInfoEntity::getCode, reqDto.getCode())
                .eqIfPresent(VipInfoEntity::getSvipAppOnline, reqDto.getSvipAppOnline())
                .likeIfPresent(VipInfoEntity::getPnum, reqDto.getPnumLike())
                .likeIfPresent(VipInfoEntity::getCode, reqDto.getCodeLike());

        queryWrapperX.geIfPresent(VipInfoEntity::getBalance, reqDto.getBalanceLowerBound())
                .leIfPresent(VipInfoEntity::getBalance, reqDto.getBalanceUpperBound());

        return queryWrapperX;
    }

    default List<VipInfoEntity> queryByConditional(VipInfoQueryReqDto reqDto) {
        LambdaQueryWrapperX<VipInfoEntity> queryWrapperX = condition(reqDto);

        return selectList(queryWrapperX);
    }

    default PageResult<VipInfoEntity> pageQueryByConditional(
            @NonNull PageParam pageParam, @NonNull VipInfoQueryReqDto reqDto) {
        LambdaQueryWrapperX<VipInfoEntity> queryWrapperX = condition(reqDto);

        boolean defaultOrderFlag = true;
        if (reqDto.getOrderBalance() != null) {
            switch (reqDto.getOrderBalance()) {
                case ascend:
                    queryWrapperX.orderByAsc(VipInfoEntity::getBalance);
                    defaultOrderFlag = false;
                    break;
                case descend:
                    queryWrapperX.orderByDesc(VipInfoEntity::getBalance);
                    defaultOrderFlag = false;
                    break;
                default:
                    break;
            }
        }

        if (reqDto.getOrderTransferBalance() != null) {
            switch (reqDto.getOrderTransferBalance()) {
                case ascend:
                    queryWrapperX.orderByAsc(VipInfoEntity::getTransferBalance);
                    defaultOrderFlag = false;
                    break;
                case descend:
                    queryWrapperX.orderByDesc(VipInfoEntity::getTransferBalance);
                    defaultOrderFlag = false;
                    break;
                default:
                    break;
            }
        }

        if (reqDto.getOrderTransferCount() != null) {
            switch (reqDto.getOrderTransferCount()) {
                case ascend:
                    queryWrapperX.orderByAsc(VipInfoEntity::getTransferCount);
                    defaultOrderFlag = false;
                    break;
                case descend:
                    queryWrapperX.orderByDesc(VipInfoEntity::getTransferCount);
                    defaultOrderFlag = false;
                    break;
                default:
                    break;
            }
        }

        // 默认排序
        if (defaultOrderFlag) {
            queryWrapperX.orderByDesc(VipInfoEntity::getSvipAppOnline)
                    .orderByDesc(VipInfoEntity::getBalance);
        }

        return selectPage(pageParam, queryWrapperX);
    }

    default VipInfoEntity queryEntityByTgUsername(@NonNull String tgUsername) {
        LambdaQueryWrapperX<VipInfoEntity> queryWrapperX = build();
        queryWrapperX.eq(VipInfoEntity::getTgUsername, tgUsername);

        return selectOne(queryWrapperX);
    }

    default VipInfoEntity queryEntityByCode(String code) {
        if (code == null) {
            return null;
        }

        LambdaQueryWrapperX<VipInfoEntity> queryWrapperX = build();
        queryWrapperX.eq(VipInfoEntity::getCode, code);

        return selectOne(queryWrapperX);
    }

    default String queryAgentPhoneByCode(@NonNull String code)
    {
        LambdaQueryWrapperX<VipInfoEntity> queryWrapperX = build();
        queryWrapperX.select(VipInfoEntity::getPnum);
        queryWrapperX.eq(VipInfoEntity::getCode, code);

        VipInfoEntity vipInfoEntity = selectOne(queryWrapperX);

        if (vipInfoEntity == null) {
            // 没有找到，返回空值
            return "";
        }

        return vipInfoEntity.getPnum();
    }
}
