import Data.Word;
import Helpers.HTMLParser;
import Helpers.SessionHelper;
import Helpers.TextParser;
import org.hibernate.Session;
import org.hibernate.service.spi.ServiceException;

import javax.net.ssl.SSLHandshakeException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Scanner;

public class Main {

//    private final static char[] DIVIDERS = {' ', ',', '.', '!', '?','"', ';', ':', '[', ']', '(', ')', '\n', '\r', '\t'};
//    private final static String S = "Hello(World)How:are;You;doing,today?You!sir.I mean\nyot";

    private final static String FILE_PATH = "1.txt";

    public static void main(String[] args) {

        // ввод адреса сайта
        System.out.println("Input site address:");
        Scanner scanner = new Scanner(System.in);
        String siteUrl = scanner.nextLine();

        try {
            SessionHelper sh = new SessionHelper();
            Session session = sh.getSession();

            // получение текста с сайта
            String text = HTMLParser.parseHTML(siteUrl);

            // запись полученного текста в файл
            try {
                PrintWriter writer = new PrintWriter(FILE_PATH);
                writer.write(text);
                writer.flush();
                writer.close();
            } catch (FileNotFoundException e) {
                System.out.println("Saving to file failed. File " + FILE_PATH);
                System.out.println(e.getMessage());
            }

            // подсчет статистики
            Map<String, Integer> statistic = TextParser.parseText(text);

            System.out.println("Words count: " + statistic.size());

            // сохранение в базу
            for (Map.Entry<String, Integer> entry : statistic.entrySet()) {
                session.beginTransaction();
                Word word = new Word();
                word.setContext(entry.getKey());
                word.setCount(entry.getValue());
                session.save(word);
                session.getTransaction().commit();
            }
            System.out.println("Saving to BD is complete");
            sh.stop();
        } catch (ServiceException e) {
            System.out.println("Cannot connect to SQL base");
            System.out.println(e.getMessage());
        } catch (MalformedURLException | IllegalArgumentException e) {
            System.out.println("Wrong URL format");
            System.out.println(e.getMessage());
        } catch (UnknownHostException | SSLHandshakeException e) {
            System.out.println("No site with this URL or no connection to the internet");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
