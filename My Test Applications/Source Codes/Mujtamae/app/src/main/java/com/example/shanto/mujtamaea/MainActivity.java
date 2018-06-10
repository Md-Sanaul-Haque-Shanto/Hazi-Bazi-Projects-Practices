package com.example.shanto.mujtamaea;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity {

    WebView brow;
    EditText urledit;
    Button go,forward,back,About,reload;
    ProgressBar progressBar;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // Load an ad into the AdMob Interstitional view.
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        AdRequest adRequest1 = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest1);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                displayInterstitial();
            }

        });



        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        brow= (WebView)findViewById(R.id.wv_brow);
      /*  urledit = (EditText)findViewById(R.id.et_url);
        go = (Button)findViewById(R.id.btn_go);*/
        forward = (Button)findViewById(R.id.btn_fwd);
        back = (Button)findViewById(R.id.btn_bck);
        About = (Button)findViewById(R.id.btn_clear);
        reload = (Button)findViewById(R.id.btn_reload);

        // When we click on something in our browser this enables to open the link in the same browser instead of opening anohter browser

        brow.setWebViewClient(new ourViewClient());
        brow.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);

                if(newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);

                }
            }
        });

        WebSettings websettings = brow.getSettings();
        websettings.setJavaScriptEnabled(true);

        brow.loadUrl("http://mujtamaecoltd.freeiz.com");

       /* go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editextvalue = urledit.getText().toString();

                if(!editextvalue.startsWith("http://"))
                    editextvalue = "http://" + editextvalue;

                String url = editextvalue;
                brow.loadUrl(url);

                //Hide keyboard after using EditText
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(urledit.getWindowToken(),0);
            }
        });*/

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(brow.canGoForward())
                    brow.goForward();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(brow.canGoBack())
                    brow.goBack();
            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brow.reload();
            }
        });

        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(MainActivity.this,About_dev.class);

                startActivity(toy);

            }


        });


    }
    private void displayInterstitial() {
        if(mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();
        }
    }


}
