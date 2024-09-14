package com.eb.rouyi.util;

import com.eb.constant.ErrorCodeConstants;
import com.eb.mvc.exception.ExceptionUtil;

/**
 * sql操作工具类
 * 
 * @author ruoyi
 */
public class SqlUtil
{
    /**
     * 定义常用的 sql关键字
     */
    public static String SQL_REGEX = "and |extractvalue|updatexml|sleep|exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |or |union |like |+|/*|user()";

    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * 限制orderBy最大长度
     */
    private static final int ORDER_BY_MAX_LENGTH = 500;

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value)
    {
        if (RuoyiStringUtils.isNotEmpty(value) && !isValidOrderBySql(value))
        {
//            throw new UtilException("参数不符合规范，不能进行查询");
            throw ExceptionUtil.business(ErrorCodeConstants.PARAMETER_ERROR);
        }
        if (RuoyiStringUtils.length(value) > ORDER_BY_MAX_LENGTH)
        {
//            throw new UtilException("参数已超过最大限制，不能进行查询");
            throw ExceptionUtil.business(ErrorCodeConstants.PARAMETER_ERROR);
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value)
    {
        return value.matches(SQL_PATTERN);
    }

    /**
     * SQL关键字检查
     */
    public static void filterKeyword(String value)
    {
        if (RuoyiStringUtils.isEmpty(value))
        {
            return;
        }
        String[] sqlKeywords = RuoyiStringUtils.split(SQL_REGEX, "\\|");
        for (String sqlKeyword : sqlKeywords)
        {
            if (RuoyiStringUtils.indexOfIgnoreCase(value, sqlKeyword) > -1)
            {
//                throw new UtilException("参数存在SQL注入风险");
                throw ExceptionUtil.business(ErrorCodeConstants.PARAMETER_ERROR);
            }
        }
    }
}
