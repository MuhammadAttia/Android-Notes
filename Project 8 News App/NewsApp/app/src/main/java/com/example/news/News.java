package com.example.news;

public class News {
    private String title;
    private String section;
    private String author;
    private String date;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTrailText(String trailText) {
        this.trailText = trailText;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String trailText;
    private String url;

    public News(String title, String author, String trailText, String date, String section, String url) {
        this.title = title;
        this.author = author;
        this.trailText = trailText;
        this.date = date;
        this.section = section;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getTrailText() {
        return trailText;
    }

    public String getUrl() {
        return url;
    }
}
