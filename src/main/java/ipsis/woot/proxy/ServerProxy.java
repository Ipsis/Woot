package ipsis.woot.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ServerProxy implements IProxy {

    /**
     * IProxy
     */
    @Override
    public void setup(FMLCommonSetupEvent event) {
    }

    @Override
    public EntityPlayer getClientPlayer() {
        throw new IllegalStateException("getClientPlayer called server-side!");
    }

    @Override
    public World getClientWorld() {
        throw new IllegalStateException("getClientWorld called server-side!");
    }
}
