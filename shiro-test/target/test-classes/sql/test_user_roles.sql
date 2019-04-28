/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.6.34 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `test_user_roles` (
	`username` varchar (33),
	`role_name` varchar (33)
); 
insert into `test_user_roles` (`username`, `role_name`) values('czj','admin');
insert into `test_user_roles` (`username`, `role_name`) values('czj','user');
