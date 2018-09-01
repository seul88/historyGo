package com.erwin.historygo;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.erwin.historygo.api.UserComparator;
import com.erwin.historygo.api.UserModel;
import com.erwin.historygo.api.UserRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.*;


// http://www.vogella.com/tutorials/AndroidListView/article.html

public class MainActivity extends ListActivity {

/*
    Button bt;
    public TextView tv;
    public ListView lv;

*/

public ArrayAdapter<String> adapter;
public ArrayList<String> displayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        displayList = new ArrayList<>();
        /*
        setContentView(R.layout.activity_mainz);

        this.bt = (Button) findViewById(R.id.button);
        this.tv = (TextView) findViewById(R.id.textView);
        this.lv = (ListView) findViewById(R.id.listView);

        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new task().execute();
            }
        });
        */
        //displayList = new ArrayList<>();
       // displayList.add(":)");
        new task().execute();
         adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, displayList );
        adapter.notifyDataSetChanged();
       // setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, displayList ) );
        setListAdapter(adapter);

    }


    public void onListItemClick( ListView parent , View v, int position, long id){
        String selectedUser = "You've chosen: " + String.valueOf(parent.getItemAtPosition(position));
        Toast.makeText(this, selectedUser, Toast.LENGTH_SHORT).show();
    }

    public class task extends android.os.AsyncTask<String, String, String> {


        public String value;
        public String result;
        private UserRepository users;
        public String playerName;
        public String playerPoints;


        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Fetching users' data from server...", Toast.LENGTH_LONG ).show();
        }


        @Override
        protected String doInBackground(String... strings) {



            String urlStr = "https://d914013e.ngrok.io/users/all";
            users = new UserRepository();

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
                    JSONObject player1 = array.getJSONObject(i);

                    String playerName =  player1.getString("name");
                    int points = player1.getInt("points");
                    //String playerPoints = Integer.toString(points);
                    String email = player1.getString("email");
                    String country = player1.getString("country");
                    int id = player1.getInt("id");

                    users.addUser(new UserModel(playerName, points, email, country, id));
                   // result += tempUsr.toString() + "\n";
                }

            }
            catch (Exception ex){

            }

            return value;
        }







        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);

            Collections.sort(users.getUsers(), new UserComparator());



           // tv.setText(this.users.getUsers().toString());

            for (UserModel _user : users.getUsers()){
                    displayList.add(_user.getName() + "  " + _user.getPoints());
              }
            adapter.notifyDataSetChanged();
         //   setListAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, displayList ) );

        }
    }


}




