package org.kukuking.manage;

import org.kukuking.database.MyDatabaseUtil;
import org.kukuking.entity.Computer;
import org.kukuking.entity.User;

public class UserUtil {
    //上机
    public static void startComputer(User user, Computer computer){
        String userID = user.getUserID();
        String computerID = computer.getComputerID();
        String sql = "insert into userecord(USERID,COMPUTERID,COST) values ('{userID}','{computerID}','-1')";
        sql = sql.replace("{userID}",userID);
        sql = sql.replace("{computerID}",computerID);
        MyDatabaseUtil.databaseInserter(sql);
    }
    //关机
    public static void endComputer(User user, Computer computer){
        String userID = user.getUserID();
        String computerID = computer.getComputerID();
        String sql = "update userecord set COST = -1 where USERID='{userID}' and  COMPUTERID = '{computerID}' and COST = -1;";
        sql = sql.replace("{userID}",userID);
        sql = sql.replace("{computerID}",computerID);
        MyDatabaseUtil.databaseInserter(sql);

    }
}
