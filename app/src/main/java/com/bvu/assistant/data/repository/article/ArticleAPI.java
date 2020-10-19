package com.bvu.assistant.data.repository.article;

import com.bvu.assistant.data.model.Article.ArticleDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ArticleAPI {

    @GET("details/{articleId}")
    Call<ArticleDetail> getDetail(@Path("articleId") int articleId);

    List<Article> getArticlesList();
}
