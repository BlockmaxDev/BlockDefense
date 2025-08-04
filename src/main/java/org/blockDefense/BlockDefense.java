package org.blockDefense;

import cn.jason31416.planetlib.PlanetLib;
import cn.jason31416.planetlib.Required;
import org.blockDefense.util.Config;
import org.blockDefense.util.Lang;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.stream.Stream;

public final class BlockDefense extends JavaPlugin {
    public static BlockDefense instance;

    public void savePluginResource(@NotNull String resourcePath) {
        if (!resourcePath.isEmpty()) {
            resourcePath = resourcePath.replace('\\', '/');
            InputStream in = this.getResource(resourcePath);
            if (in == null) {
                throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + getFile());
            } else {
                File outFile = new File(getDataFolder(), resourcePath);
                int lastIndex = resourcePath.lastIndexOf(47);
                File outDir = new File(getDataFolder(), resourcePath.substring(0, Math.max(lastIndex, 0)));
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }
                try {
                    if (!outFile.exists()) {
                        OutputStream out = Files.newOutputStream(outFile.toPath());
                        byte[] buf = new byte[1024];

                        int len;
                        while((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        out.close();
                        in.close();
                    }
                } catch (IOException var10) {
                    getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, var10);
                }
            }
        } else {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
    }
    private void saveFolder(String name) throws URISyntaxException, IOException {
        if(new File(getDataFolder(), name).isDirectory()) return;
        URI uri = getClassLoader().getResource(name).toURI();
        try(FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
            try(Stream<Path> walk = Files.walk(fileSystem.getPath(name), 1)) {
                for (Iterator<Path> it = walk.iterator(); it.hasNext(); ) {
                    Path i = it.next();
                    if(!i.toString().equals(name)) savePluginResource(i.toString());
                }
            }
        }
    }
    public void saveAllResources() {
        savePluginResource("lang.yml");
        try {
            saveFolder("schem");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        PlanetLib.initialize(this, Required.NBT, Required.VAULT);
        Config.start(this);
        saveAllResources();
        Lang.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        PlanetLib.disable();
    }
}

//
