package org.kukuking.entity;

import org.kukuking.database.MyDatabaseUtil;
import org.kukuking.database.MyDruid;
import org.springframework.web.util.HtmlUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class News {
    private int newsID;
    private String newsName;
    private String newsText;

    public News(int newsID, String newsName, String newsText) {
        this.newsID = newsID;
        this.newsName = newsName;
        this.newsText = newsText;
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getNewsText() {
        return newsText;
    }

    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }

    public static void saveNewsToDB(News news) {
        news.setNewsName(HtmlUtils.htmlEscape(news.getNewsName()));
        news.setNewsText(HtmlUtils.htmlEscape(news.getNewsText()));
        if (news.getNewsID() == -1) {
            String sql = "insert into news(news_name, news_text) VALUES ('{NEWS_NAME}','{NEWS_TEXT}')";
            sql = sql.replace("{NEWS_NAME}", news.getNewsName());
            sql = sql.replace("{NEWS_TEXT}", news.getNewsText());
            MyDatabaseUtil.databaseInserter(sql);
        } else {
            String sql = "update news set NEWS_NAME='{NEWS_NAME}',NEWS_TEXT='{NEWS_TEXT}' where NEWS_ID='{NEWS_ID}'";
            sql = sql.replace("{NEWS_NAME}", news.getNewsName());
            sql = sql.replace("{NEWS_TEXT}", news.getNewsText());
            sql = sql.replace("{NEWS_ID}", String.valueOf(news.getNewsID()));
            MyDatabaseUtil.databaseInserter(sql);
        }
    }

    public static List<News> buildNewsFromDB() {
        List<News> newsList = new ArrayList<>();
        String sql = "select NEWS_ID,NEWS_NAME,NEWS_TEXT from news";
        try (Connection connection = MyDruid.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            News temp;
            while (resultSet.next()) {
                int id = resultSet.getInt("NEWS_ID");
                String name = HtmlUtils.htmlUnescape(resultSet.getString("NEWS_NAME"));
                String text = HtmlUtils.htmlUnescape(resultSet.getString("NEWS_TEXT"));
                temp = new News(id, name, text);
                newsList.add(temp);
            }
        } catch (Exception e) {
            MyDruid.getLogger().info(e.toString());
        }
        return newsList;
    }

    public static void deleteNewsFromDB(int newsID) {
        String sql = "delete from news where NEWS_ID = '{NEWS_ID}'";
        sql = sql.replace("{NEWS_ID}", String.valueOf(newsID));
        MyDatabaseUtil.databaseInserter(sql);
    }
}
