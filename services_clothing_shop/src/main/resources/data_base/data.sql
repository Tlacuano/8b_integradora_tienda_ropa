use clothingshop;
insert ignore into request_status (id_status, status)
values (uuid_to_bin('9f1cb62f-8b57-46d1-95d4-8481e4f1c9e2'), 'Pendiente'),
       (uuid_to_bin('7c5409f1-64c5-431d-8b4c-e0d60058d100'), 'Aprobado'),
       (uuid_to_bin('0d64c931-8f3c-4b0c-a16b-dad013cdaeb4'), 'Cancelado'),
       (uuid_to_bin('ab34b311-dd64-11ee-8508-64006a586a6a'), 'Rechazado');

insert ignore into address_status (id_status, status)
values (uuid_to_bin('3b43b9d0-9f46-49fe-ae14-e69b8d1ca821'), 'Habilitada'),
       (uuid_to_bin('6c013eed-dd65-11ee-8508-64006a586a6a'), 'Desabilitada'),
       (uuid_to_bin('6c013f97-dd65-11ee-8508-64006a586a6a'), 'Predeterminada'),
       (uuid_to_bin('6c013ff1-dd65-11ee-8508-64006a586a6a'), 'Venta');

insert ignore into order_status (id_status, status)
values (uuid_to_bin('9b8c35d0-7870-41c6-8719-745eaaae1a13'), 'Preparacion'),
       (uuid_to_bin('a5c21dd3-dd66-11ee-8508-64006a586a6a'), 'Entregado'),
       (uuid_to_bin('a5c223d6-dd66-11ee-8508-64006a586a6a'), 'Rembolsado'),
       (uuid_to_bin('a5c223d6-dd66-12ee-8508-64006a586a6a'), 'Enviado'),
       (uuid_to_bin('a5c225a4-dd66-11ee-8508-64006a586a6a'), 'Cancelado');

insert ignore into card_status (id_status, status)
values (UUID_TO_BIN('1a14b8df-8269-4484-ba7d-1441f1e1d148'), 'Habilitada'),
       (UUID_TO_BIN('efade8fb-6465-4e48-8a0a-146fca51b59d'), 'Deshabilitada'),
       (UUID_TO_BIN('691b3419-dd67-11ee-8508-64006a586a6a'), 'Predeterminada');

insert ignore into image_product_status (id_status, status)
values (uuid_to_bin('bc43ef1a-13a2-4b67-bb13-f1d376e5b510'), 'Habilitada'),
       (uuid_to_bin('2f4b3cb9-e1d8-4cf9-8e16-5e6208bc7e20'), 'Deshabilitada'),
       (uuid_to_bin('f02ff8b1-dd67-11ee-8508-64006a586a6a'), 'Predeterminada');

insert ignore into categories (id_category, category, image, status)
values (uuid_to_bin('3cc47b97-5728-4c81-92bb-3880f8c27e12'), 'Hombres', 'imagen_categoria_a.jpg', 1),
       (uuid_to_bin('8367843c-47f7-4f12-af6b-2267f6819969'), 'Mujeres', 'imagen_categoria_b.jpg', 1),
       (uuid_to_bin('a4a4d8aa-5e1d-45a3-84e6-63f1708377d0'), 'Niños', 'imagen_categoria_c.jpg', 1);

insert ignore into subcategories (id_subcategory, fk_id_category, subcategory, image, status)
values (uuid_to_bin('9e9df4fd-036d-48b1-85e8-4f8f4389f1d4'), uuid_to_bin('3cc47b97-5728-4c81-92bb-3880f8c27e12'),
        'Subcategoría A', 'imagen_subcategoria_a.jpg', 1),
       (uuid_to_bin('80c49d95-7edf-4475-91b6-74ef025d2c15'), uuid_to_bin('8367843c-47f7-4f12-af6b-2267f6819969'),
        'Subcategoría B', 'imagen_subcategoria_b.jpg', 0),
       (uuid_to_bin('1e2029f0-25b3-4954-bfb7-17a778f12612'), uuid_to_bin('a4a4d8aa-5e1d-45a3-84e6-63f1708377d0'),
        'Subcategoría C', 'imagen_subcategoria_c.jpg', 1);

