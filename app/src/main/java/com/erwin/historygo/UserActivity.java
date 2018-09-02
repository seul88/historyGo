package com.erwin.historygo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent myIntent = getIntent();
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvPoints = (TextView) findViewById(R.id.tvPoints);
        TextView tvCountry = (TextView) findViewById(R.id.tvCountry);


        tvName.setText(myIntent.getStringExtra("userName"));
        tvPoints.setText(myIntent.getStringExtra("userPoints"));
        tvCountry.setText(myIntent.getStringExtra("userCountry"));
        Toast.makeText(UserActivity.this, myIntent.getStringExtra("userName"), Toast.LENGTH_SHORT).show();
    }


}
