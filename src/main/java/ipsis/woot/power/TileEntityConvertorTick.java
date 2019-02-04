package ipsis.woot.power;

import net.minecraft.util.ITickable;

public class TileEntityConvertorTick extends TileEntityConvertor implements ITickable {

    @Override
    public void update() {

        int mb = Integer.MAX_VALUE;
        fillCellTank(mb, true);
    }
}
