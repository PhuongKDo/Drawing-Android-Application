package com.example.fatla.mooncatcanvas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView btnImage1 = (ImageView)findViewById(R.id.image1);
        ImageView btnImage2 = (ImageView)findViewById(R.id.image2);
        ImageView btnImage3 = (ImageView) findViewById(R.id.image3);
        ImageView btnImage4 = (ImageView)findViewById(R.id.image4);
        ImageView btnImage5 = (ImageView)findViewById(R.id.image5);
        ImageView btnImage6 = (ImageView)findViewById(R.id.image6);

        //listener

        btnImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Mpicker= new Intent(MainActivity.this, ModePicker.class);
                startActivity(Mpicker);
            }
        });

        btnImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Mpicker= new Intent(MainActivity.this, ModePicker.class);
                startActivity(Mpicker);
            }
        });

        btnImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Mpicker= new Intent(MainActivity.this, ModePicker.class);
                startActivity(Mpicker);
            }
        });

        btnImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Mpicker= new Intent(MainActivity.this, ModePicker.class);
                startActivity(Mpicker);
            }
        });

        btnImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Mpicker= new Intent(MainActivity.this, ModePicker.class);
                startActivity(Mpicker);
            }
        });

        btnImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Mpicker= new Intent(MainActivity.this, ModePicker.class);
                startActivity(Mpicker);
            }
        });

        btnImage6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Mpicker= new Intent(MainActivity.this, ModePicker.class);
                startActivity(Mpicker);
            }
        });
    }
}
