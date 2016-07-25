package ipsis.woot.tileentity;

import cofh.api.energy.IEnergyReceiver;
import ipsis.woot.block.BlockMobFactoryProxy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileEntityMobFactoryProxy extends TileEntity implements IEnergyReceiver {

    public TileEntityMobFactoryProxy() {

    }

    private TileEntityMobFactory factory;
    private void findFactory() {

        if (factory == null) {
            IBlockState b = worldObj.getBlockState(pos.up(1));
            TileEntity te = worldObj.getTileEntity(pos.up(2));

            if (b.getBlock() instanceof BlockMobFactoryProxy && te instanceof TileEntityMobFactory)
                factory = (TileEntityMobFactory)te;
        }
    }

    private boolean isFactoryFormed() {

        boolean formed = false;

        findFactory();
        if (factory != null) {
            formed = factory.isFormed();
        }

        return formed;
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {

        boolean valid = false;
        if (from != EnumFacing.UP && isFactoryFormed())
            valid = true;

        return valid;
    }

    @Override
    public int getEnergyStored(EnumFacing from) {

        int rf = 0;
        if (from != EnumFacing.UP && isFactoryFormed())
            rf = factory.getEnergyStored(from);

        return rf;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {

        int rf = 0;
        if (from != EnumFacing.UP && isFactoryFormed())
            rf = factory.getMaxEnergyStored(from);

        return rf;
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {

        int rf = 0;
        if (from != EnumFacing.UP && isFactoryFormed())
            rf = factory.receiveEnergy(from, maxReceive, simulate);

        return rf;
    }
}
