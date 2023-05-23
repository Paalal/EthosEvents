package de.projectethos.ethosevents.ethosevents.Events;

import de.projectethos.ethosevents.ethosevents.EthosEvents;
import de.projectethos.ethosevents.ethosevents.utils.Team;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Ebbah extends Event implements Listener {

    private final int maxTeams = 2;
    private final int[] center = new int[3];
    private final List<Team> teams = Arrays.asList(new Team[9]);
    private final List<Player> contestants = new ArrayList<>(9);
    private final List<Entity> entityList = new ArrayList<>();
    private boolean running = false;

    public Ebbah(int[] center, World world, String direction) {
        EthosEvents.getInstance().registerAsListener(this);
        System.arraycopy(center, 0, this.center, 0, this.center.length);
        switch (direction.toLowerCase(Locale.ROOT)) {
            case "norden":
                spawnBoatsNorth(this.center, world);
                break;
            case "osten":
                spawnBoatsEast(this.center, world);
                break;
            case "süden":
                spawnBoatsSouth(this.center, world);
                break;
            case "westen":
                spawnBoatsWest(this.center, world);
                break;
        }
        //Todo: broadcast-message
    }

    private void spawnBoatsNorth(int[] center, final World world) {
        int i;
        for (i = -10; i < 20; i = i + 10) {
            int finalI = i;
            world.spawn(new Location(world, center[0] + i + 0.5, center[1], center[2] - 19.5), Boat.class, boat -> {
                boat.setRotation(0, 0);
                boat.customName(Component.text("Donnerfluss" + 1));
                boat.setBoatType(Boat.Type.OAK);
                entityList.add((finalI + 10)/10, boat);
            });
        }
        for (i = -10; i < 20; i = i + 10) {
            int finalI = i;
            world.spawn(new Location(world, center[0] + i + 0.5, center[1], center[2] + 20.5), Boat.class, boat -> {
                boat.setRotation(180, 0);
                boat.customName(Component.text("Donnerfluss" + 2));
                boat.setBoatType(Boat.Type.OAK);
                entityList.add((finalI + 10)/10 + 3, boat);
            });
        }
        Location location = new Location(world, center[0] + 0.5, center[1], center[2] + 0.5);
        Entity armorStandLlama = world.spawn(location, ArmorStand.class, entity -> {
            entity.setInvulnerable(true);
            entity.setVisible(false);
            entity.setSmall(true);
        });
        entityList.add(6, armorStandLlama);
        Entity llama = world.spawn(location, Llama.class, entity -> {
            entity.setColor(Llama.Color.WHITE);
            entity.setTamed(true);
            entity.addPassenger(armorStandLlama);
        });
        entityList.add(7, llama);
        Entity armorStandBoat = world.spawn(location, ArmorStand.class, entity -> {
            entity.setInvulnerable(true);
            entity.setVisible(false);
            entity.setSmall(true);
        });
        entityList.add(8, armorStandBoat);
        world.spawn(location, Boat.class, boat -> {
            boat.setRotation(90, 0);
            boat.customName(Component.text("Donnerfluss" + 9));
            boat.setBoatType(Boat.Type.OAK);
            boat.addPassenger(llama);
            boat.addPassenger(armorStandBoat);
            entityList.add(9, boat);
        });
    }

    private void spawnBoatsEast(int[] center, final World world) {
        int i;
        for (i = -10; i < 20; i = i + 10) {
            int finalI = i;
            world.spawn(new Location(world, center[0] + 20.5, center[1], center[2] + i + 0.5), Boat.class, boat -> {
                boat.setRotation(90, 0);
                boat.customName(Component.text("Donnerfluss" + 1));
                boat.setBoatType(Boat.Type.OAK);
                entityList.add((finalI + 10)/10, boat);
            });
        }
        for (i = -10; i < 20; i = i + 10) {
            int finalI = i;
            world.spawn(new Location(world, center[0] - 19.5, center[1], center[2] + i + 0.5), Boat.class, boat -> {
                boat.setRotation(-90, 0);
                boat.customName(Component.text("Donnerfluss" + 2));
                boat.setBoatType(Boat.Type.OAK);
                entityList.add((finalI + 10)/10 + 3, boat);
            });
        }
        Location location = new Location(world, center[0] + 0.5, center[1], center[2] + 0.5);
        Entity armorStandLlama = world.spawn(location, ArmorStand.class, entity -> {
            entity.setInvulnerable(true);
            entity.setVisible(false);
            entity.setSmall(true);
        });
        entityList.add(6, armorStandLlama);
        Entity llama = world.spawn(location, Llama.class, entity -> {
            entity.setColor(Llama.Color.WHITE);
            entity.setTamed(true);
            entity.addPassenger(armorStandLlama);
        });
        entityList.add(7, llama);
        Entity armorStandBoat = world.spawn(location, ArmorStand.class, entity -> {
            entity.setInvulnerable(true);
            entity.setVisible(false);
            entity.setSmall(true);
        });
        entityList.add(8, armorStandBoat);
        world.spawn(location, Boat.class, boat -> {
            boat.setRotation(90, 0);
            boat.customName(Component.text("Donnerfluss" + 9));
            boat.setBoatType(Boat.Type.OAK);
            boat.addPassenger(llama);
            boat.addPassenger(armorStandBoat);
            entityList.add(9, boat);
        });
    }

    private void spawnBoatsSouth(int[] center, final World world) {
        int i;
        for (i = -10; i < 20; i = i + 10) {
            int finalI = i;
            world.spawn(new Location(world, center[0] + i + 0.5, center[1], center[2] + 20.5), Boat.class, boat -> {
                boat.setRotation(180, 0);
                boat.customName(Component.text("Donnerfluss" + 1));
                boat.setBoatType(Boat.Type.OAK);
                entityList.add((finalI + 10)/10, boat);
            });
        }
        for (i = -10; i < 20; i = i + 10) {
            int finalI = i;
            world.spawn(new Location(world, center[0] + i + 0.5, center[1], center[2] -19.5), Boat.class, boat -> {
                boat.setRotation(0, 0);
                boat.customName(Component.text("Donnerfluss" + 2));
                boat.setBoatType(Boat.Type.OAK);
                entityList.add((finalI + 10)/10 + 3, boat);
            });
        }
        Location location = new Location(world, center[0] + 0.5, center[1], center[2] + 0.5);
        Entity armorStandLlama = world.spawn(location, ArmorStand.class, entity -> {
            entity.setInvulnerable(true);
            entity.setVisible(false);
            entity.setSmall(true);
        });
        entityList.add(6, armorStandLlama);
        Entity llama = world.spawn(location, Llama.class, entity -> {
            entity.setColor(Llama.Color.WHITE);
            entity.setTamed(true);
            entity.addPassenger(armorStandLlama);
        });
        entityList.add(7, llama);
        Entity armorStandBoat = world.spawn(location, ArmorStand.class, entity -> {
            entity.setInvulnerable(true);
            entity.setVisible(false);
            entity.setSmall(true);
        });
        entityList.add(8, armorStandBoat);
        world.spawn(location, Boat.class, boat -> {
            boat.setRotation(90, 0);
            boat.customName(Component.text("Donnerfluss" + 9));
            boat.setBoatType(Boat.Type.OAK);
            boat.addPassenger(llama);
            boat.addPassenger(armorStandBoat);
            entityList.add(9, boat);
        });
    }

    private void spawnBoatsWest(int[] center, final World world) {
        int i;
        for (i = -10; i < 20; i = i + 10) {
            int finalI = i;
            world.spawn(new Location(world, center[0] - 19.5, center[1], center[2] + i + 0.5), Boat.class, boat -> {
                boat.setRotation(-90, 0);
                boat.customName(Component.text("Donnerfluss" + 1));
                boat.setBoatType(Boat.Type.OAK);
                entityList.add((finalI + 10)/10, boat);
            });
        }
        for (i = -10; i < 20; i = i + 10) {
            int finalI = i;
            world.spawn(new Location(world, center[0] + 20.5, center[1], center[2] + i + 0.5), Boat.class, boat -> {
                boat.setRotation(90, 0);
                boat.customName(Component.text("Donnerfluss" + 2));
                boat.setBoatType(Boat.Type.OAK);
                entityList.add((finalI + 10)/10 + 3, boat);
            });
        }
        Location location = new Location(world, center[0] + 0.5, center[1], center[2] + 0.5);
        Entity armorStandLlama = world.spawn(location, ArmorStand.class, entity -> {
            entity.setInvulnerable(true);
            entity.setVisible(false);
            entity.setSmall(true);
        });
        entityList.add(6, armorStandLlama);
        Entity llama = world.spawn(location, Llama.class, entity -> {
            entity.setColor(Llama.Color.WHITE);
            entity.setTamed(true);
            entity.addPassenger(armorStandLlama);
        });
        entityList.add(7, llama);
        Entity armorStandBoat = world.spawn(location, ArmorStand.class, entity -> {
            entity.setInvulnerable(true);
            entity.setVisible(false);
            entity.setSmall(true);
        });
        entityList.add(8, armorStandBoat);
        world.spawn(location, Boat.class, boat -> {
            boat.setRotation(90, 0);
            boat.customName(Component.text("Donnerfluss" + 9));
            boat.setBoatType(Boat.Type.OAK);
            boat.addPassenger(llama);
            boat.addPassenger(armorStandBoat);
            entityList.add(9, boat);
        });
    }

    @Override
    public void removeEntitys() {
        for (Entity entity : entityList) {
            entity.remove();
        }
    }

    @Override
    public void start() {
        running = true;
        int i = 0;
        for (Entity entity : entityList) {
            if (entity instanceof Boat && contestants.size() > i ) {
                entity.addPassenger(contestants.get(i++));
            }
        }
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                runEvent();
                if (i++ == 100) {
                    running = false;
                }
            }
        }.runTaskTimer(EthosEvents.getInstance(), 0, 1);
    }

    public void addContestant(Player newContestant, int teamIndex) {
        for (Player contestant : contestants) {
            if (newContestant.equals(contestant)) {
                newContestant.sendMessage("§cDu bist dem Event schon beigetreten!");
                return;
            }
        }
        if (!teams.get(teamIndex - 1).addMember(newContestant)) {
            newContestant.sendMessage("§cDas Team ist voll.");
            return;
        }
        contestants.add(newContestant);
        if (contestants.size() == 27) start();
    }
    @Override
    public void createTeam(Player leader, int teamIndex) {
        leader.sendMessage(teamIndex + " " + teams.get(0));
        if (teams.get(teamIndex - 1) != null) {
            leader.sendMessage("§cDas Team existiert schon.");
            return;
        }
        for (Player contestant : contestants) {
            if (leader.equals(contestant)) {
                leader.sendMessage("§cDu bist dem Event schon beigetreten!");
                return;
            }
        }
        teams.add(teamIndex - 1, new Team(teamIndex, leader, 3));
        leader.sendMessage("Created team");
        contestants.add(leader);
    }

    public void runEvent() {
        //Todo: GameCode
    }

    @EventHandler
    public void onBoatLeave(EntityDismountEvent e) {
        if (!running) return;
        for (Player contestant : contestants) {
            if (e.getEntity().equals(contestant)) {
                contestant.sendMessage("§cDu kannst des Boot während der Runde nicht verlassen.");
                e.setCancelled(true);
            }
        }
    }
}
