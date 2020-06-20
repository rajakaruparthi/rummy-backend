package com.local.rummy.response;

import com.local.rummy.entity.PlayerCards;

import java.util.List;

public class DiscardCardsResponse {

    private List<PlayerCards>  playersCards;
    private List<String> deck;
    private String openCard;
    private String openJoker;

    public List<PlayerCards> getPlayersCards() {
        return playersCards;
    }

    public void setPlayersCards(List<PlayerCards> playersCards) {
        this.playersCards = playersCards;
    }

    public List<String> getDeck() {
        return deck;
    }

    public void setDeck(List<String> deck) {
        this.deck = deck;
    }

    public String getOpenCard() {
        return openCard;
    }

    public void setOpenCard(String openCard) {
        this.openCard = openCard;
    }

    public String getOpenJoker() {
        return openJoker;
    }

    public void setOpenJoker(String openJoker) {
        this.openJoker = openJoker;
    }
}
