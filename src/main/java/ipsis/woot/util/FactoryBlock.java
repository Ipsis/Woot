package ipsis.woot.util;

import ipsis.woot.ModBlocks;
import ipsis.woot.blocks.*;
import ipsis.woot.generators.BlockGenerator;
import ipsis.woot.heart.BlockHeart;
import ipsis.woot.power.BlockPower;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public enum FactoryBlock {

    BONE("bone_structure", WootColor.WHITE, BlockStructure.class),
    FLESH("flesh_structure", WootColor.BROWN, BlockStructure.class),
    BLAZE("blaze_structure", WootColor.ORANGE, BlockStructure.class),
    ENDER("ender_structure", WootColor.GREEN, BlockStructure.class),
    NETHER("nether_structure", WootColor.LIGHTGRAY, BlockStructure.class),
    REDSTONE("redstone_structure", WootColor.RED, BlockStructure.class),
    UPGRADE("upgrade_structure", WootColor.PURPLE, BlockStructure.class),
    CAP_1("cap1", WootColor.CYAN, BlockStructure.class),
    CAP_2("cap2", WootColor.CYAN, BlockStructure.class),
    CAP_3("cap3", WootColor.CYAN, BlockStructure.class),
    CAP_4("cap4", WootColor.CYAN, BlockStructure.class),
    TOTEM("totem", WootColor.LIME, null),
    CONTROLLER("controller", WootColor.YELLOW, BlockController.class),
    HEART("heart", WootColor.PINK, BlockHeart.class),
    POWER_1("power1", WootColor.YELLOW, BlockPower.class),
    POWER_2("power2", WootColor.YELLOW, BlockPower.class),
    POWER_3("power3", WootColor.YELLOW, BlockPower.class),
    IMPORT("import", WootColor.YELLOW, BlockImport.class),
    EXPORT("export", WootColor.YELLOW, BlockExport.class),
    GENERATOR("generator", WootColor.YELLOW, BlockGenerator.class);

    private String name;
    private Class clazz;
    FactoryBlock(String name, WootColor color, Class clazz) {
        this.name = name;
        this.color = color;
        this.clazz = clazz;
    }
    public String getName() {
        return this.name;
    }

    private WootColor color;
    public WootColor getColor() { return this.color; }

    public static @Nullable FactoryBlock getFactoryBlock(Block b) {

        for (FactoryBlock curr : FactoryBlock.values()) {
            if (curr.clazz != null && curr.clazz == b.getClass()) {
                if (curr.clazz == BlockStructure.class)
                    return ((BlockStructure)b).getFactoryBlockType();
                else if (curr.clazz == BlockPower.class)
                    return ((BlockPower)b).getFactoryBlockType();
                else
                    return curr;
            }
        }
        return null;
    }

    public static boolean isFactoryBlock(ItemStack itemStack) {

        for (FactoryBlock curr : FactoryBlock.values()) {
            ItemStack factoryStack = ModBlocks.getItemStackForFactoryBlock(curr);
            if (factoryStack.getItem() == itemStack.getItem())
                return true;
        }

        return false;
    }

    public static boolean isSameFactoryBlock(FactoryBlock factoryBlock, ItemStack itemStack) {

        ItemStack factoryStack = ModBlocks.getItemStackForFactoryBlock(factoryBlock);
        return factoryStack.getItem() == itemStack.getItem();
    }

    public static boolean isPowerFactoryBlock(FactoryBlock factoryBlock) {

        return factoryBlock == GENERATOR;
    }
}
