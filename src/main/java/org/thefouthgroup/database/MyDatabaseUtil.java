package org.thefouthgroup.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class MyDatabaseUtil {
    private static final DataSource dataSource = MyDruid.getDataSource();
    private static final Logger LOGGER = MyDruid.getLogger();

//    下面俩是查询 和 插入/更新的模板，第二个的result不能回传，因为在statement.close()后resultset就是空集了

    private static void databaseInserter(String sql) {
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
}