package com.and.dusi;

public class Bbs {
    String title;
    String content;

    public Bbs(){

    }

    public Bbs(String title, String content) {
        this.title =title;
        this.content=content;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }


    @Override
    public String toString() {
        return "Bbs{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}