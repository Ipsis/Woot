package ipsis.woot.recipes.factory;

import ipsis.woot.Woot;
import ipsis.woot.config.ConfigRegistry;
import ipsis.woot.factory.multiblock.FactoryConfig;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class FactoryRecipeRegistry {

    public static final FactoryRecipeRegistry REGISTRY = new FactoryRecipeRegistry();

    public FactoryRecipe get(FactoryConfig factoryConfig) {

        if (factoryConfig == null)
            return new FactoryRecipe(200, 200);

        /**
         * Log the config just now
         */
        Woot.LOGGER.info("Creating recipe");
        Woot.LOGGER.info("Upgrades");
        FactoryConfig.UpgradeInfo massInfo = factoryConfig.getUpgradeInfo(FactoryConfig.FactoryConfigUpgrade.Upgrade.MASS);
        if (massInfo.isValid())
            Woot.LOGGER.info("MASS_" + massInfo.getTier() + "/" + massInfo.getParam());
        FactoryConfig.UpgradeInfo lootingInfo = factoryConfig.getUpgradeInfo(FactoryConfig.FactoryConfigUpgrade.Upgrade.LOOTING);
        if (lootingInfo.isValid())
            Woot.LOGGER.info("LOOTING_" + lootingInfo.getTier() + "/" + lootingInfo.getParam());
        FactoryConfig.UpgradeInfo rateInfo = factoryConfig.getUpgradeInfo(FactoryConfig.FactoryConfigUpgrade.Upgrade.RATE);
        if (rateInfo.isValid())
            Woot.LOGGER.info("RATE_" + rateInfo.getTier() + "/" + rateInfo.getParam());

        /**
         * Spawn time is the time of the slowest mob
         */
        int time = 1;
        for (FakeMobKey fakeMobKey : factoryConfig.getValidMobs())
            time = Math.max(time, ConfigRegistry.CONFIG_REGISTRY.getSpawnTime(fakeMobKey));

        FactoryRecipe recipe = new FactoryRecipe(200, 200);
        return recipe;
    }

    private Map<FakeMobKey, Integer> MOB_HEALTH = new HashMap<>();
    public int getMobHealth(@Nonnull World world, @Nonnull FakeMobKey fakeMobKey) {
        int health = 65535;
        if (MOB_HEALTH.containsKey(fakeMobKey))
            health = MOB_HEALTH.get(fakeMobKey);
        else {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("id", fakeMobKey.getResourceLocation().toString());
            Entity entity = AnvilChunkLoader.readWorldEntity(nbt, world, false);
            if (entity != null && entity instanceof EntityLiving) {
                health = (int)((EntityLiving) entity).getHealth();
                MOB_HEALTH.put(fakeMobKey, health);
            }
        }
        return health;
    }

}
