package com.eb.business.dto.exceptionorder.rsp;

import com.eb.mp.mysql.entity.business.SchedulingTransferRecordEntity;
import com.eb.mp.mysql.entity.business.UtrUploadRecordEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author suyh
 * @since 2024-09-09
 */
@Data
public class SchedulingTransferRecordAuditRspDto {

    @Schema(description = "订单详情")
    private SchedulingTransferRecordEntity orderEntity;

    @Schema(description = "utr详情")
    private UtrUploadRecordEntity utrEntity;

}
