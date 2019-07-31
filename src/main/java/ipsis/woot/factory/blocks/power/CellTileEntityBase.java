package ipsis.woot.factory.blocks.power;

import ipsis.woot.util.WootDebug;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.capability.TileFluidHandler;

import java.util.List;

public abstract class CellTileEntityBase extends TileFluidHandler implements WootDebug {

    public CellTileEntityBase(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
        tank.setCapacity(getCapacity());
        tank.setTileEntity(this);
    }

    private int amount = 0;
    public void fakeFluidHandlerFill(int amount) {
        this.amount += amount;
        this.amount = MathHelper.clamp(this.amount, 0, tank.getCapacity());
    }

    public int fakeFluidHandlerDrain(int amount) {
        int drained = 0;
        if (this.amount > amount) {
            drained = amount;
            this.amount -= amount;
        } else {
            drained = this.amount;
            this.amount = 0;
        }
        return drained;
    }

    abstract int getCapacity();
    abstract int getMaxTransfer();

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> CellTileEntity");
        debug.add("      capacity: " + tank.getCapacity());
        debug.add("      transfer: " + getMaxTransfer());
        debug.add("      contains: " + tank.getFluid());
        debug.add("      fake: " + amount);
        return debug;
    }
}
