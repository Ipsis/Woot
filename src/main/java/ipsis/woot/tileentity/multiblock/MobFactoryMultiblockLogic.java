package ipsis.woot.tileentity.multiblock;

import ipsis.Woot;
import ipsis.woot.block.BlockMobFactory;
import ipsis.woot.block.BlockMobFactoryStructure;
import ipsis.woot.reference.Lang;
import ipsis.woot.tileentity.LayoutBlockInfo;
import ipsis.woot.tileentity.TileEntityMobFactoryController;
import ipsis.woot.util.BlockPosHelper;
import ipsis.woot.util.StringHelper;
import ipsis.woot.util.UnlocalizedName;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class MobFactoryMultiblockLogic {

    static {
        EnumMobFactoryTier.TIER_ONE.buildStructureMap();
        EnumMobFactoryTier.TIER_TWO.buildStructureMap();
        EnumMobFactoryTier.TIER_THREE.buildStructureMap();
        EnumMobFactoryTier.TIER_FOUR.buildStructureMap();
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

    public static void getFactoryLayout(EnumMobFactoryTier tier, BlockPos origin, EnumFacing facing, List<LayoutBlockInfo> layoutBlockInfoList) {

        for (MobFactoryModule s : tier.structureModules) {

            BlockPos p = BlockPosHelper.rotateFromSouth(s.getOffset(), facing.getOpposite());
            p = origin.add(p);
            layoutBlockInfoList.add(new LayoutBlockInfo(p, s.moduleType));
        }
    }
}
