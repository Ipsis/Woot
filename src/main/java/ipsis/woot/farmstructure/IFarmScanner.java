package ipsis.woot.farmstructure;

import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.WootMob;
import ipsis.woot.util.WootMobName;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public interface IFarmScanner {

    @Nonnull ScannedFarmBase scanFarmStructure(World world, BlockPos origin, EnumFacing facing);
    @Nonnull ScannedFarmRemote scanFarmRemote(World world, BlockPos origin);
    @Nonnull ScannedFarmController scanFarmController(World world, BlockPos origin, EnumFacing facing);
    @Nonnull ScannedFarmUpgrade scanFarmUpgrades(World world, BlockPos origin, EnumFacing facing, EnumMobFactoryTier tier);
    void applyConfiguration(World world, @Nonnull ScannedFarmController farmController, @Nonnull ScannedFarmUpgrade farmUpgrade, EnumMobFactoryTier tier);
    void scanFarmNoStop(World world, BlockPos origin, EnumFacing facing, EnumMobFactoryTier tier, BadFarmInfo badFarmInfo);

    class BadFarmInfo {

        public List<BadFarmBlock> badFarmBlocks = new ArrayList<>();
        public boolean hasCell;
        public boolean hasImporter;
        public boolean hasExporter;
    }

    class BadFarmBlock {
        BlockPos pos;
        Block correctBlock;
        int correctBlockMeta;

        public BadFarmBlock(BlockPos pos, Block block, int meta) {

            this.pos = pos;
            this.correctBlock = block;
            this.correctBlockMeta = meta;
        }

        public BlockPos getPos() {
            return pos;
        }

        public Block getCorrectBlock() {
            return correctBlock;
        }

        public int getCorrectBlockMeta() {
            return correctBlockMeta;
        }
    }
}
