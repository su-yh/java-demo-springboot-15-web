package com.eb.mp.mysql.mapper.custom;

import com.eb.business.dto.vip.req.VipAccountQueryReqDto;
import com.eb.business.dto.vip.rsp.VipAccountRspDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author suyh
 * @since 2024-09-04
 */
@Mapper
public interface VipMapper {
    long pageQueryByConditionalCount(
            @Param("queryDto") VipAccountQueryReqDto queryDto);

    List<VipAccountRspDto> pageQueryByConditional(
            @Param("pageStart") int pageStart,
            @Param("pageSize") int pageSize,
            @Param("queryDto") VipAccountQueryReqDto queryDto);

    List<VipAccountRspDto> listQueryByConditional(
            @Param("queryDto") VipAccountQueryReqDto queryDto);
}
