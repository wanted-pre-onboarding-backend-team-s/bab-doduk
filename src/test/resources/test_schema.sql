CREATE TABLE IF NOT EXISTS `restaurants`
(
    `id`           BIGINT         NOT NULL AUTO_INCREMENT,
    `sigun_name`   VARCHAR(50)    NOT NULL,
    `biz_name`     VARCHAR(50)    NOT NULL,
    `biz_status`   VARCHAR(50)    NOT NULL,
    `cuisine_type` VARCHAR(50)    NOT NULL,
    `road_addr`    VARCHAR(255)   NOT NULL,
    `jibun_addr`   VARCHAR(255)   NOT NULL,
    `lat`          DECIMAL(10, 6) NOT NULL,
    `lon`          DECIMAL(10, 6) NOT NULL,
    `manage_no`    VARCHAR(255)   NULL,
    `sigun_code`   VARCHAR(50)    NULL,
    `created_at`   DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`   DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS  `restaurant_review_stats`
(
    `id`            BIGINT      NOT NULL AUTO_INCREMENT,
    `restaurant_id` BIGINT      NOT NULL,
    `average_score` DOUBLE NOT NULL,
    `review_count`  INT         NOT NULL,
    PRIMARY KEY (`id`)
);