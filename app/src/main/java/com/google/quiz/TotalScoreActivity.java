package com.google.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TotalScoreActivity extends AppCompatActivity {

    TextView score;

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_score);

//        Intent intent = getIntent();
  //      String s = intent.getStringExtra("CorrectAnswer");

        score.setTextSize(20);
    }
}
