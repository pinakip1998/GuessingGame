package com.example.guessinggame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;


public class CategoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout dl;
    NavigationView nv;
    ActionBarDrawerToggle toggle;
    TextView user;
    public static final String EXTRA_STRING="com.example.guessinggame.EXTRA_STRING";
    public String animal="Animal";
    public String flower="Flower";
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        dl = findViewById(R.id.drawerLayout);
        nv = findViewById(R.id.nview);
        toggle = new ActionBarDrawerToggle(this, dl, R.string.open_menu, R.string.close_menu);
        nv.setNavigationItemSelectedListener(this);
        View navView=nv.getHeaderView(0);
        user=navView.findViewById(R.id.user);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getBundleExtra("cred");
        userName = b.getString("name");
        user.setText(userName);
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

    public void startGame(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        if(view.getId()==R.id.animal_category) {
            Bundle cred = getIntent().getBundleExtra("cred");
            intent.putExtra(EXTRA_STRING, animal);
            intent.putExtra("cred", cred);
        }
        else{
            Bundle cred = getIntent().getBundleExtra("cred");
            intent.putExtra(EXTRA_STRING,flower);
            intent.putExtra("cred", cred);
        }
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

