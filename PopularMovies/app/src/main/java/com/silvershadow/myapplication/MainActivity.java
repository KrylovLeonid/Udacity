package com.silvershadow.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.silvershadow.myapplication.Adapters.MoviesAdapter;
import com.silvershadow.myapplication.DataLoading.FetchMoviesData;
import com.silvershadow.myapplication.DataLoading.MovieDataHolder;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView allMoviesRV;
    public static MoviesAdapter  mMovieAdapter;
    ConnectivityManager cm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        cm.getActiveNetworkInfo();
        if(cm.getActiveNetworkInfo() != null){
            setContentView(R.layout.activity_main);

            new FetchMoviesData().execute(SupportContract.getMoviesURL("popular"), SupportContract.getMoviesURL("top_rated"));

            allMoviesRV = findViewById(R.id.movies_rv);
            allMoviesRV.setHasFixedSize(true);
            GridLayoutManager layoutManager = new GridLayoutManager(this,2);
            allMoviesRV.setLayoutManager(layoutManager);
            mMovieAdapter = new MoviesAdapter();
            allMoviesRV.setAdapter(mMovieAdapter);
        }
        else
            setContentView(R.layout.internet_error_layout);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(cm.getActiveNetworkInfo() != null){
            switch (item.getItemId()) {
                case R.id.popular_mi:
                    mMovieAdapter.setMoviesToPopular();

                    break;
                case R.id.top_rated_mi:
                    mMovieAdapter.setMoviesToTopRated();
                    break;
                default:
                    return false;

            }
            mMovieAdapter.notifyDataSetChanged();

        }

        return true;
    }
}
