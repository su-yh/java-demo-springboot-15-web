package com.suyh.util;

import java.time.LocalDate;

/**
 * @author suyh
 * @since 2024-08-28
 */
public final class DateUtils {
    /**
     * @param sourceDate 满足yyyyMMdd 格式的日期整数
     * @param daysToAdd  增加的天数，可以为负值。
     * @return yyyyMMdd 格式的日期结果
     */
    public static int plusDays(int sourceDate, int daysToAdd) {
        LocalDate source = convertToLocalDate(sourceDate);
        LocalDate localDate = source.plusDays(daysToAdd);

        return localDate.getYear() * 10000 + localDate.getMonthValue() * 100 + localDate.getDayOfMonth();
    }

    // 日期格式：yyyyMMdd
    public static LocalDate convertToLocalDate(int dateValue) {
        int year = dateValue / 10000;
        int month = dateValue % 10000 / 100;
        int day = dateValue % 100;

        return LocalDate.of(year, month, day);
    }

    public static Integer convertToInteger(LocalDate localDate) {
        int year = localDate.getYear();
        int monthValue = localDate.getMonthValue();
        int dayOfMonth = localDate.getDayOfMonth();

        return year * 10000 + monthValue * 100 + dayOfMonth;
    }

}
