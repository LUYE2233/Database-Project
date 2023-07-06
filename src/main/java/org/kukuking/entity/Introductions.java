package org.kukuking.entity;

import org.kukuking.database.MyDatabaseUtil;
import org.kukuking.database.MyDruid;
import org.springframework.web.util.HtmlUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Introductions {
    private int introductionID;
    private String introductionName;
    private String introductionText;

    public Introductions(int introductionID, String introductionName, String introductionText) {
        this.introductionID = introductionID;
        this.introductionName = introductionName;
        this.introductionText = introductionText;
    }

    public int getIntroductionID() {
        return introductionID;
    }

    public void setIntroductionID(int introductionID) {
        this.introductionID = introductionID;
    }

    public String getIntroductionName() {
        return introductionName;
    }

    public void setIntroductionName(String introductionName) {
        this.introductionName = introductionName;
    }

    public String getIntroductionText() {
        return introductionText;
    }

    public void setIntroductionText(String introductionText) {
        this.introductionText = introductionText;
    }

    public static void saveIntroductionToDB(Introductions introductions) {
        introductions.setIntroductionName(HtmlUtils.htmlEscape(introductions.getIntroductionName()));
        introductions.setIntroductionText(HtmlUtils.htmlEscape(introductions.getIntroductionText()));
        if (introductions.getIntroductionID() == -1) {
            String sql = "insert into introductions(introduction_name, introduction_text) VALUES ('{INTRODUCTION_NAME}','{INTRODUCTION_TEXT}')";
            sql = sql.replace("{INTRODUCTION_NAME}", introductions.getIntroductionName());
            sql = sql.replace("{INTRODUCTION_TEXT}", introductions.getIntroductionText());
            MyDatabaseUtil.databaseInserter(sql);
        } else {
            String sql = "update introductions set INTRODUCTION_NAME='{INTRODUCTION_NAME}',INTRODUCTION_TEXT='{INTRODUCTION_TEXT}' where INTRODUCTION_ID='{INTRODUCTION_ID}'";
            sql = sql.replace("{INTRODUCTION_NAME}", introductions.getIntroductionName());
            sql = sql.replace("{INTRODUCTION_TEXT}", introductions.getIntroductionText());
            sql = sql.replace("{INTRODUCTION_ID}", String.valueOf(introductions.getIntroductionID()));
            MyDatabaseUtil.databaseInserter(sql);
        }
    }

    public static List<Introductions> buildIntroductionsFromDB() {
        List<Introductions> introductionsList = new ArrayList<>();
        String sql = "select INTRODUCTION_ID,INTRODUCTION_NAME,INTRODUCTION_TEXT  from introductions";
        try (Connection connection = MyDruid.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            Introductions temp;
            while (resultSet.next()) {
                int id = resultSet.getInt("INTRODUCTION_ID");
                String name = HtmlUtils.htmlUnescape(resultSet.getString("INTRODUCTION_NAME"));
                String text = HtmlUtils.htmlUnescape(resultSet.getString("INTRODUCTION_TEXT"));
                temp = new Introductions(id, name, text);
                introductionsList.add(temp);
            }
        } catch (Exception e) {
            MyDruid.getLogger().info(e.toString());
        }
        return introductionsList;
    }

    public static void deleteIntroductionFromDB(int introductionID) {
        String sql = "delete from introductions where INTRODUCTION_ID = '{INTRODUCTION_ID}'";
        sql = sql.replace("{INTRODUCTION_ID}", String.valueOf(introductionID));
        MyDatabaseUtil.databaseInserter(sql);
    }
}
