create table vip_account
(
    id      bigint unsigned auto_increment,
    code    varchar(50)         null comment 'code',
    pnum    varchar(20)         null comment 'pnum',
    account varchar(100)        null comment 'account',
    img     varchar(255)        null comment 'img',
    enable  tinyint(1) unsigned null comment '使能[1-开启｜0-关闭]',
    created datetime            null comment 'created',
    updated datetime            null comment 'updated',
    constraint vip_account_pk
        primary key (id)
)
    comment '代理账户';

create index idx_account
    on vip_account (account);

create index idx_pnum
    on vip_account (pnum);

create index idx_code
    on vip_account (code);