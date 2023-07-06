package org.kukuking.entity;

import org.kukuking.database.MyDatabaseUtil;
import org.kukuking.database.MyDruid;
import org.springframework.web.util.HtmlUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Teachers {
    private int teacherID;
    private String teacherName;
    private String teacherText;

    public Teachers(int teacherID, String teacherName, String teacherText) {
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.teacherText = teacherText;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherText() {
        return teacherText;
    }

    public void setTeacherText(String teacherText) {
        this.teacherText = teacherText;
    }

    public static void saveTeacherToDB(Teachers news) {
        news.setTeacherName(HtmlUtils.htmlEscape(news.getTeacherName()));
        news.setTeacherText(HtmlUtils.htmlEscape(news.getTeacherText()));
        if (news.getTeacherID() == -1) {
            String sql = "insert into teachers(teacher_name, teacher_text) VALUES ('{TEACHER_NAME}','{TEACHER_TEXT}')";
            sql = sql.replace("{TEACHER_NAME}", news.getTeacherName());
            sql = sql.replace("{TEACHER_TEXT}", news.getTeacherText());
            MyDatabaseUtil.databaseInserter(sql);
        } else {
            String sql = "update teachers set TEACHER_NAME='{TEACHER_NAME}',TEACHER_TEXT='{TEACHER_TEXT}' where TEACHER_ID='{TEACHER_ID}'";
            sql = sql.replace("{TEACHER_NAME}", news.getTeacherName());
            sql = sql.replace("{TEACHER_TEXT}", news.getTeacherText());
            sql = sql.replace("{TEACHER_ID}", String.valueOf(news.getTeacherID()));
            MyDatabaseUtil.databaseInserter(sql);
        }
    }

    public static List<Teachers> buildTeachersFromDB() {
        List<Teachers> teachersList = new ArrayList<>();
        String sql = "select TEACHER_ID,TEACHER_NAME,TEACHER_TEXT from teachers";
        try (Connection connection = MyDruid.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            Teachers temp;
            while (resultSet.next()) {
                int id = resultSet.getInt("TEACHER_ID");
                String name = HtmlUtils.htmlUnescape(resultSet.getString("TEACHER_NAME"));
                String text = HtmlUtils.htmlUnescape(resultSet.getString("TEACHER_TEXT"));
                temp = new Teachers(id, name, text);
                teachersList.add(temp);
            }
        } catch (Exception e) {
            MyDruid.getLogger().info(e.toString());
        }
        return teachersList;
    }

    public static void deleteTeacherFromDB(int teacherID) {
        String sql = "delete from teachers where TEACHER_ID = '{TEACHER_ID}'";
        sql = sql.replace("{TEACHER_ID}", String.valueOf(teacherID));
        MyDatabaseUtil.databaseInserter(sql);
    }
}
