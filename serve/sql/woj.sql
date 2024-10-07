CREATE DATABASE IF NOT EXISTS `woj` DEFAULT CHARACTER SET utf8mb4;

USE `woj`;
-- =======================================================
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
		`uuid` varchar(32) NOT NULL COMMENT 'UUID',
		`unionId` varchar(32) DEFAULT NULL COMMENT '微信开放平台id',
		`mpOpenId` varchar(32) DEFAULT NULL COMMENT '公众号openId',
		`userAccount` varchar(100) NOT NULL COMMENT '账号（邮箱）',
		`userPassword` varchar(100) NOT NULL COMMENT '密码',
		`userName` varchar(100) DEFAULT 'AcKing用户' NULL COMMENT '昵称',
		`userProfile` varchar(100) DEFAULT NULL COMMENT '简介',
		`school` varchar(100) DEFAULT NULL COMMENT '学校',
		`course` varchar(100) DEFAULT NULL COMMENT '专业',
		`number` varchar(20) DEFAULT NULL COMMENT '学号',
		`gender` varchar(20) DEFAULT '保密' NOT NULL  COMMENT '性别',
		`github` varchar(255) DEFAULT NULL COMMENT 'github地址',
		`blog` varchar(255) DEFAULT NULL COMMENT '博客地址',
		`avatar` varchar(255) DEFAULT 'https://s3.bmp.ovh/imgs/2024/08/11/4608fafdfd47d53b.jpg' NULL COMMENT '头像地址',
		`signature` mediumtext COMMENT '个性签名',
		`titleName` varchar(255) DEFAULT NULL COMMENT '头衔、称号',
		`titleColor` varchar(255) DEFAULT NULL COMMENT '头衔、称号的颜色',
		`status` int(11) NOT NULL DEFAULT '0' COMMENT '0可用，1不可用',
		`createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
		`updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
		`isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
		PRIMARY KEY (`uuid`),
		UNIQUE KEY `USERNAME_UNIQUE` (`userAccount`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4;

insert into `user_info`(`uuid`,`unionId`,`mpOpenId`,`userAccount`,`userPassword`,`userName`,`userProfile`,`school`,`course`,`number`,`gender`,`github`,`blog`,`avatar`,`signature`,`titleName`,`titleColor`,`status`,`createTime`,`updateTime`,`isDelete`) values 
('17357976f04b4a70a9566a9e3e9aed10','','','123123123','f0797d4afde771923205f33b1a61c32a','acking','简介','泰山学院','信息科学与技术','2022075082','男','github','blog','https://avatars.githubusercontent.com/u/43477085?v=4','个性标签','头衔','头衔颜色','0','2024-06-12 23:15:06','2024-06-12 23:15:06',0);

-- =======================================================
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
                        `id` int unsigned zerofill NOT NULL,
                        `role` varchar(50) NOT NULL COMMENT '角色',
                        `description` varchar(100) DEFAULT NULL COMMENT '描述',
                        `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '默认0可用，1不可用',
                        `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into `role`(`id`,`role`,`description`,`status`,`createTime`,`updateTime`) values 
(0,'root','超级管理员',0,'2024-10-25 00:16:30','2024-10-25 00:16:30'),
(1,'admin','管理员',0,'2024-10-25 00:16:41','2024-10-25 00:16:41'),
(2,'default_user','默认用户',0,'2024-10-25 00:16:52','2024-05-15 07:39:05'),
(3,'no_subimit_user','禁止提交用户',0,'2024-05-15 07:10:14','2024-05-15 07:39:14'),
(4,'no_discuss_user','禁止发贴讨论用户',0,'2024-05-15 07:11:28','2024-05-15 07:39:16'),
(5,'mute_user','禁言包括回复讨论发帖用户',0,'2024-05-15 07:12:51','2024-05-15 07:39:19'),
(6,'no_submit_no_discuss_user','禁止提交同时禁止发帖用户',0,'2024-05-15 07:38:08','2024-05-15 07:39:34'),
(7,'no_submit_mute_user','禁言禁提交用户',0,'2024-05-15 07:39:00','2024-05-15 07:39:26'),
(8,'problem_admin','题目管理员',0,'2024-06-12 23:15:06','2024-06-12 23:15:06');

-- =======================================================
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
                             `id` TINYINT(100) unsigned NOT NULL AUTO_INCREMENT,
                             `uid` varchar(32) NOT NULL,
                             `roleId` int unsigned NOT NULL,
                             `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
                             `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             PRIMARY KEY (`id`),
                             KEY `uid` (`uid`) USING BTREE,
                             KEY `roleId` (`roleId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

