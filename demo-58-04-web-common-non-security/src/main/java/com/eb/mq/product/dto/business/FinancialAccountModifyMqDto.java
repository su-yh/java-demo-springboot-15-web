package com.eb.mq.product.dto.business;

import com.eb.constant.enums.FinancialCategoryEnums;
import com.eb.mq.product.dto.MqProductBaseDto;
import com.eb.mq.product.dto.enums.OptActionEnums;
import lombok.Data;

/**
 * @author suyh
 * @since 2024-09-05
 */
@Data
public class FinancialAccountModifyMqDto implements MqProductBaseDto {
    /**
     * CREATE(1),
     * UPDATE(2),
     *
     * @see OptActionEnums
     */
    private final int optCategory;

    private Long loginUserId;
    private String loginNickName;
    /**
     * 金融账号类别： TPP/BANK
     *
     * @see FinancialCategoryEnums
     */
    private Integer financialCategory;
    /**
     * 金融账号：TPP/银行卡号
     */
    private String financialAccount;
    /**
     * 银行账号
     */
    private String bankAccountName;
    /**
     * 银行卡号时：swiftCode
     */
    private String swiftCode;

    /**
     * vip_info 表中的手机号，表中没有限制必须，所以有可能 为null
     */
    private String pnum;
}
