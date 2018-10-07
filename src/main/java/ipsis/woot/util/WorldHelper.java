package ipsis.woot.util;

import net.minecraft.world.World;

public class WorldHelper {

    public static boolean isClientWorld(World world) {
        return world != null && world.isRemote;
    }

    public static boolean isServerWorld(World world) {
        return !isClientWorld(world);
    }
}
