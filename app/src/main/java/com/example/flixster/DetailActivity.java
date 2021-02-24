package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.Movies.Movies;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyBqZjnjQFVJ7cmNWS5dRuhICjPWBlZ0sQI";
    public static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    TextView tvOverView;
    TextView tvTitle;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvOverView = findViewById(R.id.tvOverView);
        tvTitle = findViewById(R.id.tvTitle);
        ratingBar = findViewById(R.id.ratingBar);
        youTubePlayerView = findViewById(R.id.player);

        Movies movies = Parcels.unwrap(getIntent().getParcelableExtra("movies"));
        String title = getIntent().getStringExtra("title");
        tvTitle.setText(movies.getTitle());
        tvOverView.setText(movies.getOverview());
        ratingBar.setRating((float) movies.getRating());
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, movies.getMovieid()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() ==0){
                        return;
                    }
                    String youTubeKey = results.getJSONObject(0).getString("key");
                    Log.d("DetailActivity", youTubeKey);
                    initializeYouTube(youTubeKey);
                } catch (JSONException e) {
                    Log.e("DetailActivity", "Failed to parse Json");
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

            }
        });

    }

    private void initializeYouTube(final String youTubeKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("DetailActivity","onInitializationSuccess");
                youTubePlayer.cueVideo(youTubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("DetailActivity","onInitializationFailure");
            }
        });
    }
}