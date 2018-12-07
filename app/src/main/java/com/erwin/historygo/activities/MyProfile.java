package com.erwin.historygo.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.erwin.historygo.R;


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
