package fr.edrow.kublock;

import fr.edrow.kublock.listener.PlayerListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class KubLock extends JavaPlugin {

    private static KubLock instance;

    public KubLock() {
        // Plugin Constructor.
        super();
        instance = this;
    }

    public static KubLock inst() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic.
        /*loadLanguage();*/

        Utils.consoleLog(ChatColor.GREEN + "KubLock has started!");
        ConfigManager cm = new ConfigManager(instance);
        cm.createConfig("config.yml");

        getServer().getPluginManager().registerEvents(new PlayerListener(this), instance);

        //Metrics metrics = new Metrics(instance, 12283);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic.
        Utils.consoleLog(ChatColor.RED + "KubLock has stopped!");
    }

    /*public void loadLanguage() {
        // Get the LanguageAPI of the api.
        LanguageAPI languageAPI = api.getLanguageAPI();
        String lang = (String) config.get("general.language");

        // Save the language file resource to the plugin folder.
        saveResource("lang/en_US.json", true);
        saveResource("lang/fr_FR.json", true);

        // Create and Register the fallBack language.
        Language fallBackLanguage = new Language(this, "en_US");
        languageAPI.registerLanguage(fallBackLanguage);
        System.out.println("Loaded fallback language \"en_US\" v" + Utils.getValueOrDefault("0", fallBackLanguage.getVersion()) + " translated by " + String.join("", fallBackLanguage.getAuthors()));

        // Create and Register all language in "/lang" folder.
        File file = new File(getDataFolder(), "lang/" + lang + ".json");
        if (file.exists()) {
            Language language = new Language(this, lang);
            languageAPI.registerLanguage(language);
            languageAPI.setActiveLanguage(language);
            System.out.println("Loaded active language \"" + lang + "\" v" + Utils.getValueOrDefault(language.getVersion(), "0") + " translated by " + String.join("", language.getAuthors()));
        }
    }*/
}
