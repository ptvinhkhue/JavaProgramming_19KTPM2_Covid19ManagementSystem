-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: coviddb
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acc_admin`
--

LOCK TABLES `acc_admin` WRITE;
/*!40000 ALTER TABLE `acc_admin` DISABLE KEYS */;
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
  `password` varchar(30) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `loggedIn` tinyint DEFAULT '0',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acc_covid`
--

LOCK TABLES `acc_covid` WRITE;
/*!40000 ALTER TABLE `acc_covid` DISABLE KEYS */;
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
  PRIMARY KEY (`managerID`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  CONSTRAINT `fk_acc_covid_username_acc_manager` FOREIGN KEY (`username`) REFERENCES `acc_covid` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acc_manager`
--

LOCK TABLES `acc_manager` WRITE;
/*!40000 ALTER TABLE `acc_manager` DISABLE KEYS */;
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
  `personalID` int DEFAULT NULL,
  `yob` int DEFAULT NULL,
  `addressID` int DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `placeID` int DEFAULT NULL,
  `debt` int DEFAULT NULL,
  PRIMARY KEY (`userID`),
  KEY `idx_fk_placeID` (`placeID`) /*!80000 INVISIBLE */,
  KEY `idx_fk_addressID` (`addressID`) /*!80000 INVISIBLE */,
  KEY `fk_acc_covid_username_acc_user_idx` (`username`) /*!80000 INVISIBLE */,
  CONSTRAINT `fk_acc_covid_username_acc_user` FOREIGN KEY (`username`) REFERENCES `acc_covid` (`username`),
  CONSTRAINT `fk_address_addressID_acc_user` FOREIGN KEY (`addressID`) REFERENCES `address` (`addressID`),
  CONSTRAINT `fk_place_placeID_acc_user` FOREIGN KEY (`placeID`) REFERENCES `place` (`placeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acc_user`
--

LOCK TABLES `acc_user` WRITE;
/*!40000 ALTER TABLE `acc_user` DISABLE KEYS */;
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
  PRIMARY KEY (`addressID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_covid`
--

DROP TABLE IF EXISTS `history_covid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `history_covid` (
  `historyID` int NOT NULL,
  `userID` int DEFAULT NULL,
  `statusOld` varchar(10) DEFAULT NULL,
  `statusNew` varchar(10) DEFAULT NULL,
  `placeOld` int DEFAULT NULL,
  `placeNew` int DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`historyID`),
  KEY `fk_place_placeOld_history_covid_idx` (`placeOld`),
  KEY `fk_place_placeNew_history_covid_idx` (`placeNew`),
  KEY `fk_acc_user_userID_history_covid_idx` (`userID`),
  CONSTRAINT `fk_acc_user_userID_history_covid` FOREIGN KEY (`userID`) REFERENCES `acc_user` (`userID`),
  CONSTRAINT `fk_place_placeNew_history_covid` FOREIGN KEY (`placeNew`) REFERENCES `place` (`placeID`),
  CONSTRAINT `fk_place_placeOld_history_covid` FOREIGN KEY (`placeOld`) REFERENCES `place` (`placeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_covid`
--

LOCK TABLES `history_covid` WRITE;
/*!40000 ALTER TABLE `history_covid` DISABLE KEYS */;
/*!40000 ALTER TABLE `history_covid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_manager`
--

DROP TABLE IF EXISTS `history_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `history_manager` (
  `historyID` int NOT NULL,
  `managerID` int DEFAULT NULL,
  `activity` varchar(30) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`historyID`),
  KEY `fk_acc_manager_managerID_history_manager_idx` (`managerID`),
  CONSTRAINT `fk_acc_manager_managerID_history_manager` FOREIGN KEY (`managerID`) REFERENCES `acc_manager` (`managerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_manager`
--

LOCK TABLES `history_manager` WRITE;
/*!40000 ALTER TABLE `history_manager` DISABLE KEYS */;
/*!40000 ALTER TABLE `history_manager` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `necessity`
--

LOCK TABLES `necessity` WRITE;
/*!40000 ALTER TABLE `necessity` DISABLE KEYS */;
/*!40000 ALTER TABLE `necessity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `orderID` int NOT NULL,
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
  `necessityID` int DEFAULT NULL,
  `amount` int DEFAULT NULL,
  PRIMARY KEY (`orderID`),
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `place`
--

LOCK TABLES `place` WRITE;
/*!40000 ALTER TABLE `place` DISABLE KEYS */;
/*!40000 ALTER TABLE `place` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-25 19:22:27
