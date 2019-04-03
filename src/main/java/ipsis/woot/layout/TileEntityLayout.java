package ipsis.woot.layout;

import ipsis.woot.Woot;
import ipsis.woot.debug.IWootDebug;
import ipsis.woot.factory.FactoryTier;
import ipsis.woot.factory.layout.AbsolutePattern;
import ipsis.woot.factory.layout.AbsolutePatternBuilder;
import ipsis.woot.factory.layout.FactoryPatternRepository;
import ipsis.woot.mod.ModTileEntities;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityLayout extends TileEntity implements IWootDebug {

    private AbsolutePattern absolutePattern = null;

    public TileEntityLayout() {
        super(ModTileEntities.layoutTileEntity);
    }

    public void refresh() {
        // Ths server never needs the block list as it is only used for rendering
        if (WorldHelper.isServerWorld(getWorld()))
            return;

        // Offset the layout be 2 sof that factory is displayed BELOW the layout block
        BlockPos origin = getPos().down(2);
        absolutePattern = AbsolutePatternBuilder.createAbsolutePattern(getWorld(), tier, origin,  facing);
    }

    public void setFacing(EnumFacing facing) {
        this.facing = facing;
        markDirty();
        refresh();
    }

    public AbsolutePattern getAbsolutePattern() { return this.absolutePattern; }

    /**
     * Level
     */
    public int getLevel() { return this.level; }
    public int setNextLevel() {
        level++;
        if (level >= FactoryPatternRepository.PATTERN_REPOSITORY.getPattern(tier).getHeight())
            level = -1;
        markDirty();
        refresh();
        return level;
    }

    /**
     * Tier
     */
    public FactoryTier getTier() { return this.tier; }
    public FactoryTier setNextTier() {
        tier = tier.getNext();
        markDirty();
        refresh();
        return tier;
    }

    /**
     * Client Sync
     * getUpdatePacket() -> onDataPacket()
     */
    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = getUpdateTag();
        return new SPacketUpdateTileEntity(getPos(), 0, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        read(pkt.getNbtCompound());
    }

    /**
     * Initial chunk load
     * getUpdateTag() -> handleUpdateTag()
     */
    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = super.getUpdateTag();
        this.write(compound);
        return compound;
    }

    /**
     * NBT
     */
    private EnumFacing facing = EnumFacing.SOUTH;
    private int level = -1; // level in the structure
    private FactoryTier tier = FactoryTier.TIER_1;
    private static final String NBT_FACING = "facing";
    private static final String NBT_LEVEL = "level";
    private static final String NBT_TIER = "tier";

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        compound.setInt(NBT_FACING, facing.ordinal());
        compound.setInt(NBT_LEVEL, level);
        compound.setInt(NBT_TIER, tier.ordinal());
        return compound;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        facing = EnumFacing.byIndex(compound.getInt(NBT_FACING));
        level = compound.getInt(NBT_LEVEL);
        tier = FactoryTier.byIndex(compound.getInt(NBT_TIER));
        refresh();
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        /**
         * This defaults to the bounding box size which will be a single blocks.
         * For this block we need it to be a bit larger to accommodate the largest tier factory
         */
        BlockPos pos = getPos();
        return new AxisAlignedBB(
                pos.add(-FactoryPatternRepository.PATTERN_REPOSITORY.getMaxXZOffset(), -1, -FactoryPatternRepository.PATTERN_REPOSITORY.getMaxXZOffset()),
                pos.add(FactoryPatternRepository.PATTERN_REPOSITORY.getMaxXZOffset(), FactoryPatternRepository.PATTERN_REPOSITORY.getMaxYOffset() - 1, FactoryPatternRepository.PATTERN_REPOSITORY.getMaxXZOffset()));
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext context) {
        debug.add("====> TileEntityLayout");
        debug.add("facing:" + facing);
        debug.add("level:" + level);
        debug.add("tier:" + tier);
        debug.add("absolutepattern:" + (absolutePattern != null ? "yes" : "no"));
        return debug;
    }
}
