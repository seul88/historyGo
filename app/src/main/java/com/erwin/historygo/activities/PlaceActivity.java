package com.erwin.historygo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.erwin.historygo.R;

public class PlaceActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        Intent myIntent = getIntent();

        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvPoints = (TextView) findViewById(R.id.tvPoints);
        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
//      TextView tvRating = (TextView) findViewById(R.id.tvRating);
        TextView tvYear = (TextView) findViewById(R.id.tvYear);


        tvName.setText(myIntent.getStringExtra("placeName"));
        tvPoints.setText(myIntent.getStringExtra("placePoints"));
        tvYear.setText(myIntent.getStringExtra("placeYear"));
        tvDescription.setText(myIntent.getStringExtra("placeDescription"));
        tvDescription.setMovementMethod(new ScrollingMovementMethod());


    }


}



