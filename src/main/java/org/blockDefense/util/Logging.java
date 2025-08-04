package org.blockDefense.util;

import org.blockDefense.BlockDefense;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Logging {
    public static void info(Object... objs){
        BlockDefense.instance.getLogger().info(Arrays.stream(objs).map(Object::toString).collect(Collectors.joining(" ")));
    }
}
