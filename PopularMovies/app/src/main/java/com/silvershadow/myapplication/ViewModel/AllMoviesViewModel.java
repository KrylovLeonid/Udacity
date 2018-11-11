package com.silvershadow.myapplication.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import com.silvershadow.myapplication.Converters.JSONToMovies;
import com.silvershadow.myapplication.Entities.Movie;
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

public class AllMoviesViewModel extends AndroidViewModel {
    private MutableLiveData <List<Movie>> topRatedMovies = new MutableLiveData<>();
    private MutableLiveData <List<Movie>> popularMovies = new MutableLiveData<>();
    private FavoriteMoviesDataBase favoriteMoviesDB;

    public AllMoviesViewModel(Application application) {
        super(application);
        favoriteMoviesDB = FavoriteMoviesDataBase.getInstance(this.getApplication());
        favoriteMoviesDB.favoriteMoviesDAO().loadAllFavoriteMovies();
        topRatedMovies.setValue(new ArrayList<Movie>(0));
        popularMovies.setValue(new ArrayList<Movie>(0));
    }


    public LiveData<List<Movie>> getTopRatedMovies() {
        return topRatedMovies;
    }
    public LiveData<List<Movie>> getPopularMovies() {
        return popularMovies;
    }
    public LiveData<List<Movie>> getFavoriteMoviesDB(){
        LiveData<List<Movie>> result = favoriteMoviesDB.favoriteMoviesDAO().loadAllFavoriteMovies();
        return result;
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
                        break;

                    case "top_rated":
                        topRatedMovies.setValue(movies);
                        break;
                    default:
                        break;
                }

            }
        }.execute();
    }










}
