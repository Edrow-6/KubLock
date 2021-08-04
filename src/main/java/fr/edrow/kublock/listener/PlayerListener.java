package fr.edrow.kublock.listener;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PatternPane;
import com.github.stefvanschie.inventoryframework.pane.util.Pattern;
import com.google.common.collect.Lists;
import fr.edrow.kublock.KubLock;
import fr.edrow.kublock.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PlayerListener implements Listener {
    private final KubLock instance;

    public PlayerListener(KubLock instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        // Interaction Event
        int x = Objects.requireNonNull(event.getClickedBlock()).getX();
        int y = event.getClickedBlock().getY();
        int z = event.getClickedBlock().getZ();

        String xCoord = String.valueOf(x);
        String yCoord = String.valueOf(y);
        String zCoord = String.valueOf(z);

        Player player = event.getPlayer();
        ChestGui gui = new ChestGui(3, "KubLock • Menu");
        gui.setOnGlobalClick(e -> e.setCancelled(true));
        Pattern pattern = new Pattern(
                "xxxxxxxxx",
                "xoxoxox1x",
                "xxxxxxxxx"
        );
        PatternPane pane = new PatternPane(0, 0, 9, 3, pattern);
        gui.addPane(pane);

        // Protection Information Item
        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta paperMeta = paper.getItemMeta();
        assert paperMeta != null;
        paperMeta.setDisplayName("Protection Info");
        List<String> paperLore = new ArrayList<String>();
        paperLore.add(ChatColor.LIGHT_PURPLE + "Lore 1");
        paperLore.add(ChatColor.RED + "Lore 2");
        paperMeta.setLore(paperLore);
        paper.setItemMeta(paperMeta);

        pane.bindItem('x', new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
        pane.bindItem('1', new GuiItem(paper, e -> {
            // Action to execute
        }));

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            List<Material> blocks = Lists.newArrayList();
            for(String materials : instance.getInstanceConfig().getStringList("blocks")) {
                blocks.add(Material.getMaterial(materials));
            }
            for(Material block : blocks) {
                if (Objects.requireNonNull(event.getClickedBlock()).getType() == block) {
                    if (player.isSneaking()) {
                        gui.show(player);

                        event.setCancelled(true);
                        Utils.infoLog("A player has interacted with something.");
                        event.getPlayer().sendMessage(ChatColor.of("#7CA5D9") + "Debug interaction message!");
                    }
                }
            }
        }
    }
}
