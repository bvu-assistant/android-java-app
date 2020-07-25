package com.bvu.assistant.viewmodel.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bvu.assistant.R;
import com.bvu.assistant.repository.ArticleDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewsDetailAdapter extends ArrayAdapter<ArticleDocument> {
    List<ArticleDocument> dataList;
    Activity context;


    public NewsDetailAdapter(Activity context, List<ArticleDocument> dataList) {
        super(context, R.layout.news_details_list_item);
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.news_details_list_item, null, true);
        ArticleDocument dataItem = this.dataList.get(position);
        String ext = dataItem.getFileExtension();

        ImageView icon = item.findViewById(R.id.newsDetailIcon);
        switch (ext) {
            case "pdf": {
                icon.setImageResource(R.drawable.ic_icons8_pdf_1);
                break;
            }

            case "doc": case "docx": {
                icon.setImageResource(R.drawable.ic_icons8_microsoft_word_2019);
                break;
            }

            case "ppt": case "pptx": {
                icon.setImageResource(R.drawable.ic_icons8_microsoft_powerpoint_2019);
                break;
            }

            case "xls": case "xlsx": {
                icon.setImageResource(R.drawable.ic_icons8_microsoft_excel_2019);
                break;
            }
        }


        TextView filename = item.findViewById(R.id.newsDetailFileName);
        filename.setText(dataItem.getTitle());


        Animation animation = AnimationUtils.loadAnimation(this.context, R.anim.translate_up);
        View boundLayout = context.findViewById(R.id.newsDetailListViewBound);
        View titleLayout = context.findViewById(R.id.newsDetailListLinksTitleBound);
        titleLayout.startAnimation(animation);
        boundLayout.startAnimation(animation);

        item.startAnimation(animation);


        return item;
    }

    @Override
    public int getCount() {
        return this.dataList.size();
    }
}
