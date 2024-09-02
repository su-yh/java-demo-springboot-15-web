-- 实时曲线统计
DROP TABLE if EXISTS realtime_trend_statistic;

CREATE TABLE realtime_trend_statistic
(
    id BIGSERIAL PRIMARY KEY,
    dates             int          NOT NULL,
    time_step         int          NOT NULL,
    channel           varchar(100) NOT NULL,
    pn                varchar(20)  NOT NULL,
    source            varchar(16)  NOT NULL,
    registry_count    int            default 0,
    recharge_count    int            default 0,
    recharge_amount   decimal(16, 2) default 0.00,
    withdrawal_count  int            default 0,
    withdrawal_amount decimal(16, 2) default 0.00,
    created           timestamp(3),
    updated           timestamp(3)
);

CREATE UNIQUE INDEX uni_realtime_trend_statistic_d_t_c_p_s ON realtime_trend_statistic (dates, channel, pn, source, time_step);

COMMENT ON TABLE realtime_trend_statistic IS '实时曲线统计';

comment on column realtime_trend_statistic.dates is '报表日期，格式：yyyyMMdd';
comment on column realtime_trend_statistic.time_step is '时间刻度，从0到47，一天共48个刻度';
comment on column realtime_trend_statistic.source is '投放平台，如：GG、FB';
comment on column realtime_trend_statistic.registry_count is '注册数';
comment on column realtime_trend_statistic.recharge_count is '充值笔数';
comment on column realtime_trend_statistic.recharge_amount is '充值总金额';
comment on column realtime_trend_statistic.withdrawal_count is '提现笔数';
comment on column realtime_trend_statistic.withdrawal_amount is '提现总金额';



ALTER TABLE realtime_trend_statistic
    ADD COLUMN flink_execution_mode varchar(32);
ALTER TABLE realtime_trend_statistic
    ADD COLUMN flink_window_start timestamp;
ALTER TABLE realtime_trend_statistic
    ADD COLUMN flink_window_end timestamp;
comment on column realtime_trend_statistic.flink_execution_mode is 'flink 运行时的执行模式';
comment on column realtime_trend_statistic.flink_window_start is 'flink 运行时统计窗口开始时间';
comment on column realtime_trend_statistic.flink_window_end is 'flink 运行时统计窗口结束(不包含)时间';

