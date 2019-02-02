package com.example.fatla.mooncatcanvas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.github.chrisbanes.photoview.PhotoView;

public class ModeView extends AppCompatActivity {
    Button b_prevV2, b_nextV2;


    ImageSwitcher imageSwitcherV2;

    Integer[] images2 = {R.drawable.image_8,R.drawable.image_1, R.drawable.image_2, R.drawable.image_3,
            R.drawable.image_4, R.drawable.image_5, R.drawable.image_6, R.drawable.image_7,
            R.drawable.image_8};

    int i = 0;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_view);

        b_prevV2 = (Button) findViewById(R.id.b_prevV);
        b_nextV2 = (Button) findViewById(R.id.b_nextV);

        b_prevV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 0) {
                    i--;
                    PhotoView photoView = (PhotoView) findViewById(R.id.photoViewV);
                    photoView.setImageResource(images2[i]);
                    //imageSwitcher.setImageResource(images[i]);
                }
            }
        });
        b_nextV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i < images2.length - 1) {
                    i++;
                    PhotoView photoView = (PhotoView) findViewById(R.id.photoViewV);
                    photoView.setImageResource(images2[i]);
                    //imageSwitcher.setImageResource(images[i]);
                }
            }
        });
    }
}