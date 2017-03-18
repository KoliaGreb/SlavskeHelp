package com.example.q.slavskehelp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import myPackage.Connection.ConnectionClass;

public class Login extends AppCompatActivity
{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private EditText mLogin;
    private EditText mPassword;
    private LinearLayout mError_layout;
    private TextView mError_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLogin=(EditText) findViewById(R.id.login_sing_in);
        mPassword=(EditText) findViewById(R.id.password_sing_in);
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        mError_layout=(LinearLayout) findViewById(R.id.layout_error);
        mError_text=(TextView) findViewById(R.id.error_text);
        mToggle=new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void news_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(Login.this, News.class);
        startActivity(newsIntent);

    }
    public void login_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(Login.this, Login.class);
        startActivity(newsIntent);
    }

    public void registration_start(View view) {
        Intent newsIntent=new Intent(Login.this, Registration.class);
        startActivity(newsIntent);
    }

    public void Sing_inClick(View view) {
        ConnectionClass connectionClass=new ConnectionClass();
        java.sql.Connection connection=connectionClass.CONN();
        if(connection==null) {
            Toast.makeText(Login.this,
                    "Сервер не доступний, вибачте за незручності!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            String SQL1="SELECT login, password FROM Auth_User";
            stmt = connection.createStatement();
            rs =  stmt.executeQuery(SQL1);
            int error_pass=0;
            while (rs.next()) {
                if(rs.getString(1).equals(mLogin.getText().toString())&& rs.getString(2).equals(mPassword.getText().toString()))
                {
                    error_pass=-1;
                    Intent profileIntent=new Intent(Login.this, Profile.class);
                    News.Auth_User=mLogin.getText().toString();
                    startActivity(profileIntent);
                }
                else if(rs.getString(1).equals(mLogin.getText().toString()))
                {
                    error_pass++;
                    break;
                }
            }
            if(error_pass!=0 && error_pass!=-1)
            {
                mError_text.setText("Не вірний пароль!");
                mError_layout.setVisibility(View.VISIBLE);
            }
            else if(error_pass!=-1)
            {
                mError_text.setText("Не вірні логін та пароль!");
                mError_layout.setVisibility(View.VISIBLE);
            }
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void profile_activity_start(MenuItem item) {
        if(News.Auth_User.equals("Авторизація не пройдена"))
        {
            Toast.makeText(Login.this,
                    "Пройдіть авторизацію!!",
                    Toast.LENGTH_SHORT).show();
            Intent newsIntent=new Intent(Login.this, Login.class);
            startActivity(newsIntent);

        }
        else
        {
            Intent newsIntent=new Intent(Login.this, Profile.class);
            startActivity(newsIntent);
        }
    }
}
