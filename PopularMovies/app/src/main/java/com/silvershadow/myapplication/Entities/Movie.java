package com.silvershadow.myapplication.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable {

    private int id;
    private String title;
    private String headerImg;
    private String thumbImg;
    private String description;
    private String averageRating;
    private String votes;
    private String releaseDate;
    private List<Review> reviews;
    private List<Trailer> trailers;


    public Movie(int id, String name, String headerImg, String thumbImg, String description, String averageRating, String votes, String releaseDate) {
        this.title = name;
        this.headerImg = headerImg;
        this.thumbImg = thumbImg;
        this.description = description;
        this.averageRating = averageRating;
        this.votes = votes;
        this.releaseDate = releaseDate;
        this.id = id;
        reviews = new ArrayList<>();
        trailers = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public String getThumbImg() {
        return thumbImg;
    }

    public String getDescription() {
        return description;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public String getVotes() {
        return votes;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getId(){return id;}

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }
}
