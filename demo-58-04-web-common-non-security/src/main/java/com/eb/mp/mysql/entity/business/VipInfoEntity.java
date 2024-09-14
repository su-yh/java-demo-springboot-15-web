package com.eb.mp.mysql.entity.business;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eb.constant.enums.SvipAuditStatusEnums;
import com.eb.constant.enums.DaysOfWeekEnums;
import com.eb.constant.enums.EnableStatusEnums;
import com.eb.constant.enums.HourOfDayEnums;
import com.eb.constant.enums.OnlineStateEnums;
import com.eb.constant.enums.ShowStatusEnums;
import com.eb.constant.enums.VipTypeEnums;
import com.eb.mp.typehandler.enlist.DaysOfWeekListTypeHandler;
import com.eb.mp.typehandler.enlist.HourOfDayListTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Data
@TableName(value = "vip_info", autoResultMap = true)
public class VipInfoEntity {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 代理code
     */
    @TableField("code")
    private String code;

    /**
     * 买家uid
     */
    @TableField("uid")
    private String uid;

    /**
     * 排名
     */
    @TableField("ranking")
    private Integer ranking;

    /**
     * 顺序
     */
    @TableField("sequence")
    private Integer sequence;

    /**
     * 是否启用[0:不启用，1:启用]
     */
    @TableField("enable")
    private EnableStatusEnums enable;

    /**
     * 列表是否展示[0:不展示，1:展示]
     */
    @TableField("`show`")
    private ShowStatusEnums show;

    /**
     * 评价
     */
    @TableField("evaluate")
    private Integer evaluate;

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

    /**
     * 余额
     */
    @TableField("balance")
    private BigDecimal balance;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 总销售额
     */
    @TableField("transfer_balance")
    private BigDecimal transferBalance;

    /**
     * 发货订单数
     */
    @TableField("transfer_count")
    private Integer transferCount;

    /**
     * 联系方式
     */
    @TableField("contact_details")
    @Schema(description = "联系方式")
    private String contactDetails;

    /**
     * 备注
     */
    @TableField("remark")
    private Integer remark;

    /**
     * 创建时间
     */
    @TableField("created")
    private Date created;

    /**
     * 创建人
     */
    @TableField("created_by")
    private String createdBy;

    /**
     * 初始余额
     */
    @TableField("init_transfer_balance")
    private BigDecimal initTransferBalance;

    /**
     * 初始订单数
     */
    @TableField("init_transfer_count")
    private Integer initTransferCount;

    /**
     * 是否在线时间[0:不在线，1:在线]
     */
    @TableField("online")
    private OnlineStateEnums online;

    /**
     * 工作日，周一到周日，用[,]分隔
     */
    @TableField(value = "online_day_of_week", jdbcType = JdbcType.VARCHAR, typeHandler = DaysOfWeekListTypeHandler.class)
    private List<DaysOfWeekEnums> onlineDayOfWeek;

    /**
     * 每日在线时段，0点-23点，用[,]分隔
     */
    @TableField(value = "online_hour_of_day", typeHandler = HourOfDayListTypeHandler.class)
    private List<HourOfDayEnums> onlineHourOfDay;

//    /**
//     * whatsapp账号
//     */
//    @TableField("whats_app")
//    private String whatsApp;

    /**
     * 是否审核通过 ( 0 未提交审核 | 1 审核中 | 2 审核通过 | 3 审核失败 | 4 已注销冻结）
     */
    @TableField("svip_audit_status")
    private SvipAuditStatusEnums svipAuditStatus;

    /**
     * svip系统上下线状态[0: 下线 ｜ 1: 上线]
     */
    @TableField("svip_system_online")
    private OnlineStateEnums svipSystemOnline;

    /**
     * svip代理自身上下线[0: 下线 ｜ 1: 上线]
     */
    @TableField("svip_app_online")
    private OnlineStateEnums svipAppOnline;

    /**
     * 代理类型（1自营，2代理）
     */
    @TableField("svip_vip_type")
    private VipTypeEnums svipVipType;

    /**
     * tg用户名
     */
    @TableField("tg_username")
    private String tgUsername;

    /**
     * tg chatID
     */
    @TableField("tg_chatID")
    private Long tgChatId;

    @TableField("email")
    private String email;
}
