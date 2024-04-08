# drop database if exists clothingshop;
use clothingshop;

-- CATEGORIES
INSERT IGNORE INTO categories (id_category,
                               category,
                               image,
                               status)
VALUES (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd6'),
        'Hombres',
        'https://via.placeholder.com/300x400.png?text=hombres',
        true),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd7'),
        'Mujeres',
        'https://via.placeholder.com/300x400.png?text=mujeres',
        true),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd8'),
        'Niños',
        'https://via.placeholder.com/300x400.png?text=niños',
        true);

-- SUBCATEGORIES
INSERT IGNORE INTO subcategories (id_subcategory,
                                  image,
                                  status,
                                  subcategory,
                                  fk_id_category)
VALUES
    -- HOMBRES
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd9'),
     'https://via.placeholder.com/300.png?text=camisas',
     true,
     'Camisas',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd6')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bda'),
     'https://via.placeholder.com/300.png?text=pantalones',
     true,
     'Pantalones',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd6')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bdb'),
     'https://via.placeholder.com/300.png?text=camisetas',
     true,
     'Camisetas',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd6')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bdc'),
     'https://via.placeholder.com/300.png?text=chaquetas',
     true,
     'Chaquetas',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd6')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bdd'),
     'https://via.placeholder.com/300.png?text=accesorios',
     true,
     'Accesorios',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd6')),
    -- MUJERES
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bde'),
     'https://via.placeholder.com/300.png?text=blusas',
     true,
     'Blusas',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd7')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bdf'),
     'https://via.placeholder.com/300.png?text=faldas',
     true,
     'Faldas',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd7')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be0'),
     'https://via.placeholder.com/300.png?text=pantalones',
     true,
     'Pantalones',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd7')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be1'),
     'https://via.placeholder.com/300.png?text=vestidos',
     true,
     'Vestidos',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd7')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be2'),
     'https://via.placeholder.com/300.png?text=zapatos',
     true,
     'Zapatos',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd7')),
    -- NIÑOS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be3'),
     'https://via.placeholder.com/300.png?text=camisetas',
     true,
     'Camisetas',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd8')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be4'),
     'https://via.placeholder.com/300.png?text=pantalones',
     true,
     'Pantalones',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd8')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be5'),
     'https://via.placeholder.com/300.png?text=disfraces',
     true,
     'Disfraces',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd8')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be6'),
     'https://via.placeholder.com/300.png?text=accesorios',
     true,
     'Accesorios',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd8'));

-- USERS
INSERT IGNORE INTO users
(id_user,
 email,
 email_verified,
 password,
 privacy_policy,
 status,
 verification_code)
VALUES
    -- SUPER ADMIN
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be7'),
     'superadmin@example.com',
     true,
     '$2a$10$9ecu02DGkGrQhl9iEN4uvuCm18oS.9jnqdDBQIzIEj00tm9tjud0S',
     true,
     true,
     'ABCD1234'),
    -- ADMINS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be8'),
     'admin1@example.com',
     true,
     '$2a$10$9ecu02DGkGrQhl9iEN4uvuCm18oS.9jnqdDBQIzIEj00tm9tjud0S',
     true,
     true,
     'ABCD1234'),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be9'),
     'admin2@example.com',
     true,
     '$2a$10$9ecu02DGkGrQhl9iEN4uvuCm18oS.9jnqdDBQIzIEj00tm9tjud0S',
     true,
     true,
     'ABCD1234'),
    -- SELLERS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bea'),
     'seller1@example.com',
     true,
     '$2a$10$9ecu02DGkGrQhl9iEN4uvuCm18oS.9jnqdDBQIzIEj00tm9tjud0S',
     true,
     true,
     'ABCD1234'),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860beb'),
     'seller2@example.com',
     true,
     '$2a$10$9ecu02DGkGrQhl9iEN4uvuCm18oS.9jnqdDBQIzIEj00tm9tjud0S',
     true,
     true,
     'ABCD1234'),
    -- BUYERS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bec'),
     'buyer1@example.com',
     true,
     '$2a$10$9ecu02DGkGrQhl9iEN4uvuCm18oS.9jnqdDBQIzIEj00tm9tjud0S',
     true,
     true,
     'ABCD1234'),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bed'),
     'buyer2@example.com',
     true,
     '$2a$10$9ecu02DGkGrQhl9iEN4uvuCm18oS.9jnqdDBQIzIEj00tm9tjud0S',
     true,
     true,
     'ABCD1234');

