package com.erwin.historygo;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import com.erwin.historygo.api.UserModel;
import com.erwin.historygo.api.UserComparator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Users extends AppCompatActivity {


    Button btnGetRanking;
    ListView tvRankingList;
    RequestQueue requestQueue;
    List<UserModel> usersList;
    String baseUrl = "https://dc3d2d22.ngrok.io";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_view);
        this.usersList = new ArrayList<>();
        this.btnGetRanking = (Button) findViewById(R.id.btn_get_rank);
        this.tvRankingList = (ListView) findViewById(R.id.tv_rank_list);
        // this.tvRankingList.setMovementMethod(new ScrollingMovementMethod());

        requestQueue = Volley.newRequestQueue(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    private void getRanking() {

        this.url = this.baseUrl + "/users/all";
        //   this.tvRankingList.setText(null);

        //  https://developer.android.com/training/volley/index.html

        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (response.length() > 0) {
                            usersList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    String name = jsonObj.get("name").toString();
                                    String points = jsonObj.get("points").toString();
                                    int pointsInt = Integer.parseInt(points);
                                    usersList.add(new UserModel(name, pointsInt));

                                    // addToRankingList(name, points);
                                } catch (JSONException e) {
                                    Log.e("Volley", "Invalid JSON Object.");
                                }
                            }
                        } else {
                            //  setRankingListText("No users found.");
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  setRankingListText("Error while calling REST API");
                        Log.e("Volley", error.toString());
                    }
                }
        );
        requestQueue.add(arrReq);
    }


    public void getRankingClicked(View v) {


        new JSONTask().execute();
         /*
        getRanking();


        Collections.sort(usersList, new UserComparator());
        List<String> usersToString = new ArrayList<String>();

        for (UserModel i : usersList) {
            String user = i.getName();
            String points = Integer.toString(i.getPoints());
            usersToString.add(user + "  |  " + points);
        }

        ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.row, R.id.textView1, usersToString);
        tvRankingList.setAdapter(adapter);
        tvRankingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedUser = "You've chosen: " + String.valueOf(parent.getItemAtPosition(position));

                Toast.makeText(Users.this, selectedUser, Toast.LENGTH_SHORT).show();
            }
        });

        */
    }


    public class JSONTask extends AsyncTask<String, String, List<UserModel>> {
        @Override
        protected List<UserModel> doInBackground(String... strings) {

            String url = "https://dc3d2d22.ngrok.io/users/all";
            final List<UserModel> userList = new ArrayList<UserModel>();

            JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if (response.length() > 0) {
                                userList.clear();
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject jsonObj = response.getJSONObject(i);
                                        String name = jsonObj.get("name").toString();
                                        String points = jsonObj.get("points").toString();
                                        int pointsInt = Integer.parseInt(points);
                                        userList.add(new UserModel(name, pointsInt));

                                        // addToRankingList(name, points);
                                    } catch (JSONException e) {
                                        Log.e("Volley", "Invalid JSON Object.");
                                    }
                                }
                            } else {
                                //  setRankingListText("No users found.");
                            }

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //  setRankingListText("Error while calling REST API");
                            Log.e("Error", error.toString());
                        }
                    }
            );
            requestQueue.add(arrReq);

            return userList;
        }


        protected void onPostExecute(final List<UserModel> result, Context context) {
            super.onPostExecute(result);

            Collections.sort(result, new UserComparator());
            List<String> usersToString = new ArrayList<String>();

            if (result != null) {
                for (UserModel i : result) {
                    String user = i.getName();
                    String points = Integer.toString(i.getPoints());
                    usersToString.add(user + "  |  " + points);
                }

                ListAdapter adapter = new ArrayAdapter<String>(context, R.layout.row, R.id.textView1, usersToString);
                tvRankingList.setAdapter(adapter);
                tvRankingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedUser = "You've chosen: " + String.valueOf(parent.getItemAtPosition(position));

                        Toast.makeText(Users.this, selectedUser, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }


    }
}


// https://developer.android.com/training/volley/index.html
// https://www.londonappdeveloper.com/consuming-a-json-rest-api-in-android/