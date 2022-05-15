CREATE TABLE IF NOT EXISTS product
(
    productid         INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    productname       VARCHAR(128) NOT NULL,
    category           VARCHAR(32)  NOT NULL,
    image_url          VARCHAR(256) NOT NULL,
    price              INT          NOT NULL,
    stock              INT          NOT NULL,
    description        VARCHAR(1024),
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);