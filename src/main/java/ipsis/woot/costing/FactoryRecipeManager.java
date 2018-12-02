package ipsis.woot.costing;

import ipsis.woot.factory.recipes.FactoryRecipe;
import ipsis.woot.factory.structure.FactoryConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class FactoryRecipeManager {

    public static final FactoryRecipeManager INSTANCE = new FactoryRecipeManager();

    public void clear() {
        mobHealthMap.clear();
    }

    public FactoryRecipe createFactoryRecipe(FactoryConfig factoryConfig, World world) {

        int health = getMobHealth(factoryConfig.getFakeMobKey().getResourceLocation(), world);
        return IntegerCalculator.createFactoryRecipe(health, factoryConfig);
    }

    // cache of mob healths, rather than use the EntityList all the time
    private Map<ResourceLocation, Integer> mobHealthMap = new HashMap<>();
    private int getMobHealth(ResourceLocation resourceLocation, World world) {

        // If all else fails, then make it very expensive
        int cost = 5000;
        if (mobHealthMap.containsKey(resourceLocation)) {
            cost = mobHealthMap.get(resourceLocation);
        } else {
            Entity entity = EntityList.createEntityByIDFromName(resourceLocation, world);
            if (entity instanceof EntityLiving) {
                cost = (int) ((EntityLiving) entity).getMaxHealth();
                mobHealthMap.put(resourceLocation, cost);
            }
        }

        return cost;
    }
}
