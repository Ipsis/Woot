package ipsis.woot.client;

import ipsis.woot.Woot;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;

/**
 * Based off CoFH TeTextures.java
 */
public class WootTextures {

    private static TextureMap textureMap;

    public static void register(TextureMap map) {
        /**
         * TODO I have no idea
        textureMap = map;
        BONE = register(BLOCKS + FactoryBlock.BONE.getName());
        FLESH = register(BLOCKS + FactoryBlock.FLESH.getName());
        BLAZE = register(BLOCKS + FactoryBlock.BLAZE.getName());
        ENDER = register(BLOCKS + FactoryBlock.ENDER.getName());
        NETHER = register(BLOCKS + FactoryBlock.NETHER.getName());
        REDSTONE = register(BLOCKS + FactoryBlock.REDSTONE.getName());
        UPGRADE = register(BLOCKS + FactoryBlock.UPGRADE.getName());
        CAP_1 = register(BLOCKS + FactoryBlock.CAP_1.getName());
        CAP_2 = register(BLOCKS + FactoryBlock.CAP_2.getName());
        CAP_3 = register(BLOCKS + FactoryBlock.CAP_3.getName());
        CAP_4 = register(BLOCKS + FactoryBlock.CAP_4.getName());
        CONTROLLER = register(BLOCKS + FactoryBlock.CONTROLLER.getName());
        HEART = register(BLOCKS + FactoryBlock.HEART.getName());
        CELL_1 = register(BLOCKS + FactoryBlock.CELL_1.getName());
        CELL_2 = register(BLOCKS + FactoryBlock.CELL_2.getName());
        CELL_3 = register(BLOCKS + FactoryBlock.CELL_3.getName());
        IMPORT = register(BLOCKS + FactoryBlock.IMPORT.getName());
        EXPORT = register(BLOCKS + FactoryBlock.EXPORT.getName());
         */
    }

    private static final String BLOCKS = Woot.MODID + ":blocks/";
    public static TextureAtlasSprite BONE;
    public static TextureAtlasSprite FLESH;
    public static TextureAtlasSprite BLAZE;
    public static TextureAtlasSprite ENDER;
    public static TextureAtlasSprite NETHER;
    public static TextureAtlasSprite REDSTONE;
    public static TextureAtlasSprite UPGRADE;
    public static TextureAtlasSprite CAP_1;
    public static TextureAtlasSprite CAP_2;
    public static TextureAtlasSprite CAP_3;
    public static TextureAtlasSprite CAP_4;
    public static TextureAtlasSprite CONTROLLER;
    public static TextureAtlasSprite HEART;
    public static TextureAtlasSprite CELL_1;
    public static TextureAtlasSprite CELL_2;
    public static TextureAtlasSprite CELL_3;
    public static TextureAtlasSprite IMPORT;
    public static TextureAtlasSprite EXPORT;
}
