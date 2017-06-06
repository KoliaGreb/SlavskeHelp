package myPackage.Connection;

import android.annotation.SuppressLint;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionClass implements Closeable {

    private static ConnectionClass instance;
    private Connection connection;
    private Object object=new Object();

    public static ConnectionClass getInstance(){
        if(instance==null || instance.connection==null)
            instance=new ConnectionClass();
        return instance;
    }
     private ConnectionClass()
     {
         connection=CONN();
     }
     public Connection getConnection()
     {
         try {
             if(connection.isClosed())
                 connection=CONN();
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return connection;
     }

    @SuppressLint("NewApi")
    private Connection CONN() {
        Connection conn = null;
        String ConnURL = null;
        do {
            synchronized (object) {
                try {
                    Class.forName("net.sourceforge.jtds.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    return null;
                }
                ConnURL = "jdbc:jtds:sqlserver://touristinfo.database.windows.net/SlavskeHelp";
                try {
                    conn = DriverManager.getConnection(ConnURL, "dbadmin", "Kolia1928374655");
                } catch (SQLException e) {
                    return null;
                }
            }
        }
        while (conn == null);//поки мережа не знайшла зв'язок
        return conn;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}