package ipsis.woot.policy;

import ipsis.Woot;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.util.BlacklistComparator;
import ipsis.woot.util.WootMobName;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PolicyRepository implements IPolicy {

    private List<String> blacklistModEntities = new ArrayList<>();
    private List<WootMobName> blacklistEntities = new ArrayList<>();


    /**
     * IPolicy
     */
    @Override
    public boolean canCapture(WootMobName wootMobName) {

        // blacklisted mod
        for (String mod : blacklistModEntities) {
            if (BlacklistComparator.isSameMod(wootMobName, mod))
                return false;
        }

        // blacklisted entity
        for (WootMobName name : blacklistEntities) {
            if (BlacklistComparator.isSameMob(wootMobName, name.getName()))
                return false;
        }

       if (Woot.wootConfiguration.getBoolean(EnumConfigKey.MOB_WHITELIST)) {

       } else {

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
            if (BlacklistComparator.isSameMod(itemStack, modName))
                return false;

        // Internal item blacklist
        for (String itemName : internalItemBlacklist)
            if (BlacklistComparator.isSameItem(itemStack, itemName))
                if (name.equalsIgnoreCase(itemName))
                    return false;
                    */

        return true;
    }

    @Override
    public void addModToEntityList(String modName, boolean internal) {

        if (internal)
            blacklistModEntities.add(modName);
    }

    @Override
    public void addEntityToEntityList(WootMobName wootMobName, boolean internal) {

        if (internal)
            blacklistEntities.add(wootMobName);
    }

    @Override
    public void addModToDropList(String modName, boolean internal) {

    }

    @Override
    public void addIteStackToDropList(ItemStack itemStack, boolean internal) {

    }
}
