package com.example.q.slavskehelp;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import myPackage.Connection.ConnectionClass;

public class Profile extends AppCompatActivity {
    private ActionBarDrawerToggle mToggle;
    private EditText mLogin;
    private EditText mName;
    private EditText mSurname;
    private EditText mPhone;
    private EditText mEmail;
    private EditText mCity;
    private TextView mText_Change_Profile;
    private Button mButton_Change_Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        DrawerLayout mDrawerLayout;
        mLogin=(EditText) findViewById(R.id.login_profile);
        mName=(EditText) findViewById(R.id.name_profile);
        mSurname=(EditText) findViewById(R.id.surname_profile);
        mPhone=(EditText) findViewById(R.id.phone_profile);
        mEmail=(EditText) findViewById(R.id.email_profile);
        mCity=(EditText) findViewById(R.id.city_profile);
        mText_Change_Profile=(TextView) findViewById(R.id.text_change_profile);
        mButton_Change_Profile=(Button) findViewById(R.id.button_change_profile);
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle=new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ConnectionClass connectionClass=new ConnectionClass();
        java.sql.Connection connection=connectionClass.CONN();
        if(connection==null) {
            Toast.makeText(Profile.this,
                    "Сервер не доступний, вибачте за незручності!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        try
        {
            String SQL1="SELECT login, name, surname, phone, email, city FROM Auth_User WHERE login='"
                    +News.Auth_User+"'";
            Statement stmt = connection.createStatement();
            ResultSet rs =  stmt.executeQuery(SQL1);
            while (rs.next()) {
                mLogin.setText(rs.getString(1));
                mName.setText(rs.getString(2));
                mSurname.setText(rs.getString(3));
                mPhone.setText(rs.getString(4));
                mEmail.setText(rs.getString(5));
                mCity.setText(rs.getString(6));
            }
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item)||super.onOptionsItemSelected(item);
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
        Intent newsIntent=new Intent(Profile.this, News.class);
        startActivity(newsIntent);

    }
    public void login_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(Profile.this, Login.class);
        startActivity(newsIntent);
    }

    public void registration_start(View view) {
        Intent newsIntent=new Intent(Profile.this, Registration.class);
        startActivity(newsIntent);
    }

    public void Change_profile_text_click(View view) {
        mName.setEnabled(true);
        mSurname.setEnabled(true);
        mPhone.setEnabled(true);
        mEmail.setEnabled(true);
        mCity.setEnabled(true);
        mText_Change_Profile.setVisibility(View.GONE);
        mButton_Change_Profile.setVisibility(View.VISIBLE);

    }

    public void Change_profile_button_click(View view) {
        ConnectionClass connectionClass=new ConnectionClass();
        java.sql.Connection connection=connectionClass.CONN();
        if(connection==null) {
            Toast.makeText(Profile.this,
                    "Сервер не доступний, вибачте за незручності!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        try
        {
            int can_add=0;
            if(mCity.getText().toString().equals("Славське")||
                    mCity.getText().toString().equals("Славсько")||
                    mCity.getText().toString().equals("Slavske")||
                    mCity.getText().toString().equals("Slavsko"))
            {
                can_add=1;
            }
            String SQL1="UPDATE Auth_User SET name='"+mName.getText().toString()+"', surname='"+mSurname.getText().toString()+
                    "', phone='" +mPhone.getText().toString()+"', email='" +mEmail.getText().toString()+
                    "', city='"+mCity.getText().toString()+"', can_add_note="+can_add+" WHERE login='"+mLogin.getText().toString()+"'";
            PreparedStatement preparedStmt = connection.prepareStatement(SQL1);
            preparedStmt.executeUpdate();
            connection.close();
            Toast.makeText(Profile.this,
                    "Дані оновлено!",
                    Toast.LENGTH_SHORT).show();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        mName.setEnabled(false);
        mSurname.setEnabled(false);
        mPhone.setEnabled(false);
        mEmail.setEnabled(false);
        mCity.setEnabled(false);
        mText_Change_Profile.setVisibility(View.VISIBLE);
        mButton_Change_Profile.setVisibility(View.GONE);
    }

    public void profile_activity_start(MenuItem item)
    {
        if(News.Auth_User.equals("Авторизація не пройдена"))
        {
            Toast.makeText(Profile.this,
                    "Пройдіть авторизацію!!",
                    Toast.LENGTH_SHORT).show();
            Intent newsIntent=new Intent(Profile.this, Login.class);
            startActivity(newsIntent);

        }
        else
        {
            Intent newsIntent=new Intent(Profile.this, Profile.class);
            startActivity(newsIntent);
        }
    }

    public void search_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(Profile.this, Search.class);
        startActivity(newsIntent);
    }
}
