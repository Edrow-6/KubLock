package fr.edrow.kublock.listener;

import fr.edrow.kublock.KubLock;
import fr.edrow.kublock.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class PlayerListener implements Listener {
    private final KubLock kl;

    public PlayerListener(KubLock kublock) {
        this.kl = kublock;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        // Interaction Event
        Player player = event.getPlayer();
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (Objects.requireNonNull(event.getClickedBlock()).getType() == Material.CHEST || event.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
                if (player.isSneaking()) {
                    event.setCancelled(true);
                    Utils.consoleLog("A player has interacted with something.");
                }
            }
        }
    }
}
