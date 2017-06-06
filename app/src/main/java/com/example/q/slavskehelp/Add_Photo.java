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
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import myPackage.Connection.ConnectionClass;
import myPackage.Connection.Utility;

public class Add_Photo extends AppCompatActivity{

    private Button btnSelect;
    private Button addButton;
    private ImageView ivImage;
    public String typeName;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private TextView text;
    private TextView typeNameView;
    private EditText note_name;
    private EditText note_description;
    private EditText note_price;

    private ConnectionClass connectionClass;
    String encodedimage;
    String[] houseName={"готель","бази відпочинку","приватна садиба"};
    String[] taxiName={"всі сезони","зимове","літнє"};
    String[] actionName={"боулінг","більярд","сауна","великий теніс","кінний туризм","настільний теніс","подорожі на велосипедах","подорожі на квадроциклах"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__photo);

        connectionClass=ConnectionClass.getInstance();

        addButton=(Button)findViewById(R.id.add_photo);
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

        btnSelect = (Button) findViewById(R.id.btnSelectPhoto);
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
        Intent newsIntent=new Intent(Add_Photo.this, News.class);
        startActivity(newsIntent);
    }
    public void login_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(Add_Photo.this, Login.class);
        startActivity(newsIntent);
    }

    public void registration_start(View view) {
        Intent newsIntent=new Intent(Add_Photo.this, Registration.class);
        startActivity(newsIntent);
    }

    public void profile_activity_start(MenuItem item) {
        if(News.Auth_User.equals("Авторизація не пройдена"))
        {
            Toast.makeText(Add_Photo.this,
                    "Пройдіть авторизацію!!",
                    Toast.LENGTH_SHORT).show();
            Intent newsIntent=new Intent(Add_Photo.this, Login.class);
            startActivity(newsIntent);

        }
        else
        {
            Intent newsIntent=new Intent(Add_Photo.this, Profile.class);
            startActivity(newsIntent);
        }
    }

    public void search_activity_start(MenuItem item) {
        Intent newsIntent=new Intent(Add_Photo.this, Search.class);
        startActivity(newsIntent);
    }

    public void web_cam_actiyity_start(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_web_cam:
                intent = new Intent(Add_Photo.this, web_cam_other_version.class);
                startActivity(intent);
                break;
            case R.id.nav_map:
                intent = new Intent(Add_Photo.this, MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_wether:
                intent = new Intent(Add_Photo.this, Weather.class);
                startActivity(intent);
                break;
        }
    }
    private void selectImage() {
        final CharSequence[] items = { "Зробити фото", "Завантажити з галереї",
                "Відмінити" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Add_Photo.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(Add_Photo.this);
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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

        public void Add_PhotoClick(View view) {
       AddingPhoto addingPhoto=new AddingPhoto(this);
        addingPhoto.execute();
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
                        Toast.makeText(Add_Photo.this,
                                "Сервер не доступний, вибачте за незручності!",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String query2 = "INSERT INTO Note_Photo(id_note, photo) values(" + Result_Search.id_selectNote + ", '" + encodedimage + "')";
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
                    Toast.makeText(Add_Photo.this,
                            "Фото додано!",
                            Toast.LENGTH_SHORT).show();
                    Intent newsIntent=new Intent(Add_Photo.this, Search.class);
                    startActivity(newsIntent);
                }
            });

            return null;
        }
    }

}
