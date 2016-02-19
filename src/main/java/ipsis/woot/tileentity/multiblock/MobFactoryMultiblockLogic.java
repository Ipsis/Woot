package ipsis.woot.tileentity.multiblock;

import ipsis.oss.LogHelper;
import ipsis.woot.block.BlockMobFactoryStructure;
import ipsis.woot.tileentity.TileEntityMobFactory;
import ipsis.woot.tileentity.TileEntityMobFarm;
import ipsis.woot.util.BlockPosHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class MobFactoryMultiblockLogic {

    static {
        EnumMobFactorySize.SIZE_ONE.buildStructureMap();
        EnumMobFactorySize.SIZE_TWO.buildStructureMap();
        EnumMobFactorySize.SIZE_THREE.buildStructureMap();
    }

    public static class FactorySetup {

        List<BlockPos> blockPosList;
        EnumMobFactorySize size;

        public FactorySetup() {

            blockPosList = new ArrayList<BlockPos>();
            size = null;
        }

        public EnumMobFactorySize getSize() { return this.size; }
        public List<BlockPos> getBlockPosList() { return this.blockPosList; }
        public boolean isValid() { return this.size != null; }
    }

    /**
     * Validates a factory
     * @param factory - the main factory TE
     * @return null if invalid else the size of the factory
     */
    public static FactorySetup validateFactory(TileEntityMobFarm factory) {

        FactorySetup factorySetup = new FactorySetup();
        BlockPos patternOrigin = factory.getPos().down(1);
        if (isSize(factory, EnumMobFactorySize.SIZE_THREE))
            factorySetup.size = EnumMobFactorySize.SIZE_THREE;
        else if (isSize(factory, EnumMobFactorySize.SIZE_TWO))
            factorySetup.size = EnumMobFactorySize.SIZE_TWO;
        else if (isSize(factory, EnumMobFactorySize.SIZE_ONE))
            factorySetup.size = EnumMobFactorySize.SIZE_ONE;
        else
            return factorySetup;

        LogHelper.info("validateFactory: " + factorySetup.size);

        for (MobFactoryModule s : factorySetup.size.structureModules) {

            BlockPos p = BlockPosHelper.rotateFromSouth(s.getOffset(), factory.getFacing().getOpposite());
            p = patternOrigin.add(p);

            if (!factory.getWorld().isBlockLoaded(p))
                return new FactorySetup();

            IBlockState iBlockState = factory.getWorld().getBlockState(p);
            Block block = iBlockState.getBlock();

            if (!(block instanceof BlockMobFactoryStructure)) {
                LogHelper.info("validateFactory: invalid " + p + " " + block);
                return new FactorySetup();
            }

            if (!(((BlockMobFactoryStructure)block).getModuleTypeFromState(iBlockState) == s.moduleType)) {
                LogHelper.info("validateFactory: did not find " + p + " " + s.moduleType);
                return new FactorySetup();
            }

            factorySetup.blockPosList.add(p);
        }

        return factorySetup;
    }

    static boolean isSize(TileEntityMobFarm factory, EnumMobFactorySize size) {

        BlockPos pos;
        EnumMobFactoryModule module;
        if (size == EnumMobFactorySize.SIZE_ONE) {
            pos = new BlockPos(2, -1, -2);
            module = EnumMobFactoryModule.BLOCK_2;
        } else if (size == EnumMobFactorySize.SIZE_TWO) {
            pos = new BlockPos(3, -1, -3);
            module = EnumMobFactoryModule.BLOCK_3;
        } else {
            pos = new BlockPos(4, -1, -4);
            module = EnumMobFactoryModule.BLOCK_4;
        }

        pos = BlockPosHelper.rotateFromSouth(pos, factory.getFacing().getOpposite());
        pos = factory.getPos().add(pos.getX(), pos.getY(), pos.getZ());

        IBlockState iBlockState = factory.getWorld().getBlockState(pos);
        Block b = iBlockState.getBlock();
        if (b instanceof BlockMobFactoryStructure)
            return ((BlockMobFactoryStructure)b).getModuleTypeFromState(iBlockState) == module;

        return false;
    }
}
