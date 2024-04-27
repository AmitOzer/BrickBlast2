package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    Button btnReg;
    TextInputEditText etEmail, etPassword, etNickname;
    TextView tvloginNow;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        SharedPreferences preferences = getSharedPreferences(Login.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember", "false");
        editor.apply();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnReg = findViewById(R.id.btnRegister);
        tvloginNow = findViewById(R.id.loginNow);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        etNickname = findViewById(R.id.nickname);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        tvloginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password, nickname;
                progressBar.setVisibility(View.VISIBLE);
                email = String.valueOf(etEmail.getText());
                password = String.valueOf(etPassword.getText());
                nickname = String.valueOf(etNickname.getText());
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Register.this, "Enter email", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(nickname)) {
                    Toast.makeText(Register.this, "Enter nickname", Toast.LENGTH_SHORT).show();
                }
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(nickname)) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("Register", "createUserWithEmail:success");
                                        Toast.makeText(Register.this, " created user successfully ", Toast.LENGTH_SHORT).show();
                                        //set the user nickname
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(nickname).build();
                                        user.updateProfile(profileUpdates);

                                        User userInfo = new User(nickname,email,password);
                                        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                                        databaseReference.child(user.getUid()).setValue(userInfo);

                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("Register", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(Register.this, "Registration failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}