insert ignore into roles (id_role, role_name)
values (uuid_to_bin('0f8fad5b-d9cb-469f-a165-70867728950e'), 'ROLE_ADMIN'),
       (uuid_to_bin('1ff1e570-3b33-4e1f-a9d0-5fd500e4ac9b'), 'ROLE_SELLER'),
       (uuid_to_bin('1ff1e570-3b33-4e1f-a9d0-5fd500e4ac9c'), 'ROLE_SUPERADMIN'),
       (uuid_to_bin('58c0d70e-8d7b-47e3-bfa9-6f150c0b5c6e'), 'ROLE_BUYER');

insert ignore into users (id_user, password, email, verification_code, status, email_verified, privacy_policy)
values (uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'), '$2a$10$9ecu02DGkGrQhl9iEN4uvuCm18oS.9jnqdDBQIzIEj00tm9tjud0S', 'admin@example.com', 'ABCD1234',1, 1, 1),
       (uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6'), '$2a$10$9ecu02DGkGrQhl9iEN4uvuCm18oS.9jnqdDBQIzIEj00tm9tjud0S', 'superadmin@example.com', 'WXYZ5678',1, 1, 1),
       (uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'), '$2a$10$9ecu02DGkGrQhl9iEN4uvuCm18oS.9jnqdDBQIzIEj00tm9tjud0S', 'seller@example.com', '1234EFGH', 1, 1, 1),
       (uuid_to_bin('a0d12c0d-31b9-4d3e-a67b-9d9d9d9d9d9d'), '$2a$10$9ecu02DGkGrQhl9iEN4uvuCm18oS.9jnqdDBQIzIEj00tm9tjud0S', 'buyer@example.com', 'EFGH5678', 1, 1, 1);


insert ignore into user_roles (id_user_role, fk_id_role, fk_id_user)
values (uuid_to_bin('65ac0dd9-4b5d-4b51-9475-4e397837582d'), uuid_to_bin('0f8fad5b-d9cb-469f-a165-70867728950e'),
        uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4')),
       (uuid_to_bin('65ac0dd9-4b5d-4b51-9475-4e3978375822'), uuid_to_bin('1ff1e570-3b33-4e1f-a9d0-5fd500e4ac9c'),
        uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6')),
       (uuid_to_bin('65ac0dd9-4b5d-4b51-9475-4e3978375811'), uuid_to_bin('1ff1e570-3b33-4e1f-a9d0-5fd500e4ac9b'),
        uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f')),
       (uuid_to_bin('65ac0dd9-4b5d-4b51-9475-4e3978375811'), uuid_to_bin('58c0d70e-8d7b-47e3-bfa9-6f150c0b5c6e'),
        uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f')),
         (uuid_to_bin('65ac0dd9-4b5d-4b51-9475-4e3978375711'), uuid_to_bin('58c0d70e-8d7b-47e3-bfa9-6f150c0b5c6e'),
        uuid_to_bin('a0d12c0d-31b9-4d3e-a67b-9d9d9d9d9d9d'));

insert ignore into people(id_person, fk_id_user, name, last_name, second_last_name, picture, birthday, phone_number, gender,verification_phone )
values (uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec1'), uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'), 'federico', 'peluche', 'smith', 'https://picsum.photos/710/710?random',
        '2000-09-12', '7772314221', 'otros', 1 ),
       (uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec2'),uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6'), 'billie', 'guzman', 'eilish', 'https://picsum.photos/710/710?random',
        '2001-12-18', '7778933214', 'femenino', 1),
       (uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec3'),uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'), 'dario jose', 'fernandez', 'pinkman', 'https://picsum.photos/710/710?random',
        '1998-10-01', '7778291039', 'masculino', 1),
       (uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'),uuid_to_bin('a0d12c0d-31b9-4d3e-a67b-9d9d9d9d9d9d'), 'Tlacuano', 'Enriquez', 'de la Cruz', 'https://picsum.photos/710/710?random',
        '1990-01-01', '5551234567', 'otros', 1);

insert ignore into address(id_address, address, references_address, postal_code, state, street, neighborhood,
                           fk_id_user, fk_id_status)
values (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be2'), 'Avenida Juárez 123',
        'Entre la calle 5 de mayo y la calle 16 de septiembre', '76000', 'Querétaro', 'Juárez', 'Centro',
        uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'), uuid_to_bin('3b43b9d0-9f46-49fe-ae14-e69b8d1ca821')),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be3'), 'Avenida Juárez 124',
        'Entre la calle 5 de mayo y la calle 16 de septiembre', '76000', 'Querétaro', 'Juárez', 'Centro',
        uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6'), uuid_to_bin('6c013eed-dd65-11ee-8508-64006a586a6a')),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be4'), 'Avenida Juárez 125',
        'Entre la calle 5 de mayo y la calle 16 de septiembre', '76000', 'Querétaro', 'Juárez', 'Centro',
        uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'), uuid_to_bin('3b43b9d0-9f46-49fe-ae14-e69b8d1ca821')),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be4'), 'Avenida Juárez 125',
        'Entre la calle 5 de mayo y la calle 16 de septiembre', '76000', 'Querétaro', 'Juárez', 'Centro',
        uuid_to_bin('a0d12c0d-31b9-4d3e-a67b-9d9d9d9d9d9d'), uuid_to_bin('3b43b9d0-9f46-49fe-ae14-e69b8d1ca821'));


insert ignore into requests_become_seller (id_request_become_seller, fk_id_user, fk_id_status, rejection_reason)
values (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be4'), uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'),
        uuid_to_bin('7c5409f1-64c5-431d-8b4c-e0d60058d100'), ''),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be5'), uuid_to_bin('a0d12c0d-31b9-4d3e-a67b-9d9d9d9d9d9d'),
        uuid_to_bin('9f1cb62f-8b57-46d1-95d4-8481e4f1c9e2'), 'No cumple con los requisitos');

