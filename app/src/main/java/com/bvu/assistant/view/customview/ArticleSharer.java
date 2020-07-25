package com.bvu.assistant.view.customview;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bvu.assistant.R;
import com.bvu.assistant.model.Article.Article;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ArticleSharer {


    public static void showBottomSheet(Context context, ImageButton btn, Article a) {
        View sheetLayout = LayoutInflater.from(context).inflate(R.layout.news_bottom_sheet, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);


        //  Assign the header for BottomSheet
        TextView txtCardSheetUrl = sheetLayout.findViewById(R.id.cardSheetLink);
        txtCardSheetUrl.setText(a.getTitle());


        LinearLayout cardSheetCopyUrl = sheetLayout.findViewById(R.id.cardSheetCopyUrl);
        cardSheetCopyUrl.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();

            ClipboardManager clipboardManager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(ClipData.newPlainText("url", a.getUrl()));

            Toast.makeText(context, "Đã sao chép liên kết vào bộ nhớ tạm", Toast.LENGTH_SHORT).show();
        });


        LinearLayout cardShareQRCode = sheetLayout.findViewById(R.id.cardSheetShareQR);
        cardShareQRCode.setOnClickListener(v -> {
            QRCodeDialog.show(context, cardShareQRCode, a.getUrl());
        });


        LinearLayout cardSheetShareToOthers = sheetLayout.findViewById(R.id.cardSheetShareToOthers);
        cardSheetShareToOthers.setOnClickListener(v -> {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_TEXT, a.getUrl());
            share.setType("text/plain");
            context.startActivity(Intent.createChooser(share, "Chia sẻ với ứng dụng"));
        });


        //  Show up the BottomSheet Modal
        bottomSheetDialog.setContentView(sheetLayout);
        bottomSheetDialog.show();
    }

}
