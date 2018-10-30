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
    protected void onPostExecute(JSONObject[] jsonObjects) {
        super.onPostExecute(jsonObjects);
    }
}
