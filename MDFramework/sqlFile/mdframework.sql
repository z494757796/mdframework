/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.28 : Database - mdframework
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mdframework` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mdframework`;

/*Table structure for table `banner_info` */

DROP TABLE IF EXISTS `banner_info`;

CREATE TABLE `banner_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) DEFAULT NULL COMMENT '创建人',
  `title` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `main_pic` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片',
  `link_url` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '链接',
  `order_by` int(4) DEFAULT NULL COMMENT '排序',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `status` int(1) DEFAULT '0' COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_status` int(1) DEFAULT '1' COMMENT '删除状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `banner_info` */

insert  into `banner_info`(`id`,`user_id`,`title`,`main_pic`,`link_url`,`order_by`,`start_time`,`end_time`,`status`,`create_time`,`del_status`) values (2,NULL,'轮播图1','http://localhost:8083/uploadfiles/2018/11/30/cb63d5cbbf9fa6ca33fbf49b140a55b8.jpg','http://mvnrepository.com/artifact/joda-time/joda-time/2.5',1,'2018-07-27 00:00:00','2018-07-29 00:00:00',1,'2018-11-30 15:16:12',1);

/*Table structure for table `base_info` */

DROP TABLE IF EXISTS `base_info`;

CREATE TABLE `base_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公司名称',
  `main_pic` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'logo',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '客服电话',
  `qq_number` varchar(12) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'qq号码',
  `about_us` longtext COLLATE utf8mb4_unicode_ci COMMENT '关于我们',
  `pay_amount` double DEFAULT '1' COMMENT '支付金额',
  `del_status` int(1) DEFAULT '1' COMMENT '删除状态',
  `person_amount` double DEFAULT '0' COMMENT '个人会员支付金额',
  `brand_amount` double DEFAULT '0' COMMENT '品牌会员支付金额',
  `person_expiry_time` int(5) DEFAULT NULL COMMENT '个人会员到期时间',
  `brand_expiry_time` int(5) DEFAULT NULL COMMENT '品牌会员到期时间',
  `person_content` longtext COLLATE utf8mb4_unicode_ci COMMENT '个人会员推广内容',
  `brand_content` longtext COLLATE utf8mb4_unicode_ci COMMENT '品牌会员推广内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `base_info` */

insert  into `base_info`(`id`,`name`,`main_pic`,`phone`,`qq_number`,`about_us`,`pay_amount`,`del_status`,`person_amount`,`brand_amount`,`person_expiry_time`,`brand_expiry_time`,`person_content`,`brand_content`) values (1,'基础项目框架','http://localhost:8083/uploadfiles/2018/11/30/42935f29d352309e73b244e20e55802c.jpg','020-12345611','5731104611','<p>关于我们内容</p>',1,1,99,199,365,365,NULL,NULL);

/*Table structure for table `comm_params_info` */

DROP TABLE IF EXISTS `comm_params_info`;

CREATE TABLE `comm_params_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type_name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属类型名称',
  `unique_type` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '类型唯一标识',
  `key_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '数据名称',
  `key_value` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '数据对应值',
  `order_by` int(2) DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_status` int(1) DEFAULT '1' COMMENT '删除状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `comm_params_info` */

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `time` int(11) DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';

