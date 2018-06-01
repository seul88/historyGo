package com.erwin.historygo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlaceManager extends AppCompatActivity {


    private Button button_Ratusz;
    private Button button_Zamek;
    private Button button_Politechnika;
    private Button button_Malta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_manager);

        button_Ratusz = (Button) findViewById(R.id.btnRatusz);
        button_Zamek = (Button) findViewById(R.id.btnZamek);
        button_Malta = (Button) findViewById(R.id.btnMalta);
        button_Politechnika = (Button) findViewById(R.id.btnPolitechnika);


        button_Politechnika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityPolitechnika();
            }
        });

        button_Malta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityMalta();
            }
        });

        button_Zamek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityZamek();
            }
        });

        button_Ratusz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityRatusz();
            }
        });

    }


    public void openActivityPolitechnika(){
        Intent intent = new Intent(this, Politechnika.class);
        startActivity(intent);
    }


    public void openActivityMalta(){
        Intent intent = new Intent(this, Malta.class);
        startActivity(intent);
    }

    public void openActivityRatusz(){
        Intent intent = new Intent(this, Ratusz.class);
        startActivity(intent);
    }

    public void openActivityZamek(){
        Intent intent = new Intent(this, Places.class);
        startActivity(intent);
    }

}
