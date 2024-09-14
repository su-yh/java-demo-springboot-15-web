package com.eb.mp.mysql.entity.business;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eb.constant.enums.PresentStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Data
@TableName(value = "transfer_record", autoResultMap = true)
public class TransferRecordEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 流水编号
     */
    @TableField("`order`")
    @Schema(description = "流水编号")
    private String order;

    /**
     * cp 响应订单号
     */
    @TableField("cp_order")
    private String cpOrder;

    /**
     * 代理code
     */
    @TableField("code")
    private String code;

    /**
     * 接收方uid
     */
    @TableField("receiver_uid")
    private String receiverUid;

    /**
     * 发送方uid
     */
    @TableField("sender_uid")
    private String senderUid;

    /**
     * 赠品数量
     */
//    @TableField("present")
//    private BigDecimal present;

    /**
     * 状态[1-赠送中，2-赠送成功，3-赠送失败]
     */
    @TableField("status")
    private PresentStatusEnums status;

    /**
     * 创建时间
     */
    @TableField("created")
    private Date created;

    /**
     * 完成时间
     */
    @TableField("mtime")
    private Date mtime;

    /**
     * 电话号码
     */
    @TableField("pnum")
    private String pnum;

    /**
     * 项目
     */
    @TableField("pn")
    private String pn;
}