/*Data for the table `sys_log` */

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`menu_id`,`parent_id`,`name`,`url`,`perms`,`type`,`icon`,`order_num`,`gmt_create`,`gmt_modified`) values (1,0,'基础管理','','',0,'fa fa-bars',40,'2017-08-09 22:49:47',NULL),(2,3,'系统菜单','sys/menu/','sys:menu:menu',1,'fa fa-th-list',2,'2017-08-09 22:55:15',NULL),(3,0,'系统管理','','',0,'fa fa-desktop',100,'2017-08-09 23:06:55','2017-08-14 14:13:43'),(6,3,'用户管理','sys/user/','sys:user:user',1,'fa fa-user',0,'2017-08-10 14:12:11',NULL),(7,3,'角色管理','sys/role','sys:role:role',1,'fa fa-paw',1,'2017-08-10 14:13:19',NULL),(12,6,'新增','','sys:user:add',2,'',0,'2017-08-14 10:51:35',NULL),(13,6,'编辑','','sys:user:edit',2,'',0,'2017-08-14 10:52:06',NULL),(14,6,'删除',NULL,'sys:user:remove',2,NULL,0,'2017-08-14 10:52:24',NULL),(15,7,'新增','','sys:role:add',2,'',0,'2017-08-14 10:56:37',NULL),(20,2,'新增','','sys:menu:add',2,'',0,'2017-08-14 10:59:32',NULL),(21,2,'编辑','','sys:menu:edit',2,'',0,'2017-08-14 10:59:56',NULL),(22,2,'删除','','sys:menu:remove',2,'',0,'2017-08-14 11:00:26',NULL),(24,6,'批量删除','','sys:user:batchRemove',2,'',0,'2017-08-14 17:27:18',NULL),(25,6,'停用',NULL,'sys:user:disable',2,NULL,0,'2017-08-14 17:27:43',NULL),(26,6,'重置密码','','sys:user:resetPwd',2,'',0,'2017-08-14 17:28:34',NULL),(27,1,'系统日志','common/log','common:log',1,'fa fa-warning',2,'2017-08-14 22:11:53',NULL),(28,27,'刷新',NULL,'sys:log:list',2,NULL,0,'2017-08-14 22:30:22',NULL),(29,27,'删除',NULL,'sys:log:remove',2,NULL,0,'2017-08-14 22:30:43',NULL),(30,27,'清空',NULL,'sys:log:clear',2,NULL,0,'2017-08-14 22:31:02',NULL),(48,1,'代码生成','common/generator','common:generator',1,'fa fa-code',NULL,NULL,NULL),(55,7,'编辑','','sys:role:edit',2,'',NULL,NULL,NULL),(56,7,'删除','','sys:role:remove',2,NULL,NULL,NULL,NULL),(57,1,'运行监控','/druid/index.html','',1,'fa fa-caret-square-o-right',NULL,NULL,NULL),(61,2,'批量删除','','sys:menu:batchRemove',2,NULL,NULL,NULL,NULL),(62,7,'批量删除','','sys:role:batchRemove',2,NULL,NULL,NULL,NULL),(92,0,'站点配置','','',0,'fa fa-desktop',10,NULL,NULL),(93,92,'站点信息','baseInfo/edit/1','baseInfo:baseInfo',1,'fa fa-desktop',NULL,NULL,NULL),(94,93,'查看','','baseInfo:list,baseInfo:info',2,'',NULL,NULL,NULL),(95,93,'新增','','baseInfo:save',2,'fa fa-desktop',NULL,NULL,NULL),(96,93,'编辑','','baseInfo:update',2,'fa fa-desktop',NULL,NULL,NULL),(97,93,'删除','','baseInfo:remove',2,'fa fa-desktop',NULL,NULL,NULL),(98,1,'参数配置','commParamsInfo','commParamsInfo:commParamsInfo',1,'fa fa-list',6,NULL,NULL),(99,98,'查看',NULL,'commParamsInfo:list,commParamsInfo:info',2,NULL,6,NULL,NULL),(100,98,'新增',NULL,'commParamsInfo:save',2,NULL,6,NULL,NULL),(101,98,'修改',NULL,'commParamsInfo:update',2,NULL,6,NULL,NULL),(102,98,'删除',NULL,'commParamsInfo:remove',2,NULL,6,NULL,NULL),(103,0,'内容管理','','',0,'fa fa-desktop',20,NULL,NULL),(104,103,'轮播图管理','bannerInfo','bannerInfo:bannerInfo',1,'fa fa-list',6,NULL,NULL),(105,104,'查看',NULL,'bannerInfo:list,bannerInfo:info',2,NULL,6,NULL,NULL),(106,104,'新增',NULL,'bannerInfo:save',2,NULL,6,NULL,NULL),(107,104,'修改',NULL,'bannerInfo:update',2,NULL,6,NULL,NULL),(108,104,'删除',NULL,'bannerInfo:remove',2,NULL,6,NULL,NULL),(109,0,'会员管理','','',0,'fa fa-desktop',30,NULL,NULL),(110,109,'会员信息','member','member:member',1,'fa fa-list',6,NULL,NULL),(111,110,'查看',NULL,'member:list,member:info',2,NULL,6,NULL,NULL),(112,110,'新增',NULL,'member:save',2,NULL,6,NULL,NULL),(113,110,'修改',NULL,'member:update',2,NULL,6,NULL,NULL),(114,110,'删除',NULL,'member:remove',2,NULL,6,NULL,NULL),(115,3,'组织架构','organizationInfo','organizationInfo:organizationInfo',1,'fa fa-list',6,NULL,NULL),(116,115,'查看',NULL,'organizationInfo:list,organizationInfo:info',2,NULL,6,NULL,NULL),(117,115,'新增',NULL,'organizationInfo:save',2,NULL,6,NULL,NULL),(118,115,'修改',NULL,'organizationInfo:update',2,NULL,6,NULL,NULL),(119,115,'删除',NULL,'organizationInfo:remove',2,NULL,6,NULL,NULL);

