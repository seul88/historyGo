package com.erwin.historygo.activities;

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
import android.widget.TextView;
import android.widget.Toast;

import com.erwin.historygo.R;
import com.erwin.historygo.api.PlaceModel;
import com.erwin.historygo.api.PlaceRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class MyProfile extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String userName = sharedPreferences.getString("userName","");
        String userCountry = sharedPreferences.getString("userCountry","");
        int userPoints = sharedPreferences.getInt("userPoints",0);

        TextView  tvname =  (TextView) findViewById(R.id.tvName);
        TextView tvpoints = (TextView) findViewById(R.id.tvPoints);
        TextView tvcountry = (TextView) findViewById(R.id.tvCountry);

        tvname.setText(userName);
        tvpoints.setText(Integer.toString(userPoints));
        tvcountry.setText(userCountry);


    }


}
