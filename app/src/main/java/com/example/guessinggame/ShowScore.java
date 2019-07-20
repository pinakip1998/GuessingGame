package com.example.guessinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowScore extends AppCompatActivity {

    TextView score_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_score);
        score_tv=findViewById(R.id.score_t);
        Intent intent = getIntent();
        Integer sc = intent.getIntExtra(MainActivity.EXTRA_NUMBER,0);
        score_tv.setText(sc.toString());
    }
}
