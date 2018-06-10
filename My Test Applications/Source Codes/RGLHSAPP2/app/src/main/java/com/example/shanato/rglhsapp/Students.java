package com.example.shanato.rglhsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Students extends Activity {
    public Button buttt1;
    public Button buttt2;

    public Button buttt3;

    public void  init() {
        buttt1 = (Button) findViewById(R.id.buttt111);
        buttt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Students.this, Students_form.class);

                startActivity(myy);

            }


        });


        buttt2 = (Button) findViewById(R.id.buttt2);
        buttt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Students.this,Students_Lists.class);

                startActivity(myy);

            }


        });


        buttt3 = (Button) findViewById(R.id.buttt3);
        buttt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myy = new Intent(Students.this,Student_panel.class);

                startActivity(myy);

            }


        });
    }
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
            init();
            Toast loadingmess = Toast.makeText(this,
                    "You are in Students Menu", Toast.LENGTH_SHORT);
            loadingmess.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_students, menu);
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
