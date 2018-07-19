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
-- Table structure for table `sys_dict`
--

DROP TABLE IF EXISTS `sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dict` (
  `id` varchar(36) NOT NULL,
  `type` varchar(36) DEFAULT NULL,
  `description` varchar(36) DEFAULT NULL,
  `sort` varchar(36) DEFAULT NULL,
  `value` varchar(50) DEFAULT NULL,
  `label` varchar(36) DEFAULT NULL,
  `remarks` varchar(36) DEFAULT NULL,
  `created_by` varchar(36) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(36) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `del_flg` varchar(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict`
--

LOCK TABLES `sys_dict` WRITE;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
INSERT INTO `sys_dict` VALUES ('qn100001','review_level','危险等级','10','0','无',NULL,NULL,NULL,NULL,NULL,'0'),('qn100002','review_level','危险等级','20','1','低',NULL,NULL,NULL,NULL,NULL,'0'),('qn100003','review_level','危险等级','30','2','中',NULL,NULL,NULL,NULL,NULL,'0'),('qn100004','review_level','危险等级','40','3','高',NULL,NULL,NULL,NULL,NULL,'0'),('qn100005','review_level','危险等级','50','99','未知',NULL,NULL,NULL,NULL,NULL,'0'),('qn100006','review_level_setting','设置危险等级','10','0','无',NULL,NULL,NULL,NULL,NULL,'0'),('qn100007','review_level_setting','设置危险等级','20','1','低',NULL,NULL,NULL,NULL,NULL,'0'),('qn100008','review_level_setting','设置危险等级','30','2','中',NULL,NULL,NULL,NULL,NULL,'0'),('qn100009','review_level_setting','设置危险等级','40','3','高',NULL,NULL,NULL,NULL,NULL,'0'),('qn100010','yes_no','是/否','10','Y','是',NULL,NULL,NULL,NULL,NULL,'0'),('qn100011','yes_no','是/否','20','N','否',NULL,NULL,NULL,NULL,NULL,'0'),('qn100012','del_flg','删除标识','10','0','未删除',NULL,NULL,NULL,NULL,NULL,'0'),('qn100013','del_flg','删除标识','20','1','删除',NULL,NULL,NULL,NULL,NULL,'0'),('qn100014','machine_artificial','机审/人审标识','10','0','机审',NULL,NULL,NULL,NULL,NULL,'0'),('qn100015','machine_artificial','机审/人审标识','20','1','人审',NULL,NULL,NULL,NULL,NULL,'0'),('qn100016','review_stage','审核阶段','10','0','未审核',NULL,NULL,NULL,NULL,NULL,'0'),('qn100017','review_stage','审核阶段','20','1','初步审核(机审结果)',NULL,NULL,NULL,NULL,NULL,'0'),('qn100018','review_stage','审核阶段','30','2','审核结束',NULL,NULL,NULL,NULL,NULL,'0'),('qn100019','review_state','审核状态','10','0','未开始',NULL,NULL,NULL,NULL,NULL,'0'),('qn100020','review_state','审核状态','20','1','处理中',NULL,NULL,NULL,NULL,NULL,'0'),('qn100021','review_state','审核状态','30','2','通过',NULL,NULL,NULL,NULL,NULL,'0'),('qn100022','review_state','审核状态','40','3','不通过',NULL,NULL,NULL,NULL,NULL,'0'),('qn100023','check_result_type','审核结果类型','10','0','涉政事件',NULL,NULL,NULL,NULL,NULL,'0'),('qn100024','check_result_type','审核结果类型','20','1','涉政人物',NULL,NULL,NULL,NULL,NULL,'0'),('qn100025','check_result_type','审核结果类型','30','2','暴恐',NULL,NULL,NULL,NULL,NULL,'0'),('qn100026','check_result_type','审核结果类型','40','3','黄色',NULL,NULL,NULL,NULL,NULL,'0'),('qn100027','check_result_type','审核结果类型','50','4','性感',NULL,NULL,NULL,NULL,NULL,'0'),('qn100028','review_type','审核任务类型','10','1','机审',NULL,NULL,NULL,NULL,NULL,'0'),('qn100029','review_type','审核任务类型','20','2','人审',NULL,NULL,NULL,NULL,NULL,'0'),('qn100030','video_type','视频类型','10','1','点播',NULL,NULL,NULL,NULL,NULL,'0'),('qn100031','video_type','视频类型','20','2','直播',NULL,NULL,NULL,NULL,NULL,'0'),('qn100032','live_end_flg','直播停止标识','10','0','直播中',NULL,NULL,NULL,NULL,NULL,'0'),('qn100033','live_end_flg','直播停止标识','20','1','直播停止',NULL,NULL,NULL,NULL,NULL,'0');
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-11 15:35:25
