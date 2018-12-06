package com.erwin.historygo.api;

import java.util.ArrayList;
import java.util.List;

public class QuizQuestionRepository {


    private List<QuizQuestionModel> questions;

    public List<QuizQuestionModel> getQuestions(){
        return this.questions;
    }

    public void addQuestion(QuizQuestionModel question){
        this.questions.add(question);
    }

    public void clearList(){this.questions.clear();}

    public QuizQuestionRepository(){
        this.questions = new ArrayList<>();
    }

}
