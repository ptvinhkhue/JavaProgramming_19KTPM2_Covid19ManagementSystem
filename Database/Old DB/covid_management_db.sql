CREATE DATABASE  IF NOT EXISTS `covid_management` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `covid_management`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: covid_management
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `acc_admin`
--

DROP TABLE IF EXISTS `acc_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acc_admin` (
  `adminID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `bankID` int DEFAULT NULL,
  PRIMARY KEY (`adminID`),
  KEY `fk_acc_bank_bankID_idx` (`bankID`),
  KEY `fk_acc_covid_username_idx` (`username`),
  CONSTRAINT `fk_acc_bank_bankID` FOREIGN KEY (`bankID`) REFERENCES `acc_bank` (`personalID`),
  CONSTRAINT `fk_acc_covid_username` FOREIGN KEY (`username`) REFERENCES `acc_covid` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acc_admin`
--

LOCK TABLES `acc_admin` WRITE;
/*!40000 ALTER TABLE `acc_admin` DISABLE KEYS */;
INSERT INTO `acc_admin` VALUES (1,'admin',NULL);
/*!40000 ALTER TABLE `acc_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acc_bank`
--

DROP TABLE IF EXISTS `acc_bank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acc_bank` (
  `personalID` int NOT NULL,
  `balance` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  PRIMARY KEY (`personalID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acc_bank`
--

LOCK TABLES `acc_bank` WRITE;
/*!40000 ALTER TABLE `acc_bank` DISABLE KEYS */;
INSERT INTO `acc_bank` VALUES (261547286,1000000,2),(261547311,1500000,2),(261548596,500000,2),(261548924,2000000,2);
/*!40000 ALTER TABLE `acc_bank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acc_covid`
--

DROP TABLE IF EXISTS `acc_covid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acc_covid` (
  `username` varchar(30) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `type` int DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acc_covid`
--

LOCK TABLES `acc_covid` WRITE;
/*!40000 ALTER TABLE `acc_covid` DISABLE KEYS */;
INSERT INTO `acc_covid` VALUES ('261547286',NULL,3),('261547311',NULL,3),('261548596',NULL,3),('261548924','$2a$12$cEbsvGJVuPVcwD.rGVBkGunkCtX7vNh4jfjSj6YjTbNc.SdfRDhbK',3),('admin','$2a$12$l4e8dlPMQ7NwFHtd1kbypeCqNRq58eMPA3C8N3/2dd8i7ipRXHyV6',1),('ptvkhue','$2a$12$rOw4WrsaHSQzNe50wBeiuea08/P9verFeYnoRs1KhfqfpdCsSA96G',2),('tdhtrung','$2a$12$hXDYVOVKeZTQwgfSyrWhh.arB9.dreB/o/eHPyALAb9QUpmnfpxYW',2),('test','123',2),('tttung','$2a$12$NvmBWTCzyfDUPUBpOI9SW.qek6K19uLo0unez57nbZizVAKkoe0Oi',2),('vhvy','$2a$12$mHUDyqVC6wGwhcDz2hK65.lxEmkBc7kw5UG7JJUjjPxd/bgRSlL96',2);
/*!40000 ALTER TABLE `acc_covid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acc_main`
--

DROP TABLE IF EXISTS `acc_main`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acc_main` (
  `mainID` int NOT NULL,
  `bankID` int DEFAULT NULL,
  PRIMARY KEY (`mainID`),
  KEY `fk_acc_bank_bankID_idx` (`bankID`),
  KEY `fk_acc_bank_bankID_acc_main_idx` (`bankID`),
  CONSTRAINT `fk_acc_bank_bankID_acc_main` FOREIGN KEY (`bankID`) REFERENCES `acc_bank` (`personalID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acc_main`
--

LOCK TABLES `acc_main` WRITE;
/*!40000 ALTER TABLE `acc_main` DISABLE KEYS */;
/*!40000 ALTER TABLE `acc_main` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acc_manager`
--

DROP TABLE IF EXISTS `acc_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acc_manager` (
  `managerID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `locked` int DEFAULT '0',
  PRIMARY KEY (`managerID`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  CONSTRAINT `fk_acc_covid_username_acc_manager` FOREIGN KEY (`username`) REFERENCES `acc_covid` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acc_manager`
--

LOCK TABLES `acc_manager` WRITE;
/*!40000 ALTER TABLE `acc_manager` DISABLE KEYS */;
INSERT INTO `acc_manager` VALUES (1,'ptvkhue',0),(2,'tdhtrung',0),(3,'tttung',0),(4,'test',1),(5,'vhvy',0);
/*!40000 ALTER TABLE `acc_manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acc_normal`
--

DROP TABLE IF EXISTS `acc_normal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acc_normal` (
  `normalID` int NOT NULL,
  `bankID` int DEFAULT NULL,
  PRIMARY KEY (`normalID`),
  KEY `fk_acc_bank_bankID_acc_normal_idx` (`bankID`),
  CONSTRAINT `fk_acc_bank_bankID_acc_normal` FOREIGN KEY (`bankID`) REFERENCES `acc_bank` (`personalID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acc_normal`
--

LOCK TABLES `acc_normal` WRITE;
/*!40000 ALTER TABLE `acc_normal` DISABLE KEYS */;
INSERT INTO `acc_normal` VALUES (3,261547286),(4,261547311),(2,261548596),(1,261548924);
/*!40000 ALTER TABLE `acc_normal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acc_user`
--

DROP TABLE IF EXISTS `acc_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acc_user` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `fullname` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `personalID` varchar(10) DEFAULT NULL,
  `yob` int DEFAULT NULL,
  `addressID` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `placeID` int DEFAULT NULL,
  `debt` int DEFAULT NULL,
  `loggedIn` tinyint DEFAULT '0',
  PRIMARY KEY (`userID`),
  KEY `idx_fk_placeID` (`placeID`) /*!80000 INVISIBLE */,
  KEY `idx_fk_addressID` (`addressID`) /*!80000 INVISIBLE */,
  KEY `fk_acc_covid_username_acc_user_idx` (`username`) /*!80000 INVISIBLE */,
  CONSTRAINT `fk_acc_covid_username_acc_user` FOREIGN KEY (`username`) REFERENCES `acc_covid` (`username`),
  CONSTRAINT `fk_address_addressID_acc_user` FOREIGN KEY (`addressID`) REFERENCES `address` (`addressID`),
  CONSTRAINT `fk_place_placeID_acc_user` FOREIGN KEY (`placeID`) REFERENCES `place` (`placeID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acc_user`
--

LOCK TABLES `acc_user` WRITE;
/*!40000 ALTER TABLE `acc_user` DISABLE KEYS */;
INSERT INTO `acc_user` VALUES (1,'261548924','Phạm Trọng Vinh Khuê','261548924',2001,1,1,1,0,0),(2,'261548596','Phạm Hoàng Anh','261548596',1996,3,2,3,0,0),(3,'261547286','Trần Đại Hoàng Trung','261547286',2001,2,2,2,0,0),(4,'261547311','Trần Thanh Tùng','261547311',2001,8,2,4,0,0);
/*!40000 ALTER TABLE `acc_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `addressID` int NOT NULL,
  `province` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `district` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `ward` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`addressID`),
  UNIQUE KEY `addressID_UNIQUE` (`addressID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'Bình Thuận','Phan Thiết','Xuân An'),(2,'Bình Thuận','Phan Thiết','Phú Trinh'),(3,'Bình Thuận','Tuy Phong','Bình Thạnh'),(4,'Bình Thuận','Tuy Phong','Phan Rí Cửa'),(5,'Hồ Chí Minh','Quận 8','Phường 4'),(6,'Hồ Chí Minh','Quận 7','Phường 5'),(7,'Hồ Chí Minh','Bình Chánh','Bình Hưng'),(8,'Hồ Chí Minh ','Quận 1','Phường 2');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_manager`
--

DROP TABLE IF EXISTS `history_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `history_manager` (
  `historyID` int NOT NULL AUTO_INCREMENT,
  `managerID` int DEFAULT NULL,
  `activity` varchar(100) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`historyID`),
  KEY `fk_acc_manager_managerID_history_manager_idx` (`managerID`),
  CONSTRAINT `fk_acc_manager_managerID_history_manager` FOREIGN KEY (`managerID`) REFERENCES `acc_manager` (`managerID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_manager`
--

LOCK TABLES `history_manager` WRITE;
/*!40000 ALTER TABLE `history_manager` DISABLE KEYS */;
INSERT INTO `history_manager` VALUES (19,1,'Update UserID = 1 status: F2 -> F1','2022-01-03 00:07:22'),(20,1,'Update UserID = 3 status: F3 -> F2','2022-01-03 00:07:23'),(21,1,'Update UserID = 4 status: F3 -> F2','2022-01-03 00:07:23'),(22,1,'Update UserID = 2 placeID: 1 -> 3','2022-01-03 00:07:56');
/*!40000 ALTER TABLE `history_manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_place`
--

DROP TABLE IF EXISTS `history_place`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `history_place` (
  `history_placeID` int NOT NULL AUTO_INCREMENT,
  `userID` int DEFAULT NULL,
  `placeOldID` int DEFAULT NULL,
  `placeNewID` int DEFAULT NULL,
  `datetime` date DEFAULT NULL,
  PRIMARY KEY (`history_placeID`),
  KEY `fk_place_placeOldID_history_place_idx` (`placeOldID`),
  KEY `fk_place_placeNewID_history_place_idx` (`placeNewID`),
  CONSTRAINT `fk_place_placeNewID_history_place` FOREIGN KEY (`placeNewID`) REFERENCES `place` (`placeID`),
  CONSTRAINT `fk_place_placeOldID_history_place` FOREIGN KEY (`placeOldID`) REFERENCES `place` (`placeID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_place`
--

LOCK TABLES `history_place` WRITE;
/*!40000 ALTER TABLE `history_place` DISABLE KEYS */;
INSERT INTO `history_place` VALUES (3,2,1,3,'2022-01-03');
/*!40000 ALTER TABLE `history_place` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_status`
--

DROP TABLE IF EXISTS `history_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `history_status` (
  `history_statusID` int NOT NULL AUTO_INCREMENT,
  `userID` int DEFAULT NULL,
  `statusOld` int DEFAULT NULL,
  `statusNew` int DEFAULT NULL,
  `datetime` date DEFAULT NULL,
  PRIMARY KEY (`history_statusID`),
  KEY `fk_acc_user_userID_history_covid_idx` (`userID`),
  CONSTRAINT `fk_acc_user_userID_history_status` FOREIGN KEY (`userID`) REFERENCES `acc_user` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_status`
--

LOCK TABLES `history_status` WRITE;
/*!40000 ALTER TABLE `history_status` DISABLE KEYS */;
INSERT INTO `history_status` VALUES (5,1,2,1,'2022-01-02'),(6,3,3,2,'2022-01-02'),(7,4,3,2,'2022-01-02'),(14,1,2,1,'2022-01-03'),(15,3,3,2,'2022-01-03'),(16,4,3,2,'2022-01-03');
/*!40000 ALTER TABLE `history_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `necessity`
--

DROP TABLE IF EXISTS `necessity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `necessity` (
  `necessityID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `limitAmount` int DEFAULT NULL,
  `limitTime` datetime DEFAULT NULL,
  `price` int DEFAULT NULL,
  PRIMARY KEY (`necessityID`),
  UNIQUE KEY `id_UNIQUE` (`necessityID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `necessity`
--

LOCK TABLES `necessity` WRITE;
/*!40000 ALTER TABLE `necessity` DISABLE KEYS */;
INSERT INTO `necessity` VALUES (1,'Gạo (1kg)',50,NULL,10000),(2,'Muối (1kg)',10,'2022-06-24 00:00:00',10000),(3,'Dầu ăn (1l)',20,NULL,50000),(4,'Nước rửa tay (1 chai)',50,NULL,30000),(9,'Thuốc men',NULL,NULL,75000);
/*!40000 ALTER TABLE `necessity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `orderID` int NOT NULL AUTO_INCREMENT,
  `userID` int DEFAULT NULL,
  `sum` int DEFAULT NULL,
  PRIMARY KEY (`orderID`),
  KEY `fk_acc_user_userID_order_idx` (`userID`),
  CONSTRAINT `fk_acc_user_userID_order` FOREIGN KEY (`userID`) REFERENCES `acc_user` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,1,90000),(2,2,50000);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `orderID` int NOT NULL,
  `necessityID` int NOT NULL,
  `amount` int DEFAULT NULL,
  PRIMARY KEY (`orderID`,`necessityID`),
  KEY `fk_necessity_necessityID_order_detail_idx` (`necessityID`),
  CONSTRAINT `fk_necessity_necessityID_order_detail` FOREIGN KEY (`necessityID`) REFERENCES `necessity` (`necessityID`),
  CONSTRAINT `fk_order_orderID_order_detail` FOREIGN KEY (`orderID`) REFERENCES `order` (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (1,1,1),(1,3,1),(1,4,1),(2,1,1),(2,2,1),(2,4,1);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `paymentID` int NOT NULL,
  `normalID` int DEFAULT NULL,
  `cash` int DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`paymentID`),
  KEY `fk_acc_normal_normalID_payment_idx` (`normalID`),
  CONSTRAINT `fk_acc_normal_normalID_payment` FOREIGN KEY (`normalID`) REFERENCES `acc_normal` (`normalID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,1,90000,'2021-11-26 00:00:00'),(2,2,50000,'2021-11-25 00:00:00');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `place`
--

DROP TABLE IF EXISTS `place`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `place` (
  `placeID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `capacity` int DEFAULT NULL,
  `current` int DEFAULT '0',
  PRIMARY KEY (`placeID`),
  UNIQUE KEY `id_UNIQUE` (`placeID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `place`
--

LOCK TABLES `place` WRITE;
/*!40000 ALTER TABLE `place` DISABLE KEYS */;
INSERT INTO `place` VALUES (1,'Bệnh viện đa khoa tỉnh Bình Thuận',1000,499),(2,'Trường THPT chuyên Trần Hưng Đạo',100,30),(3,'Bệnh viện 7A',1500,701),(4,'Nhà thi đấu Phú Thọ',300,75),(5,'Kí túc xá ĐHQG TP. Hồ Chí Minh',5000,3000);
/*!40000 ALTER TABLE `place` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relation`
--

DROP TABLE IF EXISTS `relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relation` (
  `userID` int NOT NULL,
  `relatedID` int NOT NULL,
  PRIMARY KEY (`userID`,`relatedID`),
  KEY `fk_acc_user_relatedID_relation_idx` (`relatedID`),
  CONSTRAINT `fk_acc_user_relatedID_relation` FOREIGN KEY (`relatedID`) REFERENCES `acc_user` (`userID`),
  CONSTRAINT `fk_acc_user_userID_relation` FOREIGN KEY (`userID`) REFERENCES `acc_user` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relation`
--

LOCK TABLES `relation` WRITE;
/*!40000 ALTER TABLE `relation` DISABLE KEYS */;
INSERT INTO `relation` VALUES (3,1),(4,1),(1,3),(1,4);
/*!40000 ALTER TABLE `relation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-04  9:24:13
