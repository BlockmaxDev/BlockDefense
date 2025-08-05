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
import java.util.concurrent.CompletableFuture;

public class Game {
    public static Game ongoingGame = null;

    public static Map<SimplePlayer, Game> playerGameMap = new HashMap<>();
    public static @Nullable Game getPlayerGame(SimplePlayer player){
        return playerGameMap.getOrDefault(player, null);
    }
    public Set<SimplePlayer> members=new HashSet<>();
    public Tower core;
    public Set<Tower> towers = new HashSet<>();

    public void addPlayer(SimplePlayer player){
        playerGameMap.put(player, this);
        members.add(player);
        player.teleport(core.location);
    }

    public void removePlayer(SimplePlayer player){
        playerGameMap.remove(player);
        members.remove(player);
    }

    public CompletableFuture<Boolean> setup(){
//        Random random = new Random();
//        int x = random.nextInt(-Config.getInt("game.spawn-radius"), Config.getInt("game.spawn-radius")),
//                z = random.nextInt(-Config.getInt("game.spawn-radius"), Config.getInt("game.spawn-radius"));
        int x=0, z=0;
        World world = Bukkit.getWorld(Config.getString("game.world"));
        assert world!=null;
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        SimpleLocation location = SimpleLocation.of(world.getHighestBlockAt(x, z)).getRelative(0, 1, 0);
        core = new Tower(location, Config.getString("game.core-type"), this);
        core.place();
        future.complete(true);
        return future;
    }
}
