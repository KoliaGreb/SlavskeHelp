package myPackage.Connection;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.q.slavskehelp.Search_v2;

import java.io.ByteArrayInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DownloadImageFromDB extends AsyncTask<Void, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(Void... params) {
        Bitmap temp=null;
        try {

            ConnectionClass connectionClass=ConnectionClass.getInstance();
            java.sql.Connection connection=connectionClass.getConnection();
            if(connection==null) {
                return null;
            }
            try
            {
                String SQL1="SELECT CAST(pictures as varchar(MAX)) FROM Category WHERE id=1";
                Statement stmt = connection.createStatement();
                ResultSet rs =  stmt.executeQuery(SQL1);
                while (rs.next()) {
                    //temp=Drawable.createFromStream(rs.getBytes(0),"hg");
                    byte[] b = rs.getBytes(0);
                    ByteArrayInputStream is = new ByteArrayInputStream(b);
                    temp=BitmapFactory.decodeStream(is);
                }
                connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return temp;
    }
    @Override
    protected void onPostExecute(Bitmap drawable) {

        //updateImageView(drawable);
    }
}
