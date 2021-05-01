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

    public int getDisplayLevel() { return displayLevel; }
    public int setNextLevel() {
        displayLevel++;
        if (displayLevel >= PatternRepository.get().getPattern(tier).getHeight())
            displayLevel = -1;
        setChanged();
        refresh();
        return displayLevel;
    }

    public int getYForLevel() {
        // Heart is one off the top
        // Layout is offset from the heart
        int height = PatternRepository.get().getPattern(tier).getHeight();
        return getBlockPos().getY() - height - LAYOUT_Y_OFFSET + displayLevel + 2;
    }

    public Tier getTier() { return tier; }
    public Tier setNextTier() {
        tier = tier.getNextValid();
        setChanged();
        refresh();
        return tier;
    }

    AbsolutePattern absolutePattern = null;
    public void refresh() {
        if (!level.isClientSide)
            return;

        BlockPos origin = getBlockPos().below(LAYOUT_Y_OFFSET);
        absolutePattern = AbsolutePattern.create(level, tier, origin, level.getBlockState(worldPosition).getValue(BlockStateProperties.HORIZONTAL_FACING));
    }

    public AbsolutePattern getAbsolutePattern() { return absolutePattern; }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        /**
         * This defaults to the bounding box size which will be a single blocks.
         * For this block we need it to be a bit larger to accommodate the largest tier factory
         */
        BlockPos pos = getBlockPos();
        return new AxisAlignedBB(
                pos.offset(-PatternRepository.get().getMaxXZOffset(), -1, -PatternRepository.get().getMaxXZOffset()),
                pos.offset(PatternRepository.get().getMaxXZOffset(), PatternRepository.get().getMaxYOffset() - 1, PatternRepository.get().getMaxXZOffset()));
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
        return new SUpdateTileEntityPacket(getBlockPos(), 0, compoundNBT);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        deserializeNBT(pkt.getTag());
        refresh();
    }

    /**
     * Initial chunk load
     * S:getUpdateTag() -> C:handleUpdateTag()
     */
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT compoundNBT = super.getUpdateTag();
        this.save(compoundNBT);
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
    int displayLevel = -1; // level in the structure to show
    static final String KEY_LEVEL = "level";
    static final String KEY_TIER = "tier";

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        compound.putInt(KEY_LEVEL, displayLevel);
        compound.putInt(KEY_TIER, tier.ordinal());
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT compound) {
        super.deserializeNBT(compound);
        readFromNBT(compound);
    }

    @Override
    public void load(BlockState blockState, CompoundNBT compoundNBT) {
        super.load(blockState, compoundNBT);
        readFromNBT(compoundNBT);
    }

    private void readFromNBT(CompoundNBT compound) {
        displayLevel = MathHelper.clamp(compound.getInt(KEY_LEVEL), -1, 16);
        tier = Tier.byIndex(compound.getInt(KEY_TIER));
    }
}
