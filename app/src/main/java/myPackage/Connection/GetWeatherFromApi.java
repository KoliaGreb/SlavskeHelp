package myPackage.Connection;

import android.os.AsyncTask;

import com.example.q.slavskehelp.Weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by q on 04.04.2017.
 */

public class GetWeatherFromApi extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        try {
            String result="";
            URL url;
            HttpURLConnection urlConnection=null;
            url=new URL(urls[0]);
            urlConnection=(HttpURLConnection) url.openConnection();
            InputStream in=urlConnection.getInputStream();
            InputStreamReader reader=new InputStreamReader(in);
            int data=reader.read();
            while (data!=-1)
            {
                char current=(char) data;
                result +=current;
                data=reader.read();

            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            JSONObject jsonObject= new JSONObject(result);
            JSONObject weatherData=new JSONObject(jsonObject.getString("main"));
            Double temperature =Double.parseDouble(weatherData.getString("temp"));
            int temperatureInteger=(int) (temperature-273.15);
            String placeName=jsonObject.getString("name");
            Weather.city_name.setText(placeName);
            Weather.temp_value.setText(String.valueOf(temperatureInteger));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
