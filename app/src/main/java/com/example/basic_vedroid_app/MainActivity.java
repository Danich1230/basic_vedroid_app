package com.example.basic_vedroid_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.SharedPreferences;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;

import android.content.Intent;
import android.widget.Switch;

import android.widget.ImageView;

import android.widget.TextView;

import android.view.MenuItem;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.content.Context;
import android.content.ContentResolver;

import android.media.AudioAttributes;

import android.net.Uri;

import android.os.Build;
import android.widget.Toast;

import org.jspecify.annotations.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

// complited 1, 2, 10
// я просто их не записал сюда в любом случаае я их показывал


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyFirebaseService";
    private static final String CHANNEL_ID = "default_channel";



    //public static String text2remember;
    private Switch switchTheme;
    private ImageView imageViewTheme;




    @Override
    protected void onCreate(Bundle savedInstanceState) {



        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        boolean nightMode = prefs.getBoolean("night_mode", false);

        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



/*
        int id = item.getItemId();
        if (id == R.id.show_text){
            if (item.isChecked()){
                TextView lab9TextView = findViewById(R.id.lab9TextView);
                lab9TextView.setVisibility(TextView.VISIBLE);
                item.setChecked(false);
            }
            else {
                TextView lab9TextView = findViewById(R.id.lab9TextView);
                lab9TextView.setVisibility(TextView.INVISIBLE);
                item.setChecked(true);
            }
        }
*/



        Button buttonlab5 = findViewById(R.id.lab5_btn);
        imageViewTheme = findViewById(R.id.imageViewTheme);
        switchTheme = findViewById(R.id.switchTheme);

        // ЛАБОРАТОРНАЯ 16 ЭТО АД ОНО ПРИНИМАЕТ ТОКЕН НО НЕ РАБОТАЕТ (РАБОТАЕТ)

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        Button buttonlab6 = findViewById(R.id.lab6_btn);

        //lab7 isnt fucking work

        Button buttonlab11 = findViewById(R.id.lab11_btn);

        Button button12 = findViewById(R.id.lab12_btn);
        Button button13 = findViewById(R.id.lab13_btn);
        Button button14 = findViewById(R.id.lab14_btn);
        Button button15 = findViewById(R.id.lab15_btn);
        Button button16 = findViewById(R.id.lab16_btn);

        int currentNightMode = getResources().getConfiguration().uiMode &
                android.content.res.Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            switchTheme.setChecked(true);
            imageViewTheme.setImageResource(R.drawable.confusion_darktheme);
        } else {
            switchTheme.setChecked(false);
            imageViewTheme.setImageResource(R.drawable.confusion_daytheme);
        }

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {

            SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
            editor.putBoolean("night_mode", isChecked);
            editor.apply();

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            imageViewTheme.setImageResource(isChecked ?
                    R.drawable.confusion_darktheme : R.drawable.confusion_daytheme);
        });

        buttonlab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, lab5Act1.class);
                startActivity(intent);
            }
        });

        buttonlab6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, lab6Act1.class);
                startActivity(intent);
            }
        });

        buttonlab11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 11 лабораторная работа

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
                        return;
                    }
                }

                Context context = MainActivity.this;
                NotificationChannel newnotchan= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    newnotchan= new
                            NotificationChannel("mychannel1","mychannel",NotificationManager.IMPORTANCE_HIGH);
                    AudioAttributes audioAttributes = new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build();
                    newnotchan.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://"+getPackageName()+"/"+R.raw.hackbuzzer), audioAttributes);
                }
                NotificationManager notificationManager = (NotificationManager)
                        getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationManager.createNotificationChannel(newnotchan);
                }
                Notification notification = null;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    notification = new Notification.Builder(context,"mychannel1")
                            .setContentTitle("OwO")
                            .setContentText("Hey, listen!")
                            .setTicker("new notification!")
                            .setChannelId("mychannel1")
                            .setSmallIcon(android.R.drawable.ic_dialog_alert)
                            .setOngoing(true)
                            .build();
                }

                notificationManager.notify(0, notification);
            }
        });

        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.hackbuzzer);
                mediaPlayer.start();

            }
        });

        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, lab13Act.class);
                startActivity(intent);

            }
        });

        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, lab14Act.class);
                startActivity(intent);

            }
        });

        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(task -> {
                            if (!task.isSuccessful()) {
                                Log.w("App", "Fetching FCM token failed", task.getException());
                                Toast.makeText(MainActivity.this,
                                        "Failed to get token",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }


                            String tkn = task.getResult();
                            Toast.makeText(MainActivity.this,
                                    "Current token [" + tkn + "]",
                                    Toast.LENGTH_LONG).show();
                            Log.d("App", "Token [" + tkn + "]");
                        });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.show_text) {

            TextView textView = findViewById(R.id.lab9TextView);

            if (item.isChecked()) {
                textView.setVisibility(View.VISIBLE);
                item.setChecked(false);
            } else {
                textView.setVisibility(View.INVISIBLE);
                item.setChecked(true);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}