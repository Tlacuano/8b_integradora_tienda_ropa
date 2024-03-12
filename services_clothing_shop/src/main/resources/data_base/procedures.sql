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




DROP PROCEDURE IF EXISTS `sp_delete_user`;
DELIMITER $$
CREATE PROCEDURE `sp_delete_user`(
    IN p_email VARCHAR(255),
    OUT result BOOLEAN)
BEGIN
    DECLARE v_user_id BINARY(16);

    SELECT id_user INTO v_user_id FROM users WHERE email = p_email;


    UPDATE users
    SET
        password = '',
        email = CONCAT(p_email, '_deleted'),
        status = 0
    WHERE id_user = v_user_id;

    UPDATE people
    SET
        name = 'Cuenta no disponible',
        last_name = '',
        second_last_name = '',
        picture = '',
        gender = 'otros',
        phone_number = ''
    WHERE fk_id_user = v_user_id;

    UPDATE sellers_information
    SET
        curp = '',
        tax_identification_number = '',
        secondary_phone_number = '',
        image_identification = '',
        privacy_policy_agreement = 0
    WHERE fk_id_user = v_user_id;

    UPDATE payment_cards
    SET
        cvv = '',
        card_number = '',
        cardholder_name = '',
        expiration_date = ''
    WHERE fk_id_user = v_user_id;


    UPDATE products
    SET
        status = 0
    WHERE fk_id_user = v_user_id;

    SELECT NOT status FROM users WHERE id_user = v_user_id;
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
    DECLARE total_products_inserted INT;
    DECLARE products_from_same_user INT;

    START TRANSACTION;
    -- 1. Verify if the user has at least one product in the shopping cart
    SELECT COUNT(*)
    INTO total_products_in_shopping_cart
    FROM shopping_cart
    WHERE fk_id_user = UUID_TO_BIN(p_user_id);

    IF total_products_in_shopping_cart = 0 THEN
        SELECT 'User does not have products in the shopping cart' AS message;
        ROLLBACK;
    ELSE
        -- 2. Verify if a product from the shopping cart is from the same user to prevent fraud
        SELECT COUNT(*)
        INTO products_from_same_user
        FROM shopping_cart sc
                 JOIN products p ON sc.fk_id_product = p.id_product
        WHERE sc.fk_id_user = UUID_TO_BIN(p_user_id)
          AND p.fk_id_user = UUID_TO_BIN(p_user_id);

        IF products_from_same_user = 0 THEN
            -- 3. Insert a new record into the orders table
            INSERT INTO orders(id_order, order_date, order_number, fk_id_address, fk_id_payment_card)
            VALUES (UUID_TO_BIN(UUID()), p_order_date, p_order_number, UUID_TO_BIN(p_order_id_address),
                    UUID_TO_BIN(p_order_id_payment_card));

            -- 4. Get the id of the new order
            SELECT id_order FROM orders WHERE order_number = p_order_number INTO v_order_id;
            IF v_order_id IS NOT NULL THEN
                -- 5. Insert the products from the shopping cart into the orders_has_products table
                INSERT INTO orders_has_products (id_order_product, amount, fk_id_order, fk_id_product, fk_id_status)
                SELECT UUID_TO_BIN(UUID()),
                       amount,
                       v_order_id,
                       fk_id_product,
                       UUID_TO_BIN('9b8c35d0-7870-41c6-8719-745eaaae1a13')
                FROM shopping_cart
                WHERE fk_id_user = UUID_TO_BIN(p_user_id);

                -- 6. Verify if the products were inserted into the orders_has_products table
                SELECT COUNT(*) INTO total_products_inserted FROM orders_has_products WHERE fk_id_order = v_order_id;
                IF total_products_inserted = total_products_in_shopping_cart THEN
                    -- 7. Delete the products from the shopping cart
                    DELETE FROM shopping_cart WHERE fk_id_user = UUID_TO_BIN(p_user_id);
                    -- 8. Get the address and payment card information
                    SELECT BIN_TO_UUID(v_order_id)         AS id_order,
                           BIN_TO_UUID(a.id_address)       AS id_address,
                           a.address                       AS address,
                           a.references_address            AS references_address,
                           a.postal_code                   AS postal_code,
                           a.state                         AS state,
                           a.street                        AS street,
                           a.neighborhood                  AS neighborhood,
                           ast.status                      AS address_status,
                           BIN_TO_UUID(pc.id_payment_card) AS id_payment_card,
                           pc.cardholder_name              AS cardholder_name,
                           pc.card_number                  AS card_number,
                           pc.expiration_date              AS expiration_date,
                           pcs.status                      AS card_status
                    FROM address a
                             JOIN payment_cards pc ON a.fk_id_user = pc.fk_id_user
                             JOIN address_status as ast ON a.fk_id_status = ast.id_status
                             JOIN card_status pcs ON pc.fk_id_status = pcs.id_status
                    WHERE a.id_address = UUID_TO_BIN(p_order_id_address)
                      AND pc.id_payment_card = UUID_TO_BIN(p_order_id_payment_card);
                    COMMIT;
                ELSE
                    SELECT 'Products were not inserted into the order' AS message;
                    ROLLBACK;
                END IF;

            ELSE
                SELECT 'Order was not created' AS message;
                ROLLBACK;
            END IF;
        ELSE
            SELECT 'User can not buy its own products' AS message;
            ROLLBACK;
        END IF;
    END IF;

