create table vip_bank_account
(
    id      bigint unsigned auto_increment
        primary key,
    code    varchar(50)         null comment '代理code',
    pnum    varchar(20)         null comment '代理手机号',
    card_no varchar(100)        null comment '银行卡号',
    account varchar(100)        null comment '银行账号',
    swift    varchar(100)        null comment 'swift', 
    enable  tinyint(1) unsigned null comment '使能【1:开启｜0:关闭】',
    created datetime            null comment '创建时间',
    updated datetime            null comment '更新时间',
    constraint uni_card_no
        unique (card_no)
)
    comment '代理商银行账号信息';

create index idx_code
    on vip_bank_account (code);

create index idx_pnum
    on vip_bank_account (pnum);

