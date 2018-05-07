/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : market

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2018-05-04 18:11:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_admin
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin`;
CREATE TABLE `tb_admin` (
  `admin_id` varchar(50) NOT NULL,
  `admin_name` varchar(50) NOT NULL,
  `pass` varchar(150) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(2) NOT NULL DEFAULT '1',
  `last_login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_admin
-- ----------------------------
INSERT INTO `tb_admin` VALUES ('admin', 'admin', 'admin', '2018-05-04 14:16:26', '2018-05-04 14:16:26', '1', '2018-05-04 14:16:26');

-- ----------------------------
-- Table structure for tb_admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin_menu`;
CREATE TABLE `tb_admin_menu` (
  `menu_id` varchar(50) NOT NULL,
  `menu_name` varchar(100) NOT NULL,
  `menu_type` varchar(2) NOT NULL,
  `parent_id` varchar(50) DEFAULT NULL,
  `status` int(1) NOT NULL DEFAULT '1',
  `url` varchar(255) DEFAULT NULL,
  `count_num` int(11) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_admin_menu
-- ----------------------------
INSERT INTO `tb_admin_menu` VALUES ('M1', '用户模块', 'M', null, '1', null, '1', '2018-05-04 14:01:01', '2018-05-04 14:02:17');
INSERT INTO `tb_admin_menu` VALUES ('M1010', '用户管理', 'M', 'M1', '1', null, '11', '2018-05-04 14:01:48', '2018-05-04 14:03:39');
INSERT INTO `tb_admin_menu` VALUES ('M1011', '权限管理', 'M', 'M1', '1', null, '111', '2018-05-04 14:03:32', '2018-05-04 14:03:45');
INSERT INTO `tb_admin_menu` VALUES ('O101001', '用户查询', 'O', 'M1010', '1', null, '1101', '2018-05-04 14:04:35', '2018-05-04 17:50:34');
INSERT INTO `tb_admin_menu` VALUES ('O101002', '用户删除', 'O', 'M1010', '1', null, '1102', '2018-05-04 14:05:00', '2018-05-04 17:50:36');
INSERT INTO `tb_admin_menu` VALUES ('O101003', '用户编辑', 'O', 'M1010', '1', null, '1103', '2018-05-04 14:05:25', '2018-05-04 17:50:38');

-- ----------------------------
-- Table structure for tb_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin_role`;
CREATE TABLE `tb_admin_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_admin_role
-- ----------------------------
INSERT INTO `tb_admin_role` VALUES ('1', 'root');

-- ----------------------------
-- Table structure for tb_admin_role_menu_rel
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin_role_menu_rel`;
CREATE TABLE `tb_admin_role_menu_rel` (
  `role_id` int(11) NOT NULL,
  `menu_id` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_admin_role_menu_rel
-- ----------------------------
INSERT INTO `tb_admin_role_menu_rel` VALUES ('1', 'M1');
INSERT INTO `tb_admin_role_menu_rel` VALUES ('1', 'M1010');
INSERT INTO `tb_admin_role_menu_rel` VALUES ('1', 'M1011');
INSERT INTO `tb_admin_role_menu_rel` VALUES ('1', 'O101001');
INSERT INTO `tb_admin_role_menu_rel` VALUES ('1', 'O101002');
INSERT INTO `tb_admin_role_menu_rel` VALUES ('1', 'O101003');

-- ----------------------------
-- Table structure for tb_admin_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin_role_rel`;
CREATE TABLE `tb_admin_role_rel` (
  `admin_id` varchar(50) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_admin_role_rel
-- ----------------------------
INSERT INTO `tb_admin_role_rel` VALUES ('admin', '1');

-- ----------------------------
-- Table structure for tb_product
-- ----------------------------
DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `pname` varchar(50) NOT NULL,
  `from` varchar(50) DEFAULT NULL,
  `burnDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `comeInDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `keepTime` varchar(255) NOT NULL,
  `method` varchar(20) NOT NULL,
  `singlePrice` decimal(10,2) NOT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_product
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `userId` varchar(255) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('admin', 'admin', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO `tb_user` VALUES ('user_422d7c98bd9842649e476aec4fb752ce', 'lilei', '62d5258f3db7c3ab06aa1b3157fe87f8');
SET FOREIGN_KEY_CHECKS=1;
