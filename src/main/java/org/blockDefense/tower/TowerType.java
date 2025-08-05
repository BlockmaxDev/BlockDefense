package org.blockDefense.tower;

import cn.jason31416.planetlib.wrapper.SimpleLocation;
import cn.jason31416.planetlib.wrapper.SimplePlayer;
import org.blockDefense.tower.type.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

public abstract class TowerType {
    public static Map<String, Class<? extends TowerType> > towerTypes = new HashMap<>();

    public static void registerTowerType(String name, Class<? extends TowerType> towerType) {
        towerTypes.put(name, towerType);
    }

    public Tower tower;

    public abstract void onBlockInteract(SimplePlayer player, SimpleLocation location, Action action);
    public abstract void onBlockBreak(SimplePlayer player, SimpleLocation location);

    public boolean interactable(){
        return false;
    }

    public void onTowerInteract(){} // called when tower is interacted

    public int updateInterval(){
        return 0; // 0 means no update
    }
    public void onUpdate(){} // called every updateInterval() ticks

    public static void init() {
        registerTowerType("core", Core.class);
    }
}
