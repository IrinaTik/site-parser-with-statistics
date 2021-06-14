import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionsTest {

    private static final String DEFAULT_TEST_URL = "https://ya.ru/";
    private static final String DB_URL = "jdbc:mysql://localhost:3306?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "1234";

    @Test
    @DisplayName("Internet connection test")
    public void testInternetConnection() {
        try {
            URL url = new URL(DEFAULT_TEST_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // чтобы не брать response body, создаем head request
            connection.setRequestMethod("HEAD");
            int statusCode = connection.getResponseCode();
            assertEquals(HttpURLConnection.HTTP_OK, statusCode);
        } catch (IOException e) {
            System.out.println("Error while connecting to URL " + DEFAULT_TEST_URL);
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("SQL server connection test")
    public void testDBConnection() {
        try {
            // коннектимся к серверу вообще без указания базы в частности
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            assertTrue(connection.isValid(10));
            connection.close();
        } catch (SQLException e) {
            fail(); // чтобы тест зафейлился
            System.out.println("Error while connecting to SQL server");
            e.printStackTrace();
        }
    }
}
