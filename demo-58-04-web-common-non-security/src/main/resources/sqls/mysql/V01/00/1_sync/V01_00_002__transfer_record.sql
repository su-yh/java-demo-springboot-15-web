create table transfer_record
(
    id           bigint unsigned auto_increment comment '主键'
        primary key,
    `order`      varchar(50)    null comment '流水编号',
    cp_order     varchar(50)    null comment 'cp 响应订单号',
    code         varchar(20)    null comment '代理code',
    receiver_uid varchar(30)    null comment '接收方uid',
    sender_uid   varchar(30)    null comment '发送方uid',
    present      decimal(20, 2) null comment '赠品数量, ',
    status       int            null comment '状态[1-赠送中，2-赠送成功，3-赠送失败]',
    created      timestamp      null comment '创建时间',
    mtime        timestamp      null comment '完成时间',
    pnum         varchar(20)    null comment '电话号码',
    pn           varchar(20)    null comment '项目',
    constraint unique_order
        unique (`order`)
);

create index idx_t_r_code
    on transfer_record (code);

create index idx_t_r_created
    on transfer_record (created);

create index idx_t_r_receiver_uid
    on transfer_record (receiver_uid);

