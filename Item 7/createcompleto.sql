start transaction;

create database `Acme-Chorbies`;

use `Acme-Chorbies`;

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';

create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete 
on `Acme-Chorbies`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
create temporary tables, lock tables, create view, create routine,
 alter routine, execute, trigger, show view 
on `Acme-Chorbies`.* to 'acme-manager'@'%';

CREATE DATABASE  IF NOT EXISTS `acme-chorbies` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `acme-chorbies`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: acme-chorbies
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_idt4b4u259p6vs4pyr9lax4eg` (`userAccount_id`),
  CONSTRAINT `FK_idt4b4u259p6vs4pyr9lax4eg` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (2398,0,'admin@acme.com','administrator1','+34666666666','administrator1',2383);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attachment`
--

DROP TABLE IF EXISTS `attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachment` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `chirp_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bnyhwxm7ara2q688wlq0928va` (`chirp_id`),
  CONSTRAINT `FK_bnyhwxm7ara2q688wlq0928va` FOREIGN KEY (`chirp_id`) REFERENCES `chirp` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment`
--

LOCK TABLES `attachment` WRITE;
/*!40000 ALTER TABLE `attachment` DISABLE KEYS */;
INSERT INTO `attachment` VALUES (2424,0,'AttachmentName1','http://www.attachmentName1.com',2405),(2425,0,'AttachmentName1','http://www.attachmentName2.com',2405),(2426,0,'AttachmentName1','http://www.attachmentName3.com',2405),(2427,0,'AttachmentName1','http://www.attachmentName4.com',2405),(2428,0,'AttachmentName1','http://www.attachmentName5.com',2405),(2429,0,'AttachmentName1','http://www.attachmentName6.com',2405),(2430,0,'AttachmentName1','http://www.attachmentName1.com',2406),(2431,0,'AttachmentName1','http://www.attachmentName2.com',2406),(2432,0,'AttachmentName1','http://www.attachmentName3.com',2406),(2433,0,'AttachmentName1','http://www.attachmentName4.com',2406),(2434,0,'AttachmentName1','http://www.attachmentName5.com',2406),(2435,0,'AttachmentName1','http://www.attachmentName6.com',2406),(2436,0,'AttachmentName1','http://www.attachmentName1.com',2408),(2437,0,'AttachmentName1','http://www.attachmentName1.com',2408);
/*!40000 ALTER TABLE `attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banner`
--

DROP TABLE IF EXISTS `banner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `banner` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banner`
--

LOCK TABLES `banner` WRITE;
/*!40000 ALTER TABLE `banner` DISABLE KEYS */;
INSERT INTO `banner` VALUES (2455,0,'http://i.imgur.com/HfkbICf.png','http://www.acme-pad-thai.com'),(2456,0,'http://i.imgur.com/pA52wSC.png','http://www.acme-bnb.com'),(2457,0,'http://i.imgur.com/GgsILAX.png','http://www.acme-cng.com'),(2458,0,'http://i.imgur.com/HfkbICf.png','http://www.acme-pad-thai.com'),(2459,0,'http://i.imgur.com/pA52wSC.png','http://www.acme-bnb.com'),(2460,0,'http://i.imgur.com/GgsILAX.png','http://www.acme-cng.com');
/*!40000 ALTER TABLE `banner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chirp`
--

DROP TABLE IF EXISTS `chirp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chirp` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `isSender` bit(1) NOT NULL,
  `recipientName` varchar(255) DEFAULT NULL,
  `senderName` varchar(255) DEFAULT NULL,
  `sendingMoment` datetime DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `recipient_id` int(11) DEFAULT NULL,
  `sender_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_a5xyhin5jhq9cbuafsy55t6mb` (`isSender`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chirp`
--

