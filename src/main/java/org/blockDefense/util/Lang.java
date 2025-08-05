package org.blockDefense.util;

import cn.jason31416.planetlib.message.Message;
import cn.jason31416.planetlib.message.MessageLoader;
import org.blockDefense.BlockDefense;

import java.io.File;

public class Lang {
    public static MessageLoader loader;
    public static void init(){
        loader = new MessageLoader(new File(BlockDefense.instance.getDataFolder(), "lang.yml"));
    }
    public static Message getMessage(String key){
        return loader.getMessage(key, key);
    }
}
