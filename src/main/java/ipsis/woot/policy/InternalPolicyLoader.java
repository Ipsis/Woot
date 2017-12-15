package ipsis.woot.policy;

import ipsis.woot.util.ItemStackHelper;
import ipsis.woot.util.WootMobName;
import ipsis.woot.util.WootMobNameBuilder;
import net.minecraft.item.ItemStack;

/**
 * This specifies mods, entities and items that don't work well with
 * Woot.
 * Anything that I regard as OP or goes against the direction that
 * another mod author intended will be in the main config files.
 */
public class InternalPolicyLoader {

    // MODS where the entities don't work well with Woot
    private static final String[] BLACKLIST_ENTITIES_FROM_MODS = {
            "cyberware",
            "withercrumbs",
            "draconicevolution"
    };

    // Entities that don't work well with Woot
    private static final String[] BLACKLIST_ENTITIES = {
            "arsmagicsa2:Dryad",
            "abyssalcraft:lesserdreadbeast",
            "abyssalcraft:greaterdreadspawn",
            "abyssalcraft:chagaroth",
            "abyssalcraft:shadowboss",
            "abyssalcraft:Jzahar",
            "roots:spriteGuardian",
            "twilightforest:lich",
            "twilightforest:knight_phantom",
            "twilightforest:quest_ram",
            "forestry:butterflyge"
    };

    // MODS where the drops don't work well with Woot
    private static final String[] BLACKLIST_DROPS_FROMS_MODS = {
            "eplus",
            "everlastingabilities",
            "cyberware"
    };

    // Items that don't work will with Woot
    private static final String[] BLACKLIST_ITEMS= {

    };

    public void load(IPolicy policy) {

        for (String modName : BLACKLIST_ENTITIES_FROM_MODS)
            policy.addModToEntityList(modName, true);

        for (String entityName : BLACKLIST_ENTITIES) {
            WootMobName wootMobName = WootMobNameBuilder.createFromConfigString(entityName);
            if (wootMobName.isValid())
                policy.addEntityToEntityList(wootMobName, true);
        }

        for (String modName : BLACKLIST_DROPS_FROMS_MODS)
            policy.addModToDropList(modName, true);

        for (String drop : BLACKLIST_ITEMS) {
            ItemStack itemStack = ItemStackHelper.getItemStackFromName(drop);
            if (!itemStack.isEmpty())
                policy.addItemToDropList(itemStack, true);
        }
    }
}
