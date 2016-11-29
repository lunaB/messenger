CREATE TABLE MEMBER(
	num number(10) not null,
	name varchar2(80) not null,
	email varchar2(80) not null,
	pw varchar2(80) not null,
	join_date date,
	CONSTRAINTS PK_MEMBER PRIMARY KEY (num)
);
CREATE SEQUENCE SEQ_MEMBER START WITH 1 INCREMENT BY 1 MAXVALUE 99999 CYCLE NOCACHE;