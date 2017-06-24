package ipsis.woot.farmstructure;

import ipsis.woot.multiblock.EnumMobFactoryTier;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public interface IFarmScanner {

    @Nonnull ScannedFarmBase scanFarmStructure(World world, BlockPos origin, EnumFacing facing);
    @Nonnull ScannedFarmProxy scanFarmProxy(World world, BlockPos origin);
    @Nonnull ScannedFarmController scanFarmController(World world, BlockPos origin, EnumFacing facing);
    @Nonnull ScannedFarmUpgrade scanFarmUpgrades(World world, BlockPos origin, EnumFacing facing, EnumMobFactoryTier tier);
    void applyConfiguration(World world, @Nonnull ScannedFarmController farmController, @Nonnull ScannedFarmUpgrade farmUpgrade, EnumMobFactoryTier tier);
}
