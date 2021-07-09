package com.carlolj.likestagram.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.carlolj.likestagram.DetailsActivity;
import com.carlolj.likestagram.R;
import com.carlolj.likestagram.fragments.ProfileFragment;
import com.carlolj.likestagram.models.Post;
import com.parse.ParseFile;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder>{

    private Context context;
    private List<Post> posts;

    public ProfileAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_post, parent, false);
        return new ProfileAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProfileAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivImage;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);

            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent ii = new Intent(itemView.getContext(), DetailsActivity.class);
                    ii.putExtra("Post", Parcels.wrap(posts.get(position)));
                    context.startActivity(ii);
                }
            });
        }

        public void bind(Post post) {
            ParseFile image = post.getImage();
            if(image != null) {
                Glide.with(ivImage.getContext()).load(image.getUrl()).into(ivImage);
            }
        }
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }
}
