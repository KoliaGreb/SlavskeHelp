package com.example.q.slavskehelp;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class web_cam_other_version extends AppCompatActivity {
    private ActionBarDrawerToggle mToggle;
    private View home1;
    private View home2;
    private View home3;
    private NavigationView navigationView;
    private TextView text;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_cam_other_version);
        navigationView = (NavigationView) this.findViewById(R.id.navigation_left);
        View navView = navigationView.getHeaderView(0);
        text = (TextView) navView.findViewById(R.id.head);
        text.setText(News.Auth_User);
        home1=(View) findViewById(R.id.navigation_home);
        home2=(View) findViewById(R.id.navigation_dashboard);
        home3=(View) findViewById(R.id.navigation_notifications);
        home1.setBackgroundColor(getResources().getColor(R.color.accent_color));
        home2.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
        home3.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
        DrawerLayout mDrawerLayout;
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle=new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        WebView myWebView = (WebView) findViewById(R.id.view_web1);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        myWebView.loadUrl("http://cdn.ua/code/sneg.info/camera/index.php?camera=04&width=640&height=360&view=&stream=04.stream&image=http%3A%2F%2Fdev.sneg.info%2Fcamera%2F4%2Flast.jpg&autoplay=1&stage=3");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("SetJavaScriptEnabled")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            WebView myWebView = (WebView) findViewById(R.id.view_web1);
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
             switch (item.getItemId()) {
                case R.id.navigation_home:
                    home1.setBackgroundColor(getResources().getColor(R.color.accent_color));
                    home2.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                    home3.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                    myWebView.loadUrl("http://cdn.ua/code/sneg.info/camera/index.php?camera=04&width=640&height=360&view=&stream=04.stream&image=http%3A%2F%2Fdev.sneg.info%2Fcamera%2F4%2Flast.jpg&autoplay=1&stage=3");
                    return true;
                case R.id.navigation_dashboard:
                    home1.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                    home2.setBackgroundColor(getResources().getColor(R.color.accent_color));
                    home3.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                    myWebView.loadUrl("http://cdn.ua/code/sneg.info/camera/index.php?camera=16&width=640&height=360&view=&stream=04.stream&image=http%3A%2F%2Fdev.sneg.info%2Fcamera%2F4%2Flast.jpg&autoplay=1&stage=3");
                    return true;
                case R.id.navigation_notifications:
                    home1.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                    home2.setBackgroundColor(getResources().getColor(R.color.accent_color_light));
                    home3.setBackgroundColor(getResources().getColor(R.color.accent_color));
                    myWebView.loadUrl("http://cdn.ua/code/sneg.info/camera/index.php?camera=23&width=640&height=360&view=&stream=04.stream&image=http%3A%2F%2Fdev.sneg.info%2Fcamera%2F4%2Flast.jpg&autoplay=1&stage=3");
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
        Intent newsIntent=new Intent(web_cam_other_version.this, News.class);
        startActivity(newsIntent);

    }
    public void login_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(web_cam_other_version.this, Login.class);
        startActivity(newsIntent);
    }

    public void registration_start(View view) {
        Intent newsIntent=new Intent(web_cam_other_version.this, Registration.class);
        startActivity(newsIntent);
    }
    public void profile_activity_start(MenuItem item) {
        if(News.Auth_User.equals("Авторизація не пройдена"))
        {
            Toast.makeText(web_cam_other_version.this,
                    "Пройдіть авторизацію!!",
                    Toast.LENGTH_SHORT).show();
            Intent newsIntent=new Intent(web_cam_other_version.this, Login.class);
            startActivity(newsIntent);

        }
        else
        {
            Intent newsIntent=new Intent(web_cam_other_version.this, Profile.class);
            startActivity(newsIntent);
        }
    }

    public void search_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(web_cam_other_version.this, Search.class);
        startActivity(newsIntent);
    }
    public void web_cam_actiyity_start(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_web_cam:
                intent = new Intent(web_cam_other_version.this, web_cam_other_version.class);
                startActivity(intent);
                break;
            case R.id.nav_map:
                intent = new Intent(web_cam_other_version.this, MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_wether:
                intent = new Intent(web_cam_other_version.this, Weather.class);
                startActivity(intent);
                break;
        }
    }

}
