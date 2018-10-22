package com.google.quiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainQuizActivity extends AppCompatActivity {
    Random rand = new Random();

    TextView question;
    TextView category;
    TextView score;

    Button b1, b2, b3;

    Football football;
    Cricket cricket;

    String Question = "";
    String Answer = "";
    String choiceOne = "", choiceTwo = "";

    int numberOfRows;
    int questionSerialNumber, optionOne, optionTwo;
    int totalOptions = 3;
    int locationCorrectAnswer;
    int totalQuestion = 8;
    int correctAnswer = 0;
    int currentQuestion = 0;
    int questionLength;

    ArrayList<String> choice = new ArrayList<>();
    String[] answers = new String[totalOptions];
    String quizCategory;

    //Preventing going back to the previous Activity
    @Override
    public void onBackPressed() {
        return;
    }

    //When any Option is pressed
    public void chosenOption(View view) {
        if (view.getTag().toString().equals(Integer.toString(locationCorrectAnswer))) {
            Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
            correctAnswer++;
        } else {
            Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
        }
        currentQuestion++;
        newQuestion(quizCategory);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);

        Intent intent = getIntent();
        quizCategory = intent.getStringExtra("QuizCategory");

        category = findViewById(R.id.category);
        score = findViewById(R.id.score);
        question = findViewById(R.id.question);
        b1 = findViewById(R.id.buttonOne);
        b2 = findViewById(R.id.buttonTwo);
        b3 = findViewById(R.id.buttonThree);

        //Deciding the quiz Category
        switch (quizCategory) {
            case "0":
                football = new Football(this);
                football.createDatabase();
                football.openDatabase();
                football.getWritableDatabase();

                newQuestion(quizCategory);
                break;

            case "1":
                cricket = new Cricket(this);
                cricket.createDatabase();
                cricket.openDatabase();
                cricket.getWritableDatabase();

                newQuestion(quizCategory);
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

    public void newQuestion(String quizCategory) {
        if (quizCategory.equals("0")) {
            quizCategory = "0";
            category.setText("Football");
            numberOfRows = (int) football.getRowCount();
            questionSerialNumber = football.getSerialNumber(numberOfRows);
            optionOne = football.getOption(questionSerialNumber, numberOfRows);
            optionTwo = football.getOption(questionSerialNumber, optionOne, numberOfRows);

            Question = football.getQuestion("Question", questionSerialNumber);
            Answer = football.getQuestion("Answer", questionSerialNumber);
            choiceOne = football.getChoice(optionOne, numberOfRows);
            choiceTwo = football.getChoice(optionTwo, numberOfRows);
        } else if (quizCategory.equals("1")) {
            category.setText("Cricket");
            quizCategory = "1";
            numberOfRows = (int) cricket.getRowCount();
            questionSerialNumber = cricket.getSerialNumber(numberOfRows);
            optionOne = cricket.getOption(questionSerialNumber, numberOfRows);
            optionTwo = cricket.getOption(questionSerialNumber, optionOne, numberOfRows);

            Question = cricket.getQuestion("Question", questionSerialNumber);
            Answer = cricket.getQuestion("Answer", questionSerialNumber);
            choiceOne = cricket.getChoice(optionOne, numberOfRows);
            choiceTwo = cricket.getChoice(optionTwo, numberOfRows);

        }

        if (questionLength < 35) {
            question.setTextSize(45);
        }
        if (questionLength >= 35 && questionLength <= 50) {
            question.setTextSize(40);
        } else {
            question.setTextSize(35);
        }
        question.setText(Question);

        choice.add(choiceOne);
        choice.add(choiceTwo);

        locationCorrectAnswer = rand.nextInt(totalOptions);

        for (int i = 0; i < totalOptions; i++) {
            if (i == locationCorrectAnswer) {
                answers[i] = Answer;
            } else {
                int n = rand.nextInt(choice.size());
                answers[i] = choice.get(n);
                choice.remove(n);
            }
        }

        score.setText(Integer.toString(correctAnswer) + "/" + Integer.toString(currentQuestion));

        b1.setText(answers[0]);
        b2.setText(answers[1]);
        b3.setText(answers[2]);

        totalQuestion--;

        if (totalQuestion == 0) {
            totalQuestion = 8;
            int scorePassed = correctAnswer;
            correctAnswer = 0;
            currentQuestion = 0;
            Intent intent = new Intent(getApplicationContext(), TotalScoreActivity.class);
            intent.putExtra("Score", Integer.toString(scorePassed));
            startActivity(intent);
        }
    }
}