-- ROLES
INSERT IGNORE INTO roles
(id_role,
 role_name)
VALUES (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bee'),
        'ROLE_SUPERADMIN'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bef'),
        'ROLE_ADMIN'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf0'),
        'ROLE_SELLER'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf1'),
        'ROLE_BUYER');

-- PEOPLE
INSERT IGNORE INTO people
(id_person,
 birthday,
 gender,
 last_name,
 name,
 phone_number,
 picture,
 second_last_name,
 verification_phone,
 fk_id_user)
VALUES
-- SUPER ADMIN
(UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf2'),
 '1990-01-01',
 'masculino',
 'Bezos',
 'Jeff',
 '1234567890',
 'https://via.placeholder.com/500.png?text=superadmin',
 'Carsi',
 true,
 UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be7')),
-- ADMINS
(UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf3'),
 '1985-07-10',
 'masculino',
 'Gates',
 'Bill',
 '1234567890',
 'https://via.placeholder.com/500.png?text=admin1',
 'Carsi',
 true,
 UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be8')),
(UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf4'),
 '1988-04-09',
 'femenino',
 'Gates',
 'Melinda',
 '1234567890',
 'https://via.placeholder.com/500.png?text=admin2',
 'Carsi',
 true,
 UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be9')),
-- SELLERS
(UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf5'),
 '1968-10-12',
 'masculino',
 'Musk',
 'Elon',
 '1234567890',
 'https://via.placeholder.com/500.png?text=seller1',
 'Carsi',
 true,
 UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bea')),
(UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf6'),
 '1996-05-01',
 'femenino',
 'Musk',
 'Maye',
 '1234567890',
 'https://via.placeholder.com/500.png?text=seller2',
 'Carsi',
 true,
 UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860beb')),
-- BUYERS
(UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf7'),
 '1975-12-05',
 'masculino',
 'Jobs',
 'Steve',
 '1234567890',
 'https://via.placeholder.com/500.png?text=buyer1',
 'Carsi',
 true,
 UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bec')),
(UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf8'),
 '1980-03-15',
 'femenino',
 'Jobs',
 'Eve',
 '1234567890',
 'https://via.placeholder.com/500.png?text=buyer2',
 'Carsi',
 true,
 UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bed'));

-- USER_ROLES
INSERT IGNORE INTO user_roles
(id_user_role,
 fk_id_role,
 fk_id_user)
VALUES
    -- SUPER ADMIN
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf9'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bee'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be7')),
    -- ADMINS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bfa'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bef'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be8')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bfb'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bef'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be9')),
    -- SELLERS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bfc'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf0'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bea')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bfd'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf0'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860beb')),
    -- BUYERS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bda'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf1'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bea')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bdb'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf1'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860beb')),

    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bfe'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf1'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bec')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bff'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf1'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bed'));

-- ADDRESS STATUS
INSERT IGNORE INTO address_status
(id_status,
 status)
VALUES (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c00'),
        'Habilitada'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c01'),
        'Deshabilitada'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c02'),
        'Predeterminada'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c03'),
        'Venta');

-- ADDRESS
INSERT IGNORE INTO address
(id_address,
 address,
 neighborhood,
 postal_code,
 references_address,
 state,
 street,
 fk_id_user,
 fk_id_status) -- references id_status from address_status
VALUES
    -- SUPER ADMIN
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c00'),
     'Mansion Bezos',
     'Polanco',
     '11560',
     'Hay un Oxxo en la esquina',
     'Ciudad de México',
     'Av. Presidente Masaryk',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf2'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c02')),
    -- ADMINS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c01'),
     'Mansion Gates',
     'Roma',
     '06700',
     'Hay un indigente abajo del puente de la esquina',
     'Ciudad de México',
     'Av. Álvaro Obregón',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf3'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c02')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c02'),
     'Mansion Gates',
     'Roma',
     '06700',
     'Hay un indigente abajo del puente de la esquina',
     'Ciudad de México',
     'Av. Álvaro Obregón',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf4'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c02')),
    -- SELLERS
    -- PREDETERMINADAS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c03'),
     'Mansion Musk',
     'Condesa',
     '06140',
     'Hay un Starbucks en la esquina',
     'Ciudad de México',
     'Av. Tamaulipas',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf5'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c02')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c04'),
     'Mansion Musk',
     'Condesa',
     '06140',
     'Hay un Starbucks en la esquina',
     'Ciudad de México',
     'Av. Tamaulipas',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf6'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c02')),
    -- VENTA
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c05'),
     'Tienda de Modas Musky',
     'Tlalnepantla',
     '54000',
     'Hay un perro cojo de tres patas en la esquina',
     'Estado de México',
     'Av. Gustavo Baz',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf5'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c03')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c06'),
     'Cuidado con el Husk',
     'Emiliano Zapata',
     '62760',
     'Hay un señor cojo de tres patas en la esquina',
     'Morelos',
     'Av. Emiliano Zapata',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf6'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c03')),
