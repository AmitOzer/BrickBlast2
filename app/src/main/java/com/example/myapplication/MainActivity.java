package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView tvDetails;
    TextView startPlay;
    Button btnLogout;
    FirebaseAuth mAuth;
    FirebaseUser user;
    User userInfo;
    int gamesPlayed = -1;
    int totalScore = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.btnLogout);
        tvDetails = findViewById(R.id.tvDetail);
        startPlay = findViewById(R.id.startPlay);
        user = mAuth.getCurrentUser();

        //read user data
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userDataRef = database.getReference("Users").child(user.getUid());

        userDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userinfo = snapshot.getValue(User.class);
                if (userinfo != null) {
                    MainActivity.this.gamesPlayed = userinfo.getGamesPlayed();
                    MainActivity.this.totalScore = userinfo.getTotalScore();
                    MainActivity.this.userInfo = userinfo;
                    String text = user.getDisplayName();
                    if (gamesPlayed != -1 && totalScore != -1) {
                        text = text + "\n\nScore: " + totalScore + "\n\nGames played: " + gamesPlayed;
                    }
                    tvDetails.setText(text);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            String text = user.getDisplayName();
            tvDetails.setText(text);
        }
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(Login.SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        startPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Levels.class);
                startActivity(intent);
                finish();
            }
        });
    }
}