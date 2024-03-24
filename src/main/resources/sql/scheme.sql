CREATE TABLE IF NOT EXIST 'dive_shop' (
    dive_shop_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    province VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    zip_code VARCHAR(255) NOT NULL,
    detail VARCHAR(255),
    phone_number VARCHAR(255),
    fax VARCHAR(255),
    comment VARCHAR(500),
    reserve_count INT NOT NULL,
    available_time VARCHAR(255),
);