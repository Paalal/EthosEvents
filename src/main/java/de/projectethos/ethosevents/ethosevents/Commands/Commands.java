package de.projectethos.ethosevents.ethosevents.Commands;

import de.projectethos.ethosevents.ethosevents.EthosEvents;
import de.projectethos.ethosevents.ethosevents.Events.Event;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(command.getName().equalsIgnoreCase("event"))) return true;
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (args.length == 0) return false;
        HashMap<String, Event> map = EthosEvents.getInstance().getEvents();
        switch (args[0]) {
            case "neu":
                if (args.length == 1) {
                    player.sendMessage("§cAnwendung: /event neu [Event] [Eventname]");
                    return true;
                }
                switch (args[1]) {
                    case "EBBAH":
                        if (args.length == 2) {
                            player.sendMessage("§cAnwendung: /event neu EBBAH [Eventname] [Richtung]");
                            return true;
                        }
                        String name = args[2];
                        String direction = args[3];
                        Location location = player.getLocation();
                        if (!EthosEvents.getInstance().addEbbahEvent(name, new int[] {(int) location.getX(), (int) location.getY(), (int) location.getZ()}, player.getWorld(), direction)) {
                            player.sendMessage("§cFehler bei dem Erstellen des Events. Gibt es den Eventnamen schon? Schaue unter §b/event auflisten§c!");
                            break;
                        }
                        player.sendMessage("§7Event §b" + name + " §7erfolgreich erstellt.");
                        break;
                }
                break;
            case "auflisten":
                if (EthosEvents.getInstance().getEvents().keySet().size() == 0) {
                    player.sendMessage("§cKeine aktiven Events!");
                    break;
                }
                player.sendMessage("§b~~~ §dAktive Events §b~~~");
                for (String eventName : EthosEvents.getInstance().getEvents().keySet()) {
                    player.sendMessage("§6" + eventName);
                }
                player.sendMessage("§b~~~ ~~~ ~~~");
                break;
            case "test":
                EthosEvents.getInstance().addEbbahEvent("Ebbah", new int[]{-13, -60, -2}, player.getWorld(), "westen");
                break;
            case "team":
                {
                if (args.length < 4) {
                    player.sendMessage("§cAnwendung: /event team <beitreten|erstellen> [Eventname] [Teamfarbe]");
                    return true;
                }
                if (!(args[1].equals("beitreten") || args[1].equals("erstellen"))) {
                    player.sendMessage("§cAnwendung: /event team <beitreten|erstellen> [Eventname] [Teamfarbe]");
                    return true;
                }
                String eventName = args[2];
                if (!EthosEvents.getInstance().getEvents().containsKey(eventName)) {
                    player.sendMessage("§cDas Event §4" + eventName + " §cgibt es nicht!");
                    return true;
                }
                String teamColor = args[3].toLowerCase(Locale.ROOT);
                int teamIndex;
                switch (teamColor) {
                    case "rot":
                        teamIndex = 1;
                        break;
                    case "türkis":
                        teamIndex = 2;
                        break;
                    case "violett":
                        teamIndex = 3;
                        break;
                    case "grün":
                        teamIndex = 4;
                        break;
                    case "gelb":
                        teamIndex = 5;
                        break;
                    case "orange":
                        teamIndex = 6;
                        break;
                    case "blau":
                        teamIndex = 7;
                        break;
                    case "magenta":
                        teamIndex = 8;
                        break;
                    case "grau":
                        teamIndex = 9;
                        break;
                    default:
                        player.sendMessage("§cDie Teamfarbe §4" + teamColor + " §cgibt es nicht!");
                        return true;
                }
                if (args[1].equals("beitreten")) {
                    EthosEvents.getInstance().joinTeam(eventName, player, teamIndex);
                } else {
                    EthosEvents.getInstance().createTeam(eventName, player, teamIndex);
                    player.sendMessage("Erstellt");
                }
                break;
                }
            case "menu":
                EthosEvents.getInventoryGUI().openInventory(player, "menu");
                break;
            case "start":
                if (args.length == 1) {
                    player.sendMessage("§cAnwendung: /event start [Eventname]");
                    return true;
                }
                String eventName = args[1];
                if (!EthosEvents.getInstance().getEvents().containsKey(eventName)) {
                    player.sendMessage("§cDas Evetnt §4" + eventName + " §cgibt es nicht!");
                    return true;
                }
                EthosEvents.getInstance().getEvents().get(eventName).start();
            default: return false;
        }
        return true;
    }
}
