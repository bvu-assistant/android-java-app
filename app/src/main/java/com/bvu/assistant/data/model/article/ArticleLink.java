package com.bvu.assistant.data.model.article;



import org.apache.commons.io.FilenameUtils;

public class ArticleLink {
    private String title;
    private String url;
    private String fileExtension;


    public ArticleLink(String title, String url) {
        this.title = title;
        this.url = this.title.contains("http")? this.title:
                url.contains("Resource") ? ("https://sinhvien.bvu.edu.vn" + url):
                url.contains("?fbclid")? url.split(".?fbclid")[0]: url;
        this.fileExtension = FilenameUtils.getExtension(this.url);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}
