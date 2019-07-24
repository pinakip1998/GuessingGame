package com.example.guessinggame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import io.realm.Realm;


public class MainActivity extends AppCompatActivity {

    public FirebaseStorage storage = FirebaseStorage.getInstance();
    public FirebaseDatabase db = FirebaseDatabase.getInstance();
    public String file_url,filename,category;
    public static String[] options=new String[4];
    public int score;
    String userName;
    LinearLayout linearLayout;
    ProgressBar spinner;
    private Integer no= new Integer(1);
    ImageView imageView;
    TextView title_tv;
    Button btn1,btn2,btn3,btn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.photo);
        title_tv = findViewById(R.id.title);
        spinner = findViewById(R.id.progressBar);
        linearLayout = findViewById(R.id.linear_main);
        spinner.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
        score=0;
        Bundle b = getIntent().getBundleExtra("cred");
        userName = b.getString("name");
        Intent intent = getIntent();
        category = intent.getStringExtra(CategoryActivity.EXTRA_STRING);
        title_tv.setText(title_tv.getText()+category);
        //First image being downloaded
        filename=no.toString()+".jpg";
        DatabaseReference dbRef = db.getReference().child(category);
        StorageReference imagesRef = storage.getReference().child(category);
        DatabaseReference ob = dbRef.child(Integer.toString(no));
        StorageReference img = imagesRef.child(filename);
        optionRetrieve(ob);
        img.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("Tuts+", "uri: " + uri.toString());
                //Handle whatever you're going to do with the URL here
               file_url = uri.toString();
                Glide.with(imageView).load(file_url).into(imageView);
                spinner.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });

    }


    public void optionRetrieve(DatabaseReference myRef)
    {
        btn1 = findViewById(R.id.btn_op1);
        btn2 = findViewById(R.id.btn_op2);
        btn3 = findViewById(R.id.btn_op3);
        btn4 = findViewById(R.id.btn_op4);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
               String value = dataSnapshot.getValue().toString();
               Log.i("Value",value);
                options= value.split("\t");
                //Log.i("this",value);
                btn1.setText(options[1]);
                btn2.setText(options[2]);
                btn3.setText(options[3]);
                btn4.setText(options[4]);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("This", "Failed to read value.", error.toException());
            }
        });
    }

    public void onButtonPressed(View view)
    {
        imageView = findViewById(R.id.photo);
       // imageBuffer = findViewById(R.id.image_buffer);
        linearLayout = findViewById(R.id.linear_main);
        spinner = findViewById(R.id.progressBar);
        btn1 = findViewById(R.id.btn_op1);
        btn2 = findViewById(R.id.btn_op2);
        btn3 = findViewById(R.id.btn_op3);
        btn4 = findViewById(R.id.btn_op4);
        int option;
        //After clicking the option is checked with the correct answer
        switch(view.getId())
        {
            case R.id.btn_op1:
                option=1;
                break;
            case R.id.btn_op2:
                option=2;
                break;
            case R.id.btn_op3:
                option=3;
                break;
            case R.id.btn_op4:
                option=4;
                break;
                default: option=0;
                break;
        }
        if(options[0].equals(options[option]))
            addPoint();

        spinner.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
        //First image setting the drawable from the buffer image view
        //imageView.setImageDrawable(imageBuffer.getDrawable());

        //Buffer image view fetching the next image
        DatabaseReference dbRef = db.getReference().child(category);
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child(category);

        if(no==25)
        {
            displayScores();
        }

        if(no<25) {
            no++;

            filename = no.toString() + ".jpg";
            DatabaseReference ob = dbRef.child(Integer.toString(no));
            optionRetrieve(ob);
            StorageReference img = imagesRef.child(filename);
            img.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.e("Tuts+", "uri: " + uri.toString());
                    //Handle whatever you're going to do with the URL here
                    file_url = uri.toString();
                    linearLayout.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.INVISIBLE);
                    Glide.with(imageView).load(file_url).into(imageView);
                }

            });
        }
    }

    private void displayScores() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Player player = realm.where(Player.class).equalTo("name", userName).findFirst();
        player.setRecentScore(score);
        if(player.getRecentScore() > player.getMax_score()) {
            player.setMax_score(player.getRecentScore());
        }

        //Toast.makeText(this, score, Toast.LENGTH_SHORT).show();
        realm.commitTransaction();
        realm.close();

        Intent intent = new Intent(this, ShowScore.class);
        Bundle b = new Bundle();
        b.putInt("score", score);
        b.putString("name", userName);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    private void addPoint() {
        score+=10;
    }
}
