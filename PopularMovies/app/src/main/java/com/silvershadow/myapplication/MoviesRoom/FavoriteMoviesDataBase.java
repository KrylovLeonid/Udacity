package com.silvershadow.myapplication.MoviesRoom;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.silvershadow.myapplication.Entities.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class FavoriteMoviesDataBase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favorite_movies";
    private static FavoriteMoviesDataBase sInstance;


    public static FavoriteMoviesDataBase getInstance(Context context){
        if (sInstance == null){
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(), FavoriteMoviesDataBase.class, FavoriteMoviesDataBase.DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract FavoriteMoviesDAO favoriteMoviesDAO();
}
