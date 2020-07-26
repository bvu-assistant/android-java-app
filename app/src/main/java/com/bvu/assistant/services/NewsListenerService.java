package com.bvu.assistant.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.bvu.assistant.R;
import com.bvu.assistant.model.Article.Article;
import com.bvu.assistant.view.activities.MainActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;



public class NewsListenerService extends Service {
    private static final String TAG = "NewsListenerService";
    private final String CHANNEL_ID = "NewsListenerService";
    FirebaseFirestore db;

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
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private void listenForCloudDataChange() {
        Toast.makeText(this, "Listening for Data changes", Toast.LENGTH_SHORT).show();
        final CollectionReference collRef = db.collection("news/details/Trang Chủ ");

        collRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w(TAG, "Listen for Cloud Data (Headlines) failed.", error);
                return;
            }


            String source = value != null && value.getMetadata().hasPendingWrites()
                    ? "Local" : "Server";

            if (value != null) {
                if (value.getDocumentChanges().size() > 1) {
                    return;
                }

                Log.d(TAG, "Processing changes...");
                DocumentChange firstChange = value.getDocumentChanges().get(0);
                DocumentChange.Type changedType = firstChange.getType();
                Log.d(TAG, changedType + " data: " + firstChange.getDocument().getData());

                if (changedType == DocumentChange.Type.ADDED) {
                    Map<String, Object> record = firstChange.getDocument().getData();
                    Article item = new Article(
                            "Headlines",
                            record.get("Title").toString(),
                            record.get("Date").toString(),
                            record.get("Link").toString(),
                            (Boolean)record.get("IsNew"),
                            false
                    );


                    Toast.makeText(NewsListenerService.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                    sendNotification("New data added to Headlines...");

                    // Intent intent = new Intent(ON_NEW_HEADLINES);
                    // intent.putExtra("articleTitle", item.getTitle());
                    // sendBroadcast(intent);
                }


                if (changedType == DocumentChange.Type.REMOVED) {
                    Map<String, Object> record = firstChange.getDocument().getData();
                    Article item = new Article(
                            "Headlines",
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


    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("BVU Assistant")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

}
