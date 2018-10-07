package ipsis.woot.event;

import ipsis.woot.client.WootTextures;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TextureStitchEventHandler {

    @SubscribeEvent
    public void onTextureStitchPreEvent(TextureStitchEvent.Pre event) {

        WootTextures.registerTextures(event.getMap());
    }
}
