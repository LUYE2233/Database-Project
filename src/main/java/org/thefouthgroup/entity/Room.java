package org.thefouthgroup.entity;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomID;
    private String roomName;
    private List<Computer> computerList = new ArrayList<>();

    public Room(String roomID, String roomName) {
        this.roomID = roomID;
        this.roomName = roomName;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<Computer> getComputerList() {
        return computerList;
    }

    public void setComputerList(List<Computer> computerList) {
        this.computerList = computerList;
    }
}
