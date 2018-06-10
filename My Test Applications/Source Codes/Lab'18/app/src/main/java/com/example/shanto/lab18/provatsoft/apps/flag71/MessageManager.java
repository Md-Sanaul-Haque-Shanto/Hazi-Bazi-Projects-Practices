package com.example.shanto.lab18.provatsoft.apps.flag71;

import android.os.Handler;
import android.widget.TextView;
import com.provatsoft.apps.simplecorplib.BuildConfig;

public class MessageManager {

    /* renamed from: com.provatsoft.apps.flag71.MessageManager.1 */
    static class C04661 implements Runnable {
        final /* synthetic */ TextView val$textView;

        C04661(TextView textView) {
            this.val$textView = textView;
        }

        public void run() {
            this.val$textView.setText(BuildConfig.FLAVOR);
            this.val$textView.setVisibility(8);
        }
    }

    public static void show(TextView textView, String message) {
        textView.setVisibility(0);
        textView.setText(message);
        new Handler().postDelayed(new C04661(textView), 5000);
    }
}
