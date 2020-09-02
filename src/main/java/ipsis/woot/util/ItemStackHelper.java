package ipsis.woot.util;

import ipsis.woot.Woot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class ItemStackHelper {

    /**
     * This modifies the input itemStack
     */
    public static ItemStack reduceByPercentage(ItemStack itemStack, double p) {

        p = MathHelper.clamp(p, 0.0F, 100.0F);

        Woot.setup.getLogger().debug("reduceByPercentage: {} @ {}%%",
                itemStack.getCount(), p);

        if (p == 0.0F || itemStack.isEmpty())
            return itemStack;

        if (p == 100.0F)
            return ItemStack.EMPTY;

        int reduction = (int)((itemStack.getCount() / 100.0F) * p);
        int left = itemStack.getCount() - reduction;
        itemStack.setCount(left);

        Woot.setup.getLogger().debug("reduceByPercentage: {}", itemStack.getCount());
        return itemStack;
    }
}
