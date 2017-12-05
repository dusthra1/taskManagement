create database taskMgmt2_m1011319;

use taskMgmt2_m1011319;

create table PROJECT(PROJ_ID int primary key,PROJ_NAME varchar(50), PROJ_DESC varchar(200));
Create table EMPLOYEE(MID varchar(25) primary key, NAME varchar(30), JOIN_DATE Date, EMAIL_ID varchar(50),PROJ_ID int NOT NULL, foreign key (PROJ_ID) references PROJECT(PROJ_ID));

create table TASK(TASK_ID int primary key,
				TASK_DESC  varchar(255) DEFAULT NULL,				
				START_DATE date DEFAULT NULL,
				DUE_DATE date DEFAULT NULL,
				PROJ_ID int(11) NOT NULL,
				foreign key (PROJ_ID) references PROJECT(PROJ_ID));
				
create table TASK_EMPLOYEE(ID bigint(20) primary key,
							TASK_ID int(11) NOT NULL,
							MID varchar(25) NOT NULL,
							foreign key (MID) references EMPLOYEE(MID),
							foreign key (TASK_ID) references TASK(TASK_ID));	

CREATE TABLE users (
  USER_ID int(11) NOT NULL,
  FNAME varchar(45) NOT NULL,
  LNAME varchar(45) DEFAULT NULL,
  PASSWORD varchar(45) DEFAULT NULL,
  EMAIL_ID varchar(45) DEFAULT NULL,
  STATUS varchar(45) DEFAULT NULL,
  USERNAME varchar(45) NOT NULL,
  LOGIN_STATUS varchar(45) DEFAULT NULL,
  LAST_LOGIN datetime DEFAULT NULL,
  PRIMARY KEY (USER_ID),
  UNIQUE KEY USERNAME_UNIQUE (USERNAME)
);

CREATE TABLE role (
  ROLE_ID int(11) NOT NULL,
  ROLE_NAME varchar(45) NOT NULL,
  DESCRIPTION varchar(45) DEFAULT NULL,
  PRIMARY KEY (ROLE_ID)
);

CREATE TABLE users_role (
  ID bigint(20) NOT NULL,
  USER_ID int(11) NOT NULL,
  ROLE_ID int(11) NOT NULL,
  PRIMARY KEY (ID),
  KEY USER_ID_idx (USER_ID),
  KEY ROLE_ID_idx (ROLE_ID),
  FOREIGN KEY (USER_ID) REFERENCES users (USER_ID),
  FOREIGN KEY (ROLE_ID) REFERENCES role (ROLE_ID)
);

CREATE TABLE locale_master (
  id int(11) NOT NULL,
  locale varchar(45) DEFAULT NULL,
  description varchar(45) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY locale_UNIQUE (locale)
);


CREATE TABLE i18n_messages (
  'code' varchar(45) NOT NULL,
  'locale_id' int(11) NOT NULL,
  'message' varchar(250) DEFAULT NULL,
  PRIMARY KEY ('code','locale_id'),
  KEY 'FK_LOCALE_ID_idx' ('locale_id'),
  FOREIGN KEY ('locale_id') REFERENCES 'locale_master' ('id')
); 
				
CREATE TABLE hibernate_sequence (next_val bigint(20) DEFAULT NULL);
CREATE TABLE task_employee_seq (next_val bigint(20) DEFAULT NULL);
CREATE TABLE user_role_seq (next_val bigint(20) DEFAULT NULL);

insert into hibernate_sequence(next_val) values(1);	
insert into task_employee_seq(next_val) values(1);
insert into user_role_seq(next_val) values(1);			


insert into project values (1,'Iphone UI', 'Iphone UI Project');
insert into project values (2,'iPad Bugs', 'Ipad Project');
insert into project values (3,'Android UI', 'Android UI Project');
insert into project values (4,'Note 5 Bugs', 'Noot5 bugs list');

Insert into EMPLOYEE values('M1001000','Ramanand','2010-04-05', 'ramanand.dusthakar@mindtree.com',1);
Insert into EMPLOYEE values('M1001001','Jhon','2010-04-06', 'employee2@mindtree.com',1);
Insert into EMPLOYEE values('M1001002','Mickey','2010-04-07', 'employee3@mindtree.com',1);
Insert into EMPLOYEE values('M1001003','Ravinder','2010-04-08', 'employee4@mindtree.com',1);
Insert into EMPLOYEE values('M1001004','Santosh','2010-04-09', 'employee5@mindtree.com',2);
Insert into EMPLOYEE values('M1001005','Raj','2010-04-10', 'employee6@mindtree.com',2);
Insert into EMPLOYEE values('M1001006','Sangeeta','2010-04-11', 'employee7@mindtree.com',2);
Insert into EMPLOYEE values('M1001007','Sneha','2010-04-12', 'employee8@mindtree.com',3);
Insert into EMPLOYEE values('M1001008','Krishna','2010-04-13', 'employee9@mindtree.com',3);
Insert into EMPLOYEE values('M1001009','Kiran','2010-04-14', 'employee10@mindtree.com',3);
Insert into EMPLOYEE values('M1001010','Chetana','2010-04-15', 'employee11@mindtree.com',4);
Insert into EMPLOYEE values('M1001011','Arun','2010-04-16', 'employee12@mindtree.com',4);

insert into locale_master values(1,'en','english');
insert into locale_master values(2,'es','spanish');
insert into locale_master values(3,'fr','french');

Insert into i18n_messages values('error.accessdenied',1,'Access Denied. Please contact your administrator');
Insert into i18n_messages values('error.accessdenied',2,'Access Denied. Please contact your administratorSP');
Insert into i18n_messages values('error.accessdenied',3,'Access Denied. Please contact your administratorFR');

commit;