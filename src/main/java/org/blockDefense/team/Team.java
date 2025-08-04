package org.blockDefense.team;

import cn.jason31416.planetlib.PlanetLib;
import cn.jason31416.planetlib.wrapper.SimpleLocation;
import cn.jason31416.planetlib.wrapper.SimplePlayer;
import cn.jason31416.planetlib.wrapper.SimpleWorld;
import org.blockDefense.tower.Tower;
import org.blockDefense.util.Config;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Team {
    public static Map<SimplePlayer, Team> playerTeamMap = new HashMap<>();
    public static @Nullable Team getPlayerTeam(SimplePlayer player){
        return playerTeamMap.getOrDefault(player, null);
    }
    public Set<SimplePlayer> members=new HashSet<>();
    public Tower core;
    public Set<Tower> towers = new HashSet<>();

    public void addPlayer(SimplePlayer player){
        playerTeamMap.put(player, this);
        members.add(player);
    }

    public void removePlayer(SimplePlayer player){
        playerTeamMap.remove(player);
        members.remove(player);
    }

    public void setup(){
        Random random = new Random();
        int x = random.nextInt(-Config.getInt("game.spawn-radius"), Config.getInt("game.spawn-radius")),
                z = random.nextInt(-Config.getInt("game.spawn-radius"), Config.getInt("game.spawn-radius"));
        World world = Bukkit.getWorld(Config.getString("game.world"));
        assert world!=null;
        PlanetLib.getScheduler().runAtLocation(SimpleLocation.of(x, 0, z, SimpleWorld.of(world)).getBukkitLocation(), () -> {
            SimpleLocation location = SimpleLocation.of(world.getHighestBlockAt(x, z));
            core = new Tower(location, Config.getString("game.core-type"), this);

        });
    }
}
