package de.projectethos.ethosevents.ethosevents;

import de.projectethos.ethosevents.ethosevents.Commands.Commands;
import de.projectethos.ethosevents.ethosevents.Events.Ebbah;
import de.projectethos.ethosevents.ethosevents.Events.Event;
import de.projectethos.ethosevents.ethosevents.Listeners.InventoryGUI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;

public final class EthosEvents extends JavaPlugin {
    private static EthosEvents instance;
    private final HashMap<String, Event> events = new HashMap<>();
    private static final InventoryGUI inventoryGUI = new InventoryGUI();

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("event")).setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents(inventoryGUI, this);
    }

    public boolean addEbbahEvent(String name, int[] pos, World world, String direction) {
        if (events.containsKey(name)) return false;
        events.put(name, new Ebbah(pos, world, direction));
        return true;
    }

    public void joinTeam(String eventName, Player newContestant, int teamIndex) {
        events.get(eventName).addContestant(newContestant, teamIndex);
    }

    public void createTeam(String eventName, Player leader, int teamIndex) {
        events.get(eventName).createTeam(leader, teamIndex);
    }

    public void deleteEvent(String name) {
        if (!events.containsKey(name)) return;
        events.get(name).removeEntitys();
        events.remove(name);
    }

    public HashMap<String, Event> getEvents(){
        return events;
    }

    public static InventoryGUI getInventoryGUI() {
        return inventoryGUI;
    }

    public static EthosEvents getInstance() {
        return instance;
    }

    public void registerAsListener(Event event) {
        Bukkit.getPluginManager().registerEvents(event, this);
    }

    @Override
    public void onDisable() {
        for (Event event : events.values()) {
            event.removeEntitys();
        }
    }
}