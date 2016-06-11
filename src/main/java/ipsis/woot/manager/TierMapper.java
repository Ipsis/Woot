package ipsis.woot.manager;

import ipsis.Woot;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.HashMap;

import static ipsis.woot.tileentity.multiblock.EnumMobFactoryTier.TIER_ONE;
import static ipsis.woot.tileentity.multiblock.EnumMobFactoryTier.TIER_THREE;
import static ipsis.woot.tileentity.multiblock.EnumMobFactoryTier.TIER_TWO;

public class TierMapper {

    HashMap<String, EnumMobFactoryTier> mapper;

    public TierMapper() {

        mapper = new HashMap<String, EnumMobFactoryTier>();
    }

    public EnumMobFactoryTier getTierForEntity(String entityName, int xp) {

        EnumMobFactoryTier tier = mapper.get(entityName);
        if (tier == null) {

           if (xp <= Settings.tierIMobXpCap)
               tier =  TIER_ONE;
            else if (xp <= Settings.tierIIMobXpCap)
               tier =  EnumMobFactoryTier.TIER_TWO;
            else
               tier = EnumMobFactoryTier.TIER_THREE;
        }

        return tier;
    }

    public boolean isTierValid(String entityName, int xp, EnumMobFactoryTier tier) {

        boolean valid = false;
        EnumMobFactoryTier correctTier = getTierForEntity(entityName, xp);
        switch (correctTier) {
            case TIER_ONE:
                valid = true;
                break;
            case TIER_TWO:
                valid = (tier == TIER_TWO || tier == TIER_THREE);
                break;
            case TIER_THREE:
                valid = (tier == TIER_THREE);
                break;
        }

        return valid;
    }

    public void addMapping(String entityName, EnumMobFactoryTier t) {

        if (Woot.mobRegistry.isValidMobName(entityName)) {
            LogHelper.info("Adding tier mapping " + entityName + "->" + t);
            mapper.put(entityName, t);
        }
    }

    public void cmdDumpTiers(ICommandSender sender) {

        for (String mobName : mapper.keySet()) {
            sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.dump.tiers.summary",
                    mobName, mapper.get(mobName).getTranslated()));
        }
    }
}
