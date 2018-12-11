CREATE DATABASE  IF NOT EXISTS `individualdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `individualdb`;
-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: individualdb
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `inbox`
--

DROP TABLE IF EXISTS `inbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `inbox` (
  `idmsg` int(11) NOT NULL AUTO_INCREMENT,
  `receiver` int(11) NOT NULL,
  `sender` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `message` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'unread',
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idmsg`),
  KEY `iduser_user_receiverr_inbox_idx` (`receiver`),
  CONSTRAINT `iduser_user_receiverr_inbox` FOREIGN KEY (`receiver`) REFERENCES `users` (`iduser`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inbox`
--

LOCK TABLES `inbox` WRITE;
/*!40000 ALTER TABLE `inbox` DISABLE KEYS */;
INSERT INTO `inbox` VALUES (1,7,'nanko','Try La Strega at Palaiologou street.It\'s value for money!','read','2018-11-18 22:52:54'),(2,7,'nanko','The famous Castropoliteia Bar would be a great choice.','unread','2018-11-18 22:55:33'),(3,6,'george','Hi nanko, Try Stamou at Amfitheas Avenue.It\'s really worthing it!! ','read','2018-11-18 23:03:32'),(4,7,'nanko','Thanks for the suggestion! I\'ll try it soon!','unread','2018-11-18 23:06:38'),(5,6,'george','There is a nice one named Athena. I\'ve been there many times. I suggest you avoid weekends! If you like yogurt you will enjoy the dessert!','unread','2018-11-18 23:13:52'),(6,7,'nanko','Hi George, Athena was really nice! Being there was a great experience! I\' ll always take your advices seriously!','unread','2018-11-18 23:17:18'),(7,3,'george','Hello panos how are you? Do you know any place in Athens to eat mousakas?','read','2018-11-18 23:26:20'),(8,6,'george','Thx you nanko for your suggestion, i will visit it the sooner i can.','unread','2018-11-18 23:27:27'),(9,3,'george','Panos , i m coming to Greece the next summer .I m looking forward to going for dinner to a traditional restaraunt.','read','2018-11-18 23:29:43'),(10,3,'george','Panos , unfortunetly i will not come to summer .My plans change for the winter this year.','read','2018-11-18 23:31:27'),(11,7,'panos','George , i will waiting to come to Athens.','unread','2018-11-18 23:33:24');
/*!40000 ALTER TABLE `inbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `questions` (
  `idqts` int(11) NOT NULL AUTO_INCREMENT,
  `senderQ` varchar(30) NOT NULL,
  `question` varchar(250) NOT NULL,
  `datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idqts`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'panos','I\'m at Rodos , i \'m looking for a nice place to have a drink.','2018-11-13 19:39:17'),(2,'nanko','Do you know any quite place to have lunch nearby Acropolis museum?','2018-11-14 23:08:57'),(3,'nanko','Where could i find tasty ice cream in Palaio Faliro?','2018-11-15 23:00:53'),(4,'george','Just arrived at Athens, looking for traditional greek cuisine.','2018-11-18 18:25:29'),(5,'george','Just arrived at Monemvasia, any suggestions where to have a coctail.','2018-11-18 18:26:09');
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varbinary(250) NOT NULL,
  `role` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'NoRole',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'offline',
  `credits` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin',_binary 'œ\Ìk\æ¿	\Ò\rY^õý\á\é','Admin','offline',0),(3,'panos',_binary 'eLNË¤[\rNû÷^\ê\Þø\á','DeleteRole','offline',30),(4,'normal',_binary 'eLNË¤[\rNû÷^\ê\Þø\á','NoRole','offline',9),(6,'nanko',_binary 'eLNË¤[\rNû÷^\ê\Þø\á','DeleteRole','offline',24),(7,'george',_binary 'eLNË¤[\rNû÷^\ê\Þø\á','EditRole','offline',18);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'individualdb'
--

--
-- Dumping routines for database 'individualdb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-19  0:05:02
