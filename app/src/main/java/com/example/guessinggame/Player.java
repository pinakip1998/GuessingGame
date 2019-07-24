package com.example.guessinggame;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Player extends RealmObject {
    @PrimaryKey
    private long id;
    private String name;
    private int recentScore = 0;
    private int max_score = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRecentScore() {
        return recentScore;
    }

    public void setRecentScore(int recentScore) {
        this.recentScore = recentScore;
    }

    public int getMax_score() {
        return max_score;
    }

    public void setMax_score(int max_score) {
        this.max_score = max_score;
    }

}
