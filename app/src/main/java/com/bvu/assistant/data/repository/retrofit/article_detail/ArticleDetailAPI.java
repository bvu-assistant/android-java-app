package com.bvu.assistant.data.repository.retrofit.article_detail;


import com.bvu.assistant.data.model.Article.ArticleDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ArticleDetailAPI {
    @GET("details/{articleId}")
    Call<ArticleDetail> getDetail(@Path("articleId") int articleId);
}
