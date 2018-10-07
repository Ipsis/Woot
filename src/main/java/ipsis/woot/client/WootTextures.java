package ipsis.woot.client;

import ipsis.Woot;
import ipsis.woot.ModBlocks;
import ipsis.woot.blocks.BlockController;
import ipsis.woot.blocks.BlockHeart;
import ipsis.woot.util.FactoryBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

/**
 * Based off CoFH TETextures.java
 */

public class WootTextures {

    private static TextureMap textureMap;

    public static void registerTextures(TextureMap map) {

        textureMap = map;

        BASE_BONE = register(BLOCKS + FactoryBlock.BONE.getName());
        BASE_FLESH = register(BLOCKS + FactoryBlock.FLESH.getName());
        BASE_BLAZE = register(BLOCKS + FactoryBlock.BLAZE.getName());
        BASE_ENDER = register(BLOCKS + FactoryBlock.ENDER.getName());
        BASE_NETHER = register(BLOCKS + FactoryBlock.NETHER.getName());
        BASE_REDSTONE = register(BLOCKS + FactoryBlock.REDSTONE.getName());
        BASE_UPGRADE = register(BLOCKS + FactoryBlock.UPGRADE.getName());
        CAP_1 = register(BLOCKS + FactoryBlock.CAP_1.getName());
        CAP_2 = register(BLOCKS + FactoryBlock.CAP_2.getName());
        CAP_3 = register(BLOCKS + FactoryBlock.CAP_3.getName());
        CAP_4 = register(BLOCKS + FactoryBlock.CAP_4.getName());
        CONTROLLER = register(BLOCKS + BlockController.getBasename());
        HEART = register(BLOCKS + BlockHeart.getBasename());
    }

    private static TextureAtlasSprite register(String sprite) {
        return textureMap.registerSprite(new ResourceLocation(sprite));
    }

    private static final String BLOCKS = Woot.MODID + ":blocks/";

    public static TextureAtlasSprite BASE_BONE;
    public static TextureAtlasSprite BASE_FLESH;
    public static TextureAtlasSprite BASE_BLAZE;
    public static TextureAtlasSprite BASE_ENDER;
    public static TextureAtlasSprite BASE_NETHER;
    public static TextureAtlasSprite BASE_REDSTONE;
    public static TextureAtlasSprite BASE_UPGRADE;
    public static TextureAtlasSprite CAP_1;
    public static TextureAtlasSprite CAP_2;
    public static TextureAtlasSprite CAP_3;
    public static TextureAtlasSprite CAP_4;
    public static TextureAtlasSprite POWER;
    public static TextureAtlasSprite IMPORTER;
    public static TextureAtlasSprite EXPORTER;
    public static TextureAtlasSprite CONTROLLER;
    public static TextureAtlasSprite HEART;
}
