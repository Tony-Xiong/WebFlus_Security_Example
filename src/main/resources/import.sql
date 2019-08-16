//初始化用户
insert into role (role_id, role_name) values ( 1, 'admin' );
insert into role (role_id, role_name) values ( 2, 'user' );
insert into operator (age, name, password, id) values (25, 'admin', 'admin', 'id000001');
insert into operator (age, name, password, id) values (25, 'user1', '123456', 'id000002');
insert into operator (age, name, password, id) values (25, 'user2', '123456', 'id000003');
insert into operator (age, name, password, id) values (25, 'user3', '123456', 'id000004');
insert into operator (age, name, password, id) values (25, 'user4', '123456', 'id000005');
insert into OPERATOR_ROLES  (ROLES_ROLE_ID , OPERATOR_ID) values ( 1, 'id000001' );
insert into OPERATOR_ROLES  (ROLES_ROLE_ID , OPERATOR_ID) values ( 2, 'id000001' );
insert into OPERATOR_ROLES  (ROLES_ROLE_ID , OPERATOR_ID) values ( 2, 'id000002' );
insert into OPERATOR_ROLES  (ROLES_ROLE_ID , OPERATOR_ID) values ( 2, 'id000003' );
insert into OPERATOR_ROLES  (ROLES_ROLE_ID , OPERATOR_ID) values ( 2, 'id000004' );
insert into OPERATOR_ROLES  (ROLES_ROLE_ID , OPERATOR_ID) values ( 2, 'id000005' );