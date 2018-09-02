package com.erwin.historygo;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.erwin.historygo.adapters.RankingAdapter;
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

public class MainActivity extends AppCompatActivity {

/*
    Button bt;
    public TextView tv;
    public ListView lv;

*/
/*
public ArrayAdapter<String> adapter;
public ArrayList<String> displayList;
*/

    public RankingAdapter adapter;
    public ArrayList<UserModel> displayList;
    public ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainz);
        displayList = new ArrayList<UserModel>();


/*
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



       // setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, displayList ) );



        this.adapter = new RankingAdapter(this, displayList );

        this.listView = (ListView) findViewById(R.id.listView);

        this.listView.setAdapter(adapter);
        new task().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                UserModel user = (UserModel) parent.getItemAtPosition(position);
                Intent myIntent = new Intent(MainActivity.this, UserActivity.class);
                String userName = user.getName();
                String userCountry = user.getCountry();
                String userPoints = Integer.toString(user.getPoints());


                myIntent.putExtra("userName",userName);
                myIntent.putExtra("userCountry",userCountry);
                myIntent.putExtra("userPoints",userPoints);
                startActivity(myIntent);
               // String selectedUser =   user.getName() + "  " + user.getCountry() + "  " + user.getEmail();
               // Toast.makeText(MainActivity.this, selectedUser, Toast.LENGTH_SHORT).show();
            }
        });

    }

/*
    public void onItermClick( ListView parent , View v, int position, long id){
        String selectedUser = "You've chosen: " + String.valueOf(parent.getItemAtPosition(position));
        Toast.makeText(this, selectedUser, Toast.LENGTH_SHORT).show();
    }
*/
    public class task extends android.os.AsyncTask<String, String, String> {


        public String value;
        public String result;
        private UserRepository users;
        public String playerName;
        public String playerPoints;


        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Fetching users' data from server...", Toast.LENGTH_SHORT ).show();

            ProgressBar progressBar = new ProgressBar(MainActivity.this);
            progressBar.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER));
            progressBar.setIndeterminate(true);
            listView.setEmptyView(progressBar);

            ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
            root.addView(progressBar);


        }


        @Override
        protected String doInBackground(String... strings) {



            String urlStr = "https://82b56a19.ngrok.io/users/all";
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
                    displayList.add(_user);
                //adapter.add(_user);
              }
            adapter.notifyDataSetChanged();
         //   setListAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, displayList ) );

        }
    }


}




