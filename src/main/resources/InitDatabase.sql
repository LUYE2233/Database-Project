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

#自动填入上机时间触发器
delimiter $$
create trigger set_start_time before insert on userecord for each row begin
    set new.STARTTIME = now();
end $$
delimiter ;
#自动填入下机时间并计算花费触发器
delimiter $$
create trigger set_end_time before update on userecord for each row begin
    set new.ENDTIME = now();
    set new.COST = timestampdiff(second ,new.STARTTIME,new.ENDTIME) * (
        select ROOMPRICE from room where ROOMID = (
            select ROOMID from computer where computer.COMPUTERID = NEW.COMPUTERID
        )
    );
    update user set USERBALANCE = USERBALANCE - NEW.COST where USERID = NEW.USERID;
end $$
delimiter ;

delete from userecord where USERID = '2021213209';

select USERID,COMPUTERID,COST,ENDTIME from userecord where DATE_FORMAT(ENDTIME,'%Y-%m-%d %H:%i:%s') between '{beginTime}' and '{endTime}';

select USERID,COMPUTERID,COST,STARTTIME,ENDTIME from userecord where ENDTIME between '{beginTime}' and '{endTime}';

select sum(COST) from userecord where COMPUTERID in (select COMPUTERID from computer where ROOMID = '00001');

select sum(TIMESTAMPDIFF(minute ,STARTTIME,ENDTIME)) from userecord where ENDTIME between '{beginTime}' and '{endTime}';

select sum(TIMESTAMPDIFF(second,STARTTIME,ENDTIME)) from userecord where ENDTIME between '2023-6-21 10:00:00' and '2023-6-21 12:00:00';

update room set ROOMNAME = '{roomName}' where ROOMID = '{roomID}';

call count_room_time('00001','2023-6-21 10:00:00','2023-6-21 12:00:00');
call count_room_Cost('00001','2023-6-21 10:00:00','2023-6-21 12:00:00');

#统计指定时间段内房间上机的总时长
delimiter $$
create procedure webtestdatabase.count_room_time(in ROOMID char(5),in BEGINTIME datetime, in FINISHTIME datetime) begin
    select sum(TIMESTAMPDIFF(second,STARTTIME,ENDTIME)) from userecord where ENDTIME between BEGINTIME and FINISHTIME and userecord.COMPUTERID in (select COMPUTERID from room where room.ROOMID = ROOMID);
end $$
delimiter ;

#统计指定时间段内房间的总消费
delimiter $$
create procedure webtestdatabase.count_room_cost(in ROOMID char(5),in BEGINTIME datetime, in FINISHTIME datetime) begin
    select sum(COST) from userecord where ENDTIME between BEGINTIME and FINISHTIME and userecord.COMPUTERID in (select COMPUTERID from room where room.ROOMID = ROOMID);
end $$
delimiter ;

#统计指定时间段内管理人员的收费合计
delimiter $$
create procedure webtestdatabase.count_manage_cost(in HEADID char(10),in BEGINTIME datetime, in FINISHTIME datetime) begin
    select sum(COST) from userecord where ENDTIME between BEGINTIME and FINISHTIME and userecord.COMPUTERID in (select COMPUTERID from room where room.HEADID = HEADID);
end $$
delimiter ;

drop procedure count_room_time;
drop procedure count_room_Cost;