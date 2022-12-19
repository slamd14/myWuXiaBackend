package com.yyx.pojo;

import java.io.Serializable;

public class Book implements Serializable {
    private Integer id;
    private String bookName;
    private String author;
    private String imgSrc;
    private String bookShortCut;
    private String bookContentUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getBookShortCut() {
        return bookShortCut;
    }

    public void setBookShortCut(String bookShortCut) {
        this.bookShortCut = bookShortCut;
    }

    public String getBookContentUrl() {
        return bookContentUrl;
    }

    public void setBookContentUrl(String bookContentUrl) {
        this.bookContentUrl = bookContentUrl;
    }
}
