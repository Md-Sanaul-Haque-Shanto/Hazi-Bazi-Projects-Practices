package com.example.shanto.lab18.provatsoft.apps.flag71.blls;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FileManager {
    private final String filePrefix;
    private final String flolderName;

    public FileManager() {
        this.flolderName = "Flag71";
        this.filePrefix = "flag";
    }

    public String saveImage(Bitmap bitmap) {
        File folder = createDirInDCIM();
        String formattedDate = new SimpleDateFormat("yyyyMMddHms").format(Calendar.getInstance().getTime());
        File image = new File(folder, String.format("%s_%s.png", new Object[]{"flag", formattedDate}));
        try {
            FileOutputStream outStream = new FileOutputStream(image);
            bitmap.compress(CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return image.getAbsolutePath();
    }

    private File createDirInDCIM() {
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath(), "Flag71");
        folder.mkdir();
        return folder;
    }
}
