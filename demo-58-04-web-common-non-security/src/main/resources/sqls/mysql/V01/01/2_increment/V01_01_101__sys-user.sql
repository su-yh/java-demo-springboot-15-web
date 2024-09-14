


ALTER TABLE sys_user
    ADD COLUMN dept_id bigint NULL COMMENT '部门ID';
ALTER TABLE sys_user
    ADD COLUMN user_type varchar(2) default '00' COMMENT '用户类型（00系统用户 01注册用户）';
ALTER TABLE sys_user
    ADD COLUMN email varchar(50) default '' COMMENT '用户邮箱';
ALTER TABLE sys_user
    ADD COLUMN phonenumber varchar(20) default '' COMMENT '手机号码';
ALTER TABLE sys_user
    ADD COLUMN sex char default '0' COMMENT '用户性别（0男 1女 2未知）';
ALTER TABLE sys_user
    ADD COLUMN avatar varchar(100) default '' COMMENT '头像路径';
ALTER TABLE sys_user
    ADD COLUMN status char default '0' COMMENT '帐号状态（0正常 1停用）';
ALTER TABLE sys_user
    ADD COLUMN del_flag char default '0' COMMENT '删除标志（0代表存在 2代表删除）';
ALTER TABLE sys_user
    ADD COLUMN login_ip varchar(128)    default '' COMMENT '最后登录IP';
ALTER TABLE sys_user
    ADD COLUMN login_date datetime    COMMENT '最后登录时间';
ALTER TABLE sys_user
    ADD COLUMN pwd_update_date datetime    COMMENT '密码最后更新时间';
ALTER TABLE sys_user
    ADD COLUMN create_by varchar(64)    default '' COMMENT '创建者';
ALTER TABLE sys_user
    ADD COLUMN update_by varchar(64)    default '' COMMENT '更新者';
ALTER TABLE sys_user
    ADD COLUMN remark varchar(500)    default '' COMMENT '备注';

ALTER TABLE sys_user DROP INDEX uni_name;
ALTER TABLE sys_user ADD INDEX idx_name(username) USING BTREE, ALGORITHM=INPLACE, LOCK=NONE;

