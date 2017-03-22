package com.example.q.slavskehelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class News extends AppCompatActivity
       {
    public static String Auth_User="Авторизація не пройдена";

    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        DrawerLayout mDrawerLayout;
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

           public void search_activity_start(MenuItem item) {
               Intent newsIntent=new Intent(News.this, Search.class);
               startActivity(newsIntent);
           }

           public void web_cam_actiyity_start(MenuItem item) {
               Intent newsIntent=new Intent(News.this, Web_camera.class);
               startActivity(newsIntent);
           }
       }
