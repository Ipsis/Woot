package ipsis.woot.policy;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;

public interface IPolicy {

    boolean canCaptureMob(EntityLiving entityLiving);
    boolean canLearnItem(ItemStack itemStack);
    boolean canDropItem(ItemStack itemStack);
}
