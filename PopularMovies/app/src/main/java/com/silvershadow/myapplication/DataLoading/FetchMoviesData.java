package com.silvershadow.myapplication.DataLoading;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchMoviesData extends AsyncTask<URL,Void,JSONObject[]> {


    @Override
    protected JSONObject[] doInBackground(URL... urls) {
        JSONObject[] results = new JSONObject[2];
        for(int i = 0; i < urls.length; i++) {
            try {
                HttpURLConnection connection = (HttpURLConnection) urls[0].openConnection();
                connection.connect();
                BufferedInputStream input = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                try {
                    results[i] = new JSONObject(reader.readLine());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return results;

    }

    @Override
    protected void onPostExecute(JSONObject[] result) {
        super.onPostExecute(result);
        MovieDataHolder.setPopularMovies(result[0]);
        MovieDataHolder.setTopRatedMoves(result[1]);
    }
}
