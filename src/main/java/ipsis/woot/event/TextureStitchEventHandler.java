package ipsis.woot.event;

import ipsis.woot.client.WootTextures;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TextureStitchEventHandler {

    @SubscribeEvent
    public void onTextureStitchPreEvent(TextureStitchEvent.Pre event) {
        WootTextures.register(event.getMap());

    }
}
