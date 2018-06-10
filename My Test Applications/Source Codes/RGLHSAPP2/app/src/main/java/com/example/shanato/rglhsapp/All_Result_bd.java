package com.example.shanato.rglhsapp;

import android.app.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class All_Result_bd extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__result_bd);


        WebView webbb =(WebView)findViewById(R.id.webView);
        webbb.setWebViewClient(new WebViewClient());
        WebSettings webSettings=webbb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webbb.loadUrl("http://www.educationboardresults.gov.bd/");


        Toast loadingmess = Toast.makeText(this,
                "You are in All Board Results", Toast.LENGTH_SHORT);
        loadingmess.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all__result_bd, menu);
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
