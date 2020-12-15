package com.bvu.assistant.ui.main.news.common;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bvu.assistant.R;
import com.bvu.assistant.data.repository.article.Article;
import com.bvu.assistant.ui.main.news.details.NewsDetailActivity;
import com.bvu.assistant.ui.custom_view.ArticleSharer;
import com.bvu.assistant.data.repository.room.SqLiteArticleHelper;
import com.bvu.assistant.data.repository.room.SqLiteArticleHelperCallback;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsViewHolder>
        implements SqLiteArticleHelperCallback, Filterable {
    private List<Article> dataList;
    private List<Article> lastDataList;
    private FragmentManager fragmentManager;
    private FragmentActivity activity;
    private Context context;
    private SqLiteArticleHelper dbHelper;


    public NewsRecyclerAdapter(List<Article> dataList, Context context, FragmentActivity activity, FragmentManager fragmentManager) {
        this.dataList = dataList;
        this.context = context;
        this.lastDataList = new ArrayList<>();
        this.activity = activity;
        this.fragmentManager = fragmentManager;

        dbHelper = new SqLiteArticleHelper(context, this);
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
        /*holder.txtCardIsNew.setText(a.isNew()? "Mới": " ");*/
        holder.imvIsNew.setVisibility(a.isNew() ? View.VISIBLE : View.GONE);
        loadFavoriteState(holder.imbCardIsFavorite, a);

        handleCardTitleClick(holder.cardTitleBound, a);
        handleShareButtonClick(holder.imbCardShare, a);
        handleFavoriteButtonClick(holder.imbCardIsFavorite, a);
    }


    @Override
    public int getItemCount() {
        return this.dataList.size();
    }

    public void updateBaseDataList() {
        this.lastDataList.clear();
        this.lastDataList.addAll(dataList);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String str = constraint.toString();
                List<Article> temp = new ArrayList<>();

                if (str.isEmpty()) {
                    temp.addAll(lastDataList);
                }
                else {
                    for (Article a: lastDataList) {
                        if (a.getTitle().toLowerCase().contains(str.toLowerCase())) {   //  Filter by each Article's Title
                            temp.add(a);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = temp;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataList.clear();
                dataList.addAll((ArrayList<Article>)results.values);
                notifyDataSetChanged();
            }
        };
    }




    private void handleCardTitleClick(FrameLayout view, Article a) {
        view.setOnClickListener(v -> {
            Intent intent = new Intent(this.context, NewsDetailActivity.class);
            intent.putExtra("article", a);
            this.context.startActivity(intent);
        });
    }

    private void handleShareButtonClick(ImageButton btn, Article a) {
        btn.setOnClickListener(view -> {
            ArticleSharer.showBottomSheet(context, activity, btn, a);
        });
    }

    private void handleFavoriteButtonClick(ImageButton btn, Article a) {
        btn.setOnClickListener(view -> {
            toggleFavorite(btn, a);
        });
    }


    private void loadFavoriteState(ImageButton btn, Article a) {
        if (dbHelper.getArticle(a.getId()) != null) {
            btn.setImageResource(R.drawable.ic_heart);
            a.setFavorite(true);
        }
        else {
            btn.setImageResource(R.drawable.ic_heart_outline);
            a.setFavorite(false);
        }
    }

    private void toggleFavorite(ImageButton btn, Article a) {
        if (!a.isFavorite()) {
            if (dbHelper.addArticle(a)) {
                btn.setImageResource(a.isFavorite()? R.drawable.ic_heart_outline: R.drawable.ic_heart);
                a.setFavorite(!a.isFavorite());
            }
        }
        else {
            if (dbHelper.deleteArticle(a.getId())) {
                btn.setImageResource(a.isFavorite()? R.drawable.ic_heart_outline: R.drawable.ic_heart);
                a.setFavorite(!a.isFavorite());
            }
        }
    }



    @Override
    public void onDatabaseTransactionSuccess(@NotNull String message) {
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDatabaseTransactionFailure(@NotNull String message) {
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}



class NewsViewHolder extends RecyclerView.ViewHolder {
    private SqLiteArticleHelper dbHelper;

    public TextView txtCardTitle;
    public TextView txtCardDate;
    /*public TextView txtCardIsNew;*/
    public ImageView imvIsNew;
    public ImageButton imbCardIsFavorite;
    public ImageButton imbCardShare;
    public FrameLayout cardTitleBound;


    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);

        this.imvIsNew = itemView.findViewById(R.id.imvIsNew);
        this.txtCardTitle = itemView.findViewById(R.id.cardTitle);
        this.txtCardDate = itemView.findViewById(R.id.cardDate);
        /*this.txtCardIsNew = itemView.findViewById(R.id.cardIsNew);*/
        this.imbCardIsFavorite = itemView.findViewById(R.id.cardFavoriteBtn);
        this.imbCardShare = itemView.findViewById(R.id.cardShareBtn);
        this.cardTitleBound = itemView.findViewById(R.id.cardTitleBound);
    }
}