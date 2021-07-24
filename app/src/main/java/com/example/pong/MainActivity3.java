package com.example.pong;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pong.views.CustomView2;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CustomView2(this));
    }
    @Override
    public void onBackPressed() {
        System.exit(0);
    }
}