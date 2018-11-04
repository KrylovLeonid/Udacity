package com.silvershadow.myapplication.Entities;

import android.net.Uri;

import java.io.Serializable;
import java.net.URL;

public class Trailer implements Serializable {
    private String  name;
    private Uri trailerUri;

    public Trailer(String name, Uri trailerURL) {
        this.name = name;
        this.trailerUri = trailerURL;
    }

    public String getName() {
        return name;
    }

    public Uri getTrailerUri() {
        return trailerUri;
    }
}
