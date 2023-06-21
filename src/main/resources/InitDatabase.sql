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

drop trigger set_start_time;
drop trigger set_end_time;

delimiter $$
create trigger set_start_time before insert on userecord for each row begin
    set new.STARTTIME = now();
end $$
delimiter ;

delimiter $$
create trigger set_end_time before update on userecord for each row begin
    set new.ENDTIME = now();
    set new.COST = timestampdiff(second ,new.STARTTIME,new.ENDTIME) * (
        select ROOMPRICE from room where ROOMID = (
            select ROOMID from computer where computer.COMPUTERID = NEW.COMPUTERID
        )
    );
end $$
delimiter ;

delete from userecord where USERID = '2021213209';

select USERID,COMPUTERID,COST,ENDTIME from userecord where DATE_FORMAT(ENDTIME,'%Y-%m-%d %H:%i:%s') between '{beginTime}' and '{endTime}';

select USERID,COMPUTERID,COST,STARTTIME,ENDTIME from userecord where ENDTIME between '{beginTime}' and '{endTime}';