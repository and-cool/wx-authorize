/*
 Navicat Premium Data Transfer

 Source Server         : mysql_img
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : trustLive

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 27/07/2020 15:30:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_cms_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_cms_admin`;
CREATE TABLE `sys_cms_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_cms_admin
-- ----------------------------
BEGIN;
INSERT INTO `sys_cms_admin` VALUES (1, 'admin', '{bcrypt}$2a$10$tomJf.pO.U6Qfc7.eLfbi.tws3fykmH36QLxd3PryX4nmhRJSqbi.', '18601341610');
COMMIT;

-- ----------------------------
-- Table structure for sys_image
-- ----------------------------
DROP TABLE IF EXISTS `sys_image`;
CREATE TABLE `sys_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `src` varchar(128) DEFAULT NULL,
  `is_carousel` int(11) DEFAULT '0',
  `orders` int(11) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `nick_name` varchar(64) DEFAULT NULL,
  `gender` int(10) DEFAULT NULL,
  `country` varchar(256) DEFAULT NULL,
  `province` varchar(64) DEFAULT NULL,
  `city` varchar(64) DEFAULT NULL,
  `address` varchar(256) DEFAULT NULL,
  `occupation` varchar(128) DEFAULT NULL,
  `phone_number` varchar(11) DEFAULT NULL,
  `pure_phone_number` varchar(11) DEFAULT NULL,
  `country_code` varchar(11) DEFAULT NULL,
  `telephone` varchar(64) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `avatar_url` varchar(256) DEFAULT NULL,
  `language` varchar(64) DEFAULT NULL,
  `open_id` varchar(128) DEFAULT NULL,
  `session_key` varchar(128) DEFAULT NULL,
  `is_group_customer` varchar(10) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_verify_code
-- ----------------------------
DROP TABLE IF EXISTS `t_verify_code`;
CREATE TABLE `t_verify_code` (
  `phone` varchar(64) NOT NULL,
  `code` varchar(6) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `expire` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
