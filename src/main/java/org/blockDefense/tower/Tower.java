package org.blockDefense.tower;

import cn.jason31416.planetlib.wrapper.SimpleLocation;
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
import com.sk89q.worldedit.function.pattern.BlockPattern;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.blockDefense.BlockDefense;
import org.blockDefense.animation.Animation;
import org.blockDefense.team.Game;
import org.blockDefense.util.Config;
import org.blockDefense.util.Logging;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Set;
import java.util.function.Supplier;

public class Tower {
    public SimpleLocation location;
    public CuboidRegion region;
    public String type;
    public Game game;
    public double hp=0;
    public TowerType towerType;
    public Set<Animation> animations;

    public Tower(SimpleLocation location, String type, Game game){
        this.location = location;
        this.type = type;
        this.game = game;
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

                region = clipboard.getRegion().getBoundingBox();
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
            game.towers.add(this);
            return true;
        } catch (Exception e) {
            Logging.error("An error has occurred during structure placing: "+ e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean destroy(){
        try{
            BukkitWorld weWorld = new BukkitWorld(location.world().getBukkitWorld());
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
                editSession.setBlocks(region, new BlockPattern(BukkitAdapter.adapt(Material.AIR.createBlockData())));
            }catch (Exception e){
                Logging.error("An error has occurred during structure breaking: "+ e.getMessage());
                e.printStackTrace();
            }
            for(Animation animation: animations){
                animation.destroy();
            }
            game.towers.remove(this);
        }catch(Exception e){
            Logging.error("An error has occurred during structure breaking: "+ e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
