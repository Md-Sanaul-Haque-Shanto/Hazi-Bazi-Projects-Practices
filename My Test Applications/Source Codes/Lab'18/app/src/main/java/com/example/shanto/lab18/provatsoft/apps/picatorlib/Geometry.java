package com.example.shanto.lab18.provatsoft.apps.picatorlib;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Geometry {
    public static Point getMidPoint(MotionEvent event) {
        float x0 = event.getX(0);
        return new Point((int) ((x0 + event.getX(1)) / 2.0f), (int) ((event.getY(0) + event.getY(1)) / 2.0f));
    }

    public static double getDistance(MotionEvent ev) {
        float x0 = ev.getX(0);
        float y0 = ev.getY(0);
        float x1 = ev.getX(1);
        float y1 = ev.getY(1);
        return Math.sqrt((double) (((x1 - x0) * (x1 - x0)) + ((y1 - y0) * (y1 - y0))));
    }

    public static Rect getRect(MotionEvent event) {
        return new Rect((int) event.getX(0), (int) event.getY(0), (int) event.getX(1), (int) event.getY(1));
    }
}
