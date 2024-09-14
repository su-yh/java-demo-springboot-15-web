-- drop table if exists sys_user;

create table sys_user
(
    id                  bigint auto_increment comment '主键' primary key,
    username            VARCHAR(64) NOT NULL COMMENT '用户名',
    password            VARCHAR(64) NOT NULL COMMENT '通过盐值加密后的密码',
    nickname            VARCHAR(64) NOT NULL COMMENT '昵称',
    salt                VARCHAR(32) NOT NULL COMMENT '盐',
    two_factor_auth_key VARCHAR(32) NOT NULL COMMENT 'google 二次认证密钥',

    created             datetime DEFAULT CURRENT_TIMESTAMP,
    updated             datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) engine = innodb comment '用户信息';



ALTER TABLE sys_user
    ADD UNIQUE INDEX uni_name (username) USING BTREE;


-- 初始化一个admin 用户: 密码也是admin
-- admin 用户的ID 必须 是1 ，这在后面的关联关系的时候需要用到
INSERT INTO sys_user (id, username, password, nickname, salt, two_factor_auth_key)
VALUES (1, 'admin', '$2a$10$RO4jsSW3Xv1d9LMFk5vj/eF9Od2w5eCTroLMhOZCJBsanez/QMWA2', 'admin',
        '837dc2085500480f8b0c2b0222d15f10', 'HMG6NIOZPH65HGKIMLUW7YIDCG2M65XN');







