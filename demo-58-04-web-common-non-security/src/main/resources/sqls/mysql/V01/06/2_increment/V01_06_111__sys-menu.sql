
-- 允许null
ALTER TABLE sys_menu
    MODIFY COLUMN menu_key varchar(64) NULL COMMENT '每个菜单的唯一主键';



