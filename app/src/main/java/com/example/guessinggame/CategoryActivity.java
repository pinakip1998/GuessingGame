package com.example.guessinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CategoryActivity extends AppCompatActivity {
    public static final String EXTRA_STRING="com.example.guessinggame.EXTRA_STRING";
    public String animal="Animal";
    public String flower="Flower";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

    }

    public void startGame(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        if(view.getId()==R.id.animal_category)
            intent.putExtra(EXTRA_STRING,animal);
        else
            intent.putExtra(EXTRA_STRING,flower);
        startActivity(intent);
        finish();
    }
}
