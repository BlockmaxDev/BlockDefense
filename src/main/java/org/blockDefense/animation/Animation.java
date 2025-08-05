package org.blockDefense.animation;

import cn.jason31416.planetlib.hook.NbtHook;
import cn.jason31416.planetlib.wrapper.SimpleLocation;
import org.blockDefense.tower.Tower;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public abstract class Animation {
    public Tower tower;
    public SimpleLocation relativeLocation;
    private List<Entity> entities = new ArrayList<>();

    public Animation(Tower tower, SimpleLocation relativeLocation) {
        this.tower = tower;
        this.relativeLocation = relativeLocation;
    }

    public SimpleLocation getLocation(){
        return tower.location.getRelative(relativeLocation.x(), relativeLocation.y(), relativeLocation.z());
    }

    protected Entity spawnEntity(SimpleLocation location, EntityType entityType){
        Entity entity = location.getBukkitLocation().getWorld().spawnEntity(location.getBukkitLocation(), entityType);
        NbtHook.addTag(entity, "bd.animation");
        entities.add(entity);
        return entity;
    }

    public void destroy(){
        remove();
        for(Entity entity : entities){
            entity.remove();
        }
    }

    public void render(){}

    public void update(){}

    public void remove(){}
}
