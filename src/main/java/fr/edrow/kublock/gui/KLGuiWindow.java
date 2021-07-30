package fr.edrow.kublock.gui;

import fr.edrow.kublock.data.KLCache;
import me.wolfyscript.utilities.api.inventory.gui.GuiCluster;
import me.wolfyscript.utilities.api.inventory.gui.GuiUpdate;
import me.wolfyscript.utilities.api.inventory.gui.GuiWindow;
import me.wolfyscript.utilities.api.inventory.gui.button.buttons.DummyButton;
import org.bukkit.Material;

public class KLGuiWindow extends GuiWindow<KLCache> {
    // Constants can be private because the Buttons are local and can only be accessed inside this GuiWindow!
    private static final String LOCAL_BUTTON = "local_button";

    protected KLGuiWindow(GuiCluster<KLCache> cluster) {
        // Use the constant you defined in the the GuiCluster.
        super(cluster, KLGuiCluster.MAIN_WINDOW.getKey(), 54);
    }

    @Override
    public void onInit() {
        // Called on init. Used to register Buttons.
        //// For example create a dummy button and use the constant you defined on top!
        registerButton(new DummyButton(LOCAL_BUTTON, Material.BARRIER));
    }

    @Override
    public void onUpdateSync(GuiUpdate<KLCache> guiUpdate) {
        // Updated sync.
        guiUpdate.setButton(1, LOCAL_BUTTON);
        guiUpdate.setButton(2, KLGuiCluster.GLOBAL_BUTTON);
    }

    @Override
    public void onUpdateAsync(GuiUpdate<KLCache> guiUpdate) {
        // Updated async.
    }
}
