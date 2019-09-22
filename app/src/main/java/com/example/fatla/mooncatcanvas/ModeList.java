package com.example.fatla.mooncatcanvas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import static java.security.AccessController.getContext;

public class ModeList extends AppCompatActivity{
    public String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_list);

        ImageView btnList1 = (ImageView)findViewById(R.id.list1);
        ImageView btnList2 = (ImageView)findViewById(R.id.list2);

        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        String test = "s";
        if (extras != null) {
            category = intent.getStringExtra("title");
            test = category;
        }

        if (test.equals("I love Programming And Design")) {
//            Intent Mview = new Intent(ModeList.this, ModePractice.class);
//            startActivity(Mview);
        } else {
//            Intent Mview = new Intent(ModeList.this, ModeSoloDraw.class);
//            startActivity(Mview);
        }
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
