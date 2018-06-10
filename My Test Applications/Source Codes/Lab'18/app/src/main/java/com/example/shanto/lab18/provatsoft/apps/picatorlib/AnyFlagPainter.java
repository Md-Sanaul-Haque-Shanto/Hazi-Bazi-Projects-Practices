package com.example.shanto.lab18.provatsoft.apps.picatorlib;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
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

public class AnyFlagPainter extends FlagPainter implements OnTouchListener, OnSeekBarChangeListener {
    private static final String TAG = "AnyFlagPainter";
    private int foreHeight;
    private int foreWidth;
    private int foreX;
    private int foreY;

    public AnyFlagPainter(int boundWidth, int boundHeight) {
        super(boundWidth, boundHeight);
        this.foreX = boundWidth / 2;
        this.foreY = boundHeight / 2;
    }

    public void listen(ImageView imageView, SeekBar seekBar) {
        super.listen(imageView, seekBar);
        imageView.setOnTouchListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        setForePoint(this.foreX, this.foreY);
        imageView.setImageBitmap(draw());
    }

    public Bitmap drawForeground() {
        Bitmap canvasBitmap = Bitmap.createBitmap(this.boundWidth, this.boundHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBitmap);
        if (this.foreground != null) {
            canvas.drawBitmap(this.imageManager.resize(this.foreground, this.foreWidth, this.foreHeight), (float) this.foreX, (float) this.foreY, new Paint());
        }
        if (this.alpha <= 0) {
            return canvasBitmap;
        }
        ImageManager imageManager = new ImageManager();
        canvasBitmap.setHasAlpha(true);
        return imageManager.transparent(canvasBitmap, this.alpha);
    }

    public Bitmap draw() {
        ImageManager imageManager = this.imageManager;
        return ImageManager.overlay(this.background, drawForeground());
    }

    public void setForePoint(int x, int y) {
        this.foreX = x - (this.foreWidth / 2);
        this.foreY = y - (this.foreHeight / 2);
    }

    public void setForePoint(Point point) {
        setForePoint(point.x, point.y);
    }

    public boolean onTouch(View v, MotionEvent event) {
        int pointer = event.getPointerCount();
        if (pointer == 1) {
            return moveForeground(event);
        }
        if (pointer != 2) {
            return false;
        }
        int distance = (int) Geometry.getDistance(event);
        Point midPoint = Geometry.getMidPoint(event);
        setForegroundArea(distance, distance);
        setForePoint(midPoint);
        this.imageView.setImageBitmap(draw());
        return true;
    }

    private boolean moveForeground(MotionEvent event) {
        switch (event.getAction()) {
            case ConnectionResult.SUCCESS /*0*/:
                setForePoint((int) event.getX(), (int) event.getY());
                this.imageView.setImageBitmap(draw());
                Log.d(TAG, "Action was DOWN");
                return true;
            case ConnectionResult.SERVICE_MISSING /*1*/:
                Log.d(TAG, "Action was UP");
                return true;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED /*2*/:
                setForePoint((int) event.getX(), (int) event.getY());
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

    public void setForeground(Bitmap foreground) {
        this.foreground = foreground.copy(Config.ARGB_8888, true);
        setForegroundArea(foreground.getWidth(), foreground.getHeight());
    }

    public void setBackground(Bitmap background) {
        this.background = this.imageManager.resize(background.copy(Config.ARGB_8888, true), MainActivity.SIZE, MainActivity.SIZE);
    }

    private void setForegroundArea(int width, int height) {
        this.foreWidth = width;
        this.foreHeight = height;
    }
}
