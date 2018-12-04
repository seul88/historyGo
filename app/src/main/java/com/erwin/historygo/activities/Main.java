package com.erwin.historygo.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.erwin.historygo.R;
import com.erwin.historygo.api.PlaceModel;
import com.erwin.historygo.api.PlaceRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

        new GetUserDetailsTask().execute();

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
            case R.id.activity_my_profile:
                Intent intent4 = new Intent(this, MyProfile.class);
                this.startActivity(intent4);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }



    }








    public class GetUserDetailsTask extends android.os.AsyncTask<String, String, String> {

        String email;
        PlaceRepository places;
        String userName;
        int userPoints;
        String userCountry;
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            email = sharedPreferences.getString("email","");

        }


        @Override
        protected String doInBackground(String... strings) {


            String urlBase = getResources().getString(R.string.app_server);
            String urlStr = urlBase + "/users/email/" + email;

            String result="";
            String ret = "";
            places = new PlaceRepository();




            try {
                URL url = new URL(urlStr);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result += br.readLine();

                JSONObject obj = new JSONObject(result);

                userName = obj.getString("name");
                userPoints = obj.getInt("points");
                userCountry = obj.getString("country");

                JSONArray array = obj.getJSONArray("places");



                for (int i = 0; i < array.length(); i++) {
                    JSONObject placeObj = array.getJSONObject(i);

                    int id = placeObj.getInt("id");
                    String placeName = placeObj.getString("name");
                    int placePoints = placeObj.getInt("points");
                    String description = placeObj.getString("description");
                    int year = placeObj.getInt("year");
                    double latitude = placeObj.getDouble("gps_N");
                    double length = placeObj.getDouble(("gps_E"));

                    places.addPlace(new PlaceModel(id, placeName, placePoints, 0, description, year, latitude, length));
                }
                ret =  places.getPlaces().toString();

            } catch (Exception ex) {

            }

            return ret;
        }

        @Override
        protected void onPostExecute(String value){
            super.onPostExecute(value);


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userName", userName);
            editor.putString("userCountry", userCountry);
            editor.putInt("userPoints", userPoints);

            editor.apply();

        }
    }



}
