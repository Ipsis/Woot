package ipsis.woot.tileentity.ng.configuration;

import ipsis.woot.tileentity.ng.WootMobName;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class MobXPManager implements IMobCost {

    private Map<String, Integer> mobXpMap = new HashMap<>();

    private String makeKey(WootMobName wootMobName) {

        return wootMobName.getName();
    }

    /**
     * IMobCost
     */
    @Override
    public int getMobSpawnCost(@Nonnull World world, @Nonnull WootMobName wootMobName) {

        int cost = 65535;
        if (wootMobName.isValid()) {
            String key = makeKey(wootMobName);
            if (mobXpMap.containsKey(key)) {
                cost = mobXpMap.get(key);
            } else {
                Entity entity = EntityList.createEntityByIDFromName(wootMobName.getResourceLocation(), world);
                if (entity != null) {
                    cost = ((EntityLiving)entity).experienceValue;
                    mobXpMap.put(key, cost);
                    entity = null;
                }
            }
        }
        return 0;
    }
}
