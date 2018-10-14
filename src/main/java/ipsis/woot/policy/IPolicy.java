package ipsis.woot.policy;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface IPolicy {

    boolean canCaptureMob(EntityLiving entityLiving);
    boolean canCaptureMob(ResourceLocation resourceLocation);
    boolean canLearnItem(ItemStack itemStack);
    boolean canDropItem(ItemStack itemStack);
}
