package com.example.shanato.rglhsapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class UseFullLinks extends Activity {


    public Button butt1;
    public Button butt2;
    public Button butt3;
    public Button butt4;
    public Button butt5;
    public Button butt6;
    public Button butt7;
  //  public Button butt8;
    public void  init() {
        butt1 = (Button) findViewById(R.id.butt1);
        butt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(UseFullLinks.this,All_Result_bd.class);

                startActivity(myy);

            }


        });


        butt2=(Button)findViewById(R.id.butt2);
        butt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(UseFullLinks.this,All_Results.class);

                startActivity(myy);

            }



        });


        butt3=(Button)findViewById(R.id.butt3);
        butt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.rajshahiboard.gov.bd/"));
                startActivity(in);

            }



        });

        butt4=(Button)findViewById(R.id.butt4);
        butt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW, Uri.parse("http://sghs.edu.bd/"));
                startActivity(in);

            }



        });

        butt5 = (Button) findViewById(R.id.butt5);
        butt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW, Uri.parse("http://pn.edu.bd/"));

                startActivity(in);

            }


        });


        butt6=(Button)findViewById(R.id.butt6);
        butt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ru.ac.bd/"));
                startActivity(in);

            }



        });


        butt7=(Button)findViewById(R.id.butt7);
        butt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.educationboard.gov.bd/"));
                startActivity(in);

            }



        });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_full_links);
        init();
        Toast loadingmess = Toast.makeText(this,
                "You are UseFull Links", Toast.LENGTH_SHORT);
        loadingmess.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_use_full_links, menu);
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
