package ipsis.woot.farmblocks;

import ipsis.Woot;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.WorldHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FactoryGlue implements IFactoryGlue {

    private IFarmBlockMaster master = null;

    public boolean hasMaster() {

        return master != null;
    }

    @Override
    public void clearMaster() {

        if (hasMaster()) {
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.MULTIBLOCK, "FactoryGlue:", "clearMaster");

            master = null;
            WorldHelper.updateClient(te.getWorld(), te.getPos());

            if (type == FactoryBlockType.CELL)
                WorldHelper.updateNeighbors(te.getWorld(), te.getPos(), ModBlocks.blockCell);
            else if (type == FactoryBlockType.EXPORTER)
                WorldHelper.updateNeighbors(te.getWorld(), te.getPos(), ModBlocks.blockExporter);
            else if (type == FactoryBlockType.IMPORTER)
                WorldHelper.updateNeighbors(te.getWorld(), te.getPos(), ModBlocks.blockImporter);
        }
    }

    @Override
    public void setMaster(IFarmBlockMaster master) {

        if (this.master != master) {
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.MULTIBLOCK, "FactoryGlue:", "setMaster");

            this.master = master;
            WorldHelper.updateClient(te.getWorld(), te.getPos());

            if (type == FactoryBlockType.CELL)
                WorldHelper.updateNeighbors(te.getWorld(), te.getPos(), ModBlocks.blockCell);
            else if (type == FactoryBlockType.EXPORTER)
                WorldHelper.updateNeighbors(te.getWorld(), te.getPos(), ModBlocks.blockExporter);
            else if (type == FactoryBlockType.IMPORTER)
                WorldHelper.updateNeighbors(te.getWorld(), te.getPos(), ModBlocks.blockImporter);
        }
    }

    private IFarmBlockMasterLocator locater;
    private FactoryBlockType type;
    private IFactoryGlueProvider provider;
    private TileEntity te;
    public FactoryGlue(FactoryBlockType type, IFarmBlockMasterLocator locator, TileEntity te, IFactoryGlueProvider iFactoryGlueProvider) {

        this.type = type;
        this.locater = locator;
        this.te = te;
        this.provider = iFactoryGlueProvider;
    }

    @Override
    public void onHello(World world, BlockPos pos) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.MULTIBLOCK, "FactoryGlue:", "onHello");
        IFarmBlockMaster tmpMaster = locater.findMaster(world, pos, provider);
        if (tmpMaster != null)
            tmpMaster.interruptFarmStructure();
    }

    @Override
    public void onGoodbye() {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.MULTIBLOCK, "FactoryGlue:", "onGoodbye");
        if (hasMaster())
            master.interruptFarmStructure();
    }

    @Override
    public FactoryBlockType getType() {
        return type;
    }

    @Override
    public BlockPos getPos() {
        return te.getPos();
    }
}
