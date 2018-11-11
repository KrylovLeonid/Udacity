package com.silvershadow.myapplication;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class SupportContract {
    public static String API_TOKEN = BuildConfig.API_KEY;
    public static final String MOVIE_SORT_TYPE_KEY = "sort type";
    public static final  String MOVIE_KEY = "movie";


    public  static String getImgURLstr(String size ){
        return new Uri.Builder().scheme("https").path("image.tmdb.org").appendPath("t").appendPath("p").appendPath(size).toString();
    }

    public static URL getMoviesURL(String sortType){
        URL result = null;
        try {
            result = new URL( new Uri.Builder().scheme("https").path("api.tmdb.org").appendPath("3").appendPath("movie").appendPath(sortType).appendQueryParameter("api_key", API_TOKEN)
                    .appendQueryParameter("page","1").build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static URL getMovieTrailersURL(int movieId){
        try {
            return new URL(new Uri.Builder().scheme("https").path("api.tmdb.org").appendPath("3").appendPath("movie").appendPath(Integer.toString(movieId)).appendPath("videos").appendQueryParameter("api_key", API_TOKEN)
                    .appendQueryParameter("page","1").build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static URL getReviewsURL(int movieId) throws MalformedURLException {
        return new URL( new Uri.Builder().scheme("https").path("api.tmdb.org").appendPath("3").appendPath("movie").appendPath(Integer.toString(movieId)).appendPath("reviews").appendQueryParameter("api_key", API_TOKEN)
                .appendQueryParameter("page","1").build().toString());
    }

    public static Uri getTrailerUri (String key){

        return new Uri.Builder().scheme("https").path("www.youtube.com").appendPath("watch").appendQueryParameter("v",key).build();
    }


}
