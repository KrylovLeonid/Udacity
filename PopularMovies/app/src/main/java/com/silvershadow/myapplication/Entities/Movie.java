package com.silvershadow.myapplication.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
@Entity (tableName = "movies")
public class Movie implements Serializable {
    @PrimaryKey
    private int id;

    private String title;
    private String headerImg;
    private String thumbImg;
    private String description;
    private String averageRating;
    private String votes;
    private String releaseDate;


    public Movie(int id, String title, String headerImg, String thumbImg, String description, String averageRating, String votes, String releaseDate) {
        this.title = title;
        this.headerImg = headerImg;
        this.thumbImg = thumbImg;
        this.description = description;
        this.averageRating = averageRating;
        this.votes = votes;
        this.releaseDate = releaseDate;
        this.id = id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public void setThumbImg(String thumbImg) {
        this.thumbImg = thumbImg;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Ignore
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass())
            return false;
        Movie other = (Movie)obj;
        return this.id == other.getId();
    }
    @Ignore
    public int hashCode(){
        return 2 + 12*id + 49*title.hashCode() + 89*headerImg.hashCode() + 140*thumbImg.hashCode() +
                240*description.hashCode() + 503*averageRating.hashCode() + 723*votes.hashCode() + 1024*releaseDate.hashCode();
    }
}
