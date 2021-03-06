-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.1.73-community - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.approve_record 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `approve_record` DISABLE KEYS */;
REPLACE INTO `approve_record` (`id`, `user_id`, `purchase_id`, `opinion`, `purchase_activity_id`, `approve_date`) VALUES
	(6, 3, 8, '', 5, '2016-10-11'),
	(7, 11, 8, '', 6, '2016-10-11'),
	(8, 13, 8, '', 7, '2016-10-11'),
	(11, 14, 8, '', 8, '2016-10-19');
/*!40000 ALTER TABLE `approve_record` ENABLE KEYS */;


-- 导出  表 prs.dept 结构
DROP TABLE IF EXISTS `dept`;
CREATE TABLE IF NOT EXISTS `dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门ID，自增',
  `dept_name` varchar(45) DEFAULT NULL COMMENT '部门名称',
  `dept_code` varchar(45) DEFAULT NULL COMMENT '单位编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.dept 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `dept` DISABLE KEYS */;
REPLACE INTO `dept` (`id`, `dept_name`, `dept_code`) VALUES
	(1, '某某镇', '001'),
	(2, '财政局', '002'),
	(3, '采购部门', '003');
/*!40000 ALTER TABLE `dept` ENABLE KEYS */;


-- 导出  表 prs.directors 结构
DROP TABLE IF EXISTS `directors`;
CREATE TABLE IF NOT EXISTS `directors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_directors_role1_idx` (`role_id`),
  CONSTRAINT `fk_directors_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.directors 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `directors` DISABLE KEYS */;
REPLACE INTO `directors` (`id`, `name`, `role_id`) VALUES
	(1, '农业局长', 3),
	(2, '财政局长', 4),
	(3, '组织部长', 5),
	(4, '宣传部长', 6);
/*!40000 ALTER TABLE `directors` ENABLE KEYS */;


-- 导出  表 prs.finance_dept 结构
DROP TABLE IF EXISTS `finance_dept`;
CREATE TABLE IF NOT EXISTS `finance_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_finance_dept_role1_idx` (`role_id`),
  CONSTRAINT `fk_finance_dept_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.finance_dept 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `finance_dept` DISABLE KEYS */;
REPLACE INTO `finance_dept` (`id`, `name`, `role_id`) VALUES
	(1, '农业股', 8),
	(2, '教科文股', 10),
	(3, '投资股', 9),
	(4, '行财股', 11),
	(5, '社保股', 12);
/*!40000 ALTER TABLE `finance_dept` ENABLE KEYS */;


-- 导出  表 prs.funds_nature 结构
DROP TABLE IF EXISTS `funds_nature`;
CREATE TABLE IF NOT EXISTS `funds_nature` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.funds_nature 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `funds_nature` DISABLE KEYS */;
REPLACE INTO `funds_nature` (`id`, `name`) VALUES
	(1, '年初预算'),
	(2, '专项资金'),
	(3, '自筹资金'),
	(4, '捐赠资金');
/*!40000 ALTER TABLE `funds_nature` ENABLE KEYS */;


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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.package 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `package` DISABLE KEYS */;
REPLACE INTO `package` (`id`, `package_uuid`, `purchase_id`, `package_name`, `package_code`, `pur_address`, `pur_date`, `expert_count`, `pur_method`, `publicity`, `vendor`, `amount`, `package_activity_id`) VALUES
	(1, 'IABGuM5S8Yl9TUpNrvT4GDe0fngtP5GnQ478', 8, NULL, '1', '1', '2016-10-20', 1, 'gkzb', 0, '1', 1, 3),
	(2, 'Pwzo3hoT5GC2ZVqthZrDtGOFzsttMezOjNiW', 8, NULL, '2', '2', '2', 2, 'gkzb', 0, '2', 2, 4),
	(3, 'Z1CR0wyB3W3LuGr6KouK1S4SqwvnWlrsUNCc', 8, NULL, '3', '3', '3', 3, 'gkzb', 0, '3', 3, 6);
/*!40000 ALTER TABLE `package` ENABLE KEYS */;


-- 导出  表 prs.package_activity 结构
DROP TABLE IF EXISTS `package_activity`;
CREATE TABLE IF NOT EXISTS `package_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.package_activity 的数据：~8 rows (大约)
/*!40000 ALTER TABLE `package_activity` DISABLE KEYS */;
REPLACE INTO `package_activity` (`id`, `status`) VALUES
	(1, 'packaged'),
	(2, 'acceptance'),
	(3, 'applied'),
	(4, 'to_pay'),
	(5, 'partially_paid'),
	(6, 'paid'),
	(7, 'complaints'),
	(8, 'failure');
/*!40000 ALTER TABLE `package_activity` ENABLE KEYS */;


-- 导出  表 prs.package_attach_file 结构
DROP TABLE IF EXISTS `package_attach_file`;
CREATE TABLE IF NOT EXISTS `package_attach_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `path` varchar(512) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  `package_id` int(11) NOT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_package_attach_file_package1_idx` (`package_id`),
  CONSTRAINT `fk_package_attach_file_package1` FOREIGN KEY (`package_id`) REFERENCES `package` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.package_attach_file 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `package_attach_file` DISABLE KEYS */;
