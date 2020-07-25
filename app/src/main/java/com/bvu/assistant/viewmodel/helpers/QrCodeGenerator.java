package com.bvu.assistant.viewmodel.helpers;

import android.graphics.Bitmap;
import android.graphics.Color;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QrCodeGenerator {

    public static Bitmap getBitmap(String inputValue) {
        QRGEncoder qrgEncoder = new QRGEncoder(inputValue, null, QRGContents.Type.TEXT, 300);
        qrgEncoder.setColorBlack(Color.BLACK);
        qrgEncoder.setColorWhite(Color.WHITE);

        try {
            return qrgEncoder.getBitmap();
        } catch (Exception e) {
            return null;
        }
    }
}
