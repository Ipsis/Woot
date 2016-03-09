package ipsis.woot.tileentity.multiblock;

import ipsis.woot.block.BlockMobFactory;
import ipsis.woot.block.BlockMobFactoryStructure;
import ipsis.woot.tileentity.TileEntityMobFactoryController;
import ipsis.woot.tileentity.TileEntityMobFactory;
import ipsis.woot.util.BlockPosHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

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
        String displayName;

        public FactorySetup() {

            blockPosList = new ArrayList<BlockPos>();
            size = null;
            mobName = "";
            displayName = "";
        }

        public EnumMobFactoryTier getSize() { return this.size; }
        public List<BlockPos> getBlockPosList() { return this.blockPosList; }
        public boolean isValid() { return this.size != null; }
        public String getMobName() { return this.mobName; }
        public String getDisplayName() { return this.displayName; }
    }

    /**
     * Validates a factory
     * @param factory - the main factory TE
     * @return null if invalid else the size of the factory
     */
    public static FactorySetup validateFactory(TileEntityMobFactory factory) {

        FactorySetup factorySetup;
        factorySetup = validateFactory(factory, EnumMobFactoryTier.TIER_THREE);
        if (factorySetup.size != null)
            return factorySetup;

        factorySetup = validateFactory(factory, EnumMobFactoryTier.TIER_TWO);
        if (factorySetup.size != null)
            return factorySetup;

        return validateFactory(factory, EnumMobFactoryTier.TIER_ONE);
    }

    static FactorySetup validateFactory(TileEntityMobFactory factory, EnumMobFactoryTier tier) {

        FactorySetup factorySetup = new FactorySetup();

        BlockPos controllerPos = factory.getPos().up(1);
        TileEntity te = factory.getWorld().getTileEntity(controllerPos);
        if (!(te instanceof TileEntityMobFactoryController)) {
            return factorySetup;
        }

        TileEntityMobFactoryController teController = (TileEntityMobFactoryController)te;
        if (teController.getMobName().equals(""))
            return factorySetup;
        
        factorySetup.mobName = teController.getMobName();
        factorySetup.displayName = teController.getDisplayName();

        BlockPos patternOrigin = factory.getPos();
        if (isSize(factory, tier))
            factorySetup.size = tier;
        else
            return factorySetup;

        EnumFacing f = factory.getWorld().getBlockState(factory.getPos()).getValue(BlockMobFactory.FACING);
        for (MobFactoryModule s : factorySetup.size.structureModules) {

            BlockPos p = BlockPosHelper.rotateFromSouth(s.getOffset(), f.getOpposite());
            p = patternOrigin.add(p);

            if (!factory.getWorld().isBlockLoaded(p)) {
                return new FactorySetup();
            }

            IBlockState iBlockState = factory.getWorld().getBlockState(p);
            Block block = iBlockState.getBlock();

            if (!(block instanceof BlockMobFactoryStructure)) {
                return new FactorySetup();
            }

            if (!(((BlockMobFactoryStructure)block).getModuleTypeFromState(iBlockState) == s.moduleType)) {
                return new FactorySetup();
            }

            factorySetup.blockPosList.add(p);
        }

        return factorySetup;
    }

    static boolean isSize(TileEntityMobFactory factory, EnumMobFactoryTier size) {

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

        EnumFacing f = factory.getWorld().getBlockState(factory.getPos()).getValue(BlockMobFactory.FACING);
        pos = BlockPosHelper.rotateFromSouth(pos, f.getOpposite());
        pos = factory.getPos().add(pos.getX(), pos.getY(), pos.getZ());

        IBlockState iBlockState = factory.getWorld().getBlockState(pos);
        Block b = iBlockState.getBlock();
        if (b instanceof BlockMobFactoryStructure)
            return ((BlockMobFactoryStructure)b).getModuleTypeFromState(iBlockState) == module;

        return false;
    }
}
