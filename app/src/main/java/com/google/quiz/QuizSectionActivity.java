package com.google.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.drawable.ClipDrawable.HORIZONTAL;

public class QuizSectionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_section);

        recyclerView = findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        List<String> input = new ArrayList<>();

        input.add("Football");
        input.add("Cricket");
        input.add("General Knowledge");
        input.add("Books");

        mAdapter = new MyAdapter(input);
        recyclerView.setAdapter(mAdapter);

        //For Horizontal lines between each item
//        DividerItemDecoration itemDecor = new DividerItemDecoration(this, HORIZONTAL);
//        recyclerView.addItemDecoration(itemDecor);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

    }
}
