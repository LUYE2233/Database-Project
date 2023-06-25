package org.kukuking.manage;

import org.kukuking.database.MyDruid;
import org.kukuking.entity.Computer;
import org.kukuking.entity.Room;
import org.kukuking.entity.UseRecord;
import org.kukuking.entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.kukuking.database.MyDatabaseUtil.databaseInserter;

public class TeacherUtil {
    private static final DataSource dataSource = MyDruid.getDataSource();
    private static final Logger LOGGER = MyDruid.getLogger();

    //添加机房
    public static void addRoom(Room room, User user) {
        if (user.getGroupID() == 2) {
            return;
        }
        String sql = "insert into room(roomid, roomname, roomprice, headid) VALUES ('{roomID}','{roomName}','{roomPrice}','{headID}')";
        sql = sql.replace("{roomID}", room.getRoomID());
        sql = sql.replace("{roomName}", room.getRoomName());
        sql = sql.replace("{roomPrice}", String.valueOf(room.getRoomPrice()));
        sql = sql.replace("{headID}", user.getUserID());
        databaseInserter(sql);
    }
    //添加电脑
    public static void addComputer(Computer computer, Room room) {
        String sql = "insert into computer(computerid, roomid) VALUES ('{computerID}','{roomID}')";
        sql = sql.replace("{roomID}", room.getRoomID());
        sql = sql.replace("{computerID}", computer.getComputerID());
        room.getComputerList().add(computer);
        databaseInserter(sql);
    }
    //删除机房
    public static void deleteRoom(Room room) {
        String sql = "delete from computer where ROOMID = '{roomID}'";
        sql = sql.replace("{roomID}", room.getRoomID());
        databaseInserter(sql);
        sql = "delete from room where ROOMID = '{roomID}'";
        sql = sql.replace("{roomID}", room.getRoomID());
        databaseInserter(sql);
    }
    //删除电脑
    public static void deleteComputer(Computer computer) {
        String sql = "delete from computer where COMPUTERID = '{computerID}'";
        sql = sql.replace("{computerID}", computer.getComputerID());
        databaseInserter(sql);
    }
    //修改用户信息
    public static void changeUserInfo(User changer, User user) {
        if (changer.getGroupID() != 2 && User.isCorrect(user)) {
            String sql = "update user set PASSWORD = '{password}' where USERID='{userID}'";
            sql = sql.replace("{userID}", user.getUserID());
            sql = sql.replace("{password}", user.getPassword());
            databaseInserter(sql);

            sql = "update user set USERNAME = '{username}' where USERID='{userID}'";
            sql = sql.replace("{userID}", user.getUserID());
            sql = sql.replace("{username}", user.getUserName());
            databaseInserter(sql);

            sql = "update usergroup set GROUPID = '{groupID}' where USERID='{userID}'";
            sql = sql.replace("{userID}", user.getUserID());
            sql = sql.replace("{groupID}", String.valueOf(user.getGroupID()));
            databaseInserter(sql);

            sql = "update user set USERBALANCE = '{userBalance}' where USERID='{userID}'";
            sql = sql.replace("{userID}", user.getUserID());
            sql = sql.replace("{userBalance}", String.valueOf(user.getBalance()));
            databaseInserter(sql);
        }
    }
    //修改机房信息
    public static void changeRoomInfo(User changer,Room room){
        String sql = "update room set ROOMNAME = '{roomName}' where ROOMID = '{roomID}'";
        sql = sql.replace("{roomName}",room.getRoomName());
        sql = sql.replace("{roomID}",room.getRoomID());

        sql = "update room set ROOMPRICE = '{roomPrice}' where ROOMID = '{roomID}'";
        sql = sql.replace("{roomPrice}",String.valueOf(room.getRoomPrice()));
        sql = sql.replace("{roomID}",room.getRoomID());
    }

    public static List<UseRecord> recordByDate(User user, String beginTime, String endTime) {
        List<UseRecord> result = new ArrayList<>();
        if (user.getGroupID() != 2) {
            String sql = "select USERID,COMPUTERID,COST,STARTTIME,ENDTIME from userecord where ENDTIME between '{beginTime}' and '{endTime}';";
            sql = sql.replace("{beginTime}", beginTime);
            sql = sql.replace("{endTime}", endTime);
            ResultSet rs = null;
            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {
                rs = statement.executeQuery(sql);
                while (rs.next()) {
                    result.add(new UseRecord(rs.getString(4), rs.getString(5), rs.getString(1), rs.getString(2), Double.valueOf(rs.getString(3))));
                }
            } catch (SQLException e) {
                LOGGER.info(e.toString());
            }
        }
        return result;
    }

    public static void sumByRoom(Room room) {
        String sql = "select sum(COST) from userecord where COMPUTERID in (select COMPUTERID from computer where ROOMID = '{roomID}')";
        sql = sql.replace("{roomID}", room.getRoomID());
        double sum = 0;
        ResultSet rs = null;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                sum = Double.parseDouble(rs.getString(1));
            }
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        System.out.println(room.getRoomName()+"上机总收费："+sum);
        System.out.println(room.getRoomName()+"上机总时长"+sum/room.getRoomPrice());
    }
}
