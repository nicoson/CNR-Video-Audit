-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 123.59.204.171    Database: qiniu
-- ------------------------------------------------------
-- Server version	5.7.22-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `review_level_setting`
--

DROP TABLE IF EXISTS `review_level_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review_level_setting` (
  `id` varchar(36) NOT NULL COMMENT 'id',
  `ops_op` varchar(20) DEFAULT NULL COMMENT '项目名称：pulp:监黄；terror:鉴暴；politician:政治人物',
  `ops_op_label` varchar(1) DEFAULT NULL,
  `ops_op_level` varchar(1) DEFAULT NULL COMMENT '审核等级：【0,1,2...】',
  `created_by` varchar(36) DEFAULT NULL COMMENT '创建人',
  `created_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(36) DEFAULT NULL COMMENT '更新人',
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flg` varchar(1) DEFAULT '0' COMMENT '删除标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review_level_setting`
--

LOCK TABLES `review_level_setting` WRITE;
/*!40000 ALTER TABLE `review_level_setting` DISABLE KEYS */;
INSERT INTO `review_level_setting` VALUES ('105789d91b1c4d2a9e84ad2ddb6e9041','pulp','0','3',NULL,'2018-05-18 14:51:52',NULL,'2018-05-18 14:51:52','0'),('1acfb90086a24161a453aec7e8d8a861','pulp','2','0',NULL,'2018-05-18 14:51:52',NULL,'2018-05-18 14:51:52','0'),('2a8dfc312a084713ae8f33a5cd8d3446','politician','0','0',NULL,'2018-05-18 14:51:52',NULL,'2018-05-18 14:51:52','0'),('3f381490abf8436aa0d832a43112f849','politician','1','2',NULL,'2018-05-18 14:51:52',NULL,'2018-05-18 14:51:52','0'),('5d2e0ffb5a974f07ac78025696a78a29','pulp','1','1',NULL,'2018-05-18 14:51:52',NULL,'2018-05-18 14:51:52','0'),('a9f7ba1839d04f608cb3b58b48f89ca1','terror','1','3',NULL,'2018-05-18 14:51:52',NULL,'2018-05-18 14:51:52','0'),('d11d38b3f1204a2aaa8e4d664b84ada6','terror','0','0',NULL,'2018-05-18 14:51:52',NULL,'2018-05-18 14:51:52','0');
/*!40000 ALTER TABLE `review_level_setting` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-11 15:35:26
