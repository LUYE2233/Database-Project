package org.thefouthgroup.manage;

import org.thefouthgroup.database.MyDatabaseUtil;
import org.thefouthgroup.entity.Computer;
import org.thefouthgroup.entity.User;

public class UserUtil {
    public static void startComputer(User user, Computer computer){
        String userID = user.getUserID();
        String computerID = computer.getComputerID();
        String sql = "insert into userecord(USERID,COMPUTERID,COST) values ('{userID}','{computerID}','-1')";
        sql = sql.replace("{userID}",userID);
        sql = sql.replace("{computerID}",computerID);
        MyDatabaseUtil.databaseInserter(sql);
    }

    public static void endComputer(User user, Computer computer){
        String userID = user.getUserID();
        String computerID = computer.getComputerID();
        String sql = "update userecord set COST = -1 where USERID='{userID}' and  COMPUTERID = '{computerID}' and COST = -1;";
        sql = sql.replace("{userID}",userID);
        sql = sql.replace("{computerID}",computerID);
        MyDatabaseUtil.databaseInserter(sql);
    }
}
