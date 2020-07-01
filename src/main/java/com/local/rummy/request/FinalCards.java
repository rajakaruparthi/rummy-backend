package com.local.rummy.request;

import java.util.List;

public class FinalCards {

    public List<Object> cards;
    public boolean foldedFlag;
    public String playerName;

    public List<Object> getCards() {
        return cards;
    }

    public void setCards(List<Object> cards) {
        this.cards = cards;
    }

    public boolean isFoldedFlag() {
        return foldedFlag;
    }

    public void setFoldedFlag(boolean foldedFlag) {
        this.foldedFlag = foldedFlag;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
