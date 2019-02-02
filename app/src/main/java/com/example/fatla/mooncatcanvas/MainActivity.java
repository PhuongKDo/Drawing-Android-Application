package com.example.fatla.mooncatcanvas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bP = (Button)findViewById(R.id.button);
        Button bV = (Button)findViewById(R.id.button2);
        Button bA = (Button)findViewById(R.id.button3);
        //listener
        bV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Mview = new Intent(MainActivity.this, ModeView.class);
                startActivity(Mview);
            }});
        bP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Mpract = new Intent(MainActivity.this, ModePractice.class);
                startActivity(Mpract);
            }
        });
        bA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Mpract = new Intent(MainActivity.this, ModeDrawAlong.class);
                startActivity(Mpract);
            }
        });

    }
}