/*Table structure for table `sys_organization_info` */

DROP TABLE IF EXISTS `sys_organization_info`;

CREATE TABLE `sys_organization_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级id',
  `all_parent_ids` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所有父级id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` int(1) DEFAULT '1' COMMENT '状态',
  `order_by` int(11) DEFAULT NULL COMMENT '排序',
  `del_status` int(1) DEFAULT '1' COMMENT '删除状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='组织架构';

/*Data for the table `sys_organization_info` */

insert  into `sys_organization_info`(`id`,`parent_id`,`all_parent_ids`,`name`,`create_id`,`create_time`,`update_id`,`update_time`,`status`,`order_by`,`del_status`) values (1,0,'0','总公司',1,'2018-12-07 16:30:20',1,'2018-12-14 17:17:23',1,10,1),(2,1,'0,1','总经办',1,'2018-12-07 16:34:18',1,'2018-12-07 16:39:24',1,1010,1),(3,1,'0,1','市场部',1,'2018-12-07 16:34:37',1,'2018-12-07 16:39:31',1,1020,1),(4,1,'0,1','研发部',1,'2018-12-07 16:40:41',1,'2018-12-07 16:40:41',1,1030,1);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `role_sign` varchar(100) DEFAULT NULL COMMENT '角色标识',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `user_id_create` bigint(255) DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='角色';

/*Data for the table `sys_role` */

