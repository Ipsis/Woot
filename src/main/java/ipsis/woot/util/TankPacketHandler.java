package ipsis.woot.util;

import ipsis.woot.fluilds.network.TankPacket;

public interface TankPacketHandler {
    void handlePacket(TankPacket packet);
}
