package com.example.q.slavskehelp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import myPackage.Connection.ConnectionClass;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
    public void dbTest(View view) {
       /* ConnectionClass connectionClass=new ConnectionClass();
        java.sql.Connection connection=connectionClass.CONN();*/
        /*if(connection==null)
            return; // тут ексепшен напишег
        try
        {
        String SQL1="INSERT INTO Auth_User" +
                "(login,password,name,surname,phone,email,city,can_add_note)" +
                "VALUES" +
                "('kolia','1111','Микола','Греб','+380965137938','koliahreb@meta.ua','Славське', 1)";
        PreparedStatement preparedStmt = connection.prepareStatement(SQL1);
        preparedStmt.executeUpdate();
    }
          		   catch (SQLException e) {
        e.printStackTrace();
    }*/
        //тут повинен робочий код. все ізі
        //підключення на вічному циклі у пошуках мережі
        // вийде з пошуку тільки сервер рішає, то 120 сек
        //все
    }

}
