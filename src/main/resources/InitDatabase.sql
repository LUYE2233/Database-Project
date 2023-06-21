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
    STARTTIME datetime,
    ENDTIME datetime,
    COST double
);

delimiter $$
create trigger set_start_time after insert on  userecord for each row begin
    update userecord set STARTTIME = now() where STARTTIME is null and USERID=NEW.USERID and COMPUTERID=NEW.COMPUTERID;
end $$

create trigger set_end_time after update on userecord for each row begin
    update userecord set ENDTIME = now() where ENDTIME is null and COST=-1 and USERID=NEW.USERID and COMPUTERID=NEW.COMPUTERID;
    update userecord set COST = timestampdiff(second ,STARTTIME,ENDTIME) * (
        select ROOMPRICE from room where ROOMID = (
            select ROOMID from computer where computer.COMPUTERID = NEW.COMPUTERID
        )
    ) where COST=-1 and USERID=NEW.USERID and COMPUTERID=NEW.COMPUTERID;
end $$
delimiter ;