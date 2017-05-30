package com.example.q.slavskehelp;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import myPackage.Connection.ConnectionClass;

public class AddNote extends AppCompatActivity{

    public String typeName;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private TextView text;
    private TextView typeNameView;
    private EditText note_name;
    private EditText note_description;
    private EditText note_price;
    String[] houseName={"готель","бази відпочинку","приватна садиба"};
    String[] taxiName={"всі сезони","зимове","літнє"};
    String[] actionName={"боулінг","більярд","сауна","великий теніс","кінний туризм","настільний теніс","подорожі на велосипедах","подорожі на квадроциклах"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        note_name=(EditText)findViewById(R.id.note_name);
        note_description=(EditText)findViewById(R.id.note_description);
        note_price=(EditText)findViewById(R.id.note_price);

        DrawerLayout mDrawerLayout;
        navigationView = (NavigationView) this.findViewById(R.id.navigation_left);
        View navView = navigationView.getHeaderView(0);
        text = (TextView) navView.findViewById(R.id.head);
        text.setText(News.Auth_User);
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle=new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        typeNameView=(TextView)findViewById(R.id.typeNameView);
        Spinner staticSpinner = (Spinner) findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.category_array,
                        android.R.layout.simple_spinner_item);
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner.setAdapter(staticAdapter);
        final Spinner dynamicSpinner = (Spinner) findViewById(R.id.type_spinner);
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, houseName);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, taxiName);
        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, actionName);
        staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                typeNameView.setVisibility(View.VISIBLE);
                dynamicSpinner.setVisibility(View.VISIBLE);
                if(parent.getItemAtPosition(position).equals("житло"))
                {
                    dynamicSpinner.setAdapter(adapter1);

                }
                else if(parent.getItemAtPosition(position).equals("таксі"))
                {
                    dynamicSpinner.setAdapter(adapter2);
                }
                else
                {
                    dynamicSpinner.setAdapter(adapter3);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                    typeName=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
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
        Intent newsIntent=new Intent(AddNote.this, News.class);
        startActivity(newsIntent);
    }
    public void login_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(AddNote.this, Login.class);
        startActivity(newsIntent);
    }

    public void registration_start(View view) {
        Intent newsIntent=new Intent(AddNote.this, Registration.class);
        startActivity(newsIntent);
    }

    public void profile_activity_start(MenuItem item) {
        if(News.Auth_User.equals("Авторизація не пройдена"))
        {
            Toast.makeText(AddNote.this,
                    "Пройдіть авторизацію!!",
                    Toast.LENGTH_SHORT).show();
            Intent newsIntent=new Intent(AddNote.this, Login.class);
            startActivity(newsIntent);

        }
        else
        {
            Intent newsIntent=new Intent(AddNote.this, Profile.class);
            startActivity(newsIntent);
        }
    }

    public void search_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(AddNote.this, Search.class);
        startActivity(newsIntent);
    }

    public void web_cam_actiyity_start(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_web_cam:
                intent = new Intent(AddNote.this, web_cam_other_version.class);
                startActivity(intent);
                break;
            case R.id.nav_map:
                intent = new Intent(AddNote.this, MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_wether:
                intent = new Intent(AddNote.this, Weather.class);
                startActivity(intent);
                break;
        }
    }

    public void AddClick(View view) {
        ConnectionClass connectionClass=new ConnectionClass();
        java.sql.Connection connection=connectionClass.CONN();
        ConnectionClass connectionClass1=new ConnectionClass();
        java.sql.Connection connection1=connectionClass1.CONN();
        ConnectionClass connectionClass2=new ConnectionClass();
        java.sql.Connection connection2=connectionClass2.CONN();
        ConnectionClass connectionClass3=new ConnectionClass();
        java.sql.Connection connection3=connectionClass3.CONN();
        ConnectionClass connectionClass4=new ConnectionClass();
        java.sql.Connection connection4=connectionClass4.CONN();
        if(connection==null ||connection1==null ||connection2==null) {
            Toast.makeText(AddNote.this,
                    "Сервер не доступний, вибачте за незручності!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        try
        {
            String SQL1="SELECT id_user FROM Auth_User where login='"+News.Auth_User+"'";
            Statement stmt = connection.createStatement();
            ResultSet rs =  stmt.executeQuery(SQL1);
            int user_id=0;
            while (rs.next()) {
                user_id=rs.getInt(1);
            }
            connection.close();
            String SQL2="SELECT id_type FROM Type_of_category where name='"+typeName+"'";
            Statement stmt2 = connection1.createStatement();
            ResultSet rs2 =  stmt2.executeQuery(SQL2);
            int type_id=0;
            while (rs2.next()) {
                type_id=rs2.getInt(1);
            }
            connection1.close();
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("uk","UA"));
            String query = "INSERT INTO Note(id_user, id_type, name, data_of_update, rating) values(" + user_id + "," + type_id + ",'" + note_name.getText().toString() + "','"+ dateFormat.format(new Date())+"',"+0+")";
            PreparedStatement preparedStatement = connection2.prepareStatement(query);
            preparedStatement.executeUpdate();
            connection2.close();

            String SQL3="SELECT id_note FROM Note Where name='"+note_name.getText().toString()+"'";
            Statement stmt3 = connection3.createStatement();
            ResultSet rs3 =  stmt3.executeQuery(SQL3);
            int note_id=0;
            while (rs3.next()) {
                note_id=rs3.getInt(1);
            }
            connection3.close();

            String query1 = "INSERT INTO Property(id_note, id_name, property_value) values(" + note_id + "," + 1 + ",'" + note_description.getText().toString() + "'),("+ note_id + "," + 2 + ",'" + note_price.getText().toString() + "')";
            PreparedStatement preparedStatement1 = connection4.prepareStatement(query1);
            preparedStatement1.executeUpdate();
            connection4.close();
            Toast.makeText(AddNote.this,
                    "Оголошення додано!",
                    Toast.LENGTH_SHORT).show();
            Intent newsIntent=new Intent(AddNote.this, Search.class);
            startActivity(newsIntent);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
