package com.example.shanto.lab18.provatsoft.apps.flag71;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.shanto.lab18.R;
import com.facebook.AccessToken;
import com.provatsoft.apps.flag71.blls.ProfileManager;
import com.provatsoft.apps.flag71.models.UserProfile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT;
    private ProgressBar progressBar;

    /* renamed from: com.provatsoft.apps.flag71.SplashActivity.1 */
    class C04681 implements Runnable {
        C04681() {
        }

        public void run() {
            SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
            SplashActivity.this.finish();
        }
    }

    static {
        SPLASH_TIME_OUT = 2000;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_splash);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ProfileManager profileManager = new ProfileManager(getApplicationContext());
        new Handler().postDelayed(new C04681(), (long) SPLASH_TIME_OUT);
    }

    private void goLoginActivity(ProfileManager profileManager) {
        if (AccessToken.getCurrentAccessToken() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        nextActivity(profileManager.getUserProfile());
    }

    public void nextActivity(UserProfile profile) {
        Intent i = new Intent(this, MainActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(AppConfigs.kPROFILE, profile);
        i.putExtras(mBundle);
        startActivity(i);
        finish();
    }

    private void showKeyHash() {
        try {
            for (Signature signature : getPackageManager().getPackageInfo("com.provatsoft.apps.picatorapp", 64).signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("YourKeyHash :", Base64.encodeToString(md.digest(), 0));
            }
        } catch (NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e2) {
        }
    }
}
