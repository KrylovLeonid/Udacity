package com.silvershadow.myapplication.Entities;

import java.io.Serializable;

public class Review implements Serializable {
    String author;
    String content;

    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
