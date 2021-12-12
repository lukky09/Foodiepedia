/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 10.4.20-MariaDB : Database - db_foodiepedia
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_foodiepedia` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `db_foodiepedia`;

/*Table structure for table `bahanresep` */

DROP TABLE IF EXISTS `bahanresep`;

CREATE TABLE `bahanresep` (
  `bahan_id` int(20) NOT NULL,
  `resep_id` int(20) NOT NULL,
  `qty` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `bahanresep` */

insert  into `bahanresep`(`bahan_id`,`resep_id`,`qty`) values 
(4,1,2),
(3,2,11),
(1,2,2),
(4,3,3),
(1,4,1),
(7,5,1),
(5,6,2),
(7,6,1);

/*Table structure for table `bahans` */

DROP TABLE IF EXISTS `bahans`;

CREATE TABLE `bahans` (
  `bahan_id` int(20) NOT NULL AUTO_INCREMENT,
  `bahan_nama` varchar(20) NOT NULL,
  `bahan_isapproved` smallint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`bahan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

/*Data for the table `bahans` */

insert  into `bahans`(`bahan_id`,`bahan_nama`,`bahan_isapproved`) values 
(1,'Wortel',1),
(2,'Kubis',1),
(3,'Rinso',1),
(4,'Kentang',1),
(5,'Permen',0),
(6,'Sprite',0),
(7,'Pepsi',0);

/*Table structure for table `reseps` */

DROP TABLE IF EXISTS `reseps`;

CREATE TABLE `reseps` (
  `resep_id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(20) NOT NULL,
  `resep_nama` varchar(20) NOT NULL,
  `resep_desc` varchar(300) NOT NULL,
  `resep_isapproved` smallint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`resep_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

/*Data for the table `reseps` */

insert  into `reseps`(`resep_id`,`user_id`,`resep_nama`,`resep_desc`,`resep_isapproved`) values 
(1,1,'Cobaresep','kenthang',1),
(2,1,'ExtraExtra','ahii<br />manyedihkan',1),
(3,1,'Huew','ini kentang<br />hiyahiya',1),
(4,1,'Blep','blep?<br />blepp!<br />blpelblep<br />',1),
(5,1,'Coba1','doomi1',0),
(6,1,'Coba2','Doomi 2',0);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_username` varchar(20) NOT NULL,
  `user_viewedname` varchar(20) NOT NULL,
  `user_password` varchar(20) NOT NULL,
  `user_isadmin` tinyint(1) NOT NULL DEFAULT 0,
  `user_isbanned` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0 = unbanned, 1 = banned',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`user_id`,`user_username`,`user_viewedname`,`user_password`,`user_isadmin`,`user_isbanned`) values 
(1,'a','Coba','a',0,0),
(2,'bb','','bb',0,0),
(3,'admin','','admin312',1,0),
(4,'boedi','BoediGakTerlaluManta','123qweasd',0,0),
(5,'bca','BCA','123456789',0,0);

/*Table structure for table `user_favorites` */

DROP TABLE IF EXISTS `user_favorites`;

CREATE TABLE `user_favorites` (
  `user_id` int(20) NOT NULL,
  `resep_id` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_favorites` */

/*Table structure for table `user_follows` */

DROP TABLE IF EXISTS `user_follows`;

CREATE TABLE `user_follows` (
  `user_id` int(20) NOT NULL,
  `user_id_follower` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_follows` */

insert  into `user_follows`(`user_id`,`user_id_follower`) values 
(1,2);

/*Table structure for table `user_message` */

DROP TABLE IF EXISTS `user_message`;

CREATE TABLE `user_message` (
  `message_id` int(20) NOT NULL AUTO_INCREMENT,
  `user_message` varchar(255) NOT NULL,
  `user_from` int(20) NOT NULL,
  `user_to` int(20) NOT NULL,
  `message_time` varchar(20) NOT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_message` */

/*Table structure for table `user_rating` */

DROP TABLE IF EXISTS `user_rating`;

CREATE TABLE `user_rating` (
  `user_id` int(20) NOT NULL,
  `resep_id` int(20) NOT NULL,
  `rating` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_rating` */

insert  into `user_rating`(`user_id`,`resep_id`,`rating`) values 
(2,1,4),
(5,1,3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
