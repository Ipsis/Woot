package ipsis.woot.modules.factory.blocks;

import ipsis.woot.modules.factory.ComponentType;
import ipsis.woot.modules.factory.FactoryModule;
import ipsis.woot.modules.factory.FactoryTier;
import ipsis.woot.modules.factory.layout.PatternLibrary;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class LayoutTileEntity extends TileEntity {

    public LayoutTileEntity() {
        super(FactoryModule.LAYOUT_TE.get());
    }

    public static final int DISPLAY_ALL = -1;

    public void nextDisplayLevel() {

        displayLevel++;
        if (displayLevel >= PatternLibrary.get().get(factoryTier).height)
            displayLevel = DISPLAY_ALL;
        setChanged();
        level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 4);
    }

    public void nextFactoryTier() {

        factoryTier = factoryTier.getNextValid();
        setChanged();
        level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 4);
    }

    public int getDisplayLevel() {
        return this.displayLevel;
    }

    public FactoryTier getFactoryTier() {
        return this.factoryTier;
    }

    /**
     * NBT
     */
    private int displayLevel = DISPLAY_ALL;
    private FactoryTier factoryTier = FactoryTier.TIER_1;
    private static final String KEY_LEVEL = "level";
    private static final String KEY_TIER = "tier";

    private void saveFactory(CompoundNBT tag) {
        tag.putInt(KEY_LEVEL, this.displayLevel);
        tag.putInt(KEY_TIER, this.factoryTier.getId());
    }

    private void loadFactory(CompoundNBT tag) {
        this.displayLevel = tag.getInt(KEY_LEVEL);
        this.factoryTier = FactoryTier.getById(tag.getInt(KEY_TIER));
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        super.save(tag);
        saveFactory(tag);
        return tag;
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state, tag);
        loadFactory(tag);
    }

    /**
     * Client receives a new chunk and requests data from server S:getUpdateTag()/C:handleUpdateTag()
     */
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT tag = super.getUpdateTag();
        saveFactory(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        load(state, tag);
    }

    /**
     * Client block update and requests data from server S:getUpdatePacket()/C:onDataPacket()
     */
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.worldPosition, 1, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT tag = pkt.getTag();
        handleUpdateTag(getBlockState(), tag);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {

        /**
         * Biggest factory is a 9x9x9
         * Collision box does not change
         */
        BlockPos pos = getBlockPos();
        return new AxisAlignedBB(
                pos.offset(-5, -1, -5),
                pos.offset(5, 5, 5)
        );
    }
}
