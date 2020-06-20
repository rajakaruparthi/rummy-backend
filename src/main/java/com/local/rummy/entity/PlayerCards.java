package com.local.rummy.entity;

public class PlayerCards {

    private String name;
    private String[] cards;

    public PlayerCards() {
    }

    public PlayerCards(String name, String[] cards) {
        this.name = name;
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getCards() {
        return cards;
    }

    public void setCards(String[] cards) {
        this.cards = cards;
    }
}
