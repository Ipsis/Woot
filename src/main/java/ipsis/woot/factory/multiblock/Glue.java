package ipsis.woot.factory.multiblock;

import ipsis.woot.Woot;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Glue implements MultiBlockGlue {

    private MultiBlockMaster master;
    private TileEntity te;
    private MultiBlockGlueProvider iMultiBlockGlueProvider;

    public Glue(TileEntity te, MultiBlockGlueProvider iMultiBlockGlueProvider) {
        this.te = te;
        this.iMultiBlockGlueProvider = iMultiBlockGlueProvider;
    }

    @Override
    public void clearMaster() {
        if (hasMaster()) {
            master = null;
            te.getWorld().setBlockState(te.getPos(),
                    te.getBlockState().with(BlockStateProperties.ATTACHED, false), 3);
            WorldHelper.updateClient(te.getWorld(), te.getPos());
            // TODO update neighbours
        }
    }

    @Override
    public void setMaster(MultiBlockMaster master) {
        if (!master.equals(this.master)) {
            this.master = master;
            te.getWorld().setBlockState(te.getPos(),
                    te.getBlockState().with(BlockStateProperties.ATTACHED, true), 3);
            WorldHelper.updateClient(te.getWorld(), te.getPos());
            // TODO update neighbours
        }
    }

    @Override
    public void onGoodbye() {
        if (hasMaster())
            master.interrupt();
    }

    @Override
    public void onHello(World world, BlockPos pos) {
        if (!hasMaster())
            GlueHelper.findMaster(world, iMultiBlockGlueProvider);
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
