package com.silvershadow.myapplication.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.silvershadow.myapplication.Converters.JSONToMovies;
import com.silvershadow.myapplication.Entities.Movie;
import com.silvershadow.myapplication.Entities.Review;
import com.silvershadow.myapplication.Entities.Trailer;
import com.silvershadow.myapplication.MoviesRoom.FavoriteMoviesDataBase;
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

public class SingleMovieViewModel extends AndroidViewModel {
    private MutableLiveData<List<Review>> reviews = new MutableLiveData<>();
    private MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();
    private FavoriteMoviesDataBase db;
    private LiveData<List<Movie>> favoriteMovies;




    public SingleMovieViewModel(Application application) {
        super(application);
        reviews.setValue(new ArrayList<Review>(0));
        trailers.setValue(new ArrayList<Trailer>(0));
        db = FavoriteMoviesDataBase.getInstance(this.getApplication());
        favoriteMovies = db.favoriteMoviesDAO().loadAllFavoriteMovies();

    }


    public LiveData<List<Review>> getReviews() {
        return reviews;
    }

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    @SuppressLint("StaticFieldLeak")
    public void loadTrailers(final int id){

        new AsyncTask<Void, Void, List<Trailer>>() {
            List<Trailer> result = new ArrayList<>();

            @Override
            protected List<Trailer> doInBackground(Void... voids) {
                try {
                    result = fetchTrailersFromURL(SupportContract.getMovieTrailersURL(id));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<Trailer> result) {
                super.onPostExecute(result);
                trailers.setValue(result);
            }

        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void loadReviews(final int id){
        new AsyncTask<Void, Void, List<Review>>() {
            List<Review> result = new ArrayList<>();

            @Override
            protected List<Review> doInBackground(Void... voids) {

                try {
                        result = fetchReviewsFromURL(SupportContract.getReviewsURL(id));

                } catch (IOException e) {
                    e.printStackTrace();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<Review> result) {
                super.onPostExecute(result);
                reviews.setValue(result);

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


    public void addFavoriteMovie(final Movie movie){

        MoviesExecutor.getInstance().getDBRequest().execute(new Runnable() {
            @Override
            public void run() {
                db.favoriteMoviesDAO().addFavoriteMovie(movie);
            }
        });
    }
    public void removeFavoriteMovie(final Movie movie){
        MoviesExecutor.getInstance().getDBRequest().execute(new Runnable() {
            @Override
            public void run() {
                db.favoriteMoviesDAO().removeFavoriteMovie(movie);
            }
        });
    }

    public LiveData<List<Movie>> getFavoriteMovies(){
        return favoriteMovies;
    }



}
