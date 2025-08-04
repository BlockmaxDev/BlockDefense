package org.blockDefense.tower;

import cn.jason31416.planetlib.wrapper.SimpleLocation;
import cn.jason31416.planetlib.wrapper.SimplePlayer;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.blockDefense.BlockDefense;
import org.blockDefense.team.Team;
import org.blockDefense.util.Config;
import org.blockDefense.util.Logging;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.FileInputStream;

public class Tower {
    public SimpleLocation location;
    public Vector size;
    public String type;
    public Team team;
    public double hp=0;
    public TowerType towerType;

    public Tower(SimpleLocation location, String type, Team team){
        this.location = location;
        this.type = type;
        this.team = team;
    }

    public boolean place(){
        try {
            BukkitWorld weWorld = new BukkitWorld(location.world().getBukkitWorld());
            Clipboard clipboard;

            File file = new File(BlockDefense.instance.getDataFolder(), "schem/"+ Config.getString("tower."+type+".schematic"));

            ClipboardFormat format = ClipboardFormats.findByFile(file);
            try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
                clipboard = reader.read();

                try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
                    Operation operation = new ClipboardHolder(clipboard)
                            .createPaste(editSession)
                            .to(BukkitAdapter.asBlockVector(location.getBlockLocation().getBukkitLocation()))
                            .build();
                    Operations.complete(operation);
                }

                size = new Vector(clipboard.getDimensions().x(), clipboard.getDimensions().y(), clipboard.getDimensions().z());
            } catch (Exception e) {
                Logging.error("An error has occurred during worldedit pasting: "+ e.getMessage());
                e.printStackTrace();
                return false;
            }
            towerType = TowerType.towerTypes.get(Config.getString("tower."+type+".type"))
                    .getConstructor()
                    .newInstance();
            towerType.tower = this;
            hp = Config.getDouble("tower."+type+".hp");
            team.towers.add(this);
            return true;
        } catch (Exception e) {
            Logging.error("An error has occurred during structure placing: "+ e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
