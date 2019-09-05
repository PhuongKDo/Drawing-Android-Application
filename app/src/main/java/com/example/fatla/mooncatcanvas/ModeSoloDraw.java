package com.example.fatla.mooncatcanvas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.UUID;

import yuku.ambilwarna.AmbilWarnaDialog;

public class ModeSoloDraw extends AppCompatActivity implements View.OnClickListener {

    private ImageButton currpaint, drawbtn, baru, erase, save, cwheel, increase, decrease, imgView;
    private DrawingView drawView;
    private String currColor;
    public int pcolor =0xFF660000;

    private Button next, prev;
    ImageSwitcher imageSwitcher;
    Integer[] images = {R.drawable.image_8,R.drawable.image_1, R.drawable.image_2, R.drawable.image_3,
            R.drawable.image_4, R.drawable.image_5, R.drawable.image_6, R.drawable.image_7,
            R.drawable.image_8, R.drawable.image_9};
    int i = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        drawView = (DrawingView)findViewById(R.id.drawing);
        drawbtn = (ImageButton)findViewById(R.id.draw_btn);
        baru = (ImageButton)findViewById(R.id.new_btn);
        erase = (ImageButton)findViewById(R.id.erase_btn);
        save = (ImageButton)findViewById(R.id.save_btn);

        cwheel = (ImageButton)findViewById(R.id.cwheel_btn);
        increase = (ImageButton)findViewById(R.id.increase_btn);
        decrease = (ImageButton)findViewById(R.id.decrease_btn);

        next = (Button)findViewById(R.id.b_next);
        prev = (Button)findViewById(R.id.b_prev);

        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
        currpaint = (ImageButton)paintLayout.getChildAt(0);

        currpaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
        drawbtn.setOnClickListener(this);
        baru.setOnClickListener(this);
        erase.setOnClickListener(this);
        save.setOnClickListener(this);

        // increase decrease brush size
        increase.setOnClickListener(this);
        decrease.setOnClickListener(this);

        // launch color wheel
        ImageButton cwheel = (ImageButton)findViewById(R.id.cwheel_btn);
        cwheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        // image switcher
        next.setOnClickListener(this);
        prev.setOnClickListener(this);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i>0){
                    i--;
                    imageSwitcher.setImageResource(images[i]);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i < images.length - 1) {
                    i++;
                    imageSwitcher.setImageResource(images[i]);
                }
            }
        });

    }

    public void paintClicked(View view) {
        if (view != currpaint) {
            imgView = (ImageButton)view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            currColor = color;
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currpaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currpaint = (ImageButton)view;
        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.increase_btn) {
            drawView.increaseBrushSize();
        }
        if (view.getId() == R.id.decrease_btn) {
            drawView.decreaseBrushSize();
        }
        if (view.getId() == R.id.draw_btn) {
            drawView.setColor(currColor);
            drawView.setupDrawing();
        }
        if (view.getId() == R.id.erase_btn) {
            drawView.setColor("#ffffff");
        }
        if (view.getId() == R.id.new_btn) {
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("New Drawing");
            newDialog.setMessage("Start New Drawing");
            newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    drawView.startNew();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            newDialog.show();
        }
        if (view.getId() == R.id.save_btn) {
            AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
            saveDialog.setTitle("Save Drawing");
            saveDialog.setMessage("Save drawing to device gallery?");
            saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    drawView.setDrawingCacheEnabled(true);
                    String imgSaved = MediaStore.Images.Media.insertImage(getContentResolver(), drawView.getDrawingCache(), UUID.randomUUID().toString()+".png", "drawing");
                    if (imgSaved != null) {
                        Toast savedtoast = Toast.makeText(getApplicationContext(), "Drawing saved to Gallery", Toast.LENGTH_SHORT);
                        savedtoast.show();
                    } else {
                        Toast unsaved = Toast.makeText(getApplicationContext(),"Image could not saved", Toast.LENGTH_SHORT);
                        unsaved.show();
                    }
                    drawView.destroyDrawingCache();
                }
            });
            saveDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            saveDialog.show();
        }
    }
    //open color picker

    public void openColorPicker(){
        pcolor = drawView.getPaintColor();
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, pcolor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                pcolor = color;
                drawView.changeColor(color);
            }
        });
        colorPicker.show();
        Toast toast = Toast.makeText(getApplicationContext(), "Picking Colorwheel!", Toast.LENGTH_SHORT);
        toast.show();
    }
}
