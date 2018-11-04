package com.silvershadow.myapplication.Converters;

import android.net.Uri;

import com.silvershadow.myapplication.Entities.Movie;
import com.silvershadow.myapplication.Entities.Review;
import com.silvershadow.myapplication.Entities.Trailer;
import com.silvershadow.myapplication.SupportContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JSONToMovies {

    public static List<Movie>getMoviesFrom(JSONObject from){
        JSONArray moviesArray;
        List<Movie> resultList = new ArrayList<>();
        try {
            moviesArray = from.getJSONArray("results");

            for(int i = 0; i <moviesArray.length();i++){
                JSONObject currentMovieJSON = moviesArray.getJSONObject(i);
                resultList.add(getMovieFrom(currentMovieJSON));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultList;

    }
    public static Movie getMovieFrom(JSONObject from) throws JSONException {
        return (new Movie(from.getInt("id"), from.getString("title"), from.getString("backdrop_path"),
                from.getString("poster_path"), from.getString("overview"), from.getString("vote_average"),
                from.getString("vote_count"), from.getString("release_date")));
    }

    public static List<Trailer> getTrailersFrom(JSONObject from){
        JSONArray trailersJSONArray;
        List<Trailer> result = new ArrayList<>();
        try {
            trailersJSONArray = from.getJSONArray("results");
            for(int i = 0; i < trailersJSONArray.length(); i++){
                String name = trailersJSONArray.getJSONObject(i).getString("name");
                Uri uri = SupportContract.getTrailerUri(trailersJSONArray.getJSONObject(i).getString("key"));

                result.add(new Trailer(name, uri));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Review> getReviwsFrom(JSONObject from){
        JSONArray trailersJSONArray;
        List<Review> result = new ArrayList<>();
        try {
            trailersJSONArray = from.getJSONArray("results");
            for(int i = 0; i < trailersJSONArray.length(); i++){
                String author = trailersJSONArray.getJSONObject(i).getString("author");
                String content = trailersJSONArray.getJSONObject(i).getString("content");

                result.add(new Review(author, content));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

}
