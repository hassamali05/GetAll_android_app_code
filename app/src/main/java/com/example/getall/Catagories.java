package com.example.getall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Catagories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagories);

        Button ib1 = (Button)findViewById(R.id.m);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Catagories.this,Mens.class);
                startActivity(i);
            }
        });

        Button ib2 = (Button)findViewById(R.id.v);
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Catagories.this,Vehicals.class);
                startActivity(i);
            }
        });
    }
}
