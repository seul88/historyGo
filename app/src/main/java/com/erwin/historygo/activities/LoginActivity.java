package com.erwin.historygo.activities;

import android.content.Context;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.erwin.historygo.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    public static String email;
    public static String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final Button btLogin = (Button) findViewById(R.id.btLogin);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegister);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.app_server) +"/registration"));
                startActivity(browserIntent);

            }
        });


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etUsername.getText().toString();
                password = etPassword.getText().toString();
                new LoginTask().execute();
            }
        });
    }


    public class LoginTask extends android.os.AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(LoginActivity.this, "Trying to log in", Toast.LENGTH_SHORT).show();

        }


        @Override
        protected String doInBackground(String... strings) {

            String urlBase = getResources().getString(R.string.app_server);
            String urlStr = urlBase + "/users/email/" + email + "/password/" + password;

            String result="";


            try {
                URL url = new URL(urlStr);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String value = br.readLine();
                result = value;


            } catch (Exception ex) {

            }

            return result;
        }

        @Override
        protected void onPostExecute(String value){
            super.onPostExecute(value);

            if (value.equals("VERIFIED")){

                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.apply();

                Intent myIntent = new Intent(LoginActivity.this, Main.class);
                startActivity(myIntent);
            }
            else if (value.equals(("DENIED"))){
                Toast.makeText(LoginActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(LoginActivity.this, "Cannot establish connection with server.", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
