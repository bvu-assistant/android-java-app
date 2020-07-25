package com.bvu.assistant.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bvu.assistant.R;
import com.bvu.assistant.model.Article.Article;
import com.bvu.assistant.repository.ArticleDetail;
import com.bvu.assistant.repository.ArticleDocument;
import com.bvu.assistant.repository.ArticleResponse;
import com.bvu.assistant.view.customview.ArticleSharer;
import com.bvu.assistant.viewmodel.adapters.NewsDetailAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class NewsDetailActivity extends AppCompatActivity {
    MaterialToolbar actionBar;
    ImageButton btnSare;
    ImageButton btnReload;
    public TextView txtMainContent;
    public SwipeRefreshLayout refresher;
    List<ArticleDocument> dataList;
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
        new ArticleResponse(this).execute(a.getUrl());
    }


    public void setData(ArticleDetail detail) {
        txtMainContent.setText(detail.getFullMessage());
        dataList.clear();
        dataList.addAll(detail.getLinks());
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
            new ArticleResponse(this).execute(a.getUrl());
        });


        txtTitle = findViewById(R.id.newsDetailTitle);
        txtTitle.setText(a.getTitle());
        txtMainContent = findViewById(R.id.newsDetailMainContent);


        listView = findViewById(R.id.newsDetailListLinks);
        dataList = new ArrayList<>();
        adapter = new NewsDetailAdapter(this, dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            ArticleDocument item = dataList.get(position);

            String ext = item.getTitle();
            if (ext.contains("http")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(NewsDetailActivity.this, OfficeViewerActivity.class);
                intent.putExtra("documentUrl", item.getUrl());
                startActivity(intent);
            }
        });


        btnSare = findViewById(R.id.newsDetailShareBtn);
        handleShareButtonClick(this, btnSare, a);


        btnReload = findViewById(R.id.newsDetailReloadBtn);
        btnReload.setOnClickListener(v -> new ArticleResponse(this).execute(a.getUrl()));
    }

    private void handleShareButtonClick(Context context, ImageButton btn, Article a) {
        btn.setOnClickListener(view -> {
            ArticleSharer.showBottomSheet(context, btn, a);
        });
    }

}