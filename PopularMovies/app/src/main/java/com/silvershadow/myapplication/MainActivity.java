package com.silvershadow.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.silvershadow.myapplication.DataLoading.FetchMoviesData;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    RecyclerView allMoviesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new FetchMoviesData().execute(SupportContract.getMoviesURL("popular"), SupportContract.getMoviesURL("top_rated"));



    }
}
