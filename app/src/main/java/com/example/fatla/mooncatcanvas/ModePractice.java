package com.example.fatla.mooncatcanvas;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.graphics.Canvas;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import android.widget.ZoomControls;

import yuku.ambilwarna.AmbilWarnaDialog;

public class ModePractice extends AppCompatActivity implements View.OnClickListener{


    ////////////////////////

    Button b_prev, b_next;

    ImageSwitcher imageSwitcher;

    Integer[] images = {R.drawable.image_8,R.drawable.image_1, R.drawable.image_2, R.drawable.image_3,
            R.drawable.image_4, R.drawable.image_5, R.drawable.image_6, R.drawable.image_7,
            R.drawable.image_8, R.drawable.image_9};

    int i = 0;

    //////////////////////

    private Button bMinus, bPlus, undo2, clear2, redo2, colorPicker2, erase2, brush2;
    private TextView tv_dotSize;
    private static final int DOT_SIZE_INCREMENT = 1;
    private CanvasView canvasView;

    //colorpicker default color
    int mDefaultColor;

    private int count;

    private Button save2;

    String ImagePath;

    ZoomControls zoomIt;


    //////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_practice);
        //----decrease brush-----//
        bMinus = (Button) findViewById(R.id.minus);
        bMinus.setOnClickListener(this);

        //----increase brush-----//
        bPlus = (Button) findViewById(R.id.plus);
        bPlus.setOnClickListener(this);

        //canvas view
        canvasView = (CanvasView) findViewById(R.id.canvas);

        //-----clear canvas----//
        clear2 = (Button) findViewById(R.id.clear);
        clear2.setOnClickListener(this);

        //---undo-----//
        undo2 = (Button) findViewById(R.id.undo);
        undo2.setOnClickListener(this);

        //---erase---//
        erase2 = (Button) findViewById(R.id.erase);
        erase2.setOnClickListener(this);

        //-----redo-----///
        redo2 = (Button) findViewById(R.id.redo);
        redo2.setOnClickListener(this);

        //-----brush----///
        brush2 = (Button) findViewById(R.id.brush);
        brush2.setOnClickListener(this);

        //show current size of the brush
        tv_dotSize = (TextView) findViewById(R.id.dotSize_tv);
        tv_dotSize.setText("○" + canvasView.getDotSize());


        //---colorPicker button---//
        colorPicker2 = (Button) findViewById(R.id.colorPicker);
        colorPicker2.setOnClickListener(this);

        //---default color---//
        mDefaultColor = ContextCompat.getColor(ModePractice.this, R.color.colorPrimary);

        //---zoom control--//
        zoomIt = (ZoomControls) findViewById(R.id.zoomControls);

        //////////////////////////////////////////////////////////////////

        zoomIt.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float x = imageSwitcher.getScaleX();
                float y = imageSwitcher.getScaleY();

                imageSwitcher.setScaleX((int) (x+1));
                imageSwitcher.setScaleY((int) (y+1));

                float x2 = canvasView.getScaleX();
                float y2 = canvasView.getScaleY();

                canvasView.setScaleX((int) (x2+1));
                canvasView.setScaleY((int) (y2+1));
            }
        });

        zoomIt.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float x = imageSwitcher.getScaleX();
                float y = imageSwitcher.getScaleY();

                imageSwitcher.setScaleX((int) (x-1));
                imageSwitcher.setScaleY((int) (y-1));

                float x2 = canvasView.getScaleX();
                float y2 = canvasView.getScaleY();

                canvasView.setScaleX((int) (x2-1));
                canvasView.setScaleY((int) (y2-1));
            }
        });

        ////////////////////////////////////////////////////////////////////////
        //image switcher
        ////////////////////////////////////////////////////////////////////////

        //setup image switcher
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory(){
            public View makeView(){
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setLayoutParams(
                        new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });


        //onclick prev/next, image switches
        b_prev = (Button) findViewById(R.id.b_prev);
        b_next = (Button) findViewById(R.id.b_next);

        b_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i>0){
                    i--;
                    imageSwitcher.setImageResource(images[i]);
                }
            }
        });
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i < images.length - 1) {
                    i++;
                    imageSwitcher.setImageResource(images[i]);
                }
            }
        });

    }

    ////////////////////////////////////////////////////////////////////////
    ////-----------------------clicking area start
    ////////////////////////////////////////////////////////////////////////


    //canvas tool: colorpicker, redo, clear, undo,
    @Override
    public void onClick(View view) {
        Button _b = (Button) findViewById(view.getId());
        switch (view.getId()){
            case R.id.colorPicker: openColorPicker();
                break;
            case R.id.redo: canvasView.onClickRedo();
                break;
            case R.id.clear: canvasView.clearCanvas();
                tv_dotSize.setText("○ " + canvasView.getDotSize());
                break;
            case R.id.undo: canvasView.onClickUndo();
                count++;
                if (count == 3){
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Keep trying!", Toast.LENGTH_SHORT);
                    toast.show();}
                tv_dotSize.setText("○ " + canvasView.getDotSize());
                break;
            case R.id.plus: canvasView.changeDotSize(+ DOT_SIZE_INCREMENT);
                tv_dotSize.setText("○ " + canvasView.getDotSize());
                break;
            case R.id.minus: canvasView.changeDotSize(- DOT_SIZE_INCREMENT);
                tv_dotSize.setText("○ " + canvasView.getDotSize());
                break;
            case R.id.erase: canvasView.eraseLine();
                break;
            case R.id.brush: canvasView.defaultBrush();
                break;
        }
    }
    ////////////////////////////////////////////////////////////////////////
    ///                     clicking area end
    ////////////////////////////////////////////////////////////////////////


    //open color picker
    public void openColorPicker(){
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
                canvasView.changeColor(color);
            }
        });
        colorPicker.show();
    }
}
