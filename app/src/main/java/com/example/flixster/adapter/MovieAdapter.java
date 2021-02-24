package com.example.flixster.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.Movies.Movies;
import com.example.flixster.R;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    Context context;
    List<Movies> movie;

    public MovieAdapter(Context context, List<Movies> movies) {
        this.context = context;
        this.movie = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movies movies = this.movie.get(position);
        holder.bind(movies);
    }

    @Override
    public int getItemCount() {
        return movie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Movies movies) {
            tvTitle.setText(movies.getTitle());
            tvOverview.setText(movies.getOverview());
            String imageURL;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                imageURL = movies.getPosterPath();
            }else{
                imageURL = movies.getBackDropPath();
            }
            Glide.with(context).load(imageURL).into(ivPoster);
            container.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movies", Parcels.wrap(movies));
                    context.startActivity(i);
                }
            });
        }
    }
}
