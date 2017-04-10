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
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import myPackage.Connection.GetWeatherFromApi;

public class Weather extends AppCompatActivity {
    private ActionBarDrawerToggle mToggle;
    private View wether1;
    private View wether2;
    private View wether3;
    public static TextView temp_value;
    public static TextView weather_value;
    public static TextView weather_wind;
    public static TextView weather_humidity;
    public static ImageView icon_image;
    public static LinearLayout weather_today_Layout;
    public static ScrollView weather_5day_Layout;
    public static ScrollView weather_14day_Layout;
    public static ImageView image14[]=new ImageView[14];
    public static TextView temperature14[]=new TextView[14];
    public static TextView wind14[]=new TextView[14];
    public static TextView humidity14[]=new TextView[14];
    public static TextView weather_value14[]=new TextView[14];
    public static TextView date14[]=new TextView[14];
    public static LinearLayout weather14_Layout[]=new LinearLayout[14];
    public static LinearLayout weather5_Layout_main;
    public static LinearLayout linlaHeaderProgress;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        weather5_Layout_main=(LinearLayout) findViewById(R.id.layout_weather_5day);
        linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        wether1=(View) findViewById(R.id.navigation_today);
        wether2=(View) findViewById(R.id.navigation_5day);
        wether3=(View) findViewById(R.id.navigation_14day);
        weather_today_Layout=(LinearLayout) findViewById(R.id.weather_today);
        weather_today_Layout.setBackgroundResource(R.drawable.button_shadow);
        weather_5day_Layout=(ScrollView) findViewById(R.id.weather_5day);
        weather_14day_Layout=(ScrollView) findViewById(R.id.weather_14day);
        weather_today_Layout.setVisibility(View.VISIBLE);
        weather_5day_Layout.setVisibility(View.GONE);
        weather_14day_Layout.setVisibility(View.GONE);
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
        temp_value=(TextView) findViewById(R.id.temperature);
        weather_value=(TextView) findViewById(R.id.weather_value);
        weather_wind=(TextView) findViewById(R.id.weather_wind);
        weather_humidity=(TextView) findViewById(R.id.weather_humidity);
        icon_image=(ImageView) findViewById(R.id.weather_icon);
        TextView date = (TextView) findViewById(R.id.weather_date);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", new Locale("uk","UA"));
        date.setText(dateFormat.format(new Date()));
        GetWeatherFromApi getWetherFromApi=new GetWeatherFromApi();
        getWetherFromApi.execute("http://api.openweathermap.org/data/2.5/weather?q=Slavske&mode=json&lang=ua&appid=1f37ab01b1fa87cb404e66109a863603"//,
                //"http://api.openweathermap.org/data/2.5/forecast?q=Slavske,ua&mode=json&lang=ua&appid=1f37ab01b1fa87cb404e66109a863603",
                //"http://api.openweathermap.org/data/2.5/forecast/daily?q=Slavske,ua&mode=json&lang=ua&cnt=14&appid=1f37ab01b1fa87cb404e66109a863603"
                );

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            GetWeatherFromApi getWetherFromApi=new GetWeatherFromApi();
            switch (item.getItemId()) {
                case R.id.navigation_today:
                    getWetherFromApi.execute("http://api.openweathermap.org/data/2.5/weather?q=Slavske&mode=json&lang=ua&appid=1f37ab01b1fa87cb404e66109a863603");
                    wether1.setBackgroundColor(getResources().getColor(R.color.accent_color));
                    wether2.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                    wether3.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                    weather_today_Layout.setVisibility(View.VISIBLE);
                    weather_5day_Layout.setVisibility(View.GONE);
                    weather_14day_Layout.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_5day:
                    getWetherFromApi.execute("http://api.openweathermap.org/data/2.5/forecast?q=Slavske,ua&mode=json&lang=ua&appid=1f37ab01b1fa87cb404e66109a863603");
                    wether1.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                    wether2.setBackgroundColor(getResources().getColor(R.color.accent_color));
                    wether3.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                    weather_today_Layout.setVisibility(View.GONE);
                    weather_5day_Layout.setVisibility(View.VISIBLE);
                    weather_14day_Layout.setVisibility(View.GONE);
                    weather5_Layout_main=(LinearLayout) findViewById(R.id.layout_weather_5day);
                    /*image5[0] = (ImageView) findViewById(R.id.icon5_0_15);
                    image5[1] = (ImageView) findViewById(R.id.icon5_1_15);
                    image5[2] = (ImageView) findViewById(R.id.icon5_2_15);
                    image5[3] = (ImageView) findViewById(R.id.icon5_3_15);
                    image5[4] = (ImageView) findViewById(R.id.icon5_4_15);
                    weather_value5[0] = (TextView) findViewById(R.id.weather_value5_0);
                    weather_value5[1] = (TextView) findViewById(R.id.weather_value5_1);
                    weather_value5[2] = (TextView) findViewById(R.id.weather_value5_2);
                    weather_value5[3] = (TextView) findViewById(R.id.weather_value5_3);
                    weather_value5[4] = (TextView) findViewById(R.id.weather_value5_4);
                    weather5_Layout[0]=(LinearLayout) findViewById(R.id.weather_5day0);
                    weather5_Layout[1]=(LinearLayout) findViewById(R.id.weather_5day1);
                    weather5_Layout[2]=(LinearLayout) findViewById(R.id.weather_5day2);
                    weather5_Layout[3]=(LinearLayout) findViewById(R.id.weather_5day3);
                    weather5_Layout[4]=(LinearLayout) findViewById(R.id.weather_5day4);
                    date5[0] = (TextView) findViewById(R.id.weather_5date0);
                    date5[1] = (TextView) findViewById(R.id.weather_5date1);
                    date5[2] = (TextView) findViewById(R.id.weather_5date2);
                    date5[3] = (TextView) findViewById(R.id.weather_5date3);
                    date5[4] = (TextView) findViewById(R.id.weather_5date4);*/
                     return true;
                case R.id.navigation_14day:
                    getWetherFromApi.execute("http://api.openweathermap.org/data/2.5/forecast/daily?q=Slavske,ua&mode=json&lang=ua&cnt=14&appid=1f37ab01b1fa87cb404e66109a863603");
                    wether1.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                    wether2.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                    wether3.setBackgroundColor(getResources().getColor(R.color.accent_color));
                    weather_today_Layout.setVisibility(View.GONE);
                    weather_5day_Layout.setVisibility(View.GONE);
                    weather_14day_Layout.setVisibility(View.VISIBLE);
                    temperature14[0] = (TextView) findViewById(R.id.temp14_0);
                    temperature14[1] = (TextView) findViewById(R.id.temp14_1);
                    temperature14[2] = (TextView) findViewById(R.id.temp14_2);
                    temperature14[3] = (TextView) findViewById(R.id.temp14_3);
                    temperature14[4] = (TextView) findViewById(R.id.temp14_4);
                    temperature14[5] = (TextView) findViewById(R.id.temp14_5);
                    temperature14[6] = (TextView) findViewById(R.id.temp14_6);
                    temperature14[7] = (TextView) findViewById(R.id.temp14_7);
                    temperature14[8] = (TextView) findViewById(R.id.temp14_8);
                    temperature14[9] = (TextView) findViewById(R.id.temp14_9);
                    temperature14[10] = (TextView) findViewById(R.id.temp14_10);
                    temperature14[11] = (TextView) findViewById(R.id.temp14_11);
                    temperature14[12] = (TextView) findViewById(R.id.temp14_12);
                    temperature14[13] = (TextView) findViewById(R.id.temp14_13);
                    wind14[0] = (TextView) findViewById(R.id.weather_wind14_0);
                    wind14[1] = (TextView) findViewById(R.id.weather_wind14_1);
                    wind14[2] = (TextView) findViewById(R.id.weather_wind14_2);
                    wind14[3] = (TextView) findViewById(R.id.weather_wind14_3);
                    wind14[4] = (TextView) findViewById(R.id.weather_wind14_4);
                    wind14[5] = (TextView) findViewById(R.id.weather_wind14_5);
                    wind14[6] = (TextView) findViewById(R.id.weather_wind14_6);
                    wind14[7] = (TextView) findViewById(R.id.weather_wind14_7);
                    wind14[8] = (TextView) findViewById(R.id.weather_wind14_8);
                    wind14[9] = (TextView) findViewById(R.id.weather_wind14_9);
                    wind14[10] = (TextView) findViewById(R.id.weather_wind14_10);
                    wind14[11] = (TextView) findViewById(R.id.weather_wind14_11);
                    wind14[12] = (TextView) findViewById(R.id.weather_wind14_12);
                    wind14[13] = (TextView) findViewById(R.id.weather_wind14_13);
                    humidity14[0] = (TextView) findViewById(R.id.weather_humidity14_0);
                    humidity14[1] = (TextView) findViewById(R.id.weather_humidity14_1);
                    humidity14[2] = (TextView) findViewById(R.id.weather_humidity14_2);
                    humidity14[3] = (TextView) findViewById(R.id.weather_humidity14_3);
                    humidity14[4] = (TextView) findViewById(R.id.weather_humidity14_4);
                    humidity14[5] = (TextView) findViewById(R.id.weather_humidity14_5);
                    humidity14[6] = (TextView) findViewById(R.id.weather_humidity14_6);
                    humidity14[7] = (TextView) findViewById(R.id.weather_humidity14_7);
                    humidity14[8] = (TextView) findViewById(R.id.weather_humidity14_8);
                    humidity14[9] = (TextView) findViewById(R.id.weather_humidity14_9);
                    humidity14[10] = (TextView) findViewById(R.id.weather_humidity14_10);
                    humidity14[11] = (TextView) findViewById(R.id.weather_humidity14_11);
                    humidity14[12] = (TextView) findViewById(R.id.weather_humidity14_12);
                    humidity14[13] = (TextView) findViewById(R.id.weather_humidity14_13);
                    weather_value14[0] = (TextView) findViewById(R.id.weather_value14_0);
                    weather_value14[1] = (TextView) findViewById(R.id.weather_value14_1);
                    weather_value14[2] = (TextView) findViewById(R.id.weather_value14_2);
                    weather_value14[3] = (TextView) findViewById(R.id.weather_value14_3);
                    weather_value14[4] = (TextView) findViewById(R.id.weather_value14_4);
                    weather_value14[5] = (TextView) findViewById(R.id.weather_value14_5);
                    weather_value14[6] = (TextView) findViewById(R.id.weather_value14_6);
                    weather_value14[7] = (TextView) findViewById(R.id.weather_value14_7);
                    weather_value14[8] = (TextView) findViewById(R.id.weather_value14_8);
                    weather_value14[9] = (TextView) findViewById(R.id.weather_value14_9);
                    weather_value14[10] = (TextView) findViewById(R.id.weather_value14_10);
                    weather_value14[11] = (TextView) findViewById(R.id.weather_value14_11);
                    weather_value14[12] = (TextView) findViewById(R.id.weather_value14_12);
                    weather_value14[13] = (TextView) findViewById(R.id.weather_value14_13);
                    image14[0] = (ImageView) findViewById(R.id.icon14_0);
                    image14[1] = (ImageView) findViewById(R.id.icon14_1);
                    image14[2] = (ImageView) findViewById(R.id.icon14_2);
                    image14[3] = (ImageView) findViewById(R.id.icon14_3);
                    image14[4] = (ImageView) findViewById(R.id.icon14_4);
                    image14[5] = (ImageView) findViewById(R.id.icon14_5);
                    image14[6] = (ImageView) findViewById(R.id.icon14_6);
                    image14[7] = (ImageView) findViewById(R.id.icon14_7);
                    image14[8] = (ImageView) findViewById(R.id.icon14_8);
                    image14[9] = (ImageView) findViewById(R.id.icon14_9);
                    image14[10] = (ImageView) findViewById(R.id.icon14_10);
                    image14[11] = (ImageView) findViewById(R.id.icon14_11);
                    image14[12] = (ImageView) findViewById(R.id.icon14_12);
                    image14[13] = (ImageView) findViewById(R.id.icon14_13);
                    date14[0] = (TextView) findViewById(R.id.weather_14date0);
                    date14[1] = (TextView) findViewById(R.id.weather_14date1);
                    date14[2] = (TextView) findViewById(R.id.weather_14date2);
                    date14[3] = (TextView) findViewById(R.id.weather_14date3);
                    date14[4] = (TextView) findViewById(R.id.weather_14date4);
                    date14[5] = (TextView) findViewById(R.id.weather_14date5);
                    date14[6] = (TextView) findViewById(R.id.weather_14date6);
                    date14[7] = (TextView) findViewById(R.id.weather_14date7);
                    date14[8] = (TextView) findViewById(R.id.weather_14date8);
                    date14[9] = (TextView) findViewById(R.id.weather_14date9);
                    date14[10] = (TextView) findViewById(R.id.weather_14date10);
                    date14[11] = (TextView) findViewById(R.id.weather_14date11);
                    date14[12] = (TextView) findViewById(R.id.weather_14date12);
                    date14[13] = (TextView) findViewById(R.id.weather_14date13);
                    weather14_Layout[0]=(LinearLayout) findViewById(R.id.weather_14day0);
                    weather14_Layout[1]=(LinearLayout) findViewById(R.id.weather_14day1);
                    weather14_Layout[2]=(LinearLayout) findViewById(R.id.weather_14day2);
                    weather14_Layout[3]=(LinearLayout) findViewById(R.id.weather_14day3);
                    weather14_Layout[4]=(LinearLayout) findViewById(R.id.weather_14day4);
                    weather14_Layout[5]=(LinearLayout) findViewById(R.id.weather_14day5);
                    weather14_Layout[6]=(LinearLayout) findViewById(R.id.weather_14day6);
                    weather14_Layout[7]=(LinearLayout) findViewById(R.id.weather_14day7);
                    weather14_Layout[8]=(LinearLayout) findViewById(R.id.weather_14day8);
                    weather14_Layout[9]=(LinearLayout) findViewById(R.id.weather_14day9);
                    weather14_Layout[10]=(LinearLayout) findViewById(R.id.weather_14day10);
                    weather14_Layout[11]=(LinearLayout) findViewById(R.id.weather_14day11);
                    weather14_Layout[12]=(LinearLayout) findViewById(R.id.weather_14day12);
                    weather14_Layout[13]=(LinearLayout) findViewById(R.id.weather_14day13);
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
