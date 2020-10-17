package ipsis.woot.modules.layout.blocks;

import ipsis.woot.modules.factory.Tier;
import ipsis.woot.modules.factory.layout.AbsolutePattern;
import ipsis.woot.modules.factory.layout.PatternRepository;
import ipsis.woot.modules.layout.LayoutSetup;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;

public class LayoutTileEntity extends TileEntity {

    private static final int LAYOUT_Y_OFFSET = 1;

    public LayoutTileEntity() {
        super(LayoutSetup.LAYOUT_BLOCK_TILE.get());
    }

    public int getLevel() { return level; }
    public int setNextLevel() {
        level++;
        if (level >= PatternRepository.get().getPattern(tier).getHeight())
            level = -1;
        markDirty();
        refresh();
        return level;
    }

    public int getYForLevel() {
        // Heart is one off the top
        // Layout is offset from the heart
        int height = PatternRepository.get().getPattern(tier).getHeight();
        return getPos().getY() - height - LAYOUT_Y_OFFSET + level + 2;
    }

    public Tier getTier() { return tier; }
    public Tier setNextTier() {
        tier = tier.getNextValid();
        markDirty();
        refresh();
        return tier;
    }

    AbsolutePattern absolutePattern = null;
    public void refresh() {
        if (!world.isRemote)
            return;

        BlockPos origin = getPos().down(LAYOUT_Y_OFFSET);
        absolutePattern = AbsolutePattern.create(world, tier, origin, world.getBlockState(pos).get(BlockStateProperties.HORIZONTAL_FACING));
    }

    public AbsolutePattern getAbsolutePattern() { return absolutePattern; }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        /**
         * This defaults to the bounding box size which will be a single blocks.
         * For this block we need it to be a bit larger to accommodate the largest tier factory
         */
        BlockPos pos = getPos();
        return new AxisAlignedBB(
                pos.add(-PatternRepository.get().getMaxXZOffset(), -1, -PatternRepository.get().getMaxXZOffset()),
                pos.add(PatternRepository.get().getMaxXZOffset(), PatternRepository.get().getMaxYOffset() - 1, PatternRepository.get().getMaxXZOffset()));
        // return IForgeTileEntity.INFINITE_EXTENT_AABB;
    }

    /**
     * Client sync
     * S:getUpdatePacket() -> C:onDataPacket()
     */
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT compoundNBT = getUpdateTag();
        return new SUpdateTileEntityPacket(getPos(), 0, compoundNBT);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        deserializeNBT(pkt.getNbtCompound());
        refresh();
    }

    /**
     * Initial chunk load
     * S:getUpdateTag() -> C:handleUpdateTag()
     */
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT compoundNBT = super.getUpdateTag();
        this.write(compoundNBT);
        return compoundNBT;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        deserializeNBT(tag);
        refresh();
    }

    /**
     * NBT
     */
    Tier tier = Tier.TIER_1;
    int level = -1; // level in the structure to show
    static final String KEY_LEVEL = "level";
    static final String KEY_TIER = "tier";

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt(KEY_LEVEL, level);
        compound.putInt(KEY_TIER, tier.ordinal());
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT compound) {
        super.deserializeNBT(compound);
        readFromNBT(compound);
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        readFromNBT(compoundNBT);
    }

    private void readFromNBT(CompoundNBT compound) {
        level = MathHelper.clamp(compound.getInt(KEY_LEVEL), -1, 16);
        tier = Tier.byIndex(compound.getInt(KEY_TIER));
    }
}
