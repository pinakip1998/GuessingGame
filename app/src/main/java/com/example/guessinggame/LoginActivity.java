package com.example.guessinggame;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {

    private EditText name_et;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getBooleanExtra("exit", false)) {
            finish();
        }


        name_et = findViewById(R.id.name_main_et);

        context = this;

    }

    public void loginAction(View view) {
//
//        Intent intent = new Intent(this,GameOver.class);
//        startActivity(intent);

        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<Player> playerAccnt = realm.where(Player.class)
                    .equalTo("name", name_et.getText().toString())
                    .findAll();
            if (playerAccnt.size() == 1) {
                Intent intent = new Intent(context, CategoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name_et.getText().toString());
                intent.putExtra("cred", bundle);
                startActivity(intent);
            } else {
                setContentView(R.layout.activity_main);
                Toast.makeText(context, "Username doesn't match.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        realm.close();

    }

    public void regAction(View view) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        try {
            RealmResults<Player> id = realm.where(Player.class).equalTo("name", name_et.getText().toString()).findAll();
            if (id.size() != 0) {
                throw new Exception("USERNAME: " + name_et.getText().toString() + " already exists.");

            }

            Player player = realm.createObject(Player.class, System.currentTimeMillis() / 1000);
            player.setName(name_et.getText().toString());

            realm.commitTransaction();

            Intent intent = new Intent(context, CategoryActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name", name_et.getText().toString());
            intent.putExtra("cred", bundle);
            startActivity(intent);

        } catch (Exception e) {
            realm.cancelTransaction();
            Toast.makeText(context, "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        realm.close();

    }
}
