package com.example.shanto.lab18.provatsoft.apps.flag71;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class InternalStorageContentProvider extends ContentProvider {
    public static final Uri CONTENT_URI;
    private static final HashMap<String, String> MIME_TYPES;

    static {
        CONTENT_URI = Uri.parse("content://eu.janmuller.android.simplecropimage.example/");
        MIME_TYPES = new HashMap();
        MIME_TYPES.put(".png", "image/png");
        MIME_TYPES.put(".png", "image/png");
    }

    public boolean onCreate() {
        try {
            File mFile = new File(getContext().getFilesDir(), Photographer.TEMP_PHOTO_FILE_NAME);
            if (!mFile.exists()) {
                mFile.createNewFile();
                getContext().getContentResolver().notifyChange(CONTENT_URI, null);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getType(Uri uri) {
        String path = uri.toString();
        for (String extension : MIME_TYPES.keySet()) {
            if (path.endsWith(extension)) {
                return (String) MIME_TYPES.get(extension);
            }
        }
        return null;
    }

    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        File f = new File(getContext().getFilesDir(), Photographer.TEMP_PHOTO_FILE_NAME);
        if (f.exists()) {
            return ParcelFileDescriptor.open(f, 805306368);
        }
        throw new FileNotFoundException(uri.getPath());
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
