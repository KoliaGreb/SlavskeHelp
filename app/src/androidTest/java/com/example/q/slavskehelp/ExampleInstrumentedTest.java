package com.example.q.slavskehelp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.ResultSet;
import java.sql.Statement;

import myPackage.Connection.ConnectionClass;
import myPackage.Connection.GetWeatherFromApi;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.q.slavskehelp", appContext.getPackageName());
    }
    @Test
    public void hasConnect() throws Exception {
        // check internet connection
        ConnectionClass connectionClass = new ConnectionClass();
        java.sql.Connection connection = connectionClass.CONN();
        assertNotNull(connection);
    }
    @Test
    public void isAuthUserNotAuth() throws Exception {
        // check not correct authorization
        String login="test_login";
        String pass="1111";
        String res="";
        ConnectionClass connectionClass=new ConnectionClass();
        java.sql.Connection connection=connectionClass.CONN();
        String SQL1="SELECT login, password FROM Auth_User";
        Statement stmt = connection.createStatement();
        ResultSet rs =  stmt.executeQuery(SQL1);
        while (rs.next()) {
            if(rs.getString(1).equals(login)&& rs.getString(2).equals(pass))
            {
                res=login;
            }
            else if(rs.getString(1).equals(login))
            {
                break;
            }
        }
        connection.close();
        assertNotEquals(res, login);
    }
    @Test
    public void isAuthUserAuth() throws Exception {
        // check correct authorization
        String login="kolia";
        String pass="1111";
        String res="";
        ConnectionClass connectionClass=new ConnectionClass();
        java.sql.Connection connection=connectionClass.CONN();
        String SQL1="SELECT login, password FROM Auth_User";
        Statement stmt = connection.createStatement();
        ResultSet rs =  stmt.executeQuery(SQL1);
        while (rs.next()) {
            if(rs.getString(1).equals(login)&& rs.getString(2).equals(pass))
            {
                res=login;
            }
            else if(rs.getString(1).equals(login))
            {
                break;
            }
        }
        connection.close();
        assertEquals(res, login);
    }
    @Test
    public void isAuthUserBadPass() throws Exception {
        // check bad password authorization
        String login="kolia";
        String pass="11";
        String errors="";
        ConnectionClass connectionClass=new ConnectionClass();
        java.sql.Connection connection=connectionClass.CONN();
        String SQL1="SELECT login, password FROM Auth_User";
        Statement stmt = connection.createStatement();
        ResultSet rs =  stmt.executeQuery(SQL1);
        int error_pass=0;
        while (rs.next()) {
            if(rs.getString(1).equals(login)&& rs.getString(2).equals(pass))
            {
                error_pass=-1;
            }
            else if(rs.getString(1).equals(login))
            {
                error_pass++;
                break;
            }
        }
        if(error_pass!=0 && error_pass!=-1)
        {
            errors="Не вірний пароль!";
        }
        else if(error_pass!=-1)
        {
            errors="Не вірні логін та пароль!";
        }
        connection.close();
        assertEquals(errors, "Не вірний пароль!");
    }
}
