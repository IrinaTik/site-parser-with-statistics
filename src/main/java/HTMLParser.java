import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HTMLParser {

    public static String parseHTML(String siteUrl) {
        try {
            Document doc = Jsoup.connect(siteUrl).maxBodySize(0).get();
            return doc.body().text();
        } catch (IOException e) {
            e.printStackTrace();
            return "=== Error while parsing ===";
        }
    }

}
