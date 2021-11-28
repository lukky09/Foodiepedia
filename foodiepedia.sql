/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 10.4.21-MariaDB : Database - db_foodiepedia
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
  `resep_id` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `bahanresep` */

/*Table structure for table `bahans` */

DROP TABLE IF EXISTS `bahans`;

CREATE TABLE `bahans` (
  `bahan_id` int(20) NOT NULL AUTO_INCREMENT,
  `bahan_nama` varchar(20) NOT NULL,
  `bahan_isapproved` smallint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`bahan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `bahans` */

insert  into `bahans`(`bahan_id`,`bahan_nama`,`bahan_isapproved`) values 
(1,'Wortel',1),
(2,'Kubis',1),
(3,'Rinso',1),
(4,'Kentang',1);

/*Table structure for table `reseps` */

DROP TABLE IF EXISTS `reseps`;

CREATE TABLE `reseps` (
  `resep_id` int(20) NOT NULL AUTO_INCREMENT,
  `resep_nama` varchar(20) NOT NULL,
  `resep_isapproved` smallint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`resep_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `reseps` */

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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
