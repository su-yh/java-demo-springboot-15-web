package com.eb.mp.mysql.entity.custom.dates;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 临时的一个实体类对象
 * 
 * @author suyh
 * @since 2024-09-05
 */
@Data
public class BigDecimalDatesStatisticEntity {
    private Integer dates;
    private BigDecimal statistic;
}
