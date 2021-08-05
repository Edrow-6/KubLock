package fr.edrow.kublock.listener;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PatternPane;
import com.github.stefvanschie.inventoryframework.pane.util.Pattern;
import com.google.common.collect.Lists;
import de.leonhard.storage.Json;
import de.leonhard.storage.Yaml;
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
import java.util.List;
import java.util.Objects;

public class PlayerListener implements Listener {
    private final KubLock instance;
    private final Yaml config = KubLock.getInstance().getInstanceConfig();
    private final Json lang = KubLock.getInstance().getInstanceLang();

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
                "xxxxxxxx0"
        );
        PatternPane pane = new PatternPane(0, 0, 9, 3, pattern);
        gui.addPane(pane);

        pane.bindItem('x', new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));

        // Protection Information Item
        ItemStack infoItem = new ItemStack(Material.PAPER);
        ItemMeta infoMeta = infoItem.getItemMeta();
        assert infoMeta != null;
        infoMeta.setDisplayName(lang.getString("gui.button.info.title"));
        List<String> infoLore = new ArrayList<String>();
        infoLore.add(ChatColor.LIGHT_PURPLE + "Lore 1");
        infoLore.add(ChatColor.RED + "Lore 2");
        infoMeta.setLore(infoLore);
        infoItem.setItemMeta(infoMeta);
        pane.bindItem('1', new GuiItem(infoItem, e -> {
            // Action to execute
        }));

        // Close Button Item
        ItemStack closeItem = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = closeItem.getItemMeta();
        assert closeMeta != null;
        closeMeta.setDisplayName(lang.getString("gui.button.close.title"));
        pane.bindItem('0', new GuiItem(closeItem, e -> {

        }));

        List<Material> blocks = Lists.newArrayList();
        for (String materials : instance.getInstanceConfig().getStringList("blocks")) {
            blocks.add(Material.getMaterial(materials));
        }

        switch (instance.getInstanceConfig().getOrSetDefault("on_event", "SNEAK_LEFT_CLICK")) {
            case "SNEAK_LEFT_CLICK":
                if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    for (Material block : blocks) {
                        if (Objects.requireNonNull(event.getClickedBlock()).getType() == block) {
                            if (player.isSneaking()) {
                                // Execute Event
                                gui.show(player);

                                // Debug
                                event.setCancelled(true);
                                Utils.infoLog("A player has interacted with " + block);
                                event.getPlayer().sendMessage(ChatColor.of("#7CA5D9") + "Debug interaction message! (SNEAK_LEFT_CLICK)");
                            }
                        }
                    }
                }
                break;
            case "SNEAK_RIGHT_CLICK":
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    for (Material block : blocks) {
                        if (Objects.requireNonNull(event.getClickedBlock()).getType() == block) {
                            if (player.isSneaking()) {
                                // Execute Event
                                gui.show(player);

                                // Debug
                                event.setCancelled(true);
                                Utils.infoLog("A player has interacted with " + block);
                                event.getPlayer().sendMessage(ChatColor.of("#7CA5D9") + "Debug interaction message! (SNEAK_RIGHT_CLICK)");
                            }
                        }
                    }
                }
                break;
            case "LEFT_CLICK":
                if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    for (Material block : blocks) {
                        if (Objects.requireNonNull(event.getClickedBlock()).getType() == block) {
                            if (!player.isSneaking()) {
                                // Execute Event
                                gui.show(player);

                                // Debug
                                event.setCancelled(true);
                                Utils.infoLog("A player has interacted with " + block);
                                event.getPlayer().sendMessage(ChatColor.of("#7CA5D9") + "Debug interaction message! (LEFT_CLICK)");
                            }
                        }
                    }
                }
                break;
            case "RIGHT_CLICK":
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    for (Material block : blocks) {
                        if (Objects.requireNonNull(event.getClickedBlock()).getType() == block) {
                            if (!player.isSneaking()) {
                                // Execute Event
                                gui.show(player);

                                // Debug
                                event.setCancelled(true);
                                Utils.infoLog("A player has interacted with " + block);
                                event.getPlayer().sendMessage(ChatColor.of("#7CA5D9") + "Debug interaction message! (RIGHT_CLICK)");
                            }
                        }
                    }
                }
                break;
        }
    }
}
