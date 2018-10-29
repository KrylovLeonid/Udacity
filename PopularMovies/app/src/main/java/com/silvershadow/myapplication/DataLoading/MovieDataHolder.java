package com.silvershadow.myapplication.DataLoading;

import com.silvershadow.myapplication.Entities.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class MovieDataHolder {
    private static List<Movie> topRatedMoves = new ArrayList<>();
    private static List<Movie> popularMovies = new ArrayList<>();

    private MovieDataHolder(){

    }



    public static List<Movie> getTopRated(){
         return topRatedMoves;
    }
    public static List<Movie> getPopular(){
        return popularMovies;
    }

    public static void setTopRatedMovies(JSONObject obj) {
        topRatedMoves = JSONtoMoviesList(obj);

    }

    public static void setPopularMovies(JSONObject obj) {
        popularMovies = JSONtoMoviesList(obj);


    }


    private static List<Movie> JSONtoMoviesList(JSONObject moviesJSON){
        JSONArray moviesArray;
        List<Movie> resultList = new ArrayList<>();
        try {
            moviesArray = moviesJSON.getJSONArray("results");

            for(int i = 0; i <moviesArray.length();i++){
                JSONObject currentMovie = moviesArray.getJSONObject(i);
                resultList.add(new Movie(currentMovie.getInt("id"), currentMovie.getString("title"), currentMovie.getString("backdrop_path"),
                        currentMovie.getString("poster_path"), currentMovie.getString("overview"), currentMovie.getString("vote_average"),
                        currentMovie.getString("vote_count"), currentMovie.getString("release_date") ));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultList;
    }


}
