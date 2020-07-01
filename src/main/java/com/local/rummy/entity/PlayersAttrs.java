package com.local.rummy.entity;

import java.util.List;

public class PlayersAttrs {

    private List<Object> cards;
    private String name;
    private boolean isFolded;

    public PlayersAttrs( List<Object> cards, String name, boolean isFolded) {
        this.name = name;
        this.cards = cards;
        this.isFolded = isFolded;
    }

    public List<Object> getCards() {
        return cards;
    }

    public void setCards(List<Object> cards) {
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isFolded() {
        return isFolded;
    }

    public void setFolded(boolean folded) {
        isFolded = folded;
    }
}
