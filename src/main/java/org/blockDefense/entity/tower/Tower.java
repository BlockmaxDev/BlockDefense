package org.blockDefense.entity.tower;

import cn.jason31416.planetlib.wrapper.SimpleLocation;
import cn.jason31416.planetlib.wrapper.SimplePlayer;
import org.bukkit.util.Vector;

public abstract class Tower {
    public SimpleLocation location;
    public Vector size;

    public void place(){
        BukkitWorld weWorld = new BukkitWorld(location.world().getBukkitWorld());
        Clipboard clipboard;
        File file = new File("pencil.schem");

        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            clipboard = reader.read();

            try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(clipboard.getOrigin())
                        // configure here
                        .build();
                Operations.complete(operation);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public abstract void processInteraction(SimplePlayer player);
}
