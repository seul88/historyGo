package com.erwin.historygo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.*;



public class MainActivity extends AppCompatActivity {


    Button bt;
    public TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainz);

        this.bt = (Button) findViewById(R.id.button);
        this.tv = (TextView) findViewById(R.id.textView);

        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new task().execute();
            }
        });
    }

    public class task extends android.os.AsyncTask<String, String, String> {


        public String value;
        public String result;


        public String playerName;
        public String playerPoints;


        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {



            String urlStr = "https://c701059d.ngrok.io/users/all";

            try {
                URL url = new URL(urlStr);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                this.value = br.readLine();

                result = "";
                JSONArray array = new JSONArray(value);

                for (int i=0; i <= array.length(); i++) {
                    JSONObject player1 = array.getJSONObject(i);

                    String playerName =  player1.getString("name");
                    int points = player1.getInt("points");
                    String playerPoints = Integer.toString(points);

                    result += playerName + "  " + playerPoints + "\n";
                }

            }
            catch (Exception ex){

            }

            return value;
        }







        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, this.result, Toast.LENGTH_LONG ).show();
            tv.setText(this.result);
        }
    }


}




