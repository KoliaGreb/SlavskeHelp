package com.example.q.slavskehelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Connection;
import android.view.View;

import myPackage.Connection.ConnectionClass;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void dbTest(View view) {
        ConnectionClass connectionClass=new ConnectionClass();
        java.sql.Connection connection=connectionClass.CONN();
        if(connection==null)
            return; // тут ексепшен напишег

        //тут повинен робочий код. все ізі
        //підключення на вічному циклі у пошуках мережі
        // вийде з пошуку тільки сервер рішає, то 120 сек
        //все
    }
}
