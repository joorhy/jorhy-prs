-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.13-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 prs 的数据库结构
DROP DATABASE IF EXISTS `prs`;
CREATE DATABASE IF NOT EXISTS `prs` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `prs`;


-- 导出  表 prs.approve_record 结构
DROP TABLE IF EXISTS `approve_record`;
CREATE TABLE IF NOT EXISTS `approve_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `purchase_id` int(11) NOT NULL,
  `opinion` varchar(45) DEFAULT NULL,
  `purchase_activity_id` int(11) NOT NULL,
  `approve_date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_approve_record_user1_idx` (`user_id`),
  KEY `fk_approve_record_purchase1_idx` (`purchase_id`),
  KEY `fk_approve_record_pruchase_activity1_idx` (`purchase_activity_id`),
  CONSTRAINT `fk_approve_record_pruchase_activity1` FOREIGN KEY (`purchase_activity_id`) REFERENCES `purchase_activity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_approve_record_purchase1` FOREIGN KEY (`purchase_id`) REFERENCES `purchase` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_approve_record_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 prs.dept 结构
DROP TABLE IF EXISTS `dept`;
CREATE TABLE IF NOT EXISTS `dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门ID，自增',
  `dept_name` varchar(45) DEFAULT NULL COMMENT '部门名称',
  `dept_code` varchar(45) DEFAULT NULL COMMENT '单位编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 prs.directors 结构
DROP TABLE IF EXISTS `directors`;
CREATE TABLE IF NOT EXISTS `directors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_directors_role1_idx` (`role_id`),
  CONSTRAINT `fk_directors_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 prs.finance_dept 结构
DROP TABLE IF EXISTS `finance_dept`;
CREATE TABLE IF NOT EXISTS `finance_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_finance_dept_role1_idx` (`role_id`),
  CONSTRAINT `fk_finance_dept_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 prs.funds_nature 结构
DROP TABLE IF EXISTS `funds_nature`;
CREATE TABLE IF NOT EXISTS `funds_nature` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 prs.package 结构
DROP TABLE IF EXISTS `package`;
CREATE TABLE IF NOT EXISTS `package` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `package_uuid` varchar(45) DEFAULT NULL COMMENT '包ID',
  `purchase_id` int(11) NOT NULL,
  `package_name` varchar(45) DEFAULT NULL COMMENT '包名',
  `package_code` varchar(45) DEFAULT NULL COMMENT '采购文号',
  `pur_address` varchar(45) DEFAULT NULL COMMENT '采购地点',
  `pur_date` varchar(45) DEFAULT NULL COMMENT '采购日期',
  `expert_count` int(11) DEFAULT '0' COMMENT '抽取专家人数',
  `pur_method` varchar(45) DEFAULT NULL COMMENT '采购方式',
  `publicity` tinyint(1) DEFAULT NULL COMMENT '采购需求公告',
  `vendor` varchar(45) DEFAULT NULL COMMENT '中标供应商',
  `amount` double DEFAULT NULL COMMENT '中标金额',
  `package_activity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_package_purchase1_idx` (`purchase_id`),
  KEY `fk_package_package_activity1_idx` (`package_activity_id`),
  CONSTRAINT `fk_package_package_activity1` FOREIGN KEY (`package_activity_id`) REFERENCES `package_activity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_package_purchase1` FOREIGN KEY (`purchase_id`) REFERENCES `purchase` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 prs.package_activity 结构
DROP TABLE IF EXISTS `package_activity`;
CREATE TABLE IF NOT EXISTS `package_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 prs.package_attach_file 结构
DROP TABLE IF EXISTS `package_attach_file`;
CREATE TABLE IF NOT EXISTS `package_attach_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `path` varchar(512) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  `package_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_package_attach_file_package1_idx` (`package_id`),
  CONSTRAINT `fk_package_attach_file_package1` FOREIGN KEY (`package_id`) REFERENCES `package` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 prs.package_product 结构
