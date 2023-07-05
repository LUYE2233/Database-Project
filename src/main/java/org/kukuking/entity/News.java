package org.kukuking.entity;

import org.kukuking.database.MyDatabaseUtil;
import org.kukuking.database.MyDruid;

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

    public static void saveNewsToDB(News news){
        String sql = "insert into news(news_name, news_text) VALUES ('{NEWS_NAME}','{NEWS_TEXT}')";
        sql = sql.replace("{NEWS_NAME}", news.getNewsName());
        sql = sql.replace("{NEWS_TEXT}", news.getNewsText());
        MyDatabaseUtil.databaseInserter(sql);
    }

    public static List<News> buildNewsFromDB(){
        List<News> newsList = new ArrayList<>();
        String sql = "select NEWS_ID,NEWS_NAME,NEWS_TEXT from news";
        try {
            Connection connection = MyDruid.getDataSource().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            News temp;
            while (resultSet.next()){
                int id = resultSet.getInt("1");
                String name = resultSet.getString("2");
                String text = resultSet.getString("3");
                temp = new News(id,name,text);
                newsList.add(temp);
            }
        }catch (Exception e){
            MyDruid.getLogger().info(e.toString());
        }
        return newsList;
    }
}
