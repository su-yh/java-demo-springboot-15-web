create table scheduling_transfer_record
(
    id                bigint auto_increment comment '主键id'
        primary key,
    code              varchar(20)    null,
    pnum              varchar(20)    null,
    pay_type          tinyint(1)     null comment '支付类型【1:tpp支付｜2:银行卡支付】]',
    tpp               varchar(100)   null comment 'tpp',
    card_no           varchar(100)   null comment '银行卡号',
    account           varchar(100)   null comment '银行账号',
    swift             varchar(50)    null comment 'swift',
    receiver_uid      varchar(20)    null comment '买家uid',
    amount            decimal(24, 2) null comment '金额',
    real_amount       decimal(24, 2) null comment '订单真实金额',
    cp_order          varchar(50)    null comment 'cp订单号',
    svip_order_no     varchar(50)    null comment '我方生成订单号(= mchnt_order_no)',
    mchnt_order_no    varchar(50)    null comment '我方生成订单号',
    uid               varchar(30)    null comment 'vip的uid信息',
    pn                varchar(100)   null comment '项目名称',
    pkg               varchar(100)   null,
    channel           varchar(100)   null comment '渠道号',
    status            tinyint(1)     null comment '状态（【1-调度中、2-成功（已付款已发货）、3-调度失败，4-已付款未发货, 5-审核中】)',
    score             int            null comment '权重分',
    mtime             datetime       null comment '完成时间',
    created           datetime       null comment '创建时间',
    updated           datetime       null comment '更新时间',
    expire_time       datetime       null comment '过期时间',
    dates             int(8)         null comment '日期',
    transfer_order_no varchar(50)    null comment '赠品订单号',
    utr               varchar(50)    null comment 'UTR',
    constraint uni_cp_order
        unique (cp_order),
    constraint uni_svip_order_no
        unique (svip_order_no),
    constraint uni_transfer_order_no
        unique (transfer_order_no)
)
    comment '调度记录';

create index idx_code
    on scheduling_transfer_record (code);

create index idx_dates
    on scheduling_transfer_record (dates);

create index idx_pnum_tpp_card_no
    on scheduling_transfer_record (pnum, tpp, card_no);

