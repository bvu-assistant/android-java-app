package com.bvu.assistant.system.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.bvu.assistant.R;
import com.bvu.assistant.data.repository.article.Article;
import com.bvu.assistant.ui.main.news.details.NewsDetailActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.Map;


public class NewsListenerService extends Service {
    private static final String TAG = "NewsListenerService";
    private final String CHANNEL_ID = "NewsListenerService";
    FirebaseFirestore db;
    ListenerRegistration listener;


    public NewsListenerService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = FirebaseFirestore.getInstance();
        Toast.makeText(this, "News listener service starting", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        listenForCloudDataChange();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "News Listener Service being killed", Toast.LENGTH_LONG).show();
        listener.remove();
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private void listenForCloudDataChange() {
        Toast.makeText(this, "Listening for Data changes", Toast.LENGTH_SHORT).show();
        final CollectionReference collRef = db.collection("news/details/Trang Chủ ");
        final String ARTICLE_TYPE = "Trang Chủ ";


        listener = collRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w(TAG, "Listen for Cloud Data (Headlines) failed.", error);
                return;
            }


            String source = value != null && value.getMetadata().hasPendingWrites()
                    ? "Local" : "Server";

            if (value != null) {
                if (value.getDocumentChanges().size() != 1) {
                    return;
                }

                Log.d(TAG, "Processing changes...");
                DocumentChange firstChange = value.getDocumentChanges().get(0);
                DocumentChange.Type changedType = firstChange.getType();
                Log.d(TAG, changedType + " data: [" + source + "] " + firstChange.getDocument().getData());

                if (changedType == DocumentChange.Type.ADDED) {
                    Map<String, Object> record = firstChange.getDocument().getData();
                    Article item = new Article(
                            ARTICLE_TYPE,
                            record.get("Title").toString(),
                            record.get("Date").toString(),
                            record.get("Link").toString(),
                            (Boolean)record.get("IsNew"),
                            false
                    );

                    sendNotification(item);
                }


                if (changedType == DocumentChange.Type.REMOVED) {
                    Map<String, Object> record = firstChange.getDocument().getData();
                    Article item = new Article(
                        ARTICLE_TYPE,
                        record.get("Title").toString(),
                        record.get("Date").toString(),
                        record.get("Link").toString(),
                        (Boolean)record.get("IsNew"),
                        false
                    );
                }
            } else {
                Log.d(TAG, "\n\n" + source + " data: null");
            }
        });
    }


    private void sendNotification(Article a) {
        Context mContext = this;


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext.getApplicationContext(), CHANNEL_ID);
        Intent intent = new Intent(mContext, NewsDetailActivity.class);
        intent.putExtra("article", a);

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        Log.i(TAG, "sendNotification: " + a.getId());


        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Thông báo mới");
        mBuilder.setContentText(a.getTitle());
        mBuilder.setPriority(Notification.PRIORITY_MAX);



        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH
            );
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(CHANNEL_ID);
        }


        mNotificationManager.notify(0, mBuilder.build());
    }

}
