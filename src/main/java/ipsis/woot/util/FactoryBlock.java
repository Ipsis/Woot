package ipsis.woot.util;

import ipsis.woot.ModBlocks;
import ipsis.woot.blocks.BlockController;
import ipsis.woot.blocks.BlockHeart;
import ipsis.woot.blocks.BlockStructure;
import net.minecraft.block.Block;

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
    POWER("power", WootColor.YELLOW, null),
    IMPORT("import", WootColor.YELLOW, null),
    EXPORT("export", WootColor.YELLOW, null);

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
            if (curr.clazz != null && curr.clazz == b.getClass())
                return curr.clazz == BlockStructure.class ? ((BlockStructure)b).getFactoryBlockType() : curr;
        }
        return null;
    }
}
