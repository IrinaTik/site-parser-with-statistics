import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class TextParser {

    private final static char[] DIVIDERS = {' ', ',', '.', '!', '?','"', ';', ':', '[', ']', '(', ')', '\n', '\r', '\t', '»', '«', '\'', '/', '—'};

    public static Map<String, Integer> parseText(String text) {
        return countUniqueWords(getWords(text));
    }

    // считает встречаемость слов
    private static Map<String, Integer> countUniqueWords(List<String> words) {
        Map<String, Integer> statistic = new HashMap<>();

        for (String word : words) {
            // если в статистике уже есть это слово, то увеличиваем его встречаемость
            if (statistic.containsKey(word)) {
                statistic.put(word, statistic.get(word) + 1);
            // а если такого слова нет, то добавляем к статистике
            } else {
                statistic.put(word, 1);
            }
        }

        return statistic;
    }

    // делит текст на слова
    private static List<String> getWords(String text) {
        List<String> words = new ArrayList<>();
        // собираем все разделители в одну строку
        String allDividers = "";
        for (int i = 0; i < DIVIDERS.length; i++) {
            allDividers += DIVIDERS[i];
        }
        // делаем из строки с разделителями regex и делим по ним весь текст
        String regexDivider = "[" + Pattern.quote(allDividers) + "]";
        String[] arr = text.split(regexDivider);
        // формируем список из слов, исключая при этом числа
        for (int i = 0; i < arr.length; i++) {
            String word = arr[i].trim().toLowerCase();
            // пустоты и - исключаем
            // (знак "-" нельзя включить в разделители, т.к. оно может встречаться в составных словах)
            if (word.equals("") || word.equals("-")) {
                continue;
            }
            try {
                Integer.parseInt(word);
            } catch (Exception e) {
                words.add(word);
            }
        }
        return words;
    }


}
