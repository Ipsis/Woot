package ipsis.woot.policy;

import ipsis.woot.util.CompareUtils;
import ipsis.woot.util.WootMobName;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * The policy basically does the following
 *
 * Internal first:
 *  Am I blocking everything from the MOD
 *  Am I blocking this specific item or MOB
 *
 * External next:
 *  Is the config blocking everything from the MOD
 *  Is the config blocking this specific item or MOB
 */

public class PolicyRepository implements IPolicy {

    // Internal take priority
    // These are things that will not work nicely with Woot
    private List<String> internalModBlacklist = new ArrayList<>();
    private List<WootMobName> internalEntityBlacklist = new ArrayList<>();
    private List<String> internalItemModBlacklist = new ArrayList<>();
    private List<ItemStack> internalItemBlacklist = new ArrayList<>();

    // External configuration
    private List<String> externalModBlacklist = new ArrayList<>();
    private List<WootMobName> externalEntityBlacklist = new ArrayList<>();
    private List<String> externalItemModBlacklist = new ArrayList<>();
    private List<ItemStack> externalItemBlacklist = new ArrayList<>();
    private List<WootMobName> externalEntityWhitelist = new ArrayList<>();



    /**
     * IPolicy
     */
    @Override
    public boolean canCapture(WootMobName wootMobName) {

        /**
         * Internal
         */
        for (String mod : internalModBlacklist)
            if (CompareUtils.isFromMod(wootMobName, mod))
                return false;

        for (WootMobName name : internalEntityBlacklist)
            if (CompareUtils.isSameMob(wootMobName, name.getName()))
                return false;

        /**
         * External
         */
        for (String mod : externalModBlacklist)
            if (CompareUtils.isFromMod(wootMobName, mod))
                return false;

        for (WootMobName name : externalEntityBlacklist)
            if (CompareUtils.isSameMob(wootMobName, name.getName()))
                return false;

        if (!externalEntityWhitelist.isEmpty()) {
            for (WootMobName name : externalEntityWhitelist)
                if (CompareUtils.isSameMob(wootMobName, name.getName()))
                    return true;

            // Not on the entity whitelist then you cannot capture
            return false;
        }

       return true;
    }

    @Override
    public boolean canLearnDrop(ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean canDrop(ItemStack itemStack) {
       /*
        for (String modName : internalModItemBlacklist)
            if (CompareUtils.isFromMod(itemStack, modName))
                return false;

        // Internal item blacklist
        for (String itemName : internalItemBlacklist)
            if (CompareUtils.isSameItem(itemStack, itemName))
                if (name.equalsIgnoreCase(itemName))
                    return false;
                    */

        return true;
    }

    @Override
    public void addModToEntityList(String modName, boolean internal) {

        if (internal) {
            internalModBlacklist.add(modName);
        } else {
            externalModBlacklist.add(modName);
        }
    }

    @Override
    public void addEntityToEntityList(WootMobName wootMobName, boolean internal) {

        if (internal) {
            internalEntityBlacklist.add(wootMobName);
        } else {
            externalEntityBlacklist.add(wootMobName);
        }
    }

    @Override
    public void addEntityToEntityWhitelist(WootMobName wootMobName) {

        externalEntityWhitelist.add(wootMobName);
    }

    @Override
    public void addModToDropList(String modName, boolean internal) {

        if (internal)
            internalItemModBlacklist.add(modName);
        else
            externalItemModBlacklist.add(modName);
    }

    @Override
    public void addItemToDropList(ItemStack itemStack, boolean internal) {

        if (internal)
            internalItemBlacklist.add(itemStack);
        else
            externalItemBlacklist.add(itemStack);

    }

    @Override
    public List<String> getEntityList(boolean internal) {

        List<String> entities = new ArrayList<>();

        if (internal) {
            entities.addAll(internalModBlacklist);
            for (WootMobName wootMobName : internalEntityBlacklist)
                entities.add(wootMobName.getName());
        } else {
            entities.addAll(externalModBlacklist);
            for (WootMobName wootMobName : externalEntityBlacklist)
                entities.add(wootMobName.getName());
        }

        return entities;
    }

    @Override
    public List<String> getItemList(boolean internal) {

        List<String> items = new ArrayList<>();

        if (internal) {
            items.addAll(internalItemModBlacklist);
            for (ItemStack itemStack : internalItemBlacklist)
                items.add(itemStack.getUnlocalizedName());
        } else {
            items.addAll(externalItemModBlacklist);
            for (ItemStack itemStack : externalItemBlacklist)
                items.add(itemStack.toString());
        }

        return items;
    }
}
