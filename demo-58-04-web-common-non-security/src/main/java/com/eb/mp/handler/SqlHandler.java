package com.eb.mp.handler;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
@Slf4j
public class SqlHandler implements Interceptor {
    public static final ThreadLocal<List<String>> AUDIT_SQL_LIST = new ThreadLocal<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object proceed = invocation.proceed();
        try {
            extracted(invocation);
        } catch (Exception exception) {
            log.error("SqlHandler invoke failed", exception);
        }
        return proceed;
    }

    @SuppressWarnings("unchecked")
    private void extracted(Invocation invocation) {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        try (Connection connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            BoundSql boundSql = mappedStatement.getBoundSql(args[1]);
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            Object parameterObject = args[1];
            Configuration configuration = mappedStatement.getConfiguration();

            // Create PreparedStatement object for SQL query
            try (PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql())) {
                if (CollectionUtils.isNotEmpty(parameterMappings)) {
                    for (int i = 0; i < parameterMappings.size(); i++) {
                        ParameterMapping parameterMapping = parameterMappings.get(i);
                        if (parameterMapping.getMode() != ParameterMode.OUT) {
                            String propertyName = parameterMapping.getProperty();
                            Object value;
                            if (boundSql.hasAdditionalParameter(propertyName)) {
                                value = boundSql.getAdditionalParameter(propertyName);
                            } else if (parameterObject == null) {
                                value = null;
                            } else if (configuration.getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass())) {
                                value = parameterObject;
                            } else {
                                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                                value = metaObject.getValue(propertyName);
                            }
                            TypeHandler typeHandler = parameterMapping.getTypeHandler();
                            JdbcType jdbcType = parameterMapping.getJdbcType();
                            if (value == null && jdbcType == null) {
                                jdbcType = mappedStatement.getConfiguration().getJdbcTypeForNull();
                            }
                            if (Objects.nonNull(value)) {
                                typeHandler.setParameter(preparedStatement, i + 1, value, jdbcType);
                            }
                        }
                    }
                }

                String sqlDetail = preparedStatement.toString();
                log.info("Executed SQL Database product: {}, URL: {}, sql: \n{}",
                        metaData.getDatabaseProductName(), extractHost(metaData.getURL()), sqlDetail);

                String name = invocation.getMethod().getName();
                if (name.equalsIgnoreCase("update")) {
                    List<String> sqlList = SqlHandler.AUDIT_SQL_LIST.get();
                    if (sqlList != null) {
                        sqlList.add(sqlDetail);
                    }
                }
            }
        } catch (SQLException e) {
            log.error("SQL execution error", e);
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private static String extractHost(String url) {
        // 定义主机部分匹配的正则表达式
        String hostPattern = "(?<=\\/\\/)(.*?)(?=\\?)";
        Pattern pattern = Pattern.compile(hostPattern);
        Matcher matcher = pattern.matcher(url);
        return matcher.find() ? matcher.group() : "Unknown Host";
    }
}
