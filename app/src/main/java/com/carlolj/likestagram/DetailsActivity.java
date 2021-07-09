package com.carlolj.likestagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.carlolj.likestagram.adapters.PostsAdapter;
import com.carlolj.likestagram.databinding.ActivityDetailsBinding;
import com.carlolj.likestagram.models.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

    private TextView tvUsername;
    private TextView tvDescription;
    private TextView tvLikes;
    private TextView tvTime;
    private ImageView ivPostImage;
    private ImageView ivProfileImage;

    String username = "shdashd";
    String description;
    String nLikes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDetailsBinding binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra("Post"));

        tvLikes = binding.tvLikes;
        tvDescription = binding.tvDescription;
        tvUsername = binding.tvUsername;
        ivPostImage = binding.ivPostImage;
        ivProfileImage = binding.ivProfileImage;
        tvTime = binding.tvTime;

        tvDescription.setText(post.getDescription());
        tvUsername.setText(post.getUser().getUsername());
        tvTime.setText(PostsAdapter.calculateTimeAgo(post.getCreatedAt()));
        ParseFile image = post.getImage();
        ParseFile userProfilePicture = post.getUser().getParseFile("profilePicture");
        int likes = post.getLikes();
        if(likes!=0){
            tvLikes.setText(String.valueOf(likes));
        }else{
            tvLikes.setText("No");
        }
        if(image != null) {
            Glide.with(ivPostImage.getContext())
                    .load(image.getUrl())
                    .into(ivPostImage);
        }
        if(userProfilePicture != null) {
            Glide.with(ivProfileImage.getContext())
                    .load(userProfilePicture.getUrl())
                    .transform(new CircleCrop())
                    .into(ivProfileImage);
        }

    }
}