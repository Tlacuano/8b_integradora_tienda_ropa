DROP PROCEDURE IF EXISTS `sp_post_role_user`;
DELIMITER $$
CREATE PROCEDURE `sp_post_role_user`(
    IN p_role_id VARCHAR(255),
    IN p_user_id VARCHAR(255))
BEGIN
    INSERT INTO user_roles(id_user_role, fk_id_role, fk_id_user)
    VALUES (UUID_TO_BIN(UUID()), UUID_TO_BIN(p_role_id), UUID_TO_BIN(p_user_id));
END $$
DELIMITER ;

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

    START TRANSACTION;
    -- 1. Verify if the user has at least one product in the shopping cart
    SELECT COUNT(*)
    INTO total_products_in_shopping_cart
    FROM shopping_cart
    WHERE fk_id_user = UUID_TO_BIN(p_user_id);

    IF total_products_in_shopping_cart = 0 THEN
        ROLLBACK;
    ELSE
        -- 2. Insert a new record into the orders table
        INSERT INTO orders(id_order, order_date, order_number, fk_id_address, fk_id_payment_card)
        VALUES (UUID_TO_BIN(UUID()), p_order_date, p_order_number, UUID_TO_BIN(p_order_id_address),
                UUID_TO_BIN(p_order_id_payment_card));

        -- 3. Get the id of the new order
        SELECT id_order FROM orders WHERE order_number = p_order_number INTO v_order_id;
        IF v_order_id IS NOT NULL THEN
            -- 4. Insert the products from the shopping cart into the orders_has_products table
            INSERT INTO orders_has_products (id_order_product, amount, fk_id_order, fk_id_product, fk_id_status)
            SELECT UUID_TO_BIN(UUID()),
                   amount,
                   v_order_id,
                   fk_id_product,
                   UUID_TO_BIN('9b8c35d0-7870-41c6-8719-745eaaae1a13')
            FROM shopping_cart
            WHERE fk_id_user = UUID_TO_BIN(p_user_id);

            -- 5. Verify if the products were inserted into the orders_has_products table
            SELECT COUNT(*) INTO total_products_inserted FROM orders_has_products WHERE fk_id_order = v_order_id;
            IF total_products_inserted = total_products_in_shopping_cart THEN
                -- 6. Delete the products from the shopping cart
                DELETE FROM shopping_cart WHERE fk_id_user = UUID_TO_BIN(p_user_id);
                -- 7. Get the address and payment card information
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
                ROLLBACK;
            END IF;

        ELSE
            ROLLBACK;
        END IF;
    END IF;

END $$
DELIMITER ;
