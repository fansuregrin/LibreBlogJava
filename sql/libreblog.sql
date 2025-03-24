CREATE TABLE `menu`
(
    `id`       INT NOT NULL AUTO_INCREMENT,
    `label`    VARCHAR(100) DEFAULT NULL,
    `name`     VARCHAR(100) DEFAULT NULL,
    `parent`   INT      DEFAULT NULL,
    `ancestor` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_parent_key` (`parent`, `label`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `role`
(
    `id`      INT          NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(255) NOT NULL,
    `menu_id` INT          NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`),
    KEY `idx_menu` (`menu_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `user`
(
    `id`          INT          NOT NULL AUTO_INCREMENT,
    `username`    VARCHAR(255) NOT NULL,
    `password`    VARCHAR(255) NOT NULL,
    `create_time` DATETIME     NOT NULL DEFAULT NOW(),
    `modify_time` DATETIME     NOT NULL DEFAULT NOW(),
    `email`       VARCHAR(255)          DEFAULT NULL,
    `realname`    VARCHAR(255)          DEFAULT NULL,
    `role_id`     INT          NOT NULL DEFAULT 4,
    `delete_flag` CHAR(1)      NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`),
    KEY `idx_role` (`role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `tag`
(
    `id`          INT          NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(100) NOT NULL,
    `slug`        VARCHAR(30)  NOT NULL,
    `create_time` DATETIME     NOT NULL DEFAULT NOW(),
    `modify_time` DATETIME     NOT NULL DEFAULT NOW(),
    `delete_flag` CHAR(1)      NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `slug` (`slug`),
    UNIQUE KEY `name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `category`
(
    `id`          INT          NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(100) NOT NULL,
    `slug`        VARCHAR(30)  NOT NULL,
    `create_time` DATETIME     NOT NULL DEFAULT NOW(),
    `modify_time` DATETIME     NOT NULL DEFAULT NOW(),
    `delete_flag` CHAR(1)      NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`),
    UNIQUE KEY `slug` (`slug`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `article`
(
    `id`          INT      NOT NULL AUTO_INCREMENT,
    `title`       VARCHAR(255)      DEFAULT NULL,
    `author_id`   INT      NOT NULL,
    `category_id` INT      NOT NULL DEFAULT 1,
    `content`     TEXT              DEFAULT NULL,
    `excerpt`     VARCHAR(255)      DEFAULT NULL,
    `create_time` DATETIME NOT NULL DEFAULT NOW(),
    `modify_time` DATETIME NOT NULL DEFAULT NOW(),
    `delete_flag` CHAR(1)  NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_author` (`author_id`),
    KEY `idx_category` (`category_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `article_tag`
(
    `article`     INT     NOT NULL,
    `tag`         INT     NOT NULL,
    `delete_flag` CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`article`, `tag`),
    KEY `article` (`article`),
    KEY `tag` (`tag`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `option`
(
    `name`    VARCHAR(32) NOT NULL,
    `user_id` INT         NOT NULL,
    `value`   MEDIUMTEXT DEFAULT NULL,
    PRIMARY KEY (`name`, `user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- 每个角色的顶级菜单
INSERT INTO menu(`id`, `label`, `name`, `parent`, `ancestor`)
VALUES (1, 'administrator', 'administrator', NULL, NULL),
       (2, 'editor', 'editor', NULL, NULL),
       (3, 'contributor', 'contributor', NULL, NULL),
       (4, 'subscriber', 'subscriber', NULL, NULL);
-- administrator 的菜单项
INSERT INTO menu(`id`, `label`, `name`, `parent`, `ancestor`)
VALUES (10, 'console', '控制台', 1, '1'),
       (11, 'manage', '管理', 1, '1'),
       (101, 'index', '首页', 10, '1,10'),
       (102, 'userCenter', '个人中心', 10, '1,10'),
       (103, 'logout', '登出', 10, '1,10'),
       (110, 'article', '文章', 11, '1,11'),
       (111, 'user', '用户', 11, '1,11'),
       (112, 'category', '分类', 11, '1,11'),
       (113, 'tag', '标签', 11, '1,11');
-- editor 的菜单项
INSERT INTO menu(`id`, `label`, `name`, `parent`, `ancestor`)
VALUES (20, 'console', '控制台', 2, '2'),
       (21, 'manage', '管理', 2, '2'),
       (200, 'index', '首页', 20, '2,20'),
       (201, 'userCenter', '个人中心', 20, '2,20'),
       (202, 'logout', '登出', 20, '2,20'),
       (210, 'article', '文章', 21, '2,21'),
       (211, 'category', '分类', 21, '2,21'),
       (212, 'tag', '标签', 21, '2,21');
-- contributor 的菜单项
INSERT INTO menu(`id`, `label`, `name`, `parent`, `ancestor`)
VALUES (30, 'console', '控制台', 3, '3'),
       (31, 'manage', '管理', 3, '3'),
       (300, 'index', '首页', 30, '3,30'),
       (301, 'userCenter', '个人中心', 30, '3,30'),
       (302, 'logout', '登出', 30, '3,30'),
       (310, 'article', '文章', 31, '3,31');
-- subscriber 的菜单项
INSERT INTO menu(`id`, `label`, `name`, `parent`, `ancestor`)
VALUES (40, 'console', '控制台', 4, '4'),
       (400, 'index', '首页', 40, '4,40'),
       (401, 'userCenter', '个人中心', 40, '4,40'),
       (402, 'logout', '登出', 40, '4,40');

-- 角色
INSERT INTO role (`id`, `name`, `menu_id`)
VALUES (1, 'administrator', 1),
       (2, 'editor', 2),
       (3, 'contributor', 3),
       (4, 'subscriber', 4);

-- 默认密码：Pw123#
SET @password = '169EAD658AA375192BFECCFE28F2F275C59574B3CF3BFCFA6727441FE5E89B30F42B89BC19598910BA2EE135BB3ECA08';
SET @administrator = 1;
SET @editor = 2;
SET @contributor = 3;
SET @subscriber = 4;
INSERT INTO `user` (`username`, `password`, `email`, `realname`, `role_id`)
VALUES ('bobwood', @password, 'bobwood@ouc.edu.cn', 'Bob Wood', @administrator),
       ('xiaohua', @password, 'xiaohua@bnu.edu.cn', '小华', @editor),
       ('xiaomo', @password, 'xiaoyan@bnu.edu.cn', '小莫', @editor),
       ('tom1997', @password, 'ts@gmail.com', 'Tom Smith', @contributor),
       ('dijia', @password, 'dijia@m78.galaxy', '迪迪', @subscriber);

INSERT INTO category (`id`, `name`, `slug`)
VALUES (1, '未分类', 'uncategoried'),
       (2, '诗歌', 'poem'),
       (3, '新闻', 'news'),
       (4, '小说', 'novel'),
       (5, '散文', 'essay'),
       (6, '故事', 'story');

INSERT INTO tag (`name`, `slug`)
VALUES ('宋词', 'song-ci'),
       ('五言诗', 'wu-yan-shi'),
       ('七言诗', 'qi-yan-shi'),
       ('Python', 'python'),
       ('前端', 'front-end'),
       ('数据库', 'db'),
       ('刘慈欣', 'liu-ci-xin'),
       ('阿瑟-克拉克', 'Arthur-Clarke'),
       ('人民日报', 'ren-min-ri-bao'),
       ('格林童话', 'green-brother');

delimiter //
CREATE PROCEDURE `genArticles`()
BEGIN
    declare i int default 1;
    declare time datetime default '2021-7-1 00:00:00';

    while i <= 100
    do
        INSERT INTO article
        (`title`, `author_id`, `category_id`, `content`, `create_time`, `modify_time`)
        VALUES (CONCAT('标题', i), FLOOR(RAND() * 5) + 1, FLOOR(RAND() * 6) + 1, CONCAT('我是内容', i), time, time);
        set i = i + 1;
        set time = DATE_ADD(time, INTERVAL FLOOR(RAND() * 1000000) + 1 SECOND);
    end while;
END
//
delimiter ;

CALL genArticles();