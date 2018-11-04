package com.silvershadow.myapplication.ViewModel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.silvershadow.myapplication.Converters.JSONToMovies;
import com.silvershadow.myapplication.Entities.Movie;
import com.silvershadow.myapplication.Entities.Review;
import com.silvershadow.myapplication.Entities.Trailer;
import com.silvershadow.myapplication.SupportContract;

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

public class MovieDataModel extends ViewModel {
    private MutableLiveData <List<Movie>> topRatedMovies = new MutableLiveData<>();
    private MutableLiveData <List<Movie>> popularMovies = new MutableLiveData<>();
    private MutableLiveData <List<Movie>> favoriteMovies = new MutableLiveData<>();

    public MovieDataModel() {
        topRatedMovies.setValue(new ArrayList<Movie>(0));
        popularMovies.setValue(new ArrayList<Movie>(0));
        favoriteMovies.setValue(new ArrayList<Movie>(0));
    }


    public LiveData<List<Movie>> getTopRatedMovies() {
        return topRatedMovies;
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return popularMovies;
    }

    public LiveData<List<Movie>> getFavoritMovies() {
        return favoriteMovies;
    }



    @SuppressLint("StaticFieldLeak")
    public void loadMovies(final String type){

        new AsyncTask<Void, Void, List<Movie>>(){
            List<Movie> result;

            @Override
            protected List<Movie> doInBackground(Void... voids) {
                JSONObject moviesJSON = new JSONObject();
                URL url = SupportContract.getMoviesURL(type);
                try {
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream stream = new BufferedInputStream(connection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    moviesJSON = new JSONObject(reader.readLine());

                } catch (IOException e) {
                    e.printStackTrace();



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                result = JSONToMovies.getMoviesFrom(moviesJSON);
                return result;
            }

            @Override
            protected void onPostExecute(List<Movie> movies) {
                super.onPostExecute(movies);
                switch(type){
                    case "popular":
                        popularMovies.setValue(movies);
                        loadPopularMoviesReviews();
                        loadPopularMoviesTrailers();
                        break;

                    case "top_rated":
                        topRatedMovies.setValue(movies);
                        loadTopRatedMoviesReview();
                        loadTopRatedMoviesTrailers();
                        break;
                    default:
                        break;
                }

            }
        }.execute();

    }

    @SuppressLint("StaticFieldLeak")
    public void loadPopularMoviesTrailers() {
        new AsyncTask<Void, Void, List<List<Trailer>>>() {


            @Override
            protected List<List<Trailer>> doInBackground(Void... voids) {
                List<List<Trailer>> result = new ArrayList<>();
                try {
                    if(popularMovies.getValue() != null){
                        for(int i = 0; i < popularMovies.getValue().size(); i++) {
                            result.add(fetchTrailersFromURL(SupportContract.getMovieTrailersURL(popularMovies.getValue().get(i).getId())));
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<List<Trailer>> trailers) {
                super.onPostExecute(trailers);
                for(int i = 0; i < popularMovies.getValue().size();i++){
                    popularMovies.getValue().get(i).setTrailers(trailers.get(i));
                }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void loadTopRatedMoviesTrailers(){
        new AsyncTask<Void, Void, List<List<Trailer>>>() {
            List<List<Trailer>> result = new ArrayList<>();

            @Override
            protected List<List<Trailer>> doInBackground(Void... voids) {

                try {
                    for(int i = 0; i < topRatedMovies.getValue().size(); i++) {
                        result.add(fetchTrailersFromURL(SupportContract.getMovieTrailersURL(topRatedMovies.getValue().get(i).getId())));
                    }

                } catch (IOException e) {
                    e.printStackTrace();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<List<Trailer>> trailers) {
                super.onPostExecute(trailers);
                for(int i = 0; i < topRatedMovies.getValue().size();i++){
                    topRatedMovies
                            .getValue()
                            .get(i)
                            .setTrailers(trailers.get(i));
                }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void loadPopularMoviesReviews(){
        new AsyncTask<Void, Void, List<List<Review>>>() {


            @Override
            protected List<List<Review>> doInBackground(Void... voids) {
                List<List<Review>> result = new ArrayList<>();
                try {
                    for(int i = 0; i < popularMovies.getValue().size(); i++) {
                        result.add(fetchReviewsFromURL(SupportContract.getReviewsURL(popularMovies.getValue().get(i).getId())));
                    }

                } catch (IOException e) {
                    e.printStackTrace();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<List<Review>> reviews) {
                super.onPostExecute(reviews);
                    for(int i = 0; i < popularMovies.getValue().size();i++) {
                        popularMovies.getValue()
                                .get(i)
                                .setReviews(reviews.get(i));
                    }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void loadTopRatedMoviesReview(){
        new AsyncTask<Void, Void, List<List<Review>>>() {
            List<List<Review>> result = new ArrayList<>();

            @Override
            protected List<List<Review>> doInBackground(Void... voids) {
                JSONObject trailersJSON;

                try {
                    for(int i = 0; i < topRatedMovies.getValue().size(); i++) {
                        result.add(fetchReviewsFromURL(SupportContract.getReviewsURL(popularMovies.getValue().get(i).getId())));
                    }

                } catch (IOException e) {
                    e.printStackTrace();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<List<Review>> reviews) {
                super.onPostExecute(reviews);
                for(int i = 0; i < popularMovies.getValue().size();i++){
                    popularMovies.getValue().get(i).setReviews(reviews.get(i));
                }
            }
        }.execute();
    }

    private List<Trailer> fetchTrailersFromURL(URL url) throws IOException, JSONException {
        List<Trailer> result;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream stream = new BufferedInputStream(connection.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        JSONObject trailersJSON = new JSONObject(reader.readLine());
        result = JSONToMovies.getTrailersFrom(trailersJSON);
        connection.disconnect();
        return result;
    }

    private List<Review> fetchReviewsFromURL(URL url) throws IOException, JSONException{
        List<Review> result;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream stream = new BufferedInputStream(connection.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        JSONObject trailersJSON = new JSONObject(reader.readLine());
        result = JSONToMovies.getReviwsFrom(trailersJSON);
        connection.disconnect();
        return result;
    }

}
