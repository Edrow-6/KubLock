package fr.edrow.kublock;

import de.leonhard.storage.Json;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ReloadSettings;
import fr.edrow.kublock.listener.PlayerListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class KubLock extends JavaPlugin {

    private static KubLock instance;
    private Yaml config;
    private Json lang;

    public KubLock() {
        // Plugin Constructor.
        super();
        instance = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic.
        Utils.infoLog(ChatColor.DARK_PURPLE + "————————————————————————————————————————");
        Utils.infoLog(ChatColor.LIGHT_PURPLE + "The plugin has started! " + ChatColor.DARK_GRAY + instance.getDescription().getVersion());
        Utils.infoLog(ChatColor.DARK_PURPLE + "————————————————————————————————————————");

        loadConfiguration("config.yml");
        loadLanguages();

        getServer().getPluginManager().registerEvents(new PlayerListener(this), instance);

        if (config.getBoolean("metrics")) {
            Metrics metrics = new Metrics(instance, 12283);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic.
        Utils.infoLog(ChatColor.DARK_RED + "————————————————————————");
        Utils.infoLog(ChatColor.RED + "The plugin has stopped!");
        Utils.infoLog(ChatColor.DARK_RED + "————————————————————————");
    }
    public void loadConfiguration(String filename) {
        File file = new File(instance.getDataFolder(), filename);

        if (!file.exists()) {
            if (!instance.getDataFolder().isDirectory() && !instance.getDataFolder().mkdir()) {
                Utils.warningLog("Creation of the data folder \"" + instance.getDataFolder() + "\" failed!");
            }
            saveDefaultConfig();
            Utils.warningLog(ChatColor.YELLOW + "The configuration file \"" + filename + "\" was not found! Creating...");
        }
        Utils.infoLog(ChatColor.GREEN + "The configuration file \"" + filename + "\" has been successfully loaded!");

        config = new Yaml(filename, instance.getDataFolder().getPath(), instance.getResource(filename));
        config.setReloadSettings(ReloadSettings.INTELLIGENT);
    }

    public void loadLanguages() {
        String langVar = config.getOrDefault("language", "en_US");
        File file = new File(instance.getDataFolder().getPath() + "/lang", langVar + ".json");

        lang = new Json(langVar + ".json", instance.getDataFolder().getPath() + "/lang", instance.getResource("lang/" + langVar + ".json"));

        if (file.exists()) {
            Utils.infoLog(ChatColor.DARK_GREEN + "Loaded language file \"" + langVar + ".json\" successfully!");
        } else {
            Utils.warningLog(ChatColor.RED + "language file \"" + langVar + ".json\" doesn't exist!");
        }
    }

    public static KubLock getInstance() {
        return instance;
    }

    public Yaml getInstanceConfig() {
        return config;
    }

    public Json getInstanceLang() {
        return lang;
    }
}
