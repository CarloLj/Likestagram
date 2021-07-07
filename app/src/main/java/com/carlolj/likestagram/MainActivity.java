package com.carlolj.likestagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.carlolj.likestagram.fragments.ComposeFragment;
import com.carlolj.likestagram.fragments.HomeFragment;
import com.carlolj.likestagram.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    final FragmentManager fragmentManager = getSupportFragmentManager();

    final Fragment home = new HomeFragment();
    final Fragment compose = new ComposeFragment();
    final Fragment profile = new ProfileFragment();

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = home;
                        Log.d(TAG, "HOME");
                        break;
                    case R.id.action_compose:
                        fragment = compose;
                        Log.d(TAG, "COMPOSE");
                        break;
                    default:
                        fragment = profile;
                        Log.d(TAG, "PROFILE");
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigation.setSelectedItemId(R.id.action_home);
        queryPosts();
    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e!=null){
                    Log.e(TAG, "Issue with getting posts", e);
                }
                for(Post post : posts){
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
            }
        });
    }
}