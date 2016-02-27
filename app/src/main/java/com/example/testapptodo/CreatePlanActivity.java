package com.example.testapptodo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.testapptodo.R;

public class CreatePlanActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        final Spinner spinner1 = (Spinner)findViewById(R.id.Exercise1);
        final EditText reps1 = (EditText) findViewById(R.id.reps1);
        final EditText sets1 = (EditText) findViewById(R.id.sets1);
        final EditText run1 = (EditText) findViewById(R.id.run1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String text = spinner1.getSelectedItem().toString();

                if (text.equals("Run")) {
                    reps1.setVisibility(View.INVISIBLE);
                    sets1.setVisibility(View.INVISIBLE);
                    run1.setVisibility(View.VISIBLE);
                }
                else {
                    reps1.setVisibility(View.VISIBLE);
                    sets1.setVisibility(View.VISIBLE);
                    run1.setVisibility(View.INVISIBLE);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_plan, menu);
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

    public void showRow2(View view) {

    }

}
