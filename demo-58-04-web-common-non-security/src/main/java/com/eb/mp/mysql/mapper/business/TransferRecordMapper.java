package com.eb.mp.mysql.mapper.business;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.eb.constant.DataSourceNames;
import com.eb.mp.mybatis.BaseMapperX;
import com.eb.mp.mybatis.LambdaQueryWrapperX;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.TransferRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Mapper
@DS(DataSourceNames.CDS_MYSQL)
public interface TransferRecordMapper extends BaseMapperX<TransferRecordEntity> {
    default PageResult<TransferRecordEntity> listPageByUidLike(
            PageParam pageParam, @Nullable String uidLike) {
        LambdaQueryWrapperX<TransferRecordEntity> queryWrapperX = build();
        if (uidLike != null) {
            queryWrapperX.or(w -> w.like(TransferRecordEntity::getReceiverUid, uidLike));
            queryWrapperX.or(w -> w.like(TransferRecordEntity::getSenderUid, uidLike));
        }

        return selectPage(pageParam, queryWrapperX);
    }
}