REPLACE INTO `package_attach_file` (`id`, `uuid`, `name`, `path`, `size`, `package_id`, `type`) VALUES
	(2, 'o_1avgfbniou65g5e1ro6tha18n0f', 'persistency_cfg.xls', 'D:\\share\\jorhy-prs\\src\\main\\webapp\\upload\\persistency_cfg8.xls', 158208, 3, 'evaluation'),
	(5, 'o_1avgm9rbbbnda931kcm10tp14fj8', 'persistency_cfg.xls', 'D:\\share\\jorhy-prs\\src\\main\\webapp\\upload\\persistency_cfg12.xls', 158208, 3, 'acceptance');
/*!40000 ALTER TABLE `package_attach_file` ENABLE KEYS */;


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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.package_product 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `package_product` DISABLE KEYS */;
REPLACE INTO `package_product` (`id`, `uuid`, `type`, `name`, `count`, `price`, `spec`, `pre_price`, `param`, `packaged_count`, `package_id`) VALUES
	(1, 'kVKDPB74b4K9zRMLhWyJP110CTM9c2Ip0U01', '商品类', '1', 1, 1, '1', 1, '1', 1, 1),
	(2, 'dXzwZBsXJ6e0BMZnQvzcu3QVqCrnDFkc69H8', '服务类', '2', 1, 2, '2', 2, '2', 1, 3);
/*!40000 ALTER TABLE `package_product` ENABLE KEYS */;


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

-- 正在导出表  prs.package_record 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `package_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `package_record` ENABLE KEYS */;


-- 导出  表 prs.permission 结构
DROP TABLE IF EXISTS `permission`;
CREATE TABLE IF NOT EXISTS `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `perm` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.permission 的数据：~9 rows (大约)
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
REPLACE INTO `permission` (`id`, `perm`) VALUES
	(1, 'applicant'),
	(2, 'accounting'),
	(3, 'leader'),
	(4, 'director'),
	(5, 'sector'),
	(6, 'regulatory'),
	(7, 'bureau'),
	(8, 'purchase'),
	(9, 'payment');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;


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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.product 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
REPLACE INTO `product` (`id`, `uuid`, `type`, `name`, `count`, `purchase_id`, `price`, `spec`, `pre_price`, `param`, `packaged_count`) VALUES
	(8, 'kVKDPB74b4K9zRMLhWyJP110CTM9c2Ip0U01', '商品类', '1', 1, 8, 1, '1', 1, '1', 1),
	(9, 'dXzwZBsXJ6e0BMZnQvzcu3QVqCrnDFkc69H8', '服务类', '2', 2, 8, 2, '2', 2, '2', 2),
	(10, 'BcSuQ58B2JO1JQONokJyF27QGMIJlr8NnbC9', '工程类', '3', 3, 8, 3, '3', 3, '3', 0);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;


-- 导出  表 prs.purchase 结构
DROP TABLE IF EXISTS `purchase`;
CREATE TABLE IF NOT EXISTS `purchase` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '采购ID，自增',
  `code` varchar(45) DEFAULT NULL COMMENT '采购编号',
  `purchase_uuid` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `funds_src` varchar(45) DEFAULT NULL,
  `contacts` varchar(45) DEFAULT NULL,
  `phone_num` varchar(45) DEFAULT NULL,
  `purchase_activity_id` int(11) NOT NULL,
  `commodity_pre_price` double DEFAULT NULL,
  `service_pre_price` double DEFAULT NULL,
  `engineering_pre_price` double DEFAULT NULL,
  `dept_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `purchase_type_id` int(11) NOT NULL,
  `purchase_nature_id` int(11) NOT NULL DEFAULT '0',
  `funds_nature_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_purchase_pruchase_activity1_idx` (`purchase_activity_id`),
  KEY `fk_purchase_dept1_idx` (`dept_id`),
  KEY `fk_purchase_role1_idx` (`role_id`),
  KEY `fk_purchase_purchase_type1_idx` (`purchase_type_id`),
  KEY `fk_purchase_purchase_nature1_idx` (`purchase_nature_id`),
  KEY `fk_purchase_funds_nature1_idx` (`funds_nature_id`),
  CONSTRAINT `fk_purchase_dept1` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_funds_nature1` FOREIGN KEY (`funds_nature_id`) REFERENCES `funds_nature` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_pruchase_activity1` FOREIGN KEY (`purchase_activity_id`) REFERENCES `purchase_activity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_purchase_nature1` FOREIGN KEY (`purchase_nature_id`) REFERENCES `purchase_nature` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_purchase_type1` FOREIGN KEY (`purchase_type_id`) REFERENCES `purchase_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchase_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.purchase 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
REPLACE INTO `purchase` (`id`, `code`, `purchase_uuid`, `name`, `funds_src`, `contacts`, `phone_num`, `purchase_activity_id`, `commodity_pre_price`, `service_pre_price`, `engineering_pre_price`, `dept_id`, `role_id`, `purchase_type_id`, `purchase_nature_id`, `funds_nature_id`) VALUES
	(8, '1', '2e5QEb9HanUwBgTHYKvukzsmmzSqRzXdH3fQ', '过程', '1', '1', '1', 8, 100, 100, 100, 1, 15, 1, 5, 0);
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;


-- 导出  表 prs.purchase_activity 结构
DROP TABLE IF EXISTS `purchase_activity`;
CREATE TABLE IF NOT EXISTS `purchase_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.purchase_activity 的数据：~10 rows (大约)
/*!40000 ALTER TABLE `purchase_activity` DISABLE KEYS */;
REPLACE INTO `purchase_activity` (`id`, `status`) VALUES
	(1, 'applicant'),
	(2, 'accounting'),
	(3, 'leader'),
	(4, 'director'),
	(5, 'sector'),
	(6, 'regulatory'),
	(7, 'bureau'),
	(8, 'purchase'),
	(9, 'payment'),
	(10, 'paid');
