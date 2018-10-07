package ipsis.woot.client;

import ipsis.Woot;
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
}
