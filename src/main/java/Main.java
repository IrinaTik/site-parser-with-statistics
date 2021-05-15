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

        System.out.println("Всего слов: " + statistic.size());
        for (Map.Entry<String, Integer> entry : statistic.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

}