-- BUYERS
-- PREDETERMINADAS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c07'),
     'Mansion Jobs',
     'Coyoacán',
     '04000',
     'Al sur del hospital',
     'Ciudad de México',
     'Av. Universidad',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf7'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c02')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c08'),
     'Mansion Jobs',
     'Coyoacán',
     '04000',
     'Al sur del hospital',
     'Ciudad de México',
     'Av. Universidad',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf8'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c02')),
    -- HABILITADAS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c09'),
     'Casa Vacacional Jobs',
     'Playa del Carmen',
     '77710',
     'Al lado del hotel',
     'Quintana Roo',
     'Av. Playa del Carmen',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf7'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c00')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c0a'),
     'Apartamento Jobs',
     'Polanco',
     '11560',
     'Al lado del Oxxo',
     'Ciudad de México',
     'Av. Presidente Masaryk',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf8'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c00'));

-- SELLERS INFORMATION
INSERT IGNORE INTO sellers_information
(fk_id_user, -- references id_person from people
 curp,
 image_identification,
 privacy_policy_agreement,
 secondary_phone_number,
 tax_identification_number,
 block_sell)
VALUES
    -- SELLER 1
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf5'),
     'MUEA680101HDFLNS09',
     'https://via.placeholder.com/500x300.png?text=identificacion1',
     true,
     '1234567891',
     'IRMUEA680101HDFLNS09',
     false),
    -- SELLER 2
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bf6'),
     'MUEA960501HDFLNS09',
     'https://via.placeholder.com/500x300.png?text=identificacion2',
     true,
     '1234567892',
     'IRMUEA960501HDFLNS09',
     false);

-- CARD STATUS
INSERT IGNORE INTO card_status
(id_status,
 status)
VALUES (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c0b'),
        'Habilitada'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c0c'),
        'Deshabilitada'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c0d'),
        'Predeterminada');

-- PAYMENT CARDS
INSERT IGNORE INTO payment_cards
(id_payment_card,
 card_number,
 cardholder_name,
 cvv,
 expiration_date,
 fk_id_status,
 fk_id_user)
VALUES
    -- SELLERS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c0e'),
     '1234567890123456',
     'Elon Musk',
     '123',
     '12/25',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c0d'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bea')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c0f'),
     '6543210987654321',
     'Maye Musk',
     '321',
     '06/27',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c0d'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860beb')),
    -- BUYERS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c10'),
     '1234567890123457',
     'Steve Jobs',
     '456',
     '04/26',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c0d'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bec')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c11'),
     '6543210987654322',
     'Eve Jobs',
     '654',
     '03/23',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c0d'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bed'));

-- PRODUCTS
INSERT IGNORE INTO products
(id_product,
 amount,
 description,
 price,
 product_name,
 status,
 fk_id_subcategory,
 fk_id_user)
