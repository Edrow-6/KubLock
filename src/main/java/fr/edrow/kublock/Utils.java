package fr.edrow.kublock;

public class Utils {
    // ChatColor.COLOR or ChatColor.of("#ffffff")
    public static void infoLog(String message) {
        KubLock.getInstance().getLogger().info(message);
    }

    public static void warningLog(String message) {
        KubLock.getInstance().getLogger().warning(message);
    }

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }
}
