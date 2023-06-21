package org.thefouthgroup.entity;

import java.util.List;

public class UseRecord {
    private String startTime;
    private String endTime;
    private String userID;
    private String computerID;
    private double cost;

    public UseRecord(String startTime, String endTime, String userID, String computerID, double cost) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.userID = userID;
        this.computerID = computerID;
        this.cost = cost;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getComputerID() {
        return computerID;
    }

    public void setComputerID(String computerID) {
        this.computerID = computerID;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public static void showRecord(List<UseRecord> list) {
        for (UseRecord u : list) {
            System.out.println("Start time=" + u.startTime);
            System.out.println("End time  =" + u.endTime);
            System.out.println("UserID    =" + u.userID);
            System.out.println("ComputerID=" + u.computerID);
            System.out.println("Cost      =" + u.cost);
            System.out.println("<------------------------------->");
        }
    }
}
