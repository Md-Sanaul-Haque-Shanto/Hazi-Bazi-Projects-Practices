package com.example.shanato.rglhsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Students_Lists extends Activity {



    public Button buttt1;
    public Button butt2;
    public Button butt3;
    public Button butt4;
    public Button butt5;
    public Button butt6;
    public Button butt7;
    public Button butt8;


    public void  init() {
        buttt1 = (Button) findViewById(R.id.buttt111);
        buttt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Students_Lists.this, Batch_2009.class);

                startActivity(myy);

            }


        });


        butt2 = (Button) findViewById(R.id.buttt2);
        butt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Students_Lists.this,Batch_2010.class);

                startActivity(myy);

            }


        });


        butt3 = (Button) findViewById(R.id.buttt3);
        butt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Students_Lists.this,Batch_2011.class);

                startActivity(myy);

            }


        });


        butt4 = (Button) findViewById(R.id.buttt4);
        butt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Students_Lists.this,Batch_2012.class);

                startActivity(myy);

            }


        });


        butt5 = (Button) findViewById(R.id.buttt5);
        butt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Students_Lists.this,Batch_2013.class);

                startActivity(myy);

            }


        });


        butt6 = (Button) findViewById(R.id.buttt6);
        butt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Students_Lists.this,Batch_2014.class);

                startActivity(myy);

            }


        });

        butt7 = (Button) findViewById(R.id.buttt7);
        butt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Students_Lists.this,Batch_2015.class);

                startActivity(myy);

            }


        });


        butt8 = (Button) findViewById(R.id.buttt8);
        butt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Students_Lists.this,Batch_2016.class);

                startActivity(myy);

            }


        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students__lists);
        init();
        Toast loadingmess = Toast.makeText(this,
                "You are Students Lists", Toast.LENGTH_SHORT);
        loadingmess.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_students__lists, menu);
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
