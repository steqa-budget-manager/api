package ru.steqa.api.utility;

public class StringUtility {
    private static final String[] MONTHS = {
            "января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "октября", "ноября", "декабря"
    };

    public static String plural(Long number, String one, String few, String many) {
        long n = Math.abs(number);
        long lastTwo = n % 100;
        long last = n % 10;

        if (lastTwo >= 11 && lastTwo <= 14) {
            return many;
        }
        if (last == 1) {
            return one;
        }
        if (last >= 2 && last <= 4) {
            return few;
        }
        return many;
    }

    public static String getMonthName(int month) {
        if (month < 1 || month > 12) {
            return "";
        }
        return MONTHS[month - 1];
    }
}
