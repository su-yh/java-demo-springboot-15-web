

-- 添加字段 tg_username/chatID
ALTER TABLE vip_info
    ADD COLUMN email varchar(320) NULL COMMENT 'email';