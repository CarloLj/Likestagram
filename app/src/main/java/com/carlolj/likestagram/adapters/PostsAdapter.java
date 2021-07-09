package com.carlolj.likestagram.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.carlolj.likestagram.DetailsActivity;
import com.carlolj.likestagram.fragments.ProfileFragment;
import com.carlolj.likestagram.models.Post;
import com.carlolj.likestagram.R;
import com.parse.ParseFile;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.Date;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PostsAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvUsername;
        private TextView tvDescription;
        private TextView tvLikes;
        private TextView tvCreatedAt;
        private ImageView ivPostImage;
        private ImageView ivProfileImage;
        private ImageView ivLike;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            ivLike = itemView.findViewById(R.id.ivLike);
            tvCreatedAt = itemView.findViewById(R.id.tvTime);

            itemView.setOnClickListener(this);

            ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) context;
                    Fragment fragment;
                    fragment = new ProfileFragment(posts.get(getAdapterPosition()).getUser());
                    ((AppCompatActivity) context).getSupportFragmentManager();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).commit();
                    Toast.makeText(itemView.getContext(), "Opening selected profile", Toast.LENGTH_SHORT).show();
                }
            });

            ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Clicked on like button", Toast.LENGTH_SHORT).show();
                    ivLike.setImageResource(R.drawable.ufi_heart_active);
                }
            });
        }

        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            tvCreatedAt.setText(calculateTimeAgo(post.getCreatedAt()));
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

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent ii = new Intent(itemView.getContext(), DetailsActivity.class);
            ii.putExtra("Post", Parcels.wrap(posts.get(position)));
            context.startActivity(ii);
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        list.addAll(list);
        notifyDataSetChanged();
    }

    public static String calculateTimeAgo(Date createdAt) {

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            createdAt.getTime();
            long time = createdAt.getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + "m ago";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + "h ago";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + "d ago";
            }
        } catch (Exception e) {
            Log.i("Error:", "getRelativeTimeAgo failed", e);
            e.printStackTrace();
        }

        return "";
    }
}
