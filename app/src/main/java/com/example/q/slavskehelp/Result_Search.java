package com.example.q.slavskehelp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import myPackage.Connection.ConnectionClass;

public class Result_Search extends AppCompatActivity {
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private TextView text;
    private ImageButton prices;
    private ImageButton times;
    private ImageButton ratings;
    public static ScrollView scrollResultSearch;
    public static LinearLayout resultSearchLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result__search);
        prices=(ImageButton)findViewById(R.id.b_price);
        times=(ImageButton)findViewById(R.id.b_time);
        ratings=(ImageButton)findViewById(R.id.b_rating);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            times.setBackground(getResources().getDrawable(R.drawable.button_shadow_light_accent));
        }
        scrollResultSearch=(ScrollView)findViewById(R.id.scroll_result_search);
        resultSearchLinearLayout=(LinearLayout)findViewById(R.id.layout_result_search);
        DrawerLayout mDrawerLayout;
        navigationView = (NavigationView) this.findViewById(R.id.navigation_left);
        View navView = navigationView.getHeaderView(0);
        text = (TextView) navView.findViewById(R.id.head);
        text.setText(News.Auth_User);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(News.Auth_User.equals("Авторизація не пройдена"))
                    {
                        Toast.makeText(Result_Search.this,
                                "Пройдіть авторизацію!!",
                                Toast.LENGTH_SHORT).show();
                        Intent newsIntent=new Intent(Result_Search.this, Login.class);
                        startActivity(newsIntent);
                    }
                    else
                    {
                        startActivity(new Intent(Result_Search.this,AddNote.class));
                    }

                }
            });
        }
        //-----------------------------------------------------------------------------------
        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.CONN();
        if(connection==null) {
            Toast.makeText(Result_Search.this,
                    "Сервер не доступний, вибачте за незручності!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        try
        {
            String SQL1="SELECT id_user, name, data_of_update, rating, id_note FROM Note where id_type="+Search.ID_Type+" Order By data_of_update desc";
            Statement stmt = connection.createStatement();
            ResultSet rs =  stmt.executeQuery(SQL1);
            //rs.last();
            int rows = 2;//rs.getRow();
            //rs.beforeFirst();
            ArrayList<String> note_names=new ArrayList<String>();
            ArrayList<String> note_datas=new ArrayList<String>();
            ArrayList<Integer> note_ratings=new ArrayList<Integer>();
            ArrayList<String> note_description=new ArrayList<String>();
            ArrayList<String> note_price=new ArrayList<String>();
            ArrayList<String> user_login=new ArrayList<String>();
            ArrayList<String> user_phone=new ArrayList<String>();
            ArrayList<Integer> rsIndex=new ArrayList<>();
            ArrayList<Integer> rsIndex2=new ArrayList<>();
            while (rs.next()) {
                note_names.add(rs.getString(2));
                note_datas.add(rs.getString(3));
                note_ratings.add(rs.getInt(4));
                rsIndex.add(rs.getInt(5));
                rsIndex2.add(rs.getInt(1));
            }
            for(int j=0;j<note_names.size();j++)
            {
                String SQL2="SELECT property_value FROM Property WHERE id_name=1 AND id_note="+rsIndex.get(j);
                //rs.close();
                //Statement stmt2 = connection.createStatement();
                ResultSet rs2 =  stmt.executeQuery(SQL2);
                while (rs2.next()) {
                    note_description.add(rs2.getString(1));
                }
                String SQL3="SELECT property_value FROM Property WHERE id_name=2 AND id_note="+rsIndex.get(j);
                rs2.close();
                //Statement stmt3 = connection.createStatement();
                ResultSet rs3 =  stmt.executeQuery(SQL3);
                while (rs3.next()) {
                    note_price.add(rs3.getString(1));
                }
                rs3.close();
                String SQL4="SELECT login,phone FROM Auth_User WHERE id_user="+rsIndex2.get(j);
                //Statement stmt4 = connection.createStatement();
                ResultSet rs4 =  stmt.executeQuery(SQL4);
                while (rs4.next()) {
                    user_login.add(rs4.getString(1));
                    user_phone.add(rs4.getString(2));
                }
                rs4.close();
            }
            connection.close();
            /*Toast.makeText(Result_Search.this,
                    "Дані зчитані з сервера!",
                    Toast.LENGTH_SHORT).show();*/
            LinearLayout res_Layout[]=new LinearLayout[note_names.size()];
            LinearLayout info_Layout[]=new LinearLayout[note_names.size()];
            LinearLayout image_Layout[]=new LinearLayout[note_names.size()];
            LinearLayout moreInfo_Layout[]=new LinearLayout[note_names.size()];
            TextView nameView[]=new TextView[note_names.size()];
            TextView priceView[]=new TextView[note_names.size()];
            TextView ratingView[]=new TextView[note_names.size()];
            TextView dataView[]=new TextView[note_names.size()];
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, 300);
            LinearLayout.LayoutParams horisontal_layoutParams = new LinearLayout.LayoutParams
                    (0, LinearLayout.LayoutParams.MATCH_PARENT,3f);
            LinearLayout.LayoutParams image_layoutParams = new LinearLayout.LayoutParams
                    (0, LinearLayout.LayoutParams.MATCH_PARENT,2f);
            LinearLayout.LayoutParams vertical_layoutParams = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(2, 2, 2, 10);
            LinearLayout.LayoutParams params_text_view = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams params_info= new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f);



           /* new DownloadImageTask(icon_image[0])
                    .execute("http://i.imgur.com/DvpvklR.png");
*/
            URL newurl = new URL("http://vignette3.wikia.nocookie.net/simpsons/images/6/60/No_Image_Available.png/revision/latest?cb=20130527163652g");
            Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
            for(int j=0;j<note_names.size();j++)
            {
                ImageView icon_image[]=new ImageView[note_names.size()];
                icon_image[j]=new ImageView(this);
                icon_image[j].setLayoutParams(new GridView.LayoutParams(290, 290));
                icon_image[j].setScaleType(ImageView.ScaleType.FIT_XY);
                icon_image[j].setPadding(4, 4, 4, 4);

                icon_image[j].setImageBitmap(mIcon_val);
                res_Layout[j]=new LinearLayout(this);
                res_Layout[j].setOrientation(LinearLayout.HORIZONTAL);
                res_Layout[j].setLayoutParams(layoutParams);
                res_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                res_Layout[j].setPadding(2,2,2,2);
                res_Layout[j].setMinimumHeight(200);
                res_Layout[j].setGravity(Gravity.CENTER);

                info_Layout[j]=new LinearLayout(this);
                info_Layout[j].setOrientation(LinearLayout.HORIZONTAL);
                info_Layout[j].setLayoutParams(horisontal_layoutParams);
                info_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                info_Layout[j].setPadding(2,2,2,2);
                info_Layout[j].setMinimumHeight(200);
                info_Layout[j].setGravity(Gravity.CENTER);

                image_Layout[j]=new LinearLayout(this);
                image_Layout[j].setOrientation(LinearLayout.HORIZONTAL);
                image_Layout[j].setLayoutParams(image_layoutParams);
                image_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                image_Layout[j].setPadding(2,2,2,2);
                image_Layout[j].setMinimumHeight(200);
                image_Layout[j].setGravity(Gravity.CENTER);

                moreInfo_Layout[j]=new LinearLayout(this);
                moreInfo_Layout[j].setOrientation(LinearLayout.VERTICAL);
                moreInfo_Layout[j].setLayoutParams(params_text_view);
                moreInfo_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                moreInfo_Layout[j].setPadding(2,2,2,2);
                moreInfo_Layout[j].setGravity(Gravity.CENTER);

                nameView[j]=new TextView(this);
                nameView[j].setLayoutParams(params_info);
                nameView[j].setGravity(Gravity.CENTER);
                nameView[j].setTextColor(nameView[j].getContext().getResources().getColor(R.color.colorMainText));
                nameView[j].setText(note_names.get(j));
                nameView[j].setTextSize(24);

                priceView[j]=new TextView(this);
                priceView[j].setLayoutParams(params_info);
                priceView[j].setGravity(Gravity.CENTER);
                priceView[j].setTextColor(priceView[j].getContext().getResources().getColor(R.color.colorMainText));
                String temp_autor="Ціна : "+note_price.get(j)+" грн.";
                priceView[j].setText(temp_autor);
                priceView[j].setTextSize(14);

                ratingView[j]=new TextView(this);
                ratingView[j].setLayoutParams(params_info);
                ratingView[j].setGravity(Gravity.CENTER);
                ratingView[j].setTextColor(ratingView[j].getContext().getResources().getColor(R.color.colorMainText));
                String temp_rating="Рейтинг : "+note_ratings.get(j);
                ratingView[j].setText(temp_rating);
                ratingView[j].setTextSize(14);

                dataView[j]=new TextView(this);
                dataView[j].setLayoutParams(params_info);
                dataView[j].setGravity(Gravity.CENTER);
                dataView[j].setTextColor(ratingView[j].getContext().getResources().getColor(R.color.colorMainText));
                String temp_data="Дата оновлення : "+note_datas.get(j).substring(0,10);
                dataView[j].setText(temp_data);
                dataView[j].setTextSize(14);

                image_Layout[j].addView(icon_image[j]);
                //res_Layout[j].addView(nameView[j]);
                moreInfo_Layout[j].addView(nameView[j]);
                moreInfo_Layout[j].addView(priceView[j]);
                moreInfo_Layout[j].addView(ratingView[j]);
                moreInfo_Layout[j].addView(dataView[j]);
                info_Layout[j].addView(moreInfo_Layout[j]);
                //info_Layout[j].addView(priceView[j]);
                //info_Layout[j].addView(ratingView[j]);
                res_Layout[j].addView(image_Layout[j]);
                res_Layout[j].addView(info_Layout[j]);
                resultSearchLinearLayout.addView(res_Layout[j]);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
            drawer.openDrawer(GravityCompat.START);
            super.onBackPressed();
        }
    }

    public void news_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(Result_Search.this, News.class);
        startActivity(newsIntent);

    }
    public void login_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(Result_Search.this, Login.class);
        startActivity(newsIntent);
    }

    public void registration_start(View view) {
        Intent newsIntent=new Intent(Result_Search.this, Registration.class);
        startActivity(newsIntent);
    }

    public void profile_activity_start(MenuItem item) {
        if(News.Auth_User.equals("Авторизація не пройдена"))
        {
            Toast.makeText(Result_Search.this,
                    "Пройдіть авторизацію!!",
                    Toast.LENGTH_SHORT).show();
            Intent newsIntent=new Intent(Result_Search.this, Login.class);
            startActivity(newsIntent);

        }
        else
        {
            Intent newsIntent=new Intent(Result_Search.this, Profile.class);
            startActivity(newsIntent);
        }
    }

    public void search_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(Result_Search.this, Search.class);
        startActivity(newsIntent);
    }

    public void web_cam_actiyity_start(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_web_cam:
                intent = new Intent(Result_Search.this, web_cam_other_version.class);
                startActivity(intent);
                break;
            case R.id.nav_map:
                intent = new Intent(Result_Search.this, MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_wether:
                intent = new Intent(Result_Search.this, Weather.class);
                startActivity(intent);
                break;
        }
    }

    public void SortTimeClick(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            times.setBackground(getResources().getDrawable(R.drawable.button_shadow_light_accent));
            prices.setBackground(getResources().getDrawable(R.drawable.button_shadow_accent));
            ratings.setBackground(getResources().getDrawable(R.drawable.button_shadow_accent));
        }
        resultSearchLinearLayout.removeAllViews();
        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.CONN();
        if(connection==null) {
            Toast.makeText(Result_Search.this,
                    "Сервер не доступний, вибачте за незручності!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        try
        {
            String SQL1="SELECT id_user, name, data_of_update, rating, id_note FROM Note where id_type="+Search.ID_Type+" Order By data_of_update desc";
            Statement stmt = connection.createStatement();
            ResultSet rs =  stmt.executeQuery(SQL1);
            //rs.last();
            int rows = 2;//rs.getRow();
            //rs.beforeFirst();
            ArrayList<String> note_names=new ArrayList<String>();
            ArrayList<String> note_datas=new ArrayList<String>();
            ArrayList<Integer> note_ratings=new ArrayList<Integer>();
            ArrayList<String> note_description=new ArrayList<String>();
            ArrayList<String> note_price=new ArrayList<String>();
            ArrayList<String> user_login=new ArrayList<String>();
            ArrayList<String> user_phone=new ArrayList<String>();
            ArrayList<Integer> rsIndex=new ArrayList<>();
            ArrayList<Integer> rsIndex2=new ArrayList<>();
            while (rs.next()) {
                note_names.add(rs.getString(2));
                note_datas.add(rs.getString(3));
                note_ratings.add(rs.getInt(4));
                rsIndex.add(rs.getInt(5));
                rsIndex2.add(rs.getInt(1));
            }
            for(int j=0;j<note_names.size();j++)
            {
                String SQL2="SELECT property_value FROM Property WHERE id_name=1 AND id_note="+rsIndex.get(j);
                //rs.close();
                //Statement stmt2 = connection.createStatement();
                ResultSet rs2 =  stmt.executeQuery(SQL2);
                while (rs2.next()) {
                    note_description.add(rs2.getString(1));
                }
                String SQL3="SELECT property_value FROM Property WHERE id_name=2 AND id_note="+rsIndex.get(j);
                rs2.close();
                //Statement stmt3 = connection.createStatement();
                ResultSet rs3 =  stmt.executeQuery(SQL3);
                while (rs3.next()) {
                    note_price.add(rs3.getString(1));
                }
                rs3.close();
                String SQL4="SELECT login,phone FROM Auth_User WHERE id_user="+rsIndex2.get(j);
                //Statement stmt4 = connection.createStatement();
                ResultSet rs4 =  stmt.executeQuery(SQL4);
                while (rs4.next()) {
                    user_login.add(rs4.getString(1));
                    user_phone.add(rs4.getString(2));
                }
                rs4.close();
            }
            connection.close();
            /*Toast.makeText(Result_Search.this,
                    "Дані зчитані з сервера!",
                    Toast.LENGTH_SHORT).show();*/
            LinearLayout res_Layout[]=new LinearLayout[note_names.size()];
            LinearLayout info_Layout[]=new LinearLayout[note_names.size()];
            LinearLayout image_Layout[]=new LinearLayout[note_names.size()];
            LinearLayout moreInfo_Layout[]=new LinearLayout[note_names.size()];
            TextView nameView[]=new TextView[note_names.size()];
            TextView priceView[]=new TextView[note_names.size()];
            TextView ratingView[]=new TextView[note_names.size()];
            TextView dataView[]=new TextView[note_names.size()];
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, 300);
            LinearLayout.LayoutParams horisontal_layoutParams = new LinearLayout.LayoutParams
                    (0, LinearLayout.LayoutParams.MATCH_PARENT,3f);
            LinearLayout.LayoutParams image_layoutParams = new LinearLayout.LayoutParams
                    (0, LinearLayout.LayoutParams.MATCH_PARENT,2f);
            LinearLayout.LayoutParams vertical_layoutParams = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(2, 2, 2, 10);
            LinearLayout.LayoutParams params_text_view = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams params_info= new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f);



           /* new DownloadImageTask(icon_image[0])
                    .execute("http://i.imgur.com/DvpvklR.png");
*/
            URL newurl = new URL("http://vignette3.wikia.nocookie.net/simpsons/images/6/60/No_Image_Available.png/revision/latest?cb=20130527163652g");
            Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
            for(int j=0;j<note_names.size();j++)
            {
                ImageView icon_image[]=new ImageView[note_names.size()];
                icon_image[j]=new ImageView(this);
                icon_image[j].setLayoutParams(new GridView.LayoutParams(290, 290));
                icon_image[j].setScaleType(ImageView.ScaleType.FIT_XY);
                icon_image[j].setPadding(4, 4, 4, 4);

                icon_image[j].setImageBitmap(mIcon_val);
                res_Layout[j]=new LinearLayout(this);
                res_Layout[j].setOrientation(LinearLayout.HORIZONTAL);
                res_Layout[j].setLayoutParams(layoutParams);
                res_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                res_Layout[j].setPadding(2,2,2,2);
                res_Layout[j].setMinimumHeight(200);
                res_Layout[j].setGravity(Gravity.CENTER);

                info_Layout[j]=new LinearLayout(this);
                info_Layout[j].setOrientation(LinearLayout.HORIZONTAL);
                info_Layout[j].setLayoutParams(horisontal_layoutParams);
                info_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                info_Layout[j].setPadding(2,2,2,2);
                info_Layout[j].setMinimumHeight(200);
                info_Layout[j].setGravity(Gravity.CENTER);

                image_Layout[j]=new LinearLayout(this);
                image_Layout[j].setOrientation(LinearLayout.HORIZONTAL);
                image_Layout[j].setLayoutParams(image_layoutParams);
                image_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                image_Layout[j].setPadding(2,2,2,2);
                image_Layout[j].setMinimumHeight(200);
                image_Layout[j].setGravity(Gravity.CENTER);

                moreInfo_Layout[j]=new LinearLayout(this);
                moreInfo_Layout[j].setOrientation(LinearLayout.VERTICAL);
                moreInfo_Layout[j].setLayoutParams(params_text_view);
                moreInfo_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                moreInfo_Layout[j].setPadding(2,2,2,2);
                moreInfo_Layout[j].setGravity(Gravity.CENTER);

                nameView[j]=new TextView(this);
                nameView[j].setLayoutParams(params_info);
                nameView[j].setGravity(Gravity.CENTER);
                nameView[j].setTextColor(nameView[j].getContext().getResources().getColor(R.color.colorMainText));
                nameView[j].setText(note_names.get(j));
                nameView[j].setTextSize(24);

                priceView[j]=new TextView(this);
                priceView[j].setLayoutParams(params_info);
                priceView[j].setGravity(Gravity.CENTER);
                priceView[j].setTextColor(priceView[j].getContext().getResources().getColor(R.color.colorMainText));
                String temp_autor="Ціна : "+note_price.get(j)+" грн.";
                priceView[j].setText(temp_autor);
                priceView[j].setTextSize(14);

                ratingView[j]=new TextView(this);
                ratingView[j].setLayoutParams(params_info);
                ratingView[j].setGravity(Gravity.CENTER);
                ratingView[j].setTextColor(ratingView[j].getContext().getResources().getColor(R.color.colorMainText));
                String temp_rating="Рейтинг : "+note_ratings.get(j);
                ratingView[j].setText(temp_rating);
                ratingView[j].setTextSize(14);

                dataView[j]=new TextView(this);
                dataView[j].setLayoutParams(params_info);
                dataView[j].setGravity(Gravity.CENTER);
                dataView[j].setTextColor(ratingView[j].getContext().getResources().getColor(R.color.colorMainText));
                String temp_data="Дата оновлення : "+note_datas.get(j).substring(0,10);
                dataView[j].setText(temp_data);
                dataView[j].setTextSize(14);

                image_Layout[j].addView(icon_image[j]);
                //res_Layout[j].addView(nameView[j]);
                moreInfo_Layout[j].addView(nameView[j]);
                moreInfo_Layout[j].addView(priceView[j]);
                moreInfo_Layout[j].addView(ratingView[j]);
                moreInfo_Layout[j].addView(dataView[j]);
                info_Layout[j].addView(moreInfo_Layout[j]);
                //info_Layout[j].addView(priceView[j]);
                //info_Layout[j].addView(ratingView[j]);
                res_Layout[j].addView(image_Layout[j]);
                res_Layout[j].addView(info_Layout[j]);
                resultSearchLinearLayout.addView(res_Layout[j]);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SortRatingClick(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            times.setBackground(getResources().getDrawable(R.drawable.button_shadow_accent));
            prices.setBackground(getResources().getDrawable(R.drawable.button_shadow_accent));
            ratings.setBackground(getResources().getDrawable(R.drawable.button_shadow_light_accent));
        }
        resultSearchLinearLayout.removeAllViews();
        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.CONN();
        if(connection==null) {
            Toast.makeText(Result_Search.this,
                    "Сервер не доступний, вибачте за незручності!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        try
        {
            String SQL1="SELECT id_user, name, data_of_update, rating, id_note FROM Note where id_type="+Search.ID_Type+" Order By rating desc";
            Statement stmt = connection.createStatement();
            ResultSet rs =  stmt.executeQuery(SQL1);
            //rs.last();
            int rows = 2;//rs.getRow();
            //rs.beforeFirst();
            ArrayList<String> note_names=new ArrayList<String>();
            ArrayList<String> note_datas=new ArrayList<String>();
            ArrayList<Integer> note_ratings=new ArrayList<Integer>();
            ArrayList<String> note_description=new ArrayList<String>();
            ArrayList<String> note_price=new ArrayList<String>();
            ArrayList<String> user_login=new ArrayList<String>();
            ArrayList<String> user_phone=new ArrayList<String>();
            ArrayList<Integer> rsIndex=new ArrayList<>();
            ArrayList<Integer> rsIndex2=new ArrayList<>();
            while (rs.next()) {
                note_names.add(rs.getString(2));
                note_datas.add(rs.getString(3));
                note_ratings.add(rs.getInt(4));
                rsIndex.add(rs.getInt(5));
                rsIndex2.add(rs.getInt(1));
            }
            for(int j=0;j<note_names.size();j++)
            {
                String SQL2="SELECT property_value FROM Property WHERE id_name=1 AND id_note="+rsIndex.get(j);
                //rs.close();
                //Statement stmt2 = connection.createStatement();
                ResultSet rs2 =  stmt.executeQuery(SQL2);
                while (rs2.next()) {
                    note_description.add(rs2.getString(1));
                }
                String SQL3="SELECT property_value FROM Property WHERE id_name=2 AND id_note="+rsIndex.get(j);
                rs2.close();
                //Statement stmt3 = connection.createStatement();
                ResultSet rs3 =  stmt.executeQuery(SQL3);
                while (rs3.next()) {
                    note_price.add(rs3.getString(1));
                }
                rs3.close();
                String SQL4="SELECT login,phone FROM Auth_User WHERE id_user="+rsIndex2.get(j);
                //Statement stmt4 = connection.createStatement();
                ResultSet rs4 =  stmt.executeQuery(SQL4);
                while (rs4.next()) {
                    user_login.add(rs4.getString(1));
                    user_phone.add(rs4.getString(2));
                }
                rs4.close();
            }
            connection.close();
            /*Toast.makeText(Result_Search.this,
                    "Дані зчитані з сервера!",
                    Toast.LENGTH_SHORT).show();*/
            LinearLayout res_Layout[]=new LinearLayout[note_names.size()];
            LinearLayout info_Layout[]=new LinearLayout[note_names.size()];
            LinearLayout image_Layout[]=new LinearLayout[note_names.size()];
            LinearLayout moreInfo_Layout[]=new LinearLayout[note_names.size()];
            TextView nameView[]=new TextView[note_names.size()];
            TextView priceView[]=new TextView[note_names.size()];
            TextView ratingView[]=new TextView[note_names.size()];
            TextView dataView[]=new TextView[note_names.size()];
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, 300);
            LinearLayout.LayoutParams horisontal_layoutParams = new LinearLayout.LayoutParams
                    (0, LinearLayout.LayoutParams.MATCH_PARENT,3f);
            LinearLayout.LayoutParams image_layoutParams = new LinearLayout.LayoutParams
                    (0, LinearLayout.LayoutParams.MATCH_PARENT,2f);
            LinearLayout.LayoutParams vertical_layoutParams = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(2, 2, 2, 10);
            LinearLayout.LayoutParams params_text_view = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams params_info= new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f);



           /* new DownloadImageTask(icon_image[0])
                    .execute("http://i.imgur.com/DvpvklR.png");
*/
            URL newurl = new URL("http://vignette3.wikia.nocookie.net/simpsons/images/6/60/No_Image_Available.png/revision/latest?cb=20130527163652g");
            Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
            for(int j=0;j<note_names.size();j++)
            {
                ImageView icon_image[]=new ImageView[note_names.size()];
                icon_image[j]=new ImageView(this);
                icon_image[j].setLayoutParams(new GridView.LayoutParams(290, 290));
                icon_image[j].setScaleType(ImageView.ScaleType.FIT_XY);
                icon_image[j].setPadding(4, 4, 4, 4);

                icon_image[j].setImageBitmap(mIcon_val);
                res_Layout[j]=new LinearLayout(this);
                res_Layout[j].setOrientation(LinearLayout.HORIZONTAL);
                res_Layout[j].setLayoutParams(layoutParams);
                res_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                res_Layout[j].setPadding(2,2,2,2);
                res_Layout[j].setMinimumHeight(200);
                res_Layout[j].setGravity(Gravity.CENTER);

                info_Layout[j]=new LinearLayout(this);
                info_Layout[j].setOrientation(LinearLayout.HORIZONTAL);
                info_Layout[j].setLayoutParams(horisontal_layoutParams);
                info_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                info_Layout[j].setPadding(2,2,2,2);
                info_Layout[j].setMinimumHeight(200);
                info_Layout[j].setGravity(Gravity.CENTER);

                image_Layout[j]=new LinearLayout(this);
                image_Layout[j].setOrientation(LinearLayout.HORIZONTAL);
                image_Layout[j].setLayoutParams(image_layoutParams);
                image_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                image_Layout[j].setPadding(2,2,2,2);
                image_Layout[j].setMinimumHeight(200);
                image_Layout[j].setGravity(Gravity.CENTER);

                moreInfo_Layout[j]=new LinearLayout(this);
                moreInfo_Layout[j].setOrientation(LinearLayout.VERTICAL);
                moreInfo_Layout[j].setLayoutParams(params_text_view);
                moreInfo_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                moreInfo_Layout[j].setPadding(2,2,2,2);
                moreInfo_Layout[j].setGravity(Gravity.CENTER);

                nameView[j]=new TextView(this);
                nameView[j].setLayoutParams(params_info);
                nameView[j].setGravity(Gravity.CENTER);
                nameView[j].setTextColor(nameView[j].getContext().getResources().getColor(R.color.colorMainText));
                nameView[j].setText(note_names.get(j));
                nameView[j].setTextSize(24);

                priceView[j]=new TextView(this);
                priceView[j].setLayoutParams(params_info);
                priceView[j].setGravity(Gravity.CENTER);
                priceView[j].setTextColor(priceView[j].getContext().getResources().getColor(R.color.colorMainText));
                String temp_autor="Ціна : "+note_price.get(j)+" грн.";
                priceView[j].setText(temp_autor);
                priceView[j].setTextSize(14);

                ratingView[j]=new TextView(this);
                ratingView[j].setLayoutParams(params_info);
                ratingView[j].setGravity(Gravity.CENTER);
                ratingView[j].setTextColor(ratingView[j].getContext().getResources().getColor(R.color.colorMainText));
                String temp_rating="Рейтинг : "+note_ratings.get(j);
                ratingView[j].setText(temp_rating);
                ratingView[j].setTextSize(14);

                dataView[j]=new TextView(this);
                dataView[j].setLayoutParams(params_info);
                dataView[j].setGravity(Gravity.CENTER);
                dataView[j].setTextColor(ratingView[j].getContext().getResources().getColor(R.color.colorMainText));
                String temp_data="Дата оновлення : "+note_datas.get(j).substring(0,10);
                dataView[j].setText(temp_data);
                dataView[j].setTextSize(14);

                image_Layout[j].addView(icon_image[j]);
                //res_Layout[j].addView(nameView[j]);
                moreInfo_Layout[j].addView(nameView[j]);
                moreInfo_Layout[j].addView(priceView[j]);
                moreInfo_Layout[j].addView(ratingView[j]);
                moreInfo_Layout[j].addView(dataView[j]);
                info_Layout[j].addView(moreInfo_Layout[j]);
                //info_Layout[j].addView(priceView[j]);
                //info_Layout[j].addView(ratingView[j]);
                res_Layout[j].addView(image_Layout[j]);
                res_Layout[j].addView(info_Layout[j]);
                resultSearchLinearLayout.addView(res_Layout[j]);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SortPriceClick(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            times.setBackground(getResources().getDrawable(R.drawable.button_shadow_accent));
            prices.setBackground(getResources().getDrawable(R.drawable.button_shadow_light_accent));
            ratings.setBackground(getResources().getDrawable(R.drawable.button_shadow_accent));
        }
        resultSearchLinearLayout.removeAllViews();
        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.CONN();
        if(connection==null) {
            Toast.makeText(Result_Search.this,
                    "Сервер не доступний, вибачте за незручності!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        try
        {
            String SQL1="SELECT n.id_user, n.name, n.data_of_update, n.rating, n.id_note, p.property_value FROM Note n, Property p Where n.id_type="+Search.ID_Type+" AND p.id_name=2 AND n.id_note=p.id_note Order By p.property_value desc";
            Statement stmt = connection.createStatement();
            ResultSet rs =  stmt.executeQuery(SQL1);
            //rs.last();
            int rows = 2;//rs.getRow();
            //rs.beforeFirst();
            ArrayList<String> note_names=new ArrayList<String>();
            ArrayList<String> note_datas=new ArrayList<String>();
            ArrayList<Integer> note_ratings=new ArrayList<Integer>();
            ArrayList<String> note_description=new ArrayList<String>();
            ArrayList<String> note_price=new ArrayList<String>();
            ArrayList<String> user_login=new ArrayList<String>();
            ArrayList<String> user_phone=new ArrayList<String>();
            ArrayList<Integer> rsIndex=new ArrayList<>();
            ArrayList<Integer> rsIndex2=new ArrayList<>();
            while (rs.next()) {
                note_names.add(rs.getString(2));
                note_datas.add(rs.getString(3));
                note_ratings.add(rs.getInt(4));
                rsIndex.add(rs.getInt(5));
                rsIndex2.add(rs.getInt(1));
            }
            for(int j=0;j<note_names.size();j++)
            {
                String SQL2="SELECT property_value FROM Property WHERE id_name=1 AND id_note="+rsIndex.get(j);
                //rs.close();
                //Statement stmt2 = connection.createStatement();
                ResultSet rs2 =  stmt.executeQuery(SQL2);
                while (rs2.next()) {
                    note_description.add(rs2.getString(1));
                }
                String SQL3="SELECT property_value FROM Property WHERE id_name=2 AND id_note="+rsIndex.get(j);
                rs2.close();
                //Statement stmt3 = connection.createStatement();
                ResultSet rs3 =  stmt.executeQuery(SQL3);
                while (rs3.next()) {
                    note_price.add(rs3.getString(1));
                }
                rs3.close();
                String SQL4="SELECT login,phone FROM Auth_User WHERE id_user="+rsIndex2.get(j);
                //Statement stmt4 = connection.createStatement();
                ResultSet rs4 =  stmt.executeQuery(SQL4);
                while (rs4.next()) {
                    user_login.add(rs4.getString(1));
                    user_phone.add(rs4.getString(2));
                }
                rs4.close();
            }
            connection.close();
            /*Toast.makeText(Result_Search.this,
                    "Дані зчитані з сервера!",
                    Toast.LENGTH_SHORT).show();*/
            LinearLayout res_Layout[]=new LinearLayout[note_names.size()];
            LinearLayout info_Layout[]=new LinearLayout[note_names.size()];
            LinearLayout image_Layout[]=new LinearLayout[note_names.size()];
            LinearLayout moreInfo_Layout[]=new LinearLayout[note_names.size()];
            TextView nameView[]=new TextView[note_names.size()];
            TextView priceView[]=new TextView[note_names.size()];
            TextView ratingView[]=new TextView[note_names.size()];
            TextView dataView[]=new TextView[note_names.size()];
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, 300);
            LinearLayout.LayoutParams horisontal_layoutParams = new LinearLayout.LayoutParams
                    (0, LinearLayout.LayoutParams.MATCH_PARENT,3f);
            LinearLayout.LayoutParams image_layoutParams = new LinearLayout.LayoutParams
                    (0, LinearLayout.LayoutParams.MATCH_PARENT,2f);
            LinearLayout.LayoutParams vertical_layoutParams = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(2, 2, 2, 10);
            LinearLayout.LayoutParams params_text_view = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams params_info= new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f);



           /* new DownloadImageTask(icon_image[0])
                    .execute("http://i.imgur.com/DvpvklR.png");
*/
            URL newurl = new URL("http://vignette3.wikia.nocookie.net/simpsons/images/6/60/No_Image_Available.png/revision/latest?cb=20130527163652g");
            Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
            for(int j=0;j<note_names.size();j++)
            {
                ImageView icon_image[]=new ImageView[note_names.size()];
                icon_image[j]=new ImageView(this);
                icon_image[j].setLayoutParams(new GridView.LayoutParams(290, 290));
                icon_image[j].setScaleType(ImageView.ScaleType.FIT_XY);
                icon_image[j].setPadding(4, 4, 4, 4);

                icon_image[j].setImageBitmap(mIcon_val);
                res_Layout[j]=new LinearLayout(this);
                res_Layout[j].setOrientation(LinearLayout.HORIZONTAL);
                res_Layout[j].setLayoutParams(layoutParams);
                res_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                res_Layout[j].setPadding(2,2,2,2);
                res_Layout[j].setMinimumHeight(200);
                res_Layout[j].setGravity(Gravity.CENTER);

                info_Layout[j]=new LinearLayout(this);
                info_Layout[j].setOrientation(LinearLayout.HORIZONTAL);
                info_Layout[j].setLayoutParams(horisontal_layoutParams);
                info_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                info_Layout[j].setPadding(2,2,2,2);
                info_Layout[j].setMinimumHeight(200);
                info_Layout[j].setGravity(Gravity.CENTER);

                image_Layout[j]=new LinearLayout(this);
                image_Layout[j].setOrientation(LinearLayout.HORIZONTAL);
                image_Layout[j].setLayoutParams(image_layoutParams);
                image_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                image_Layout[j].setPadding(2,2,2,2);
                image_Layout[j].setMinimumHeight(200);
                image_Layout[j].setGravity(Gravity.CENTER);

                moreInfo_Layout[j]=new LinearLayout(this);
                moreInfo_Layout[j].setOrientation(LinearLayout.VERTICAL);
                moreInfo_Layout[j].setLayoutParams(params_text_view);
                moreInfo_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                moreInfo_Layout[j].setPadding(2,2,2,2);
                moreInfo_Layout[j].setGravity(Gravity.CENTER);

                nameView[j]=new TextView(this);
                nameView[j].setLayoutParams(params_info);
                nameView[j].setGravity(Gravity.CENTER);
                nameView[j].setTextColor(nameView[j].getContext().getResources().getColor(R.color.colorMainText));
                nameView[j].setText(note_names.get(j));
                nameView[j].setTextSize(24);

                priceView[j]=new TextView(this);
                priceView[j].setLayoutParams(params_info);
                priceView[j].setGravity(Gravity.CENTER);
                priceView[j].setTextColor(priceView[j].getContext().getResources().getColor(R.color.colorMainText));
                String temp_autor="Ціна : "+note_price.get(j)+" грн.";
                priceView[j].setText(temp_autor);
                priceView[j].setTextSize(14);

                ratingView[j]=new TextView(this);
                ratingView[j].setLayoutParams(params_info);
                ratingView[j].setGravity(Gravity.CENTER);
                ratingView[j].setTextColor(ratingView[j].getContext().getResources().getColor(R.color.colorMainText));
                String temp_rating="Рейтинг : "+note_ratings.get(j);
                ratingView[j].setText(temp_rating);
                ratingView[j].setTextSize(14);

                dataView[j]=new TextView(this);
                dataView[j].setLayoutParams(params_info);
                dataView[j].setGravity(Gravity.CENTER);
                dataView[j].setTextColor(ratingView[j].getContext().getResources().getColor(R.color.colorMainText));
                String temp_data="Дата оновлення : "+note_datas.get(j).substring(0,10);
                dataView[j].setText(temp_data);
                dataView[j].setTextSize(14);

                image_Layout[j].addView(icon_image[j]);
                //res_Layout[j].addView(nameView[j]);
                moreInfo_Layout[j].addView(nameView[j]);
                moreInfo_Layout[j].addView(priceView[j]);
                moreInfo_Layout[j].addView(ratingView[j]);
                moreInfo_Layout[j].addView(dataView[j]);
                info_Layout[j].addView(moreInfo_Layout[j]);
                //info_Layout[j].addView(priceView[j]);
                //info_Layout[j].addView(ratingView[j]);
                res_Layout[j].addView(image_Layout[j]);
                res_Layout[j].addView(info_Layout[j]);
                resultSearchLinearLayout.addView(res_Layout[j]);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Ошибка передачи", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
