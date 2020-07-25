package com.bvu.assistant.view.customview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bvu.assistant.R;
import com.bvu.assistant.viewmodel.helpers.QrCodeGenerator;

public class QRCodeDialog {

    public static void show (Context context, ViewGroup root, String content) {
        View qrCodeLayout = LayoutInflater.from(context).inflate(R.layout.qrcode_layout, root, false);
        ImageView imvQRCode = qrCodeLayout.findViewById(R.id.imvQRCode);
        imvQRCode.setImageBitmap(QrCodeGenerator.getBitmap(content));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(qrCodeLayout);

        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
        dialog.show();
    }

}
