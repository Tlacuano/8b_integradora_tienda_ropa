DROP PROCEDURE IF EXISTS `sp_post_role_user`;
DELIMITER $$
CREATE PROCEDURE `sp_post_role_user`(
    IN p_role_id VARCHAR(255),
    IN p_user_id VARCHAR(255))
BEGIN
    INSERT INTO user_roles(id_user_role, fk_id_role, fk_id_user)
    VALUES (UUID_TO_BIN(UUID()), UUID_TO_BIN(p_role_id), UUID_TO_BIN(p_user_id));

    SELECT status FROM users WHERE id_user = UUID_TO_BIN(p_user_id);
END $$
DELIMITER ;


-- procedute to logicall delete a user
DROP PROCEDURE IF EXISTS `sp_delete_user`;
DELIMITER $$
CREATE PROCEDURE `sp_delete_user`(
    IN p_email VARCHAR(255))
BEGIN
    DECLARE v_user_id BINARY(16);
    DECLARE result VARCHAR(255);


    SELECT id_user INTO v_user_id FROM users WHERE email = p_email;

    UPDATE users
    SET password = '',
        email    = CONCAT(p_email, '_deleted_', UUID()),
        status   = 0
    WHERE id_user = v_user_id;

    UPDATE people
    SET name             = 'Cuenta no disponible',
        last_name        = '',
        second_last_name = '',
        picture          = '',
        gender           = 'otros',
        phone_number     = ''
    WHERE fk_id_user = v_user_id;

    UPDATE sellers_information
    SET curp                      = '',
        tax_identification_number = '',
        secondary_phone_number    = '',
        image_identification      = '',
        privacy_policy_agreement  = 0
    WHERE fk_id_user = v_user_id;

    UPDATE payment_cards
    SET cvv             = '',
        card_number     = '',
        cardholder_name = '',
        expiration_date = ''
    WHERE fk_id_user = v_user_id;

    UPDATE products
    SET status = 0
    WHERE fk_id_user = v_user_id;

    SET result = 'Usuario eliminado';
    SELECT result;

END$$
DELIMITER ;


-- Procedure to change the status of a payment card
DROP PROCEDURE IF EXISTS `sp_put_payment_card_status`;
DELIMITER $$
CREATE PROCEDURE IF NOT EXISTS `sp_put_payment_card_status`(
    IN p_payment_card_id VARCHAR(255),
    IN p_status VARCHAR(15)
)
BEGIN
    DECLARE v_user_id BINARY(16);
    DECLARE v_total_payment_cards INT;
    DECLARE v_status_id BINARY(16);
    DECLARE v_default_payment_card_id BINARY(16);
    START TRANSACTION;

    -- 1. Verify if the payment card exists
    SELECT COUNT(*)
    INTO v_total_payment_cards
    FROM payment_cards
    WHERE id_payment_card = UUID_TO_BIN(p_payment_card_id);

    IF v_total_payment_cards = 0 THEN
        SELECT 'Payment card does not exist' AS error_message;
        ROLLBACK;
    ELSE
        -- 2. Get the user id of the payment card
        SELECT fk_id_user
        INTO v_user_id
        FROM payment_cards
        WHERE id_payment_card = UUID_TO_BIN(p_payment_card_id);

        -- 3. Verify if the status exists
        SELECT id_status
        INTO v_status_id
        FROM card_status
        WHERE status = p_status;

        IF v_status_id IS NULL THEN
            SELECT 'Status does not exist' AS error_message;
            ROLLBACK;
        ELSE
            -- 4. Verify if the user has at least one payment card with the status default
            SELECT id_payment_card
            INTO v_default_payment_card_id
            FROM payment_cards pc
                     JOIN card_status cs ON pc.fk_id_status = cs.id_status
            WHERE pc.fk_id_user = v_user_id
              AND cs.status = 'Predeterminada';

            IF UUID_TO_BIN(p_payment_card_id) = v_default_payment_card_id THEN
                SELECT 'You can not change the status of the default payment card' AS error_message;
                ROLLBACK;
            ELSE
                IF p_status = 'Predeterminada' AND v_default_payment_card_id IS NOT NULL THEN
                    -- 5. Update the status of the payment card
                    UPDATE payment_cards
                    SET fk_id_status = v_status_id
                    WHERE id_payment_card = UUID_TO_BIN(p_payment_card_id);
                    -- 6. Update the status of the payment card that was default
                    UPDATE payment_cards
                    SET fk_id_status = (SELECT id_status FROM card_status WHERE status = 'Habilitada')
                    WHERE id_payment_card = v_default_payment_card_id;
                    SELECT 'El estado de la tarjeta fue actualizado' AS message;
                    COMMIT;
                ELSE
                    -- 6. Update the status of the payment card
                    UPDATE payment_cards
                    SET fk_id_status = v_status_id
                    WHERE id_payment_card = UUID_TO_BIN(p_payment_card_id);
                    SELECT 'El estado de la tarjeta fue actualizado' AS message;
                    COMMIT;
                END IF;
            END IF;
        END IF;
    END IF;
