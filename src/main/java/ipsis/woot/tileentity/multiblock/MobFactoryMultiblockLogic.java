package ipsis.woot.tileentity.multiblock;

import ipsis.oss.LogHelper;
import ipsis.woot.block.BlockMobFactoryStructure;
import ipsis.woot.tileentity.TileEntityMobFarm;
import ipsis.woot.util.BlockPosHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class MobFactoryMultiblockLogic {

    static {
        EnumMobFactoryTier.TIER_ONE.buildStructureMap();
        EnumMobFactoryTier.TIER_TWO.buildStructureMap();
        EnumMobFactoryTier.TIER_THREE.buildStructureMap();
    }

    public static class FactorySetup {

        List<BlockPos> blockPosList;
        EnumMobFactoryTier size;
        String mobName;

        public FactorySetup() {

            blockPosList = new ArrayList<BlockPos>();
            size = null;
            // TODO need to scan for the mob block
            mobName = "Pig";
        }

        public EnumMobFactoryTier getSize() { return this.size; }
        public List<BlockPos> getBlockPosList() { return this.blockPosList; }
        public boolean isValid() { return this.size != null; }
        public String getMobName() { return this.mobName; }
    }

    /**
     * Validates a factory
     * @param factory - the main factory TE
     * @return null if invalid else the size of the factory
     */
    public static FactorySetup validateFactory(TileEntityMobFarm factory) {

        FactorySetup factorySetup;
        factorySetup = validateFactory(factory, EnumMobFactoryTier.TIER_THREE);
        if (factorySetup.size != null)
            return factorySetup;

        factorySetup = validateFactory(factory, EnumMobFactoryTier.TIER_TWO);
        if (factorySetup.size != null)
            return factorySetup;

        return validateFactory(factory, EnumMobFactoryTier.TIER_ONE);
    }

    static FactorySetup validateFactory(TileEntityMobFarm factory, EnumMobFactoryTier tier) {

        LogHelper.info("validateFactory:");

        FactorySetup factorySetup = new FactorySetup();
        BlockPos patternOrigin = factory.getPos().down(1);
        if (isSize(factory, tier))
            factorySetup.size = tier;
        else
            return factorySetup;

        LogHelper.info("validateFactory: might be " + factorySetup.size);

        for (MobFactoryModule s : factorySetup.size.structureModules) {

            BlockPos p = BlockPosHelper.rotateFromSouth(s.getOffset(), factory.getFacing().getOpposite());
            p = patternOrigin.add(p);

            if (!factory.getWorld().isBlockLoaded(p)) {
                LogHelper.info("validateFactory: unloaded " + p);
                return new FactorySetup();
            }

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

            LogHelper.info("validateFactory: matched " + p + " " + block);
            factorySetup.blockPosList.add(p);
        }

        LogHelper.info("validateFactory: factory is size " + factorySetup.size);
        return factorySetup;
    }

    static boolean isSize(TileEntityMobFarm factory, EnumMobFactoryTier size) {

        BlockPos pos;
        EnumMobFactoryModule module;
        if (size == EnumMobFactoryTier.TIER_ONE) {
            pos = new BlockPos(2, -1, -2);
            module = EnumMobFactoryModule.BLOCK_2;
        } else if (size == EnumMobFactoryTier.TIER_TWO) {
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
        LogHelper.info("isSize: " + size + " checking " + pos + " " + b);
        if (b instanceof BlockMobFactoryStructure)
            return ((BlockMobFactoryStructure)b).getModuleTypeFromState(iBlockState) == module;

        return false;
    }
}
