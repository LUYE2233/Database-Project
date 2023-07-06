package org.kukuking.entity;

import org.kukuking.database.MyDatabaseUtil;
import org.kukuking.database.MyDruid;
import org.springframework.web.util.HtmlUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Labs {
    private int labID;
    private String labName;
    private String labText;

    public Labs(int labID, String labName, String labText) {
        this.labID = labID;
        this.labName = labName;
        this.labText = labText;
    }

    public int getLabID() {
        return labID;
    }

    public void setLabID(int labID) {
        this.labID = labID;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getLabText() {
        return labText;
    }

    public void setLabText(String labText) {
        this.labText = labText;
    }

    public static void saveLabToDB(Labs lab) {
        lab.setLabName(HtmlUtils.htmlEscape(lab.getLabName()));
        lab.setLabText(HtmlUtils.htmlEscape(lab.getLabText()));
        if (lab.getLabID() == -1) {
            String sql = "insert into labs(lab_name, lab_text) VALUES ('{LAB_NAME}','{LAB_TEXT}')";
            sql = sql.replace("{LAB_NAME}", lab.getLabName());
            sql = sql.replace("{LAB_TEXT}", lab.getLabText());
            MyDatabaseUtil.databaseInserter(sql);
        } else {
            String sql = "update labs set LAB_NAME='{LAB_NAME}',LAB_TEXT='{LAB_TEXT}' where LAB_ID='{LAB_ID}'";
            sql = sql.replace("{LAB_NAME}", lab.getLabName());
            sql = sql.replace("{LAB_TEXT}", lab.getLabText());
            sql = sql.replace("{LAB_ID}", String.valueOf(lab.getLabID()));
            MyDatabaseUtil.databaseInserter(sql);
        }
    }

    public static List<Labs> buildLabsFromDB() {
        List<Labs> labsList = new ArrayList<>();
        String sql = "select LAB_ID,LAB_NAME,LAB_TEXT from labs";
        try (Connection connection = MyDruid.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            Labs temp;
            while (resultSet.next()) {
                int id = resultSet.getInt("LAB_ID");
                String name = HtmlUtils.htmlUnescape(resultSet.getString("LAB_NAME"));
                String text = HtmlUtils.htmlUnescape(resultSet.getString("LAB_TEXT"));
                temp = new Labs(id, name, text);
                labsList.add(temp);
            }
        } catch (Exception e) {
            MyDruid.getLogger().info(e.toString());
        }
        return labsList;
    }

    public static void deleteLabFromDB(int labID) {
        String sql = "delete from labs where LAB_ID = '{LAB_ID}'";
        sql = sql.replace("{LAB_ID}", String.valueOf(labID));
        MyDatabaseUtil.databaseInserter(sql);
    }
}
