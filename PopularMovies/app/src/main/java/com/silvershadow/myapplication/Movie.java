package com.silvershadow.myapplication;

public class Movie {

    private int id;
    private String name;
    private String headerImg;
    private String thumbImg;
    private String description;
    private String averageRating;
    private String votes;
    private String releaseDate;


    public Movie(int id, String name, String headerImg, String thumbImg, String description, String averageRating, String votes, String releaseDate) {
        this.name = name;
        this.headerImg = headerImg;
        this.thumbImg = thumbImg;
        this.description = description;
        this.averageRating = averageRating;
        this.votes = votes;
        this.releaseDate = releaseDate;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public String getThumbImg() {
        return thumbImg;
    }

    public String getDescreption() {
        return description;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public String getVotes() {
        return votes;
    }

    public String getReliaseDate() {
        return releaseDate;
    }
}
