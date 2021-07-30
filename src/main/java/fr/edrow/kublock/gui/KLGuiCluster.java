package fr.edrow.kublock.gui;

import fr.edrow.kublock.data.KLCache;
import me.wolfyscript.utilities.api.inventory.gui.GuiCluster;
import me.wolfyscript.utilities.api.inventory.gui.InventoryAPI;
import me.wolfyscript.utilities.api.inventory.gui.button.buttons.DummyButton;
import me.wolfyscript.utilities.util.NamespacedKey;
import org.bukkit.Material;

public class KLGuiCluster extends GuiCluster<KLCache> {
    // Constant for Cluster id.
    public static final String CLUSTER_ID = "main";
    // Constant for NamespacedKey for windows.
    public static final NamespacedKey MAIN_WINDOW = new NamespacedKey(CLUSTER_ID, "main_window");
    // Constant for Global Buttons.
    public static final NamespacedKey GLOBAL_BUTTON = new NamespacedKey(CLUSTER_ID, "global_button");

    public KLGuiCluster(InventoryAPI<KLCache> inventoryAPI, String CLUSTER_ID) {
        super(inventoryAPI, CLUSTER_ID);
    }

    @Override
    public void onInit() {
        // Called on init! Used to register GuiWindows and Buttons.
        registerGuiWindow(new MainWindow(this));
        registerButton(new DummyButton<KLCache>(GLOBAL_BUTTON.getKey(), Material.BARRIER));
    }
}