END $$
DELIMITER ;

-- Procedure to insert a new order
DROP PROCEDURE IF EXISTS `sp_post_order`;
DELIMITER $$
CREATE PROCEDURE `sp_post_order`(
    IN p_user_id VARCHAR(255),
    IN p_order_date DATE,
    IN p_order_id_address VARCHAR(255),
    IN p_order_id_payment_card VARCHAR(255),
    IN p_order_number VARCHAR(100)
)
BEGIN
    DECLARE v_order_id BINARY(16);
    DECLARE total_products_in_shopping_cart INT;
    DECLARE products_from_same_user INT;
    DECLARE pending_status_id BINARY(16);

    START TRANSACTION;
    -- 1. Verify if the user has at least one product in the shopping cart
    SELECT COUNT(*)
    INTO total_products_in_shopping_cart
    FROM shopping_cart
    WHERE fk_id_user = UUID_TO_BIN(p_user_id);

    IF total_products_in_shopping_cart = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'The user does not have products in the shopping cart';
    END IF;

    -- 2. Verify if a product from the shopping cart is from the same user to prevent fraud
    SELECT COUNT(*)
    INTO products_from_same_user
    FROM shopping_cart sc
             JOIN products p ON sc.fk_id_product = p.id_product
    WHERE sc.fk_id_user = UUID_TO_BIN(p_user_id)
      AND p.fk_id_user = UUID_TO_BIN(p_user_id);

    IF products_from_same_user != 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'The user can not buy his own products';
    END IF;

    -- 3. Insert a new record into the orders table
    INSERT INTO orders(id_order, order_date, order_number, fk_id_address, fk_id_payment_card)
    VALUES (UUID_TO_BIN(UUID()), p_order_date, p_order_number, UUID_TO_BIN(p_order_id_address),
            UUID_TO_BIN(p_order_id_payment_card));

    -- 4. Get the id of the new order and the pending status id
    SELECT id_order FROM orders WHERE order_number = p_order_number INTO v_order_id;
    SELECT id_status INTO pending_status_id FROM order_status WHERE status = 'Sin pagar';
    IF v_order_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'The order was not inserted';
    END IF;

    -- 4.5 Verify that there's enough stock for the products in the shopping cart
    SELECT COUNT(*)
    INTO total_products_in_shopping_cart
    FROM shopping_cart sc
             JOIN products p ON sc.fk_id_product = p.id_product
    WHERE sc.fk_id_user = UUID_TO_BIN(p_user_id)
      AND sc.amount > p.amount;

    IF total_products_in_shopping_cart != 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'There is not enough stock for the products in the shopping cart';
    END IF;

    -- 5. Insert the products from the shopping cart into the orders_has_products table
    INSERT INTO orders_has_products (id_order_product, amount, fk_id_order, fk_id_product, fk_id_status)
    SELECT UUID_TO_BIN(UUID()),
           amount,
           v_order_id,
           fk_id_product,
           pending_status_id
    FROM shopping_cart
    WHERE fk_id_user = UUID_TO_BIN(p_user_id);
    COMMIT;
END $$
DELIMITER ;


-- Procedure to insert a new product gallery
DROP PROCEDURE IF EXISTS `sp_post_product_gallery`;
DELIMITER $$
CREATE PROCEDURE `sp_post_product_gallery`(
    IN p_product_id VARCHAR(255),
    IN p_image VARCHAR(255),
    IN p_status_id VARCHAR(255)
)
BEGIN
    DECLARE v_total_products INT;
    DECLARE v_total_images INT;
    DECLARE v_new_total_images INT;

    -- Declare handler for SQL exceptions
    DECLARE exit handler for sqlexception
        BEGIN
            ROLLBACK;
            RESIGNAL;
        END;

    -- Start the transaction
    START TRANSACTION;

