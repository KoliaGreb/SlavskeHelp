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
import android.widget.TextView;
import android.widget.Toast;

public class News extends AppCompatActivity
       {
    public static String Auth_User="Авторизація не пройдена";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
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
        if (mToggle.onOptionsItemSelected(item))
            return true;

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

            public void news_activity_start(MenuItem item)
            {
                Intent newsIntent=new Intent(News.this, News.class);
                startActivity(newsIntent);

            }

           public void login_activity_start(MenuItem item)
           {
               Intent newsIntent=new Intent(News.this, Login.class);
               startActivity(newsIntent);
           }
           public void profile_activity_start(MenuItem item)
           {
               if(Auth_User.equals("Авторизація не пройдена"))
               {
                   Toast.makeText(News.this,
                           "Пройдіть авторизацію!!",
                           Toast.LENGTH_SHORT).show();
                   Intent newsIntent=new Intent(News.this, Login.class);
                   startActivity(newsIntent);

               }
               else
               {
                   Intent newsIntent=new Intent(News.this, Profile.class);
                   startActivity(newsIntent);
               }
           }
       }
