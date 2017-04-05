package com.example.q.slavskehelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import myPackage.Connection.ConnectionClass;

public class Registration extends AppCompatActivity
       {
           private ActionBarDrawerToggle mToggle;
           private EditText mLogin;
           private EditText mPassword;
           private EditText mName;
           private EditText mSurname;
           private EditText mPhone;
           private EditText mEmail;
           private EditText mCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        DrawerLayout mDrawerLayout;
        mLogin=(EditText) findViewById(R.id.login_input);
        mPassword=(EditText) findViewById(R.id.password_input);
        mName=(EditText) findViewById(R.id.name_input);
        mSurname=(EditText) findViewById(R.id.surname_input);
        mPhone=(EditText) findViewById(R.id.phone_input);
        mEmail=(EditText) findViewById(R.id.email_input);
        mCity=(EditText) findViewById(R.id.city_input);
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle=new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        Intent newsIntent=new Intent(Registration.this, News.class);
        startActivity(newsIntent);

    }
           public void login_activity_start(MenuItem item) {
               Intent newsIntent=new Intent(Registration.this, Login.class);
               startActivity(newsIntent);
           }

           public void Registration_click(View view) {
               ConnectionClass connectionClass=new ConnectionClass();
               java.sql.Connection connection=connectionClass.CONN();
               if(connection==null) {
                   Toast.makeText(Registration.this,
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
                   String SQL1="INSERT INTO Auth_User" +
                           "(login,password,name,surname,phone,email,city,can_add_note)" +
                           "VALUES" +
                           "('"+mLogin.getText().toString()+"','"+mPassword.getText().toString()+"','"+mName.getText().toString()+
                           "','"+mSurname.getText().toString()+"','"+mPhone.getText().toString()+"','"+mEmail.getText().toString()+
                           "','"+mCity.getText().toString()+"',"+can_add+")";
                   PreparedStatement preparedStmt = connection.prepareStatement(SQL1);
                   preparedStmt.executeUpdate();
                   connection.close();
               }
               catch (SQLException e) {
                   e.printStackTrace();
               }
               Intent profileIntent=new Intent(Registration.this, Profile.class);
               profileIntent.putExtra("auth_user",mLogin.getText().toString());
               News.Auth_User=mLogin.getText().toString();
               startActivity(profileIntent);

           }

           public void profile_activity_start(MenuItem item)
           {
               if(News.Auth_User.equals("Авторизація не пройдена"))
               {
                   Toast.makeText(Registration.this,
                           "Пройдіть авторизацію!!",
                           Toast.LENGTH_SHORT).show();
                   Intent newsIntent=new Intent(Registration.this, Login.class);
                   startActivity(newsIntent);

               }
               else
               {
                   Intent newsIntent=new Intent(Registration.this, Profile.class);
                   startActivity(newsIntent);
               }
           }

           public void search_activity_start(MenuItem item) {
               Intent newsIntent=new Intent(Registration.this, Search.class);
               startActivity(newsIntent);
           }

           public void web_cam_actiyity_start(MenuItem item) {
               Intent intent;
               switch (item.getItemId()) {
                   case R.id.nav_web_cam:
                       intent = new Intent(Registration.this, web_cam_other_version.class);
                       startActivity(intent);
                       break;
                   case R.id.nav_map:
                       intent = new Intent(Registration.this, MapsActivity.class);
                       startActivity(intent);
                       break;
                   case R.id.nav_wether:
                       intent = new Intent(Registration.this, Weather.class);
                       startActivity(intent);
                       break;
               }
           }
       }

