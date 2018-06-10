package com.example.shanto.mujtamaea;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class About_dev extends Activity {
    ImageView imageView,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_dev);




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




        ImageView imageView1= (ImageView) findViewById(R.id.serverOakTea);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+8801703085340"));
                startActivity(callIntent);
            }


        });

        ImageView imageView2= (ImageView) findViewById(R.id.serverOakTeam);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(About_dev.this, MainActivity.class);

                startActivity(toy);

            }


        });

        ImageView imageView3= (ImageView) findViewById(R.id.emailOakTeam);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("http://gmail.com"));
                startActivity(in);

            }



        });
        ImageView imageView4= (ImageView) findViewById(R.id.appbajar);
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("http://appbajar.com"));
                startActivity(in);

            }



        });

        ImageView imageView5= (ImageView) findViewById(R.id.play_store_id);
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com"));
                startActivity(in);

            }



        });
        ImageView imageView6= (ImageView) findViewById(R.id.team_fb);
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("http://m.facebook.com/mdsanaulhaqueshanto1"));
                startActivity(in);

            }



        });

        ImageView imageView7= (ImageView) findViewById(R.id.muj);
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("http://mujtamaecoltd.freeiz.com"));
                startActivity(in);

            }



        });




    }


    private void displayInterstitial() {
        if(mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_dev, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
