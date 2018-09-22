package ipsis.woot.util;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;

public interface IPolicy {

    /**
     * Are we allowed to capture any entities from the mod
     * @param modid - the mod to check
     * @return
     */
    boolean canCaptureFromMod(String modid);

    /**
     * Are we allowed to capture a specific entity
     * @param entityLiving - the entity to check
     * @return
     */
    boolean canCaptureMob(EntityLiving entityLiving);

    /**
     * Are we allowed to learn items from this mod
     * @param modid - the mod to check
     * @return
     */
    boolean canLearnFromMod(String modid);

    /**
     * Are we allowed to learn a specific item
     * @param itemStack - the item to check
     * @return
     */
    boolean canLearnItem(ItemStack itemStack);

    boolean canDropFromMod(String modid);

    boolean canDropItem(ItemStack itemStack);


}
