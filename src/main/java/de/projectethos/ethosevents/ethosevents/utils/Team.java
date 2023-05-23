package de.projectethos.ethosevents.ethosevents.utils;

import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;

import java.util.ArrayList;
import java.util.List;

public class Team {
    final int teamIndex;
    final Player leader;
    final List<Player> members = new ArrayList<>();
    final int maxMembers;
    final BlockVector teamSpawn;
    int wins;

    public Team(int teamIndex, Player leader, int maxMembers) {
        this.teamIndex = teamIndex;
        this.leader = leader;
        this.maxMembers = maxMembers;
        teamSpawn = null;
        members.add(leader);
    }

    public Team (int teamIndex, Player leader, int maxMembers, BlockVector teamSpawn) {
        this.teamIndex = teamIndex;
        this.leader = leader;
        this.maxMembers = maxMembers;
        this.teamSpawn = teamSpawn;
        members.add(leader);
    }

    public boolean addMember(Player newMember) {
        if (members.size() == maxMembers) return false;
        for (Player member : members) {
            if (member.equals(newMember)) return false;
        }
        members.add(newMember);
        return true;
    }

    public int getTeamIndex() {
        return teamIndex;
    }

    public Player getLeader() {
        return leader;
    }

    public List<Player> getMembers() {
        return members;
    }

    public int getWins() {
        return wins;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void increaseWins() {
        wins++;
    }
}