DROP TABLE IF EXISTS `package_product`;
CREATE TABLE IF NOT EXISTS `package_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目ID，自增',
  `uuid` varchar(45) DEFAULT NULL COMMENT '项目ID',
  `type` varchar(45) DEFAULT NULL COMMENT '项目类别',
  `name` varchar(45) DEFAULT NULL COMMENT '项目名称',
  `count` int(11) DEFAULT NULL COMMENT '数量',
  `price` double DEFAULT NULL COMMENT '单价',
  `spec` varchar(45) DEFAULT NULL COMMENT '规格型号',
  `pre_price` double DEFAULT NULL COMMENT '预算总价',
  `param` varchar(45) DEFAULT NULL COMMENT '技术参数及参数',
  `packaged_count` int(11) DEFAULT '0' COMMENT '已分包数量',
  `package_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_copy1_package1_idx` (`package_id`),
  CONSTRAINT `fk_product_copy1_package1` FOREIGN KEY (`package_id`) REFERENCES `package` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 prs.package_record 结构
DROP TABLE IF EXISTS `package_record`;
CREATE TABLE IF NOT EXISTS `package_record` (
  `user_id` int(11) NOT NULL,
  `package_id` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `package_activity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_package_record_user1_idx` (`user_id`),
  KEY `fk_package_record_package1_idx` (`package_id`),
  KEY `fk_package_record_package_activity1_idx` (`package_activity_id`),
  CONSTRAINT `fk_package_record_package1` FOREIGN KEY (`package_id`) REFERENCES `package` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_package_record_package_activity1` FOREIGN KEY (`package_activity_id`) REFERENCES `package_activity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_package_record_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 prs.permission 结构
DROP TABLE IF EXISTS `permission`;
CREATE TABLE IF NOT EXISTS `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `perm` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 prs.product 结构
DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目ID，自增',
  `uuid` varchar(45) DEFAULT NULL COMMENT '项目ID',
  `type` varchar(45) DEFAULT NULL COMMENT '项目类别',
  `name` varchar(45) DEFAULT NULL COMMENT '项目名称',
  `count` int(11) DEFAULT NULL COMMENT '数量',
  `purchase_id` int(11) NOT NULL,
  `price` double DEFAULT NULL COMMENT '单价',
  `spec` varchar(45) DEFAULT NULL COMMENT '规格型号',
  `pre_price` double DEFAULT NULL COMMENT '预算总价',
  `param` varchar(45) DEFAULT NULL COMMENT '技术参数及参数',
  `packaged_count` int(11) DEFAULT '0' COMMENT '已分包数量',
  PRIMARY KEY (`id`),
  KEY `fk_product_purchase1_idx` (`purchase_id`),
  CONSTRAINT `fk_product_purchase1` FOREIGN KEY (`purchase_id`) REFERENCES `purchase` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 prs.purchase 结构
DROP TABLE IF EXISTS `purchase`;
CREATE TABLE IF NOT EXISTS `purchase` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '采购ID，自增',
  `code` varchar(45) DEFAULT NULL COMMENT '采购编号',
  `purchase_uuid` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `funds_src` varchar(45) DEFAULT NULL,
  `funds_nature_id` int(11) NOT NULL,
  `contacts` varchar(45) DEFAULT NULL,
  `phone_num` varchar(45) DEFAULT NULL,
  `purchase_activity_id` int(11) NOT NULL,
  `commodity_pre_price` double DEFAULT NULL,
  `service_pre_price` double DEFAULT NULL,
  `engineering_pre_price` double DEFAULT NULL,
  `dept_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_purchase_funds_nature1_idx` (`funds_nature_id`),
  KEY `fk_purchase_pruchase_activity1_idx` (`purchase_activity_id`),
  KEY `fk_purchase_dept1_idx` (`dept_id`),
  KEY `fk_purchase_role1_idx` (`role_id`),
  CONSTRAINT `fk_purchase_dept1` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_funds_nature1` FOREIGN KEY (`funds_nature_id`) REFERENCES `funds_nature` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_pruchase_activity1` FOREIGN KEY (`purchase_activity_id`) REFERENCES `purchase_activity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 prs.purchase_activity 结构
DROP TABLE IF EXISTS `purchase_activity`;
CREATE TABLE IF NOT EXISTS `purchase_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 prs.purchase_attach_file 结构
DROP TABLE IF EXISTS `purchase_attach_file`;
CREATE TABLE IF NOT EXISTS `purchase_attach_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `path` varchar(512) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  `purchase_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_purchase_attach_file_purchase1_idx` (`purchase_id`),
  CONSTRAINT `fk_purchase_attach_file_purchase1` FOREIGN KEY (`purchase_id`) REFERENCES `purchase` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 prs.role 结构
DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(45) DEFAULT NULL COMMENT '用户角色\n乡镇：普通职员，单位会计，分管领导-农业局，分管领导-财政局，分管领导-组织部，分管领导-宣传部\n上级：分管股室-农业股，教科文股，投资股，行财股，社保股, \n',
  `post` varchar(45) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_role_user1_idx` (`user_id`),
  KEY `fk_role_permission1_idx` (`permission_id`),
  CONSTRAINT `fk_role_permission1` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 prs.user 结构
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID,自增',
  `username` varchar(45) DEFAULT NULL COMMENT '用户名',
  `passwd` varchar(45) DEFAULT NULL COMMENT '密码',
  `dept_id` int(11) NOT NULL,
  `real_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_dept_idx` (`dept_id`),
  CONSTRAINT `fk_user_dept` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
