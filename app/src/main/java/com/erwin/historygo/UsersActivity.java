package com.erwin.historygo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class UsersActivity extends AppCompatActivity {


    Button btnGetRanking;
    TextView tvRankingList;
    RequestQueue requestQueue;

    String baseUrl =  "https://c72ea029.ngrok.io/users/all";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_view);

        this.btnGetRanking = (Button) findViewById(R.id.btn_get_rank);
        this.tvRankingList = (TextView) findViewById(R.id.tv_rank_list);
        this.tvRankingList.setMovementMethod(new ScrollingMovementMethod());

        requestQueue = Volley.newRequestQueue(this);

    }


    private void addToRankingList(String repoName, String lastUpdated) {

        /*   cast String -> int           */
        String strRow = repoName + " / " + lastUpdated;
        String currentText = tvRankingList.getText().toString();
        this.tvRankingList.setText(currentText + "\n\n" + strRow);
    }

    private void setRankingListText(String str) {
        this.tvRankingList.setText(str);
    }


    private void getRanking() {

        this.url = this.baseUrl;

        //  https://developer.android.com/training/volley/index.html

        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (response.length() > 0) {

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    String name = jsonObj.get("name").toString();
                                    String points = jsonObj.get("points").toString();
                                    addToRankingList(name, points);
                                } catch (JSONException e) {
                                    Log.e("Volley", "Invalid JSON Object.");
                                }
                            }
                        } else {
                            setRankingListText("No users found.");
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setRankingListText("Error while calling REST API");
                        Log.e("Volley", error.toString());
                    }
                }
        );
        requestQueue.add(arrReq);
    }

    public void getRankingClicked(View v) {

        getRanking();
        // sort ranking
    }
}
