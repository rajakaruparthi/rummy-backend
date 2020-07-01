package com.local.rummy.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("players")
public class Players {

    @Id
    private String name;

    private boolean folded;

    public Players(String name, boolean folded) {
        this.name = name;
        this.folded = folded;
    }

    public Players() {
    }

    public boolean isFolded() {
        return folded;
    }

    public void setFolded(boolean folded) {
        this.folded = folded;
    }

    public Players(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
