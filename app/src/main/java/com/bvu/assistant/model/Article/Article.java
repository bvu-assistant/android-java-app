package com.bvu.assistant.model.Article;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Article implements Serializable {
    public enum ArticleType {Headlines, StudentNews};

    private ArticleType type;
    private String title;
    private String date;
    private String url;
    private boolean isNew;
    private boolean isFavorite;

    public Article(ArticleType type, String title, String date, String url, boolean isNew, boolean isFavorite) {
        this.type = type;
        this.title = title;
        this.date = date;
        this.url = url;
        this.isNew = isNew;
        this.isFavorite = isFavorite;
    }


    public ArticleType getType() {
        return type;
    }

    public void setType(ArticleType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
