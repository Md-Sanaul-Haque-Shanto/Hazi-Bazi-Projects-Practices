package com.example.shanto.lab18.provatsoft.apps.picatorlib;

import android.graphics.Bitmap;
import android.support.v4.view.MotionEventCompat;
import android.widget.ImageView;
import android.widget.SeekBar;

public abstract class FlagPainter {
    protected static final int SIZE = 512;
    protected int alpha;
    private int alphaPc;
    protected Bitmap background;
    protected int boundHeight;
    protected int boundWidth;
    protected Bitmap foreground;
    ImageManager imageManager;
    protected ImageView imageView;
    protected SeekBar seekBar;

    protected abstract Bitmap draw();

    protected abstract Bitmap drawForeground();

    public abstract void setBackground(Bitmap bitmap);

    public abstract void setForeground(Bitmap bitmap);

    public void listen(ImageView imageView, SeekBar seekBar) {
        if (seekBar != null) {
            seekBar.setProgress(this.alphaPc);
        }
        this.imageView = imageView;
        this.seekBar = seekBar;
    }

    public FlagPainter(int boundWidth, int boundHeight) {
        this.imageManager = new ImageManager();
        this.boundWidth = boundWidth;
        this.boundHeight = boundHeight;
    }

    public void setAlphaInPc(int percent) {
        this.alpha = (percent * MotionEventCompat.ACTION_MASK) / 100;
        this.alphaPc = percent;
    }
}
