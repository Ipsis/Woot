package ipsis.woot.util;

import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.reference.Settings;
import net.minecraft.util.DamageSource;

public class DamageSourceWoot extends DamageSource {

    static final String IN_SPAWNER = "inSpawner";
    static final String IN_SPAWNER_LOOTING_I = IN_SPAWNER + ".lootingI";
    static final String IN_SPAWNER_LOOTING_II = IN_SPAWNER + ".lootingII";
    static final String IN_SPAWNER_LOOTING_III = IN_SPAWNER + ".lootingIII";

    public static DamageSourceWoot inSpawner = new DamageSourceWoot(IN_SPAWNER, EnumEnchantKey.NO_ENCHANT);
    public static DamageSourceWoot inSpawnerLootingI = new DamageSourceWoot(IN_SPAWNER_LOOTING_I, EnumEnchantKey.LOOTING_I);
    public static DamageSourceWoot inSpawnerLootingII = new DamageSourceWoot(IN_SPAWNER_LOOTING_II, EnumEnchantKey.LOOTING_II);
    public static DamageSourceWoot inSpawnerLootingIII = new DamageSourceWoot(IN_SPAWNER_LOOTING_III, EnumEnchantKey.LOOTING_III);

    EnumEnchantKey enchantKey;
    public DamageSourceWoot(String damageType, EnumEnchantKey enchantKey) {
        super(damageType);
        this.enchantKey = enchantKey;
    }

    public static DamageSourceWoot getDamageSource(String damageType) {

        if (damageType.equals(IN_SPAWNER))
            return inSpawner;
        else if (damageType.equals(IN_SPAWNER_LOOTING_I))
            return inSpawnerLootingI;
        else if (damageType.equals(IN_SPAWNER_LOOTING_II))
            return inSpawnerLootingII;
        else if (damageType.equals(IN_SPAWNER_LOOTING_III))
            return inSpawnerLootingIII;

        return null;
    }

    public static DamageSourceWoot getDamageSource(EnumEnchantKey enchantKey) {

        if (enchantKey == EnumEnchantKey.NO_ENCHANT)
            return inSpawner;
        else if (enchantKey == EnumEnchantKey.LOOTING_I)
            return inSpawnerLootingI;
        else if (enchantKey == EnumEnchantKey.LOOTING_II)
            return inSpawnerLootingII;
        else if (enchantKey == EnumEnchantKey.LOOTING_III)
            return inSpawnerLootingIII;

        return null;
    }

    public EnumEnchantKey getEnchantKey() {
        return enchantKey;
    }

    public static DamageSourceWoot getDamageSource(int lootingLevel) {

        if (lootingLevel == Settings.enchantLootingILevel)
            return inSpawnerLootingI;
        else if (lootingLevel == Settings.enchantLootingIILevel)
            return inSpawnerLootingII;
        else if (lootingLevel == Settings.enchantLootingIIILevel)
            return inSpawnerLootingIII;

        return null;
    }
}
