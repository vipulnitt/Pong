package com.example.pong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
   private Button button1,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 =findViewById(R.id.button);
        button2 =findViewById(R.id.button2);
      button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normalmode();
            }
        });
       button2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ComputerMode();
           }
       });
    }
public void normalmode()
    {
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
    }
    public void ComputerMode()
    {
        Intent intents = new Intent(this,MainActivity3.class);
        startActivity(intents);
    }




}