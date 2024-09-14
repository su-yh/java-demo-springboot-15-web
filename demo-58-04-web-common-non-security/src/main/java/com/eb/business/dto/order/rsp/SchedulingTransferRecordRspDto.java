package com.eb.business.dto.order.rsp;

import com.eb.mp.mysql.entity.business.SchedulingTransferRecordEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author suyh
 * @since 2024-09-04
 */
@Data
public class SchedulingTransferRecordRspDto {
    @Schema(description = "订单详情")
    private SchedulingTransferRecordEntity entity;


}
