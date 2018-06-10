package com.example.shanato.rglhsapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;


public class School_Locations extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school__locations);


        WebView webbb =(WebView)findViewById(R.id.webView);
        webbb.setWebViewClient(new WebViewClient());
        WebSettings webSettings=webbb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webbb.loadUrl("http://www.google.com.bd/maps/place/Government+Laboratory+High+School/@24.3712897,88.5800637,181m/data=!3m1!1e3!4m13!1m7!3m6!1s0x39fbef1c2819ceb7:0xdae9467f16cf01c9!2sGovernment+Laboratory+High+School!3b1!8m2!3d24.3715963!4d88.5802377!3m4!1s0x39fbef1c2819ceb7:0xdae9467f16cf01c9!8m2!3d24.3715963!4d88.5802377");
        webbb.setWebViewClient(new WebViewClient());

        webbb.getSettings().setBuiltInZoomControls(true);
       webbb.getSettings().setUseWideViewPort(true);



        Toast loadingmess = Toast.makeText(this,
                "You are in School Locations", Toast.LENGTH_SHORT);
        loadingmess.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_school__locations, menu);
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
