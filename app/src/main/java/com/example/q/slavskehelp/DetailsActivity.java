package com.example.q.slavskehelp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import myPackage.Connection.ConnectionClass;

public class DetailsActivity extends AppCompatActivity {
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private TextView text;
    private TextView data;
    private TextView rating;
    private TextView name;
    private TextView description;
    private TextView login;
    private TextView phone;
    private ImageView bad;
    private ImageView good;
    private Integer id_User=0;
    private TextView good_value;
    private TextView bad_value;
    private EditText comment_text;
    private LinearLayout commentLinearLayout;
    private LinearLayout image_horisontal;
    private LinearLayout changes_notes;

    private LinearLayout taxi;
    private LinearLayout house;
    private LinearLayout entertainment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        DrawerLayout mDrawerLayout;

        house=(LinearLayout)findViewById(R.id.it_is_house);
        taxi=(LinearLayout)findViewById(R.id.it_is_taxi);
        entertainment=(LinearLayout)findViewById(R.id.it_is_entertainment);

        TextView new_years_price=(TextView)findViewById(R.id.detail_price_new_year);
        TextView winter_price=(TextView)findViewById(R.id.detail_price_winter);
        TextView summer_price=(TextView)findViewById(R.id.detail_price_summer);
        TextView other_price=(TextView)findViewById(R.id.detail_price_other);
        TextView address_house=(TextView)findViewById(R.id.detail_address_house);
        TextView number_room=(TextView)findViewById(R.id.detail_room_number);
        TextView total_number=(TextView)findViewById(R.id.detail_total_place);
        TextView main_service=(TextView)findViewById(R.id.detail_main_service);
        TextView addition_service=(TextView)findViewById(R.id.detail_addition_service);
        TextView marca_auto=(TextView)findViewById(R.id.detail_marca_taxi);
        TextView number_people=(TextView)findViewById(R.id.detail_number_people_taxi);
        TextView address_entertainment=(TextView)findViewById(R.id.detail_entertainment_address);
        String str="";
        if(Search.ID_Category==1)
        {
            str="Новорічна ціна від :"+Result_Search.New_Years_Price;
            new_years_price.setText(str);
            str="Ціна(зима, без НР) :"+Result_Search.Winter_Price;
            winter_price.setText(str);
            str="Ціна(літо) :"+Result_Search.Summer_Price;
            summer_price.setText(str);
            str="Ціна(весна/осінь) :"+Result_Search.Other_Price;
            other_price.setText(str);
            str="Адреса :"+Result_Search.Address_House;
            address_house.setText(str);
            str="Кількість номерів :"+Result_Search.Room_Number;
            number_room.setText(str);
            str="Загальна кількість місць :"+Result_Search.Total_Place;
            total_number.setText(str);
            str="Послуги, які входять в вартість :"+Result_Search.Main_Service;
            main_service.setText(str);
            str="Послуги за додаткову плату :"+Result_Search.Addition_Service;
            addition_service.setText(str);
            house.setVisibility(View.VISIBLE);
            taxi.setVisibility(View.GONE);
            entertainment.setVisibility(View.GONE);
        }
        else if(Search.ID_Category==2)
        {
            str="Марка автомобіля :"+Result_Search.Auto_Marca;
            marca_auto.setText(str);
            str="Кількість пасажирів :"+Result_Search.Number_People;
            number_people.setText(str);
            house.setVisibility(View.GONE);
            taxi.setVisibility(View.VISIBLE);
            entertainment.setVisibility(View.GONE);
        }
        else
        {
            str="Адреса :"+Result_Search.Address_Entertainment;
            address_entertainment.setText(str);
            house.setVisibility(View.GONE);
            taxi.setVisibility(View.GONE);
            entertainment.setVisibility(View.VISIBLE);
        }

