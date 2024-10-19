package com.eb.constant;

/**
 * @author suyh
 * @since 2023-11-26
 */
public final class ErrorCodeConstants {
    public static final String ERROR_CODE_PREFIX = "error.code";

    public static final int REFUSE_MODIFY_ADMIN_USER = 1013001;
    public static final int REFUSE_MODIFY_ADMIN_ROLE = 1013002;
    public static final int CANNOT_DELETE_ROLE = 1013003;
    public static final int CANNOT_DELETE_DICT_TYPE = 1013004;

    // ruoyi SystemUser
    public static final int RUOYI_SYSTEM_USER_CREATE_USERNAME_EXISTS = 1013101;
    public static final int RUOYI_SYSTEM_USER_CREATE_PHONE_NUMBER_EXISTS = 1013102;
    public static final int RUOYI_SYSTEM_USER_CREATE_EMAIL_EXISTS = 1013103;
    public static final int RUOYI_SYSTEM_USER_EDIT_USERNAME_EXISTS = 1013104;
    public static final int RUOYI_SYSTEM_USER_EDIT_PHONE_NUMBER_EXISTS = 1013105;
    public static final int RUOYI_SYSTEM_USER_EDIT_EMAIL_EXISTS = 1013106;
    public static final int RUOYI_SYSTEM_USER_DELETE_FAILED = 1013107;

    // ruoyi SystemRole
    public static final int RUOYI_SYSTEM_ROLE_CREATE_FAILED_NAME_EXISTS = 1013201;
    public static final int RUOYI_SYSTEM_ROLE_CREATE_FAILED_KEY_EXISTS = 1013202;
    public static final int RUOYI_SYSTEM_ROLE_EDIT_FAILED_NAME_EXISTS = 1013203;
    public static final int RUOYI_SYSTEM_ROLE_EDIT_FAILED_KEY_EXISTS = 1013204;
    public static final int RUOYI_SYSTEM_ROLE_EDIT_FAILED = 1013205;

    // ruoyi SystemMenu
    public static final int RUOYI_SYSTEM_MENU_CREATE_FAILED_NAME_EXISTS = 1013301;
    public static final int RUOYI_SYSTEM_MENU_CREATE_FAILED_PATH_ERROR = 1013302;
    public static final int RUOYI_SYSTEM_MENU_EDIT_FAILED_NAME_EXISTS = 1013303;
    public static final int RUOYI_SYSTEM_MENU_EDIT_FAILED_PATH_ERROR = 1013304;
    public static final int RUOYI_SYSTEM_MENU_EDIT_FAILED_PARENT_ERROR = 1013305;
    public static final int RUOYI_SYSTEM_MENU_DELETE_FAILED_CHILD_ERROR = 1013306;
    public static final int RUOYI_SYSTEM_MENU_DELETE_FAILED_ASSIGNED_ERROR = 1013307;
    public static final int RUOYI_SYSTEM_MENU_CREATE_FAILED_KEY_ERROR = 1013308;
    public static final int RUOYI_SYSTEM_MENU_EDIT_FAILED_KEY_EXISTS = 1013309;



    public static final int NO_IMPLEMENT = 1014001;
    public static final int ACCESS_DENIED = 1014403;


    public static final int SERVICE_ERROR = 1015000;
    public static final int PARAMETER_ERROR = 1015001;
    public static final int PARAMETER_ERROR_PARAM = 1015002;
    public static final int USERNAME_NOT_EXISTS = 1015003;
    public static final int USER_PASSWORD_NOT_MATCH = 1015004;
    public static final int USER_NOT_LOGIN = 1015005;
    public static final int TOKEN_ERROR_OR_EXPIRE = 1015006;
    public static final int USER_EXISTS = 1015007;
    public static final int USER_BAD_CREDENTIALS = 1015008;
    public static final int USER_LOGIN_CAPTCHA_ERROR = 1015009;
    public static final int USER_NOT_EXISTS = 1015010;
    public static final int KEY_ID_LOST = 1015011;
    public static final int KEY_MUST_NULL = 1015012;
    public static final int TPP_EXISTS = 1015013;
    public static final int BANK_NO_EXISTS = 1015014;
    // 某条记录不存在
    public static final int RECORD_NOT_EXISTS = 1015015;
    public static final int UTR_UPLOAD_RECORD_NOT_EXISTS = 1015016;
    // 使用修正cp订单号匹配失败，请检查数据
    public static final int SCHEDULING_TRANSFER_RECORD_CP_ORDER_REVISE_NOT_MATCH = 1015017;
    // 此订单已完成，无需处理
    public static final int SCHEDULING_TRANSFER_RECORD_STATUS_SUCCESS = 1015018;
    public static final int SCHEDULING_TRANSFER_RECORD_STATUS_MATCH = 1015019;
    public static final int SCHEDULING_TRANSFER_RECORD_ORDER_AUDIT_ERROR = 1015020;
    public static final int SCHEDULING_TRANSFER_RECORD_CP_ORDER_EXISTS = 1015021;
    public static final int VIP_INFO_UPDATE_FAILED_TG_USERNAME_EXISTS = 1015022;
    public static final int USER_OLD_PASSWORD_NOT_MATCH = 1015023;

    public static final int WEIGHT_CONFIG_ID_NOT_EXISTS = 1016001;

    public static final int FILE_NOT_EXISTS = 1017001;


}
