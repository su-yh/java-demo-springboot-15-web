create table weight_config
(
    id               bigint auto_increment comment '主键id'
        primary key,
    weight_dimension varchar(50)  null comment '维度',
    compare_status   tinyint(1)   null comment '比较状态',
    score            int          null comment '分数',
    weight_value     int          null comment '值',
    remark           varchar(200) null comment '备注'
)
    comment '权重分配置';


