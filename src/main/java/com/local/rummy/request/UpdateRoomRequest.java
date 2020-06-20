package com.local.rummy.request;

public class UpdateRoomRequest {

    private String roomId;
    private String username;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String id) {
        this.roomId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
