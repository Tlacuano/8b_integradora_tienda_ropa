use clothingshop;

insert into roles (id_role, role_name) values
                                           (uuid_to_bin('0f8fad5b-d9cb-469f-a165-70867728950e'), 'administrador'),
                                           (uuid_to_bin('1ff1e570-3b33-4e1f-a9d0-5fd500e4ac9c'), 'vendedor'),
                                           (uuid_to_bin('58c0d70e-8d7b-47e3-bfa9-6f150c0b5c6e'), 'comprador');

insert into users (id_user, password, email, verification_code, status) values
                                                                            (uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'), 'contraseña123', 'usuario1@example.com', 'ABCD1234', 'activo'),
                                                                            (uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6'), 'password456', 'usuario2@example.com', 'WXYZ5678', 'inactivo'),
                                                                            (uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'), 'pass789', 'usuario3@example.com', '1234EFGH', 'activo');
insert into users_roles (id_user_role, fk_id_role, fk_id_user) values
                                                                   (uuid_to_bin('65ac0dd9-4b5d-4b51-9475-4e397837582d'), uuid_to_bin('0f8fad5b-d9cb-469f-a165-70867728950e'), uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4')),
                                                                   (uuid_to_bin('bc76463b-c45e-4f43-8d11-d878d17318a4'), uuid_to_bin('1ff1e570-3b33-4e1f-a9d0-5fd500e4ac9c'), uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6')),
                                                                   (uuid_to_bin('8f12b03b-3d71-4397-ba2d-8885cfd9a5e9'), uuid_to_bin('58c0d70e-8d7b-47e3-bfa9-6f150c0b5c6e'), uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'));

insert into request_data_change (id_request_data_change, fk_id_user, fk_id_status, new_user_information, rejection_reason) values
                                                                                                                               (uuid_to_bin('c53f84bb-94ae-48b7-91de-ae297e0b836d'), uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'), uuid_to_bin('9f1cb62f-8b57-46d1-95d4-8481e4f1c9e2'), 'Nueva información', NULL),
                                                                                                                               (uuid_to_bin('3f4ad6e7-cf3f-458a-af3c-7d7df21eb28b'), uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6'), uuid_to_bin('7c5409f1-64c5-431d-8b4c-e0d60058d100'), 'Detalles actualizados', 'Información inválida proporcionada'),
                                                                                                                               (uuid_to_bin('9908d1d0-4875-41f4-9a1f-1ab96191e88a'), uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'), uuid_to_bin('0d64c931-8f3c-4b0c-a16b-dad013cdaeb4'), 'Datos corregidos', NULL);

insert into address_status (id_status, status) values
    (uuid_to_bin('3b43b9d0-9f46-49fe-ae14-e69b8d1ca821'), 'habilitadas');

insert into address (id_address, address, references_address, postal_code, state, street, neighborhood, fk_id_status, fk_id_user) values
    (uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be2'), 'Avenida Juárez 123', 'Frente a la plaza principal', '01000', 'Ciudad de México', 'Juárez', 'Centro', uuid_to_bin('3b43b9d0-9f46-49fe-ae14-e69b8d1ca821'), uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'));

insert into sellers_information (id_seller_info, fk_id_user, tax_identification_number, secondary_phone_number, privacy_policy_agreement, image_identification, curp) values
                                                                                                                                                                          (uuid_to_bin('3e5f39a7-4a56-4b5c-a2c8-cb5898b10c28'), uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'), 'RFC123456789', '555-555-5555', 1, 'image1.jpg', 'ABCD123456EFGHAA01'),
                                                                                                                                                                          (uuid_to_bin('14c21e77-0b3e-4b0e-b07e-d9ef183b89c4'), uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6'), 'RFC987654321', '444-444-4444', 0, 'image2.jpg', 'XYZW987654LKJHA02'),
                                                                                                                                                                          (uuid_to_bin('5a58b3d1-0464-42fc-92ff-25601e04ef30'), uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'), 'RFC456789123', '666-666-6666', 1, 'image3.jpg', 'PQRS456789MNOPA03');

insert into person (id_person, fk_id_user, name, last_name, second_last_name, picture, birthday, phone_number, gender) values
                                                                                                                           (uuid_to_bin('0185e062-b6a0-451b-9a21-17815b9cf478'), uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'), 'Juan', 'Pérez', 'García', 'juan.jpg', '1990-05-15', '555-555-5555', 'M'),
                                                                                                                           (uuid_to_bin('29b832b0-1c2b-42a5-b365-13a8fbbadbb1'), uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6'), 'María', 'González', 'López', 'maria.jpg', '1985-12-10', '444-444-4444', 'F'),
                                                                                                                           (uuid_to_bin('8ec5e1a0-95d7-4c34-8966-19a416c5b734'), uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'), 'José', 'Martínez', 'Hernández', 'jose.jpg', '1995-08-20', '666-666-6666', 'M');

insert into request_become_seller (id_request_become_seller, fk_id_user, fk_id_status, rejection_reason) values
                                                                                                             (uuid_to_bin('2e35c366-1d70-4398-aac7-c715f8e3b485'), uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'), uuid_to_bin('9f1cb62f-8b57-46d1-95d4-8481e4f1c9e2'), NULL),
                                                                                                             (uuid_to_bin('bd3dfe49-3c90-4e79-a94d-d9f60cf420e1'), uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6'), uuid_to_bin('7c5409f1-64c5-431d-8b4c-e0d60058d100'), 'Documentación insuficiente'),
                                                                                                             (uuid_to_bin('01ec7693-0536-4ccf-8a78-9e703ba0ab8d'), uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'), uuid_to_bin('9f1cb62f-8b57-46d1-95d4-8481e4f1c9e2'), NULL);

insert into orders (id_order, order_date, order_number, fk_id_payment, fk_id_address) values
                                                                                          (uuid_to_bin('71d43af4-b1b4-41a9-aa8e-fd08d0047e05'), '2024-03-07', 'ORD123456', uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'), uuid_to_bin('58d09dec-94b3-4db7-a60c-abb986a89be2')),
                                                                                          (uuid_to_bin('8748d44b-e413-4426-8e7f-8a33203da61c'), '2024-03-07', 'ORD789012', uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6'), uuid_to_bin('b93b4405-91c5-40a4-a2b3-169d8b7f171a')),
                                                                                          (uuid_to_bin('20fbab9a-2d2f-4c0f-8b5f-7fdd1a1c9d3b'), '2024-03-07', 'ORD345678', uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'), uuid_to_bin('c747e758-20b0-4b63-802e-d7596a11a18f'));

insert into order_status (id_status, status) values
                                                 (uuid_to_bin('9b8c35d0-7870-41c6-8719-745eaaae1a13'), 'Pendiente'),
                                                 (uuid_to_bin('85fe19ab-12ae-4b24-ae49-74789cfd1441'), 'Completado'),
                                                 (uuid_to_bin('70b6d88b-8f67-4d45-bc9b-d41813c3b5c7'), 'Cancelado');
insert into orders_has_products (id_order_product, fk_id_order, fk_id_product, amount, fk_id_status) values
                                                                                                         (uuid_to_bin('7dbcf1f3-6046-4935-83a2-ec0bca86b546'), uuid_to_bin('71d43af4-b1b4-41a9-aa8e-fd08d0047e05'), 201, 5, uuid_to_bin('9b8c35d0-7870-41c6-8719-745eaaae1a13')),
                                                                                                         (uuid_to_bin('1042dc29-3642-4dbb-b6f6-68e5de94853b'), uuid_to_bin('8748d44b-e413-4426-8e7f-8a33203da61c'), 202, 3, uuid_to_bin('85fe19ab-12ae-4b24-ae49-74789cfd1441')),
                                                                                                         (uuid_to_bin('ebf41c7d-39b5-4b14-8d09-ec5c4e28d5f0'), uuid_to_bin('20fbab9a-2d2f-4c0f-8b5f-7fdd1a1c9d3b'), 303, 2, uuid_to_bin('70b6d88b-8f67-4d45-bc9b-d41813c3b5c7'));



insert into reviews (id_review, fk_id_order_product, comment, review_date, assessment) values
                                                                                           (uuid_to_bin('7b66008a-1508-4890-9037-693826803422'), uuid_to_bin('7dbcf1f3-6046-4935-83a2-ec0bca86b546'), '¡Gran producto, envío rápido!', '2024-03-07', 5),
                                                                                           (uuid_to_bin('9b2cd5bb-14a2-432d-85f6-4c7a398e1773'), uuid_to_bin('1042dc29-3642-4dbb-b6f6-68e5de94853b'), 'La calidad del producto estaba bien, la entrega se retrasó.', '2024-03-07', 3),
                                                                                           (uuid_to_bin('7759d1f6-3e53-4cfc-83fc-8d4ae0d0127e'), uuid_to_bin('ebf41c7d-39b5-4b14-8d09-ec5c4e28d5f0'), 'Decepcionado con el producto, mala calidad.', '2024-03-07', 2);

insert into products (id_product, product_name, description, price, amount, fk_id_user, fk_id_subcategory, status) values
                                                                                                                       (uuid_to_bin('b61e5aa1-1987-4791-8cfd-9984ac74712e'), 'Producto A', 'Descripción del Producto A', 50.99, 100, uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'), uuid_to_bin('9e9df4fd-036d-48b1-85e8-4f8f4389f1d4'), 'Activo'),
                                                                                                                       (uuid_to_bin('9bce2650-8b17-4a8b-a1e8-73c8f185a849'), 'Producto B', 'Descripción del Producto B', 29.99, 50, uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6'), uuid_to_bin('80c49d95-7edf-4475-91b6-74ef025d2c15'), 'Inactivo'),
                                                                                                                       (uuid_to_bin('120c50c5-c6d1-4e79-b1b0-dfd2ae2e8624'), 'Producto C', 'Descripción del Producto C', 99.99, 200, uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'), uuid_to_bin('1e2029f0-25b3-4954-bfb7-17a778f12612'), 'Activo');

insert into product_gallery (id_image, fk_id_product, image, fk_id_status) values
                                                                               (uuid_to_bin('d7007f99-94ef-45d1-a84c-6bc4be54f1c9'), uuid_to_bin('b61e5aa1-1987-4791-8cfd-9984ac74712e'), 'imagen1.jpg', uuid_to_bin('bc43ef1a-13a2-4b67-bb13-f1d376e5b510')),
                                                                               (uuid_to_bin('c2ed55ee-6013-4c17-bc3e-ef2bca0b5f5b'), uuid_to_bin('9bce2650-8b17-4a8b-a1e8-73c8f185a849'), 'imagen2.jpg', uuid_to_bin('bc43ef1a-13a2-4b67-bb13-f1d376e5b510')),
                                                                               (uuid_to_bin('bb5e47ab-3cf2-4296-b46b-ba03abbe7129'), uuid_to_bin('120c50c5-c6d1-4e79-b1b0-dfd2ae2e8624'), 'imagen3.jpg', uuid_to_bin('2f4b3cb9-e1d8-4cf9-8e16-5e6208bc7e20'));

insert into image_product_status (id_status, status) values
                                                         (uuid_to_bin('bc43ef1a-13a2-4b67-bb13-f1d376e5b510'), 'Activo'),
                                                         (uuid_to_bin('2f4b3cb9-e1d8-4cf9-8e16-5e6208bc7e20'), 'Inactivo');

insert into wish_lists (id_wish, amount, fk_id_user, fk_id_product) values
                                                                        (uuid_to_bin('6c3b0da6-748b-4c69-b0c0-e890f190eaca'), 1, uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'), uuid_to_bin('b61e5aa1-1987-4791-8cfd-9984ac74712e')),
                                                                        (uuid_to_bin('8b3e4dc5-79c3-4ef3-89bc-8f2210b79f13'), 2, uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6'), uuid_to_bin('9bce2650-8b17-4a8b-a1e8-73c8f185a849')),
                                                                        (uuid_to_bin('a8c90b51-02d2-462d-8a12-b52bf48a7b3a'), 1, uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'), uuid_to_bin('120c50c5-c6d1-4e79-b1b0-dfd2ae2e8624'));

insert into shopping_cart (id_shopping, fk_id_user, fk_id_product, amount) values
                                                                               (uuid_to_bin('1dd809ed-90f1-44df-90b9-7f26e4da0fcf'), uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'), uuid_to_bin('b61e5aa1-1987-4791-8cfd-9984ac74712e'), 2),
                                                                               (uuid_to_bin('fa4f0809-bc19-403f-8ef5-4e24a5cf4900'), uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6'), uuid_to_bin('9bce2650-8b17-4a8b-a1e8-73c8f185a849'), 1),
                                                                               (uuid_to_bin('90e5a223-3c7f-4d76-b42e-d7e1b04a14c2'), uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'), uuid_to_bin('120c50c5-c6d1-4e79-b1b0-dfd2ae2e8624'), 3);

insert into subcategories (id_subcategory, fk_id_category, subcategory, image, status) values
                                                                                           (uuid_to_bin('9e9df4fd-036d-48b1-85e8-4f8f4389f1d4'), uuid_to_bin('0f8fad5b-d9cb-469f-a165-70867728950e'), 'Subcategoría A', 'imagen_subcategoria_a.jpg', 'Activo'),
                                                                                           (uuid_to_bin('80c49d95-7edf-4475-91b6-74ef025d2c15'), uuid_to_bin('1ff1e570-3b33-4e1f-a9d0-5fd500e4ac9c'), 'Subcategoría B', 'imagen_subcategoria_b.jpg', 'Inactivo'),
                                                                                           (uuid_to_bin('1e2029f0-25b3-4954-bfb7-17a778f12612'), uuid_to_bin('58c0d70e-8d7b-47e3-bfa9-6f150c0b5c6e'), 'Subcategoría C', 'imagen_subcategoria_c.jpg', 'Activo');

insert into categories (id_category, category, image, status) values
                                                                  (uuid_to_bin('3cc47b97-5728-4c81-92bb-3880f8c27e12'), 'Categoría A', 'imagen_categoria_a.jpg', 'Activo'),
                                                                  (uuid_to_bin('8367843c-47f7-4f12-af6b-2267f6819969'), 'Categoría B', 'imagen_categoria_b.jpg', 'Inactivo'),
                                                                  (uuid_to_bin('a4a4d8aa-5e1d-45a3-84e6-63f1708377d0'), 'Categoría C', 'imagen_categoria_c.jpg', 'Activo');

insert into requests_return_product (id_request_return_product, id_orden_product, fk_id_status, rejection_reason) values
                                                                                                                      (uuid_to_bin('3b5e4726-c660-490e-99c4-22a3c69fc4e7'), uuid_to_bin('7dbcf1f3-6046-4935-83a2-ec0bca86b546'), uuid_to_bin('9f1cb62f-8b57-46d1-95d4-8481e4f1c9e2'), NULL),
                                                                                                                      (uuid_to_bin('7cbe4b13-4327-4084-bc3b-cf5fcb96334f'), uuid_to_bin('1042dc29-3642-4dbb-b6f6-68e5de94853b'), uuid_to_bin('7c5409f1-64c5-431d-8b4c-e0d60058d100'), 'Producto incorrecto recibido'),
                                                                                                                      (uuid_to_bin('ed89d4b1-82cd-4c89-8a26-ebeaa06ef16f'),uuid_to_bin('ebf41c7d-39b5-4b14-8d09-ec5c4e28d5f0') , uuid_to_bin('0d64c931-8f3c-4b0c-a16b-dad013cdaeb4'), NULL);

insert into return_product_gallery (id_image, id_request, image) values
                                                                     (uuid_to_bin('e05d2413-1883-4380-a83b-bc5c8eb29251'), uuid_to_bin('3b5e4726-c660-490e-99c4-22a3c69fc4e7'), 'imagen_devolucion1.jpg'),
                                                                     (uuid_to_bin('a030e3e5-0a89-44a7-bac4-5e3a5b4ac994'),uuid_to_bin('7cbe4b13-4327-4084-bc3b-cf5fcb96334f') , 'imagen_devolucion2.jpg'),
                                                                     (uuid_to_bin('4ecbe0d3-5ef6-4a28-a17a-6118c9457f08'), uuid_to_bin('ed89d4b1-82cd-4c89-8a26-ebeaa06ef16f'), 'imagen_devolucion3.jpg');

insert into request_status (id_status, status) values
                                                   (uuid_to_bin('9f1cb62f-8b57-46d1-95d4-8481e4f1c9e2'), 'Pendiente'),
                                                   (uuid_to_bin('7c5409f1-64c5-431d-8b4c-e0d60058d100'), 'Procesado'),
                                                   (uuid_to_bin('0d64c931-8f3c-4b0c-a16b-dad013cdaeb4'), 'Cancelado');

insert into request_sell_product (id_request_sell_product, fk_id_product, fk_id_status, rejection_reason) values
                                                                                                              (uuid_to_bin('e0d6a725-1a47-4707-92b8-196a2dfc7a89'), uuid_to_bin('b61e5aa1-1987-4791-8cfd-9984ac74712e'), uuid_to_bin('9f1cb62f-8b57-46d1-95d4-8481e4f1c9e2'), NULL),
                                                                                                              (uuid_to_bin('ceee63ae-05c9-48f2-99d1-f4bbf8d6b5ae'), uuid_to_bin('9bce2650-8b17-4a8b-a1e8-73c8f185a849'), uuid_to_bin('7c5409f1-64c5-431d-8b4c-e0d60058d100'), 'Inventario insuficiente'),
                                                                                                              (uuid_to_bin('7a847017-f7a3-4676-bf11-8e2c41b91956'), uuid_to_bin('120c50c5-c6d1-4e79-b1b0-dfd2ae2e8624'), uuid_to_bin('0d64c931-8f3c-4b0c-a16b-dad013cdaeb4'), NULL);

-- Tabla: payment_cards
insert into payment_cards (id_payment, fk_id_user, cardholder_name, card_number, card_expiration_date, card_cvv, fk_id_status) values
                                                                                                                                   (uuid_to_bin('98239d12-cb2b-4690-87c5-3c6e8d4a8e1f'), uuid_to_bin('64327ac8-6bd5-47b4-b9a3-25b3438f6ec4'), 'John Doe', '1234 5678 9012 3456', '12/25', '123', uuid_to_bin('9f1cb62f-8b57-46d1-95d4-8481e4f1c9e2')),
                                                                                                                                   (uuid_to_bin('b04882aa-9d1c-4a11-b3bb-145edc7f05fd'), uuid_to_bin('5e56a7ab-7e5d-4f7d-b2de-42b3cc8d74f6'), 'Jane Smith', '9876 5432 1098 7654', '06/23', '456', uuid_to_bin('7c5409f1-64c5-431d-8b4c-e0d60058d100')),
                                                                                                                                   (uuid_to_bin('fdb0dbd5-5254-4ad1-a011-e0c5c1162174'), uuid_to_bin('e53cf28d-90b9-4c5b-9a2e-f0f343e8e42f'), 'Alice Johnson', '4567 8901 2345 6789', '09/24', '789', uuid_to_bin('0d64c931-8f3c-4b0c-a16b-dad013cdaeb4');

-- Tabla: card_status
insert into card_status (id_status, status) values
                                                (UUID_TO_BIN('1a14b8df-8269-4484-ba7d-1441f1e1d148'), 'Activo'),
                                                (UUID_TO_BIN('efade8fb-6465-4e48-8a0a-146fca51b59d'), 'Inactivo');






