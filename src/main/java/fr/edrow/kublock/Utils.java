package fr.edrow.kublock;

public class Utils {
    // ChatColor.COLOR or ChatColor.of("#ffffff")
    public static void consoleLog(String message) {
        System.out.println(message);
    }

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }
}
