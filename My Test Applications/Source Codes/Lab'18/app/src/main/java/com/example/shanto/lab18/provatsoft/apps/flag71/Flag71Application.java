package com.example.shanto.lab18.provatsoft.apps.flag71;

import android.app.Application;
import com.facebook.FacebookSdk;

public class Flag71Application extends Application {
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);
    }
}
