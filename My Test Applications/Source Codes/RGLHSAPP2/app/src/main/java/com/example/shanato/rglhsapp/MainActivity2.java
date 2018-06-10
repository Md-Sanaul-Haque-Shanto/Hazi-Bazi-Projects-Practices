package com.example.shanato.rglhsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity2 extends Activity {

    public Button butt1;
    public Button butt2;
    public Button butt3;
    public Button butt4;



    public void  init() {
        butt1 = (Button) findViewById(R.id.buttt111);
        butt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(MainActivity2.this,Archive.class);

                startActivity(myy);

            }


        });


        butt2 = (Button) findViewById(R.id.buttt222);
        butt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(MainActivity2.this,Menu11.class);

                startActivity(myy);

            }


        });


        butt3 = (Button) findViewById(R.id.buttt3);
        butt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(MainActivity2.this,About_App.class);

                startActivity(toy);

            }


        });


        butt4 = (Button) findViewById(R.id.buttt4);
        butt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(MainActivity2.this,About_Developer.class);

                startActivity(toy);

            }


        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        init();
        Toast loadingmess = Toast.makeText(this,
                "You are in App Archives", Toast.LENGTH_SHORT);
        loadingmess.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
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
