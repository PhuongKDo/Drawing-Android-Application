package com.example.fatla.mooncatcanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DrawingView extends View {

    private Path drawPath;
    private boolean erase = false;
    private Paint drawPaint, canvasPaint;
    private Canvas drawCanvas;
    private int paintColor=0xFF660000;
    private Bitmap canvasBitmap;
    private float brushSize, lastBrushSize;
    private Integer currentBrushSize = 5;

    private ArrayList<Path> mPaths;
    private ArrayList<Path> undonePaths = new ArrayList<Path>();
    private ArrayList<Paint> mPaints;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }
    public void startNew() {
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        setupDrawing();
        invalidate();
    }
    public void setErase(boolean isErase) {
        erase = isErase;
        if (erase) drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        else drawPaint.setXfermode(null);
    }
    public void setBrushSize(float newSize) {
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newSize, getResources().getDisplayMetrics());
        brushSize = pixelAmount;
        drawPaint.setStrokeWidth(brushSize);
    }
    public void  setLastBrushSize(float lastSize){
        lastBrushSize = lastSize;
    }
    public float getBrushSize() {
        return lastBrushSize;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
//        canvas.drawPath(drawPath, drawPaint);

        for (int i = 0; i < mPaths.size(); ++i)
            canvas.drawPath(mPaths.get(i), mPaints.get(i));
    }
    private void addPath(boolean fill)
    {
        drawPath = new Path();
        mPaths.add(drawPath);

        drawPaint = new Paint();
        mPaints.add(drawPaint);

        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(currentBrushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    public void onClickUndo() {
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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.addPath(true);
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                this.addPath(true);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void increaseBrushSize() {
        drawPaint.setStrokeWidth(currentBrushSize++);
    }

    public void decreaseBrushSize() {
        drawPaint.setStrokeWidth(currentBrushSize--);
    }
    public int getPaintColor() {
      return paintColor;
    };
    public void changeColor (int cwheel_color) {
        drawPaint.setColor(cwheel_color);
    }
    public void setColor(String newColor) {
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }
    public void setupDrawing(){
        this.mPaths = new ArrayList<Path>();
        this.mPaints = new ArrayList<Paint>();

        this.addPath(false);

        drawPaint = new Paint();
        drawPath = new Path();

        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(currentBrushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);

        brushSize = 10;
        lastBrushSize = brushSize;
        drawPaint.setStrokeWidth(brushSize);
    }
}
