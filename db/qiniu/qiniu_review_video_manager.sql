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
-- Table structure for table `review_video_manager`
--

DROP TABLE IF EXISTS `review_video_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review_video_manager` (
  `id` varchar(36) NOT NULL COMMENT 'videoId',
  `job_id` varchar(48) DEFAULT NULL COMMENT '审核任务id',
  `source` varchar(300) DEFAULT NULL COMMENT '视频资源的原始地址',
  `video_uri` varchar(300) DEFAULT NULL COMMENT '视频资源的归档地址',
  `video_cover` varchar(300) DEFAULT NULL COMMENT '视频封面的地址',
  `review_stage` varchar(1) DEFAULT NULL COMMENT '审核阶段 0 ：未审核； 1 ：初步审核（机审结果）； 2 ：审核结束',
  `review_level` varchar(2) DEFAULT NULL COMMENT '审核的结果等级  0 ：无； 1 ：低； 2 ：中； 3 ：高',
  `review_manual_level` varchar(2) DEFAULT NULL,
  `review_robotic_level` varchar(2) DEFAULT NULL COMMENT '机器审核的结果等级',
  `review_robotic_score` decimal(5,2) DEFAULT NULL COMMENT '机器审核的可行性度',
  `review_ops_pulp_manual_level` varchar(1) DEFAULT NULL COMMENT '监黄人工审核的结果等级',
  `review_ops_pulp_robotic_level` varchar(1) DEFAULT NULL COMMENT '监黄机器审核的结果等级',
  `review_ops_pulp_robotic_score` decimal(5,2) DEFAULT NULL COMMENT '鉴黄的机器审核的可行性度',
  `review_ops_terror_manual_level` varchar(1) DEFAULT NULL COMMENT '鉴暴的人工审核的结果等级',
  `review_ops_terror_robotic_level` varchar(1) DEFAULT NULL COMMENT '鉴暴的机器审核的结果等级',
  `review_ops_terror_robotic_score` decimal(5,2) DEFAULT NULL COMMENT '鉴暴的机器审核的可行性度',
  `review_ops_politician_manual_level` varchar(1) DEFAULT NULL COMMENT '政治人物的人工审核的结果等级',
  `review_ops_politician_robotic_level` varchar(1) DEFAULT NULL COMMENT '政治人物的机器审核的结果等级',
  `review_ops_politician_robotic_score` decimal(5,2) DEFAULT NULL COMMENT '政治人物的机器审核的可行性度',
  `video_name` varchar(100) DEFAULT NULL COMMENT '视频名称',
  `video_time` varchar(100) DEFAULT NULL COMMENT '视频时长',
  `reviewer_id` varchar(36) DEFAULT NULL COMMENT '审核人ID',
  `created_by` varchar(36) DEFAULT NULL COMMENT '创建人',
  `created_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(36) DEFAULT NULL COMMENT '更新人',
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flg` varchar(1) DEFAULT NULL COMMENT '删除标志',
  `video_type` varchar(1) DEFAULT NULL COMMENT '视频类型 2 :直播；1：点播',
  `live_end` varchar(1) DEFAULT NULL COMMENT '直播结束标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review_video_manager`
--

LOCK TABLES `review_video_manager` WRITE;
/*!40000 ALTER TABLE `review_video_manager` DISABLE KEYS */;
INSERT INTO `review_video_manager` VALUES ('01ef45cc51d645a9922a0e0900f87f05','1526556903192373651_xvRLKrdBpeu7E5XdObIxJA','http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8','http://pili-live-hls.argo.qnsdk.com/argo/1526556903192373651_xvRLKrdBpeu7E5XdObIxJA.m3u8',NULL,'0','99','99','99',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'直播2',NULL,NULL,NULL,'2018-05-17 20:24:26',NULL,'2018-05-17 20:24:26','1','2','1'),('02a1fa1e506c43eabe3e341914059b03','5b0660d7e7134b000767c71d','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ejm841l0ro85an41c8f908bh.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ejm841l0ro85an41c8f908bh_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ejm841l0ro85an41c8f908bh_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'巴尼正传.ts_ 出现反共主义信息（麦卡锡主义）.mkv',NULL,NULL,NULL,'2018-05-24 14:51:04',NULL,'2018-05-24 14:51:16','0','1',NULL),('04c616dacd774622ab1ebcc1a423c46a','5afd32b12f48630007daac09','http://p8d4x5qlh.bkt.clouddn.com/o_1cdmgrsfr19581049hrr1e9f1c5e9.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1cdmgrsfr19581049hrr1e9f1c5e9.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1cdmgrsfr19581049hrr1e9f1c5e9_C.jpg','1','3','99','3',0.84,NULL,'1',0.90,NULL,'1',0.84,NULL,'0',1.00,'情圣.mp4','148.240000','Q0001',NULL,'2018-05-17 20:24:28',NULL,'2018-05-17 20:24:28','1','1',NULL),('057167e84bc64ef0b8a5642ae0aa87dd','5b06921c4f73e00006da632d','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qkfettqo1crq1uq93b61ek18p.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qkfettqo1crq1uq93b61ek18p.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qkfettqo1crq1uq93b61ek18p_C.jpg','1','3','99','3',0.52,NULL,'0',0.52,NULL,'0',1.00,NULL,'0',1.00,'癫狂之旅第1季02.ts_男性露上半身.mkv',NULL,'Q0001',NULL,'2018-05-24 18:21:17',NULL,'2018-05-24 18:21:39','0','1',NULL),('07660c191b894be8a010d9ed1fb4e1ed','1526559888420741218_pFAfl4zmjxFXFi3uxkh84A','http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8','http://pili-live-hls.argo.qnsdk.com/argo/1526559888420741218_pFAfl4zmjxFXFi3uxkh84A.m3u8',NULL,'0','99','99','99',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'直播测试44444',NULL,NULL,NULL,'2018-05-17 20:24:49',NULL,'2018-05-17 21:28:00','0','2','1'),('086a29182d2a46c0a22043969deae5cc','1526555052248121944_RLKQlzY4HB0nksftdXN7gg','http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8','http://pili-live-hls.argo.qnsdk.com/argo/1526555052248121944_RLKQlzY4HB0nksftdXN7gg.m3u8',NULL,'0','99','99','99',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'直播1',NULL,NULL,NULL,'2018-05-17 20:24:27',NULL,'2018-05-17 20:24:27','1','2','1'),('0d7f03a410014400bc0b1cfe0dcea234','1526563680138595025_H7fZy1mCJTWokl8zsVv8rw','http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8','http://pili-live-hls.argo.qnsdk.com/argo/1526563680138595025_H7fZy1mCJTWokl8zsVv8rw.m3u8',NULL,'0','99',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0d7f03a410014400bc0b1cfe0dcea234',NULL,NULL,NULL,'2018-05-18 14:47:45',NULL,'2018-05-18 14:47:45','0','2','1'),('1c3d72177bd14a5f9f588dbcf74b97ec','5b0227befb09fe0006d8617b','http://p8d4x5qlh.bkt.clouddn.com/o_1ce06m9be1vec17951dg77shoes3k.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce06m9be1vec17951dg77shoes3k.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce06m9be1vec17951dg77shoes3k_C.jpg','2','3','3','3',1.00,'1','1',1.00,'1','1',1.00,NULL,'0',1.00,'bandicam_conv.mp4','150.000000','Q0001',NULL,'2018-05-21 09:58:24','Q0001','2018-05-24 18:37:07','0','1',NULL),('1cf505f5eecc498b9d8d091cc1551266','5b0692928d938d0007060aed','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qo69c1ivnqqf15g11q3e19crfh.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qo69c1ivnqqf15g11q3e19crfh_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qo69c1ivnqqf15g11q3e19crfh_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'隐秘线索.ts_亲吻镜头.mkv',NULL,NULL,NULL,'2018-05-24 18:23:14',NULL,'2018-05-24 18:23:27','0','1',NULL),('1eae8040a4304105894abfd186812793','5b06619d3b1e310008ef5516','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ep6a5em014rk32m4tr1f82jh.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ep6a5em014rk32m4tr1f82jh.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ep6a5em014rk32m4tr1f82jh_C.jpg','1','3','99','3',0.99,NULL,'2',1.00,NULL,'1',0.99,NULL,'0',1.00,'速度与激情4_同性亲吻画面.mp4','79.722542','Q0001',NULL,'2018-05-24 14:54:22',NULL,'2018-05-24 14:55:42','0','1',NULL),('2657571775c4496cb49041f108fd02ca','5b025da328ecf40007a37859','http://p8d4x5qlh.bkt.clouddn.com/o_1ce0jtghll36jrtll21nmi3fgkc.mpeg','http://p8d4x5qlh.bkt.clouddn.com/o_1ce0jtghll36jrtll21nmi3fgkc.mpeg','http://p8d4x5qlh.bkt.clouddn.com/o_1ce0jtghll36jrtll21nmi3fgkc_C.jpg','2','3','3','3',0.96,NULL,'2',1.00,'1','1',0.96,NULL,'0',1.00,'亡灵.mpeg',NULL,'Q0001',NULL,'2018-05-21 13:48:23','Q0001','2018-05-24 18:37:26','0','1',NULL),('26a7f7b3db9a4a388a6eb9f244c522b0','5b066043984fe600070b0b83','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg9119ti6jn1a5118etolh2b.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg9119ti6jn1a5118etolh2b.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg9119ti6jn1a5118etolh2b_C.jpg','2','1','1','1',0.55,'1','1',0.55,NULL,'0',1.00,NULL,'0',1.00,'情敌复仇战.ts_语言粗俗.mkv',NULL,'Q0001',NULL,'2018-05-24 14:48:36','Q0001','2018-05-24 18:39:51','0','1',NULL),('26ec2606b1864aa0b11da72a12a2194e','5afd342683f62f000706bcea','http://p8d4x5qlh.bkt.clouddn.com/o_1cdmh79ru1g531q7i14ln1uijdr69.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1cdmh79ru1g531q7i14ln1uijdr69.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1cdmh79ru1g531q7i14ln1uijdr69_C.jpg','1','1','99','1',0.84,NULL,'1',0.90,NULL,'1',0.84,NULL,'0',1.00,'情圣.mp4','148.240000','Q0001',NULL,'2018-05-17 20:24:28',NULL,'2018-05-17 20:24:28','1','1',NULL),('2ae681a9eae54a928a889b2b6e6860f7','5b0258b0f4eabe0007a81ad1','http://p8d4x5qlh.bkt.clouddn.com/o_1ce0iraf26p71omi17hvoom1t6p9.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce0iraf26p71omi17hvoom1t6p9.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce0iraf26p71omi17hvoom1t6p9_C.jpg','2','3','3','3',0.98,'1','1',1.00,'1','1',0.98,NULL,'0',1.00,'人类感官3.mp4','63.560000','Q0001',NULL,'2018-05-21 13:27:14','Q0001','2018-05-24 18:36:44','0','1',NULL),('2b63acdc9fbf4e6ba1b804788d8a39a1','5b0259b106f8150007930767','http://p8d4x5qlh.bkt.clouddn.com/o_1ce0j36pfof51l6711tq8bb1hvcj5.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce0j36pfof51l6711tq8bb1hvcj5.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce0j36pfof51l6711tq8bb1hvcj5_C.jpg','2','3','3','3',0.98,'1','1',1.00,'1','1',0.98,NULL,'0',1.00,'人类感官3.mp4','63.560000','Q0001',NULL,'2018-05-21 13:31:30','Q0001','2018-05-24 18:37:15','0','1',NULL),('2caefd4edd2f409286af5e98ca88c9dd','5b069232e74cf10007aa5840','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ql68q1pb0170tu7b1070161q9f.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ql68q1pb0170tu7b1070161q9f.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ql68q1pb0170tu7b1070161q9f_C.jpg','1','3','99','3',0.61,NULL,'0',0.61,NULL,'0',1.00,NULL,'0',1.00,'曼哈顿夜曲.TS_女性半裸和亲吻镜头.mkv',NULL,'Q0001',NULL,'2018-05-24 18:21:58',NULL,'2018-05-24 18:23:00','0','1',NULL),('2fb1d054e84b48bbbe0e0417daf9af86','5afe5eb4f4eabe0007a802df','http://p8d4x5qlh.bkt.clouddn.com/o_1cdoq3igbtbj1ri914fp1vfcfh9.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1cdoq3igbtbj1ri914fp1vfcfh9.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1cdoq3igbtbj1ri914fp1vfcfh9_C.jpg','2','3','3','3',0.84,'1','1',0.90,'1','1',0.84,NULL,'0',1.00,'情圣.mp4','148.240000','Q0001',NULL,'2018-05-18 13:03:50','Q0001','2018-05-18 13:35:11','0','1',NULL),('38811a921d16406582ba49c10f5cd9cc','5b06605d0e7dae00076a4ce3','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg911ane1flm1t1d4g5pj32c.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg911ane1flm1t1d4g5pj32c.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg911ane1flm1t1d4g5pj32c_C.jpg','1','3','99','3',0.99,NULL,'2',1.00,NULL,'1',0.99,NULL,'0',1.00,'速度与激情4_同性亲吻画面.mp4','79.722542','Q0001',NULL,'2018-05-24 14:49:02',NULL,'2018-05-24 14:50:10','0','1',NULL),('3b73f2942c3f4205b5f8af8365219f55','5b069287f9e6d800069a95a7','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qnmul1rg31vstep78jp1m1qej.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qnmul1rg31vstep78jp1m1qej.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qnmul1rg31vstep78jp1m1qej_C.jpg','1','3','99','3',0.47,NULL,'0',0.47,NULL,'0',1.00,NULL,'0',1.00,'侠探杰克：永不回头.ts_男女裸露上半身.mkv',NULL,'Q0001',NULL,'2018-05-24 18:23:04',NULL,'2018-05-24 18:23:30','0','1',NULL),('3de613b2970e4f57b49c81e007d7367f','5b0661843be12800074c00ce','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8eooh9q971a4n5ni1oja1j99ib.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8eooh9q971a4n5ni1oja1j99ib.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8eooh9q971a4n5ni1oja1j99ib_C.jpg','2','1','1','1',0.55,'1','1',0.55,NULL,'0',1.00,NULL,'0',1.00,'情敌复仇战.ts_语言粗俗.mkv',NULL,'Q0001',NULL,'2018-05-24 14:53:57','Q0001','2018-05-24 18:41:35','0','1',NULL),('3e716bbbb7ff433ba66a33f5c788cbb3','5afc6227a0304600078c6bfc','http://p8d4x5qlh.bkt.clouddn.com/o_1cdktuk571d523302qv1va818vs9.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1cdktuk571d523302qv1va818vs9.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1cdktuk571d523302qv1va818vs9_C.jpg','1','3','99','3',0.84,NULL,'1',0.90,NULL,'1',0.84,NULL,'0',1.00,'??.mp4','148.240000','Q0001',NULL,'2018-05-17 20:24:28',NULL,'2018-05-17 20:24:28','1','1',NULL),('3fc7529ae5c84469bd431dd8b7f71480','5b06610d8d938d0007060979','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ekeunlejcp15gunsj110d3.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ekeunlejcp15gunsj110d3_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ekeunlejcp15gunsj110d3_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'坏中尉.ts_吸食毒品和色情镜头.mkv',NULL,NULL,NULL,'2018-05-24 14:51:58',NULL,'2018-05-24 14:53:40','0','1',NULL),('41cfe984509446108227d5cd318db6f0','5afe5897d00c4e00075451b9','http://p8d4x5qlh.bkt.clouddn.com/o_1cdoojqseffj14s0uaa1sak1gor9.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1cdoojqseffj14s0uaa1sak1gor9.mp4',NULL,'1','3','99','3',0.94,NULL,'2',1.00,NULL,'1',0.94,NULL,'0',1.00,'bct.mp4','121.666667','Admin',NULL,'2018-05-21 10:24:15',NULL,'2018-05-21 10:24:15','0','1',NULL),('462b037d6c8d43749e08dc2438ce82a2','5b0692603be12800074c0241','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qmgupcrq1lgmqa1mc5pdcc7.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qmgupcrq1lgmqa1mc5pdcc7_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qmgupcrq1lgmqa1mc5pdcc7_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'铁杉树丛第1季01 .ts_女性露点和性爱行为.mkv',NULL,NULL,NULL,'2018-05-24 18:22:25',NULL,'2018-05-24 18:22:51','0','1',NULL),('4b0c73fbb63c45d69e551882e0b2c2b4','5b06629a8d938d0007060983','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8f1aneag815g31duv1mfr155vn7.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8f1aneag815g31duv1mfr155vn7_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8f1aneag815g31duv1mfr155vn7_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'虎胆龙威2.ts_血腥画面.mp4','23.240000',NULL,NULL,'2018-05-24 14:58:35',NULL,'2018-05-24 14:59:37','0','1',NULL),('4d136e793a984bcea14c31f645a2fd56','5b0691e7f9e6d800069a95a6','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qiqlc1nke13kp1r3t16l41v5c6d.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qiqlc1nke13kp1r3t16l41v5c6d.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qiqlc1nke13kp1r3t16l41v5c6d_C.jpg','1','3','99','3',1.00,NULL,'0',1.00,NULL,'0',1.00,NULL,'0',1.00,'阿尔法屋第二季-10-男女有裸露镜头.mkv',NULL,'Q0001',NULL,'2018-05-24 18:20:24',NULL,'2018-05-24 18:20:50','0','1',NULL),('4ffcb8ed823e4c009e3c18a75d0b61e9','5b0691954f73e00006da632b','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qg1ll7s8bfd1tpl3a7mt013.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qg1ll7s8bfd1tpl3a7mt013.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qg1ll7s8bfd1tpl3a7mt013_C.jpg','1','3','99','3',0.39,NULL,'0',0.39,NULL,'0',1.00,NULL,'0',1.00,'边桥谜案第2季09.ts_20180524_164513.mkv',NULL,NULL,NULL,'2018-05-24 18:33:55',NULL,'2018-05-24 18:33:55','1','1',NULL),('549c33cbfae04638be849dba990e21b7','5b069277afcf3c000741479d','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qn28m17ndtat1h9r14dm1rqdd5.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qn28m17ndtat1h9r14dm1rqdd5_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qn28m17ndtat1h9r14dm1rqdd5_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'铁杉树丛第1季06.ts_亲吻和性爱镜头.mkv',NULL,NULL,NULL,'2018-05-24 18:22:48',NULL,'2018-05-24 18:23:35','0','1',NULL),('5b1ad15d392f459ea062d5a0fee783c0','1526624219914468798_3roBhR1lsYk33GSXYW3SxA','http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8','http://pili-live-hls.argo.qnsdk.com/argo/1526624219914468798_3roBhR1lsYk33GSXYW3SxA.m3u8',NULL,'0','99','99','99',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'5.18直播1',NULL,NULL,NULL,'2018-05-18 14:46:35',NULL,'2018-05-18 14:46:35','0','2','1'),('5ba9890a4722421bbac5ea861eb80c28','5afc5fa183f62f000706b972','http://p8d4x5qlh.bkt.clouddn.com/o_1cdktau9f15vj1d1ppvhnfl15g29.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1cdktau9f15vj1d1ppvhnfl15g29.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1cdktau9f15vj1d1ppvhnfl15g29_C.jpg','1','3','99','3',0.84,NULL,'1',0.90,NULL,'1',0.84,NULL,'0',1.00,'??.mp4','148.240000','Q0001',NULL,'2018-05-17 20:24:29',NULL,'2018-05-17 20:24:29','1','1',NULL),('5cca04dfdbe74ad2be8c6952f7b44389','1526555450723084444_vkhy7nuPZ7qtuQN8XFhPAg','http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8','http://pili-live-hls.argo.qnsdk.com/argo/1526555450723084444_vkhy7nuPZ7qtuQN8XFhPAg.m3u8',NULL,'0','99','99','99',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'直播2',NULL,NULL,NULL,'2018-05-17 20:24:27',NULL,'2018-05-17 20:24:27','1','2','1'),('5dd09bd0d4a74b4eafef2826217cd43f','5b069198e74cf10007aa583f','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qh5u61q2f136l11to17lo17cu4l.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qh5u61q2f136l11to17lo17cu4l.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qh5u61q2f136l11to17lo17cu4l_C.jpg','2','1','1','1',0.99,'1','1',0.99,NULL,'0',1.00,NULL,'0',1.00,'哥谭镇第一季-9-同性亲吻.mkv',NULL,'Q0001',NULL,'2018-05-24 18:19:23','Q0001','2018-05-24 18:40:11','0','1',NULL),('5e026a28c5f6453abd6fdf48499dd287','1526557705154960053_mkqyxhKyrO3GTVxBgAKLxw','http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8','http://pili-live-hls.argo.qnsdk.com/argo/1526557705154960053_mkqyxhKyrO3GTVxBgAKLxw.m3u8',NULL,'0','99',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'5e026a28c5f6453abd6fdf48499dd287',NULL,NULL,NULL,'2018-05-17 20:24:26',NULL,'2018-05-17 20:24:26','1','2','1'),('6222d8b3a83e4aefa00bfea66c34a9e1','5b06615a3b1e310008ef5514','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ennot1n73dai11ag1fevfggv.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ennot1n73dai11ag1fevfggv_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ennot1n73dai11ag1fevfggv_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'金矿.ts_敏感词汇-滚床单.mkv',NULL,NULL,NULL,'2018-05-24 14:53:15',NULL,'2018-05-24 14:53:20','0','1',NULL),('68b1794468214bf4b893aa3d8b4b3c95','1526560999612541290_SNpsFnL6MYNA6Y3nrERqeQ','http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8','http://pili-live-hls.argo.qnsdk.com/argo/1526560999612541290_SNpsFnL6MYNA6Y3nrERqeQ.m3u8',NULL,'0','99','99','99',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'直播5',NULL,NULL,NULL,'2018-05-18 14:10:20',NULL,'2018-05-18 14:10:20','0','2','1'),('6a4177b11a774996bbc2a1c7fed547bd','5b0661a63b1e310008ef5517','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8eq0791cefve41i951suvudilv.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8eq0791cefve41i951suvudilv.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8eq0791cefve41i951suvudilv_C.jpg','1','3','99','3',0.98,NULL,'2',1.00,NULL,'1',0.98,NULL,'0',1.00,'血域燃烧.ts_血腥镜头.mkv',NULL,'Q0001',NULL,'2018-05-24 14:54:31',NULL,'2018-05-24 14:54:48','0','1',NULL),('6c6fa8f3111f41e3b13f05ff26f1e4ac','5b0691fcafcf3c000741479c','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qjbff1pve1fik1ku03ueb0q7b.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qjbff1pve1fik1ku03ueb0q7b.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qjbff1pve1fik1ku03ueb0q7b_C.jpg','1','3','99','3',0.39,NULL,'0',0.39,NULL,'0',1.00,NULL,'0',1.00,'边桥谜案第2季09.ts_20180524_164513.mkv',NULL,'Q0001',NULL,'2018-05-24 18:20:45',NULL,'2018-05-24 18:21:24','0','1',NULL),('6e1102fd1151476aaaf96ab969721d70','5afc30de6ff1e00007d7628a','http://ozd3w1on4.bkt.clouddn.com/o_1cdkhtiq91tqu1ug4158h1tuucmk9.mp4','http://ozd3w1on4.bkt.clouddn.com/o_1cdkhtiq91tqu1ug4158h1tuucmk9.mp4','http://ozd3w1on4.bkt.clouddn.com/o_1cdkhtiq91tqu1ug4158h1tuucmk9_C.jpg','1','3','99','3',0.84,NULL,'1',0.90,NULL,'1',0.84,NULL,'0',1.00,'情圣.mp4','148.240000','Q0001',NULL,'2018-05-17 20:24:29',NULL,'2018-05-17 20:24:29','1','1',NULL),('702afa9170084924b78aa27358fcf813','5b06616c8d938d000706097b','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8eo91f168p18jfgonpitonrhn.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8eo91f168p18jfgonpitonrhn_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8eo91f168p18jfgonpitonrhn_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'念念.ts_人物雕塑上面出现”民族救星“字样.mkv',NULL,NULL,NULL,'2018-05-24 14:53:33',NULL,'2018-05-24 14:53:43','0','1',NULL),('73583419d72848c3a944e042bdfff89e','5b06602a3b1e310008ef550a','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg91hjb136850r1e42i7r25.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg91hjb136850r1e42i7r25_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg91hjb136850r1e42i7r25_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'坏中尉.ts_吸食毒品和色情镜头.mkv',NULL,NULL,NULL,'2018-05-24 14:48:11',NULL,'2018-05-24 14:49:56','0','1',NULL),('7854437338d74b99ad72c4c74d92c2ba','5b02596ffb09fe0006d862f3','http://p8d4x5qlh.bkt.clouddn.com/o_1ce0irj2g1rk917krimv1sbk9b2v.mpeg','http://p8d4x5qlh.bkt.clouddn.com/o_1ce0irj2g1rk917krimv1sbk9b2v.mpeg','http://p8d4x5qlh.bkt.clouddn.com/o_1ce0irj2g1rk917krimv1sbk9b2v_C.jpg','2','3','3','3',1.00,'0','0',1.00,NULL,'0',1.00,NULL,'0',1.00,'闪灵.mpeg',NULL,'Q0001',NULL,'2018-05-21 13:30:24','Q0001','2018-05-24 18:38:26','0','1',NULL),('822bd4b447274daca1257c50d9c193ae','1526554380302100640_ytEuQ7vnRsyvVnEL4t8TrA','http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8','http://pili-live-hls.argo.qnsdk.com/argo/1526554380302100640_ytEuQ7vnRsyvVnEL4t8TrA.m3u8',NULL,'0','99',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'822bd4b447274daca1257c50d9c193ae',NULL,NULL,NULL,'2018-05-17 20:24:27',NULL,'2018-05-17 20:24:27','1','2','1'),('89074000e22343d88ca134393aaa9dbd','5b066032f9e6d800069a942c','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg91pk51kjr1n7d138q39o27.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg91pk51kjr1n7d138q39o27_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg91pk51kjr1n7d138q39o27_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'金矿.ts_敏感词汇-滚床单.mkv',NULL,NULL,NULL,'2018-05-24 14:48:19',NULL,'2018-05-24 14:48:29','0','1',NULL),('8e99693e0928432ca9dff0341aa66612','5b066039f9e6d800069a942d','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg91pqp3gn4s4158h1fjd29.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg91pqp3gn4s4158h1fjd29_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg91pqp3gn4s4158h1fjd29_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'念念.ts_人物雕塑上面出现”民族救星“字样.mkv',NULL,NULL,NULL,'2018-05-24 14:48:26',NULL,'2018-05-24 14:48:35','0','1',NULL),('8ffaf497d14944ddaaae279fad80637f','5b065fca984fe600070b0b80','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ebf5uct6128512p71fj2ji9.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ebf5uct6128512p71fj2ji9_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ebf5uct6128512p71fj2ji9_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'巴尼正传.ts_ 出现反共主义信息（麦卡锡主义）.mkv',NULL,NULL,NULL,'2018-05-24 14:46:35',NULL,'2018-05-24 14:46:54','0','1',NULL),('9394ab9cfdc14851b7626ca035a5e2c8','5afd5b58a0304600078c7071','http://p8d4x5qlh.bkt.clouddn.com/o_1cdmqid79bgi127s13i31o6413r79.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1cdmqid79bgi127s13i31o6413r79_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1cdmqid79bgi127s13i31o6413r79_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'[三S三S十里桃H]第26集v2_hd.mp4','2764.196281',NULL,NULL,'2018-05-17 20:24:27',NULL,'2018-05-17 20:24:27','1','1',NULL),('9397b5de4ead480a9ce5d1e9a7b9be66','5b06603b8d938d000706096f','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg911knfo35158t17761km32a.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg911knfo35158t17761km32a_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg911knfo35158t17761km32a_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'情敌复仇战.ts_ 吐槽中国环境.mkv',NULL,NULL,NULL,'2018-05-24 14:48:28',NULL,'2018-05-24 14:48:43','0','1',NULL),('95d9594c56ac4a15afc46b9f03de0e02','5b06919ce7134b000767c887','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qgkmnddr1l1i1cv7k1e1g9q2r.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qgkmnddr1l1i1cv7k1e1g9q2r_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qgkmnddr1l1i1cv7k1e1g9q2r_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'丑闻第三季08.ts_同性性爱照片.mkv',NULL,NULL,NULL,'2018-05-24 18:19:09',NULL,'2018-05-24 18:19:21','0','1',NULL),('a70a9841d4aa4e6cb4d67333be13438b','5b066175f9e6d800069a9438','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8eoi26nv61uc62qh1qij1hdji1.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8eoi26nv61uc62qh1qij1hdji1_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8eoi26nv61uc62qh1qij1hdji1_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'情敌复仇战.ts_ 吐槽中国环境.mkv',NULL,NULL,NULL,'2018-05-24 14:53:42',NULL,'2018-05-24 14:53:59','0','1',NULL),('a8271bdb9d9f41edb810a740abfe8f94','1526557204975471809_x1ZF8PnYfzOzA0iSyPjdeA','http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8','http://pili-live-hls.argo.qnsdk.com/argo/1526557204975471809_x1ZF8PnYfzOzA0iSyPjdeA.m3u8',NULL,'0','99','99','99',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'直播3',NULL,NULL,NULL,'2018-05-17 20:24:26',NULL,'2018-05-17 20:24:26','1','2','1'),('ab4dc9ddbc184877a8d1f7d1250c1466','5b066152984fe600070b0b92','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8enfoo147h19jarqu1q9p640gl.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8enfoo147h19jarqu1q9p640gl_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8enfoo147h19jarqu1q9p640gl_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'金矿.ts_敏感词汇-爆菊花.mkv',NULL,NULL,NULL,'2018-05-24 14:53:07',NULL,'2018-05-24 14:53:15','0','1',NULL),('ae19d211c1dc42afbfde6bb4092e1e06','5afc5a4e83f62f000706b96f','http://p8d4x5qlh.bkt.clouddn.com/o_1cdks191cj52vgm1ste1ej01blks.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1cdks191cj52vgm1ste1ej01blks.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1cdks191cj52vgm1ste1ej01blks_C.jpg','1','3','99','3',0.84,NULL,'1',0.90,NULL,'1',0.84,NULL,'0',1.00,'??.mp4','148.240000','Q0001',NULL,'2018-05-17 20:24:30',NULL,'2018-05-17 20:24:30','1','1',NULL),('b1753cd920494cdda603f60a44366aa5','1526554104834139439_P9VHUp4VhhBGFR4LtBzxgg','http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8','http://pili-live-hls.argo.qnsdk.com/argo/1526554104834139439_P9VHUp4VhhBGFR4LtBzxgg.m3u8',NULL,'0','99','99','99',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'这是一个直播测试',NULL,NULL,NULL,'2018-05-17 20:24:27',NULL,'2018-05-17 20:24:27','1','2','1'),('b19c6843c8254187a066784ad904778a','5b06628dafcf3c0007414631','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8f0u4s1sgltrk10rf1jjfuf6mh.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8f0u4s1sgltrk10rf1jjfuf6mh_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8f0u4s1sgltrk10rf1jjfuf6mh_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'北回归线6_存在反对毛泽东主义倾向.ts_20180524_145523.mp4','48.840000',NULL,NULL,'2018-05-24 14:58:22',NULL,'2018-05-24 14:58:43','0','1',NULL),('b23813ef743b4da49226efb63a15d9f2','5b069185f9e6d800069a95a4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qfnuju9fiv31qdo1ch21rnl9.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qfnuju9fiv31qdo1ch21rnl9.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qfnuju9fiv31qdo1ch21rnl9_C.jpg','1','3','99','3',1.00,NULL,'0',1.00,NULL,'0',1.00,NULL,'0',1.00,'阿尔法屋第二季-10-男女有裸露镜头.mkv',NULL,NULL,NULL,'2018-05-24 18:33:32',NULL,'2018-05-24 18:33:32','1','1',NULL),('b32163457bd54b75a043e0dd15faa863','5b0691a1afcf3c000741479b','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qgqls1tjc1773ap9g9delf3d.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qgqls1tjc1773ap9g9delf3d_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qgqls1tjc1773ap9g9delf3d_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'癫狂之旅第1季02.ts_男性露点.mkv',NULL,NULL,NULL,'2018-05-24 18:19:14',NULL,'2018-05-24 18:19:27','0','1',NULL),('c292e0e92cd74c06bd32c5f9ddbefc8c','5b0692503b1e310008ef568a','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qm5ft1gld1npd3lk1b0qtchbl.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qm5ft1gld1npd3lk1b0qtchbl_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qm5ft1gld1npd3lk1b0qtchbl_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'秋日之光改.ts_性爱动作.mkv',NULL,NULL,NULL,'2018-05-24 18:22:09',NULL,'2018-05-24 18:22:29','0','1',NULL),('c6357fdb432f4de18bb0977c33e39072','5b0661643b1e310008ef5515','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8eo0pu1enqlqbuki18lmpich9.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8eo0pu1enqlqbuki18lmpich9.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8eo0pu1enqlqbuki18lmpich9_C.jpg','2','1','1','1',0.99,'1','1',0.99,NULL,'0',1.00,NULL,'0',1.00,'空降.ts_色情裸露镜头.mkv',NULL,'Q0001',NULL,'2018-05-24 14:53:25','Q0001','2018-05-24 18:40:06','0','1',NULL),('d624b2ba5eca40d089becc77ba90d1e1','5b02260e06f81500079305e7','http://p8d4x5qlh.bkt.clouddn.com/o_1ce067foekjs4cpgo3ljf1osi9.mpeg','http://p8d4x5qlh.bkt.clouddn.com/o_1ce067foekjs4cpgo3ljf1osi9_R.mp4',NULL,'1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'秋日之光改.mpeg',NULL,NULL,NULL,'2018-05-21 10:22:19',NULL,'2018-05-21 10:22:19','1','1',NULL),('d85a3749a3a349c4b786885ff2bedc5f','5b0691a83b1e310008ef5687','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qgvra17g31qf1kuokj614mc3v.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qgvra17g31qf1kuokj614mc3v.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qgvra17g31qf1kuokj614mc3v_C.jpg','1','3','99','3',0.52,NULL,'0',0.52,NULL,'0',1.00,NULL,'0',1.00,'癫狂之旅第1季02.ts_男性露上半身.mkv',NULL,'Q0001',NULL,'2018-05-24 18:19:21',NULL,'2018-05-24 18:19:40','0','1',NULL),('dad641608a974379bd09216e0ade3a6e','5b065fe9f9e6d800069a942b','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ec4ha1921sipfl5to9d68p.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ec4ha1921sipfl5to9d68p_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ec4ha1921sipfl5to9d68p_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'花神咖啡馆.ts_亲吻镜头.mkv',NULL,NULL,NULL,'2018-05-24 14:47:06',NULL,'2018-05-24 14:47:35','0','1',NULL),('df4182b6c30445c2af3cb7f9c308d2de','1526545806370840379_qae4ArvQZVHPQ95r22pvOQ','http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8','http://pili-live-hls.argo.qnsdk.com/argo/1526545806370840379_qae4ArvQZVHPQ95r22pvOQ.m3u8',NULL,'0','99','99','99',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'香港卫视',NULL,NULL,NULL,'2018-05-17 20:24:27',NULL,'2018-05-17 20:24:27','1','2','1'),('dfe66570739244bf83625ef27c9fce55','5b0660300e7dae00076a4cdf','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg918jdroljvilol1o2b26.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg918jdroljvilol1o2b26_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg918jdroljvilol1o2b26_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'金矿.ts_敏感词汇-爆菊花.mkv',NULL,NULL,NULL,'2018-05-24 14:48:17',NULL,'2018-05-24 14:48:25','0','1',NULL),('e4195dff931d4c2789c87dcd5665c166','5b0691c1f9e6d800069a95a5','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qhhgp160q1u2lgh41mcv106k4v.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qhhgp160q1u2lgh41mcv106k4v.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qhhgp160q1u2lgh41mcv106k4v_C.jpg','1','1','99','1',0.36,NULL,'1',0.36,NULL,'0',1.00,NULL,'0',1.00,'基隆.ts_有色情动作.mp4','55.920000','Q0001',NULL,'2018-05-24 18:19:46',NULL,'2018-05-24 18:20:34','0','1',NULL),('e469af0509694ff889680f138f1ab459','5afc593e06f815000792e6c5','http://p8d4x5qlh.bkt.clouddn.com/o_1cdkrp1rsosu1rus11f25qt12729.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1cdkrp1rsosu1rus11f25qt12729.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1cdkrp1rsosu1rus11f25qt12729_C.jpg','2','1','1','3',0.84,'1','1',0.90,NULL,'1',0.84,NULL,'0',1.00,'??.mp4','148.240000','Q0001',NULL,'2018-05-17 20:24:31','Q0001','2018-05-17 20:24:31','1','1',NULL),('e7f669b707104d1a8b7761f4098df3d9','5b066037984fe600070b0b81','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg911f91icd1q7v1n5i14vt28.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg911f91icd1q7v1n5i14vt28.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg911f91icd1q7v1n5i14vt28_C.jpg','2','1','1','1',0.99,'1','1',0.99,NULL,'0',1.00,NULL,'0',1.00,'空降.ts_色情裸露镜头.mkv',NULL,'Q0001',NULL,'2018-05-24 14:48:24','Q0001','2018-05-24 18:39:45','0','1',NULL),('e8676c86217d4ddeb2d54c6eab6ed376','5b0660e8984fe600070b0b8a','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ek2tkpij1tf51qg5cu799cc1.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ek2tkpij1tf51qg5cu799cc1_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8ek2tkpij1tf51qg5cu799cc1_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'花神咖啡馆.ts_亲吻镜头.mkv',NULL,NULL,NULL,'2018-05-24 14:51:21',NULL,'2018-05-24 14:51:53','0','1',NULL),('e918a45edde9437c9eec6ebcc13e9844','5b06629dafcf3c0007414633','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8f1joo1ro7dko6qh17k11tolo5.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8f1joo1ro7dko6qh17k11tolo5.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8f1joo1ro7dko6qh17k11tolo5_C.jpg','1','1','99','1',0.94,NULL,'1',0.94,NULL,'0',1.00,NULL,'0',1.00,'纵横四海.ts_名画裸露画面.mp4','6.640000','Q0001',NULL,'2018-05-24 14:58:38',NULL,'2018-05-24 14:58:47','0','1',NULL),('f45ef152f530499c9bf84441c64e0e56','5b0227cba0304600078c8b61','http://p8d4x5qlh.bkt.clouddn.com/o_1ce06mqubfbdn9293c1uaf11a09.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce06mqubfbdn9293c1uaf11a09.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce06mqubfbdn9293c1uaf11a09_C.jpg','2','3','3','3',0.84,'1','1',0.90,'1','1',0.84,NULL,'0',1.00,'情圣.mp4','148.240000','Q0001',NULL,'2018-05-21 09:58:35','Q0001','2018-05-24 18:37:04','0','1',NULL),('f48e207c7bd34f509caf5edec49f4404','5b06919a0e7dae00076a4e5b','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qgeta1i1t1ac8clku8so6629.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qgeta1i1t1ac8clku8so6629_R.mp4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8qgeta1i1t1ac8clku8so6629_C.jpg','1','0','99','0',1.00,NULL,'2',1.00,NULL,'0',1.00,NULL,'0',1.00,'丑闻第三季08.ts_同性亲吻镜头.mkv',NULL,NULL,NULL,'2018-05-24 18:19:07',NULL,'2018-05-24 18:19:19','0','1',NULL),('f5da22fb2ddd4db08074a84c97b73345','1526557981009225521_V5n8LcZcQTtb4mq4F2as4Q','http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8','http://pili-live-hls.argo.qnsdk.com/argo/1526557981009225521_V5n8LcZcQTtb4mq4F2as4Q.m3u8',NULL,'0','99',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'f5da22fb2ddd4db08074a84c97b73345',NULL,NULL,NULL,'2018-05-17 20:24:26',NULL,'2018-05-17 20:24:26','1','2','1'),('f9e57f4fed9a4267ad8ebcc0a93790a1','5b0660644f73e00006da61b4','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg913qf1p0situ16r3dhd2d.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg913qf1p0situ16r3dhd2d.mkv','http://p8d4x5qlh.bkt.clouddn.com/o_1ce8edg913qf1p0situ16r3dhd2d_C.jpg','1','3','99','3',0.98,NULL,'2',1.00,NULL,'1',0.98,NULL,'0',1.00,'血域燃烧.ts_血腥镜头.mkv',NULL,'Q0001',NULL,'2018-05-24 14:49:08',NULL,'2018-05-24 14:49:24','0','1',NULL),('fd475b3163b74082b9dbf263f40b7152','1526558880007812676_Paq5TJGPnct3xwqNU6052w','http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8','http://pili-live-hls.argo.qnsdk.com/argo/1526558880007812676_Paq5TJGPnct3xwqNU6052w.m3u8',NULL,'0','99',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'fd475b3163b74082b9dbf263f40b7152',NULL,NULL,NULL,'2018-05-17 20:24:26',NULL,'2018-05-17 20:24:26','1','2','1');
/*!40000 ALTER TABLE `review_video_manager` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-11 15:35:23