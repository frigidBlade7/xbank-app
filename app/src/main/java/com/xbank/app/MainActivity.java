package com.xbank.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;

    //create string parameters

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ConstraintLayout cl = new ConstraintLayout(this);
        setContentView(R.layout.activity_main);
        //get text using findViewById

        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
        Button button = (Button)findViewById(R.id.button);

    }

    public void showToast(View view) {

        /*Toast displayToast = Toast.makeText(MainActivity.this,"Logging in",Toast.LENGTH_LONG);
        //displayToast.show();*/

        String usernameString = usernameEditText.getText().toString();
        String passwordString = passwordEditText.getText().toString();

        //write log statement to check if username is correct
        //and password is also correct
        Log.d("MAIN_ACTIVITY", usernameString);


        //if username = user and password == abcd
        if(usernameString.equals("Ama") && passwordString.equals("password")){
            //success

            Toast.makeText(this, "Welcome "+usernameString, Toast.LENGTH_SHORT).show();

            //navigate to welcome activity
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);

        }else{
            //failure
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
        //check string parameters



    }
}