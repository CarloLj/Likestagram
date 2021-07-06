package com.carlolj.likestagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.carlolj.likestagram.databinding.ActivityLoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    Button btnSign;
    EditText etUser, etPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //There is someone signed already
        if(ParseUser.getCurrentUser() != null){
            goMainActivity();
        }

        btnSign = binding.btnSign;
        etUser = binding.etUser;
        etPass = binding.etPass;
    }


    public void onLogin(View view) {
        loginUser(etUser.getText().toString(),etPass.getText().toString());
    }

    private void loginUser(String username, String password){
        Log.i(TAG, "Attempting to login user " + username);
        //Log in in the background in other thread
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                //If the parse exception is null, means that executed correctly
                if(e!=null){
                    Log.e(TAG, "Issue with login", e);
                    return;
                }
                Toast.makeText(LoginActivity.this, "Welcome back! " + username, Toast.LENGTH_SHORT).show();
                goMainActivity();
            }
        });
    }

    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}