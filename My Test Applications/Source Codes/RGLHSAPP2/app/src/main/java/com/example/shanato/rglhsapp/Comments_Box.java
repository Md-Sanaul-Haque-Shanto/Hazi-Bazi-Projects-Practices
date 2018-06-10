package com.example.shanato.rglhsapp;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class Comments_Box extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments__box);


        WebView webbb =(WebView)findViewById(R.id.webV4);
        webbb.setWebViewClient(new WebViewClient());
        WebSettings webSettings=webbb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webbb.loadUrl("http://docs.google.com/forms/d/e/1FAIpQLSckV-XB_sN84bQJcgEM2AZ4IWfaGUiwnyllWR2THcW1JM-MwA/viewform");

        Toast loadingmess = Toast.makeText(this,
                "Welcome To Comment Box", Toast.LENGTH_SHORT);
        loadingmess.show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comments__box, menu);
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