insert into `user_role`(`id`,`uid`,`roleId`,`createTime`,`updateTime`) values 
(1,'17357976f04b4a70a9566a9e3e9aed10',0,'2024-10-25 00:16:30','2024-10-25 00:16:30');

-- =======================================================
/*标签表*/
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '标签名字',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- =======================================================
/*题目表*/
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `problemId` varchar(255) NOT NULL COMMENT '问题的自定义ID',
  `title` varchar(255) NOT NULL COMMENT '题目',
	`tagList` varchar(255) NOT NULL COMMENT '标签',
  `author` varchar(255) DEFAULT '未知' COMMENT '作者',
  `timeLimit` int(11) DEFAULT '1000' COMMENT '单位ms',
  `memoryLimit` int(11) DEFAULT '65535' COMMENT '单位kb',
  `stackLimit` int(11) DEFAULT '128' COMMENT '单位mb',
  `description` longtext COMMENT '描述',
  `input` longtext COMMENT '输入描述',
  `output` longtext COMMENT '输出描述',
  `source` text COMMENT '题目来源',
  `difficulty` int(11) DEFAULT '0' COMMENT '题目难度,0简单，1中等，2困难',
  `auth` int(11) DEFAULT '1' COMMENT '默认为1公开，2为私有，3为比赛题目',
  `judgeMode` varchar(255) DEFAULT 'default' COMMENT '题目评测模式,default、spj、interactive',
  `spjCode` longtext COMMENT '特判程序或交互程序代码',
  `spjLanguage` varchar(255) DEFAULT NULL COMMENT '特判程序或交互程序代码的语言',
  `modifiedUser` varchar(255) DEFAULT NULL COMMENT '修改题目的管理员用户名',
  `isFileIo` tinyint(1) DEFAULT '0' COMMENT '是否是file io自定义输入输出文件模式',
  `ioReadFileName` varchar(255) DEFAULT NULL COMMENT '题目指定的file io输入文件的名称',
  `ioWriteFileName` varchar(255) DEFAULT NULL COMMENT '题目指定的file io输出文件的名称',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `author` (`author`),
  KEY `problem_id` (`problemId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- =======================================================
/*题目标签表*/
DROP TABLE IF EXISTS `problem_tag`;
CREATE TABLE `problem_tag` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) unsigned NOT NULL COMMENT '题目id',
  `tid` bigint(20) unsigned NOT NULL COMMENT '标签id',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`),
  KEY `tid` (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- =======================================================
/*代码提交表*/
DROP TABLE IF EXISTS `problem_submit`;
CREATE TABLE `problem_submit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `language` varchar(255) NOT NULL COMMENT '代码语言',
  `code` text NOT NULL COMMENT '提交代码',
	`judgeInfo` varchar(255) COMMENT '判题信息',
	`state` INT NOT NULL DEFAULT 0 COMMENT '判题状态（0 - 待判题、1 - 判题中、2 - 判题结束）',
	`pid` bigint(20) unsigned NOT NULL COMMENT '题目id',
	`uid` varchar(32) NOT NULL COMMENT '用户id',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- =======================================================
/*课程表*/
/*暂已弃用*/
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	`pid` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '父节点id',
  `name` varchar(255) NOT NULL COMMENT '名称',
	`level` INT NOT NULL DEFAULT 1 COMMENT '层级',
  `description` text NOT NULL COMMENT '描述',
	`avatar` varchar(255) DEFAULT 'https://avatars.githubusercontent.com/u/43477085?v=4' COMMENT '图像',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- =======================================================
/*活动表*/
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL COMMENT '标题',
  `description` longtext NOT NULL COMMENT '活动细则',
	`avatar` varchar(255) DEFAULT 'https://avatars.githubusercontent.com/u/43477085?v=4' COMMENT '图像',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `name` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- =======================================================
/*公告表*/
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL COMMENT '标题',
  `content` longtext NOT NULL COMMENT '内容',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



