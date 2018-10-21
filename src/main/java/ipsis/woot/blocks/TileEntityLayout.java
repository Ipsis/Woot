package ipsis.woot.blocks;

import ipsis.Woot;
import ipsis.woot.factory.structure.pattern.AbsolutePattern;
import ipsis.woot.util.FactoryTier;
import ipsis.woot.util.WorldHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityLayout extends TileEntity {

    private EnumFacing facing = EnumFacing.SOUTH;
    private FactoryTier factoryTier = FactoryTier.TIER_1;
    private AbsolutePattern absolutePattern;

    public void setFacing(EnumFacing facing) {
        this.facing = facing;
        markDirty();
        refreshLayout();
    }

    public void refreshLayout() {

        // The server never needs the block list - it is only used for rendering
        if (WorldHelper.isServerWorld(getWorld()))
            return;

        // Now offset the layout by 1 so the factory display is BELOW the guide block
        BlockPos pos = getPos().down(1);
        absolutePattern = Woot.PATTERN_REPOSITORY.createAbsolutePattern(getWorld(), FactoryTier.TIER_1, pos, facing);
    }

    public AbsolutePattern getAbsolutePattern() { return this.absolutePattern; }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {

        /**
         * This defaults to the bounding box size which will be a single blocks.
         * For this block we need it to be a bit larger to accommodate the largest tier factory
         */
        BlockPos pos = getPos();
        return new AxisAlignedBB(
                pos.add(-Woot.PATTERN_REPOSITORY.getMaxXZOffset(), -1, -Woot.PATTERN_REPOSITORY.getMaxXZOffset()),
                pos.add(Woot.PATTERN_REPOSITORY.getMaxXZOffset(), Woot.PATTERN_REPOSITORY.getMaxYOffset() - 1, Woot.PATTERN_REPOSITORY.getMaxXZOffset()));
    }
}
