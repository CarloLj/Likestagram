package com.carlolj.likestagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.carlolj.likestagram.databinding.ActivityLoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    Button btnSign;
    EditText etUser, etPass;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //There is someone signed already
        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        btnSign = binding.btnSign;
        etUser = binding.etUser;
        etPass = binding.etPass;
        layout = binding.layout;
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(6000);
        animationDrawable.start();
    }


    public void onLogin(View view) {
        loginUser(etUser.getText().toString(),etPass.getText().toString());
    }

    public void onLogUp(View view) {
        logUpUser(etUser.getText().toString(),etPass.getText().toString());
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to login user " + username);
        loginInBackground(username,password);
    }

    private void loginInBackground(String username, String password){
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                //If the parse exception is null, means that executed correctly
                if (e!=null) {
                    Log.e(TAG, "Issue with login", e);
                    return;
                }
                Toast.makeText(LoginActivity.this, "Welcome back! " + username, Toast.LENGTH_SHORT).show();
                goMainActivity();
            }
        });
    }

    private void logUpUser(String username, String password) {
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    loginInBackground(username,password);
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }

    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}