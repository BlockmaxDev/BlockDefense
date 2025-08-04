package org.blockDefense;

import cn.jason31416.planetlib.PlanetLib;
import cn.jason31416.planetlib.Required;
import org.blockDefense.util.Config;
import org.blockDefense.util.Lang;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlockDefense extends JavaPlugin {
    public static BlockDefense instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        PlanetLib.initialize(this, Required.NBT, Required.VAULT);
        Config.start(this);
        Lang.init();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

//