LOCK TABLES `chirp` WRITE;
/*!40000 ALTER TABLE `chirp` DISABLE KEYS */;
INSERT INTO `chirp` VALUES (2405,1,'','Chorbi2 Chorbi2','Chorbi1 Chorbi1','2016-04-04 11:45:00','subject Chirp','text Chirp',2472,2471),(2406,1,'\0','Chorbi2 Chorbi2','Chorbi1 Chorbi1','2016-04-04 11:45:00','subject Chirp','text Chirp',2472,2471),(2407,1,'','Chorbi2 Chorbi2','Chorbi1 Chorbi1','2016-04-04 11:45:00','subject Chirp','text Chirp',2472,2471),(2408,1,'\0','Chorbi2 Chorbi2','Chorbi1 Chorbi1','2016-04-04 11:45:00','subject Chirp','text Chirp',2472,2471),(2409,1,'','Chorbi2 Chorbi2','Chorbi1 Chorbi1','2016-04-04 11:45:00','subject Chirp','text Chirp',2472,2471),(2410,1,'','Chorbi2 Chorbi2','Chorbi1 Chorbi1','2016-04-04 11:45:00','subject Chirp','text Chirp',2472,2471),(2411,1,'','Chorbi2 Chorbi2','Chorbi1 Chorbi1','2016-04-04 11:45:00','subject Chirp','text Chirp',2472,2471),(2412,1,'','Chorbi2 Chorbi2','Chorbi1 Chorbi1','2016-04-04 11:45:00','subject Chirp','text Chirp',2472,2471),(2413,1,'\0','Chorbi1 Chorbi1','Chorbi2 Chorbi2','2016-04-04 11:45:00','subject Chirp','text Chirp',2471,2472),(2414,1,'\0','Chorbi1 Chorbi1','Chorbi2 Chorbi2','2016-04-04 11:45:00','subject Chirp','text Chirp',2471,2472),(2415,1,'\0','Chorbi1 Chorbi1','Chorbi2 Chorbi2','2016-04-04 11:45:00','subject Chirp','text Chirp',2471,2472),(2416,1,'\0','Chorbi1 Chorbi1','Chorbi2 Chorbi2','2016-04-04 11:45:00','subject Chirp','text Chirp',2471,2472),(2417,1,'\0','Chorbi1 Chorbi1','Chorbi2 Chorbi2','2016-04-04 11:45:00','subject Chirp','text Chirp',2471,2472),(2418,1,'\0','Chorbi1 Chorbi1','Chorbi2 Chorbi2','2016-04-04 11:45:00','subject Chirp','text Chirp',2471,2472),(2419,1,'','Chorbi3 Chorbi3','Chorbi2 Chorbi2','2016-04-04 11:45:00','subject Chirp','text Chirp',2473,2472),(2420,1,'','Chorbi4 Chorbi4','Chorbi3 Chorbi3','2016-04-04 11:45:00','subject Chirp','text Chirp',2474,2473),(2421,1,'\0','Chorbi3 Chorbi3','Chorbi4 Chorbi4','2016-04-04 11:45:00','subject Chirp','text Chirp',2473,2474),(2422,1,'\0','Chorbi3 Chorbi3','Chorbi- Chorbi-','2016-04-04 11:45:00','subject Chirp','text Chirp',2473,NULL),(2423,1,'\0','Chorbi- Chorbi-','Chorbi3 Chorbi3','2016-04-04 11:45:00','subject Chirp','text Chirp',NULL,2473);
/*!40000 ALTER TABLE `chirp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chorbi`
--

DROP TABLE IF EXISTS `chorbi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chorbi` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  `chargedFee` double NOT NULL,
  `birthDate` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `desiredRelationship` varchar(255) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `coordinates_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hjmrn8vvm3631n2jvyojlilsj` (`coordinates_id`),
  UNIQUE KEY `UK_qrvmwkp25xc5exr6m3jgaxu4x` (`userAccount_id`),
  KEY `UK_9hoj3pv0gojtme0f9n5etw6k3` (`desiredRelationship`),
  KEY `UK_hdlfsw7lxgcbclq2iom6me4h8` (`genre`),
  KEY `UK_5wdj0ha7n2mjpiojoe3gy0qh9` (`description`),
  KEY `UK_ovkyoevssjuf41lisig22x0b8` (`birthDate`),
  CONSTRAINT `FK_qrvmwkp25xc5exr6m3jgaxu4x` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`),
  CONSTRAINT `FK_hjmrn8vvm3631n2jvyojlilsj` FOREIGN KEY (`coordinates_id`) REFERENCES `coordinates` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chorbi`
--

LOCK TABLES `chorbi` WRITE;
/*!40000 ALTER TABLE `chorbi` DISABLE KEYS */;
INSERT INTO `chorbi` VALUES (2471,0,'chorbi1@acme.com','Chorbi1','+34000000000','Chorbi1',2384,0,'1990-06-20','description chorbi1','love','man','https://image.freepik.com/iconos-gratis/avatar-de-usuario-de-esquema_318-34741.jpg',2438),(2472,0,'chorbi2@acme.com','Chorbi2','+34111111111','Chorbi2',2385,0,'1970-06-20','description chorbi2','friendship','man','https://image.freepik.com/iconos-gratis/avatar-de-usuario-de-esquema_318-34741.jpg',2440),(2473,0,'chorbi3@acme.com','Chorbi3','+34222222222','Chorbi3',2386,0,'1988-06-20','description chorbi3','activities','man','https://image.freepik.com/iconos-gratis/avatar-de-usuario-de-esquema_318-34741.jpg',2442),(2474,0,'chorbi4@acme.com','Chorbi4','+34333333333','Chorbi4',2387,0,'1979-06-20','description chorbi4, call me at +34333333333','love','woman','https://image.freepik.com/iconos-gratis/avatar-de-usuario-de-esquema_318-34741.jpg',2444),(2475,0,'chorbi5@acme.com','Chorbi5','+34444444444','Chorbi5',2388,0,'1995-06-20','description chorbi5, add me chorbi5@acme.com','love','woman','https://image.freepik.com/iconos-gratis/avatar-de-usuario-de-esquema_318-34741.jpg',2446),(2476,0,'chorbi6@acme.com','Chorbi6','+34555555555','Chorbi6',2389,0,'1980-04-21','description chorbi6','friendship','woman','https://image.freepik.com/iconos-gratis/avatar-de-usuario-de-esquema_318-34741.jpg',2448),(2477,0,'chorbi6@acme.com','Chorbi7','+34555555555','Chorbi7',2390,0,'1980-04-21','description chorbi7','friendship','woman','https://image.freepik.com/iconos-gratis/avatar-de-usuario-de-esquema_318-34741.jpg',2450),(2478,0,'chorbi8@acme.com','Chorbi8','+34555555555','Chorbi8',2391,0,'1980-04-21','description chorbi8','friendship','woman','https://image.freepik.com/iconos-gratis/avatar-de-usuario-de-esquema_318-34741.jpg',2452);
/*!40000 ALTER TABLE `chorbi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cachedTime` bigint(20) NOT NULL,
  `chorbiFee` double NOT NULL,
  `managerFee` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
INSERT INTO `configuration` VALUES (2470,0,43200000,1,1);
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coordinates`
--

DROP TABLE IF EXISTS `coordinates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coordinates` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_pvt53kmofix4yr038656p7lsu` (`city`),
  KEY `UK_9pfiy0hcosbd9ksl8u9b7q06o` (`province`),
  KEY `UK_euu6v4as8xmbdohwle87755dd` (`country`),
  KEY `UK_d70etifmvemmrunavcayuh821` (`state`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coordinates`
--

LOCK TABLES `coordinates` WRITE;
/*!40000 ALTER TABLE `coordinates` DISABLE KEYS */;
INSERT INTO `coordinates` VALUES (2438,0,'Seville','Spain','Seville',''),(2439,0,'Seville','','',''),(2440,0,'Madrid','Spain','',''),(2441,0,'','','',''),(2442,0,'Austin','United States','','Texas'),(2443,0,'','','',''),(2444,0,'Paris','France','',''),(2445,0,'','','',''),(2446,0,'Seville','Spain','',''),(2447,0,'','','',''),(2448,0,'Seville','Spain','',''),(2449,0,'','','',''),(2450,0,'Seville','Spain','',''),(2451,0,'','','',''),(2452,0,'Seville','Spain','',''),(2453,0,'','','','');
/*!40000 ALTER TABLE `coordinates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creditcard`
--

DROP TABLE IF EXISTS `creditcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creditcard` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `cvvCode` int(11) NOT NULL,
  `expirationMonth` int(11) NOT NULL,
  `expirationYear` int(11) NOT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `customer_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ngdvidm52sk4p32c2tt4a9nq7` (`customer_id`),
  KEY `UK_12talwm2jns3cits0lnbbckl8` (`expirationYear`),
  KEY `UK_evhfhpjdhcmyygxjo8sa6opcy` (`expirationMonth`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditcard`
--

LOCK TABLES `creditcard` WRITE;
/*!40000 ALTER TABLE `creditcard` DISABLE KEYS */;
INSERT INTO `creditcard` VALUES (2454,0,'AMEX',500,10,2020,'Manager1','4485733078654607',2399),(2494,0,'VISA',500,10,2020,'Chorbi1','4485733078654607',2471),(2495,0,'MASTERCARD',500,10,2020,'Chorbi2','4485733078654607',2472),(2496,0,'AMEX',500,10,2020,'Chorbi3','4485733078654607',2473);
/*!40000 ALTER TABLE `creditcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  `chargedFee` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pwmktpkay2yx7v00mrwmuscl8` (`userAccount_id`),
  CONSTRAINT `FK_pwmktpkay2yx7v00mrwmuscl8` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `organisedMoment` datetime DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `seatsOffered` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `manager_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_631pasi0hdboeipx82pqbibkn` (`seatsOffered`),
  KEY `UK_fsy5jk057qep9wfso1isjyh2v` (`organisedMoment`),
  KEY `FK_cm6wrj8rjwiu1ftcjtq0l6wuo` (`manager_id`),
  CONSTRAINT `FK_cm6wrj8rjwiu1ftcjtq0l6wuo` FOREIGN KEY (`manager_id`) REFERENCES `manager` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (2461,0,'Event 1 just see your self','2017-05-10 00:00:00','https://images.pexels.com/photos/31257/pexels-photo.jpg?h=350',6,'event1',2399),(2462,0,'Event 2 just see your self','2017-06-17 00:00:00','https://images.pexels.com/photos/31257/pexels-photo.jpg?h=350',10,'event2',2399),(2463,0,'Event 3 just see your self','2017-01-17 00:00:00','https://images.pexels.com/photos/31257/pexels-photo.jpg?h=350',5,'event3',2399),(2464,0,'Event 4 just see your self','2016-04-17 00:00:00','https://images.pexels.com/photos/31257/pexels-photo.jpg?h=350',2,'event4',2399),(2465,0,'Event 5 just see your self','2015-04-17 00:00:00','https://images.pexels.com/photos/31257/pexels-photo.jpg?h=350',7,'event5',2399),(2466,0,'Event 6 just see your self','2017-05-15 00:00:00','https://images.pexels.com/photos/31257/pexels-photo.jpg?h=350',20,'event6',2399),(2467,0,'Event 1m2 just see your self','2017-12-20 00:00:00','https://images.pexels.com/photos/31257/pexels-photo.jpg?h=350',10,'event1Manager2',2400),(2468,0,'Event 2m2 just see your self','2017-12-22 00:00:00','https://images.pexels.com/photos/31257/pexels-photo.jpg?h=350',10,'event2Manager2',2400),(2469,0,'Event 1m3 just see your self','2017-06-22 00:00:00','https://images.pexels.com/photos/31257/pexels-photo.jpg?h=350',10,'event1Manager3',2401);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `likes` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `stars` int(11) NOT NULL,
  `liked_id` int(11) NOT NULL,
  `liker_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_ptedhnafmbvng8nyjipds40sq` (`stars`),
  KEY `FK_5f2yipvghjdnd82byd8c8au33` (`liked_id`),
  KEY `FK_4se8l0bnwm6trfmo1jsn3rln6` (`liker_id`),
  CONSTRAINT `FK_4se8l0bnwm6trfmo1jsn3rln6` FOREIGN KEY (`liker_id`) REFERENCES `chorbi` (`id`),
  CONSTRAINT `FK_5f2yipvghjdnd82byd8c8au33` FOREIGN KEY (`liked_id`) REFERENCES `chorbi` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` VALUES (2479,0,'nice','2016-10-10 20:30:00',3,2472,2471),(2480,0,'niiiice','2016-10-10 20:33:00',2,2473,2471),(2481,0,'','2016-10-10 20:34:00',1,2474,2471),(2482,0,'','2016-10-10 20:35:00',3,2475,2471),(2483,0,'','2016-10-10 20:36:00',2,2476,2471),(2484,0,'','2016-10-10 20:37:00',3,2477,2471),(2485,0,'','2016-10-10 20:38:00',1,2473,2472);
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  `chargedFee` double NOT NULL,
  `VAT` varchar(255) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_84bmmxlq61tiaoc7dy7kdcghh` (`userAccount_id`),
  CONSTRAINT `FK_84bmmxlq61tiaoc7dy7kdcghh` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES (2399,0,'manager1@acme.com','Manager1','+34333355535','Manager1',2392,6,'0','SoftSolves'),(2400,0,'manager2@acme.com','Manager2','+34333344443','Manager2',2393,0,'0','Solves'),(2401,0,'manager3@acme.com','Manager3','+34333336663','Manager3',2394,0,'0','Soft'),(2402,0,'manager4@acme.com','Manager4','+34333333893','Manager4',2395,0,'0','SSoves'),(2403,0,'manager5@acme.com','Manager5','+34333883333','Manager5',2396,0,'0','Golees'),(2404,0,'manager6@acme.com','Manager6','+34333773333','Manager6',2397,0,'0','Softer');
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `register`
--

DROP TABLE IF EXISTS `register`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `register` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `chorbi_id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_437kucja445jxhuxneqe8l5so` (`chorbi_id`),
  KEY `FK_8wa1m9x1sx7pwclumpmus86qm` (`event_id`),
  CONSTRAINT `FK_8wa1m9x1sx7pwclumpmus86qm` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  CONSTRAINT `FK_437kucja445jxhuxneqe8l5so` FOREIGN KEY (`chorbi_id`) REFERENCES `chorbi` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `register`
--

LOCK TABLES `register` WRITE;
/*!40000 ALTER TABLE `register` DISABLE KEYS */;
INSERT INTO `register` VALUES (2497,0,2471,2461),(2498,0,2471,2462),(2499,0,2471,2463),(2500,0,2471,2464),(2501,0,2471,2465),(2502,0,2471,2466),(2503,0,2472,2461),(2504,0,2473,2461),(2505,0,2474,2461),(2506,0,2475,2461),(2507,0,2476,2461),(2508,0,2476,2462);
/*!40000 ALTER TABLE `register` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `searchtemplate`
--

DROP TABLE IF EXISTS `searchtemplate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `searchtemplate` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `cacheMoment` datetime DEFAULT NULL,
  `desiredRelationship` varchar(255) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `chorbi_id` int(11) NOT NULL,
  `coordinates_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_n6tillyj5veb60uuot1b24lac` (`chorbi_id`),
  KEY `FK_sacit3a66xps0g3utxay4cuj6` (`coordinates_id`),
  CONSTRAINT `FK_sacit3a66xps0g3utxay4cuj6` FOREIGN KEY (`coordinates_id`) REFERENCES `coordinates` (`id`),
  CONSTRAINT `FK_n6tillyj5veb60uuot1b24lac` FOREIGN KEY (`chorbi_id`) REFERENCES `chorbi` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `searchtemplate`
--

LOCK TABLES `searchtemplate` WRITE;
/*!40000 ALTER TABLE `searchtemplate` DISABLE KEYS */;
INSERT INTO `searchtemplate` VALUES (2486,0,20,'2016-10-10 22:22:00','love','man','',2471,2439),(2487,0,0,'2016-10-10 22:22:00','','','chorbi3',2472,2441),(2488,0,0,'2016-10-10 22:22:00','friendship','','',2473,2443),(2489,0,0,'2016-10-10 22:22:00','','woman','',2474,2445),(2490,0,40,'2016-10-10 22:22:00','','','',2475,2447),(2491,0,0,'2016-10-10 22:22:00','','','',2476,2449),(2492,0,0,'2016-10-10 22:22:00','','','',2477,2451),(2493,0,0,'2016-10-10 22:22:00','','','',2478,2453);
/*!40000 ALTER TABLE `searchtemplate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `searchtemplate_chorbi`
--

DROP TABLE IF EXISTS `searchtemplate_chorbi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `searchtemplate_chorbi` (
  `SearchTemplate_id` int(11) NOT NULL,
  `chorbies_id` int(11) NOT NULL,
  KEY `FK_3t03s45pt13r22kb7y510060k` (`chorbies_id`),
  KEY `FK_8qqojnmpd0r5ur1bo34ymb973` (`SearchTemplate_id`),
  CONSTRAINT `FK_8qqojnmpd0r5ur1bo34ymb973` FOREIGN KEY (`SearchTemplate_id`) REFERENCES `searchtemplate` (`id`),
  CONSTRAINT `FK_3t03s45pt13r22kb7y510060k` FOREIGN KEY (`chorbies_id`) REFERENCES `chorbi` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `searchtemplate_chorbi`
--

LOCK TABLES `searchtemplate_chorbi` WRITE;
/*!40000 ALTER TABLE `searchtemplate_chorbi` DISABLE KEYS */;
INSERT INTO `searchtemplate_chorbi` VALUES (2486,2472),(2486,2473),(2486,2474),(2486,2475),(2486,2476),(2486,2477),(2487,2473);
/*!40000 ALTER TABLE `searchtemplate_chorbi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (2383,0,'','21232f297a57a5a743894a0e4a801fc3','admin'),(2384,0,'','3daa859a294cdefb20a84840240a76f5','chorbi1'),(2385,0,'','0c8746de81268518ff83490301db8652','chorbi2'),(2386,0,'','a2da05a88eead7e64593826cafc6a7a7','chorbi3'),(2387,0,'','a09dd233386632e297a7f4f461989563','chorbi4'),(2388,0,'','7e062f6f2a4c0f7ec5abacef2917e033','chorbi5'),(2389,0,'\0','0b41c51bd4b755c5b639498f55058fd3','chorbi6'),(2390,0,'\0','cd33d975ad65688dc4d54b67ed48fd1a','chorbi7'),(2391,0,'\0','cf0216b73314f84c64fd88f5adf4dfb2','chorbi8'),(2392,0,'','c240642ddef994358c96da82c0361a58','manager1'),(2393,0,'','8df5127cd164b5bc2d2b78410a7eea0c','manager2'),(2394,0,'','2d3a5db4a2a9717b43698520a8de57d0','manager3'),(2395,0,'','e1ec6fc941af3ba79a4ac5242dd39735','manager4'),(2396,0,'','029cb1d27c0b9c551703ccba2591c334','manager5'),(2397,0,'','46bae9ead851e0f288529e6322f4fd77','manager6');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (2383,'ADMINISTRATOR'),(2384,'CHORBI'),(2385,'CHORBI'),(2386,'CHORBI'),(2387,'CHORBI'),(2388,'CHORBI'),(2389,'CHORBI'),(2390,'CHORBI'),(2391,'CHORBI'),(2392,'MANAGER'),(2393,'MANAGER'),(2394,'MANAGER'),(2395,'MANAGER'),(2396,'MANAGER'),(2397,'MANAGER');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-27 16:34:59

commit;
