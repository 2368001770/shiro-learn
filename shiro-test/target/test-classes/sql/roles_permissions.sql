/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.6.34 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `roles_permissions` (
	`role_name` varchar (33),
	`permission` varchar (33)
); 
insert into `roles_permissions` (`role_name`, `permission`) values('admin','user:delete');
insert into `roles_permissions` (`role_name`, `permission`) values('admin','user:update');
insert into `roles_permissions` (`role_name`, `permission`) values('admin','user:select');
