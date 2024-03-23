CREATE TABLE IF NOT EXISTS Province
(
    korean_province_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name               VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS City
(
    korean_city_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    name               VARCHAR(255) NOT NULL,
    korean_province_id BIGINT,
    FOREIGN KEY (korean_province_id) REFERENCES Province (korean_province_id)
);