package com.example.guessinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.realm.Realm;

public class ShowScore extends AppCompatActivity {

    TextView score_tv;
    private TextView highScore;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_score);
        Bundle b = getIntent().getExtras();
        int score = b.getInt("score");
        username = b.getString("name");
        score_tv=findViewById(R.id.score_t);
        highScore = findViewById(R.id.high_score_tv);

        Player player = Realm.getDefaultInstance().where(Player.class).equalTo("name", username).findFirst();
        int maxScore = player.getMax_score();
        score_tv.setText(String.valueOf(score));
        highScore.setText(String.valueOf(maxScore));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,CategoryActivity.class);
        startActivity(intent);
        finish();
    }

    public void exitGame(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("exit", true);
        startActivity(intent);
    }

    public void playAgain(View view){
        Intent intent = new Intent(this, CategoryActivity.class);
        Bundle b = new Bundle();
        b.putString("name", username);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
    public void viewLeaderBoard(View view){
        Intent intent = new Intent(this,LeaderBoard.class);
        startActivity(intent);
    }
}
