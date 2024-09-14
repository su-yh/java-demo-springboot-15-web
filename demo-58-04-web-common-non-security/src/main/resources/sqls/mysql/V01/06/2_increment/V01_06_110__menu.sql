INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (1, 'Agent Information', 0, 0, 'agent-manage', 'pages/agent-manage/index', NULL, 'agentManage', 1, 0, 'C', '0',
        '0', 'agent-manage', 'AccountBookFilled', 'admin01', sysdate(), 'admin', sysdate(), '',
        'routes.agentManage');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (2, 'Agent Account Configuration', 0, 1, 'agent-account-config', 'pages/agent-account-config/index', NULL,
        'agentAccountManage', 1, 0, 'C', '0', '0', '', 'NodeIndexOutlined', 'admin01', sysdate(), 'admin',
        sysdate(), '', 'routes.agentAccountConfig');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (3, 'Order Manage', 0, 2, 'order-manage', 'pages/order-manage/index', NULL, 'orderManage', 1, 0, 'C', '0', '0',
        '', 'FileProtectOutlined', 'admin01', sysdate(), 'admin', sysdate(), '',
        'routes.orderManage');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (4, 'Abnormal Order Manage', 0, 2, 'abnormal-order-manage', NULL, NULL, '', 1, 0, 'M', '0', '0', NULL,
        'PullRequestOutlined', 'admin01', sysdate(), 'admin', sysdate(), '',
        'routes.abnormalOrderManage');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (5, 'Order Entry', 4, 0, 'order-entry', 'pages/abnormal-order-manage/order-entry/index', NULL,
        'abnormalOrderEntry', 1, 0, 'C', '0', '0', '', '#', 'admin01', sysdate(), 'admin',
        sysdate(), '', 'routes.abnormalOrderEntry');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (6, 'Order Processing', 4, 1, 'order-process', 'pages/abnormal-order-manage/order-process/index', NULL,
        'abnormalOrderProcess', 1, 0, 'C', '0', '0', '', '#', 'admin01', sysdate(), 'admin',
        sysdate(), '', 'routes.abnormalOrderProcess');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (7, 'Order Review', 4, 2, 'order-review', 'pages/abnormal-order-manage/order-review/index', NULL,
        'abnormalOrderReview', 1, 0, 'C', '0', '0', '', '#', 'admin01', sysdate(), 'admin',
        sysdate(), '', 'routes.abnormalOrderReview');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (8, 'Abnormal Order Statistics', 4, 3, 'order-statistics', NULL, NULL, '', 1, 0, 'M', '0', '0', NULL, '#',
        'admin01', sysdate(), 'admin', sysdate(), '', 'routes.abnormalOrderStatistics');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (9, 'By Day', 8, 0, 'day-statistic', 'pages/abnormal-order-manage/order-statistics/day-statistic/index', NULL,
        'dayStatistic', 1, 0, 'C', '0', '0', '', '#', 'admin01', sysdate(), 'admin', sysdate(),
        '', 'routes.abnormalOrderDay');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (10, 'By Operations Staff', 8, 1, 'operations-statistic',
        'pages/abnormal-order-manage/order-statistics/operations-statistic/index', NULL, 'abnormalOperations', 1, 0,
        'C', '0', '0', '', '#', 'admin01', sysdate(), 'admin', sysdate(), '',
        'routes.abnormalOperations');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (11, 'By Merchant No', 8, 3, 'merchantsno-statistic',
        'pages/abnormal-order-manage/order-statistics/merchantsno-statistic/index', NULL, 'abnormalMerchantsNo', 1, 0,
        'C', '0', '0', '', '#', 'admin01', sysdate(), 'admin', sysdate(), '',
        'routes.abnormalMerchantsNo');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (12, 'Statistics Manage', 0, 4, 'statistics-manage', NULL, NULL, '', 1, 0, 'M', '0', '0', NULL,
        'BarChartOutlined', 'admin01', sysdate(), 'admin', sysdate(), '',
        'routes.statisticsManage');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (13, 'Order(By Day)', 12, 0, 'day', 'pages/statistics-manage/day/index', NULL, 'statisticsDay', 1, 0, 'C', '0',
        '0', '', '#', 'admin01', sysdate(), 'admin', sysdate(), '', 'routes.statisticsDay');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (14, 'Order(By Agent)', 12, 1, 'code', 'pages/statistics-manage/code/index', NULL, 'Code', 1, 0, 'C', '0', '0',
        '', '#', 'admin01', sysdate(), 'admin', sysdate(), '', 'routes.statisticsCode');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (15, 'SMS Payment(Details)', 12, 2, 'sms-pay-details', 'pages/statistics-manage/sms-pay-details/index', NULL,
        'smsPayDetails', 1, 0, 'C', '0', '0', '', '#', 'admin01', sysdate(), 'admin', sysdate(),
        '', 'routes.statisticsSmsDetails');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (16, 'SMS Payment (Agent)', 12, 3, 'sms-pay-agent', 'pages/statistics-manage/sms-pay-agent/index', NULL,
        'smsPayAgents', 1, 0, 'C', '0', '0', '', '#', 'admin01', sysdate(), 'admin', sysdate(),
        '', 'routes.statisticsSmsAgent');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (17, 'Weight Config', 0, 5, 'weight-config', 'pages/weight-config/index', NULL, 'weightConfig', 1, 0, 'C', '0',
        '0', '', 'HourglassOutlined', 'admin01', sysdate(), 'admin', sysdate(), '',
        'routes.weightConfig');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (18, 'Customer Service Tools', 0, 6, 'cs-tools', 'pages/cs-tools/index', NULL, 'csTools', 1, 0, 'C', '0', '0',
        '', 'UserSwitchOutlined', 'admin01', sysdate(), 'admin', sysdate(), '',
        'routes.csTools');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (19, 'System Manage', 0, 7, 'system', NULL, NULL, '', 1, 0, 'M', '0', '0', NULL, 'TrophyOutlined', 'admin01',
        sysdate(), 'admin', sysdate(), '', 'routes.system');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (20, 'User Manage', 19, 0, 'user-manage', 'pages/system/user-manage/index', NULL, 'systemUser', 1, 0, 'C', '0',
        '0', '', 'UserOutlined', 'admin01', sysdate(), 'admin', sysdate(), '',
        'routes.systemUser');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (21, 'Role Manage', 19, 1, 'role-manage', 'pages/system/role-manage/index', NULL, 'systemRole', 1, 0, 'C', '0',
        '0', '', '#', 'admin01', sysdate(), 'admin', sysdate(), '', 'routes.systemRole');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache,
                      menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark,
                      menu_key)
VALUES (22, 'Menu Manage', 19, 3, 'menu-manage', 'pages/system/menu-manage/index', NULL, 'systemMenu', 1, 0, 'C', '0',
        '0', '', '#', 'admin01', sysdate(), 'admin', sysdate(), '', 'routes.systemMenu');
