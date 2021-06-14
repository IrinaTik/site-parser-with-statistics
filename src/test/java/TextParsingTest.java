import Helpers.TextParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextParsingTest {

    //char[] DIVIDERS = {' ', ',', '.', '!', '?','"', ';', ':', '[', ']', '(', ')', '\n', '\r', '\t', '»', '«', '\'', '/', '—'};
    private final static String TEST_STRING = "Hello World,How.are!You?doing\"today;You:sir[I]mean(you)how\nare\ryou\tso»awesome«i\'mean/very—awesome";
    private final static Map<String, Integer> RIGHT_STATISTIC = new HashMap<>();

    @BeforeAll
    public static void fillRightStatisticMap() {
        RIGHT_STATISTIC.put("hello", 1);
        RIGHT_STATISTIC.put("world", 1);
        RIGHT_STATISTIC.put("how", 2);
        RIGHT_STATISTIC.put("are", 2);
        RIGHT_STATISTIC.put("you", 4);
        RIGHT_STATISTIC.put("doing", 1);
        RIGHT_STATISTIC.put("today", 1);
        RIGHT_STATISTIC.put("sir", 1);
        RIGHT_STATISTIC.put("i", 2);
        RIGHT_STATISTIC.put("mean", 2);
        RIGHT_STATISTIC.put("so", 1);
        RIGHT_STATISTIC.put("very", 1);
        RIGHT_STATISTIC.put("awesome", 2);
    }

    @Test
    @DisplayName("Getting words statistic test")
    public void testTextSplitting() {
        Map<String, Integer> statistic = TextParser.parseText(TEST_STRING);
        assertEquals(statistic, RIGHT_STATISTIC);
    }
}