VALUES
    -- SELLER 1
    -- HOMBRES CAMISAS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c12'),
     8,
     'Camisa de vestir blanca para hombre de manga larga con tela de algodón',
     359.99,
     'Camisa de vestir',
     true,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bd9'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bea')),
    -- HOMBRES PANTALONES
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c13'),
     5,
     'Pantalón cargo verde militar para hombre con bolsas laterales y traseras',
     499.99,
     'Pantalón cargo',
     true,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bda'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bea')),
    -- HOMBRES CAMISETAS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c14'),
     10,
     'Camiseta negra para hombre con cuello redondo y manga corta',
     199.99,
     'Camiseta negra',
     true,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bdb'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bea')),
    -- HOMBRES CHAQUETAS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c15'),
     3,
     'Chamarra de piel negra para hombre con cierre frontal y bolsas laterales',
     999.99,
     'Chamarra de piel',
     true,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bdc'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bea')),
    -- HOMBRES ACCESORIOS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c16'),
     15,
     'Reloj de pulsera para hombre con correa de piel y caja de acero inoxidable',
     799.99,
     'Reloj de pulsera',
     true,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bdd'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bea')),
    -- SELLER 2
    -- MUJERES BLUSAS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c17'),
     8,
     'Blusa de tirantes color rosa para mujer con cuello en V y tela de algodón',
     259.99,
     'Blusa rosa',
     true,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bde'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860beb')),
    -- MUJERES FALDAS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c18'),
     5,
     'Falda corta color azul marino para mujer con cintura elástica y tela de poliéster',
     399.99,
     'Falda azul',
     true,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bdf'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860beb')),
    -- MUJERES PANTALONES
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c19'),
     10,
     'Pantalón de mezclilla color azul para mujer con corte recto y bolsas laterales',
     499.99,
     'Pantalón de mezclilla',
     true,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be0'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860beb')),
    -- MUJERES VESTIDOS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1a'),
     3,
     'Vestido largo color morado para mujer con cuello en V y manga corta',
     799.99,
     'Vestido morado',
     true,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be1'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860beb')),
-- MUJERES ZAPATOS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1b'),
     15,
     'Zapatos de tacón color café para mujer con punta redonda y tacón de 10 cm',
     899.99,
     'Zapatos de tacón',
     true,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be2'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860beb')),
    -- NIÑOS CAMISETAS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1c'),
     8,
     'Camiseta de manga corta color azul para niño con estampado de dinosaurio',
     199.99,
     'Camiseta de dinosaurio',
     true,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be3'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860beb')),
    -- NIÑOS PANTALONES
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1d'),
     5,
     'Pantalón de mezclilla color azul para niño con corte recto y bolsas laterales',
     299.99,
     'Pantalón de mezclilla',
     true,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be4'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860beb')),
    -- NIÑOS DISFRACES
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1e'),
     10,
     'Disfraz de superhéroe para niño con capa y antifaz',
     399.99,
     'Disfraz de superhéroe',
     true,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be5'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860beb')),
    -- NIÑOS ACCESORIOS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1f'),
     15,
     'Mochila escolar para niño con estampado de dinosaurios y bolsas laterales',
     299.99,
     'Mochila de dinosaurios',
     true,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860be6'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860beb'));

-- IMAGE PRODUCT STATUS
INSERT IGNORE INTO image_product_status
(id_status,
 status)
VALUES (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c20'),
        'Habilitada'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c21'),
        'Deshabilitada'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c22'),
        'Principal');

-- PRODUCT GALLERY
INSERT IGNORE INTO product_gallery
(id_image,
 image,
 fk_id_product,
 fk_id_status)
