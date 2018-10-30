package com.silvershadow.myapplication.Entities;

import java.io.Serializable;
import java.net.URL;

public class Trailer implements Serializable {
    String name;
    URL trailerURL;

    public Trailer(String name, URL trailerURL) {
        this.name = name;
        this.trailerURL = trailerURL;
    }

    public String getName() {
        return name;
    }

    public URL getTrailerURL() {
        return trailerURL;
    }
}
