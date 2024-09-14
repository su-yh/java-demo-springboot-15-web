-- ----------------------------
-- 8、角色和部门关联表  角色1-N部门
-- ----------------------------
-- drop table if exists sys_role_dept;
create table sys_role_dept
(
    role_id bigint(20) not null comment '角色ID',
    dept_id bigint(20) not null comment '部门ID',
    primary key (role_id, dept_id)
) engine=innodb comment = '角色和部门关联表';