insert  into `sys_role`(`role_id`,`role_name`,`role_sign`,`remark`,`user_id_create`,`gmt_create`,`gmt_modified`) values (1,'超级用户角色','admin','拥有最高权限',2,'2017-08-12 00:43:52','2017-08-12 19:14:59'),(56,'系统管理员',NULL,'系统管理员',NULL,NULL,NULL);

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2613 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`id`,`role_id`,`menu_id`) values (367,44,1),(368,44,32),(369,44,33),(370,44,34),(371,44,35),(372,44,28),(373,44,29),(374,44,30),(375,44,38),(376,44,4),(377,44,27),(378,45,38),(379,46,3),(380,46,20),(381,46,21),(382,46,22),(383,46,23),(384,46,11),(385,46,12),(386,46,13),(387,46,14),(388,46,24),(389,46,25),(390,46,26),(391,46,15),(392,46,2),(393,46,6),(394,46,7),(581,48,38),(582,48,32),(583,48,33),(584,48,34),(585,48,35),(586,48,4),(587,48,28),(588,48,29),(589,48,30),(590,48,27),(591,48,1),(598,50,38),(632,38,42),(737,51,38),(738,51,39),(739,51,40),(740,51,41),(741,51,4),(742,51,32),(743,51,33),(744,51,34),(745,51,35),(746,51,27),(747,51,28),(748,51,29),(749,51,30),(750,51,1),(1038,49,-1),(1039,52,50),(1040,52,49),(1064,54,53),(1095,55,2),(1096,55,6),(1097,55,7),(1098,55,3),(1099,55,50),(1100,55,49),(1101,55,1),(1375,59,1),(1376,59,3),(2271,56,20),(2272,56,21),(2273,56,22),(2274,56,61),(2275,56,2),(2276,56,12),(2277,56,13),(2278,56,14),(2279,56,24),(2280,56,25),(2281,56,26),(2282,56,6),(2283,56,15),(2284,56,55),(2285,56,56),(2286,56,62),(2287,56,7),(2288,56,3),(2289,56,69),(2290,56,70),(2291,56,71),(2292,56,72),(2293,56,73),(2294,56,74),(2295,56,68),(2296,56,94),(2297,56,96),(2298,56,93),(2299,56,92),(2300,56,103),(2301,56,104),(2302,56,105),(2303,56,106),(2304,56,107),(2305,56,108),(2554,1,94),(2555,1,96),(2556,1,93),(2557,1,92),(2558,1,105),(2559,1,106),(2560,1,107),(2561,1,108),(2562,1,104),(2563,1,103),(2564,1,111),(2565,1,112),(2566,1,113),(2567,1,114),(2568,1,110),(2569,1,109),(2570,1,48),(2571,1,57),(2572,1,27),(2573,1,99),(2574,1,100),(2575,1,101),(2576,1,102),(2577,1,98),(2578,1,1),(2579,1,122),(2580,1,123),(2581,1,124),(2582,1,125),(2583,1,121),(2584,1,120),(2585,1,12),(2586,1,13),(2587,1,14),(2588,1,24),(2589,1,25),(2590,1,26),(2591,1,6),(2592,1,55),(2593,1,56),(2594,1,62),(2595,1,15),(2596,1,7),(2597,1,61),(2598,1,20),(2599,1,21),(2600,1,22),(2601,1,2),(2602,1,116),(2603,1,117),(2604,1,118),(2605,1,119),(2606,1,115),(2607,1,3),(2608,1,131),(2609,1,132),(2610,1,133),(2611,1,134),(2612,1,135);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `organization_id` int(11) DEFAULT NULL COMMENT '组织架构id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(255) DEFAULT NULL COMMENT '状态 0:禁用，1:正常',
  `user_id_create` bigint(255) DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`user_id`,`organization_id`,`username`,`password`,`email`,`mobile`,`status`,`user_id_create`,`gmt_create`,`gmt_modified`,`name`) values (1,4,'admin','27bd386e70f280e24c2f4f2a549b82cf','admin@example.com','123456111',1,1,'2017-08-15 21:40:39','2017-08-15 21:41:00','超级管理员'),(126,1,'system','58597bc12e1ec8ee2662bd9c121cec7d','12121@qq.com',NULL,1,NULL,NULL,NULL,'网站管理员');

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`user_id`,`role_id`) values (119,1,1),(121,126,56);

/*Table structure for table `t_member` */

DROP TABLE IF EXISTS `t_member`;

CREATE TABLE `t_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号码',
  `main_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `open_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信openid',
  `union_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信unionid',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_status` int(1) DEFAULT '1' COMMENT '删除状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_member` */

insert  into `t_member`(`id`,`name`,`mobile`,`main_pic`,`open_id`,`union_id`,`create_time`,`del_status`) values (1,'软件定制开发','','https://wx.qlogo.cn/mmopen/vi_32/Em7hgnibibTKZaLJrjvS98ZZz6I4SNzV4IqsF0WyYTMGPddEJnahm2iaMiaARrVaahfPySjKADFLF27rQ7kX1V8KxQ/132','orQU340F-Tb0blG5o_JkbL0aQies','oqO7B0jWjmCMkw1NkYqtizCH41T0','2018-11-30 18:44:06',1);

/*Table structure for table `zy_template_info` */

DROP TABLE IF EXISTS `zy_template_info`;

CREATE TABLE `zy_template_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `main_pic` varchar(255) DEFAULT NULL COMMENT '模板图片',
  `status` int(1) DEFAULT '1' COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_status` int(1) DEFAULT '1' COMMENT '删除状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='模板信息表';

/*Data for the table `zy_template_info` */

insert  into `zy_template_info`(`id`,`name`,`main_pic`,`status`,`create_time`,`del_status`) values (1,'模板1','http://localhost/uploadfiles/2018/10/13/ec07ea225bdc9fbb64a1b24bdeff5e1f.jpg',1,'2018-10-13 15:15:17',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
