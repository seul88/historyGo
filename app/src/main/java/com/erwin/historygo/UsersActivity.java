package com.erwin.historygo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

import com.erwin.historygo.api.User;
import com.erwin.historygo.api.UserComparator;
import com.erwin.historygo.api.UserRepository;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UsersActivity extends AppCompatActivity {


    Button btnGetRanking;
    ListView tvRankingList;
    RequestQueue requestQueue;
    List<User> usersList;
    String baseUrl = "https://f642940a.ngrok.io";
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

/*
    private void addToRankingList(String user, String points) {

        //  cast String -> int
        String strRow = user + "  |  " + points;
        String currentText = tvRankingList.getText().toString();
        this.tvRankingList.setText(currentText + "\n\n" + strRow);

    }

    private void setRankingListText(String str) {
        this.tvRankingList.
    }
*/

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
                                    int pointsInt =  Integer.parseInt(points);
                                    usersList.add(new User(name, pointsInt));

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

        UserAsyncTask task = new UserAsyncTask();
        task.execute();


       // getRanking();


        Collections.sort(usersList, new UserComparator());
        List<String> usersToString = new ArrayList<String>();

        for (User i : usersList){
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

            Toast.makeText(UsersActivity.this, selectedUser, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class UserAsyncTask extends AsyncTask<URL,Void,UserRepository>{

        @Override
        protected UserRepository doInBackground(URL... urls) {
            String address = "https://5007a819.ngrok.io/users/all";
            final UserRepository repository = new UserRepository();
            JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, address,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {


                            if (response.length() > 0) {
                                repository.clearList();
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject jsonObj = response.getJSONObject(i);
                                        String name = jsonObj.get("name").toString();
                                        String points = jsonObj.get("points").toString();
                                        int pointsInt =  Integer.parseInt(points);
                                        User user = new User(name, pointsInt);
                                        repository.addUser(user);

                                    } catch (JSONException e) {
                                        Log.e("Volley", "Invalid JSON Object.");
                                    }


                                }

                            }


                            else {
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
        return repository;
        }

    }


    }

// https://developer.android.com/training/volley/index.html
// https://www.londonappdeveloper.com/consuming-a-json-rest-api-in-android/