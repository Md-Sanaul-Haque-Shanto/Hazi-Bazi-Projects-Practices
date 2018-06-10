package com.example.shanto.lab18.provatsoft.apps.flag71;



public class AppConfigs {
    public static final boolean DEBUG = false;
    public static final int INIT_ALPHA_SEEK = 70;
    public static final int PHOTO_SIZE = 512;
    public static String kDRAWABLE_IMAGE_ID;
    public static String kPROFILE;

    static {
        kPROFILE = Scopes.PROFILE;
        kDRAWABLE_IMAGE_ID = "image_id";
    }
}
