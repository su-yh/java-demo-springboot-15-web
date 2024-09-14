package com.eb.business.dto;

import com.eb.util.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

/**
 * 有日期范围查询的抽象基类
 *
 * @author suyh
 * @since 2024-09-05
 */
@Data
public class DatesRangeQueryReqDto {
    @Schema(description = "开始日期，格式：yyyyMMdd")
    private Integer datesStart;

    @Schema(description = "结束日期，格式：yyyyMMdd")
    private Integer datesLast;

    /**
     * @param offsetStart 开始日期：基于今天偏移的天数
     * @param offsetLast  结束日期：基于今天偏移的天数
     */
    public static void defaultDatesRange(
            @NonNull DatesRangeQueryReqDto queryDto, int offsetStart, int offsetLast) {
        Integer datesStart = queryDto.getDatesStart();
        Integer datesLast = queryDto.getDatesLast();
        if (datesStart != null && datesLast != null) {
            return;
        }

        LocalDate localDate = LocalDate.now();
        int dates = DateUtils.convertToInteger(localDate);
        datesStart = DateUtils.plusDays(dates, offsetStart);
        datesLast = DateUtils.plusDays(dates, offsetLast);

        queryDto.setDatesStart(datesStart).setDatesLast(datesLast);
    }
}
