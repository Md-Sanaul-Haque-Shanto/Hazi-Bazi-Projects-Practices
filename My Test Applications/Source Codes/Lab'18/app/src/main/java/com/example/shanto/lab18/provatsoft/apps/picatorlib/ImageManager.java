package com.example.shanto.lab18.provatsoft.apps.picatorlib;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;
import com.facebook.internal.ServerProtocol;
import com.provatsoft.apps.simplecorplib.CropImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class ImageManager {
    public Bitmap transparent(Bitmap src, int alpha) {
        Bitmap transBitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(transBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        Paint paint = new Paint();
        paint.setAlpha(alpha);
        canvas.drawBitmap(src, 0.0f, 0.0f, paint);
        return transBitmap;
    }

    public static Bitmap overlay(Bitmap background, Bitmap foreground) {
        Bitmap bmOverlay = Bitmap.createBitmap(background.getWidth(), background.getHeight(), background.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(background, new Matrix(), null);
        canvas.drawBitmap(foreground, 0.0f, 0.0f, null);
        return bmOverlay;
    }

    public Bitmap resize(Bitmap bmp, float ratio) {
        return Bitmap.createScaledBitmap(bmp, (int) (((float) bmp.getWidth()) * ratio), (int) (((float) bmp.getHeight()) * ratio), true);
    }

    public Bitmap resize(Bitmap bmp, int w, int h) {
        return Bitmap.createScaledBitmap(bmp, w, h, true);
    }

    public Bitmap getBitMap(ImageView imageView) {
        return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
    }

    public boolean saveImage(String imageName, Bitmap bitmap) {
        try {
            FileOutputStream outStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), imageName + ".png"));
            bitmap.compress(CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public Bitmap getFacebookProfilePicture(String url) {
        Bitmap bitmap = null;
        HttpGet httpRequest = new HttpGet(URI.create(url));
        try {
            bitmap = BitmapFactory.decodeStream(new BufferedHttpEntity(new DefaultHttpClient().execute(httpRequest).getEntity()).getContent());
            httpRequest.abort();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public void performCrop(Activity activity, Uri picUri, int size) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
            cropIntent.putExtra(CropImage.ASPECT_X, 1);
            cropIntent.putExtra(CropImage.ASPECT_Y, 1);
            cropIntent.putExtra(CropImage.OUTPUT_X, size);
            cropIntent.putExtra(CropImage.OUTPUT_Y, size);
            cropIntent.putExtra(CropImage.RETURN_DATA, true);
            activity.startActivityForResult(cropIntent, 2);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "Your device doesn't support the crop action!", 0).show();
        }
    }
}
