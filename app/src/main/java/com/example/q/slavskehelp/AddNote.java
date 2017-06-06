package com.example.q.slavskehelp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import myPackage.Connection.ConnectionClass;
import myPackage.Connection.Utility;

public class AddNote extends AppCompatActivity{

    private Button addButton;
    private ImageView ivImage;
    public String typeName;
    private ActionBarDrawerToggle mToggle;
    private TextView typeNameView;
    private EditText note_name;
    private EditText note_description;
    private EditText note_price;
    private LinearLayout house;
    private LinearLayout taxi;
    private LinearLayout entertainment;
    //------------
    private EditText winter_price;
    private EditText summer_price;
    private EditText other_price;
    private EditText number_count;
    private EditText total_namber_count;
    private EditText main_service;
    private EditText additional_service;
    private EditText house_address;
    private EditText auto_marca;
    private EditText number_people_car;
    private EditText entertainment_address;
    int finalNote_id;
    //------------

    private ConnectionClass connectionClass;
    String encodedimage;
    String[] houseName={"готель","бази відпочинку","приватна садиба"};
    String[] taxiName={"всі сезони","зимове","літнє"};
    String[] actionName={"боулінг","більярд","сауна","великий теніс","кінний туризм","настільний теніс","подорожі на велосипедах","подорожі на квадроциклах"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        connectionClass=ConnectionClass.getInstance();
        //-------------------------------------------------------------
        winter_price=(EditText)findViewById(R.id.house_price_winter);
        summer_price=(EditText)findViewById(R.id.house_price_summer);
        other_price=(EditText)findViewById(R.id.house_price_other);
        number_count=(EditText)findViewById(R.id.number_room);
        total_namber_count=(EditText)findViewById(R.id.total_number_place);
        main_service=(EditText)findViewById(R.id.main_service);
        additional_service=(EditText)findViewById(R.id.addition_service);
        house_address=(EditText)findViewById(R.id.house_address);
        auto_marca=(EditText)findViewById(R.id.taxi_marca_car);
        number_people_car=(EditText)findViewById(R.id.taxi_people_count);
        entertainment_address=(EditText)findViewById(R.id.entertainmant_address);
        //-------------------------------------------------------------
        house=(LinearLayout) findViewById(R.id.layout_add_note_house);
        taxi=(LinearLayout)findViewById(R.id.layout_add_note_taxi);
        entertainment=(LinearLayout)findViewById(R.id.layout_add_note_entertainment);
        addButton=(Button)findViewById(R.id.add_note);
        note_name=(EditText)findViewById(R.id.note_name);
        note_description=(EditText)findViewById(R.id.note_description);
        note_price=(EditText)findViewById(R.id.note_price);

        DrawerLayout mDrawerLayout;
        NavigationView navigationView = (NavigationView) this.findViewById(R.id.navigation_left);
        View navView = navigationView.getHeaderView(0);
        TextView text = (TextView) navView.findViewById(R.id.head);
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
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, houseName);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, taxiName);
        final ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,
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
                    house.setVisibility(View.VISIBLE);
                    taxi.setVisibility(View.GONE);
                    entertainment.setVisibility(View.GONE);

                }
                else if(parent.getItemAtPosition(position).equals("таксі"))
                {
                    dynamicSpinner.setAdapter(adapter2);
                    house.setVisibility(View.GONE);
                    taxi.setVisibility(View.VISIBLE);
                    entertainment.setVisibility(View.GONE);
                }
                else
                {
                    dynamicSpinner.setAdapter(adapter3);
                    house.setVisibility(View.GONE);
                    taxi.setVisibility(View.GONE);
                    entertainment.setVisibility(View.VISIBLE);
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
        Button btnSelect = (Button) findViewById(R.id.btnSelectPhoto);
        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        ivImage = (ImageView) findViewById(R.id.ivImage);
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

            Connection connection=connectionClass.getConnection();

        /*ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.CONN();
        ConnectionClass connectionClass2=new ConnectionClass();
        Connection connection2=connectionClass2.CONN();
       /* ConnectionClass connectionClass3=new ConnectionClass();
        Connection connection3=connectionClass3.CONN();
        ConnectionClass connectionClass4=new ConnectionClass();
        Connection connection4=connectionClass4.CONN();*/

        if(connection==null) {
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

            String SQL2="SELECT id_type FROM Type_of_category where name='"+typeName+"'";
            ///Statement stmt2 = connection.createStatement();
            ResultSet rs2 =  stmt.executeQuery(SQL2);
            int type_id=0;
            while (rs2.next()) {
                type_id=rs2.getInt(1);
            }
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("uk","UA"));
            String query = "INSERT INTO Note(id_user, id_type, name, data_of_update, rating) values(" + user_id + "," + type_id + ",'" + note_name.getText().toString() + "','"+ dateFormat.format(new Date())+"',"+0+")";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            String SQL3="SELECT id_note FROM Note Where name='"+note_name.getText().toString()+"'";
           // Statement stmt3 = connection3.createStatement();
            ResultSet rs3 =  stmt.executeQuery(SQL3);
            int note_id=0;
            while (rs3.next()) {
                note_id=rs3.getInt(1);
            }
            //connection3.close();
            String query1="";
            if(house.getVisibility()==View.VISIBLE) {
                query1 = "INSERT INTO Property(id_note, id_name, property_value) values("
                        + note_id + "," + 1 + ",'" + note_description.getText().toString() + "'),("
                        + note_id + "," + 2 + ",'" + note_price.getText().toString() + "'),("
                        + note_id + "," + 5 + ",'" + winter_price.getText().toString() + "'),("
                        + note_id + "," + 6 + ",'" + summer_price.getText().toString() + "'),("
                        + note_id + "," + 7 + ",'" + other_price.getText().toString() + "'),("
                        + note_id + "," + 8 + ",'" + house_address.getText().toString() + "'),("
                        + note_id + "," + 9 + ",'" + number_count.getText().toString() + "'),("
                        + note_id + "," + 10 + ",'" + total_namber_count.getText().toString() + "'),("
                        + note_id + "," + 11 + ",'" + main_service.getText().toString() + "'),("
                        + note_id + "," + 12 + ",'" + additional_service.getText().toString() + "')";
            }
            else if(taxi.getVisibility()==View.VISIBLE)
            {
                query1 = "INSERT INTO Property(id_note, id_name, property_value) values("
                        + note_id + "," + 1 + ",'" + note_description.getText().toString() + "'),("
                        + note_id + "," + 2 + ",'" + note_price.getText().toString() + "'),("
                        + note_id + "," + 13 + ",'" + auto_marca.getText().toString() + "'),("
                        + note_id + "," + 14 + ",'" + note_price.getText().toString() + "')";

            }
            else
            {
                query1 = "INSERT INTO Property(id_note, id_name, property_value) values("
                        + note_id + "," + 1 + ",'" + note_description.getText().toString() + "'),("
                        + note_id + "," + 2 + ",'" + note_price.getText().toString() + "'),("
                        + note_id + "," + 15 + ",'" + entertainment_address.getText().toString() + "')";
            }
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            preparedStatement1.executeUpdate();
            preparedStatement1.close();
            finalNote_id = note_id;
            AddingPhoto temp=new AddingPhoto(this);
            temp.execute();
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

    private void selectImage() {
        final CharSequence[] items = { "Зробити фото", "Завантажити з галереї",
                "Відмінити" };
        AlertDialog.Builder builder = new AlertDialog.Builder(AddNote.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(AddNote.this);
                if (items[item].equals("Зробити фото")) {
                    userChoosenTask="Зробити фото";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Завантажити з галереї")) {
                    userChoosenTask="Завантажити з галереї";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Відмінити")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
//code for deny
                }
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        addButton.setVisibility(View.VISIBLE);
        ivImage.setImageBitmap(bm);
    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        if (thumbnail != null) {
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        }
        //encodedimage= Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addButton.setVisibility(View.VISIBLE);
        ivImage.setImageBitmap(thumbnail);
    }

    class AddingPhoto extends AsyncTask<Void,Void,Void>
    {

        Activity activity;
        public AddingPhoto(Activity activity)
        {
            this.activity=activity;

        }
        @Override
        protected Void doInBackground(Void... params) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Bitmap image=((BitmapDrawable)ivImage.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    encodedimage=Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);




                    Connection connection=connectionClass.getConnection();
                    if(connection==null) {
                        Toast.makeText(AddNote.this,
                                "Сервер не доступний, вибачте за незручності!",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String query2 = "INSERT INTO Note_Photo(id_note, photo) values(" +finalNote_id  + ", '" + encodedimage + "')";
                    PreparedStatement preparedStatement2 = null;
                    try {
                        preparedStatement2 = connection.prepareStatement(query2);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        preparedStatement2.executeUpdate();
                        connectionClass.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(AddNote.this,
                            "Фото додано!",
                            Toast.LENGTH_SHORT).show();
                    Intent newsIntent=new Intent(AddNote.this, Search.class);
                    startActivity(newsIntent);
                }
            });

            return null;
        }
    }
}
