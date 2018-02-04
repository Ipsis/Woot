package ipsis.woot.tileentity;

import ipsis.woot.block.BlockMobFactoryUpgrade;
import ipsis.woot.block.BlockMobFactoryUpgradeB;
import ipsis.woot.farmblocks.*;
import ipsis.woot.util.EnumSpawnerUpgrade;
import ipsis.woot.util.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityMobFactoryUpgrade extends TileEntity implements IFarmBlockUpgrade, IFactoryGlueProvider {

    private IFactoryGlue iFactoryGlue;

    public TileEntityMobFactoryUpgrade() {

        iFactoryGlue = new FactoryGlue(IFactoryGlue.FactoryBlockType.UPGRADE, new UpgradeMasterLocator(), this, this);
    }

    public void onBlockAdded() {

        iFactoryGlue.onHello(getWorld(), getPos());
    }

    @Override
    public void invalidate() {

        super.invalidate();
        iFactoryGlue.onGoodbye();
    }

    @Override
    public void onChunkUnload() {

        super.onChunkUnload();
        iFactoryGlue.onGoodbye();
    }

    boolean isClientFormed;
    public boolean isClientFormed() { return isClientFormed; }

    /**
     * ChunkData packet handling
     * Currently calls readFromNBT on reception
     */
    @Override
    public NBTTagCompound getUpdateTag() {

        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("formed", iFactoryGlue.hasMaster());
        return nbtTagCompound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {

        super.handleUpdateTag(tag);
        isClientFormed = tag.getBoolean("formed");
    }

    /**
     * UpdateTileEntity packet handling
     */
    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        NBTTagCompound nbtTagCompound = getUpdateTag();
        return new SPacketUpdateTileEntity(this.pos, getBlockMetadata(), nbtTagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {

        handleUpdateTag(pkt.getNbtCompound());
        WorldHelper.updateClient(getWorld(), getPos());
    }

    /**
     * IFarmBlockUpgrade
     */
    @Override
    public EnumSpawnerUpgrade getUpgrade() {

        IBlockState blockState = getWorld().getBlockState(getPos());
        Block block = blockState.getBlock();

        if (block instanceof BlockMobFactoryUpgrade)
            return EnumSpawnerUpgrade.getFromVariant(blockState.getValue(BlockMobFactoryUpgrade.VARIANT));
        else if (block instanceof BlockMobFactoryUpgradeB)
            return EnumSpawnerUpgrade.getFromVariant(blockState.getValue(BlockMobFactoryUpgradeB.VARIANT));
        else
            return EnumSpawnerUpgrade.XP_I; // Should never happen
    }

    @Nonnull
    @Override
    public IFactoryGlue getIFactoryGlue() {

        return iFactoryGlue;
    }
}
