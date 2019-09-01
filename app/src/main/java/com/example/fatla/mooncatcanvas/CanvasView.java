package com.example.fatla.mooncatcanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CanvasView extends View implements View.OnTouchListener {
    private View main;
    private ImageView imageView;

    //setting for the dot size
    private final int DEFAULT_DOT_SIZE = 6;
    private final int MAX_DOT_SIZE =100;
    private final int MIN_DOT_SIZE = 2;
    private int mDotSize;
    int radius = 1;

    //color
    private int mPenColor;
    private int mPenColorBackup = Color.BLACK;

    private TextView tv_dotSize;

    //paths for size of the brush
    private ArrayList<Path> mPaths;
    private ArrayList<Paint> mPaints;

    //undo paths
    private ArrayList<Path> undonePaths = new ArrayList<Path>();

    // canvas details
    public int width;
    public int height;
    public Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private float mX, mY, mOldX, mOldY;
    private static final float TOLERANCE = 5;
    public int DEFAULT_COLOR = Color.BLACK;

    Context context;

    //canvas details//----------------------------------- start
    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.init();
    }

    private void init()
    {
        this.mDotSize = DEFAULT_DOT_SIZE;
        this.mPenColor = DEFAULT_COLOR;
        this.mPaints = new ArrayList<Paint>();
        this.mPaths = new ArrayList<Path>();
        this.mPath = new Path();
        //this.addPath();
        this.addPath(false);
        this.mX = this.mY = this.mOldX = this.mOldY = (float)0.0;
        this.setOnTouchListener(this);
    }

    private void addPath(boolean fill)
    {
        mPath = new Path();
        mPaths.add(mPath);
        mPaint = new Paint();
        mPaints.add(mPaint);
        mPaint.setColor(mPenColor);
        if(!fill)
            mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mDotSize);

        //mPaint.setDither(true);
       // mPaint.setAntiAlias(true);
       // mPaint.setStrokeJoin(Paint.Join.ROUND);
        //mPaint.setStrokeCap(Paint.Cap.ROUND);
    }
//////////////////////////canvas details//----------------------------------- end
    //brush
    public void defaultBrush(){
        mPenColor = mPenColorBackup;
        mPaint.setColor(mPenColor);
    }

    //update color
    public void changeColor(int color){
        this.mPenColor = color;
        this.mPenColorBackup = color;
        this.mPaint.setColor(mPenColor);
    }

    //erase
    public void eraseLine(){
        mPenColor = Color.WHITE;
        mPaint.setColor(mPenColor);
    }

    //change the size of the brush
    public void changeDotSize(int increment) {
        this.mDotSize += increment;
        this.mDotSize = Math.max(mDotSize, MIN_DOT_SIZE);
        this.mDotSize = Math.min(mDotSize, MAX_DOT_SIZE);
    }
    // when you click undo


    public void onClickUndo() {
        if (mPaths.size() > 0) {
            undonePaths.add(mPaths.remove(mPaths.size() - 1));
            invalidate();
        }
        else{}
        if (mPaths.size() > 0) {
            undonePaths.add(mPaths.remove(mPaths.size() - 1));
            invalidate();
        }
        else{}
        if (mPaths.size() > 0) {
            undonePaths.add(mPaths.remove(mPaths.size() - 1));
            invalidate();
        }
        else{}
    }
    // when you click redo
    public void onClickRedo() {
        if (undonePaths.size() > 0) {
            mPaths.add(undonePaths.remove(undonePaths.size() - 1));
            invalidate();
        }
        else{}
        if (undonePaths.size() > 0) {
            mPaths.add(undonePaths.remove(undonePaths.size() - 1));
            invalidate();
        }
        else{}
        if (undonePaths.size() > 0) {
            mPaths.add(undonePaths.remove(undonePaths.size() - 1));
            invalidate();
        }
        else{}
    }

    //get the size of the dot
    public int getDotSize() {
        return mDotSize;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        for (int i = 0; i < mPaths.size(); ++i)
            canvas.drawPath(mPaths.get(i), mPaints.get(i));
        // canvas.drawPath(mPath, mPaint);

    }

    private void startTouch (float x, float y){
        mPath.moveTo(x,y);
        mX = x;
        mY = y;
    }

    private void moveTouch(float x, float y){
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if(dx >= TOLERANCE || dy >= TOLERANCE){
            mPath.quadTo(mX,mY,(x+mX)/2, (y+mY)/2);
            mX = x;
            mY = y;
        }
    }

    public void clearCanvas(){
        // mPath.reset();
        this.init();
        invalidate();
    }

    private void upTouch(){
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mX = motionEvent.getX();
        mY = motionEvent.getY();

        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.addPath(true);
                this.mPath.addCircle(mX, mY, mDotSize/2, Path.Direction.CW);
                this.addPath(false);
                this.mPath.moveTo(mX,mY);
                //initPaint();
                //invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                this.mPath.lineTo(mX,mY);
                //moveTouch(x,y);
                //invalidate();
                break;
            case MotionEvent.ACTION_UP:
                this.addPath(true);
                if (mOldX == mX && mOldY == mY)
                    this.mPath.addCircle(mX, mY, mDotSize/2, Path.Direction.CW);
                //upTouch();
                //invalidate();
                break;

        }
        this.invalidate();
        mOldX = mX;
        mOldY = mY;
        return true;
    }



}
