package com.example.basic_vedroid_app;


import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import android.widget.CompoundButton;
import android.widget.Switch;

import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

// complited 1, 2.
// Now its 10



public class MainActivity extends AppCompatActivity {

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




        Button buttonlab5 = findViewById(R.id.lab5_btn);
        imageViewTheme = findViewById(R.id.imageViewTheme);
        switchTheme = findViewById(R.id.switchTheme);

        Button buttonlab6 = findViewById(R.id.lab6_btn);

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




    }
}
