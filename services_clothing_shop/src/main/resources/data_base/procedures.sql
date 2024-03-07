drop procedure if exists `sp_post_role_user`;
delimiter $$
create procedure `sp_post_role_user`(in p_role_id varchar(255), in p_user_id varchar(255))
begin
insert into user_roles(id_user_role, fk_id_role, fk_id_user)
values (UUID_TO_BIN(UUID()), UUID_TO_BIN(p_role_id), UUID_TO_BIN(p_user_id));
end$$
delimiter ;


