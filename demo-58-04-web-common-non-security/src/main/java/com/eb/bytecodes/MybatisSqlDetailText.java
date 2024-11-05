package com.eb.bytecodes;

import com.mysql.cj.jdbc.ClientPreparedStatement;
import com.zaxxer.hikari.pool.ProxyStatement;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * @author suyh
 * @since 2024-10-19
 */
public class MybatisSqlDetailText {
    public static void rebuildSqlDetail() {
        try {
            rebuildToStringForProxyStatement();
            rebuildToStringForClientPreparedStatement();
        } catch (NotFoundException | CannotCompileException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改{@link ProxyStatement#toString()} 方法，只保留SQL 信息部分
     */
    private static void rebuildToStringForProxyStatement()
            throws NotFoundException, CannotCompileException {
        ClassPool classPool = BizClassPoll.getDefault();
        CtClass ctClass = classPool.get("com.zaxxer.hikari.pool.ProxyStatement");

        CtMethod ctMethod = ctClass.getDeclaredMethod("toString");

        String body = "    {\n" +
                "        return delegate.toString();\n" +
                "    }\n";
        ctMethod.setBody(body);

        ctClass.toClass();
    }

    /**
     * 修改{@link ClientPreparedStatement#toString()} 方法，只保留SQL 信息部分
     */
    private static void rebuildToStringForClientPreparedStatement()
            throws NotFoundException, CannotCompileException {
        ClassPool classPool = BizClassPoll.getDefault();
        CtClass ctClass = classPool.get("com.mysql.cj.jdbc.ClientPreparedStatement");

        CtMethod ctMethod = ctClass.getDeclaredMethod("toString");

        String body = "    {\n" +
                "        return ((PreparedQuery) this.query).asSql();\n" +
                "    }\n";
        ctMethod.setBody(body);

        ctClass.toClass();
    }
}
