INSERT INTO chat_user(name, email, passwd, forgot_passwd, created, updated)
VALUES('管理人', 'root@example.com', 'root', 'root', '2019-11-12 08:34:19',  '2019-11-12 08:34:19');
INSERT INTO chat_user(name, email, passwd, forgot_passwd, created, updated)
VALUES('tanaka', 'tanaka@example.com', 'tanaka', 'tanaka', '2019-11-12 08:34:19',  '2019-11-12 08:34:19');
INSERT INTO chat_user(name, email, passwd, forgot_passwd, created, updated)
VALUES('ken', 'ken@example.com', 'ken', 'ken', '2019-11-12 08:34:19',  '2019-11-12 08:34:19');

INSERT INTO chat_room(name, comment, tag, max_roomsum, user_id, created, updated)
VALUES('管理人ルーム', 'テストルームです。', 'root', '2', 1, '2019-11-12 08:34:19', '2019-11-12 08:34:19');

INSERT INTO chat_comment(comment, room_id, user_id, created)
VALUES('こんにちは', '1', '1', '2019-11-12 08:34:19');

INSERT INTO chat_login(room_id, user_id, created)
VALUES('1', '1', '2019-11-12 08:34:19');

INSERT INTO chat_enter(room_id, user_id, manager_id, max_sum, created)
VALUES('1', '1', '1', 2, '2019-11-12 08:34:19');
