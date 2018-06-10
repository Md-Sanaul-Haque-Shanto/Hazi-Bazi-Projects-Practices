package com.example.shanto.lab18.provatsoft.apps.flag71;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import com.provatsoft.apps.simplecorplib.CropImage;
import java.io.File;

public class Photographer {
    private static final String TAG = "Photographer";
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.png";
    private Activity activity;
    public File tempFile;

    public Photographer(Activity theActivity) {
        this.activity = theActivity;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            this.tempFile = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
        } else {
            this.tempFile = new File(this.activity.getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }
    }

    public void takePicture() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        try {
            Uri mImageCaptureUri;
            if ("mounted".equals(Environment.getExternalStorageState())) {
                mImageCaptureUri = Uri.fromFile(this.tempFile);
            } else {
                mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
            }
            intent.putExtra("output", mImageCaptureUri);
            intent.putExtra(CropImage.RETURN_DATA, true);
            this.activity.startActivityForResult(intent, 2);
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, "cannot take picture", e);
        }
    }

    public void openGallery() {
        Intent photoPickerIntent = new Intent("android.intent.action.PICK");
        photoPickerIntent.setType("image/*");
        this.activity.startActivityForResult(photoPickerIntent, 1);
    }

    public void startCropImage() {
        Intent intent = new Intent(this.activity, CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, this.tempFile.getPath());
        intent.putExtra(CropImage.SCALE, true);
        intent.putExtra(CropImage.ASPECT_X, 1);
        intent.putExtra(CropImage.ASPECT_Y, 1);
        this.activity.startActivityForResult(intent, 3);
    }
}
