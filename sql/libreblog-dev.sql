CREATE TABLE `menu`
(
    `id`        INT     NOT NULL AUTO_INCREMENT,
    `code`      VARCHAR(100)     DEFAULT NULL,
    `name`      VARCHAR(100)     DEFAULT NULL,
    `parent_id` INT     NOT NULL DEFAULT 0,
    `ancestors` VARCHAR(255)     DEFAULT NULL,
    `level`     INT     NOT NULL DEFAULT 0,
    `sort`      INT     NOT NULL DEFAULT 1,
    `type`      TINYINT NOT NULL,
    PRIMARY KEY (`id`),
    KEY idx_parent_id (`parent_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `role`
(
    `id`      INT          NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `role_menu`
(
    id INT NOT NULL AUTO_INCREMENT,
    role_id INT NOT NULL,
    menu_id INT NOT NULL,
    scope TINYINT COMMENT '权限范围：0-全部，1-仅自己',
    PRIMARY KEY (id),
    KEY idx_role_id (role_id),
    KEY idx_menu_id (menu_id)
) ENGINE InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE `user`
(
    `id`          INT          NOT NULL AUTO_INCREMENT,
    `username`    VARCHAR(255) NOT NULL,
    `password`    VARCHAR(255) NOT NULL,
    `create_time` DATETIME     NOT NULL DEFAULT NOW(),
    `modify_time` DATETIME     NOT NULL DEFAULT NOW(),
    `email`       VARCHAR(255)          DEFAULT NULL,
    `realname`    VARCHAR(255)          DEFAULT NULL,
    `avatar`      VARCHAR(500)          DEFAULT NULL,
    `role_id`     INT          NOT NULL DEFAULT 4,
    `delete_flag` CHAR(1)      NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    KEY `idx_username` (`username`)
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
    PRIMARY KEY (`id`)
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
    PRIMARY KEY (`id`)
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

-- 菜单
INSERT INTO menu (id, code, name, parent_id, ancestors, level, sort, type) VALUES
(1, 'sysMgr', '系统管理', 0, '', 1, 1, 1),
(2, 'userMgr', '用户管理', 1, '1', 2, 1, 2),
(3, 'articleMgr', '文章管理', 1, '1', 2, 2, 2),
(4, 'categoryMgr', '分类管理', 1, '1', 2, 3, 2),
(5, 'tagMgr', '标签管理', 1, '1', 2, 4, 2),
(6, 'roleMgr', '角色管理', 1, '1', 2, 5, 2),
(7, 'menuMgr', '菜单管理', 1, '1', 2, 6, 2),
(30, 'userMgr:list', '用户管理:列表', 2, '1,2', 3, 1, 3),
(31, 'userMgr:get', '用户管理:查询', 2, '1,2', 3, 2, 3),
(32, 'userMgr:create', '用户管理:新增', 2, '1,2', 3, 3, 3),
(33, 'userMgr:update', '用户管理:更新', 2, '1,2', 3, 4, 3),
(34, 'userMgr:delete', '用户管理:删除', 2, '1,2', 3, 5, 3),
(40, 'articleMgr:list', '文章管理:列表', 3, '1,3', 3, 1, 3),
(41, 'articleMgr:get', '文章管理:查询', 3, '1,3', 3, 2, 3),
(42, 'articleMgr:create', '文章管理:新增', 3, '1,3', 3, 3, 3),
(43, 'articleMgr:update', '文章管理:更新', 3, '1,3', 3, 4, 3),
(44, 'articleMgr:delete', '文章管理:删除', 3, '1,3', 3, 5, 3),
(50, 'categoryMgr:list', '分类管理:列表', 4, '1,4', 3, 1, 3),
(51, 'categoryMgr:get', '分类管理:查询', 4, '1,4', 3, 2, 3),
(52, 'categoryMgr:create', '分类管理:新增', 4, '1,4', 3, 3, 3),
(53, 'categoryMgr:update', '分类管理:更新', 4, '1,4', 3, 4, 3),
(54, 'categoryMgr:delete', '分类管理:删除', 4, '1,4', 3, 5, 3),
(60, 'tagMgr:list', '标签管理:列表', 5, '1,5', 3, 1, 3),
(61, 'tagMgr:get', '标签管理:查询', 5, '1,5', 3, 2, 3),
(62, 'tagMgr:create', '标签管理:新增', 5, '1,5', 3, 3, 3),
(63, 'tagMgr:update', '标签管理:更新', 5, '1,5', 3, 4, 3),
(64, 'tagMgr:delete', '标签管理:删除', 5, '1,5', 3, 5, 3),
(70, 'roleMgr:list', '角色管理:列表', 6, '1,6', 3, 1, 3),
(80, 'menuMgr:list', '菜单管理:列表', 7, '1,7', 3, 1, 3);

-- 角色
SET @administrator = 1;
SET @editor = 2;
SET @contributor = 3;
SET @subscriber = 4;
INSERT INTO role (`id`, `name`)
VALUES (@administrator, 'administrator'),
       (@editor, 'editor'),
       (@contributor, 'contributor'),
       (@subscriber, 'subscriber');

-- 角色权限关系
INSERT INTO role_menu (role_id, menu_id, scope) VALUES
(@administrator, 1, null),
(@administrator, 2, null),
(@administrator, 3, null),
(@administrator, 4, null),
(@administrator, 5, null),
(@administrator, 6, null),
(@administrator, 7, null),
(@administrator, 30, 0),
(@administrator, 31, 0),
(@administrator, 32, 0),
(@administrator, 33, 0),
(@administrator, 34, 0),
(@administrator, 40, 0),
(@administrator, 41, 0),
(@administrator, 42, 0),
(@administrator, 43, 0),
(@administrator, 44, 0),
(@administrator, 50, 0),
(@administrator, 51, 0),
(@administrator, 52, 0),
(@administrator, 53, 0),
(@administrator, 54, 0),
(@administrator, 60, 0),
(@administrator, 61, 0),
(@administrator, 62, 0),
(@administrator, 63, 0),
(@administrator, 64, 0),
(@administrator, 70, 0),
(@administrator, 80, 0),
(@editor, 1, null),
(@editor, 2, null),
(@editor, 3, null),
(@editor, 4, null),
(@editor, 5, null),
(@editor, 6, null),
(@editor, 7, null),
(@editor, 30, 0), -- editor可以获取所有用户列表
(@editor, 31, 1), -- editor只能获取自己的信息
(@editor, 33, 1), -- editor可以编辑自己的信息
(@editor, 40, 0),
(@editor, 41, 0),
(@editor, 42, 0),
(@editor, 43, 0),
(@editor, 44, 0),
(@editor, 50, 0),
(@editor, 51, 0),
(@editor, 52, 0),
(@editor, 53, 0),
(@editor, 54, 0),
(@editor, 60, 0),
(@editor, 61, 0),
(@editor, 62, 0),
(@editor, 63, 0),
(@editor, 64, 0),
(@editor, 70, 1),
(@editor, 80, 1),
(@contributor, 1, null),
(@contributor, 2, null),
(@contributor, 3, null),
(@contributor, 4, null),
(@contributor, 5, null),
(@contributor, 6, null),
(@contributor, 7, null),
(@contributor, 30, 1),
(@contributor, 31, 1), -- contributor只能获取自己的信息
(@contributor, 33, 1),
(@contributor, 40, 1),
(@contributor, 41, 1),
(@contributor, 42, 1),
(@contributor, 43, 1),
(@contributor, 44, 1),
(@contributor, 50, 0),
(@contributor, 51, 1),
(@contributor, 60, 0),
(@contributor, 61, 1),
(@contributor, 70, 1),
(@contributor, 80, 1),
(@subscriber, 1, null),
(@subscriber, 2, null),
(@subscriber, 3, null),
(@subscriber, 4, null),
(@subscriber, 5, null),
(@subscriber, 6, null),
(@subscriber, 7, null),
(@subscriber, 30, 1),
(@subscriber, 31, 1),
(@subscriber, 33, 1),
(@subscriber, 70, 1),
(@subscriber, 80, 1)
;

-- 默认密码：Pw123#
SET @password = '169EAD658AA375192BFECCFE28F2F275C59574B3CF3BFCFA6727441FE5E89B30F42B89BC19598910BA2EE135BB3ECA08';
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