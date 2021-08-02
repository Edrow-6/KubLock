package fr.edrow.kublock;

import de.leonhard.storage.Config;
import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.internal.settings.ConfigSettings;
import de.leonhard.storage.internal.settings.DataType;
import de.leonhard.storage.internal.settings.ReloadSettings;
import org.bukkit.ChatColor;

import java.io.File;

public class ConfigManager {
    private final KubLock kl;

    public ConfigManager(KubLock kublock) {
        super();
        this.kl = kublock;
    }

    public void createConfig(String name) {
        File file = new File(kl.getDataFolder(), name);

        if (!kl.getDataFolder().exists()) {
            Utils.consoleLog(ChatColor.YELLOW + "The data folder \"" + kl.getDataFolder() + "\" does not exist...");
            kl.getDataFolder().mkdir();
            if (kl.getDataFolder().mkdir()) {
                Utils.consoleLog(ChatColor.GREEN + "Creation of the data folder \"" + kl.getDataFolder() + "\" completed!");
            } else {
                Utils.consoleLog(ChatColor.RED + "Creation of the data folder \"" + kl.getDataFolder() + "\" failed!");
            }
        }

        if (!file.exists()) {
            Utils.consoleLog(ChatColor.YELLOW + "The configuration file \"" + name + "\"  was not found!");
            Config configUsingLightningBuilder = LightningBuilder
                    .fromFile(file)
                    .addInputStream(kl.getResource(name))
                    .setDataType(DataType.SORTED)
                    .setConfigSettings(ConfigSettings.PRESERVE_COMMENTS)
                    .setReloadSettings(ReloadSettings.INTELLIGENT)
                    .createConfig();

            if (file.exists()) {
                Utils.consoleLog(ChatColor.GREEN + "The configuration file \"" + name + "\" has been successfully created!");
            } else {
                Utils.consoleLog(ChatColor.RED + configUsingLightningBuilder.getString("NO_PERMISSIONS"));
            }
        }
    }
}
