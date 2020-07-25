package com.bvu.assistant.view.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bvu.assistant.R;
import com.bvu.assistant.view.customview.QRCodeDialog;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.apache.commons.io.FilenameUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Timer;


public class OfficeViewerActivity extends AppCompatActivity {
    private static final String TAG = "OfficeViewerActivity";
    WebView web_view;
    SwipeRefreshLayout refresher;
    MaterialToolbar actionBar;
    String viewUrl;
    ImageButton shareBtn;
    ImageButton reloadBtn;
    View progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_viewer);

        initAndMapping();

        Intent receivedIntent = getIntent();
        String documentUrl = receivedIntent.getStringExtra("documentUrl");
        viewUrl = getDocumentViewerUrl(documentUrl);

        loadWebView(viewUrl);
    }


    private void initAndMapping() {
        shareBtn = findViewById(R.id.officeShareBtn);
        handleShareButtonClick(this, shareBtn);

        progressBar = findViewById(R.id.officeViewerProgressBar);
        progressBar.setScaleX(0f);
        progressBar.setPivotX(0f);

        web_view = findViewById(R.id.web_view);
        web_view.requestFocus();
        web_view.getSettings().setJavaScriptEnabled(true);


        refresher = findViewById(R.id.officeViewerRefresher);
        refresher.setEnabled(false);
        refresher.setColorSchemeColors(
            Color.rgb(0, 138, 230),
            Color.rgb(25, 189, 0),
            Color.rgb(255, 204, 0),
            Color.rgb(245, 0, 53));
        refresher.setOnRefreshListener(()-> web_view.reload());


        actionBar = findViewById(R.id.officeViewerActionBar);
        actionBar.setNavigationOnClickListener(view -> {
            finish();
        });


        reloadBtn = findViewById(R.id.officeViewerReloadBtn);
        reloadBtn.setOnClickListener(v -> web_view.reload());
    }


    private void loadWebView(String viewUrl) {
        web_view.loadUrl(viewUrl);


        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(OfficeViewerActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (view.getTitle() == null || view.getTitle().isEmpty()) {
                    Toast.makeText(OfficeViewerActivity.this, "Trying to reload the page...", Toast.LENGTH_SHORT).show();
                    view.reload();
                    return;
                }

                hideOpenInDriveButton();
                refresher.setRefreshing(false);
            }
        });


        web_view.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    refresher.setRefreshing(true);
//                    progressBar.animate().setDuration(750).scaleX(progress / 100f).start();
                    return;
                }

//                progressBar.animate().setDuration(250).scaleX(1f).start();
//                progressBar.animate().setDuration(750).alpha(0).setStartDelay(3000).start();
//                progressBar.animate().setDuration(250).scaleX(0f).setStartDelay(3000).start();
            }
        });
    }

    private String getDocumentViewerUrl(String documentUrl) {
        String ms = "https://view.officeapps.live.com/op/view.aspx?src=";
        String gg = "https://docs.google.com/viewer?embedded=true&url=";

        String provider = "";
        String ext = FilenameUtils.getExtension(documentUrl);

        switch (ext) {
            case "pdf": {
                provider = gg;
                break;
            }

            case "doc": case "docx": case "xls": case "xlsx": case "ppt": case "pptx": {
                provider = ms;
                break;
            }

            default: {
                provider = "";
                Toast.makeText(this, "File extension invalid", Toast.LENGTH_LONG).show();
                break;
            }
        }

        return provider + documentUrl;
    }

    private void hideOpenInDriveButton() {
        String script = "document.querySelector('[role=toolbar]').style.display = \"none\"";
        web_view.evaluateJavascript(script, value -> {
            Log.i(TAG, "hideOpenInDriveButton: " + value);
        });
    }

    private void handleShareButtonClick(Context context, ImageButton btn) {
        btn.setOnClickListener(view -> {
            View sheetLayout = LayoutInflater.from(context).inflate(R.layout.news_bottom_sheet, null);
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);


            //  Assign the header for BottomSheet
            TextView txtCardSheetUrl = sheetLayout.findViewById(R.id.cardSheetLink);
            txtCardSheetUrl.setText(viewUrl);


            LinearLayout cardSheetCopyUrl = sheetLayout.findViewById(R.id.cardSheetCopyUrl);
            cardSheetCopyUrl.setOnClickListener(v -> {
                bottomSheetDialog.dismiss();

                ClipboardManager clipboardManager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText("url", viewUrl));

                Toast.makeText(context, "Đã sao chép liên kết vào bộ nhớ tạm", Toast.LENGTH_SHORT).show();
            });


            LinearLayout cardShareQRCode = sheetLayout.findViewById(R.id.cardSheetShareQR);
            cardShareQRCode.setOnClickListener(v -> {
                QRCodeDialog.show(context, cardShareQRCode, viewUrl);
            });


            LinearLayout cardSheetShareToOthers = sheetLayout.findViewById(R.id.cardSheetShareToOthers);
            cardSheetShareToOthers.setOnClickListener(v -> {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_TEXT, viewUrl);
                share.setType("text/plain");
                context.startActivity(Intent.createChooser(share, "Chia sẻ với ứng dụng"));
            });


            //  Show up the BottomSheet Modal
            bottomSheetDialog.setContentView(sheetLayout);
            bottomSheetDialog.show();
        });
    }
}
