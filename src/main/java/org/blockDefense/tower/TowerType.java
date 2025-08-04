package org.blockDefense.tower;

import cn.jason31416.planetlib.wrapper.SimpleLocation;
import cn.jason31416.planetlib.wrapper.SimplePlayer;
import org.blockDefense.tower.type.*;

import java.util.HashMap;
import java.util.Map;

public abstract class TowerType {
    public static Map<String, Class<? extends TowerType> > towerTypes = new HashMap<>();

    public static void registerTowerType(String name, Class<? extends TowerType> towerType) {
        towerTypes.put(name, towerType);
    }

    public Tower tower;

    public abstract void onInteract(SimplePlayer player);
    public abstract void onUpdate();
    public abstract void onBlockBreak(SimplePlayer player, SimpleLocation location);

    public static void init() {
        registerTowerType("core", Core.class);
    }
}
