package com.bvu.assistant.view.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bvu.assistant.R;
import com.bvu.assistant.model.Article.Article;
import com.bvu.assistant.view.customview.ArticleSharer;
import com.bvu.assistant.viewmodel.adapters.NewsDetailAdapter;
import com.bvu.assistant.viewmodel.repository.ArticleLink;
import com.bvu.assistant.viewmodel.retrofit.article_detail.ArticleDetail;
import com.bvu.assistant.viewmodel.retrofit.article_detail.ArticleDetailAPI;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsDetailActivity extends AppCompatActivity {
    private static final String TAG = "NewsDetailActivityTAG";
    MaterialToolbar actionBar;
    ImageButton btnSare;
    ImageButton btnReload;
    public TextView txtMainContent;
    public SwipeRefreshLayout refresher;
    List<ArticleLink> dataSource;
    NewsDetailAdapter adapter;
    TextView txtTitle;
    ListView listView;
    Article a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Intent receivedIntent = getIntent();
        a = (Article)receivedIntent.getSerializableExtra("article");

        initAndMapping();
        getDetailResponse(a.getId());
    }



    public void setData(ArticleDetail detail) {
        txtMainContent.setText(detail.getFullMessage());
        dataSource.clear();
        dataSource.addAll(detail.getRecords());
        adapter.notifyDataSetChanged();
    }


    private void initAndMapping() {
        actionBar = findViewById(R.id.newsDetailActionBar);
        actionBar.setSubtitle("Ngày " + a.getDate());
        actionBar.setNavigationOnClickListener(view -> {
            finish();
        });


        refresher = findViewById(R.id.newsDetailRefresher);
        refresher.setEnabled(false);
        refresher.setColorSchemeColors(
            Color.rgb(0, 138, 230),
            Color.rgb(25, 189, 0),
            Color.rgb(255, 204, 0),
            Color.rgb(245, 0, 53)
        );
        refresher.setOnRefreshListener(()-> {
            getDetailResponse(a.getId());
        });


        txtTitle = findViewById(R.id.newsDetailTitle);
        txtTitle.setText(a.getTitle());
        txtMainContent = findViewById(R.id.newsDetailMainContent);


        listView = findViewById(R.id.newsDetailListLinks);
        dataSource = new ArrayList<>();
        adapter = new NewsDetailAdapter(this, dataSource);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            ArticleLink item = dataSource.get(position);

            String ext = item.getTitle();
            if (ext.contains("http")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(NewsDetailActivity.this, DocumentViewerActivity.class);
                intent.putExtra("documentUrl", item.getUrl());
                startActivity(intent);
            }
        });


        btnSare = findViewById(R.id.newsDetailShareBtn);
        handleShareButtonClick(this, btnSare, a);


        btnReload = findViewById(R.id.newsDetailReloadBtn);
        btnReload.setOnClickListener(v -> getDetailResponse(a.getId()));
    }

    private void handleShareButtonClick(Context context, ImageButton btn, Article a) {
        btn.setOnClickListener(view -> {
            ArticleSharer.showBottomSheet(context, btn, a);
        });
    }


    private void getDetailResponse(int articleId) {
        refresher.setRefreshing(true);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bvu-news-getter.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ArticleDetailAPI detailAPI = retrofit.create(ArticleDetailAPI.class);


        detailAPI.getDetail(articleId)
            .enqueue(new Callback<ArticleDetail>() {
                @Override
                public void onResponse(Call<ArticleDetail> call, Response<ArticleDetail> response) {
                    refresher.setRefreshing(false);

                    if (response.isSuccessful() && response.body() != null) {
                        ArticleDetail detail = response.body();
                        setData(detail);
                        return;
                    }

                    Toast.makeText(NewsDetailActivity.this, "Có lỗi trong quá trình xử lý thông tin nè", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ArticleDetail> call, Throwable t) {
                    refresher.setRefreshing(false);
                    Toast.makeText(NewsDetailActivity.this, "Có lỗi trong quá trình xử lý thông tin nè", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFailure: " + t.getMessage() + t + call);
                }
            });
    }
}