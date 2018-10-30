package com.silvershadow.myapplication.DataLoading;

import android.os.AsyncTask;

import com.silvershadow.myapplication.Entities.Review;
import com.silvershadow.myapplication.Entities.Trailer;
import com.silvershadow.myapplication.MovieDetailsActivity;
import com.silvershadow.myapplication.SupportContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class FetchMovieReviewsAndTrailers extends AsyncTask<URL, Void, JSONObject[]> {

    @Override
    protected JSONObject[] doInBackground(URL... urls) {

        JSONObject[] result = new JSONObject[urls.length];

        for(int i = 0; i < urls.length; i++){
            try {
                HttpURLConnection connection = (HttpURLConnection) urls[i].openConnection();
                connection.connect();
                InputStream stream = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                try {
                    result[i] = new JSONObject(reader.readLine());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                connection.disconnect();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return result;
    }

    @Override
    protected void onPostExecute(JSONObject[] json) {
        super.onPostExecute(json);
        try {
            List<Trailer> trailers = convertJSONToTrailers(json[0].getJSONArray("results"));
            List<Review> reviews = convertJSONToReviews(json[1].getJSONArray("results"));
            MovieDetailsActivity.sReviews = reviews;
            MovieDetailsActivity.sTrailers = trailers;

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    private List<Trailer> convertJSONToTrailers(JSONArray arr) throws JSONException {
        List<Trailer> result = new ArrayList<>();
        for(int i = 0; i < arr.length();i++ )
            result.add(new Trailer(arr.getJSONObject(i).getString ("name"), SupportContract.getTrailerURL(arr.getJSONObject(i).getString("key"))));
        return result;
    }

    private List<Review> convertJSONToReviews (JSONArray arr) throws JSONException {
        List<Review> result = new ArrayList<>();
        for(int i = 0; i < arr.length();i++ )
            result.add(new Review(arr.getJSONObject(i).getString("author"), arr.getJSONObject(i).getString("content")));

        return result;
    }



}
