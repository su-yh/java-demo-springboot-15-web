package com.eb.mq.product.dto.business;

import com.eb.constant.enums.FinancialCategoryEnums;
import lombok.Data;

/**
 * @author suyh
 * @since 2024-09-11
 */
@Data
public class SelectFinancialNoticeMqDto {
    /**
     * 代理商code
     */
    private String code;

    /**
     * 代理商手机号
     */
    private String pnum;
    /**
     * 金融账号类别： TPP/BANK
     *
     * @see FinancialCategoryEnums
     */
    private Integer financialCategory;
    /**
     * 表ID：TPP/银行卡号
     */
    private Long tableID;
}
