use clothingshop;

/*inserts to roles*/
insert ignore into roles(id_role, role_name)
values
        (UUID_TO_BIN('101b3ce2-dbf4-11ee-8508-64006a586a6a'), 'ADMINISTRADOR'),
        (UUID_TO_BIN('101b5bf7-dbf4-11ee-8508-64006a586a6a'), 'COMPRADOR'),
        (UUID_TO_BIN('101b5d84-dbf4-11ee-8508-64006a586a6a'), 'VENDEDOR');

/*insert to usera*/
insert ignore into users(id_user,email)
values
       (UUID_TO_BIN('3efeb060-dbf7-11ee-8508-64006a586a6a'), 'root@gmail.com');

insert ignore into user_roles(id_user_role, fk_id_role, fk_id_user)
values
       (UUID_TO_BIN('aee50784-dbf7-11ee-8508-64006a586a6a'), UUID_TO_BIN('101b3ce2-dbf4-11ee-8508-64006a586a6a'), UUID_TO_BIN('3efeb060-dbf7-11ee-8508-64006a586a6a'));