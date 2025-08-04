package org.blockDefense.base;

import cn.jason31416.planetlib.wrapper.SimplePlayer;
import org.blockDefense.entity.tower.Tower;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Team {
    public static Map<SimplePlayer, Team> playerTeamMap = new HashMap<>();
    public static @Nullable Team getPlayerTeam(SimplePlayer player){
        return playerTeamMap.getOrDefault(player, null);
    }
    public Set<SimplePlayer> members=new HashSet<>();
    public Tower core;

    public void addPlayer(SimplePlayer player){
        playerTeamMap.put(player, this);
        members.add(player);
    }

    public void removePlayer(SimplePlayer player){
        playerTeamMap.remove(player);
        members.remove(player);
    }
}
