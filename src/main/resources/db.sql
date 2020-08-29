--------------------------------------------------------
--  SCHEMA
--------------------------------------------------------


-- USER SQL
CREATE USER cards IDENTIFIED BY cards 
DEFAULT TABLESPACE "USERS"
TEMPORARY TABLESPACE "TEMP";

-- QUOTAS
ALTER USER cards QUOTA UNLIMITED ON NEWTS;

-- SYSTEM PRIVILEGES
GRANT CREATE TRIGGER TO cards ;
GRANT CREATE VIEW TO cards ;
GRANT CREATE SESSION TO cards ;
GRANT CREATE TABLE TO cards ;
GRANT CREATE TYPE TO cards ;
GRANT CREATE SEQUENCE TO cards ;
GRANT UNLIMITED TABLESPACE TO cards ;
GRANT CREATE PROCEDURE TO cards ;


--------------------------------------------------------
--  TABLES
--------------------------------------------------------


DROP TABLE Bank_Approval CASCADE CONSTRAINTS ;

DROP TABLE Cards CASCADE CONSTRAINTS ;

DROP TABLE Clients CASCADE CONSTRAINTS ;

DROP TABLE Employees CASCADE CONSTRAINTS ;

DROP TABLE Requests CASCADE CONSTRAINTS ;


CREATE TABLE Bank_Approval
  (
    Approval_Id   NUMBER (5) NOT NULL ,
    Approval_Date DATE NOT NULL ,
    Request_Id    NUMBER (5) NOT NULL
  ) ;
COMMENT ON COLUMN Bank_Approval.Approval_Id
IS
  'APPROVAL PER REQUEST' ;
  ALTER TABLE Bank_Approval ADD CONSTRAINT Bank_Approval_PK PRIMARY KEY ( Approval_Id ) ;

CREATE TABLE Cards
  (
    Card_Id    NUMBER (5) NOT NULL ,
    PIN        NUMBER (5) NOT NULL ,
    Issue_Date DATE NOT NULL ,
    Request_Id NUMBER (5) NOT NULL
  ) ;
ALTER TABLE Cards ADD CONSTRAINT Cards_PK PRIMARY KEY ( Card_Id ) ;

CREATE TABLE Clients
  (
    Client_Id    NUMBER (5) NOT NULL ,
    First_Name   VARCHAR2 (20 CHAR) NOT NULL ,
    Last_Name    VARCHAR2 (20 CHAR) NOT NULL ,
    Address      VARCHAR2 (50 CHAR) NOT NULL ,
    Email        VARCHAR2 (30 CHAR) ,
    Phone_Number VARCHAR2 (15 CHAR)
  ) ;
ALTER TABLE Clients ADD CONSTRAINT Clients_PK PRIMARY KEY ( Client_Id ) ;

CREATE TABLE Employees
  (
    Employee_Id  NUMBER (5) NOT NULL ,
    First_Name   VARCHAR2 (20 CHAR) NOT NULL ,
    Last_Name    VARCHAR2 (20 CHAR) NOT NULL ,
    Address      VARCHAR2 (50 CHAR) NOT NULL ,
    Phone_Number VARCHAR2 (15 CHAR)
  ) ;
ALTER TABLE Employees ADD CONSTRAINT Employees_PK PRIMARY KEY ( Employee_Id ) ;

CREATE TABLE Requests
  (
    Request_id     NUMBER (5) NOT NULL ,
    Account_number VARCHAR2 (20 CHAR) NOT NULL ,
    Request_Date   DATE NOT NULL ,
    Client_Id      NUMBER (5) NOT NULL ,
    Employee_Id    NUMBER (5) NOT NULL
  ) ;
COMMENT ON COLUMN Requests.Request_id
IS
  'REQUEST PER CARD' ;

ALTER TABLE Requests ADD CONSTRAINT Requests_PK PRIMARY KEY ( Request_id ) ;

ALTER TABLE Bank_Approval ADD CONSTRAINT Bank_Appr_Client_Req_FK FOREIGN KEY ( Request_Id ) REFERENCES Requests ( Request_id ) ;

ALTER TABLE Cards ADD CONSTRAINT Cards_Requests_FK FOREIGN KEY ( Request_Id ) REFERENCES Requests ( Request_id ) ;

ALTER TABLE Requests ADD CONSTRAINT Requests_Clients_FK FOREIGN KEY ( Client_Id ) REFERENCES Clients ( Client_Id ) ;

ALTER TABLE Requests ADD CONSTRAINT Requests_Employees_FK FOREIGN KEY ( Employee_Id ) REFERENCES Employees ( Employee_Id ) ;





