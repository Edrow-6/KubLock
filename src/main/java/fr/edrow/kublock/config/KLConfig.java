package fr.edrow.kublock.config;

import me.wolfyscript.utilities.api.config.YamlConfiguration;
import me.wolfyscript.utilities.api.config.ConfigAPI;

public class KLConfig extends YamlConfiguration {
    public KLConfig(ConfigAPI configAPI, String path, String name, String defPath, String defFileName, boolean override) {
        super(configAPI, path, name, defPath, defFileName, override);
    }

    public String getLang() {
        return getString("language");
    }
}
