package org.blockDefense.animation;

import cn.jason31416.planetlib.wrapper.SimpleLocation;
import org.blockDefense.tower.Tower;

public class HologramAnimation extends Animation {
    public String text;
    public HologramAnimation(Tower tower, SimpleLocation relativeLocation, String text) {
        super(tower, relativeLocation);
        this.text = text;
    }

    @Override
    public void render() {
        spawnEntity(location, text);
    }
}
