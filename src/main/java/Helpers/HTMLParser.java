package Helpers;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HTMLParser {

    public static String parseHTML(String siteUrl) throws IOException {
        Connection connection = Jsoup.connect(siteUrl).maxBodySize(0);
        Connection.Response response = connection.execute();
        int statusCode = response.statusCode();
        if (statusCode == 200) {
            Document doc = connection.get();
            return doc.body().text();
        }
        throw new IOException("Received error code " + statusCode);
    }

}
