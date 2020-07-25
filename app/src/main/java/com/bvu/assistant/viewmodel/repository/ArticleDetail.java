package com.bvu.assistant.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ArticleDetail {
    private String fullMessage;
    public Map<String, String> links;


    public ArticleDetail() {
        this.fullMessage = new String("");
        this.links = new HashMap<>();
    }

    public ArticleDetail(String fullMessage, Map<String, String> links) {
        this.fullMessage = fullMessage;
        this.links = links;
    }

    public String getFullMessage() {
        List<ArticleDocument> links = getLinks();

        for (ArticleDocument doc : links) {
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

    public List<ArticleDocument> getLinks() {
        List<ArticleDocument> resList = new ArrayList<>();

        for (Map.Entry<String, String> entry : this.links.entrySet()) {
            resList.add(new ArticleDocument(entry.getKey(), entry.getValue()));
        }

        return resList;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }
}
