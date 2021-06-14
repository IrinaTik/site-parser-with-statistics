import Data.Word;
import Helpers.HTMLParser;
import Helpers.SessionHelper;
import Helpers.TextParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.hibernate.Session;
import org.hibernate.service.spi.ServiceException;

import javax.net.ssl.SSLHandshakeException;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private final static String FILE_PATH = "siteCode.txt";

    private static final Logger ROOT_LOGGER = LogManager.getRootLogger();
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("error");
    private static final Marker INFO_MARKER = MarkerManager.getMarker("info");

    public static void main(String[] args) {

        // ввод адреса сайта
        System.out.println("Input site address:");
        Scanner scanner = new Scanner(System.in);
        String siteUrl = scanner.nextLine();

        try {
            // получение текста с сайта
            String text = HTMLParser.parseHTML(siteUrl, FILE_PATH);
            ROOT_LOGGER.info(INFO_MARKER, "Site parsing is complete - {}", siteUrl);

            // подсчет статистики
            Map<String, Integer> statistic = TextParser.parseText(text);
            ROOT_LOGGER.info(INFO_MARKER, "Text statistic computing is complete");

            System.out.println("Words count: " + statistic.size());

            SessionHelper sh = new SessionHelper();
            Session session = sh.getSession();
            ROOT_LOGGER.info(INFO_MARKER, "Initializing DB connection");

            // сохранение в базу
            for (Map.Entry<String, Integer> entry : statistic.entrySet()) {
                session.beginTransaction();
                Word word = new Word();
                word.setContext(entry.getKey());
                word.setCount(entry.getValue());
                session.save(word);
                session.getTransaction().commit();
            }
            ROOT_LOGGER.info(INFO_MARKER, "Saving statistic to BD is complete");
            sh.stop();
            ROOT_LOGGER.info(INFO_MARKER, "DB connection closed");
        } catch (ServiceException e) {
            ROOT_LOGGER.error(ERROR_MARKER, "Cannot connect to SQL base\n\t{}", e.getClass());
            System.out.println("\t" + e.getMessage());
        } catch (FileNotFoundException e) {
            ROOT_LOGGER.error(ERROR_MARKER, "Cannot write to file, file not found - " + FILE_PATH + " \n\t{}", e.getClass());
            System.out.println("\t" + e.getMessage());
        } catch (MalformedURLException | IllegalArgumentException e) {
            ROOT_LOGGER.error(ERROR_MARKER, "Wrong URL format for site {}\n\t{}", siteUrl, e.getClass());
            System.out.println("\t" + e.getMessage());
        } catch (UnknownHostException | SSLHandshakeException e) {
            ROOT_LOGGER.error(ERROR_MARKER, "No site with this URL or no connection to the internet\n\t{}", e.getClass());
            System.out.println("\t" + e.getMessage());
        } catch (Exception e) {
            ROOT_LOGGER.error(ERROR_MARKER, "Unknown error\n\t {}", e.getClass());
            e.printStackTrace();
        }

    }

}
