package com.example.fatla.mooncatcanvas;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;

import android.provider.MediaStore.Images;
import android.widget.Toast;

public class DrawingView extends View {

    private Path drawPath;
    private boolean erase = false;
    private Paint drawPaint, canvasPaint;
    private Canvas drawCanvas;
    private int paintColor=0xFF660000;
    private Bitmap canvasBitmap;
    private float brushSize, lastBrushSize;
    private Integer currentBrushSize = 5;
    private DrawingView drawView;
    private ArrayList<Path> mPaths;
    private ArrayList<Path> undonePaths = new ArrayList<Path>();
    private ArrayList<Paint> undonePaints = new ArrayList<Paint>();
    private ArrayList<Paint> mPaints;

    private ModeSoloDraw ModeSoloDrawView;
    public boolean flagline = false;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

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

        for (int i = 0; i < mPaths.size(); ++i) {
            canvas.drawPath(mPaths.get(i), mPaints.get(i));
            invalidate();
        }
    }
    private void addPath(boolean fill)
    {
        drawPath = new Path();
        mPaths.add(drawPath);

        drawPaint = new Paint();
        mPaints.add(drawPaint);
        System.out.print(paintColor);
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(currentBrushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        invalidate();

    }

    public void onClickUndo() {
        if (mPaths.size() > 0) {
        undonePaths.add(mPaths.remove(mPaths.size() - 1));
        undonePaints.add(mPaints.remove(mPaints.size() - 1));
            invalidate();
        } else {}
        if (mPaths.size() > 0) {
            undonePaths.add(mPaths.remove(mPaths.size() - 1));
            undonePaints.add(mPaints.remove(mPaints.size() - 1));
            invalidate();
        }
        else{}
    }
    // when you click redo
    public void onClickRedo() {
        if (undonePaths.size() > 0) {
            mPaths.add(undonePaths.remove(undonePaths.size() - 1));
            mPaints.add(undonePaints.remove(undonePaints.size() - 1));
            invalidate();
        }
        else{}
    }

    public void drawLine(boolean flag) {
        flagline = flag;
    }

    public void bucket(boolean flag) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.addPath(true);
                drawPath.moveTo(touchX, touchY);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (flagline == false) {
                    drawPath.lineTo(touchX, touchY);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (flagline == true) {
                    drawPath.lineTo(touchX, touchY);
                }
                this.addPath(true);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                invalidate();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void changeBrushSize(int size) {
        currentBrushSize = size;
        invalidate();

    }

    public int getPaintColor() {
        return paintColor;
    }

    public void setColor(String newColor) {
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
        invalidate();
    }

//    public void getCurrentSize() {
//        currentBrushSize = ModeSoloDrawView.size;
//        drawPaint.setStrokeWidth(currentBrushSize);
//        invalidate();
//    }

    public void setupDrawing(){
        this.mPaths = new ArrayList<Path>();
        this.mPaints = new ArrayList<Paint>();

        this.addPath(false);

        drawPaint = new Paint();
        drawPath = new Path();

        drawPaint.setColor(paintColor);
        drawPaint.setStrokeWidth(currentBrushSize);
        drawPaint.setAntiAlias(true);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);

        drawPaint.setPathEffect(new CornerPathEffect(10) );
        canvasPaint = new Paint(Paint.DITHER_FLAG);
//        lastBrushSize = currentBrushSize;
    }

    public void saveDrawing2() {
        fixMediaDir();

//        Activity a = (Activity) getContext();
//        if (ContextCompat.checkSelfPermission(a, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            // Permission is not granted
//        }

        if (checkPermissionREAD_EXTERNAL_STORAGE(getContext())) {
//              loadBitmapFromView(drawView);
            this.setDrawingCacheEnabled(true);
            String imgSaved = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), this.getDrawingCache(), UUID.randomUUID().toString() + ".png", "drawing");
            if (imgSaved != null) {
                Toast savedtoast = Toast.makeText(getContext().getApplicationContext(), "Drawing saved to Gallerydfdfd", Toast.LENGTH_SHORT);
                savedtoast.show();
            } else {
                Toast unsaved = Toast.makeText(getContext().getApplicationContext(), "Image could not saved", Toast.LENGTH_SHORT);
                unsaved.show();
            }
            drawView.destroyDrawingCache();
        }
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }


    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                }
                break;
            default:
        }
    }

    public void saveDrawing() {
        MediaStore.Images.Media.insertImage( getContext().getContentResolver(), canvasBitmap, "test" , "test");
    }

    /**
     * Android internals have been modified to store images in the media folder with
     * the correct date meta data
     * @author samuelkirton
     */
    public static class CapturePhotoUtils {

        /**
         * A copy of the Android internals  insertImage method, this method populates the
         * meta data with DATE_ADDED and DATE_TAKEN. This fixes a common problem where media
         * that is inserted manually gets saved at the end of the gallery (because date is not populated).
         */
        public static final String insertImage(ContentResolver cr,
                                               Bitmap source,
                                               String title,
                                               String description) {

            ContentValues values = new ContentValues();
            values.put(Images.Media.TITLE, title);
            values.put(Images.Media.DISPLAY_NAME, title);
            values.put(Images.Media.DESCRIPTION, description);
            values.put(Images.Media.MIME_TYPE, "image/jpeg");
            // Add the date meta data to ensure the image is added at the front of the gallery
            values.put(Images.Media.DATE_ADDED, System.currentTimeMillis());
            values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());

            Uri url = null;
            String stringUrl = null;    /* value to be returned */

            try {
                url = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                if (source != null) {
                    OutputStream imageOut = cr.openOutputStream(url);
                    try {
                        source.compress(Bitmap.CompressFormat.JPEG, 50, imageOut);
                    } finally {
                        imageOut.close();
                    }

                    long id = ContentUris.parseId(url);
                    // Wait until MINI_KIND thumbnail is generated.
                    Bitmap miniThumb = Images.Thumbnails.getThumbnail(cr, id, Images.Thumbnails.MINI_KIND, null);
                    // This is for backward compatibility.
                    storeThumbnail(cr, miniThumb, id, 50F, 50F,Images.Thumbnails.MICRO_KIND);
                } else {
                    cr.delete(url, null, null);
                    url = null;
                }
            } catch (Exception e) {
                if (url != null) {
                    cr.delete(url, null, null);
                    url = null;
                }
            }

            if (url != null) {
                stringUrl = url.toString();
            }

            return stringUrl;
        }

        /**
         * A copy of the Android internals StoreThumbnail method, it used with the insertImage to
         * populate the android.provider.MediaStore.Images.Media#insertImage with all the correct
         * meta data. The StoreThumbnail method is private so it must be duplicated here.
         */
        private static final Bitmap storeThumbnail(
                ContentResolver cr,
                Bitmap source,
                long id,
                float width,
                float height,
                int kind) {

            // create the matrix to scale it
            Matrix matrix = new Matrix();

            float scaleX = width / source.getWidth();
            float scaleY = height / source.getHeight();

            matrix.setScale(scaleX, scaleY);

            Bitmap thumb = Bitmap.createBitmap(source, 0, 0,
                    source.getWidth(),
                    source.getHeight(), matrix,
                    true
            );

            ContentValues values = new ContentValues(4);
            values.put(Images.Thumbnails.KIND,kind);
            values.put(Images.Thumbnails.IMAGE_ID,(int)id);
            values.put(Images.Thumbnails.HEIGHT,thumb.getHeight());
            values.put(Images.Thumbnails.WIDTH,thumb.getWidth());

            Uri url = cr.insert(Images.Thumbnails.EXTERNAL_CONTENT_URI, values);

            try {
                OutputStream thumbOut = cr.openOutputStream(url);
                thumb.compress(Bitmap.CompressFormat.JPEG, 100, thumbOut);
                thumbOut.close();
                return thumb;
            } catch (FileNotFoundException ex) {
                return null;
            } catch (IOException ex) {
                return null;
            }
        }
    }

    void fixMediaDir() {
        File sdcard = Environment.getExternalStorageDirectory();
        if (sdcard != null) {
            File mediaDir = new File(sdcard, "DCIM/Camera");
            if (!mediaDir.exists()) {
                mediaDir.mkdirs();
            }
        }
    }
}
