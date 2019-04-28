/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.6.34 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `test_roles_permissions` (
	`role_name` varchar (33),
	`permission` varchar (33)
); 
insert into `test_roles_permissions` (`role_name`, `permission`) values('admin','user:delete');
insert into `test_roles_permissions` (`role_name`, `permission`) values('admin','user:update');
insert into `test_roles_permissions` (`role_name`, `permission`) values('admin','user:select');
insert into `test_roles_permissions` (`role_name`, `permission`) values('user','user:select');
