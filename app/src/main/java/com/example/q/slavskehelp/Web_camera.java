package com.example.q.slavskehelp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import myPackage.Connection.ConnectionClass;

public class Web_camera extends AppCompatActivity {
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_camera);
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
    public void news_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(Web_camera.this, News.class);
        startActivity(newsIntent);

    }
    public void login_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(Web_camera.this, Login.class);
        startActivity(newsIntent);
    }

    public void registration_start(View view) {
        Intent newsIntent=new Intent(Web_camera.this, Registration.class);
        startActivity(newsIntent);
    }
    public void profile_activity_start(MenuItem item) {
        if(News.Auth_User.equals("Авторизація не пройдена"))
        {
            Toast.makeText(Web_camera.this,
                    "Пройдіть авторизацію!!",
                    Toast.LENGTH_SHORT).show();
            Intent newsIntent=new Intent(Web_camera.this, Login.class);
            startActivity(newsIntent);

        }
        else
        {
            Intent newsIntent=new Intent(Web_camera.this, Profile.class);
            startActivity(newsIntent);
        }
    }

    public void search_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(Web_camera.this, Search.class);
        startActivity(newsIntent);
    }

    public void openWeb_cam(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.web_cam_1:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://cdn.ua/code/sneg.info/camera/index.php?camera=04&width=640&height=360&view=&stream=04.stream&image=http%3A%2F%2Fdev.sneg.info%2Fcamera%2F4%2Flast.jpg&autoplay=1&stage=3"));
                startActivity(intent);
                break;
            case R.id.web_cam_2:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://cdn.ua/code/sneg.info/camera/index.php?camera=16&width=640&height=360&view=&stream=04.stream&image=http%3A%2F%2Fdev.sneg.info%2Fcamera%2F4%2Flast.jpg&autoplay=1&stage=3"));
                        //"http://www.zaharberkut.net/img/snig_info_bugel1.html"));
                startActivity(intent);
                break;
            case R.id.web_cam_3:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://cdn.ua/code/sneg.info/camera/index.php?camera=23&width=640&height=360&view=&stream=04.stream&image=http%3A%2F%2Fdev.sneg.info%2Fcamera%2F4%2Flast.jpg&autoplay=1&stage=3"));
                //http://www.zaharberkut.net/img/snig_info_18.html"));
                startActivity(intent);
                break;
        }
    }

    public void web_cam_actiyity_start(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_web_cam:
                intent = new Intent(Web_camera.this, Web_camera.class);
                startActivity(intent);
                break;
            case R.id.nav_map:
                intent = new Intent(Web_camera.this, MapsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
