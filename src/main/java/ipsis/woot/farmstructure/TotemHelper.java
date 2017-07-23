package ipsis.woot.farmstructure;

import ipsis.woot.farmblocks.IFarmBlockUpgrade;
import ipsis.woot.util.EnumSpawnerUpgrade;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TotemHelper {

    public static EnumSpawnerUpgrade getUpgrade(World world, BlockPos origin, int tier) {

        BlockPos blockPos = origin.up(tier - 1);
        TileEntity te = world.getTileEntity(blockPos);
        if (te instanceof IFarmBlockUpgrade)
            return ((IFarmBlockUpgrade) te).getUpgrade();

        return null;
    }

    public static int getTier(World world, BlockPos origin, int tier) {

        BlockPos blockPos = origin.up(tier - 1);
        TileEntity te = world.getTileEntity(blockPos);
        if (te instanceof IFarmBlockUpgrade)
            return ((IFarmBlockUpgrade) te).getUpgrade().getTier();

        return -1;
    }
}
