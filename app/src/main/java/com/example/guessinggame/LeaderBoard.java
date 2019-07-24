package com.example.guessinggame;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class LeaderBoard extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        recyclerView = findViewById(R.id.leader_board_recycler);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Player> playerResult = realm.where(Player.class).findAll();
        playerResult = playerResult.sort("max_score", Sort.DESCENDING);

        LeaderBoardAdapter leaderBoardAdapter = new LeaderBoardAdapter(playerResult, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(leaderBoardAdapter);
    }
}

