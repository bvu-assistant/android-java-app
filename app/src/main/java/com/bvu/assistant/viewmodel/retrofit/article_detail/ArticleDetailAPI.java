package com.bvu.assistant.viewmodel.retrofit.article_detail;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ArticleDetailAPI {
    @GET("details/{articleId}")
    Call<ArticleDetail> getDetail(@Path("articleId") int articleId);
}
