package com.eb.rouyi.excel.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Locale;

/**
 * Excel数据格式处理适配器
 * 
 * @author ruoyi
 */
public interface RuoyiExcelHandlerAdapter
{
    /**
     * 格式化
     * 
     * @param value 单元格数据值
     * @param locale 国际化
     * @param args excel注解args参数组
     * @param cell 单元格对象
     * @param wb 工作簿对象
     *
     * @return 处理后的值
     */
    Object format(Object value, Locale locale, String[] args, Cell cell, Workbook wb);
}
