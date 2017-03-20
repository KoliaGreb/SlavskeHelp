package com.example.q.slavskehelp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Search extends AppCompatActivity {

    private ActionBarDrawerToggle mToggle;
    //private Context mContext;

  //  private Button mSearch_Button;

    private LinearLayout left_arrow;
    private LinearLayout right_arrow;
    private LinearLayout tennis_Layout;
    private LinearLayout sauna_Layout;
    private LinearLayout biliard_Layout;
    private LinearLayout bowling_Layout;
    private LinearLayout big_tennis_Layout;
    private LinearLayout bicycle_Layout;
    private LinearLayout kvadro_Layout;
    private LinearLayout horse_Layout;

    private LinearLayout change_type_Layout;
    private LinearLayout home_Layout;
    private LinearLayout taxi_Layout;
    private LinearLayout entertainment_Layout;

    private LinearLayout select_home_Layout;
    private LinearLayout select_taxi_Layout;
    private LinearLayout select_entertainment_Layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /*mSearch_Button=(Button) findViewById(R.id.button_search);
        mContext = getApplicationContext();
        Drawable drawableRed = ContextCompat.getDrawable(
                mContext,
                R.drawable.ic_search_white_24px
        );
        mSearch_Button.setCompoundDrawablesWithIntrinsicBounds(
                null, // Drawable left
                null, // Drawable top
                drawableRed, // Drawable right
                null // Drawable bottom
        );*/
        tennis_Layout=(LinearLayout) findViewById(R.id.tennis_layout);
        sauna_Layout=(LinearLayout) findViewById(R.id.sauna_layout);
        biliard_Layout=(LinearLayout) findViewById(R.id.biliard_layout);
        bowling_Layout=(LinearLayout) findViewById(R.id.bowling_layout);
        big_tennis_Layout=(LinearLayout) findViewById(R.id.big_tennis_layout);
        bicycle_Layout=(LinearLayout) findViewById(R.id.bicycle_layout);
        kvadro_Layout=(LinearLayout) findViewById(R.id.kvadro_layout);
        horse_Layout=(LinearLayout) findViewById(R.id.horse_layout);
        left_arrow=(LinearLayout) findViewById(R.id.left_arrow_layout);
        right_arrow=(LinearLayout) findViewById(R.id.right_arrow_layout);

        change_type_Layout=(LinearLayout) findViewById(R.id.change_type_layout);
        home_Layout=(LinearLayout) findViewById(R.id.home_layout);
        taxi_Layout=(LinearLayout) findViewById(R.id.taxi_layout);
        entertainment_Layout=(LinearLayout) findViewById(R.id.entertainment_layout);

        select_home_Layout=(LinearLayout) findViewById(R.id.select_home);
        select_taxi_Layout=(LinearLayout) findViewById(R.id.select_taxi);
        select_entertainment_Layout=(LinearLayout) findViewById(R.id.select_entertainment);


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
        Intent newsIntent=new Intent(Search.this, News.class);
        startActivity(newsIntent);

    }
    public void login_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(Search.this, Login.class);
        startActivity(newsIntent);
    }

    public void registration_start(View view) {
        Intent newsIntent=new Intent(Search.this, Registration.class);
        startActivity(newsIntent);
    }
    public void profile_activity_start(MenuItem item) {
        if(News.Auth_User.equals("Авторизація не пройдена"))
        {
            Toast.makeText(Search.this,
                    "Пройдіть авторизацію!!",
                    Toast.LENGTH_SHORT).show();
            Intent newsIntent=new Intent(Search.this, Login.class);
            startActivity(newsIntent);

        }
        else
        {
            Intent newsIntent=new Intent(Search.this, Profile.class);
            startActivity(newsIntent);
        }
    }

    public void search_Click(View view) {

    }

    public void right_arrow_click(View view) {
        if(tennis_Layout.getVisibility()==View.VISIBLE) {
            tennis_Layout.setVisibility(View.GONE);
            sauna_Layout.setVisibility(View.GONE);
            biliard_Layout.setVisibility(View.GONE);
            left_arrow.setVisibility(View.VISIBLE);
            bowling_Layout.setVisibility(View.VISIBLE);
            big_tennis_Layout.setVisibility(View.VISIBLE);
        }
        else
        {
            left_arrow.setVisibility(View.VISIBLE);
            right_arrow.setVisibility(View.GONE);
            bowling_Layout.setVisibility(View.GONE);
            big_tennis_Layout.setVisibility(View.GONE);
            bicycle_Layout.setVisibility(View.VISIBLE);
            kvadro_Layout.setVisibility(View.VISIBLE);
            horse_Layout.setVisibility(View.VISIBLE);
        }
    }

    public void left_arrow_click(View view) {
        if(bowling_Layout.getVisibility()==View.VISIBLE)
        {
            right_arrow.setVisibility(View.VISIBLE);
            left_arrow.setVisibility(View.GONE);
            bowling_Layout.setVisibility(View.GONE);
            big_tennis_Layout.setVisibility(View.GONE);
            tennis_Layout.setVisibility(View.VISIBLE);
            sauna_Layout.setVisibility(View.VISIBLE);
            biliard_Layout.setVisibility(View.VISIBLE);
        }
        else
        {
            bowling_Layout.setVisibility(View.VISIBLE);
            big_tennis_Layout.setVisibility(View.VISIBLE);
            bicycle_Layout.setVisibility(View.GONE);
            kvadro_Layout.setVisibility(View.GONE);
            horse_Layout.setVisibility(View.GONE);
        }
    }

    public void select_home_category_click(View view) {
        select_home_Layout.setBackgroundResource(R.drawable.border_red);
        change_type_Layout.setVisibility(View.VISIBLE);
        home_Layout.setVisibility(View.VISIBLE);
        taxi_Layout.setVisibility(View.GONE);
        entertainment_Layout.setVisibility(View.GONE);
    }

    public void select_taxi_category_click(View view) {
        select_taxi_Layout.setBackgroundResource(R.drawable.border_red);
        change_type_Layout.setVisibility(View.VISIBLE);
        home_Layout.setVisibility(View.GONE);
        taxi_Layout.setVisibility(View.VISIBLE);
        entertainment_Layout.setVisibility(View.GONE);
    }

    public void select_entertainment_category_click(View view) {
        select_entertainment_Layout.setBackgroundResource(R.drawable.border_red);
        change_type_Layout.setVisibility(View.VISIBLE);
        home_Layout.setVisibility(View.GONE);
        taxi_Layout.setVisibility(View.GONE);
        entertainment_Layout.setVisibility(View.VISIBLE);
    }

    public void search_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(Search.this, Search.class);
        startActivity(newsIntent);
    }
}