/*!40000 ALTER TABLE `purchase_activity` ENABLE KEYS */;


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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.purchase_attach_file 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `purchase_attach_file` DISABLE KEYS */;
REPLACE INTO `purchase_attach_file` (`id`, `uuid`, `name`, `path`, `size`, `purchase_id`) VALUES
	(1, 'o_1aumh7qpq1s1i1t8f9jus4nh9t7', 'IMG_20160908_092400_426.jpg', 'D:\\share\\jorhy-prs\\src\\main\\webapp\\upload\\IMG_20160908_092400_426.jpg', 2720982, 8);
/*!40000 ALTER TABLE `purchase_attach_file` ENABLE KEYS */;


-- 导出  表 prs.purchase_nature 结构
DROP TABLE IF EXISTS `purchase_nature`;
CREATE TABLE IF NOT EXISTS `purchase_nature` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.purchase_nature 的数据：~8 rows (大约)
/*!40000 ALTER TABLE `purchase_nature` DISABLE KEYS */;
REPLACE INTO `purchase_nature` (`id`, `name`) VALUES
	(1, '公开招标'),
	(2, '询价采购'),
	(3, '竞争性谈判'),
	(4, '竞争性磋商'),
	(5, '单一来源'),
	(6, '商场直购'),
	(7, '网上竞价'),
	(8, '自行采购');
/*!40000 ALTER TABLE `purchase_nature` ENABLE KEYS */;


-- 导出  表 prs.purchase_type 结构
DROP TABLE IF EXISTS `purchase_type`;
CREATE TABLE IF NOT EXISTS `purchase_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.purchase_type 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `purchase_type` DISABLE KEYS */;
REPLACE INTO `purchase_type` (`id`, `name`) VALUES
	(1, '一级单位'),
	(2, '二级单位');
/*!40000 ALTER TABLE `purchase_type` ENABLE KEYS */;


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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.role 的数据：~16 rows (大约)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
REPLACE INTO `role` (`id`, `role`, `post`, `user_id`, `permission_id`) VALUES
	(1, 'applicant', '办事员', 1, 1),
	(2, 'accounting', '单位会计', 2, 2),
	(3, 'agriculture_leader', '分管农业局长', 3, 3),
	(4, 'financial_leader', '分管财政局长', 4, 3),
	(5, 'organization_leader', '分管组织部长', 5, 3),
	(6, 'propaganda_leader', '分管宣传部长', 6, 3),
	(7, 'director', '局长', 7, 4),
	(8, 'agriculture_sector', '农业股', 8, 5),
	(9, 'ESC_sector', '教科文股', 9, 5),
	(10, 'investment_sector', '投资股', 10, 5),
	(11, 'AF_sector', '行财股', 11, 5),
	(12, 'SS_sector', '社保股', 12, 5),
	(13, 'regulatory_unit', '财政监管股', 13, 6),
	(14, 'financial_bureau', '财政局局长', 14, 7),
	(15, 'purchase', '执行部门', 15, 8),
	(16, 'payment', '支付中心', 16, 9);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- 正在导出表  prs.user 的数据：~16 rows (大约)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
REPLACE INTO `user` (`id`, `username`, `passwd`, `dept_id`, `real_name`) VALUES
	(1, '1', '1', 1, '赵某某'),
	(2, '2', '2', 1, '钱会计'),
	(3, '3', '3', 1, '孙局长'),
	(4, '4', '4', 1, '李局长'),
	(5, '5', '5', 1, '周部长'),
	(6, '6', '6', 1, '吴部长'),
	(7, '7', '7', 2, '小郑'),
	(8, '8', '8', 2, '小王'),
	(9, '9', '9', 2, '小冯'),
	(10, '10', '10', 2, '小陈'),
	(11, '11', '11', 2, '小褚'),
	(12, '12', '12', 2, '卫局长'),
	(13, '13', '13', 3, '蒋工'),
	(14, '14', '14', 1, '沈会计'),
	(15, '15', '15', 2, '李XX'),
	(16, '16', '16', 1, '李镇长');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
