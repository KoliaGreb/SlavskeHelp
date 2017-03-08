package myPackage.Connection;

import android.annotation.SuppressLint;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionClass {

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        do {
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
        while (conn == null);//поки мережа не знайшла зв'язок
        return conn;
    }
}