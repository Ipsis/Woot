package ipsis.woot.tileentity;

import ipsis.woot.block.BlockMobFactoryUpgrade;
import ipsis.woot.block.BlockMobFactoryUpgradeB;
import ipsis.woot.manager.EnumSpawnerUpgrade;
import ipsis.woot.tileentity.ng.farmblocks.*;
import ipsis.woot.util.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TileEntityMobFactoryUpgrade extends TileEntity implements IFarmBlockConnection, IFarmBlockUpgrade{

    IFarmBlockMaster farmBlockMaster = null;


    public boolean hasMaster() { return farmBlockMaster != null; }


    @Override
    public BlockPos getStructurePos() {

        return getPos();
    }

    public void blockAdded() {

        IFarmBlockMaster tmpMaster = new UpgradeMasterLocator().findMaster(getWorld(), getPos(), this);
        if (tmpMaster != null)
            tmpMaster.interruptFarmStructure();
    }

    @Override
    public void invalidate() {

        // Master will be set by the farm when it finds the block
        if (hasMaster())
            farmBlockMaster.interruptFarmStructure();
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
        nbtTagCompound.setBoolean("formed", farmBlockMaster != null);
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
     * IFarmBlockConnection
     */
    public void clearMaster() {

        if (farmBlockMaster != null) {
            farmBlockMaster = null;
            WorldHelper.updateClient(getWorld(), getPos());
        }
    }

    @Override
    public void setMaster(IFarmBlockMaster master) {

        if (farmBlockMaster != master) {
            farmBlockMaster = master;
            WorldHelper.updateClient(getWorld(), getPos());
        }
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

}
