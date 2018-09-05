package com.erwin.historygo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.erwin.historygo.R;

public class Main extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.btStartGame);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Main.this, Map.class);
                startActivity(myIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.activity_maps:
                Intent intent1 = new Intent(this, Map.class);
                this.startActivity(intent1);
                return true;
            case R.id.activity_user_ranking:
                Intent intent2 = new Intent(this, UsersRankingActivity.class);
                this.startActivity(intent2);
                return true;

            case R.id.activity_place:
                Intent intent3 = new Intent(this, PlacesList.class);
                this.startActivity(intent3);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }



    }
}