-- 1. Verify if the product exists
    SELECT COUNT(*)
    INTO v_total_products
    FROM products
    WHERE id_product = UUID_TO_BIN(p_product_id)
        FOR
    UPDATE;

    IF v_total_products = 0 THEN
        SELECT 'Product does not exist' AS message;
        -- Rollback the transaction if the product does not exist
        ROLLBACK;
    ELSE
        -- 2. Verify how many images the product has
        SELECT COUNT(*)
        INTO v_total_images
        FROM product_gallery
        WHERE fk_id_product = UUID_TO_BIN(p_product_id);

        -- 3. Insert a new image into the product gallery
        INSERT INTO product_gallery(fk_id_product, fk_id_status, id_image, image)
        VALUES (UUID_TO_BIN(p_product_id), UUID_TO_BIN(p_status_id), UUID_TO_BIN(UUID()), p_image);

        -- 4. Verify if the image was inserted
        SELECT COUNT(*)
        INTO v_new_total_images
        FROM product_gallery
        WHERE fk_id_product = UUID_TO_BIN(p_product_id);

        IF v_new_total_images > v_total_images THEN
            SELECT 'Image was inserted' AS message;
            -- Commit the transaction if the image was inserted
            COMMIT;
        ELSE
            SELECT 'Image was not inserted' AS message;
            -- Rollback the transaction if the image was not inserted
            ROLLBACK;
        END IF;
    END IF;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS `insert_request_data_change`;
DELIMITER $$
CREATE PROCEDURE insert_request_data_change(IN p_email VARCHAR(100), IN p_new_user_information JSON)
BEGIN
    DECLARE v_user_id BINARY(16);
    DECLARE v_pending_status_id BINARY(16);

    SELECT id_user INTO v_user_id FROM users WHERE email = p_email;

    SELECT id_status INTO v_pending_status_id FROM request_status WHERE status = 'Pendiente';


    INSERT INTO requests_data_change (id_request_data_change, fk_id_user, new_user_information, fk_id_status)
    VALUES (UUID_TO_BIN(UUID()), v_user_id, p_new_user_information, v_pending_status_id);
    SELECT TRUE as messagge;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS `update_request_data_change`;
DELIMITER $$
CREATE PROCEDURE update_request_data_change(
    IN p_request_id BINARY(16),
    IN p_status VARCHAR(255),
    IN p_rejection_reason VARCHAR(255)
)
BEGIN
    DECLARE v_status_id BINARY(16);

    -- Buscar el ID del estado basado en el texto proporcionado
    SELECT id_status
    INTO v_status_id
    FROM request_status
    WHERE status = p_status;

    -- Verificar si el estado existe
    IF v_status_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Estado no válido';
    ELSE
        -- Verificar si la solicitud existe
        IF EXISTS (SELECT 1 FROM requests_data_change WHERE id_request_data_change = p_request_id) THEN
            -- Actualizar la solicitud
            UPDATE requests_data_change
            SET fk_id_status     = v_status_id,
                rejection_reason = p_rejection_reason
            WHERE id_request_data_change = p_request_id;
        ELSE
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La solicitud no existe';
        END IF;
    END IF;
    SELECT TRUE as messagge;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS `insert_request_become_seller`;
DELIMITER $$
CREATE PROCEDURE insert_request_become_seller(IN p_email VARCHAR(100), IN p_user_seller_information JSON)
BEGIN
    DECLARE v_user_id BINARY(16);
    DECLARE v_pending_status_id BINARY(16);

    SELECT id_user INTO v_user_id FROM users WHERE email = p_email;

    SELECT id_status INTO v_pending_status_id FROM request_status WHERE status = 'Pendiente';

    INSERT INTO requests_become_seller (id_request_become_seller, fk_id_user, user_seller_information, fk_id_status)
    VALUES (UUID_TO_BIN(UUID()), v_user_id, p_user_seller_information, v_pending_status_id);
    SELECT TRUE as message;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS `update_request_become_seller`;
