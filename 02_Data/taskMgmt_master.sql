CREATE DATABASE `taskmgmt` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `taskmgmt`;

CREATE TABLE `project` (
  `PROJ_ID` int(11) NOT NULL,
  `PROJ_NAME` varchar(50) DEFAULT NULL,
  `PROJ_DESC` varchar(200) DEFAULT NULL,
  `CREATE_TS` datetime DEFAULT NULL,
  `UPDATE_TS` datetime DEFAULT NULL,
  PRIMARY KEY (`PROJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `employee` (
  `MID` varchar(25) NOT NULL,
  `NAME` varchar(30) DEFAULT NULL,
  `JOIN_DATE` date DEFAULT NULL,
  `EMAIL_ID` varchar(50) DEFAULT NULL,
  `PROJ_ID` int(11) NOT NULL,
  `CREATE_TS` datetime DEFAULT NULL,
  `UPDATE_TS` datetime DEFAULT NULL,
  PRIMARY KEY (`MID`),
  KEY `FKffwdl0g4t8ndrdbqw7dluurot` (`PROJ_ID`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`PROJ_ID`) REFERENCES `project` (`PROJ_ID`),
  CONSTRAINT `FKffwdl0g4t8ndrdbqw7dluurot` FOREIGN KEY (`PROJ_ID`) REFERENCES `project` (`PROJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `files_upload` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(128) NOT NULL,
  `file_data` longblob NOT NULL,
  `content_type` varchar(100) DEFAULT NULL,
  `CREATE_TS` datetime DEFAULT NULL,
  `UPDATE_TS` datetime DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `type_code` (
  `TYPE_CODE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `TYPE_CODE` varchar(255) DEFAULT NULL,
  `CREATE_TS` datetime DEFAULT NULL,
  `UPDATE_TS` datetime DEFAULT NULL,
  PRIMARY KEY (`TYPE_CODE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `type_values` (
  `TYPE_VALUE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `TYPE_VALUE` varchar(255) DEFAULT NULL,
  `TYPE_CODE_ID` int(11) DEFAULT NULL,
  `CREATE_TS` datetime DEFAULT NULL,
  `UPDATE_TS` datetime DEFAULT NULL,
  PRIMARY KEY (`TYPE_VALUE_ID`),
  KEY `FKpw65w1rkgb26pr7f6xfj7fj6d` (`TYPE_CODE_ID`),
  CONSTRAINT `FKpw65w1rkgb26pr7f6xfj7fj6d` FOREIGN KEY (`TYPE_CODE_ID`) REFERENCES `type_code` (`TYPE_CODE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

CREATE TABLE `i18n_messages` (
  `CODE` varchar(255) NOT NULL,
  `MESSAGE` varchar(255) DEFAULT NULL,
  `LOCALE_ID` int(11) NOT NULL,
  `CREATE_TS` datetime DEFAULT NULL,
  `UPDATE_TS` datetime DEFAULT NULL,
  PRIMARY KEY (`CODE`,`LOCALE_ID`),
  KEY `FKmmh32yovj8snqsn2ffg2mcrs6` (`LOCALE_ID`),
  CONSTRAINT `FKmmh32yovj8snqsn2ffg2mcrs6` FOREIGN KEY (`LOCALE_ID`) REFERENCES `type_values` (`TYPE_VALUE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `role` (
  `ROLE_ID` int(11) NOT NULL,
  `CREATE_TS` datetime DEFAULT NULL,
  `UPDATE_TS` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `ROLE_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `role_permissions` (
  `ROLE_ID` int(11) NOT NULL,
  `PERMISSION_ID` int(11) NOT NULL,
  `ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKbr1ettrcfmc1k855meqhnmp0` (`PERMISSION_ID`),
  KEY `FK3sah2xlcnfnxs8r70pux3sp0f` (`ROLE_ID`),
  CONSTRAINT `FK3sah2xlcnfnxs8r70pux3sp0f` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ROLE_ID`),
  CONSTRAINT `FKbr1ettrcfmc1k855meqhnmp0` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `type_values` (`TYPE_VALUE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `role_permissions_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `task` (
  `TASK_ID` int(11) NOT NULL,
  `TASK_DESC` varchar(255) DEFAULT NULL,
  `START_DATE` date DEFAULT NULL,
  `DUE_DATE` date DEFAULT NULL,
  `PROJ_ID` int(11) NOT NULL,
  `TASK_NAME` varchar(255) DEFAULT NULL,
  `CREATE_TS` datetime DEFAULT NULL,
  `UPDATE_TS` datetime DEFAULT NULL,
  PRIMARY KEY (`TASK_ID`),
  KEY `task_ibfk_1` (`PROJ_ID`),
  CONSTRAINT `FKejtx7xw6e030gaavef7qmkdpc` FOREIGN KEY (`PROJ_ID`) REFERENCES `project` (`PROJ_ID`),
  CONSTRAINT `task_ibfk_1` FOREIGN KEY (`PROJ_ID`) REFERENCES `project` (`PROJ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `task_employee` (
  `ID` bigint(20) NOT NULL,
  `TASK_ID` int(11) NOT NULL,
  `MID` varchar(25) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKlqo3y4xr2tg5x82welml2h4ma` (`MID`),
  KEY `FKjsmaam62dnv7e1tin5ucjip77` (`TASK_ID`),
  CONSTRAINT `FKjsmaam62dnv7e1tin5ucjip77` FOREIGN KEY (`TASK_ID`) REFERENCES `task` (`TASK_ID`),
  CONSTRAINT `FKlqo3y4xr2tg5x82welml2h4ma` FOREIGN KEY (`MID`) REFERENCES `employee` (`MID`),
  CONSTRAINT `task_employee_ibfk_1` FOREIGN KEY (`MID`) REFERENCES `employee` (`MID`),
  CONSTRAINT `task_employee_ibfk_2` FOREIGN KEY (`TASK_ID`) REFERENCES `task` (`TASK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `task_employee_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `USER_ID` int(11) NOT NULL,
  `FNAME` varchar(45) NOT NULL,
  `LNAME` varchar(45) DEFAULT NULL,
  `PASSWORD` varchar(45) DEFAULT NULL,
  `EMAIL_ID` varchar(45) DEFAULT NULL,
  `STATUS` varchar(45) DEFAULT NULL,
  `USERNAME` varchar(45) NOT NULL,
  `LOGIN_STATUS` varchar(45) DEFAULT NULL,
  `LAST_LOGIN` datetime DEFAULT NULL,
  `CREATE_TS` datetime DEFAULT NULL,
  `UPDATE_TS` datetime DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `USERNAME_UNIQUE` (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users_role` (
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  `ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKro07f1ftodw09y67rp9adyfd8` (`ROLE_ID`),
  KEY `FK2tlfrcab3bnbq4s42062qcdew` (`USER_ID`),
  CONSTRAINT `FK2tlfrcab3bnbq4s42062qcdew` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`USER_ID`),
  CONSTRAINT `FKro07f1ftodw09y67rp9adyfd8` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users_role_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into type_code (TYPE_CODE_ID,TYPE_CODE,DESCRIPTION) values (1,'locale','locale master codes');
insert into type_code (TYPE_CODE_ID,TYPE_CODE,DESCRIPTION) values (2,'role_permissions','default permissions for role');

insert into type_values (TYPE_VALUE_ID,TYPE_VALUE,TYPE_CODE_ID,DESCRIPTION) values (1,'en',1,'english');
insert into type_values (TYPE_VALUE_ID,TYPE_VALUE,TYPE_CODE_ID,DESCRIPTION) values (2,'es',1,'spanish');
insert into type_values (TYPE_VALUE_ID,TYPE_VALUE,TYPE_CODE_ID,DESCRIPTION) values (3,'fr',1,'french');
insert into type_values (TYPE_VALUE_ID,TYPE_VALUE,TYPE_CODE_ID,DESCRIPTION) values (4,'PERM_ACCESS_ADMIN_AREA',2,'admin role permission');
insert into type_values (TYPE_VALUE_ID,TYPE_VALUE,TYPE_CODE_ID,DESCRIPTION) values (5,'PERM_ACCESS_VIEW_TASKS',2,'manager role permission');

insert into role (ROLE_ID,ROLE_NAME,DESCRIPTION) values (1,'SYSADMIN','super user');
insert into role (ROLE_ID,ROLE_NAME,DESCRIPTION) values (2,'ADMIN','super user');
insert into role (ROLE_ID,ROLE_NAME,DESCRIPTION) values (3,'MANAGER','super user');

insert into role_permissions (ID,PERMISSION_ID,ROLE_ID) values(1,4,2);
insert into role_permissions (ID,PERMISSION_ID,ROLE_ID) values(2,5,3);

insert into users (USER_ID,FNAME,LNAME,PASSWORD,EMAIL_ID,STATUS,USERNAME) values (1,'Ramanand','Dusthakar','YWJjZDEyMzQ=','ramanand@mindtree.com','A','dusthra1');
insert into users (USER_ID,FNAME,LNAME,PASSWORD,EMAIL_ID,STATUS,USERNAME) values (2,'Sneha','Dingari','YWJjZDEyMzQ=','sneha@mindtree.com','A','sdingari');

insert into users_role (ID,USER_ID,ROLE_ID) values (1,1,2);
insert into users_role (ID,USER_ID,ROLE_ID) values (2,2,3);

insert into project (PROJ_ID,PROJ_NAME,PROJ_DESC) values (1,'Iphone UI', 'Iphone UI Project');
insert into project (PROJ_ID,PROJ_NAME,PROJ_DESC) values (2,'iPad Bugs', 'Ipad Project');
insert into project (PROJ_ID,PROJ_NAME,PROJ_DESC) values (3,'Android UI', 'Android UI Project');
insert into project (PROJ_ID,PROJ_NAME,PROJ_DESC) values (4,'Note 5 Bugs', 'Note5 bugs list');

Insert into EMPLOYEE (MID,NAME,JOIN_DATE,EMAIL_ID,PROJ_ID) values('M1001000','Ramanand','2010-04-05', 'ramanand.dusthakar@mindtree.com',1);
Insert into EMPLOYEE (MID,NAME,JOIN_DATE,EMAIL_ID,PROJ_ID) values('M1001001','Jhon','2010-04-06', 'employee2@mindtree.com',1);
Insert into EMPLOYEE (MID,NAME,JOIN_DATE,EMAIL_ID,PROJ_ID) values('M1001002','Mickey','2010-04-07', 'employee3@mindtree.com',1);
Insert into EMPLOYEE (MID,NAME,JOIN_DATE,EMAIL_ID,PROJ_ID) values('M1001003','Ravinder','2010-04-08', 'employee4@mindtree.com',1);
Insert into EMPLOYEE (MID,NAME,JOIN_DATE,EMAIL_ID,PROJ_ID) values('M1001004','Santosh','2010-04-09', 'employee5@mindtree.com',2);
Insert into EMPLOYEE (MID,NAME,JOIN_DATE,EMAIL_ID,PROJ_ID) values('M1001005','Raj','2010-04-10', 'employee6@mindtree.com',2);
Insert into EMPLOYEE (MID,NAME,JOIN_DATE,EMAIL_ID,PROJ_ID) values('M1001006','Sangeeta','2010-04-11', 'employee7@mindtree.com',2);
Insert into EMPLOYEE (MID,NAME,JOIN_DATE,EMAIL_ID,PROJ_ID) values('M1001007','Sneha','2010-04-12', 'employee8@mindtree.com',3);
Insert into EMPLOYEE (MID,NAME,JOIN_DATE,EMAIL_ID,PROJ_ID) values('M1001008','Krishna','2010-04-13', 'employee9@mindtree.com',3);
Insert into EMPLOYEE (MID,NAME,JOIN_DATE,EMAIL_ID,PROJ_ID) values('M1001009','Kiran','2010-04-14', 'employee10@mindtree.com',3);
Insert into EMPLOYEE (MID,NAME,JOIN_DATE,EMAIL_ID,PROJ_ID) values('M1001010','Chetana','2010-04-15', 'employee11@mindtree.com',4);
Insert into EMPLOYEE (MID,NAME,JOIN_DATE,EMAIL_ID,PROJ_ID) values('M1001011','Arun','2010-04-16', 'employee12@mindtree.com',4);

insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.project',1,'Project');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.project',2,'ProjectSP');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.project',3,'ProjectFR');

insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.taskName',1,'Taskname');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.taskName',2,'TasknameSP');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.taskName',3,'TasknameFR');

insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.desc',1,'Description');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.desc',2,'DescriptionSP');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.desc',3,'DescriptionFR');

insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.startDate',1,'Start Date');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.startDate',2,'Start DateSP');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.startDate',3,'Start DateFR');

insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.dueDate',1,'Due Date');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.dueDate',2,'Due DateSP');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.dueDate',3,'Due DateFR');

insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.employees',1,'Employees');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.employees',2,'EmployeesSP');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('label.employees',3,'EmployeesFR');

insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('error.generic',1,'ERROR Occurred. Please contact your Administrator');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('error.generic',2,'ERROR OccurredSP. Please contact your Administrator');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('error.generic',3,'ERROR OccurredFR. Please contact your Administrator');

insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('logoff.successful',1,'You have successfully logged out');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('logoff.successful',2,'You have successfully logged out');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('logoff.successful',3,'You have successfully logged out');

--insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('',1,'');
--insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('',2,'');
--insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('',3,'');

insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('error.invalidLogin',1,'Invalid credentials.Please try again');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('error.invalidLogin',2,'Invalid credentials.Please try againSP');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('error.invalidLogin',3,'Invalid credentials.Please try againFR');


insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('error.emptyLoginCredentials',1,'Empty Credentials. Please try again');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('error.emptyLoginCredentials',2,'Empty Credentials. Please try againSP');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('error.emptyLoginCredentials',3,'Empty Credentials. Please try againFR');

insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('error.userNotFound',1,'User not found');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('error.userNotFound',2,'User not foundSP');
insert i18n_messages (CODE,LOCALE_ID,MESSAGE) values ('error.userNotFound',3,'User not foundFR');


COMMIT;
