package fr.edrow.kublock;

import fr.edrow.kublock.config.KLConfig;
import fr.edrow.kublock.data.KLCache;
import fr.edrow.kublock.gui.KLGuiCluster;
import me.wolfyscript.utilities.api.WolfyUtilities;
import me.wolfyscript.utilities.api.chat.Chat;
import me.wolfyscript.utilities.api.config.ConfigAPI;
import me.wolfyscript.utilities.api.inventory.gui.InventoryAPI;
import me.wolfyscript.utilities.api.language.LanguageAPI;
import me.wolfyscript.utilities.api.language.Language;
import me.wolfyscript.utilities.main.metrics.bukkit.Metrics;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class KubLock extends JavaPlugin implements Listener {

    private KLConfig config;
    private final WolfyUtilities api;

    public KubLock() {
        // Plugin Constructor.
        super();

        api = WolfyUtilities.get(this, false);
        Chat chat = api.getChat();
        chat.setInGamePrefix("§7[§3KUB§7] ");
        chat.setConsolePrefix("§7[§3KUB§7] ");

        api.setInventoryAPI(new InventoryAPI<>(api.getPlugin(), api, KLCache.class));
    }

    @Override
    public void onEnable() {
        // Plugin startup logic.
        api.initialize();
        InventoryAPI<KLCache> inventoryAPI = api.getInventoryAPI(KLCache.class);

        ConfigAPI configAPI = api.getConfigAPI();
        config = new KLConfig(configAPI, "me/edrow/kublock/config", "config",  getDataFolder().getPath(), "config", false);
        configAPI.registerConfig(config);
        config.loadDefaults();

        loadLanguage();

        inventoryAPI.registerCluster(new KLGuiCluster(inventoryAPI, "main"));

        System.out.println("KubLock has started!");
        getServer().getPluginManager().registerEvents(this, this);
        Metrics metrics = new Metrics(this, 12283);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic.
        System.out.println("KubLock has stopped!");
    }

    public void loadLanguage() {
        // Get the LanguageAPI of the api.
        LanguageAPI languageAPI = api.getLanguageAPI();
        ConfigAPI configAPI = api.getConfigAPI();
        String lang = config.getLang();

        // Save the language file resource to the plugin folder.
        saveResource("lang/en_US.json", true);
        saveResource("lang/fr_FR.json", true);

        // Create and Register the fallBack language.
        Language fallBackLanguage = new Language(this, "en_US");
        languageAPI.registerLanguage(fallBackLanguage);
        System.out.println("Loaded fallback language \"en_US\" v" + fallBackLanguage.getVersion() + "translated by " + String.join("", fallBackLanguage.getAuthors()));

        // Create and Register all language in "/lang" folder.
        File file = new File(getDataFolder(), "lang/" + lang + ".json");
        if (file.exists()) {
            Language language = new Language(this, lang);
            languageAPI.registerLanguage(language);
            languageAPI.setActiveLanguage(language);
            System.out.println("Loaded active language \"" + lang + "\" v" + language.getVersion() + " translated by " + String.join("", language.getAuthors()));
        }
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        // Interaction Event
        Player player = event.getPlayer();
        KLCache cache = api.getInventoryAPI(KLCache.class).getGuiHandler(player).getCustomCache();
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType() == Material.CHEST || event.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
                if (player.isSneaking()) {
                    api.getInventoryAPI().openCluster(player, "main");
                    System.out.println("A player has interacted with something.");
                }
            }
        }
    }
}
