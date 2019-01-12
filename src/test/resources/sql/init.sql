DROP TABLE IF EXISTS `Sys_User`;
CREATE TABLE `Sys_User`(
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(200) NOT NULL,
  `password` VARCHAR(200) NOT NULL,
  `name` VARCHAR(200) NOT NULL,
  `department` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`username`)
)CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `riqi` date NOT NULL,
  `staff_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sbtime` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `xbtime` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `Sys_Role`;
CREATE TABLE `Sys_Role`(
`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
`name` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`name`)
) CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `Sys_permission`;
CREATE TABLE `Sys_permission`(
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL,
  `description` VARCHAR(200) DEFAULT NULL,
  `url` VARCHAR(200) NOT NULL,
  `pid` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`)
) CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `Sys_role_user`;
CREATE TABLE `Sys_role_user`(
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT UNSIGNED NOT NULL,
  `role_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`)
) CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `Sys_permission_role`;
CREATE TABLE `Sys_permission_role`(
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` BIGINT UNSIGNED NOT NULL,
  `permission_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`)
)CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `office_time_calendar`;
CREATE TABLE `office_time_calendar` (
  `id` bigint(20) NOT NULL,
  `day` date NOT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `status` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
)CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

insert into SYS_USER (id,username, password, name, department) values (1,'101', 'admin', 'Oven', 'UNO');
insert into SYS_USER (id,username, password, name, department) values (2,'111', 'abel', 'Alien', 'UFO');

insert into SYS_ROLE(id,name) values(1,'ROLE_ADMIN');
insert into SYS_ROLE(id,name) values(2,'ROLE_USER');

insert into record(riqi,staff_id, sbtime, xbtime) values('2019-01-11','101','10:00','15:00');
insert into record(riqi,staff_id, sbtime, xbtime) values('2019-01-11','111','11:00','5:00');

insert into SYS_ROLE_USER(user_id,role_id) values(1,1);
insert into SYS_ROLE_USER(user_id,role_id) values(2,2);

INSERT INTO `Sys_permission` VALUES ('1', 'ROLE_HOME', 'home page', '/', null), ('2', 'ROLE_ADMIN', 'empty admin page', '/admin', null), ('3', 'ROLE_USEROP', 'user operations', '/authority/*', null);
INSERT INTO `Sys_permission` VALUES ('4', 'ROLE_ROLEOP', 'role operations', '/role/*', null);
INSERT INTO `sys_permission` VALUES (5, 'ROLE_IMPORTOP', 'import record', '/import', NULL);
INSERT INTO `sys_permission` VALUES (6, 'ROLE_EXPORTOP', 'export record', '/export', NULL);
INSERT INTO `sys_permission` VALUES (7, 'ROLE_SELECTOP', 'select staff record', '/selectRecordByIdAndDate', NULL);


INSERT INTO `Sys_permission_role` VALUES ('1', '1', '1'), ('2', '1', '2'), ('3', '2', '1') , ('4', '1', '3'), ('5', '1', '4');

