package ipsis.woot.policy;

import ipsis.woot.util.WootMobName;
import net.minecraft.item.ItemStack;

public interface IPolicy {

    boolean canCapture(WootMobName wootMobName);
    boolean canLearnDrop(ItemStack itemStack);
    boolean canDrop(ItemStack itemStack);

    void addModToEntityList(String modName, boolean internal);
    void addEntityToEntityList(WootMobName wootMobName, boolean internal);
    void addModToDropList(String modName, boolean internal);
    void addIteStackToDropList(ItemStack itemStack, boolean internal);
}
