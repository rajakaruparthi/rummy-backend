package com.local.rummy.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
public class FinalShowCards {

    @Id
    private String handId;

    private String roomId;

    private Date time;

    private List<PlayersAttrs> playersAttrsList;

    public String getHandId() {
        return handId;
    }

    public void setHandId(String handId) {
        this.handId = handId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<PlayersAttrs> getPlayersAttrsList() {
        return playersAttrsList;
    }

    public void setPlayersAttrsList(List<PlayersAttrs> playersAttrsList) {
        this.playersAttrsList = playersAttrsList;
    }
}
