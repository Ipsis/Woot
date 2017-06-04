package ipsis.woot.tileentity.ng.farmblocks;

import ipsis.woot.tileentity.ng.WootMob;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public interface IFarmBlockController {

    @Nonnull WootMob getWootMob();
    boolean isProgrammed();
    boolean program(ItemStack itemStack);
}
