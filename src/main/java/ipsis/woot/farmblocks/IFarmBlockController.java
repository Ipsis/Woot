package ipsis.woot.farmblocks;

import ipsis.woot.util.WootMob;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public interface IFarmBlockController {

    @Nonnull WootMob getWootMob();
    boolean isProgrammed();
    boolean program(ItemStack itemStack);
}
