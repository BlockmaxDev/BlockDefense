package org.blockDefense.animation;

import cn.jason31416.planetlib.wrapper.SimpleLocation;
import org.blockDefense.tower.Tower;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;

public class BlockDisplayAnimation extends Animation {
    public Material blockType;
    public float size;
    public BlockDisplay display;
    public BlockDisplayAnimation(Tower tower, SimpleLocation relativeLocation, Material blockType, float size) {
        super(tower, relativeLocation);
        this.blockType = blockType;
        this.size = size;
    }
    public void render() {
        display = (BlockDisplay) spawnEntity(getLocation(), EntityType.BLOCK_DISPLAY);
        display.setBlock(blockType.createBlockData());
        display.setDisplayWidth(size);
        display.setDisplayHeight(size);
    }
}
