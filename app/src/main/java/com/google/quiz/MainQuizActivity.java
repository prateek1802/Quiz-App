package com.google.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainQuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);

        Intent intent = getIntent();

        String s = intent.getStringExtra("List");
        TextView textView = findViewById(R.id.textView);

        textView.setText(s);
    }
}
