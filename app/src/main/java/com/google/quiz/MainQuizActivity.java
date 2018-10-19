package com.google.quiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MainQuizActivity extends AppCompatActivity {

    String Question = "";
    String Answer = "";
    String choiceOne = "", choiceTwo = "";

    int numberOfRows;
    int questionSerialNumber, optionOne, optionTwo;

    //Preventing going back to the previous Activity
    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);

        TextView question = findViewById(R.id.question);
        Button b1 = findViewById(R.id.buttonOne);
        Button b2 = findViewById(R.id.buttonTwo);
        Button b3 = findViewById(R.id.buttonThree);

        Intent intent = getIntent();
        String quizCategory = intent.getStringExtra("QuizCategory");


        //Deciding the quiz Category
        switch (quizCategory) {
            case "0":
                Log.i("Category", "Football");

                Football football = new Football(this);
                football.createDatabase();
                football.openDatabase();
                football.getWritableDatabase();

                numberOfRows = (int) football.getRowCount();
                questionSerialNumber = football.getSerialNumber(numberOfRows);
                optionOne = football.getOption(questionSerialNumber, numberOfRows);
                optionTwo = football.getOption(questionSerialNumber, optionOne, numberOfRows);

                Question = football.getQuestion("Question", questionSerialNumber);
                Answer = football.getQuestion("Answer", questionSerialNumber);
                choiceOne = football.getChoice(optionOne, numberOfRows);
                choiceTwo = football.getChoice(optionTwo, numberOfRows);

                break;

            case "1":
                Log.i("Category", "Cricket");

                Cricket cricket = new Cricket(this);
                cricket.createDatabase();
                cricket.openDatabase();
                cricket.getWritableDatabase();

                numberOfRows = (int) cricket.getRowCount();
                questionSerialNumber = cricket.getSerialNumber(numberOfRows);
                optionOne = cricket.getOption(questionSerialNumber, numberOfRows);
                optionTwo = cricket.getOption(questionSerialNumber, optionOne, numberOfRows);

                Question = cricket.getQuestion("Question", questionSerialNumber);
                Answer = cricket.getQuestion("Answer", questionSerialNumber);
                choiceOne = cricket.getChoice(optionOne, numberOfRows);
                choiceTwo = cricket.getChoice(optionTwo, numberOfRows);

                break;

            case "2":
                Log.i("Category", "General Knowledge");
                break;
            case "3":
                Log.i("Category", "Books");
                break;
            case "4":
                Log.i("Category", "Politics");
                break;
            case "5":
                Log.i("Category", "Bollywood");
                break;
            case "6":
                Log.i("Category", "TV Series");
                break;
            case "7":
                Log.i("Category", "Misc");
                break;
            default:
                Log.i("Message", "Error");
        }

        question.setText(Question);
        b1.setText(Answer);
        b2.setText(choiceOne);
        b3.setText(choiceTwo);
    }
}
