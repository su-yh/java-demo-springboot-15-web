package com.eb.bytecodes;

import javassist.ClassPool;

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
        importPackages(classPool);
    }

    private static void importPackages(ClassPool classPool) {
        classPool.importPackage("com.mysql.cj.PreparedQuery");
    }
}
