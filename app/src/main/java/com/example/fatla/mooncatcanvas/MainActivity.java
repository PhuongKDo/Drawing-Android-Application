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

        //listener

        btnImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Mpicker= new Intent(MainActivity.this, List.class);
                startActivity(Mpicker);
            }
        });

        btnImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Mpicker= new Intent(MainActivity.this, List.class);
                startActivity(Mpicker);
            }
        });
    }
}
