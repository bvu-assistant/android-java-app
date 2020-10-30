package com.bvu.assistant.ui.custom_view;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.bvu.assistant.R;
import com.bvu.assistant.data.repository.article.Article;
import com.bvu.assistant.databinding.NewsBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import eightbitlab.com.blurview.RenderScriptBlur;

public class ArticleSharer {


    public static void showBottomSheet(Context context, Activity activity, ImageButton btn, Article a) {
        LayoutInflater inflater = LayoutInflater.from(context);
        NewsBottomSheetBinding B = DataBindingUtil.inflate(inflater, R.layout.news_bottom_sheet, null, false);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);


        //  Assign the header for BottomSheet
        B.cardSheetLink.setText(a.getTitle());


        B.cardSheetCopyUrl.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();

            ClipboardManager clipboardManager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(ClipData.newPlainText("url", a.getUrl()));

            Toast.makeText(context, "Đã sao chép liên kết vào bộ nhớ tạm", Toast.LENGTH_SHORT).show();
        });


        B.cardSheetShareQR.setOnClickListener(v -> {
            QRCodeDialog.show(context, B.cardSheetShareQR, a.getUrl());
        });


        B.cardSheetShareToOthers.setOnClickListener(v -> {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_TEXT, a.getUrl());
            share.setType("text/plain");
            context.startActivity(Intent.createChooser(share, "Chia sẻ với ứng dụng"));
        });


        //  Show up the BottomSheet Modal
        bottomSheetDialog.setContentView(B.getRoot());
        blurBottomNavBar(B, context, activity);
        bottomSheetDialog.show();
    }

    private static void blurBottomNavBar(NewsBottomSheetBinding B, Context context, Activity activity) {
        TypedValue value = new TypedValue();
        context.getResources().getValue(R.dimen.mainAct_bottomNav_blurRadius, value, true);

        float radius = value.getFloat();
        View decorView = activity.getWindow().getDecorView();
        Drawable windowBackground = decorView.getBackground();

        B.newsBottomSheetBounder.setupWith((ViewGroup)B.getRoot())
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(new RenderScriptBlur(context))
            .setBlurRadius(radius)
            .setOverlayColor(context.getResources().getColor(R.color.main_act_bottomnav_overlay))
            .setHasFixedTransformationMatrix(false);
    }

}
