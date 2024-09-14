package com.eb.mp.mysql.mapper.business;

import com.eb.mp.mybatis.BaseMapperX;
import com.eb.mp.mybatis.LambdaQueryWrapperX;
import com.eb.mp.mysql.entity.business.UtrUploadRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author suyh
 * @since 2024-09-09
 */
@Mapper
public interface UtrUploadRecordMapper extends BaseMapperX<UtrUploadRecordEntity> {

    /**
     * 通过cp order 查询出一条记录。
     */
    default UtrUploadRecordEntity queryEntityByCpOrder(String cpOrder) {
        if (cpOrder == null) {
            return null;
        }

        LambdaQueryWrapperX<UtrUploadRecordEntity> queryWrapperX = build();

        queryWrapperX.eq(UtrUploadRecordEntity::getCpOrder, cpOrder);

        queryWrapperX.orderByDesc(UtrUploadRecordEntity::getCreated)
                .orderByDesc(UtrUploadRecordEntity::getId);
        // FIXME: suyh - 乔哥说了，这里按只能匹配到一条数据(limit 1)来做，后面有确定的处理方式再来决定
        queryWrapperX.last("limit 1");
        return selectOne(queryWrapperX);
    }

    /**
     * 通过修正cp order 查询出一条记录。
     */
    default UtrUploadRecordEntity queryEntityByCpOrderRevise(String cpOrderRevise) {
        if (cpOrderRevise == null) {
            return null;
        }

        LambdaQueryWrapperX<UtrUploadRecordEntity> queryWrapperX = build();

        queryWrapperX.eq(UtrUploadRecordEntity::getCpOrderRevise, cpOrderRevise);

        queryWrapperX.orderByDesc(UtrUploadRecordEntity::getCreated)
                .orderByDesc(UtrUploadRecordEntity::getId);
        // FIXME: suyh - 乔哥说了，这里按只能匹配到一条数据(limit 1)来做，后面有确定的处理方式再来决定
        queryWrapperX.last("limit 1");
        return selectOne(queryWrapperX);
    }

    List<UtrUploadRecordEntity> queryListByAuditOrder(@Param("order") String order);
}
