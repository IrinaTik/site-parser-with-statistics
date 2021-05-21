import Data.Word;
import Helpers.HTMLParser;
import Helpers.SessionHelper;
import Helpers.TextParser;
import org.hibernate.Session;
import org.hibernate.service.spi.ServiceException;

import javax.net.ssl.SSLHandshakeException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Scanner;

public class Main {

//    private final static char[] DIVIDERS = {' ', ',', '.', '!', '?','"', ';', ':', '[', ']', '(', ')', '\n', '\r', '\t'};
//    private final static String S = "Hello(World)How:are;You;doing,today?You!sir.I mean\nyot";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String siteUrl = scanner.nextLine();

        try {
            SessionHelper sh = new SessionHelper();
            Session session = sh.getSession();

            String text = HTMLParser.parseHTML(siteUrl);
            Map<String, Integer> statistic = TextParser.parseText(text);

            System.out.println("Всего слов: " + statistic.size());

            // сохранение в базу
            for (Map.Entry<String, Integer> entry : statistic.entrySet()) {
                session.beginTransaction();
                Word word = new Word();
                word.setContext(entry.getKey());
                word.setCount(entry.getValue());
                session.save(word);
                session.getTransaction().commit();
            }

            sh.stop();
        } catch (ServiceException e) {
            System.out.println("Cannot connect to SQL base");
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
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
