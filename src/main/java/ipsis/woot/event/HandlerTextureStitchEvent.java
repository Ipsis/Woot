package ipsis.woot.event;

import ipsis.woot.reference.Reference;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerTextureStitchEvent {

    public static TextureAtlasSprite factory;
    public static TextureAtlasSprite controller;
    public static TextureAtlasSprite block1;
    public static TextureAtlasSprite block2;
    public static TextureAtlasSprite block3;
    public static TextureAtlasSprite block4;
    public static TextureAtlasSprite block5;
    public static TextureAtlasSprite tiericap;
    public static TextureAtlasSprite tieriicap;
    public static TextureAtlasSprite tieriiicap;
    public static TextureAtlasSprite tierivcap;

    @SubscribeEvent
    public void onTextureStitchEvent(TextureStitchEvent.Pre event) {

        factory = forName(event.getMap(), "factory", "blocks");
        controller = forName(event.getMap(), "controller", "blocks");
        block1 = forName(event.getMap(), "structure_block_1", "blocks");
        block2 = forName(event.getMap(), "structure_block_2", "blocks");
        block3 = forName(event.getMap(), "structure_block_3", "blocks");
        block4 = forName(event.getMap(), "structure_block_4", "blocks");
        block5 = forName(event.getMap(), "structure_block_5", "blocks");
        tiericap = forName(event.getMap(), "structure_tier_i_cap", "blocks");
        tieriicap = forName(event.getMap(), "structure_tier_ii_cap", "blocks");
        tieriiicap = forName(event.getMap(), "structure_tier_iii_cap", "blocks");
        tierivcap = forName(event.getMap(), "structure_tier_iv_cap", "blocks");
    }

    private static TextureAtlasSprite forName(TextureMap textureMap, String name, String dir) {

        return textureMap.registerSprite(new ResourceLocation(Reference.MOD_NAME_LOWER + ":" + dir + "/" + name));
    }
}
