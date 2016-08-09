package ipsis.woot.manager;

import ipsis.woot.oss.LogHelper;
import ipsis.woot.tileentity.TileEntityMobFactory;
import ipsis.woot.tileentity.TileEntityMobFactoryExtender;
import ipsis.woot.tileentity.TileEntityMobFactoryProxy;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class ProxyManager {

    boolean validProxy;
    TileEntityMobFactory factory;
    List<TileEntityMobFactoryExtender> extenderList;
    TileEntityMobFactoryProxy proxy;

    public ProxyManager(TileEntityMobFactory factory) {

        this();
        this.factory = factory;
    }

    private ProxyManager() {

        proxy = null;
        extenderList = new ArrayList<>();
        validProxy = false;
    }

    public boolean isValidProxy() {
        return validProxy;
    }

    public List<TileEntityMobFactoryExtender> getExtenderList() {

        return extenderList;
    }

    public void scanProxy() {

        proxy = null;
        extenderList.clear();
        validProxy = false;

        BlockPos blockPos = factory.getPos().down(1);
        TileEntity te = factory.getWorld().getTileEntity(blockPos);
        while (te != null && te instanceof TileEntityMobFactoryExtender) {
            extenderList.add((TileEntityMobFactoryExtender)te);
            blockPos = blockPos.down(1);
            te = factory.getWorld().getTileEntity(blockPos);
        }

        if (te != null && te instanceof TileEntityMobFactoryProxy)
            proxy = (TileEntityMobFactoryProxy)te;

        if (proxy != null && extenderList.size() >= 1) {
            validProxy = true;
            setMaster(true);
        }
    }

    public void setMaster(boolean connected) {

        if (proxy != null) {
            if (connected)
                proxy.setMaster(factory);
            else
                proxy.clearMaster();
        }

        for (TileEntityMobFactoryExtender te : extenderList) {
            if (connected)
                te.setMaster(factory);
            else
                te.clearMaster();
        }
    }

    public List<IItemHandler> getIItemHandlers() {

        List<IItemHandler> handlers = new ArrayList<IItemHandler>();

        if (validProxy && proxy != null) {
            for (EnumFacing f : EnumFacing.VALUES) {
                if (f == EnumFacing.UP)
                    continue;;

                BlockPos pos = proxy.getPos().offset(f);
                if (!factory.getWorld().isBlockLoaded(pos))
                    continue;

                TileEntity te = factory.getWorld().getTileEntity(pos);
                if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite()))
                    handlers.add(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite()));
            }
        }

        return handlers;
    }



}
