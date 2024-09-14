

-- 添加字段 tg_username/chatID
ALTER TABLE vip_info
    ADD COLUMN tg_username varchar(50) NULL COMMENT 'tg_username',
    ADD COLUMN tg_chatID BIGINT UNSIGNED NULL COMMENT 'tg_chatID';

-- 添加唯一索引
ALTER TABLE vip_info
    ADD UNIQUE INDEX idx_v_i_tg(tg_username) USING BTREE;

-- 添加人工审核状态字段
ALTER TABLE vip_tpp
    ADD COLUMN review tinyint(1) NULL COMMENT '人工审核状态【2 审核通过，3 审核拒绝】';
ALTER TABLE vip_bank_account
    ADD COLUMN review tinyint(1) NULL COMMENT '人工审核状态【2 审核通过，3 审核拒绝】';

-- 添加订单审计状态
ALTER TABLE scheduling_transfer_record
    ADD COLUMN order_audit_status tinyint(1) default 0 NULL COMMENT '订单审计状态【0-正常，1-待审核，2-存疑，3-审核通过，4-拒绝】';

-- 添加异常订单审核人员ID
ALTER TABLE scheduling_transfer_record
    ADD COLUMN audit_user_id BIGINT NULL COMMENT '订单审核用户主键ID';

-- 添加异常订单审核人员昵称
ALTER TABLE scheduling_transfer_record
    ADD COLUMN audit_user_nick VARCHAR(64) NULL COMMENT '订单审核用户昵称';

-- 购买信息
ALTER TABLE scheduling_transfer_record
    ADD COLUMN order_info varchar(1000) NULL
    COMMENT '购买信息 [{"sku":"SKU123","quantity":10,"price":99.99},{"sku":"SKU456","quantity":5,"price":59.99}]';
-- 物流信息
ALTER TABLE scheduling_transfer_record
    ADD COLUMN logistics_info varchar(1000) NULL
    COMMENT '物流信息 [{"carrier":"usps","trackingNumber":"123456"},{"carrier":"ups","trackingNumber":"789012"}]';

ALTER TABLE utr_upload_record
    ADD COLUMN cp_order_revise varchar(50) NULL COMMENT '修正订单号';

ALTER TABLE scheduling_transfer_record
    ADD COLUMN audit_remark varchar(256) NULL COMMENT '审核备注';