insert ignore into sellers_information (fk_id_user, tax_identification_number, privacy_policy_agreement,
                                        image_identification, curp, secondary_phone_number)
values (uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'), '123456789', 1, 'https://picsum.photos/1080/710?random%27', 'CURP123456789', '7774666887');

insert ignore into payment_cards(id_payment_card, cardholder_name, card_number, expiration_date, cvv, fk_id_user,
                                 fk_id_status)
values (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be8'), 'Federico Peluche Smith', '1234567891234567', '12/25',
        '123', uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'),
        uuid_to_bin('1a14b8df-8269-4484-ba7d-1441f1e1d148')),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be9'), 'Billie Guzman Eilish', '9876543219876543', '12/25', '321',
        uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6'), uuid_to_bin('efade8fb-6465-4e48-8a0a-146fca51b59d')),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be0'), 'Dario Jose Fernandez Pinkman', '1239874561239874',
        '12/25', '456', uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'),
        uuid_to_bin('691b3419-dd67-11ee-8508-64006a586a6a'));

insert ignore into orders (id_order, order_date, order_number, fk_id_payment_card, fk_id_address)
values (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be1'), '2021-12-01', '123456789',
        uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be8'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be2')),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b33'), '2021-12-02', '987654321',
        uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be9'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be3')),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b34'), '2021-12-03', '123987456',
        uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be0'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be4'));

