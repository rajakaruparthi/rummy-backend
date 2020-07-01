package com.local.rummy.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("Room")
public class Room {

    @Id
    private String id;

    private String roomname;

    private String password;

    private List<Players> playersList;

    public List<Players> getPlayersList() {
        return playersList;
    }

    public Room(String roomname, List<Players> playersList) {
        this.roomname = roomname;
        this.playersList = playersList;
    }

    public Room() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPlayersList(List<Players> playersList) {
        this.playersList = playersList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }
}
