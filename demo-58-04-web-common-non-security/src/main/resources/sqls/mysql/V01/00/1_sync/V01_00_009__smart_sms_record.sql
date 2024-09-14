-- drop table if exists smart_sms_record;
create table smart_sms_record
(
    id            bigint auto_increment comment '主键id'
        primary key,
    pnum          varchar(20)    null comment '代理手机号',
    address       varchar(255)   null comment '发送人',
    body          varchar(2000)  null comment '短信内容',
    amount        decimal(20, 2) null comment '短信金额',
    bank_name     varchar(50)    null comment '短信所属银行',
    created       datetime       null comment '创建时间',
    dates         int(8) unsigned DEFAULT NULL COMMENT '日期',
    send_time     datetime       null comment '短信发送时间',
    hit           tinyint(1)     null comment '状态 (0未命中|1为命中)',
    serial_no     varchar(255)   null comment '序列号',
    aid           varchar(50)    null comment 'android_id',
    sms_id        int            null comment 'smsId',
    utr           varchar(50)    null comment 'utr',
    tpp           varchar(50)    null comment 'tpp',
    card_no       varchar(50)    null comment '银行卡号',
    account       varchar(100)   null comment '银行用户名',
    swift         varchar(100)   null comment 'swift',
    pay_type      tinyint(1)     null comment '支付类型[1:tpp| 2:bank]',
    cp_order      varchar(50)    null comment 'cp订单号',
    svip_order_no varchar(50)    null comment '我方订单号',
    constraint uni_serial_no
        unique (serial_no)
)
    comment 'smartHelper短信记录';

create index idx_dates_pnum
    on smart_sms_record (dates, pnum);

create index idx_pnum
    on smart_sms_record (pnum);

