create table `user`
(
    USERNAME    varchar(20),
    PASSWORD    varchar(20),
    USERID      char(10) primary key,
    USERBALANCE double
);

create table usergroup
(
    USERID  char(10) references user (USERID),
    GROUPID char(1)
);

create table room
(
    ROOMID    char(5) primary key,
    ROOMNAME  varchar(20),
    ROOMPRICE double,
    HEADID    char(10) references user (USERID)
);

create table computer
(
    COMPUTERID char(10) primary key,
    ROOMID     char(5) references room (ROOMID)
);

create table USERECORD
(
    USERID char(10) references user (USERID),
    COMPUTERID char(10) references computer (COMPUTERID),
    STARTTIME varchar(20),
    ENDTIME varchar(20),
    COST double
);