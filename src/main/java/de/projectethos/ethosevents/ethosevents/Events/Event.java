package de.projectethos.ethosevents.ethosevents.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class Event implements Listener {
    private final int maxContestants = 0;
    private final int[] center = new int[3];

    public abstract void addContestant(Player newContestant, int teamIndex);

    public abstract void createTeam(Player leader, int teamIndex);

    public abstract void removeEntitys();

    public abstract void start();

    public String[] printList() {
        return new String[0];
    }

    public int[] getCenter() {
        return center;
    }
}
