package com.example.q.slavskehelp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import myPackage.Connection.GetWeatherFromApi;

public class Weather extends AppCompatActivity {
    private ActionBarDrawerToggle mToggle;
    private View wether1;
    private View wether2;
    private View wether3;
    public static TextView city_name;
    public static TextView temp_value;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        wether1=(View) findViewById(R.id.navigation_today);
        wether2=(View) findViewById(R.id.navigation_5day);
        wether3=(View) findViewById(R.id.navigation_14day);
        wether1.setBackgroundColor(getResources().getColor(R.color.accent_color));
        wether2.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
        wether3.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
        DrawerLayout mDrawerLayout;
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle=new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.weather_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        city_name= (TextView) findViewById(R.id.city);
        temp_value=(TextView) findViewById(R.id.temperature);
        GetWeatherFromApi getWetherFromApi=new GetWeatherFromApi();
        getWetherFromApi.execute("http://api.openweathermap.org/data/2.5/weather?q=Slavske&mode=json&lang=ua&appid=1f37ab01b1fa87cb404e66109a863603");

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_today:
                    wether1.setBackgroundColor(getResources().getColor(R.color.accent_color));
                    wether2.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                    wether3.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                    return true;
                case R.id.navigation_5day:
                    wether1.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                    wether2.setBackgroundColor(getResources().getColor(R.color.accent_color));
                    wether3.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                     return true;
                case R.id.navigation_14day:
                    wether1.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                    wether2.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                    wether3.setBackgroundColor(getResources().getColor(R.color.accent_color));
                  return true;
            }
            return false;
        }

    };
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
        Intent newsIntent=new Intent(Weather.this, News.class);
        startActivity(newsIntent);

    }
    public void login_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(Weather.this, Login.class);
        startActivity(newsIntent);
    }

    public void registration_start(View view) {
        Intent newsIntent=new Intent(Weather.this, Registration.class);
        startActivity(newsIntent);
    }
    public void profile_activity_start(MenuItem item) {
        if(News.Auth_User.equals("Авторизація не пройдена"))
        {
            Toast.makeText(Weather.this,
                    "Пройдіть авторизацію!!",
                    Toast.LENGTH_SHORT).show();
            Intent newsIntent=new Intent(Weather.this, Login.class);
            startActivity(newsIntent);

        }
        else
        {
            Intent newsIntent=new Intent(Weather.this, Profile.class);
            startActivity(newsIntent);
        }
    }

    public void search_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(Weather.this, Search.class);
        startActivity(newsIntent);
    }
    public void web_cam_actiyity_start(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_web_cam:
                intent = new Intent(Weather.this, web_cam_other_version.class);
                startActivity(intent);
                break;
            case R.id.nav_map:
                intent = new Intent(Weather.this, MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_wether:
                intent = new Intent(Weather.this, Weather.class);
                startActivity(intent);
                break;
        }
    }
}