        changes_notes=(LinearLayout)findViewById(R.id.change_note);
        image_horisontal=(LinearLayout)findViewById(R.id.image_horisontal_layout);
        comment_text=(EditText)findViewById(R.id.new_comment_text);
        commentLinearLayout=(LinearLayout)findViewById(R.id.comment_layout);
        good_value=(TextView)findViewById(R.id.up_value);
        bad_value=(TextView)findViewById(R.id.down_value);
        bad=(ImageView)findViewById(R.id.hand_down);
        good=(ImageView)findViewById(R.id.hand_up);
        data=(TextView)findViewById(R.id.detail_data);
        rating=(TextView)findViewById(R.id.detail_rating);
        name=(TextView)findViewById(R.id.detail_name);
        description=(TextView)findViewById(R.id.detail_description);
        login=(TextView)findViewById(R.id.detail_login);
        phone=(TextView)findViewById(R.id.detail_phone);
        String temp="Дата :"+Result_Search.Data.substring(0,10);
        data.setText(temp);
        temp="Рейтинг :"+Result_Search.Rating;
        rating.setText(temp);
        name.setText(Result_Search.Name);
        description.setText(Result_Search.Description);
        temp="Автор :"+Result_Search.Autor;
        login.setText(temp);
        temp="Телефон :"+Result_Search.Phone;
        phone.setText(temp);
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
        }
        FloatingActionButton call_phone = (FloatingActionButton) findViewById(R.id.call_phone);
        call_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numb = "tel:" +Result_Search.Phone;
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(numb));
                if (ActivityCompat.checkSelfPermission(DetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(DetailsActivity.this,
                            android.Manifest.permission.CALL_PHONE)) {
                    } else {
                        ActivityCompat.requestPermissions(DetailsActivity.this,
                                new String[]{android.Manifest.permission.CALL_PHONE},1);
                    }
                }
                startActivity(intent);
            }
        });
        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.CONN();
        if(connection==null) {
            Toast.makeText(DetailsActivity.this,
                    "Сервер не доступний, вибачте за незручності!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String SQL1 = "SELECT id_user From Auth_User where login='"+News.Auth_User+"'";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL1);
            while (rs.next()) {
                id_User=rs.getInt(1);
            }
            bad.setImageDrawable(getResources().getDrawable(R.drawable.ic_thumb_down_black_24dp));
            good.setImageDrawable(getResources().getDrawable(R.drawable.ic_thumb_up_black_24dp));
            String SQL2 = "SELECT id_type_response FROM Response where id_user= "+id_User+" AND id_note= "+ Result_Search.id_selectNote;
            ResultSet rs1 = stmt.executeQuery(SQL2);
            if(rs1!=null) {
                while (rs1.next()) {
                    if(rs1.getInt(1)==1)
                    {
                        bad.setImageDrawable(getResources().getDrawable(R.drawable.ic_thumb_down_black_24dp));
                        good.setImageDrawable(getResources().getDrawable(R.drawable.ic_thumb_up_select_24dp));
                    }
                    else
                    {
                        bad.setImageDrawable(getResources().getDrawable(R.drawable.ic_thumb_down_select_24dp));
                        good.setImageDrawable(getResources().getDrawable(R.drawable.ic_thumb_up_black_24dp));
                    }
                }
            }
            int good_count=0;
            int bad_count=0;
            int rating_new;
            String SQL3 = "SELECT COUNT(*) FROM Response where id_type_response=1 AND id_note="+Result_Search.id_selectNote;
            ResultSet rs3 = stmt.executeQuery(SQL3);
            while (rs3.next()) {
                good_count=rs3.getInt(1);
            }
            String SQL4 = "SELECT COUNT(*) FROM Response where id_type_response=2 AND id_note="+Result_Search.id_selectNote;
            ResultSet rs4 = stmt.executeQuery(SQL4);
            while (rs4.next()) {
                bad_count=rs4.getInt(1);
            }
            String res_good=""+good_count;
            String res_bad=""+bad_count;
            rating_new=good_count-bad_count;
            String res_rating="Рейтинг : "+rating_new;
            good_value.setText(res_good);
            bad_value.setText(res_bad);
            rating.setText(res_rating);
            int id_user_this_note=0;
            String SQL5 = "SELECT id_user FROM Note where id_note="+Result_Search.id_selectNote;
            ResultSet rs5 = stmt.executeQuery(SQL5);
            while (rs5.next()) {
                id_user_this_note=rs5.getInt(1);
            }
            if(id_user_this_note!=0 && id_User!=0 &&id_user_this_note==id_User)
            {
                changes_notes.setVisibility(View.VISIBLE);
            }
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();

        }
        ConnectionClass connectionClass1=new ConnectionClass();
        Connection connection1=connectionClass1.CONN();
        if(connection1==null) {
            Toast.makeText(DetailsActivity.this,
                    "Сервер не доступний, вибачте за незручності!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        try
        {
            String SQL1="SELECT text, id_user FROM Comments WHERE id_note="+Result_Search.id_selectNote;
            Statement stmt = connection1.createStatement();
            ResultSet rs =  stmt.executeQuery(SQL1);
            final ArrayList<String> comment_text=new ArrayList<String>();
            final ArrayList<Integer> comment_id_user=new ArrayList<Integer>();
            final ArrayList<String> user_login=new ArrayList<String>();
            while (rs.next()) {
                comment_text.add(rs.getString(1));
                comment_id_user.add(rs.getInt(2));
            }
            for(int j=0;j<comment_text.size();j++)
            {
                String SQL4="SELECT login FROM Auth_User WHERE id_user="+comment_id_user.get(j);
                ResultSet rs4 =  stmt.executeQuery(SQL4);
                while (rs4.next()) {
                    user_login.add(rs4.getString(1));
                }
                rs4.close();
            }
            connection1.close();
            LinearLayout res_Layout[]=new LinearLayout[comment_text.size()];
            TextView textView[]=new TextView[comment_text.size()];
            TextView loginView[]=new TextView[comment_text.size()];
            LinearLayout text_Layout[]=new LinearLayout[comment_text.size()];
            LinearLayout login_Layout[]=new LinearLayout[comment_text.size()];
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(2, 2, 2, 10);
            LinearLayout.LayoutParams params_text = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams params_info= new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params_info.setMargins(20,0,0,8);
            for(int j=0;j<comment_text.size();j++)
            {
                res_Layout[j]=new LinearLayout(this);
                res_Layout[j].setOrientation(LinearLayout.VERTICAL);
                res_Layout[j].setLayoutParams(layoutParams);
                res_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                res_Layout[j].setPadding(2,2,2,2);
                res_Layout[j].setMinimumHeight(100);
                res_Layout[j].setGravity(Gravity.CENTER);

                text_Layout[j]=new LinearLayout(this);
                text_Layout[j].setOrientation(LinearLayout.HORIZONTAL);
                text_Layout[j].setLayoutParams(params_text);
                //text_Layout[j].setBackgroundResource(R.drawable.commentar_layout);
                text_Layout[j].setPadding(2,2,2,2);
                text_Layout[j].setMinimumHeight(100);
                text_Layout[j].setGravity(Gravity.CENTER);

                login_Layout[j]=new LinearLayout(this);
                login_Layout[j].setOrientation(LinearLayout.HORIZONTAL);
                login_Layout[j].setLayoutParams(params_text);
                //login_Layout[j].setBackgroundResource(R.drawable.commentar_layout);
                login_Layout[j].setPadding(2,2,2,2);
                login_Layout[j].setMinimumHeight(50);
                login_Layout[j].setGravity(Gravity.RIGHT);

                textView[j]=new TextView(this);
                textView[j].setLayoutParams(params_info);
                textView[j].setGravity(Gravity.CENTER);
                textView[j].setPadding(10,5,10,5);
                textView[j].setBackgroundResource(R.drawable.commentar_layout);
                textView[j].setTextColor(textView[j].getContext().getResources().getColor(R.color.colorMainText));
                textView[j].setText(comment_text.get(j));
                textView[j].setTextSize(16);

                loginView[j]=new TextView(this);
                loginView[j].setLayoutParams(params_info);
                loginView[j].setGravity(Gravity.RIGHT);
                loginView[j].setTextColor(loginView[j].getContext().getResources().getColor(R.color.colorMainText));
                String temp_autor="Автор : "+user_login.get(j);
                loginView[j].setText(temp_autor);
                loginView[j].setTypeface(null, Typeface.ITALIC);
                loginView[j].setTextSize(12);
                text_Layout[j].addView(textView[j]);
                login_Layout[j].addView(loginView[j]);
                res_Layout[j].addView(text_Layout[j]);
                res_Layout[j].addView(login_Layout[j]);

                commentLinearLayout.addView(res_Layout[j]);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }



        ConnectionClass connectionClass2=new ConnectionClass();
        Connection connection2=connectionClass2.CONN();
        if(connection2==null) {
            Toast.makeText(DetailsActivity.this,
                    "Сервер не доступний, вибачте за незручності!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        try
        {
            String SQL1="SELECT photo FROM Note_Photo WHERE id_note="+Result_Search.id_selectNote;
            Statement stmt = connection2.createStatement();
            ResultSet rs =  stmt.executeQuery(SQL1);
            final ArrayList<String> photo=new ArrayList<String>();
            while (rs.next()) {
                photo.add(rs.getString(1));
            }
            rs.close();
            connection2.close();
            ImageView imView[]=new ImageView[photo.size()];

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(2, 2, 2, 2);
            LinearLayout image_Layout[]=new LinearLayout[photo.size()];
            for(int j=0;j<photo.size();j++)
            {
                image_Layout[j]=new LinearLayout(this);
                image_Layout[j].setOrientation(LinearLayout.HORIZONTAL);
                image_Layout[j].setLayoutParams(layoutParams);
                image_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                image_Layout[j].setPadding(2,2,2,2);
                image_Layout[j].setMinimumHeight(200);
                image_Layout[j].setGravity(Gravity.CENTER);

                imView[j]=new ImageView(this);
                imView[j].setLayoutParams(new GridView.LayoutParams(500, 500));
                imView[j].setScaleType(ImageView.ScaleType.FIT_XY);
                imView[j].setPadding(4, 4, 4, 4);
                byte[] decodedString = Base64.decode(photo.get(j), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imView[j].setImageBitmap(decodedByte);
                image_Layout[j].addView(imView[j]);
                image_horisontal.addView(image_Layout[j]);
            }
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
        Intent newsIntent=new Intent(DetailsActivity.this, News.class);
        startActivity(newsIntent);

    }
    public void login_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(DetailsActivity.this, Login.class);
        startActivity(newsIntent);
    }

    public void registration_start(View view) {
        Intent newsIntent=new Intent(DetailsActivity.this, Registration.class);
        startActivity(newsIntent);
    }
    public void profile_activity_start(MenuItem item) {
        if(News.Auth_User.equals("Авторизація не пройдена"))
        {
            Toast.makeText(DetailsActivity.this,
                    "Пройдіть авторизацію!!",
                    Toast.LENGTH_SHORT).show();
            Intent newsIntent=new Intent(DetailsActivity.this, Login.class);
            startActivity(newsIntent);

        }
        else
        {
            Intent newsIntent=new Intent(DetailsActivity.this, Profile.class);
            startActivity(newsIntent);
        }
    }

    public void search_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(DetailsActivity.this, Search.class);
        startActivity(newsIntent);
    }

    public void web_cam_actiyity_start(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_web_cam:
                intent = new Intent(DetailsActivity.this, web_cam_other_version.class);
                startActivity(intent);
                break;
            case R.id.nav_map:
                intent = new Intent(DetailsActivity.this, MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_wether:
                intent = new Intent(DetailsActivity.this, Weather.class);
                startActivity(intent);
                break;
        }
    }

    public void bad_click(View view) {
        if(News.Auth_User.equals("Авторизація не пройдена"))
        {
            Toast.makeText(DetailsActivity.this,
                    "Пройдіть авторизацію!!",
                    Toast.LENGTH_SHORT).show();
            Intent newsIntent=new Intent(DetailsActivity.this, Login.class);
            startActivity(newsIntent);

        }
        else {
            bad.setImageDrawable(getResources().getDrawable(R.drawable.ic_thumb_down_select_24dp));
            good.setImageDrawable(getResources().getDrawable(R.drawable.ic_thumb_up_black_24dp));
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.CONN();
            if (connection == null) {
                Toast.makeText(DetailsActivity.this,
                        "Сервер не доступний, вибачте за незручності!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                String SQL1 = "DELETE FROM Response WHERE id_note= " + Result_Search.id_selectNote + " AND id_user= " + id_User + ";INSERT INTO Response(id_note, id_user, id_type_response) values(" + Result_Search.id_selectNote + "," + id_User + ", 2)";
                PreparedStatement preparedStmt = connection.prepareStatement(SQL1);
                preparedStmt.executeUpdate();

                int good_count=0;
                int bad_count=0;
                String SQL3 = "SELECT COUNT(*) FROM Response where id_type_response=1 AND id_note="+Result_Search.id_selectNote;
                Statement stmt = connection.createStatement();
                ResultSet rs3 = stmt.executeQuery(SQL3);
                while (rs3.next()) {
                    good_count=rs3.getInt(1);
                }
                String SQL4 = "SELECT COUNT(*) FROM Response where id_type_response=2 AND id_note="+Result_Search.id_selectNote;
                ResultSet rs4 = stmt.executeQuery(SQL4);
                while (rs4.next()) {
                    bad_count=rs4.getInt(1);
                }
                int rating_new;
                String res_good=""+good_count;
                String res_bad=""+bad_count;
                rating_new=good_count-bad_count;
                String res_rating="Рейтинг : "+rating_new;
                good_value.setText(res_good);
                bad_value.setText(res_bad);
                rating.setText(res_rating);
                String SQLrating = "UPDATE Note Set rating="+ rating_new+" where id_note="+Result_Search.id_selectNote;
                PreparedStatement preparedStmt1 = connection.prepareStatement(SQLrating);
                preparedStmt1.executeUpdate();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }

    public void good_click(View view) {
        if(News.Auth_User.equals("Авторизація не пройдена"))
        {
            Toast.makeText(DetailsActivity.this,
                    "Пройдіть авторизацію!!",
                    Toast.LENGTH_SHORT).show();
            Intent newsIntent=new Intent(DetailsActivity.this, Login.class);
            startActivity(newsIntent);
        }
        else {
            bad.setImageDrawable(getResources().getDrawable(R.drawable.ic_thumb_down_black_24dp));
            good.setImageDrawable(getResources().getDrawable(R.drawable.ic_thumb_up_select_24dp));
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.CONN();
            if (connection == null) {
                Toast.makeText(DetailsActivity.this,
                        "Сервер не доступний, вибачте за незручності!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                String SQL1 = "DELETE FROM Response WHERE id_note= " + Result_Search.id_selectNote + " AND id_user= " + id_User + ";INSERT INTO Response(id_note, id_user, id_type_response) values(" + Result_Search.id_selectNote + "," + id_User + ", 1)";
                PreparedStatement preparedStmt = connection.prepareStatement(SQL1);
                preparedStmt.executeUpdate();
                int good_count=0;
                int bad_count=0;
                String SQL3 = "SELECT COUNT(*) FROM Response where id_type_response=1 AND id_note="+Result_Search.id_selectNote;
                Statement stmt = connection.createStatement();
                ResultSet rs3 = stmt.executeQuery(SQL3);
                while (rs3.next()) {
                    good_count=rs3.getInt(1);
                }
                String SQL4 = "SELECT COUNT(*) FROM Response where id_type_response=2 AND id_note="+Result_Search.id_selectNote;
                ResultSet rs4 = stmt.executeQuery(SQL4);
                while (rs4.next()) {
                    bad_count=rs4.getInt(1);
                }
                String res_good=""+good_count;
                String res_bad=""+bad_count;
                int rating_new=good_count-bad_count;
                String res_rating="Рейтинг : "+rating_new;
                good_value.setText(res_good);
                bad_value.setText(res_bad);
                rating.setText(res_rating);
                String SQLrating = "UPDATE Note Set rating="+ rating_new+" where id_note="+Result_Search.id_selectNote;
                PreparedStatement preparedStmt1 = connection.prepareStatement(SQLrating);
                preparedStmt1.executeUpdate();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }
    public void AddCommentClick(View view) {
        if(News.Auth_User.equals("Авторизація не пройдена"))
        {
            Toast.makeText(DetailsActivity.this,
                    "Пройдіть авторизацію!!",
                    Toast.LENGTH_SHORT).show();
            Intent newsIntent=new Intent(DetailsActivity.this, Login.class);
            startActivity(newsIntent);
        }
        else {

            ConnectionClass connectionClass=new ConnectionClass();
            Connection connection=connectionClass.CONN();
            if(connection==null) {
                Toast.makeText(DetailsActivity.this,
                        "Сервер не доступний, вибачте за незручності!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                String SQL0="INSERT INTO Comments(id_note, id_user,text) values("+Result_Search.id_selectNote+","+ id_User+",'"+comment_text.getText()+"')";
                PreparedStatement preparedStmt = connection.prepareStatement(SQL0);
                preparedStmt.executeUpdate();
                connection.close();

            }

            catch (SQLException e) {
                e.printStackTrace();
            }
            commentLinearLayout.removeAllViews();
            ConnectionClass connectionClass1=new ConnectionClass();
            Connection connection1=connectionClass1.CONN();
            if(connection1==null) {
                Toast.makeText(DetailsActivity.this,
                        "Сервер не доступний, вибачте за незручності!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            try
            {
                String SQL1="SELECT text, id_user FROM Comments WHERE id_note="+Result_Search.id_selectNote;
                Statement stmt = connection1.createStatement();
                ResultSet rs =  stmt.executeQuery(SQL1);
                final ArrayList<String> comment_text=new ArrayList<String>();
                final ArrayList<Integer> comment_id_user=new ArrayList<Integer>();
                final ArrayList<String> user_login=new ArrayList<String>();
                while (rs.next()) {
                    comment_text.add(rs.getString(1));
                    comment_id_user.add(rs.getInt(2));
                }
                for(int j=0;j<comment_text.size();j++)
                {
                    String SQL4="SELECT login FROM Auth_User WHERE id_user="+comment_id_user.get(j);
                    ResultSet rs4 =  stmt.executeQuery(SQL4);
                    while (rs4.next()) {
                        user_login.add(rs4.getString(1));
                    }
                    rs4.close();
                }
                connection1.close();
                LinearLayout res_Layout[]=new LinearLayout[comment_text.size()];
                TextView textView[]=new TextView[comment_text.size()];
                TextView loginView[]=new TextView[comment_text.size()];
                LinearLayout text_Layout[]=new LinearLayout[comment_text.size()];
                LinearLayout login_Layout[]=new LinearLayout[comment_text.size()];
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(2, 2, 2, 10);
                LinearLayout.LayoutParams params_text = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams params_info= new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params_info.setMargins(20,0,0,8);
                for(int j=0;j<comment_text.size();j++)
                {
                    res_Layout[j]=new LinearLayout(this);
                    res_Layout[j].setOrientation(LinearLayout.VERTICAL);
                    res_Layout[j].setLayoutParams(layoutParams);
                    res_Layout[j].setBackgroundResource(R.drawable.button_shadow);
                    res_Layout[j].setPadding(2,2,2,2);
                    res_Layout[j].setMinimumHeight(100);
                    res_Layout[j].setGravity(Gravity.CENTER);

                    text_Layout[j]=new LinearLayout(this);
                    text_Layout[j].setOrientation(LinearLayout.HORIZONTAL);
                    text_Layout[j].setLayoutParams(params_text);
                    //text_Layout[j].setBackgroundResource(R.drawable.commentar_layout);
                    text_Layout[j].setPadding(2,2,2,2);
                    text_Layout[j].setMinimumHeight(100);
                    text_Layout[j].setGravity(Gravity.CENTER);

                    login_Layout[j]=new LinearLayout(this);
                    login_Layout[j].setOrientation(LinearLayout.HORIZONTAL);
                    login_Layout[j].setLayoutParams(params_text);
                    //login_Layout[j].setBackgroundResource(R.drawable.commentar_layout);
                    login_Layout[j].setPadding(2,2,2,2);
                    login_Layout[j].setMinimumHeight(50);
                    login_Layout[j].setGravity(Gravity.RIGHT);

                    textView[j]=new TextView(this);
                    textView[j].setLayoutParams(params_info);
                    textView[j].setGravity(Gravity.CENTER);
                    textView[j].setPadding(10,5,10,5);
                    textView[j].setBackgroundResource(R.drawable.commentar_layout);
                    textView[j].setTextColor(textView[j].getContext().getResources().getColor(R.color.colorMainText));
                    textView[j].setText(comment_text.get(j));
                    textView[j].setTextSize(16);

                    loginView[j]=new TextView(this);
                    loginView[j].setLayoutParams(params_info);
                    loginView[j].setGravity(Gravity.RIGHT);
                    loginView[j].setTextColor(loginView[j].getContext().getResources().getColor(R.color.colorMainText));
                    String temp_autor="Автор : "+user_login.get(j);
                    loginView[j].setText(temp_autor);
                    loginView[j].setTypeface(null, Typeface.ITALIC);
                    loginView[j].setTextSize(12);
                    text_Layout[j].addView(textView[j]);
                    login_Layout[j].addView(loginView[j]);
                    res_Layout[j].addView(text_Layout[j]);
                    res_Layout[j].addView(login_Layout[j]);

                    commentLinearLayout.addView(res_Layout[j]);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            comment_text.setText("");
        }
    }

    public void openPhotoClick(View view) {
        Intent newsIntent=new Intent(DetailsActivity.this, Add_Photo.class);
        startActivity(newsIntent);
    }

    public void deleteNote(View view) {
        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.CONN();
        if(connection==null) {
            Toast.makeText(DetailsActivity.this,
                    "Сервер не доступний, вибачте за незручності!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String SQL0="DELETE FROM Note_Photo WHERE id_note="+Result_Search.id_selectNote+"; "+
                    "DELETE FROM Response WHERE id_note="+Result_Search.id_selectNote+"; "+
                    "DELETE FROM Comments WHERE id_note="+Result_Search.id_selectNote+"; "+
                    "DELETE FROM Property WHERE id_note="+Result_Search.id_selectNote+"; "+
                    "DELETE FROM Note WHERE id_note="+Result_Search.id_selectNote+"; ";
            PreparedStatement preparedStmt = connection.prepareStatement(SQL0);
            preparedStmt.executeUpdate();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        Toast.makeText(DetailsActivity.this,
                "Оголошення видалено!",
                Toast.LENGTH_SHORT).show();
        Intent newsIntent=new Intent(DetailsActivity.this, Search.class);
        startActivity(newsIntent);
    }
}
