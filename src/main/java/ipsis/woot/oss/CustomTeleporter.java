package ipsis.woot.oss;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * McJtys teleport code from his tutorial series
 */
public class CustomTeleporter extends Teleporter {

    private final WorldServer worldServer;
    private double x;
    private double y;
    private double z;

    public CustomTeleporter(WorldServer world, double x, double y, double z) {

        super(world);
        this.worldServer = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {

        this.worldServer.getBlockState(new BlockPos(this.x, this.y, this.z));
        entityIn.setPosition(this.x, this.y, this.z);
        entityIn.motionX = 0.0f;
        entityIn.motionY = 0.0f;
        entityIn.motionZ = 0.0f;
    }

    public static void teleportToDimension(EntityPlayer entityPlayer, int dimension, double x, double y, double z) {

        int oldDimension = entityPlayer.getEntityWorld().provider.getDimension();
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP)entityPlayer;
        MinecraftServer minecraftServer = entityPlayer.getEntityWorld().getMinecraftServer();
        WorldServer worldServer = minecraftServer.getWorld(dimension);
        entityPlayer.addExperienceLevel(0);

        worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new CustomTeleporter(worldServer, x, y, z));
        entityPlayer.setPositionAndUpdate(x, y, z);

        if (oldDimension == -1) {
            // For some reason teleporting out of the end does weird things
            entityPlayer.setPositionAndUpdate(x, y, z);
            worldServer.spawnEntity(entityPlayer);
            worldServer.updateEntityWithOptionalForce(entityPlayer, false);
        }

    }
}
