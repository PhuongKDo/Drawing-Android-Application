package com.example.fatla.mooncatcanvas;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ModePicker extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_picker);

        RelativeLayout lay = findViewById(R.id.layout);
        AnimationDrawable anim = (AnimationDrawable) lay.getBackground();
        anim.setEnterFadeDuration(2000);
        anim.setExitFadeDuration(4000);
        anim.start();

        Button bP = (Button)findViewById(R.id.practice);
        Button bV = (Button)findViewById(R.id.view);
        Button bA = (Button)findViewById(R.id.draw);
        //listener
        bV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Mview = new Intent(ModePicker.this, ModeView.class);
                startActivity(Mview);
            }});
        bP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Mpract = new Intent(ModePicker.this, ModePractice.class);
                startActivity(Mpract);
            }
        });
        bA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Mpract = new Intent(ModePicker.this, ModeDrawAlong.class);
                startActivity(Mpract);
            }
        });

    }
}
