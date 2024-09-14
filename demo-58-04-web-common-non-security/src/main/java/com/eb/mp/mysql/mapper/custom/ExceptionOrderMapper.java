package com.eb.mp.mysql.mapper.custom;

import com.eb.business.dto.exceptionorder.req.ExceptionOrderQueryReqDto;
import com.eb.mp.mysql.entity.custom.ExceptionOrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author suyh
 * @since 2024-09-09
 */
@Mapper
public interface ExceptionOrderMapper {

    long listPageUtrRecordCount(@Param("queryReqDto") ExceptionOrderQueryReqDto queryReqDto);

    List<ExceptionOrderEntity> listPageUtrRecord(
            @Param("pageStart") int pageStart,
            @Param("pageSize") Integer pageSize,
            @Param("queryReqDto") ExceptionOrderQueryReqDto queryReqDto);

    List<ExceptionOrderEntity> listUtrRecord(
            @Param("queryReqDto") ExceptionOrderQueryReqDto queryReqDto);
}
