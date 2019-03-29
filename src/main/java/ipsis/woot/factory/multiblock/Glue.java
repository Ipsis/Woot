package ipsis.woot.factory.multiblock;

import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Glue implements IMultiBlockGlue {
    private IMultiBlockMaster master;
    private TileEntity te;
    private IMultiBlockGlueProvider iMultiBlockGlueProvider;

    public Glue(TileEntity te, IMultiBlockGlueProvider iMultiBlockGlueProvider) {
        this.te = te;
        this.iMultiBlockGlueProvider = iMultiBlockGlueProvider;
    }

    @Override
    public void clearMaster() {
        if (hasMaster()) {
            master = null;
            WorldHelper.updateClient(te.getWorld(), te.getPos());
            // TODO update neighbours
        }
    }

    @Override
    public void setMaster(IMultiBlockMaster master) {
        if (this.master != master) {
            this.master = master;
            WorldHelper.updateClient(te.getWorld(), te.getPos());
            // TODO update neighbours
        }
    }

    @Override
    public void onHello(World world, BlockPos pos) {
        IMultiBlockMaster tmpMaster = LocatorHelper.findMaster(world, iMultiBlockGlueProvider);
        if (tmpMaster != null)
            tmpMaster.interrupt();
    }

    @Override
    public void onGoodbye() {
        if (hasMaster())
            master.interrupt();
    }

    @Override
    public boolean hasMaster() {
        return master != null;
    }

    @Override
    public BlockPos getPos() {
        return te.getPos();
    }

}
