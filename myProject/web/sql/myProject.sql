CREATE TABLE `user_info` (
  `ID` bigint(8) unsigned NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(50) NOT NULL COMMENT '用户名-作为唯一的区分',
  `ALIAS` varchar(300) DEFAULT NULL COMMENT '昵称',
  `PASSWORD` varchar(50) NOT NULL COMMENT '后续用MD5加密',
  `ROLE_KEY` varchar(500) DEFAULT NULL COMMENT '角色控制',
  `USR_DESC` varchar(200) DEFAULT NULL COMMENT '用户描述',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT '电子邮件地址',
  `MOBIL` varchar(30) DEFAULT NULL COMMENT '手机号',
  `ONLINE_STATE` varchar(10) DEFAULT NULL COMMENT '判断是否在线',
  `CREATE_DATE` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_DATE` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '控制密码过期时期',
  `LAST_LOG_TIME` timestamp NULL DEFAULT NULL COMMENT '最后一次登录时间',
  `LOG_COUNT` int(4) DEFAULT NULL COMMENT '登录系统次数',
  `SCORE` int(8) DEFAULT NULL COMMENT '积分系统，方便后续扩展',
  `FLAG` int(2) DEFAULT NULL COMMENT '后续考虑',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lev_score` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `LEV` int(4) NOT NULL COMMENT '等级控制',
  `SCORE` int(8) NOT NULL COMMENT '积分临界值',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `azyhong`.`lev_score` (`ID`, `LEV`, `SCORE`) VALUES ('1', '1', '200');
INSERT INTO `azyhong`.`lev_score` (`ID`, `LEV`, `SCORE`) VALUES ('2', '2', '500');
INSERT INTO `azyhong`.`lev_score` (`ID`, `LEV`, `SCORE`) VALUES ('3', '3', '1000');
INSERT INTO `azyhong`.`lev_score` (`ID`, `LEV`, `SCORE`) VALUES ('4', '4', '2000');
INSERT INTO `azyhong`.`lev_score` (`ID`, `LEV`, `SCORE`) VALUES ('5', '5', '5000');
INSERT INTO `azyhong`.`lev_score` (`ID`, `LEV`, `SCORE`) VALUES ('6', '6', '10000');
INSERT INTO `azyhong`.`lev_score` (`ID`, `LEV`, `SCORE`) VALUES ('7', '7', '20000');
INSERT INTO `azyhong`.`lev_score` (`ID`, `LEV`, `SCORE`) VALUES ('8', '8', '50000');
INSERT INTO `azyhong`.`lev_score` (`ID`, `LEV`, `SCORE`) VALUES ('9', '9', '100000');


CREATE TABLE `user_money_detail` (
  `ID` bigint(8) unsigned NOT NULL AUTO_INCREMENT,
  `MONEY` int(10) DEFAULT '0' COMMENT '支出或收入的钱（元）',
  `TYPE` varchar(50) DEFAULT NULL COMMENT '花费类型，后期根据此进行统计',
  `FLAG` int(2) DEFAULT NULL COMMENT '判断是否是收入还是支出',
  `CREATE_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '录入系统时间',
  `MAKE_DATE` datetime DEFAULT NULL COMMENT '实际花费或入账的时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

