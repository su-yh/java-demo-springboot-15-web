package com.eb.mq.product.dto.business;

import com.eb.constant.enums.OnlineStateEnums;
import com.eb.mq.product.dto.MqProductBaseDto;
import lombok.Data;

/**
 * @author suyh
 * @since 2024-09-07
 */
@Data
public class VipInfoSvipAppOnlineMqDto implements MqProductBaseDto {
    private Long loginUserId;
    private String loginNickName;

    private Long vipInfoId;

    /**
     * ONLINE(1), 在线
     * OFFLINE(0), 离线
     *
     * @see OnlineStateEnums
     */
    private Integer svipAppOnline;

    /**
     * 手机号
     */
    private String pnum;
}
