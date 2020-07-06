package com.local.rummy.request;

public class ShuffleCardsRequest {

    private String roomId;

    private int distributeIndex;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getDistributeIndex() {
        return distributeIndex;
    }

    public void setDistributeIndex(int distributeIndex) {
        this.distributeIndex = distributeIndex;
    }
}
