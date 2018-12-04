package com.erwin.historygo.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.erwin.historygo.R;
import com.erwin.historygo.adapters.PlaceAdapter;
import com.erwin.historygo.api.PlaceModel;
import com.erwin.historygo.api.PlaceRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PlacesList extends AppCompatActivity {



    public PlaceAdapter adapter;
    public ArrayList<PlaceModel> displayList;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        displayList = new ArrayList<PlaceModel>();

        this.adapter = new PlaceAdapter(this, displayList);

        this.listView = (ListView) findViewById(R.id.listView);

        this.listView.setAdapter(adapter);
        new task().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PlaceModel place = (PlaceModel) parent.getItemAtPosition(position);
                Intent myIntent = new Intent(PlacesList.this, PlaceActivity.class);
                String placeName = place.getName();
                String placeDescription = place.getDescription();
                String placePoints = Integer.toString(place.getPoints());
                String placeYear = Integer.toString(place.getYear());

                myIntent.putExtra("placeName", placeName);
                myIntent.putExtra("placeDescription", placeDescription);
                myIntent.putExtra("placePoints", placePoints);

                myIntent.putExtra("placeYear", placeYear);
                startActivity(myIntent);
            }
        });
    }



        public class task extends android.os.AsyncTask<String, String, String> {


            public String value;
            public String result;
            private PlaceRepository places;



            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                Toast.makeText(PlacesList.this, "Fetching places' data from server...", Toast.LENGTH_SHORT ).show();

                ProgressBar progressBar = new ProgressBar(PlacesList.this);
                progressBar.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER));
                progressBar.setIndeterminate(true);
                listView.setEmptyView(progressBar);

                ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                root.addView(progressBar);


            }


            @Override
            protected String doInBackground(String... strings) {


                String urlBase = getResources().getString(R.string.app_server);
                String urlStr = urlBase + "/places/all";
                places = new PlaceRepository();

                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.connect();

                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    this.value = br.readLine();

                    result = "";
                    JSONArray array = new JSONArray(value);


                    for (int i=0; i < array.length(); i++) {
                        JSONObject placeObj = array.getJSONObject(i);

                        String placeName =  placeObj.getString("name");
                        int points = placeObj.getInt("points");
                        String description = placeObj.getString("description");
                      //  double rating = placeObj.getDouble("rating");
                        int year = placeObj.getInt("year");

                        places.addPlace(new PlaceModel(placeName, points, description,
                                //country,
                                 year));
                    }
                }
                catch (Exception ex){
                }

                return value;
            }



            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);


                for (PlaceModel _places : places.getPlaces()){
                    adapter.add(_places);
                }
                adapter.notifyDataSetChanged();
            }
        }


}
