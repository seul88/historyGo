package com.erwin.historygo.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.erwin.historygo.R;
import com.erwin.historygo.api.QuizQuestionModel;
import com.erwin.historygo.api.QuizQuestionRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Quiz extends AppCompatActivity {


    public QuizQuestionRepository questionRepository;
    public  Button btn1;
    public  Button btn2;
    public  Button btn3;
    public  Button btn4;
    public  TextView tv;
    public AlertDialog.Builder builder;
    public int questionCounter;
    public int correctAnswerCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        tv = (TextView) findViewById(R.id.tvQuiz);


        questionRepository = new QuizQuestionRepository();

        Intent myIntent = getIntent();
        String json = myIntent.getStringExtra("json");
        questionCounter = 0;
        correctAnswerCounter = 0;



        JSONArray array;
        try {
            array = new JSONArray(json);
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
                builder = new AlertDialog.Builder(this);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }





        displayNextQuestion();



    }



    public void displayNextQuestion(){

        tv.setText(questionRepository.getQuestions().get(questionCounter).getQuestion());
        btn1.setText(questionRepository.getQuestions().get(questionCounter).getA());
        btn2.setText(questionRepository.getQuestions().get(questionCounter).getB());
        btn3.setText(questionRepository.getQuestions().get(questionCounter).getC());
        btn4.setText(questionRepository.getQuestions().get(questionCounter).getD());

    }




    public void checkAnswer(View view){
        String correct = questionRepository.getQuestions().get(questionCounter).getCorrectAnswer();
        Button answer = (Button) findViewById(view.getId());
        String answerText = answer.getText().toString();

        String response;


        if (answerText.equals(correct)) {
            response = "Correct";
            correctAnswerCounter++;
        }
        else {
            response = "Wrong";
        }



        builder.setTitle(answerText);
        builder.setMessage("Correct answer is: "+ correct);
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (questionCounter == 4){


                    new AddUserPoints().execute();
                    Toast.makeText(Quiz.this,  "Your result is: "+correctAnswerCounter+"/5", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Quiz.this, Main.class);
                    Quiz.this.startActivity(intent);
                }
                else {
                    questionCounter++;
                    displayNextQuestion();
                }
            }
        });

    builder.setCancelable(false);
    builder.show();
    }

    public class AddUserPoints extends android.os.AsyncTask<String, String, String> {

       public SharedPreferences sharedPreferences;
       public String userName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            userName = sharedPreferences.getString("userName","");

        }


        @Override
        protected String doInBackground(String... strings) {
            String urlBase = getResources().getString(R.string.app_server);
            String endpoint = "/users/name/"+userName+"/points/" + correctAnswerCounter;
            String queryUrl = urlBase+endpoint;

            URL url;
            try {
                url = new URL(queryUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String result = br.readLine();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }

    }
}
