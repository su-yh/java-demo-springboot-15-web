-- 样例
INSERT INTO vip_info (code, uid, ranking, sequence, enable, `show`, evaluate, pnum, pn, balance, icon, name,
                      transfer_balance, transfer_count, contact_details, remark, created,
                      created_by, init_transfer_balance, init_transfer_count, online, online_day_of_week,
                      online_hour_of_day, status, tpp, whats_app, audit_status, utr, vip_type,
                      receive_orders, pan_card, aid, bank_account)
VALUES ('seller_01', '54589', 1, 20, 1, 1, 56, '918275412365', 'face_serums', 3377.00, 'https://files.catbox.moe/v1n9a9.png', 'sudar store',
        26629534.00, 9381, '917389572493', 1, '2022-10-22 20:00:00',
        'Admin', 3478711.00, 2164, 1, '1,2,3,4,5,6,7',
        '0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23', null, null, null, null, null, null,
        null, null, null, null);

-- 样例
INSERT INTO weight_config (weight_dimension, compare_status, score, weight_value, remark)
VALUES ('half_hour_order_number', -1, 25, 10, null);
INSERT INTO weight_config (weight_dimension, compare_status, score, weight_value, remark)
VALUES ('half_hour_order_number', -1, 15, 50, null);
INSERT INTO weight_config (weight_dimension, compare_status, score, weight_value, remark)
VALUES ('half_hour_order_number', -1, 5, 100, null);
INSERT INTO weight_config (weight_dimension, compare_status, score, weight_value, remark)
VALUES ('daily_order_number', -1, 25, 50, null);
