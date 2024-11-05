package com.eb.bytecodes;

import javassist.ClassPool;
import javassist.LoaderClassPath;

/**
 * @author suyh
 * @since 2024-10-19
 */
public class BizClassPoll {
    static {
        init();
    }

    public static ClassPool getDefault() {
        return ClassPool.getDefault();
    }

    private static void init() {
        ClassPool classPool = ClassPool.getDefault();

        /*
         * Caused by: java.lang.RuntimeException: javassist.NotFoundException: com.zaxxer.hikari.pool.ProxyStatement
         * 	at com.ebusiness.bytecodes.MybatisSqlDetailText.rebuildSqlDetail(MybatisSqlDetailText.java:21)
         * 	at com.ebusiness.CommunityEbusinessManager.main(CommunityEbusinessManager.java:16)
         * 	... 8 more
         * Caused by: javassist.NotFoundException: com.zaxxer.hikari.pool.ProxyStatement
         *
         * 在使用spring boot 插件打包之后，在线上运行时会有如上异常出现。可以将如下ClassLoader 添加进去就可以了。
         * 但是使用 maven-assembly-plugin  打包却没有这个问题
         */
        classPool.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
        importPackages(classPool);
    }

    private static void importPackages(ClassPool classPool) {
        classPool.importPackage("com.mysql.cj.PreparedQuery");
    }
}