--------------------------------------------------------
--  SEQUENCES  --  STARTS FROM 10
--------------------------------------------------------



   CREATE SEQUENCE  "CARDS"."BANL_APPROVAL_SEQ"  MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 10 NOCACHE  NOORDER  NOCYCLE ;


   CREATE SEQUENCE  "CARDS"."CARDS_SEQ"  MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 10 NOCACHE  NOORDER  NOCYCLE ;


   CREATE SEQUENCE  "CARDS"."CLIENTS_SEQ"  MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 10 NOCACHE  NOORDER  NOCYCLE ;


   CREATE SEQUENCE  "CARDS"."EMPLOYEES_SEQ"  MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 10 NOCACHE  NOORDER  NOCYCLE ;


   CREATE SEQUENCE  "CARDS"."REQUESTS_SEQ"  MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 10 NOCACHE  NOORDER  NOCYCLE ;



--------------------------------------------------------
--  DATA
--------------------------------------------------------

Insert into CARDS.CLIENTS (CLIENT_ID,FIRST_NAME,LAST_NAME,ADDRESS,EMAIL,PHONE_NUMBER) values ('1','John','Miller','45 John Street, MillerVile, CA 94109',null,null);
Insert into CARDS.CLIENTS (CLIENT_ID,FIRST_NAME,LAST_NAME,ADDRESS,EMAIL,PHONE_NUMBER) values ('2','Ken','Williams','66 Ken Lane, WilliamsVile, NY 74183',null,null);
Insert into CARDS.CLIENTS (CLIENT_ID,FIRST_NAME,LAST_NAME,ADDRESS,EMAIL,PHONE_NUMBER) values ('3','Martha','Simmons','155 Simmons Road., MarthaVille, WA 98801',null,null);


Insert into CARDS.EMPLOYEES (EMPLOYEE_ID,FIRST_NAME,LAST_NAME,ADDRESS,PHONE_NUMBER) values ('1','Sharon','Green','131 Sharon Street, GreenCity, France',null);
Insert into CARDS.EMPLOYEES (EMPLOYEE_ID,FIRST_NAME,LAST_NAME,ADDRESS,PHONE_NUMBER) values ('2','Eric','Sanders','55 Sanders Street, EricCity, France',null);


Insert into CARDS.REQUESTS (REQUEST_ID,ACCOUNT_NUMBER,REQUEST_DATE,CLIENT_ID,EMPLOYEE_ID) values ('1','11120055',to_date('16-AUG-2019','DD-MON-yyyy'),'1','1');
Insert into CARDS.REQUESTS (REQUEST_ID,ACCOUNT_NUMBER,REQUEST_DATE,CLIENT_ID,EMPLOYEE_ID) values ('2','11120056',to_date('16-AUG-2019','DD-MON-yyyy'),'1','1');
Insert into CARDS.REQUESTS (REQUEST_ID,ACCOUNT_NUMBER,REQUEST_DATE,CLIENT_ID,EMPLOYEE_ID) values ('3','112206557',to_date('13-AUG-2019','DD-MON-yyyy'),'2','2');
Insert into CARDS.REQUESTS (REQUEST_ID,ACCOUNT_NUMBER,REQUEST_DATE,CLIENT_ID,EMPLOYEE_ID) values ('4','112596557',to_date('13-AUG-2019','DD-MON-yyyy'),'3','2');


Insert into CARDS.BANK_APPROVAL (APPROVAL_ID,APPROVAL_DATE,REQUEST_ID) values ('1',to_date('20-AUG-2019','DD-MON-yyyy'),'1');
Insert into CARDS.BANK_APPROVAL (APPROVAL_ID,APPROVAL_DATE,REQUEST_ID) values ('2',to_date('20-AUG-2019','DD-MON-yyyy'),'2');
Insert into CARDS.BANK_APPROVAL (APPROVAL_ID,APPROVAL_DATE,REQUEST_ID) values ('3',to_date('20-AUG-2019','DD-MON-yyyy'),'3');
Insert into CARDS.BANK_APPROVAL (APPROVAL_ID,APPROVAL_DATE,REQUEST_ID) values ('4',to_date('19-AUG-2019','DD-MON-yyyy'),'4');


Insert into CARDS.CARDS (CARD_ID,PIN,ISSUE_DATE,APPROVAL_ID) values ('1','12345',to_date('21-AUG-2019','DD-MON-yyyy'),'1');
Insert into CARDS.CARDS (CARD_ID,PIN,ISSUE_DATE,APPROVAL_ID) values ('2','12346',to_date('21-AUG-2019','DD-MON-yyyy'),'2');
Insert into CARDS.CARDS (CARD_ID,PIN,ISSUE_DATE,APPROVAL_ID) values ('3','54321',to_date('21-AUG-2019','DD-MON-yyyy'),'3');
Insert into CARDS.CARDS (CARD_ID,PIN,ISSUE_DATE,APPROVAL_ID) values ('4','11133',to_date('21-AUG-2019','DD-MON-yyyy'),'4');
