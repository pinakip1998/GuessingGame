package com.example.guessinggame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import io.realm.Realm;

public class ShowScore extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView score_tv;
    private TextView highScore;
    String userName;
    DrawerLayout dl;
    NavigationView nv;
    ActionBarDrawerToggle toggle;
    TextView user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_score);
        Bundle b = getIntent().getExtras();
        int score = b.getInt("score");
        userName = b.getString("name");
        score_tv=findViewById(R.id.score_t);
        highScore = findViewById(R.id.high_score_tv);
        dl = findViewById(R.id.drawerLayout);
        nv = findViewById(R.id.nview);
        toggle = new ActionBarDrawerToggle(this, dl, R.string.open_menu, R.string.close_menu);
        nv.setNavigationItemSelectedListener(this);
        View navView=nv.getHeaderView(0);
        user=navView.findViewById(R.id.user);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user.setText(userName);

        Player player = Realm.getDefaultInstance().where(Player.class).equalTo("name", userName).findFirst();
        int maxScore = player.getMax_score();
        score_tv.setText(String.valueOf(score));
        highScore.setText(String.valueOf(maxScore));
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CategoryActivity.class);
        Bundle b = new Bundle();
        b.putString("name", userName);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
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
        b.putString("name", userName);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
    public void viewLeaderBoard(View view){
        Intent intent = new Intent(this,LeaderBoard.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.score:
                Intent intent = new Intent(this,LeaderBoard.class);
                startActivity(intent);
                break;
            case R.id.exit:
                Intent intent3 = new Intent(this, LoginActivity.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent3.putExtra("exit", true);
                startActivity(intent3);
                break;
            case R.id.logout:
                Intent intent2 = new Intent(this,LoginActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                finish();
                break;
        }

        return true;
    }
}