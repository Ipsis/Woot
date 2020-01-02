package ipsis.woot.util;

import ipsis.woot.fluilds.network.FluidStackPacket;

public interface FluidStackPacketHandler {

    void handlePacket(FluidStackPacket packet);
}
