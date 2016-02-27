package com.example.testapptodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.testapptodo.R;

public class SignInActivity extends Activity {

    public final static String EXTRA_EMAIL = "SignInActivity.EMAIL";
    public final static String EXTRA_PASSWORD = "SignInActivity.PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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
    public void enteredCredentials(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText emailText = (EditText) findViewById(R.id.EmailText);
        EditText passwordText = (EditText) findViewById(R.id.PasswordText);

        String email = emailText.getText().toString();
        intent.putExtra(EXTRA_EMAIL, email);

        String password = passwordText.getText().toString();
        intent.putExtra(EXTRA_PASSWORD, password);

        if(!email.equals("")){
            MainActivity.setUser(true);
        }

        startActivity(intent);
    }
}

