package ipsis.woot.event;

import ipsis.woot.school.SchoolManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ServerTickEventHandler {

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {

        if (event.phase == TickEvent.Phase.START)
            return;

        SchoolManager.tick();
    }
}
