package org.blockDefense.util;

import cn.jason31416.planetlib.PlanetLib;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.util.Transformation;
import org.joml.*;

public final class BlockDisplayUtil {

    /**
     * 旋转 BlockDisplay 实体
     * @param display 目标 BlockDisplay
     * @param rotationX X轴旋转角度（单位：度）
     * @param rotationZ Z轴旋转角度（单位：度）
     */
    public static void rotateBlockDisplay(BlockDisplay display, float rotationX, float rotationZ) {
        Transformation oldTransformation = display.getTransformation();

        // 创建新的旋转四元数
        Quaternionf rotation = new Quaternionf()
                .rotateX((float) org.joml.Math.toRadians(rotationX))
                .rotateZ((float) org.joml.Math.toRadians(rotationZ));

        // 创建新的 Transformation
        Transformation newTransformation = new Transformation(
                oldTransformation.getTranslation(),  // 保持原位移
                rotation,                          // 新旋转
                oldTransformation.getScale(),       // 保持原缩放
                oldTransformation.getRightRotation() // 保持原右旋转
        );

        display.setTransformation(newTransformation);
    }

    /**
     * 缩放 BlockDisplay 实体
     * @param display 目标 BlockDisplay
     * @param scaleX X轴缩放比例
     * @param scaleY Y轴缩放比例
     * @param scaleZ Z轴缩放比例
     */
    public static void scaleBlockDisplay(BlockDisplay display, float scaleX, float scaleY, float scaleZ) {
        Transformation oldTransformation = display.getTransformation();

        // 创建新的 Transformation
        Transformation newTransformation = new Transformation(
                oldTransformation.getTranslation(),  // 保持原位移
                oldTransformation.getLeftRotation(), // 保持原旋转
                new Vector3f(scaleX, scaleY, scaleZ), // 新缩放
                oldTransformation.getRightRotation() // 保持原右旋转
        );

        display.setTransformation(newTransformation);
    }

    /**
     * 生成基础 BlockDisplay
     * @param location 生成位置
     * @param material 方块类型（Material枚举）
     * @param glowing 是否发光
     */
    public static BlockDisplay spawnDisplay(Location location, Material material, boolean glowing) {
        World world = location.getWorld();
        if (world == null) throw new IllegalArgumentException("World cannot be null!");

        BlockDisplay display = world.spawn(location, BlockDisplay.class);
        display.setBlock(Bukkit.createBlockData(material));
        display.setGlowing(glowing);
        return display;
    }

    /**
     * 为已有的 BlockDisplay 生成关联的 Interaction（交互实体）
     * @param display 目标 BlockDisplay
     * @return 生成的 Interaction 实体
     */
    public static Interaction addInteractionToDisplay(BlockDisplay display) {
        // 1. 在 BlockDisplay 的位置生成 Interaction
        Interaction interaction = display.getWorld().spawn(display.getLocation(), Interaction.class);

        // 2. 设置交互参数
        interaction.setInteractionWidth(1.0f);    // 交互区域宽度
        interaction.setInteractionHeight(1.0f);   // 交互区域高度
        interaction.setResponsive(true);          // 允许交互

        // 3. 绑定 Interaction 到 BlockDisplay（同步位置）
        PlanetLib.getScheduler().runTimer(  // 替换为你的插件实例
                task -> {
                    if (display.isDead() || interaction.isDead()) {
                        task.cancel();  // 如果任一实体被移除，停止同步
                        return;
                    }
                    interaction.teleport(display.getLocation());  // 实时跟随 BlockDisplay
                }, 0, 1
        );

        return interaction;
    }
}