/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50709
Source Host           : localhost:3306
Source Database       : bs_audit

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2017-10-19 18:29:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for audit_record2017090700
-- ----------------------------
DROP TABLE IF EXISTS `audit_record2017090700`;
CREATE TABLE `audit_record2017090700` (
  `id` bigint(32) NOT NULL,
  `times` datetime NOT NULL,
  `level` int(1) unsigned NOT NULL,
  `handle_status` tinyint(1) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ck_handle_status` (`handle_status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of audit_record2017090700
-- ----------------------------

-- ----------------------------
-- Table structure for audit_record_base
-- ----------------------------
DROP TABLE IF EXISTS `audit_record_base`;
CREATE TABLE `audit_record_base` (
  `id` bigint(32) NOT NULL,
  `times` datetime NOT NULL,
  `level` int(1) unsigned NOT NULL,
  `handle_status` tinyint(1) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ck_handle_status` (`handle_status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of audit_record_base
-- ----------------------------

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `department_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `parent_department_id` bigint(20) unsigned DEFAULT NULL,
  `department_name` varchar(50) NOT NULL DEFAULT '',
  `is_operation_all` tinyint(1) unsigned NOT NULL,
  `is_operation_this_level` tinyint(1) unsigned NOT NULL,
  `description` varchar(255) DEFAULT '',
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', null, 'true', '1', '1', '');

-- ----------------------------
-- Table structure for module
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module` (
  `module_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `module_name` varchar(50) NOT NULL,
  `request_mapping` varchar(50) NOT NULL,
  `module_group_id` bigint(20) unsigned DEFAULT NULL,
  `module_insert_address` varchar(50) NOT NULL,
  `module_delete_address` varchar(50) NOT NULL,
  `module_update_address` varchar(50) NOT NULL,
  `module_select_address` varchar(50) NOT NULL,
  `is_enable` tinyint(1) unsigned NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `icon_cls` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`module_id`),
  UNIQUE KEY `moduleName` (`module_name`),
  KEY `FKmedrofcf7b7x52ue2n506cil1` (`module_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2037 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of module
-- ----------------------------
INSERT INTO `module` VALUES ('2000', 'user.management.nav.title', '/pages/charts/echarts', '1000', '/user/add', '/user/update-password', '', '/user/query', '1', '用户管理', null);
INSERT INTO `module` VALUES ('2001', 'role.rights.management.nav.title', '/rights/popedom', '1000', '', '', '', '', '1', '角色权限管理', '');
INSERT INTO `module` VALUES ('2002', 'baseline.management.nav.title', '', '1001', '', '', '', '', '1', '基线管理', null);
INSERT INTO `module` VALUES ('2003', 'vulnerability.management.nav.title', '', '1001', '', '', '', '', '1', '漏洞管理', null);
INSERT INTO `module` VALUES ('2004', 'event.query.nav.title', '', '1002', '', '', '', '', '1', '事件查询', null);
INSERT INTO `module` VALUES ('2005', 'events.tracing.nav.title', '', '1002', '', '', '', '', '1', '事件回放', null);
INSERT INTO `module` VALUES ('2006', 'routine.report.nav.title', '', '1003', '', '', '', '', '1', '常规报表', null);
INSERT INTO `module` VALUES ('2007', 'compliance.report.nav.title', '', '1003', '', '', '', '', '1', '合规报表', null);
INSERT INTO `module` VALUES ('2008', 'report.configuration.nav.title', '', '1003', '', '', '', '', '1', '报表配置', null);
INSERT INTO `module` VALUES ('2009', 'customized.reports.nav.title', '', '1003', '', '', '', '', '1', '自定义报表', null);
INSERT INTO `module` VALUES ('2010', 'auditsys.driver.level.ip.nav.title', '', '1004', '', '', '', '', '1', '驱动级IP', null);
INSERT INTO `module` VALUES ('2011', 'auditsys.sentence.system.nav.title', '', '1004', '', '', '', '', '1', '系统语句', null);
INSERT INTO `module` VALUES ('2012', 'auditsys.white.list.nav.title', '', '1004', '', '', '', '', '1', '白名单', null);
INSERT INTO `module` VALUES ('2013', 'operation.log.nav.title', '', '1005', '', '', '', '/log/query', '1', '操作日志', null);
INSERT INTO `module` VALUES ('2014', 'audit.strategy.nav.title', '', '1006', '', '', '', '', '1', '审计策略', null);
INSERT INTO `module` VALUES ('2015', 'rule.nav.title', '', '1006', '', '', '', '', '1', '规则', null);
INSERT INTO `module` VALUES ('2016', 'rule.set.nav.title', '', '1006', '', '', '', '', '1', '规则组', null);
INSERT INTO `module` VALUES ('2017', 'tool.monitored.nav.title', '', '1006', '', '', '', '', '1', '工具监控', null);
INSERT INTO `module` VALUES ('2018', 'warning.strategy.nav.title', '', '1006', '', '', '', '', '1', '告警策略', null);
INSERT INTO `module` VALUES ('2019', 'time.object.nav.title', '', '1007', '', '', '', '', '1', '时间对象', null);
INSERT INTO `module` VALUES ('2020', 'rulesys.driver.level.ip.nav.title', '', '1008', '', '', '', '', '1', '驱动级IP', null);
INSERT INTO `module` VALUES ('2021', 'rulesys.sentence.system.nav.title', '', '1008', '', '', '', '', '1', '系统语句', null);
INSERT INTO `module` VALUES ('2022', 'rulesys.white.list.nav.title', '', '1008', '', '', '', '', '1', '白名单', null);
INSERT INTO `module` VALUES ('2023', 'node.configuration.nav.title', '', '1009', '', '', '', '', '1', '节点配置', null);
INSERT INTO `module` VALUES ('2024', 'network.port.configuration.nav.title', '', '1009', '', '', '', '', '1', '网口配置', null);
INSERT INTO `module` VALUES ('2025', 'sms.and.mail.nav.title', '', '1010', '', '', '', '', '1', '短信与邮件', null);
INSERT INTO `module` VALUES ('2026', 'syslog.nav.title', '', '1010', '', '', '', '', '1', 'SYSLOG', null);
INSERT INTO `module` VALUES ('2027', 'snmp.nav.title', '', '1010', '', '', '', '', '1', 'SNMP', null);
INSERT INTO `module` VALUES ('2028', 'data.backup.nav.title', '', '1011', '', '', '', '', '1', '数据备份', null);
INSERT INTO `module` VALUES ('2029', 'data.recovery.nav.title', '', '1011', '', '', '', '', '1', '数据恢复', null);
INSERT INTO `module` VALUES ('2030', 'data.cleaning.nav.title', '', '1011', '', '', '', '', '1', '数据清理', null);
INSERT INTO `module` VALUES ('2031', 'license.management.nav.title', '', '1012', '', '', '', '', '1', '许可管理', null);
INSERT INTO `module` VALUES ('2032', 'fault.diagnosis.nav.title', '', '1012', '', '', '', '', '1', '故障诊断', null);
INSERT INTO `module` VALUES ('2033', 'login.security.nav.title', '', '1012', '', '', '', '', '1', '登录安全', null);
INSERT INTO `module` VALUES ('2034', 'system.upgrade.nav.title', '', '1012', '', '', '', '', '1', '系统升级', null);
INSERT INTO `module` VALUES ('2035', 'ntp.configuration.nav.title', '', '1012', '', '', '', '', '1', 'NTP配置', null);
INSERT INTO `module` VALUES ('2036', 'system.log.nav.title', '', '1013', '', '', '', '', '1', '系统日志', null);

-- ----------------------------
-- Table structure for module_group
-- ----------------------------
DROP TABLE IF EXISTS `module_group`;
CREATE TABLE `module_group` (
  `module_group_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `module_name` varchar(50) NOT NULL,
  `icon_cls` varchar(255) DEFAULT NULL,
  `description` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`module_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1014 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of module_group
-- ----------------------------
INSERT INTO `module_group` VALUES ('1000', 'users_with_permission_nav_title', 'nb-compose', '用户与权限');
INSERT INTO `module_group` VALUES ('1001', 'risk_management_nav_title', null, '风险管理');
INSERT INTO `module_group` VALUES ('1002', 'audit_management_nav_title', null, '审计管理');
INSERT INTO `module_group` VALUES ('1003', 'report_management_nav_title', null, '报表管理');
INSERT INTO `module_group` VALUES ('1004', 'auditsys_audit_filtering_nav_title', null, '审计过滤');
INSERT INTO `module_group` VALUES ('1005', 'log_management_nav_title', null, '日志管理');
INSERT INTO `module_group` VALUES ('1006', 'policy_configuration_nav_title', null, '策略配置');
INSERT INTO `module_group` VALUES ('1007', 'configuration_object_nav_title', null, '对象配置');
INSERT INTO `module_group` VALUES ('1008', 'rulesys_audit_filtering_nav_title', null, '审计过滤');
INSERT INTO `module_group` VALUES ('1009', 'network_management_nav_title', null, '网络管理');
INSERT INTO `module_group` VALUES ('1010', 'alarm_configuration_nav_title', null, '告警配置');
INSERT INTO `module_group` VALUES ('1011', 'data_maintenance_nav_title', null, '数据维护');
INSERT INTO `module_group` VALUES ('1012', 'system_maintenance_nav_title', null, '系统维护');
INSERT INTO `module_group` VALUES ('1013', 'log_management_nav_title', null, '日志管理');

-- ----------------------------
-- Table structure for operation_log
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `login_ip` varchar(20) NOT NULL,
  `success` bit(1) NOT NULL,
  `module_name` varchar(50) DEFAULT NULL,
  `oper_desc` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for popedom
-- ----------------------------
DROP TABLE IF EXISTS `popedom`;
CREATE TABLE `popedom` (
  `popedom_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `is_allow_browse` tinyint(1) NOT NULL DEFAULT '1',
  `is_allow_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_allow_edit` tinyint(1) NOT NULL DEFAULT '0',
  `is_allow_insert` tinyint(1) NOT NULL DEFAULT '0',
  `module_id` bigint(20) unsigned NOT NULL,
  `role_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`popedom_id`),
  KEY `module_id` (`module_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `popedom_ibfk_1` FOREIGN KEY (`module_id`) REFERENCES `module` (`module_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `popedom_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of popedom
-- ----------------------------
INSERT INTO `popedom` VALUES ('1', '1', '1', '1', '1', '2000', '1');
INSERT INTO `popedom` VALUES ('2', '1', '1', '1', '1', '2013', '1');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL,
  `role_group_id` bigint(20) unsigned DEFAULT NULL,
  `is_enable` tinyint(1) unsigned NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  KEY `uk_role_group_id` (`role_group_id`) USING BTREE,
  CONSTRAINT `role_ibfk_1` FOREIGN KEY (`role_group_id`) REFERENCES `role_group` (`role_group_id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '审计管理员', '3', '1', null);

-- ----------------------------
-- Table structure for role_group
-- ----------------------------
DROP TABLE IF EXISTS `role_group`;
CREATE TABLE `role_group` (
  `role_group_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_group_name` varchar(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_group
-- ----------------------------
INSERT INTO `role_group` VALUES ('1', 'system', '系统管理员');
INSERT INTO `role_group` VALUES ('2', 'rule', '规则管理员');
INSERT INTO `role_group` VALUES ('3', 'audit', '审计管理员');

-- ----------------------------
-- Table structure for role_module_group
-- ----------------------------
DROP TABLE IF EXISTS `role_module_group`;
CREATE TABLE `role_module_group` (
  `role_module_group_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_group_id` bigint(20) unsigned DEFAULT NULL,
  `module_grop_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`role_module_group_id`),
  KEY `role_group_id` (`role_group_id`),
  KEY `module_grop_id` (`module_grop_id`),
  CONSTRAINT `role_module_group_ibfk_1` FOREIGN KEY (`role_group_id`) REFERENCES `role_group` (`role_group_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `role_module_group_ibfk_2` FOREIGN KEY (`module_grop_id`) REFERENCES `module_group` (`module_group_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_module_group
-- ----------------------------
INSERT INTO `role_module_group` VALUES ('1', '3', '1000');

-- ----------------------------
-- Table structure for systemset
-- ----------------------------
DROP TABLE IF EXISTS `systemset`;
CREATE TABLE `systemset` (
  `systemsetId` int(12) unsigned NOT NULL AUTO_INCREMENT,
  `userdwmc` varchar(50) NOT NULL COMMENT '单位名称',
  `userAddress` varchar(50) DEFAULT NULL,
  `userType` varchar(20) DEFAULT NULL,
  `userLinkmen` varchar(20) DEFAULT NULL,
  `userTelnumber` varchar(20) DEFAULT NULL,
  `userStartdate` datetime DEFAULT NULL,
  `userRemark` mediumtext,
  `serviceDepartment` varchar(50) DEFAULT NULL,
  `serviceMen` varchar(50) DEFAULT NULL,
  `serviceTelnumber` varchar(50) DEFAULT NULL,
  `serviceFaxnumber` varchar(50) DEFAULT NULL,
  `serviceEmail` varchar(50) DEFAULT NULL,
  `serviceHomepage` varchar(50) DEFAULT NULL,
  `serviceQQ` varchar(50) DEFAULT NULL,
  `serviceRemark` mediumtext COMMENT '服务单位备注',
  PRIMARY KEY (`systemsetId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of systemset
-- ----------------------------

-- ----------------------------
-- Table structure for system_info
-- ----------------------------
DROP TABLE IF EXISTS `system_info`;
CREATE TABLE `system_info` (
  `systeminfo_id` int(12) unsigned NOT NULL AUTO_INCREMENT,
  `is_allow_external_login` tinyint(1) unsigned NOT NULL,
  `copyright_info` varchar(50) NOT NULL,
  `copyright_owner` varchar(50) NOT NULL,
  `is_need_identifing_code` tinyint(1) unsigned NOT NULL,
  `is_need_replace_initial_password` tinyint(1) unsigned NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `session_timeout_minute` int(11) NOT NULL,
  `system_version` varchar(50) NOT NULL,
  `system_name` varchar(50) NOT NULL,
  PRIMARY KEY (`systeminfo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_info
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `login_name` varchar(20) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `department_id` bigint(20) unsigned DEFAULT NULL,
  `is_allow_login` tinyint(1) unsigned NOT NULL,
  `is_send_message` tinyint(1) unsigned DEFAULT NULL,
  `is_off_line` tinyint(1) DEFAULT NULL,
  `last_login_date` datetime DEFAULT NULL,
  `login_times` int(11) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `mobile_phone_number` varchar(20) DEFAULT NULL,
  `mail` varchar(50) DEFAULT NULL,
  `qq` varchar(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `department_id` (`department_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', null, null, 'admin', '刘华春', 'ISMvKXpXpadDiUoOSoAfww==', '1', '1', null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for user_department
-- ----------------------------
DROP TABLE IF EXISTS `user_department`;
CREATE TABLE `user_department` (
  `user_department_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `department_id` int(10) NOT NULL,
  PRIMARY KEY (`user_department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_department
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_role_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`user_role_id`),
  KEY `uk_user_id` (`user_id`) USING BTREE,
  KEY `uk_role_id` (`role_id`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');

-- ----------------------------
-- View structure for user_module
-- ----------------------------
DROP VIEW IF EXISTS `user_module`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `user_module` AS select `module_group`.`module_group_id` AS `module_group_id`,`module_group`.`module_name` AS `module_name`,`module_group`.`icon_cls` AS `icon_cls`,`user`.`user_id` AS `user_id`,`module_group`.`description` AS `description` from ((((`module_group` left join `role_module_group` on((`module_group`.`module_group_id` = `role_module_group`.`module_grop_id`))) left join `role` on((`role`.`role_group_id` = `role_module_group`.`role_group_id`))) left join `user_role` on((`user_role`.`role_id` = `role`.`role_id`))) left join `user` on((`user_role`.`user_id` = `user`.`user_id`))) ;
