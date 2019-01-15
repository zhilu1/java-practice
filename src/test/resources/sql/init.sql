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
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`on_date` date NOT NULL,
`start_time` time DEFAULT NULL,
`end_time` time DEFAULT NULL,
`status` tinyint(4) NOT NULL,
PRIMARY KEY (`id`)
)CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

insert into SYS_USER (id,username, password, name, department) values (1,'101', 'admin', 'Oven', 'UNO');
insert into SYS_USER (id,username, password, name, department) values (2,'111', 'abel', 'Alien', 'UFO');

insert into SYS_ROLE(id,name) values(1,'管理员');
insert into SYS_ROLE(id,name) values(2,'普通');

insert into record(riqi,staff_id, sbtime, xbtime) values('2019-01-11','101','10:00','15:00');
insert into record(riqi,staff_id, sbtime, xbtime) values('2019-01-11','111','11:00','5:00');

insert into SYS_ROLE_USER(user_id,role_id) values(1,1);
insert into SYS_ROLE_USER(user_id,role_id) values(2,2);

INSERT INTO `sys_permission` VALUES (1, 'ROLE_HOME', '首页', '/', NULL);
INSERT INTO `sys_permission` VALUES (2, 'ROLE_ADMIN', '临时页面', '/admin', NULL);
INSERT INTO `sys_permission` VALUES (3, 'ROLE_USEROP', '用户管理', '/authority/*', NULL);
INSERT INTO `sys_permission` VALUES (4, 'ROLE_ROLEOP', '角色管理', '/role/*', NULL);
INSERT INTO `sys_permission` VALUES (5, 'ROLE_IMPORTOP', '导入员工考勤记录', '/import', NULL);
INSERT INTO `sys_permission` VALUES (6, 'ROLE_EXPORTOP', '导出员工考勤记录', '/export', NULL);
INSERT INTO `sys_permission` VALUES (7, 'ROLE_SELECTOP', '考勤界面筛选员工', '/selectRecordByIdAndDate', NULL);
INSERT INTO `sys_permission` VALUES (8, 'ROLE_CALENDARMG', '管理考勤工作历', '/calendar/edit', NULL);
INSERT INTO `sys_permission` VALUES (9, 'ROLE_CALENDARVW', '查看考勤工作历', '/calendar/manage', NULL);



INSERT INTO Sys_permission_role(role_id,permission_id) VALUES ('1', '1'),('1', '2'),('1', '3'),('1', '4'),('1', '5'),('1', '6'),('1', '7'),('1', '8'),('1', '9');
INSERT INTO Sys_permission_role(role_id,permission_id) VALUES ('2', '1'),('2', '9');

INSERT INTO `office_time_calendar` VALUES (1, '2019-1-15', '10:00:00', '20:00:00', 1);
