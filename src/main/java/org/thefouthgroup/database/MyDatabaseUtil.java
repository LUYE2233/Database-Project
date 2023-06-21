package org.thefouthgroup.database;

import org.thefouthgroup.entity.Computer;
import org.thefouthgroup.entity.Room;
import org.thefouthgroup.entity.User;

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

    public static boolean login(String userName, String password) {
        boolean result = false;
        if (userName == null || password == null) {
            return result;
        }
        String sql = "SELECT PASSWORD FROM USER WHERE USERNAME = '{userName}'";
        sql = sql.replace("{userName}", userName);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next() && rs.getString(1).equals(password)) {
                result = true;
            }
        } catch (SQLException e) {
            LOGGER.info(e.toString());
        }
        return result;
    }

    public static String getUserID(String userName) {
        String result = null;
        if (userName == null) {
            return result;
        }
        String sql = "SELECT USERID FROM USER WHERE USERNAME = '{userName}'";
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

    public static int getUserGroup(String userID) {
        int result = 2;
        if (userID == null) {
            return result;
        }
        String sql = "SELECT GROUPID FROM usergroup WHERE USERID = '{userID}'";
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

    public static void signUp(String userName, String password, String userID) {
        if (getUserID(userName) == null) {
            String sql = "insert into USER(USERNAME,PASSWORD,USERID,USERBALANCE) values('{username}','{password}','{userID}','{userBalance}')";
            sql = sql.replace("{username}", userName);
            sql = sql.replace("{password}", password);
            sql = sql.replace("{userID}", userID);
            sql = sql.replace("{userBalance}", "0");
            databaseInserter(sql);
            String groupSQL = "insert into usergroup(userid, groupid) VALUES ('{userid}','{groupid}')";
            groupSQL = groupSQL.replace("{userid}", userID);
            groupSQL = groupSQL.replace("{groupid}", "2");
            databaseInserter(groupSQL);
        }
//      groupID: 0=root 1=teacher 2=student
    }

    public static void reset(String userName, String password) {
        String sql = "UPDATE USER SET PASSWORD = '{password}' WHERE USERNAME = '{username}'";
        sql = sql.replace("{username}", userName);
        sql = sql.replace("{password}", password);
        databaseInserter(sql);
    }

    public static int indentify(String userID) {
        int result = 2;
        String sql = "select GROUPID from usergroup where USERID = '{userID}'";
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

    public static void loadComputer(Room room) {
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
    }

    public static User buildFromDB(String userID) {
        String sql = "select user.USERID,USERNAME,PASSWORD,USERBALANCE,GROUPID from user,usergroup where user.USERID=usergroup.USERID and user.USERID = '{userID}'";
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

    public static void main(String[] args) {
        String sql = "select user.USERID,USERNAME,PASSWORD,USERBALANCE,GROUPID from user,usergroup where user.USERID=usergroup.USERID and user.USERID = '{userID}'";
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