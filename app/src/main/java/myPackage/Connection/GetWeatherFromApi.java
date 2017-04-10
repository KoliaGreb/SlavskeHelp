package myPackage.Connection;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.slavskehelp.R;
import com.example.q.slavskehelp.Weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class GetWeatherFromApi extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        try {
            String result="";
            URL url;
            HttpURLConnection urlConnection;
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
        if(Weather.weather_today_Layout.getVisibility()== View.VISIBLE) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject weatherData = new JSONObject(jsonObject.getString("main"));
                JSONObject weatherWind = new JSONObject(jsonObject.getString("wind"));
                Double temperature = Double.parseDouble(weatherData.getString("temp"));
                Double humidity = Double.parseDouble(weatherData.getString("humidity"));
                Double wind = Double.parseDouble(weatherWind.getString("speed"));
                Weather.weather_wind.setText("Швидкість вітру: " + String.valueOf(wind) + "м/с");
                Weather.weather_humidity.setText("Відносна вологість: " + String.valueOf(humidity) + "%");
                int temperatureInteger = (int) (temperature - 273.15);
                if (temperatureInteger > 0) {
                    Weather.temp_value.setText("+" + String.valueOf(temperatureInteger) + "°C");
                } else {
                    Weather.temp_value.setText("" + String.valueOf(temperatureInteger) + "°C");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray weatherValue = jsonObject.getJSONArray("weather");
                String weather_text = "";
                String icon = "";
                for (int i = 0; i < weatherValue.length(); i++) {
                    weather_text = weatherValue.getJSONObject(i).getString("description");
                    icon = weatherValue.getJSONObject(i).getString("icon");
                }
                ImageGenerator(Weather.icon_image, icon);
                String res = weather_text.substring(0, 1).toUpperCase() + weather_text.substring(1);
                Weather.weather_value.setText(res);
                Weather.linlaHeaderProgress.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(Weather.weather_5day_Layout.getVisibility()== View.VISIBLE)
        {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray weatherValue = jsonObject.getJSONArray("list");
                String weather_text = "";
                LinearLayout weather5_Layout[]=new LinearLayout[weatherValue.length()];
                TextView dt_txt[]=new TextView[weatherValue.length()];
                AppCompatImageView icon5[]=new AppCompatImageView [weatherValue.length()];
                LinearLayout weather5_Layout_temp_and_value[]=new LinearLayout[weatherValue.length()];
                TextView temp5[]=new TextView[weatherValue.length()];
                TextView value5[]=new TextView[weatherValue.length()];
                LinearLayout weather5_Layout_hum_and_wind[]=new LinearLayout[weatherValue.length()];
                TextView wind5[]=new TextView[weatherValue.length()];
                TextView humidity5[]=new TextView[weatherValue.length()];
                for (int i = 0; i < weatherValue.length(); i++) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(2, 2, 2, 10);
                    weather5_Layout[i]=new LinearLayout(Weather.weather5_Layout_main.getContext());
                    dt_txt[i]=new TextView(Weather.weather5_Layout_main.getContext());
                    icon5[i]=new AppCompatImageView (Weather.weather5_Layout_main.getContext());
                    weather5_Layout_temp_and_value[i]=new LinearLayout(Weather.weather5_Layout_main.getContext());
                    weather5_Layout_hum_and_wind[i]=new LinearLayout(Weather.weather5_Layout_main.getContext());
                    temp5[i]=new TextView(Weather.weather5_Layout_main.getContext());
                    value5[i]=new TextView(Weather.weather5_Layout_main.getContext());
                    wind5[i]=new TextView(Weather.weather5_Layout_main.getContext());
                    humidity5[i]=new TextView(Weather.weather5_Layout_main.getContext());
                    weather5_Layout[i].setOrientation(LinearLayout.HORIZONTAL);
                    weather5_Layout[i].setLayoutParams(layoutParams);
                    weather5_Layout[i].setBackgroundResource(R.drawable.button_shadow);
                    weather5_Layout[i].setPadding(2,2,2,2);
                    weather5_Layout[i].setMinimumHeight(200);
                    weather5_Layout_temp_and_value[i].setOrientation(LinearLayout.VERTICAL);
                    weather5_Layout_hum_and_wind[i].setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams layoutEnd = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2f);
                    LinearLayout.LayoutParams params_dt = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                    LinearLayout.LayoutParams params_image = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2f);
                    LinearLayout.LayoutParams params_text_view = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f);
                    temp5[i].setLayoutParams(params_text_view);
                    value5[i].setLayoutParams(params_text_view);
                    wind5[i].setLayoutParams(params_text_view);
                    humidity5[i].setLayoutParams(params_text_view);
                    weather5_Layout_hum_and_wind[i].setLayoutParams(layoutEnd);
                    weather5_Layout_temp_and_value[i].setLayoutParams(layoutEnd);
                    dt_txt[i].setLayoutParams(params_dt);
                    icon5[i].setLayoutParams(params_image);
                    icon5[i].setId(i);
                    dt_txt[i].setGravity(Gravity.CENTER);
                    temp5[i].setGravity(Gravity.CENTER);
                    value5[i].setGravity(Gravity.CENTER);
                    humidity5[i].setGravity(Gravity.CENTER);
                    wind5[i].setGravity(Gravity.CENTER);
                    weather5_Layout[i].setGravity(Gravity.CENTER);
                    weather5_Layout_hum_and_wind[i].setGravity(Gravity.CENTER);
                    weather5_Layout_temp_and_value[i].setGravity(Gravity.CENTER);
                    JSONArray weather = weatherValue.getJSONObject(i).getJSONArray("weather");
                    JSONObject wind = weatherValue.getJSONObject(i).getJSONObject("wind");
                    JSONObject main = weatherValue.getJSONObject(i).getJSONObject("main");
                    String dt=weatherValue.getJSONObject(i).getString("dt_txt");
                    dt_txt[i].setText(dt);
                    dt_txt[i].setTextColor(dt_txt[i].getContext().getResources().getColor(R.color.colorMainText));
                    temp5[i].setTextColor(temp5[i].getContext().getResources().getColor(R.color.colorMainText));
                    value5[i].setTextColor(value5[i].getContext().getResources().getColor(R.color.colorMainText));
                    humidity5[i].setTextColor(humidity5[i].getContext().getResources().getColor(R.color.colorMainText));
                    wind5[i].setTextColor(wind5[i].getContext().getResources().getColor(R.color.colorMainText));

                    for(int j=0;j<weather.length();j++) {
                        weather_text = weather.getJSONObject(j).getString("description");
                        String icon = weather.getJSONObject(j).getString("icon");
                        switch (icon) {
                            case "01d":
                                icon5[i].setImageResource(R.drawable.ic_01d);

                                break;
                            case "01n":
                                icon5[i].setImageResource(R.drawable.ic_01n);

                                break;
                            case "02d":
                                icon5[i].setImageResource(R.drawable.ic_02d);

                                break;
                            case "02n":
                                icon5[i].setImageResource(R.drawable.ic_02n);

                                break;
                            case "03d":
                                icon5[i].setImageResource(R.drawable.ic_03d);

                                break;
                            case "03n":
                                icon5[i].setImageResource(R.drawable.ic_03n);

                                break;
                            case "04d":
                                icon5[i].setImageResource(R.drawable.ic_04d);
                                break;
                            case "04n":
                                icon5[i].setImageResource(R.drawable.ic_04n);
                                break;
                            case "09d":
                                icon5[i].setImageResource(R.drawable.ic_09d);
                                break;
                            case "09n":
                                icon5[i].setImageResource(R.drawable.ic_09n);
                                break;
                            case "10d":
                                icon5[i].setImageResource(R.drawable.ic_10d);
                                break;
                            case "10n":
                                icon5[i].setImageResource(R.drawable.ic_10n);
                                break;
                            case "11d":
                                icon5[i].setImageResource(R.drawable.ic_11d);
                                break;
                            case "11n":
                                icon5[i].setImageResource(R.drawable.ic_11n);
                                break;
                            case "13d":
                                icon5[i].setImageResource(R.drawable.ic_13d);
                                break;
                            case "13n":
                                icon5[i].setImageResource(R.drawable.ic_13n);
                                break;
                            case "50d":
                                icon5[i].setImageResource(R.drawable.ic_50d);
                                break;
                            default:
                                icon5[i].setImageResource(R.drawable.ic_50n);
                                break;
                        }
                        String res = weather_text.substring(0, 1).toUpperCase() + weather_text.substring(1);
                        value5[i].setText(res);
                    }
                        Double wind_speed = Double.parseDouble(wind.getString("speed"));
                        wind5[i].setText("Швидкість вітру: " + String.valueOf(wind_speed) + "м/с");
                        Double temp = Double.parseDouble(main.getString("temp"));
                        Double humidity = Double.parseDouble(main.getString("humidity"));
                        humidity5[i].setText("Відносна вологість: " + String.valueOf(humidity) + "%");
                        int temperatureDay = (int) (temp - 273.15);
                        if (temperatureDay > 0)
                        {
                            temp5[i].setText("+" + String.valueOf(temperatureDay) + "°C");
                        }
                        else
                        {
                            temp5[i].setText(""+String.valueOf(temperatureDay) + "°C");
                        }

                    weather5_Layout_temp_and_value[i].addView(temp5[i]);
                    weather5_Layout_temp_and_value[i].addView(value5[i]);
                    weather5_Layout_hum_and_wind[i].addView(wind5[i]);
                    weather5_Layout_hum_and_wind[i].addView(humidity5[i]);
                    weather5_Layout[i].addView(dt_txt[i]);
                    weather5_Layout[i].addView(icon5[i]);
                    weather5_Layout[i].addView(weather5_Layout_temp_and_value[i]);
                    weather5_Layout[i].addView(weather5_Layout_hum_and_wind[i]);
                    Weather.weather5_Layout_main.addView(weather5_Layout[i]);
                    Weather.linlaHeaderProgress.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        else if(Weather.weather_14day_Layout.getVisibility()== View.VISIBLE)
        {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE d.MM.yy", new Locale("uk","UA"));
            for(int i=0;i<14;i++)
            {
                Weather.date14[i].setText(dateFormat.format(new Date().getTime()+i*24*60*60*1000));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(2, 2, 2, 10);
                Weather.weather14_Layout[i].setLayoutParams(layoutParams);
                Weather.weather14_Layout[i].setBackgroundResource(R.drawable.button_shadow);
                Weather.weather14_Layout[i].setPadding(2,2,2,2);
                Weather.weather14_Layout[i].setMinimumHeight(200);

            }
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray weatherValue = jsonObject.getJSONArray("list");
                String weather_text = "";
                String icon = "";
                for (int i = 0; i < weatherValue.length(); i++) {
                    JSONObject temperature = weatherValue.getJSONObject(i).getJSONObject("temp");
                    JSONArray weather = weatherValue.getJSONObject(i).getJSONArray("weather");
                    Double humidity = Double.parseDouble(weatherValue.getJSONObject(i).getString("humidity"));
                    Double wind = Double.parseDouble(weatherValue.getJSONObject(i).getString("speed"));
                    Weather.wind14[i].setText("Швидкість вітру: " + String.valueOf(wind) + "м/с");
                    Weather.humidity14[i].setText("Відносна вологість: " + String.valueOf(humidity) + "%");
                    Weather.wind14[i].setTextColor(Weather.weather_value14[i].getContext().getResources().getColor(R.color.colorMainText));
                    Weather.humidity14[i].setTextColor(Weather.weather_value14[i].getContext().getResources().getColor(R.color.colorMainText));
                    Weather.temperature14[i].setTextColor(Weather.weather_value14[i].getContext().getResources().getColor(R.color.colorMainText));
                    Weather.wind14[i].setGravity(Gravity.CENTER);
                    Weather.temperature14[i].setGravity(Gravity.CENTER);
                    Weather.humidity14[i].setGravity(Gravity.CENTER);
                    Weather.weather_value14[i].setGravity(Gravity.CENTER);
                    for(int j=0;j<weather.length();j++) {
                        weather_text = weather.getJSONObject(j).getString("description");
                        icon = weather.getJSONObject(j).getString("icon");
                        ImageGenerator(Weather.image14[i], icon);
                        String res = weather_text.substring(0, 1).toUpperCase() + weather_text.substring(1);
                        Weather.weather_value14[i].setText(res);
                        Weather.weather_value14[i].setTextColor(Weather.weather_value14[i].getContext().getResources().getColor(R.color.colorMainText));
                    }
                        Double tempDay = Double.parseDouble(temperature.getString("day"));
                        Double tempNight = Double.parseDouble(temperature.getString("night"));
                        int temperatureDay = (int) (tempDay - 273.15);
                        int temperatureNight = (int) (tempNight - 273.15);
                        if (temperatureDay > 0 && temperatureNight>0)
                        {
                            Weather.temperature14[i].setText("+" + String.valueOf(temperatureDay) + "°C"+" | "+"+" + String.valueOf(temperatureNight) + "°C");
                        }
                        else if (temperatureDay > 0 && temperatureNight<0)
                        {
                            Weather.temperature14[i].setText("+" + String.valueOf(temperatureDay) + "°C"+" | " + String.valueOf(temperatureNight) + "°C");
                        }
                        else if (temperatureDay < 0 && temperatureNight>0)
                        {
                            Weather.temperature14[i].setText(String.valueOf(temperatureDay) + "°C"+" | "+"+" + String.valueOf(temperatureNight) + "°C");
                        }
                        else
                        {
                            Weather.temperature14[i].setText(String.valueOf(temperatureDay) + "°C"+" | " + String.valueOf(temperatureNight) + "°C");
                        }
                    Weather.linlaHeaderProgress.setVisibility(View.GONE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            Weather.linlaHeaderProgress.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onPreExecute() {
        // SHOW THE SPINNER WHILE LOADING FEEDS
        Weather.linlaHeaderProgress.setVisibility(View.VISIBLE);
    }
    private void ImageGenerator(ImageView icon_image, String icon)
    {
        switch (icon) {
            case "01d":
                icon_image.setImageResource(R.drawable.ic_01d);
                icon_image.invalidate();
                break;
            case "01n":
                icon_image.setImageResource(R.drawable.ic_01n);
                icon_image.invalidate();
                break;
            case "02d":
                icon_image.setImageResource(R.drawable.ic_02d);
                icon_image.invalidate();
                break;
            case "02n":
                icon_image.setImageResource(R.drawable.ic_02n);
                icon_image.invalidate();
                break;
            case "03d":
                icon_image.setImageResource(R.drawable.ic_03d);
                icon_image.invalidate();
                break;
            case "03n":
                icon_image.setImageResource(R.drawable.ic_03n);
                icon_image.invalidate();
                break;
            case "04d":
                icon_image.setImageResource(R.drawable.ic_04d);
                icon_image.invalidate();
                break;
            case "04n":
                icon_image.setImageResource(R.drawable.ic_04n);
                break;
            case "09d":
                icon_image.setImageResource(R.drawable.ic_09d);
                icon_image.invalidate();
                break;
            case "09n":
                icon_image.setImageResource(R.drawable.ic_09n);
                icon_image.invalidate();
                break;
            case "10d":
                icon_image.setImageResource(R.drawable.ic_10d);
                icon_image.invalidate();
                break;
            case "10n":
                icon_image.setImageResource(R.drawable.ic_10n);
                icon_image.invalidate();
                break;
            case "11d":
                icon_image.setImageResource(R.drawable.ic_11d);
                icon_image.invalidate();
                break;
            case "11n":
                icon_image.setImageResource(R.drawable.ic_11n);
                icon_image.invalidate();
                break;
            case "13d":
                icon_image.setImageResource(R.drawable.ic_13d);
                icon_image.invalidate();
                break;
            case "13n":
                icon_image.setImageResource(R.drawable.ic_13n);
                icon_image.invalidate();
                break;
            case "50d":
                icon_image.setImageResource(R.drawable.ic_50d);
                icon_image.invalidate();
                break;
            default:
                icon_image.setImageResource(R.drawable.ic_50n);
                icon_image.invalidate();
                break;
        }
    }
}
