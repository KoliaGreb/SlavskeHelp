package com.example.q.slavskehelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class News extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
    }

    public void onclickmenuTest(View view) {
        setContentView(R.layout.activity_left_menu);
    }
}