VALUES
    -- SELLER 1
    -- CAMISA
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c23'),
     'https://via.placeholder.com/500.png?text=camisa1',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c12'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c22')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c24'),
     'https://via.placeholder.com/500.png?text=camisa2',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c12'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c20')),
    -- PANTALON
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c25'),
     'https://via.placeholder.com/500.png?text=pantalon1',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c13'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c22')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c26'),
     'https://via.placeholder.com/500.png?text=pantalon2',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c13'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c20')),
    -- CAMISETA
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c27'),
     'https://via.placeholder.com/500.png?text=camiseta1',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c14'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c22')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c28'),
     'https://via.placeholder.com/500.png?text=camiseta2',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c14'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c20')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c99'),
     'https://via.placeholder.com/500.png?text=camiseta3',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c14'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c20')),
    -- CHAMARRA
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c29'),
     'https://via.placeholder.com/500.png?text=chamarra1',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c15'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c22')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c2a'),
     'https://via.placeholder.com/500.png?text=chamarra2',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c15'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c20')),
    -- ACCESORIO
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c2b'),
     'https://via.placeholder.com/500.png?text=reloj1',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c16'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c22')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c2c'),
     'https://via.placeholder.com/500.png?text=reloj2',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c16'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c20')),
    -- SELLER 2
    -- BLUSA
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c2d'),
     'https://via.placeholder.com/500.png?text=blusa1',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c17'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c22')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c2e'),
     'https://via.placeholder.com/500.png?text=blusa2',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c17'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c20')),
    -- FALDA
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c2f'),
     'https://via.placeholder.com/500.png?text=falda1',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c18'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c22')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c30'),
     'https://via.placeholder.com/500.png?text=falda2',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c18'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c20')),
    -- PANTALON
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c31'),
     'https://via.placeholder.com/500.png?text=pantalon1',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c19'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c22')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c32'),
     'https://via.placeholder.com/500.png?text=pantalon2',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c19'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c20')),
    -- VESTIDO
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c33'),
     'https://via.placeholder.com/500.png?text=vestido1',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1a'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c22')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c34'),
     'https://via.placeholder.com/500.png?text=vestido2',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1a'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c20')),
    -- ZAPATOS
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c35'),
     'https://via.placeholder.com/500.png?text=zapatos1',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1b'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c22')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c36'),
     'https://via.placeholder.com/500.png?text=zapatos2',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1b'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c20')),
    -- NIÑOS CAMISETA
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c37'),
     'https://via.placeholder.com/500.png?text=camiseta1',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1c'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c22')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c38'),
     'https://via.placeholder.com/500.png?text=camiseta2',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1c'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c20')),
    -- NIÑOS PANTALON
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c39'),
     'https://via.placeholder.com/500.png?text=pantalon1',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1d'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c22')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c3a'),
     'https://via.placeholder.com/500.png?text=pantalon2',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1d'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c20')),
    -- NIÑOS DISFRAZ
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c3b'),
     'https://via.placeholder.com/500.png?text=disfraz1',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1e'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c22')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c3c'),
     'https://via.placeholder.com/500.png?text=disfraz2',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1e'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c20')),
    -- NIÑOS ACCESORIO
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c3d'),
     'https://via.placeholder.com/500.png?text=mochila1',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1f'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c22')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c3e'),
     'https://via.placeholder.com/500.png?text=mochila2',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1f'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c20'));

-- WISH LIST
INSERT IGNORE INTO wish_list
(id_wish,
 amount,
 fk_id_product,
 fk_id_user)
VALUES
    -- BUYER 1
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c3f'),
     1,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c12'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bec')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c40'),
     2,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c13'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bec')),
    -- BUYER 2
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c41'),
     3,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c17'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bed')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c42'),
     2,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c18'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bed'));

-- SHOPPING CART
INSERT IGNORE INTO shopping_cart
(id_shopping_cart,
 amount,
 fk_id_product,
 fk_id_user)
VALUES
    -- BUYER 1
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c43'),
     2,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c14'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bec')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c44'),
     1,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c15'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bec')),
    -- BUYER 2
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c45'),
     1,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c19'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bed')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c46'),
     2,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1a'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bed'));

-- REQUEST STATUS
INSERT IGNORE INTO request_status
(id_status,
 status)
VALUES (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c47'),
        'Pendiente'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c48'),
        'Aprobado'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c49'),
        'Rechazado'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c4a'),
        'Cancelado');

-- REQUESTS BECOME SELLER
INSERT IGNORE INTO requests_become_seller
(id_request_become_seller,
 rejection_reason,
 user_seller_information,
 fk_id_status,
 fk_id_user)
VALUES (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c4b'),
        '',
        '{
          "curp": "MUEA680101HDFLNS09",
          "imageIdentification": "https://via.placeholder.com/500x300.png?text=identificacion1",
          "privacyPolicyAgreement": true,
          "secondaryPhoneNumber": "1234567891",
          "taxIdentificationNumber": "IRMUEA680101HDFLNS09"
        }',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c48'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bea')),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c4c'),
        '',
        '{
          "curp": "MUEA960501HDFLNS09",
          "imageIdentification": "https://via.placeholder.com/500x300.png?text=identificacion2",
          "privacyPolicyAgreement": true,
          "secondaryPhoneNumber": "1234567892",
          "taxIdentificationNumber": "IRMUEA960501HDFLNS09"
        }',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c48'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860beb')),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c4d'),
        'El CURP proporcionado no coincide con la identificación oficial',
        '{
          "curp": "MVEA960501HDFLNS09",
          "imageIdentification": "https://via.placeholder.com/500x300.png?text=identificacion2",
          "privacyPolicyAgreement": true,
          "secondaryPhoneNumber": "1234567892",
          "taxIdentificationNumber": "IRMUEA960501HDFLNS09"
        }',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c49'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bed'));

-- REQUESTS DATA CHANGE
INSERT IGNORE INTO requests_data_change
(id_request_data_change,
 new_user_information,
 rejection_reason,
 fk_id_status,
 fk_id_user)
