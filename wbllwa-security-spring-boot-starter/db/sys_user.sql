/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.180
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : 192.168.0.180:3306
 Source Schema         : remote-camera

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 01/12/2022 10:49:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'Jone', 18, 'test1@baomidou.com', '{noop}12345');
INSERT INTO `sys_user` VALUES (2, 'Jack', 20, 'test2@baomidou.com', '{noop}12345');
INSERT INTO `sys_user` VALUES (3, 'Tom', 28, 'test3@baomidou.com', '{noop}12345');
INSERT INTO `sys_user` VALUES (4, 'Sandy', 21, 'test4@baomidou.com', '{noop}12345');
INSERT INTO `sys_user` VALUES (5, 'Billie', 24, 'test5@baomidou.com', '{noop}12345');

SET FOREIGN_KEY_CHECKS = 1;
