package org.blockDefense.util;

import org.blockDefense.BlockDefense;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Logging {
    public static void info(Object... objs){
        BlockDefense.instance.getLogger().info(Arrays.stream(objs).map(Object::toString).collect(Collectors.joining(" ")));
    }
    public static void error(Object... objs){
        BlockDefense.instance.getLogger().severe(Arrays.stream(objs).map(Object::toString).collect(Collectors.joining(" ")));
    }
    public static void warning(Object... objs){
        BlockDefense.instance.getLogger().warning(Arrays.stream(objs).map(Object::toString).collect(Collectors.joining(" ")));
    }
}
