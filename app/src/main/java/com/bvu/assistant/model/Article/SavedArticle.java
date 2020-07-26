package com.bvu.assistant.model.Article;


public class SavedArticle extends Article{
    private String savedTime;

    public SavedArticle(String type, String title, String date, String url, boolean isNew, boolean isFavorite, String savedTime) {
        super(type, title, date, url, isNew, isFavorite);
        this.savedTime = savedTime;
    }


    public String getSavedTime() {
        return savedTime;
    }

    public void setSavedTime(String savedTime) {
        this.savedTime = savedTime;
    }
}
