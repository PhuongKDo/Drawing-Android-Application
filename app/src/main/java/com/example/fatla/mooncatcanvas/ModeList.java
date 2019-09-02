package com.example.fatla.mooncatcanvas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class ModeList extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_list);

        ImageView btnList1 = (ImageView)findViewById(R.id.list1);
        ImageView btnList2 = (ImageView)findViewById(R.id.list2);

        btnList1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Mview = new Intent(ModeList.this, ModePicker.class);
                startActivity(Mview);
            }
        });

        btnList2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Mview = new Intent(ModeList.this, ModePicker.class);
                startActivity(Mview);
            }
        });
    }
}