VALUES (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c4e'),
        '{
          "secondary_phone_number": "1234567899"
        }',
        '',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c47'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860bea'));

-- REQUESTS SELL PRODUCT
INSERT IGNORE INTO requests_sell_product
(id_request_sell_product,
 rejection_reason,
 fk_id_product,
 fk_id_status)
VALUES (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c4f'),
        '',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1e'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c47')),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c50'),
        '',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1f'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c47')),
       -- PRODUCTS ALREADY REGISTERED BEFORE
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3862c51'),
        '',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c12'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c48')),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3862c52'),
        '',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c13'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c48')),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3862c53'),
        '',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c14'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c48')),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3862c54'),
        '',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c15'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c48')),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3862c55'),
        '',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c16'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c48')),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3862c56'),
        '',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c17'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c48')),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3862c57'),
        '',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c18'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c48')),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3862c58'),
        '',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c19'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c48')),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3862c59'),
        '',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1a'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c48')),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3862c60'),
        '',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1b'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c48')),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3862c61'),
        '',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1c'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c48')),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3862c62'),
        '',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1d'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c48'));

-- ORDERS
INSERT IGNORE INTO orders
(id_order,
 order_date,
 order_number, -- 16 alfanumeric
 fk_id_address,
 fk_id_payment_card)
VALUES
    -- BUYER 1
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c51'),
     '2024-01-01',
     'A1B2C3D4E5F6G7H8',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c07'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c10')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c52'),
     '2024-01-02',
     'H8G7F6E5D4C3B2A1',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c09'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c10')),
    -- BUYER 2
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c53'),
     '2024-01-03',
     '1A2B3C4D5E6F7G8H',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c08'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c11')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c54'),
     '2024-01-04',
     'H7G6F5E4D3C2B1A',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c0a'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c11'));

-- ORDER STATUS
INSERT IGNORE INTO order_status
(id_status,
 status)
VALUES (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c55'),
        'Preparación'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c56'),
        'Enviado'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c57'),
        'Entregado'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c58'),
        'Cancelado'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c59'),
        'Reembolsado'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3960c5a'),
        'Sin pagar');

-- ORDERS HAS PRODUCTS
INSERT IGNORE INTO orders_has_products
(id_order_product,
 amount,
 fk_id_order,
 fk_id_product,
 fk_id_status)
VALUES
    -- BUYER 1
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c5a'),
     1,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c51'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1b'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c55')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c5b'),
     2,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c51'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c19'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c55')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c5c'),
     3,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c52'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c16'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c57')),
    -- BUYER 2
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c88'),
     3,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c53'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c15'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c56')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c5d'),
     2,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c53'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c12'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c56')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c5e'),
     1,
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c54'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c1c'),
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c59'));

-- REVIEWS
INSERT IGNORE INTO reviews
(id_review,
 assessment,
 comment,
 review_date,
 fk_id_order_product)
VALUES
    -- BUYER 1
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c5f'),
     5,
     'Excelente producto, muy buena calidad',
     '2024-01-02',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c5a')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c60'),
     4,
     'Buena calidad, pero tardó un poco en llegar',
     '2024-01-03',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c5b')),
    -- BUYER 2
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c61'),
     3,
     'No era lo que esperaba, pero está bien',
     '2024-01-04',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c5c')),
    (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c62'),
     2,
     'No me gustó, no era lo que esperaba',
     '2024-01-05',
     UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c5d'));

-- REQUESTS RETURN PRODUCT
INSERT IGNORE INTO requests_return_product
(id_request_return_product,
 rejection_reason,
 order_has_product,
 fk_id_status)
VALUES (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c63'),
        '',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c5e'),
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c48'));

-- RETURN PRODUCT GALLERY
INSERT IGNORE INTO return_product_gallery
(id_image,
 image,
 fk_id_request_return_product)
VALUES (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c64'),
        'https://via.placeholder.com/500.png?text=devolucion1',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c63')),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c65'),
        'https://via.placeholder.com/500.png?text=devolucion2',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c63')),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c66'),
        'https://via.placeholder.com/500.png?text=devolucion3',
        UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c63'));

INSERT IGNORE INTO transaction_status
(id_status,
 status)
VALUES (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c67'),
        'Pendiente'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c68'),
        'Pagado'),
       (UUID_TO_BIN('ed08f0eb-ec02-11ee-b34f-d85ed3860c69'),
        'Cancelado');