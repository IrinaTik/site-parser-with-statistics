package Helpers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class HTMLParser {

    public static String parseHTML(String siteUrl, String filePath) throws IOException {
        // открытие соединения с сайтом
        URLConnection url = new URL(siteUrl).openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String inputLine;

        // построчное считывание кода страницы и запись этого кода в файл
        PrintWriter writer = new PrintWriter(filePath);
        while ((inputLine = reader.readLine()) != null) {
            builder.append(inputLine);
            writer.write(inputLine + "\n");
        }
        reader.close();
        writer.flush();
        writer.close();

        // получение текста из кода страницы
        Document doc = Jsoup.parse(builder.toString());
        return doc.body().text();
    }

}