END $$
DELIMITER ;

-- Procedure to insert a new productGallery
DROP PROCEDURE IF EXISTS `sp_post_product_gallery`;
DELIMITER $$
CREATE PROCEDURE `sp_post_product_gallery`(
    IN p_product_id VARCHAR(255),
    IN p_image_url_1 VARCHAR(255),
    IN p_image_url_2 VARCHAR(255),
    IN p_image_url_3 VARCHAR(255),
    IN p_image_url_4 VARCHAR(255),
    IN p_image_url_5 VARCHAR(255)
)
BEGIN
    DECLARE v_product_id BINARY(16);
    DECLARE v_total_images INT;

    -- 0. Start the transaction
    START TRANSACTION;

    -- 1. Verify if the product exists
    SELECT COUNT(*)
    INTO v_total_images
    FROM products
    WHERE id_product = UUID_TO_BIN(p_product_id);

    IF v_total_images = 0 THEN
        SELECT 'Product does not exist' AS message;
    ELSE
        -- 2. Insert the images into the product_gallery table
        INSERT INTO product_gallery(fk_id_product, fk_id_status, id_image, image)
        VALUES (UUID_TO_BIN(p_product_id), UUID_TO_BIN('f02ff8b1-dd67-11ee-8508-64006a586a6a'), UUID_TO_BIN(UUID()), p_image_url_1,),
               (UUID_TO_BIN(p_product_id), UUID_TO_BIN('bc43ef1a-13a2-4b67-bb13-f1d376e5b510'), UUID_TO_BIN(UUID()), p_image_url_2),
               (UUID_TO_BIN(p_product_id), UUID_TO_BIN('bc43ef1a-13a2-4b67-bb13-f1d376e5b510'), UUID_TO_BIN(UUID()), p_image_url_3,),
               (UUID_TO_BIN(p_product_id), UUID_TO_BIN('bc43ef1a-13a2-4b67-bb13-f1d376e5b510'), UUID_TO_BIN(UUID()), p_image_url_4,),
               (UUID_TO_BIN(p_product_id), UUID_TO_BIN('bc43ef1a-13a2-4b67-bb13-f1d376e5b510'), UUID_TO_BIN(UUID()), p_image_url_5,);

        -- 3. Verify if the images were inserted
        SELECT COUNT(*)
        INTO v_total_images
        FROM product_gallery
        WHERE fk_id_product = UUID_TO_BIN(p_product_id);

        IF v_total_images = 5 THEN
           --Get the images of the product inserted
            SELECT id_image, image
            FROM product_gallery
            WHERE fk_id_product = UUID_TO_BIN(p_product_id);
        ELSE
            SELECT 'Images were not inserted' AS message;
        END IF;
    END IF;

    -- 4. Commit the transaction
    COMMIT;
END $$
DELIMITER ;
