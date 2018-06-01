package com.erwin.historygo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Malta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malta);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
