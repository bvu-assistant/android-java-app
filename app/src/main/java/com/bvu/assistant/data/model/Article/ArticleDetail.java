package com.bvu.assistant.data.model.Article;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ArticleDetail {
    @SerializedName("fullMessage")
    @Expose
    private String fullMessage;

    @SerializedName("links")
    @Expose
    private Map<String, String> links;



    public ArticleDetail(String fullMessage, Map<String, String> links) {
        this.fullMessage = fullMessage;
        this.links = links;
    }


    public List<ArticleLink> getRecords() {
        List<ArticleLink> res = new ArrayList<>();

        this.getLinks().forEach((title, url) -> {
            res.add(new ArticleLink(title, url));
        });

        return res;
    }

    public String getFullMessage() {
        List<ArticleLink> links = getRecords();

        for (ArticleLink doc : links) {
            if (fullMessage.contains(doc.getTitle()))
                if (!doc.getTitle().contains("http")) {
                    fullMessage = fullMessage.replace(doc.getTitle(), "");
                }
        }

        return fullMessage;
    }

    public void setFullMessage(String fullMessage) {
        this.fullMessage = fullMessage;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

}
