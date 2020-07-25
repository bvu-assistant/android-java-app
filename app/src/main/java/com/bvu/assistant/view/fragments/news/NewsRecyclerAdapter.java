package com.bvu.assistant.view.fragments.news;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bvu.assistant.R;
import com.bvu.assistant.model.Article.Article;
import com.bvu.assistant.view.activities.NewsDetailActivity;
import com.bvu.assistant.view.customview.ArticleSharer;

import java.util.List;


public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private List<Article> dataList;
    private FragmentManager fragmentManager;
    private FragmentActivity activity;
    private Context context;

    public NewsRecyclerAdapter(List<Article> dataList, Context context, FragmentActivity activity, FragmentManager fragmentManager) {
        this.dataList = dataList;
        this.context = context;
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View card = inflater.inflate(R.layout.news_card_item, parent, false);
        return new NewsViewHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article a = this.dataList.get(position);

        holder.txtCardTitle.setText(a.getTitle());
        holder.txtCardDate.setText(a.getDate());
        holder.txtCardIsNew.setText(a.isNew()? "Mới": " ");

        handleCardTitleClick(holder.cardTitleBound, a);
        handleShareButtonClick(holder.imbCardShare, a);
        handleFavoriteButtonClick(holder.imbCardIsFavorite, a);
    }

    @Override
    public int getItemCount() {
        return this.dataList.size();
    }


    private void handleCardTitleClick(FrameLayout view, Article a) {
        view.setOnClickListener(v -> {
            Intent intent = new Intent(this.context, NewsDetailActivity.class);
            intent.putExtra("article", a);
            this.context.startActivity(intent);
        });
    }

    private void handleFavoriteButtonClick(ImageButton btn, Article a) {
        btn.setImageResource(a.isFavorite()? R.drawable.ic_baseline_favorite_24: R.drawable.ic_baseline_favorite_border_24);
        btn.setOnClickListener(view -> {
            btn.setImageResource(a.isFavorite()? R.drawable.ic_baseline_favorite_border_24: R.drawable.ic_baseline_favorite_24);
            a.setFavorite(!a.isFavorite());
        });
    }

    private void handleShareButtonClick(ImageButton btn, Article a) {
        btn.setOnClickListener(view -> {
            ArticleSharer.showBottomSheet(context, btn, a);
        });
    }
}



class NewsViewHolder extends RecyclerView.ViewHolder {
    public TextView txtCardTitle;
    public TextView txtCardDate;
    public TextView txtCardIsNew;
    public ImageButton imbCardIsFavorite;
    public ImageButton imbCardShare;
    public FrameLayout cardTitleBound;

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        this.txtCardTitle = itemView.findViewById(R.id.cardTitle);
        this.txtCardDate = itemView.findViewById(R.id.cardDate);
        this.txtCardIsNew = itemView.findViewById(R.id.cardIsNew);
        this.imbCardIsFavorite = itemView.findViewById(R.id.cardFavoriteBtn);
        this.imbCardShare = itemView.findViewById(R.id.cardShareBtn);
        this.cardTitleBound = itemView.findViewById(R.id.cardTitleBound);
    }

}