package com.carlolj.likestagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLogout(View view) {
        onLogoutButton();
    }

    public void onLogoutButton() {
        // forget who's logged in
        ParseUser.logOut();

        // navigate backwards to Login screen
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // this makes sure the Back button won't work
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // same as above
        startActivity(i);
    }


}