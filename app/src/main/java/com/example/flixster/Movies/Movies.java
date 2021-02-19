package com.example.flixster.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movies {

    String backDropPath;
    String posterPath;
    String title;
    String overview;

    public Movies(JSONObject jsonObject) throws JSONException {
        backDropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
    }
    public static List<Movies> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movies> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movies(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }
    public String getBackDropPath(){
        return String.format("https://image.tmdb.org/t/p/w342/%s", backDropPath);
    }
    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
