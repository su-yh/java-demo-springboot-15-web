create table vip_tpp
(
    id      bigint unsigned auto_increment
        primary key,
    code    varchar(50)         null comment '代理code', 
    pnum    varchar(20)         null comment '代理手机号',
    tpp     varchar(100)        null comment '第三方支付 Third-party payment platform',
    enable  tinyint(1) unsigned null comment '使能【1-开启｜0-关闭】',
    created datetime            null comment '创建时间',
    updated datetime            null comment '更新时间',
    constraint uni_tpp
        unique (tpp)
)
    comment '代理tpp';

create index idx_code
    on vip_tpp (code);

create index idx_pnum
    on vip_tpp (pnum);

