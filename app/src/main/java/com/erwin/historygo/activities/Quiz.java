package com.erwin.historygo.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.erwin.historygo.R;
import com.erwin.historygo.api.QuizQuestionModel;
import com.erwin.historygo.api.QuizQuestionRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Quiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final Button btn1 = (Button) findViewById(R.id.btn1);
        final Button btn2 = (Button) findViewById(R.id.btn2);
        final Button btn3 = (Button) findViewById(R.id.btn3);
        final Button btn4 = (Button) findViewById(R.id.btn4);

        new QuizFetchTask().execute();
    }


    public class QuizFetchTask extends android.os.AsyncTask<String, String, String> {


        QuizQuestionRepository questionRepository;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            questionRepository = new QuizQuestionRepository();
        }


        @Override
        protected String doInBackground(String... strings) {

            String urlBase = getResources().getString(R.string.app_server);
            String urlStr = urlBase + "/questions/random";

            String result="";


            try {
                URL url = new URL(urlStr);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String value = br.readLine();

                result = "";
                JSONArray array = new JSONArray(value);



                for (int i=0; i < array.length(); i++) {
                    JSONObject questionObj = array.getJSONObject(i);
                    String a = questionObj.getString("a");
                    String b = questionObj.getString("b");
                    String c = questionObj.getString("c");
                    String d = questionObj.getString("d");
                    String correctAnswer = questionObj.getString("correctAnswer");
                    String question = questionObj.getString("question");

                    QuizQuestionModel questionModel = new QuizQuestionModel(a,b,c,d,correctAnswer,question);
                    questionRepository.addQuestion(questionModel);

                }




            } catch (Exception ex) {

            }

            return result;
        }

        @Override
        protected void onPostExecute(String value){
            super.onPostExecute(value);


                for (QuizQuestionModel question : questionRepository.getQuestions() ){

                    Toast.makeText(Quiz.this,  question.getA(), Toast.LENGTH_SHORT).show();

                }


        }
    }
}
