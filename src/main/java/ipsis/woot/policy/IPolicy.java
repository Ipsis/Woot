package ipsis.woot.policy;

import ipsis.woot.util.WootMobName;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IPolicy {

    boolean canCapture(WootMobName wootMobName);
    boolean canGenerateFrom(WootMobName wootMobName);
    boolean canLearnDrop(ItemStack itemStack);
    boolean canDrop(ItemStack itemStack);

    // Return a list of mods and specific mobs that are blocked
    List<String> getEntityList(boolean internal);
    // Return a list of mods and specific items that are blocked
    List<String> getItemList(boolean internal);

    // Stop all entities from the mods
    void addModToEntityList(String modName, boolean internal);

    // Stop specific entity
    void addEntityToEntityList(WootMobName wootMobName, boolean internal);

    // Add specific entity to the whitelist
    void addEntityToEntityWhitelist(WootMobName wootMobName);

    // Stop all items from the mod
    void addModToDropList(String modName, boolean internal);

    // Stop specific item
    void addItemToDropList(ItemStack itemStack, boolean internal);

    // Allow generate but no capture of entity
    void addEntityToGenerateOnlyList(WootMobName wootMobName);
}
