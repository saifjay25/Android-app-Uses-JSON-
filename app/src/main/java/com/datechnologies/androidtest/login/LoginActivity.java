package com.datechnologies.androidtest.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;

import java.util.HashMap;
import java.util.Map;

/**
 * A screen that displays a login prompt, allowing the user to login to the D & A Technologies Web Server.
 *
 */
public class LoginActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }
    // volley is used to send data to a server so you compile volley in you dependencies in gradle script
    EditText email;
    EditText password;
    Button login;
    String server="http://dev.datechnologies.co/Tests/scripts/login.php";
    AlertDialog.Builder build;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.login_button);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        if(savedInstanceState!=null) {
           email.setText(savedInstanceState.getString("email"));
           password.setText(savedInstanceState.getString("password"));
        }
        build = new AlertDialog.Builder(LoginActivity.this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final long start = System.currentTimeMillis();
                StringRequest request = new StringRequest(Request.Method.POST, server,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                long end = System.currentTimeMillis();
                                long difference = end-start;
                                String s=Long.toString(difference);
                                build.setTitle("Response");
                                build.setMessage(response+" API call in milliseconds: "+s);
                                build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        email.setText("");
                                        password.setText("");
                                        Intent intent= new Intent (LoginActivity.this,MainActivity.class);
                                        LoginActivity.this.startActivity(intent);
                                    }
                                });
                                AlertDialog alert= build.create();
                                alert.show();
                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        long end = System.currentTimeMillis();
                        long difference = end-start;
                        String s=Long.toString(difference);
                        build.setTitle("response");
                        build.setMessage("{\"code\":\"Error\",\"message\":\"Invalid Parameters\"}"+" API call in milisecond: "+s);
                        build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                email.setText("");
                                password.setText("");
                            }
                        });
                        AlertDialog alert= build.create();
                        alert.show();
                    }
                }){
                    //this send data to server within the string request
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map <String, String> map= new HashMap<>();
                        map.put("email",email.getText().toString());
                        map.put("password", password.getText().toString());
                        return map;
                    }
                };
                //add string request to the request queue
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(request);

            }
        });
        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked
        // TODO: Save screen state on screen rotation, inputted username and password should not disappear on screen rotation

        // TODO: Send 'email' and 'password' to http://dev.datechnologies.co/Tests/scripts/login.php
        // TODO: as FormUrlEncoded parameters.

        // TODO: When you receive a response from the login endpoint, display an AlertDialog.
        // TODO: The AlertDialog should display the 'code' and 'message' that was returned by the endpoint.
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity

        // TODO: The only valid login credentials are:
        // TODO: email: info@datechnologies.co
        // TODO: password: Test123
        // TODO: so please use those to test the login.
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email",email.toString());
        outState.putString("password", password.toString());
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
