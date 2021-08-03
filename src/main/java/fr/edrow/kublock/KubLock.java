package fr.edrow.kublock;

import de.leonhard.storage.Json;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ReloadSettings;
import fr.edrow.kublock.listener.PlayerListener;
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
        Utils.consoleLog(ChatColor.DARK_PURPLE + "————————————————————————————————————————");
        Utils.consoleLog(ChatColor.LIGHT_PURPLE + "The plugin has started! " + ChatColor.DARK_GRAY + instance.getDescription().getVersion());
        Utils.consoleLog(ChatColor.DARK_PURPLE + "————————————————————————————————————————");

        loadConfiguration("config.yml");
        loadLanguage();

        getServer().getPluginManager().registerEvents(new PlayerListener(this), instance);

        //Metrics metrics = new Metrics(instance, 12283);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic.
        Utils.consoleLog(ChatColor.DARK_RED + "————————————————————————");
        Utils.consoleLog(ChatColor.RED + "The plugin has stopped!");
        Utils.consoleLog(ChatColor.DARK_RED + "————————————————————————");
    }
    public void loadConfiguration(String filename) {
        File file = new File(instance.getDataFolder(), filename);

        if (!file.exists()) {
            if (!instance.getDataFolder().isDirectory() && !instance.getDataFolder().mkdir()) {
                Utils.consoleLog("Creation of the data folder \"" + instance.getDataFolder() + "\" failed!");
            }
            Utils.consoleLog(ChatColor.YELLOW + "The configuration file \"" + filename + "\" was not found! Creating...");
        }
        Utils.consoleLog(ChatColor.GREEN + "The configuration file \"" + filename + "\" has been successfully loaded!");

        config = new Yaml(filename, instance.getDataFolder().getPath(), instance.getResource(filename));
        config.setReloadSettings(ReloadSettings.INTELLIGENT);
    }

    public void loadLanguage() {
        String langVar = config.getOrDefault("language", "en_US");
        File file = new File(instance.getDataFolder().getPath() + "/lang", langVar + ".json");

        lang = new Json(langVar + ".json", instance.getDataFolder().getPath() + "/lang", instance.getResource("lang/" + langVar + ".json"));

        if (file.exists()) {
            Utils.consoleLog(ChatColor.DARK_GREEN + "Loaded language file \"" + langVar + ".json\" successfully!");
        } else {
            Utils.consoleLog(ChatColor.RED + "language file \"" + langVar + ".json\" doesn't exist!");
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
