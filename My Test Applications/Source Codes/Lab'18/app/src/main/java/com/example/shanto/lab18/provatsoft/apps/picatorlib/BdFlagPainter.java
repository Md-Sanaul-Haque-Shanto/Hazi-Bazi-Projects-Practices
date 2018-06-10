package com.example.shanto.lab18.provatsoft.apps.picatorlib;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.google.android.gms.common.ConnectionResult;
import com.provatsoft.apps.flag71.MainActivity;

public class BdFlagPainter extends FlagPainter implements OnTouchListener, OnSeekBarChangeListener {
    private static final String GREEN = "#01796F";
    private static final String RED = "#E11837";
    private static final String TAG = "BdFlagPainter";
    private int centerX;
    private int centerY;
    private int radius;

    public BdFlagPainter(int boundWidth, int boundHeight) {
        super(boundWidth, boundHeight);
        int unit = boundWidth / 6;
        this.centerX = boundWidth / 2;
        this.centerY = boundHeight / 2;
        this.radius = unit * 2;
    }

    public void setBackground(Bitmap background) {
        this.background = this.imageManager.resize(background.copy(Config.ARGB_8888, true), MainActivity.SIZE, MainActivity.SIZE);
    }

    public void listen(ImageView imageView, SeekBar seekBar) {
        super.listen(imageView, seekBar);
        imageView.setOnTouchListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        imageView.setImageBitmap(draw());
    }

    protected Bitmap draw() {
        ImageManager imageManager = this.imageManager;
        return ImageManager.overlay(this.background, drawForeground());
    }

    public void setForeground(Bitmap foreground) {
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Bitmap drawForeground() {
        Bitmap canvasBitmap = Bitmap.createBitmap(this.boundWidth, this.boundHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBitmap);
        Paint paint = new Paint();
        canvas.drawBitmap(canvasBitmap, 0.0f, 0.0f, paint);
        paint.setColor(Color.parseColor(GREEN));
        canvas.drawRect(0.0f, 0.0f, (float) this.boundWidth, (float) this.boundHeight, paint);
        paint.setColor(Color.parseColor(RED));
        canvas.drawCircle((float) this.centerX, (float) this.centerY, (float) this.radius, paint);
        if (this.alpha <= 0) {
            return canvasBitmap;
        }
        canvasBitmap.setHasAlpha(true);
        return this.imageManager.transparent(canvasBitmap, this.alpha);
    }

    public void setCenter(int x, int y) {
        this.centerX = x;
        this.centerY = y;
    }

    public void setCenter(Point point) {
        this.centerX = point.x;
        this.centerY = point.y;
    }

    public boolean onTouch(View v, MotionEvent event) {
        int pointer = event.getPointerCount();
        if (pointer == 1) {
            return moveCircle(event);
        }
        if (pointer != 2) {
            return false;
        }
        double distance = Geometry.getDistance(event);
        Point point = Geometry.getMidPoint(event);
        setRadius((int) (distance / 2.0d));
        setCenter(point);
        this.imageView.setImageBitmap(draw());
        return true;
    }

    private boolean moveCircle(MotionEvent event) {
        switch (event.getAction()) {
            case ConnectionResult.SUCCESS /*0*/:
                setCenter((int) event.getX(), (int) event.getY());
                this.imageView.setImageBitmap(draw());
                Log.d(TAG, "Action was DOWN");
                return true;
            case ConnectionResult.SERVICE_MISSING /*1*/:
                Log.d(TAG, "Action was UP");
                return true;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED /*2*/:
                setCenter((int) event.getX(), (int) event.getY());
                this.imageView.setImageBitmap(draw());
                Log.d(TAG, "Action was MOVE");
                return true;
            case ConnectionResult.SERVICE_DISABLED /*3*/:
                Log.d(TAG, "Action was CANCEL");
                return true;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                Log.d(TAG, "Movement occurred outside bounds of current screen element");
                return true;
            default:
                return false;
        }
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setAlphaInPc(progress);
        this.imageView.setImageBitmap(draw());
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