insert ignore into products (id_product, product_name, description, price, amount, fk_id_subcategory, status,fk_id_user)
values (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b25'), 'playera',
        'playera de algodon', 200.0, 1,
        uuid_to_bin('9e9df4fd-036d-48b1-85e8-4f8f4389f1d4'), 1,
        uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6')),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b26'), 'pantalon',
        'pantalon de mezclilla', 300.5, 5,
        uuid_to_bin('80c49d95-7edf-4475-91b6-74ef025d2c15'), 1,
        uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6')),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b27'), 'zapatos',
        'zapatos de piel', 400.5, 3,
        uuid_to_bin('1e2029f0-25b3-4954-bfb7-17a778f12612'), 1,
        uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6'));

insert ignore into product_gallery (id_image, fk_id_product, image, fk_id_status)
values (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b28'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b25'),
        'imagen1', uuid_to_bin('bc43ef1a-13a2-4b67-bb13-f1d376e5b510')),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b29'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b26'),
        'imagen2', uuid_to_bin('2f4b3cb9-e1d8-4cf9-8e16-5e6208bc7e20')),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b30'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b27'),
        'imagen3', uuid_to_bin('f02ff8b1-dd67-11ee-8508-64006a586a6a'));

insert ignore into orders_has_products (id_order_product, fk_id_order, fk_id_product, amount, fk_id_status)
values (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b10'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be1'),
        uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b25'), 1, uuid_to_bin('9b8c35d0-7870-41c6-8719-745eaaae1a13')),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b11'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b33'),
        uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b26'), 2, uuid_to_bin('9b8c35d0-7870-41c6-8719-745eaaae1a13')),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b12'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b34'),
        uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b27'), 3, uuid_to_bin('9b8c35d0-7870-41c6-8719-745eaaae1a13'));

insert ignore into reviews(id_review,comment, fk_id_order_product, review_date, assessment)
values (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b13'), 'Excelente producto', uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b10'),
        '2021-12-01', 5),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b14'), 'Buen producto', uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b11'),
        '2021-12-02', 4),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b15'), 'Malo producto', uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b12'),
        '2021-12-03', 2);

insert ignore into shopping_cart(id_shopping_cart, fk_id_user, fk_id_product, amount)
values (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b16'), uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'),
        uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b25'), 1),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b17'), uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6'),
        uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b26'), 2),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b18'), uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'),
        uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b27'), 3);

insert ignore into wish_list (id_wish, amount, fk_id_user, fk_id_product)
values (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b19'), 1, uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'),
        uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b25')),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b20'), 2, uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6'),
        uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b26')),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b21'), 3, uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'),
        uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b27'));

insert ignore into requests_return_product (id_request_return_product, order_has_product, fk_id_status, rejection_reason)
values (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b22'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b10'),
        uuid_to_bin('9f1cb62f-8b57-46d1-95d4-8481e4f1c9e2'), 'No cumple con los requisitos'),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b23'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b11'),
        uuid_to_bin('7c5409f1-64c5-431d-8b4c-e0d60058d100'), 'Cumple con los requisitos'),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b24'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b12'),
        uuid_to_bin('0d64c931-8f3c-4b0c-a16b-dad013cdaeb4'), 'No cumple con los requisitos');

insert ignore into return_product_gallery (id_image, fk_id_request_return_product, image)
values (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b25'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b22'),
        'imagen1'),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b26'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b23'),
        'imagen2'),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b27'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b24'),
        'imagen3');

insert ignore into requests_sell_product(id_request_sell_product, fk_id_product, fk_id_status, rejection_reason)
values (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b28'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b25'),
        uuid_to_bin('9f1cb62f-8b57-46d1-95d4-8481e4f1c9e2'), 'No cumple con los requisitos'),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b29'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b26'),
        uuid_to_bin('7c5409f1-64c5-431d-8b4c-e0d60058d100'), 'Cumple con los requisitos'),
       (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b30'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89b27'),
        uuid_to_bin('0d64c931-8f3c-4b0c-a16b-dad013cdaeb4'), 'No cumple con los requisitos');

