package com.silvershadow.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.silvershadow.myapplication.Adapters.MoviesAdapter;
import com.silvershadow.myapplication.DataLoading.FetchMoviesData;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    RecyclerView allMoviesRV;
    MoviesAdapter  mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new FetchMoviesData().execute(SupportContract.getMoviesURL("popular"), SupportContract.getMoviesURL("top_rated"));

        mMovieAdapter = new MoviesAdapter();
        allMoviesRV = findViewById(R.id.movies_rv);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        allMoviesRV.setLayoutManager(layoutManager);
        allMoviesRV.setAdapter(mMovieAdapter);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.popular_mi:
                mMovieAdapter.setMoviesToPopular();
                break;
            case R.id.top_rated_mi:
                mMovieAdapter.setMoviesToTopRated();
                break;
            default:
                return false;

        }

        return true;
    }
}
