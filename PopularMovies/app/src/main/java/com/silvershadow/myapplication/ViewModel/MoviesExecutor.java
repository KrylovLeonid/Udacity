package com.silvershadow.myapplication.ViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MoviesExecutor {
    private static final Object LOCK = new Object();
    private static MoviesExecutor sInstance;
    private final Executor networkRequest;
    private final Executor DBRequest;


    public MoviesExecutor(Executor networkRequest, Executor DBRequest) {
        this.networkRequest = networkRequest;
        this.DBRequest = DBRequest;
    }

    public static MoviesExecutor getInstance(){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new MoviesExecutor(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3));
            }
        }
        return sInstance;
    }

    public Executor getNetworkRequest() {
        return networkRequest;
    }

    public Executor getDBRequest() {
        return DBRequest;
    }
}
