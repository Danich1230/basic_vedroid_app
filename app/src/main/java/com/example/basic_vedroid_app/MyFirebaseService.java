package com.example.basic_vedroid_app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;



// я знаю что это бред списанный из google и адоптированный под мой код

public class MyFirebaseService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseService";
    private static final String CHANNEL_ID = "default_channel";

    @Override
    public void onNewToken(@NonNull String token) {
        String msg = getString(R.string.msg_token_fmt, token);
        Log.d(TAG, msg);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d(TAG, "Message received: " + remoteMessage);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                1410,
                intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Default Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(remoteMessage.getNotification() != null
                        ? remoteMessage.getNotification().getTitle()
                        : "Message")
                .setContentText(remoteMessage.getNotification() != null
                        ? remoteMessage.getNotification().getBody()
                        : "You have a new message")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        notificationManager.notify(1410, notificationBuilder.build());
    }
}
