-- drop table if exists user;

create table user
(
    id       bigint auto_increment comment '主键' primary key,
    username VARCHAR(64) NOT NULL COMMENT '用户名',
    password VARCHAR(64) NOT NULL COMMENT '通过盐值加密后的密码',
    nickname VARCHAR(64) NOT NULL COMMENT '昵称',
    salt     VARCHAR(32) NOT NULL COMMENT '盐',

    created  datetime DEFAULT CURRENT_TIMESTAMP,
    updated  datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) engine = innodb comment '用户信息';



ALTER TABLE user
    ADD UNIQUE INDEX uni_name (username) USING BTREE;


-- 初始化一个admin 用户: 密码也是admin
INSERT INTO user (username, password, nickname, salt)
VALUES ('admin', '$2a$10$RO4jsSW3Xv1d9LMFk5vj/eF9Od2w5eCTroLMhOZCJBsanez/QMWA2', 'admin', '837dc2085500480f8b0c2b0222d15f10');







