package com.example.testapptodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.testapptodo.R;

public class SignUpActivity extends Activity {

    public final static String EXTRA_EMAIL = "SignUpActivity.EMAIL";
    public final static String EXTRA_GENDER = "SignUpActivity.GENDER";
    public final static String EXTRA_HEIGHT = "SignUpActivity.HEIGHT";
    public final static String EXTRA_WEIGHT = "SignUpActivity.WEIGHT";
    public final static String EXTRA_AGE = "SignUpActivity.AGE";
    public final static String EXTRA_PASSWORD = "SignUpActivity.PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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

    /** Called when the user clicks the Send button */
    public void signUp(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText emailText = (EditText) findViewById(R.id.Email);
        EditText genderText = (EditText) findViewById(R.id.Gender);
        EditText heightText = (EditText) findViewById(R.id.Height);
        EditText weightText = (EditText) findViewById(R.id.Weight);
        EditText ageText = (EditText) findViewById(R.id.Age);
        EditText passwordText = (EditText) findViewById(R.id.Password);

        String email = emailText.getText().toString();
        intent.putExtra(EXTRA_EMAIL, email);

        String gender = genderText.getText().toString();
        intent.putExtra(EXTRA_GENDER, gender);

        String height = heightText.getText().toString();
        intent.putExtra(EXTRA_HEIGHT, height);

        String weight = weightText.getText().toString();
        intent.putExtra(EXTRA_WEIGHT, weight);

        String age = ageText.getText().toString();
        intent.putExtra(EXTRA_AGE, age);

        String password = passwordText.getText().toString();
        intent.putExtra(EXTRA_PASSWORD, password);

        MainActivity.setUser(true);

        startActivity(intent);
    }
}
