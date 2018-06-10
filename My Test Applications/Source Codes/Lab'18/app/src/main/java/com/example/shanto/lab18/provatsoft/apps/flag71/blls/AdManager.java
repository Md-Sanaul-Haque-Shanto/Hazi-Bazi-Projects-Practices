package com.example.shanto.lab18.provatsoft.apps.flag71.blls;

import android.content.Context;
import android.provider.Settings.Secure;
import android.support.v4.view.MotionEventCompat;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.provatsoft.apps.simplecorplib.BuildConfig;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AdManager {
    private AdRequest bannerRequest;
    private Builder bannerRequestBuilder;
    private Context context;

    public AdManager(Context context) {
        this.context = context;
    }

    public void enableAd(AdView adView) {
        showAd(adView);
    }

    public void showAd(AdView adView) {
        String thisDeviceId = getDeviceId();
        this.bannerRequestBuilder = new Builder();
        this.bannerRequest = this.bannerRequestBuilder.build();
        adView.loadAd(this.bannerRequest);
    }

    public String getDeviceId() {
        return md5(Secure.getString(this.context.getContentResolver(), "android_id")).toUpperCase();
    }

    public String md5(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (byte b : messageDigest) {
                hexString.append(Integer.toHexString(b & MotionEventCompat.ACTION_MASK));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return BuildConfig.FLAVOR;
        }
    }
}
