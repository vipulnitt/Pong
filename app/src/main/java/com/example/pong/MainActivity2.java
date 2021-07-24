package com.example.pong;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pong.views.CustomView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CustomView(this));
    }
    @Override
    public void onBackPressed() {
        System.exit(0);
    }
}