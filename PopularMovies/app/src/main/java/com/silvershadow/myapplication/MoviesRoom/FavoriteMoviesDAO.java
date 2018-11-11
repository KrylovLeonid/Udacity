package com.silvershadow.myapplication.MoviesRoom;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.silvershadow.myapplication.Entities.Movie;

import java.util.List;

@Dao
public interface FavoriteMoviesDAO {
    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> loadAllFavoriteMovies();

    @Query("SELECT EXISTS(SELECT id FROM movies WHERE id = :id)")
    int contains(int id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavoriteMovie(Movie movie);

    @Delete
    void removeFavoriteMovie(Movie movie);

}
