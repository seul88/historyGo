package com.erwin.historygo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                Intent intent1 = new Intent(this, Maps.class);
                this.startActivity(intent1);
                return true;
            case R.id.activity_users_view:
                Intent intent2 = new Intent(this, Users.class);
                this.startActivity(intent2);
                return true;

            case R.id.activity_places:
                Intent intent3 = new Intent(this, Places.class);
                this.startActivity(intent3);
                return true;

            case R.id.activity_malta:
                Intent intent4 = new Intent(this, Malta.class);
                this.startActivity(intent4);
                return true;

            case R.id.activity_politechnika:
                Intent intent5 = new Intent(this, Politechnika.class);
                this.startActivity(intent5);
                return true;

            case R.id.activity_ratusz:
                Intent intent6 = new Intent(this, Ratusz.class);
                this.startActivity(intent6);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }



    }
}
