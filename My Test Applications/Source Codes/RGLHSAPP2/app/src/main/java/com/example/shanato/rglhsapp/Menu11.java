package com.example.shanato.rglhsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Menu11 extends Activity {
    public Button butt1;
    public Button butt2;
    public Button butt3;
    public Button butt4;
    public Button butt5;
    public Button butt6;
    public Button butt7;
    public Button butt8;
    public Button butt9;
    public Button butt10;
    public Button butt11;
    public Button butt12;
   
    public void  init() {
        butt1 = (Button) findViewById(R.id.butt111);
        butt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Menu11.this,School_Website.class);

                startActivity(myy);

            }


        });
        butt12 = (Button) findViewById(R.id.butt2222);
        butt12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Menu11.this,Others.class);

                startActivity(myy);

            }


        });

        butt2 = (Button) findViewById(R.id.buttt2);
        butt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Menu11.this,SchoolResults.class);

                startActivity(myy);

            }


        });



        butt3 = (Button) findViewById(R.id.buttt3);
        butt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Menu11.this,Students.class);

                startActivity(myy);

            }


        });


        butt4 = (Button) findViewById(R.id.buttt4);
        butt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Menu11.this,Teachers.class);

                startActivity(myy);

            }


        });


        butt5 = (Button) findViewById(R.id.buttt5);
        butt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Menu11.this,HeadMaster.class);

                startActivity(myy);

            }


        });


        butt6 = (Button) findViewById(R.id.buttt6);
        butt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Menu11.this,Achivements.class);

                startActivity(myy);

            }


        });

        butt7 = (Button) findViewById(R.id.buttt7);
        butt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Menu11.this,Gallary.class);

                startActivity(myy);

            }


        });


        butt8 = (Button) findViewById(R.id.buttt8);
        butt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Menu11.this,UseFullLinks.class);

                startActivity(myy);

            }


        });
        butt9 = (Button) findViewById(R.id.buttt9);
        butt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Menu11.this,Comments_Box.class);

                startActivity(myy);

            }


        });


        butt10 = (Button) findViewById(R.id.buttt10);
        butt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Menu11.this,Contacts_Us.class);

                startActivity(myy);

            }


        });
        butt11 = (Button) findViewById(R.id.buttt111);
        butt11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Menu11.this,App_Futures.class);

                startActivity(myy);

            }


        });



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu11);
        init();

        Toast loadingmess = Toast.makeText(this,
                "You are in App Menu", Toast.LENGTH_SHORT);
        loadingmess.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu11, menu);
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
