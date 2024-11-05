package com.eb.constant.enums;

import lombok.Getter;

/**
 * @author suyh
 * @since 2024-10-19
 */
@Getter
public enum AuditEnums {
    SYSTEM_USER_CREATE("System Manage/User Manage", "新建用户"),
    SYSTEM_USER_EDIT("System Manage/User Manage", "编辑用户"),
    SYSTEM_USER_DELETE("System Manage/User Manage", "删除用户"),
    ;

    private final String page;
    private final String operation;

    AuditEnums(String page, String operation) {
        this.page = page;
        this.operation = operation;
    }
}
