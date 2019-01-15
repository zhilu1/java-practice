INSERT INTO `sys_permission` VALUES (1, 'ROLE_HOME', '首页', '/', NULL);
INSERT INTO `sys_permission` VALUES (2, 'ROLE_ADMIN', '临时页面', '/admin', NULL);
INSERT INTO `sys_permission` VALUES (3, 'ROLE_USEROP', '用户管理', '/authority/*', NULL);
INSERT INTO `sys_permission` VALUES (4, 'ROLE_ROLEOP', '角色管理', '/role/*', NULL);
INSERT INTO `sys_permission` VALUES (5, 'ROLE_IMPORTOP', '导入员工考勤记录', '/import', NULL);
INSERT INTO `sys_permission` VALUES (6, 'ROLE_EXPORTOP', '导出员工考勤记录', '/export', NULL);
INSERT INTO `sys_permission` VALUES (7, 'ROLE_SELECTOP', '考勤界面筛选员工', '/selectRecordByIdAndDate', NULL);
INSERT INTO `sys_permission` VALUES (8, 'ROLE_CALENDARMG', '管理考勤工作历', '/calendar/edit', NULL);
INSERT INTO `sys_permission` VALUES (9, 'ROLE_CALENDARVW', '查看考勤工作历', '/calendar/manage', NULL);
