package org.kukuking.database;

import org.kukuking.entity.Computer;
import org.kukuking.entity.Room;
import org.kukuking.entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MyDatabaseUtil {
    private static final DataSource dataSource = MyDruid.getDataSource();
    private static final Logger LOGGER = MyDruid.getLogger();

//    下面俩是查询 和 插入/更新的模板，第二个的result不能回传，因为在statement.close()后resultset就是空集了

    public static void databaseInserter(String sql) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
    }

    private static ResultSet databaseSearcher(String sql) {
        ResultSet result = null;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return result;
    }

    /*________________________________________________________________________________________________*/
    //用户登陆
    public static boolean login(String userID, String password) {
        boolean result = false;
        if (userID == null || password == null) {
            return result;
        }
        String sql = "SELECT PASSWORD FROM user_main WHERE USERID = '{userID}'";
        sql = sql.replace("{userID}", userID);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next() && rs.getString("PASSWORD").equals(password)) {
                result = true;
            }
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return result;
    }

    public static void initDB(){
        String sql = """
                """;
    }

    public static String getUserID(String userName) {
        String result = null;
        if (userName == null) {
            return result;
        }
        String sql = "SELECT USERID FROM user_main WHERE USERNAME = '{userName}'";
        sql = sql.replace("{userName}", userName);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                result = rs.getString(1);
            }
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return result;
    }

    //用户注册
    public static void signUp(String userName, String password, String userID) {
        if (getUserID(userName) == null) {
            String sql = "insert into user_main(USERNAME,PASSWORD,USERID,USERBALANCE) values('{username}','{password}','{userID}','{userBalance}')";
            sql = sql.replace("{username}", userName);
            sql = sql.replace("{password}", password);
            sql = sql.replace("{userID}", userID);
            sql = sql.replace("{userBalance}", "0");
            databaseInserter(sql);
            String groupSQL = "insert into user_group(userid, groupid) VALUES ('{userid}','{groupid}')";
            groupSQL = groupSQL.replace("{userid}", userID);
            groupSQL = groupSQL.replace("{groupid}", "2");
            databaseInserter(groupSQL);
        }
//      groupID: 0=root 1=teacher 2=student
    }

    public static void reset(String userID, String password) {
        String sql = "UPDATE user_main SET PASSWORD = '{password}' WHERE USERID = '{userID}'";
        sql = sql.replace("{userID}", userID);
        sql = sql.replace("{password}", password);
        databaseInserter(sql);
    }

    public static int indentify(String userID) {
        int result = 2;
        String sql = "select GROUPID from user_group where USERID = '{userID}'";
        sql = sql.replace("{userID}", userID);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                result = Integer.parseInt(rs.getString(1));
            }
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return result;
    }

    public static List<Room> loadRoom() {
        List<Room> result = new ArrayList<>();
        String sql = "select * from room";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            for (int i = 1; i <= rs.getRow(); i++) {
                System.out.println(rs.getString(i));
            }
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return result;
    }

    public static List<Computer> loadComputer(Room room) {
        String roomID = room.getRoomID();
        String sql = "select COMPUTERID from computer where ROOMID = '{roomID}'";
        List<Computer> computerList = room.getComputerList();
        sql = sql.replace("{roomID}", roomID);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                computerList.add(new Computer(rs.getString(1)));
            }
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return computerList;
    }

    public static User buildUserFromDB(String userID) {
        String sql = "select user_main.USERID,USERNAME,PASSWORD,USERBALANCE,GROUPID from user_main,user_group where user_main.USERID=user_group.USERID and user_main.USERID = '{userID}'";
        sql = sql.replace("{userID}", userID);
        ResultSet result = null;
        User user = null;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            result = statement.executeQuery(sql);
            while (result.next()) {
                String userIDt = result.getString(1);
                String userName = result.getString(2);
                String password = result.getString(3);
                double userBalance = Double.valueOf(result.getString(4));
                int groupID = Integer.parseInt(result.getString(5));
                user = new User(userName,password,userIDt,userBalance,groupID);
            }
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return user;
    }

    public static Room buildRoomFromDB(String roomID) {
        String sql = "select ROOMID,ROOMNAME,ROOMPRICE from room where ROOMID = '{roomID}'";
        sql = sql.replace("{roomID}", roomID);
        ResultSet result = null;
        Room room = null;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            result = statement.executeQuery(sql);
            while (result.next()) {
                String roomIDt = result.getString(1);
                String roomName = result.getString(2);
                double roomPrice = Double.parseDouble(result.getString(3));
                room = new Room(roomIDt,roomName,roomPrice);
            }
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return room;
    }

    public static void main(String[] args) {
        String sql = "select user_main.USERID,USERNAME,PASSWORD,USERBALANCE,GROUPID from user_main,user_group where user.USERID=usergroup.USERID and user.USERID = '{userID}'";
        sql = sql.replace("{userID}", "2021213196");
        ResultSet result = null;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            result = statement.executeQuery(sql);
            while (result.next()) {
                String userID = result.getString(1);
                String userName = result.getString(2);
                String password = result.getString(3);
                double userBalance = Double.valueOf(result.getString(4));
                int groupID = Integer.parseInt(result.getString(5));
            }
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
    }


}