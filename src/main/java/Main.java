import Data.Word;
import Helpers.HTMLParser;
import Helpers.SessionHelper;
import Helpers.TextParser;
import org.hibernate.Session;

import java.util.Map;
import java.util.Scanner;

public class Main {

//    private final static char[] DIVIDERS = {' ', ',', '.', '!', '?','"', ';', ':', '[', ']', '(', ')', '\n', '\r', '\t'};
//    private final static String S = "Hello(World)How:are;You;doing,today?You!sir.I mean\nyot";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String siteUrl = scanner.nextLine();

        String text = HTMLParser.parseHTML(siteUrl);
        Map<String, Integer> statistic = TextParser.parseText(text);

        SessionHelper sh = new SessionHelper();
        Session session = sh.getSession();

        System.out.println("Всего слов: " + statistic.size());
        for (Map.Entry<String, Integer> entry : statistic.entrySet()) {
            session.beginTransaction();
            Word word = new Word();
            word.setContext(entry.getKey());
            word.setCount(entry.getValue());
            session.save(word);
            session.getTransaction().commit();
        }

        sh.stop();
    }

}
