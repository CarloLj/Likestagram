package com.carlolj.likestagram.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carlolj.likestagram.R;
import com.carlolj.likestagram.models.Post;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class PostHelper {
    public static final String TAG = "PostHelper";

    public static void LikePost(Post post, ImageView btnFavorite, TextView tvLikesNumber, Context context) {
        ParseUser user = ParseUser.getCurrentUser();
        if (!post.isLiked()) {
            post.addLike(user.getObjectId());
            post.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Log.e(TAG, "Error while saving, e");
                        Toast.makeText(context, "Error while saving!", Toast.LENGTH_SHORT).show();
                    }
                    Log.i(TAG, "Post save was successful!");
                    btnFavorite.setBackgroundResource(R.drawable.ufi_heart_active);
                    tvLikesNumber.setText(String.valueOf(post.getLikesNumber()));
                }
            });
        } else {
            post.removeLike(user.getObjectId());
            post.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Log.e(TAG, "Error while saving, e");
                        Toast.makeText(context, "Error while saving!", Toast.LENGTH_SHORT).show();
                    }
                    Log.i(TAG, "Post save was successful!");
                    btnFavorite.setBackgroundResource(R.drawable.ufi_heart);
                    tvLikesNumber.setText(String.valueOf(post.getLikesNumber()));
                }
            });
        }
    }

    public static void getLikeState(Post post, ImageView btnFavorite) {
        if (post.isLiked())
            btnFavorite.setBackgroundResource(R.drawable.ufi_heart_active);
        else
            btnFavorite.setBackgroundResource(R.drawable.ufi_heart);
    }

    public static void getLikesCount(Post post, TextView tvLikesNumber) {
        tvLikesNumber.setText(String.valueOf(post.getLikesNumber()));
    }
}