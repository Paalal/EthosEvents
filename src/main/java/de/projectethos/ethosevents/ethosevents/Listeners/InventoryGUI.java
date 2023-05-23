package de.projectethos.ethosevents.ethosevents.Listeners;

import de.projectethos.ethosevents.ethosevents.EthosEvents;
import de.projectethos.ethosevents.ethosevents.Events.Ebbah;
import de.projectethos.ethosevents.ethosevents.utils.EventType;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class InventoryGUI implements Listener {
    private final Inventory menu;
    private final Inventory hardcodedEvents;
    private Inventory activeEvents;
    private Inventory eventEditor;

    private final HashMap<Player, int[]> clickCoords = new HashMap<>();
    private final HashMap<Player, String> eventNamePlayers = new HashMap<>();
    private final HashMap<Player, String> directionPlayers = new HashMap<>();

    public InventoryGUI() {
        menu = Bukkit.createInventory(null, InventoryType.HOPPER, Component.text("§6Eventerstellung"));
        hardcodedEvents = Bukkit.createInventory(null, InventoryType.HOPPER, Component.text("Vorgefertigte Events"));
        initializeItems();
    }

    public void initializeItems() {
        menu.addItem(createGuiItem(Material.BOOK, "§dVorgefertigte Events", Arrays.asList(new Component[]{Component.text("§bHier findest Du eine Auswahl von vorprogrammierten Events,"), Component.text("§bbei denen Du nur noch das Zentrum des Spielfelds angeben musst.")})));
        menu.addItem(createGuiItem(Material.BREWING_STAND, "§dEvent Vorlagen", Arrays.asList(new Component[]{Component.text("§bHier findest Du einige Eventkategorien, bei denen"), Component.text("§bDu noch weitere Einstellungen vornehmen musst.")})));
        menu.addItem(createGuiItem(Material.PAPER, "§6Aktive Events",  Arrays.asList(new Component[]{Component.text("§bHier findest Du eine Auflistung aller aktiver Events und kannst diese bearbeiten.")})));

        hardcodedEvents.addItem(createGuiItem(Material.OAK_BOAT, "§dEBBAH", Arrays.asList(new Component[]{Component.text("§bErstelle ein EBBAH Event.")})));
    }

    protected ItemStack createGuiItem(final Material material, final String name, final List<Component> lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name));
        meta.lore(lore);

        item.setItemMeta(meta);

        return item;
    }

    public void openInventory(final HumanEntity ent, String inventoryName) {
        switch (inventoryName) {
            case "hardcodedEvents":
                ent.openInventory(hardcodedEvents);
                break;
            default:
                ent.openInventory(menu);
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        final Inventory inventory = e.getInventory();
        if (!(inventory.equals(menu) || inventory.equals(hardcodedEvents) || inventory.equals(activeEvents) || inventory.equals(eventEditor))) return;
        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final int slotNr = e.getRawSlot();
        final Player player = (Player) e.getWhoClicked();

        if (inventory.equals(menu)) {
            switch (slotNr) {
                case 0:
                    player.openInventory(hardcodedEvents);
                    break;
                case 1:
                    player.sendMessage("You clicked at slot \"Event Vorlagen\"");
                    break;
                case 2:
                    activeEvents = Bukkit.createInventory(player, InventoryType.CHEST, Component.text("Aktive Events"));
                    for (String eventName : EthosEvents.getInstance().getEvents().keySet()) {
                        //Todo: Weitere Events einfügen
                        if (EthosEvents.getInstance().getEvents().get(eventName) instanceof Ebbah) activeEvents.addItem(createGuiItem(Material.OAK_BOAT, "§d" + eventName, Arrays.asList(new Component[]{Component.text("Bearbeite das Event " + eventName)})));
                    }
                    player.openInventory(activeEvents);
                    break;
                default:
                    break;
            }
            return;
        }


        if (inventory.equals(hardcodedEvents)) {
            switch (slotNr) {
                case 0:
                    createEvent(player, EventType.EBBAH);
                    break;
                case 1:
                    //code
                    break;
                case 2:
                    break;
                default:
            }
            return;
        }

        if (inventory.equals(activeEvents)) {
            final String eventName = PlainTextComponentSerializer.plainText().serialize(clickedItem.displayName()).replaceAll("\\[|\\]", "");
            player.openInventory(createEventEditor(eventName));
            return;
        }

        if (inventory.equals(eventEditor)) {
            switch (slotNr) {
                case 1:
                    String eventName = PlainTextComponentSerializer.plainText().serialize(e.getView().title());
                    EthosEvents.getInstance().deleteEvent(eventName.replace("§d", ""));
            }
        }
    }

    private void createEvent(Player player, EventType eventType) {
        if (clickCoords.containsKey(player)) {
            //Todo: Error sound
            return;
        }
        player.closeInventory();
        player.sendMessage("§bKlicke den Zentralen Block des Spielfelds an.");
        clickCoords.put(player, null);
        new BukkitRunnable() {
            int repetitions = 0;
            @Override
            public void run() {
                if ((clickCoords.get(player)) != null){

                    int[] pos = clickCoords.get(player);
                    clickCoords.remove(player);
                    repetitions = 0;

                    player.sendMessage("§bBenenne das Event im Chat.");
                    eventNamePlayers.put(player, null);
                    new BukkitRunnable() {
                        @Override
                        public void run() {

                            if ((eventNamePlayers.get(player)) != null) {
                                String eventName = eventNamePlayers.get(player);
                                eventNamePlayers.remove(player);

                                if (eventType.equals(EventType.EBBAH)) {
                                    player.sendMessage("§bGebe die Richtung des roten Tors im Chat ein.");
                                    directionPlayers.put(player, null);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            if ((directionPlayers.get(player)) != null) {
                                                String direction = directionPlayers.get(player);
                                                directionPlayers.remove(player);
                                                if (!EthosEvents.getInstance().addEbbahEvent(eventName, pos, player.getWorld(), direction)) {
                                                    player.sendMessage("§cFehler bei dem Erstellen des Events. Gibt es den Eventnamen schon? Schaue unter §b/event auflisten§c!");
                                                }
                                                player.sendMessage("§7Event §b" + eventName + " §7erfolgreich erstellt.");
                                                cancel();
                                            }

                                            if (++repetitions == 200) {
                                                clickCoords.remove(player);
                                                player.sendMessage("§cDie Eventerstellung ist abgelaufen");
                                                cancel();
                                            }
                                        }
                                    }.runTaskTimer(EthosEvents.getInstance(), 0, 1);
                                    cancel();
                                    return;
                                }
                            }

                            if (++repetitions == 200) {
                                clickCoords.remove(player);
                                player.sendMessage("§cDie Eventerstellung ist abgelaufen");
                                cancel();
                            }
                        }
                    }.runTaskTimer(EthosEvents.getInstance(), 0, 1);
                    cancel();
                }

                if (++repetitions == 200) {
                    clickCoords.remove(player);
                    player.sendMessage("§cDie Eventerstellung ist abgelaufen");
                    cancel();
                }
            }
        }.runTaskTimer(EthosEvents.getInstance(), 0, 1);
    }

    private Inventory createEventEditor(String eventName) {
        eventEditor = Bukkit.createInventory(null, InventoryType.CHEST, Component.text(eventName));
        eventEditor.addItem(createGuiItem(Material.PLAYER_HEAD, "Teilnehmer", Arrays.asList(new Component[]{Component.text("Auflistung der Eventteilnehmer")})));
        eventEditor.addItem(createGuiItem(Material.BARRIER, "Event löschen",  Arrays.asList(new Component[]{Component.text("Löscht das Event")})));
        return eventEditor;
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(menu) || e.getInventory().equals(hardcodedEvents) || e.getInventory().equals(activeEvents) || e.getInventory().equals(eventEditor)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockClick(final PlayerInteractEvent e) {
        if (clickCoords.isEmpty()) return;
        if (!(clickCoords.containsKey(e.getPlayer()))) return;
        Block block = e.getClickedBlock();
        if (block == null) return;
        if (block.getState().getBlockData().getMaterial().equals(Material.AIR)) return;
        if (clickCoords.get(e.getPlayer()) != null) return;
        int[] pos = new int[]{block.getX(), block.getY()+1, block.getZ()};
        clickCoords.replace(e.getPlayer(), pos);
    }

    @EventHandler
    public void onChatMessage(final AsyncChatEvent e) {
        if (eventNamePlayers.isEmpty() && directionPlayers.isEmpty()) return;
        if (!(eventNamePlayers.containsKey(e.getPlayer()) || directionPlayers.containsKey(e.getPlayer()))) return;
        e.setCancelled(true);
        if (eventNamePlayers.containsKey(e.getPlayer())) {
            String eventName = ((TextComponent) e.message()).content();
            if (eventNamePlayers.get(e.getPlayer()) != null) return;
            eventNamePlayers.replace(e.getPlayer(), eventName);
        } else {
            String direction = ((TextComponent) e.message()).content().toLowerCase(Locale.ROOT);
            if (!(direction.equals("norden") || direction.equals("osten") || direction.equals("süden") || direction.equals("westen"))) {
                e.getPlayer().sendMessage("§4" + direction + " §cist keine valide Richtung");
                return;
            }
            if (directionPlayers.get(e.getPlayer()) != null) return;
            directionPlayers.replace(e.getPlayer(), direction);
        }
    }
}
