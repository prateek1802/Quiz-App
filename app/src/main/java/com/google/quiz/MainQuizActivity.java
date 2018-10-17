package com.google.quiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainQuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);
        TextView question = findViewById(R.id.question);
        String Question = "";
        String Answer = "";
        int questionSerialNumber;
        Intent intent = getIntent();
        String quizCategory = intent.getStringExtra("QuizCategory");


        //Deciding which database to Open and choose
        switch (quizCategory) {
            case "0":
                Log.i("Category", "Football");

                Football football = new Football(this);
                football.createDatabase();
                football.openDatabase();
                football.getWritableDatabase();

                int numberOfRows = (int) football.getRowCount();
                questionSerialNumber = football.getSerialNumber(numberOfRows);
                Question = football.getQuestion("Question", questionSerialNumber);
                Answer = football.getQuestion("Answer", questionSerialNumber);

                break;
            case "1":
                Log.i("Category", "Cricket");
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
    }
}
