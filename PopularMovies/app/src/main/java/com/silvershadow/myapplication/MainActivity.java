package com.silvershadow.myapplication;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.silvershadow.myapplication.Adapters.MoviesAdapter;
import com.silvershadow.myapplication.Entities.Movie;
import com.silvershadow.myapplication.ViewModel.AllMoviesViewModel;


public class MainActivity extends AppCompatActivity {

    public enum SortType{
        POPULAR{
            @Override
            public void setType() {
                mMovieAdapter.setMoviesToPopular();
            }
        },
        TOP_RATED{
            @Override
            public void setType() {
                mMovieAdapter.setMoviesToTopRated();
            }
        },
        FAVORITE{
            @Override
            public void setType() {
                mMovieAdapter.setMoviesToFavorite();
            }

        };
        public abstract void setType();
    }

    private RecyclerView allMoviesRV;
    public static MoviesAdapter mMovieAdapter;
    private ConnectivityManager cm;
    private AllMoviesViewModel model;
    private SortType currentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        cm.getActiveNetworkInfo();
        if (cm.getActiveNetworkInfo() != null) {

            setContentView(R.layout.activity_main);
            model = ViewModelProviders.of(this).get(AllMoviesViewModel.class);

            model.loadMovies("popular");
            model.loadMovies("top_rated");

            allMoviesRV = findViewById(R.id.movies_rv);
            allMoviesRV.setHasFixedSize(true);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            allMoviesRV.setLayoutManager(layoutManager);
            mMovieAdapter = new MoviesAdapter(model, getLifecycle(), new MoviesAdapter.MovieItemClickListener() {
                @Override
                public void onItemClick(Movie movie, Context context) {
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra(SupportContract.MOVIE_KEY, movie);
                    startActivity(intent);
                }
            });
            allMoviesRV.setAdapter(mMovieAdapter);

            if(savedInstanceState == null)
                mMovieAdapter.setMoviesToPopular();
        } else
            setContentView(R.layout.internet_error_layout);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (cm.getActiveNetworkInfo() != null) {
            switch (item.getItemId()) {
                case R.id.popular_mi:
                    mMovieAdapter.setMoviesToPopular();
                    setTitle(R.string.popular);
                    currentType = SortType.POPULAR;
                    break;
                case R.id.top_rated_mi:
                    mMovieAdapter.setMoviesToTopRated();
                    setTitle(R.string.top_rated);
                    currentType = SortType.TOP_RATED;
                    break;
                case R.id.favorite_mi:
                    mMovieAdapter.setMoviesToFavorite();
                    setTitle(R.string.favorite);
                    currentType = SortType.FAVORITE;
                    break;

                    default:
                    return false;

            }
            mMovieAdapter.notifyDataSetChanged();

        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SupportContract.MOVIE_SORT_TYPE_KEY, currentType);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null && !savedInstanceState.isEmpty()) {
            currentType = (SortType) savedInstanceState.getSerializable(SupportContract.MOVIE_SORT_TYPE_KEY);
            if(currentType != null)
                currentType.setType();
        }
    }
}
