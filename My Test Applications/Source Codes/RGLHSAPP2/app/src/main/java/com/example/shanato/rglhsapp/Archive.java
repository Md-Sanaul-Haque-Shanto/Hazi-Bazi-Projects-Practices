package com.example.shanato.rglhsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Archive extends Activity {


    public Button butt1;
    public Button butt2;
    public Button butt3;
    public Button butt4;
    public Button butt5;
    public Button butt6;
    public Button butt7;


    public void  init() {
        butt1 = (Button) findViewById(R.id.buttt111);
        butt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Archive.this,About_School.class);

                startActivity(myy);

            }


        });


        butt2 = (Button) findViewById(R.id.buttt2);
        butt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Archive.this,About_Logo.class);

                startActivity(myy);

            }


        });



        butt3 = (Button) findViewById(R.id.buttt3);
        butt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Archive.this,Buildings.class);

                startActivity(myy);

            }


        });


        butt4 = (Button) findViewById(R.id.buttt4);
        butt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Archive.this,HeadMasters.class);

                startActivity(myy);

            }


        });


        butt5 = (Button) findViewById(R.id.buttt5);
        butt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Archive.this,School_Staffs.class);

                startActivity(myy);

            }


        });


        butt6 = (Button) findViewById(R.id.buttt6);
        butt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Archive.this,Results_Success.class);

                startActivity(myy);

            }


        });

        butt7 = (Button) findViewById(R.id.buttt7);
        butt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Archive.this,Major_Informations.class);

                startActivity(myy);

            }


        });






    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        init();

        Toast loadingmess = Toast.makeText(this,
                "You are in Archive", Toast.LENGTH_SHORT);
        loadingmess.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_archive, menu);
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
