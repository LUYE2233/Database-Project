package org.thefouthgroup.manage;

import org.thefouthgroup.database.MyDruid;
import org.thefouthgroup.entity.Computer;
import org.thefouthgroup.entity.Room;
import org.thefouthgroup.entity.User;

import javax.sql.DataSource;
import java.util.logging.Logger;

import static org.thefouthgroup.database.MyDatabaseUtil.databaseInserter;

public class TeacherUtil {
    private static final DataSource dataSource = MyDruid.getDataSource();
    private static final Logger LOGGER = MyDruid.getLogger();
    public static void addRoom(Room room, User user){
        if(user.getGroupID() == 2){
            return;
        }
        String sql ="insert into room(roomid, roomname, roomprice, headid) VALUES ('{roomID}','{roomName}','{roomPrice}','{headID}')";
        sql =  sql.replace("{roomID}",room.getRoomID());
        sql =  sql.replace("{roomName}",room.getRoomName());
        sql =  sql.replace("{roomPrice}",String.valueOf(room.getRoomPrice()));
        sql =  sql.replace("{headID}", user.getUserID());
        databaseInserter(sql);
    }

    public static void addComputer(Computer computer,Room room){
    String sql = "insert into computer(computerid, roomid) VALUES ('{computerID}','{roomID}')";
        sql =  sql.replace("{roomID}",room.getRoomID());
        sql =  sql.replace("{computerID}",computer.getComputerID());
        databaseInserter(sql);
    }

    public static void deleteRoom(Room room){
        String sql = "delete from computer where ROOMID = '{roomID}'";
        sql =  sql.replace("{roomID}",room.getRoomID());
        databaseInserter(sql);
        sql = "delete from room where ROOMID = '{roomID}'";
        sql =  sql.replace("{roomID}",room.getRoomID());
        databaseInserter(sql);
    }

    public static void deleteComputer(Computer computer){
        String sql = "delete from computer where COMPUTERID = '{computerID}'";
        sql =  sql.replace("{computerID}",computer.getComputerID());
        databaseInserter(sql);
    }

    public static void changeInformation(User changer,User user){
        if(changer.getGroupID() != 2 || User.isCorrect(user)){
            String sql = "insert into USER(USERNAME,PASSWORD,USERID,USERBALANCE) values('{username}','{password}','{userID}','{userBalance}')";
            sql = sql.replace("{username}", user.getUserName());
            sql = sql.replace("{password}", user.getPassword());
            sql = sql.replace("{userID}", user.getUserID());
            sql = sql.replace("{userBalance}",String.valueOf(user.getBalance()));
            databaseInserter(sql);
        }
    }
}