DELIMITER $$
CREATE PROCEDURE update_request_become_seller(
    IN p_request_id BINARY(16),
    IN p_status VARCHAR(255),
    IN p_rejection_reason VARCHAR(255)
)
BEGIN
    DECLARE v_status_id BINARY(16);

    SELECT id_status INTO v_status_id FROM request_status WHERE status = p_status;

    IF v_status_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Estado no válido';
    ELSE
        IF EXISTS (SELECT 1 FROM requests_become_seller WHERE id_request_become_seller = p_request_id) THEN
            UPDATE requests_become_seller
            SET fk_id_status     = v_status_id,
                rejection_reason = p_rejection_reason
            WHERE id_request_become_seller = p_request_id;
        ELSE
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La solicitud no existe';
        END IF;
    END IF;
    SELECT TRUE as message;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS `insert_seller_role`;
DELIMITER $$
CREATE PROCEDURE insert_seller_role(IN p_request_id BINARY(16))
BEGIN
    DECLARE v_user_id BINARY(16);
    DECLARE v_seller_role_id BINARY(16);

    SELECT fk_id_user INTO v_user_id FROM requests_become_seller WHERE id_request_become_seller = p_request_id;

    SELECT id_role INTO v_seller_role_id FROM roles WHERE role_name = 'ROLE_SELLER';

    INSERT INTO user_roles (id_user_role, fk_id_role, fk_id_user)
    VALUES (UUID_TO_BIN(UUID()), v_seller_role_id, v_user_id);
    SELECT TRUE as message;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS `find_request_become_seller_by_email`;
DELIMITER $$
CREATE PROCEDURE find_request_become_seller_by_email(IN p_email VARCHAR(100))
BEGIN
    DECLARE v_user_id BINARY(16);
    DECLARE v_status_id BINARY(16);
    DECLARE v_status VARCHAR(255);
    DECLARE v_exists_request INT;

    -- Verificar si existe una solicitud para el email proporcionado
    SELECT COUNT(*) INTO v_exists_request FROM users u
                                                   INNER JOIN requests_become_seller r ON u.id_user = r.fk_id_user
    WHERE u.email = p_email;

    IF v_exists_request = 0 THEN
        -- No se encontró ninguna solicitud para ese email, retornar false
        SELECT 0 AS result;
    ELSE
        -- Obtener el ID de usuario
        SELECT id_user INTO v_user_id FROM users WHERE email = p_email;

        -- Obtener el ID de estado de la solicitud
        SELECT fk_id_status INTO v_status_id FROM requests_become_seller WHERE fk_id_user = v_user_id;

        -- Obtener el estado de la solicitud
        SELECT status INTO v_status FROM request_status WHERE id_status = v_status_id;

        IF v_status = 'Pendiente' THEN
            -- El estado es pendiente, retornar true
            SELECT 1 AS result;
        ELSE
            -- El estado es rechazado o cualquier otro, retornar false
            SELECT 0 AS result;
        END IF;
    END IF;
END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS `find_request_become_seller_by_email`;
DELIMITER $$
CREATE PROCEDURE find_request_become_seller_by_email(IN p_email VARCHAR(100))
BEGIN
    DECLARE v_user_id BINARY(16);
    DECLARE v_status_id BINARY(16);
    DECLARE v_status VARCHAR(255);
    DECLARE v_exists_request INT;

    -- Verificar si existe una solicitud para el email proporcionado
    SELECT COUNT(*) INTO v_exists_request FROM users u
                                                   INNER JOIN requests_become_seller r ON u.id_user = r.fk_id_user
    WHERE u.email = p_email;

    IF v_exists_request = 0 THEN
        -- No se encontró ninguna solicitud para ese email, retornar false
        SELECT 0 AS result;
    ELSE
        -- Obtener el ID de usuario
        SELECT id_user INTO v_user_id FROM users WHERE email = p_email;

        -- Obtener el ID de estado de la solicitud
        SELECT fk_id_status INTO v_status_id FROM requests_become_seller WHERE fk_id_user = v_user_id;

        -- Obtener el estado de la solicitud
        SELECT status INTO v_status FROM request_status WHERE id_status = v_status_id;

        IF v_status = 'Pendiente' THEN
            -- El estado es pendiente, retornar true
            SELECT 1 AS result;
        ELSE
            -- El estado es rechazado o cualquier otro, retornar false
            SELECT 0 AS result;
        END IF;
    END IF;
END $$
DELIMITER ;
