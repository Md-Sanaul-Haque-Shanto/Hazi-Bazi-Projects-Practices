package com.example.shanato.rglhsapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class About_Developer extends Activity {
    public Button but3;
    public Button but4;
    public Button but5;
    public Button but6;
    public void  init(){
        but3=(Button)findViewById(R.id.but3);
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+8801703085340"));
                startActivity(callIntent);
            }



        });


        but4=(Button)findViewById(R.id.but4);
        but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("http://mobile.facebook.com/MdSanaulHaqueShanto1/"));
                startActivity(in);

            }



        });


        but5=(Button)findViewById(R.id.but5);
        but5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("http://mobile.twitter.com/MdSanaulHaque0/"));
                startActivity(in);

            }



        });

        but6=(Button)findViewById(R.id.but6);
        but6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse("http://mdshsservice.blogspot.com"));
                startActivity(in);

            }



        });



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__developer);

        init();
        Toast loadingmess = Toast.makeText(this,
                "You are in About Developer", Toast.LENGTH_SHORT);
        loadingmess.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about__developer, menu);
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
