package ipsis.woot.loot.schools;

import ipsis.Woot;
import ipsis.woot.farming.ITickTracker;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.loot.ILootLearner;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.EnumEnchantKey;
import ipsis.woot.util.WootMobName;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import static ipsis.woot.loot.schools.TartarusManager.INVALID_SPAWN_ID;

public class TartarusSchool implements ILootLearner {

    private int spawnId = INVALID_SPAWN_ID;

    @Override
    public void tick(ITickTracker tickTracker, World world, BlockPos origin, IFarmSetup farmSetup) {

        if (spawnId != INVALID_SPAWN_ID) {
            List<EntityItem> items = Woot.tartarusManager.getLootInBox(world, spawnId);
            LogHelper.info("Tartaruschool: collected drops " + items);

            Woot.lootRepository.learn(farmSetup.getWootMobName(), farmSetup.getEnchantKey(), items, false);
            for (EntityItem i : items)
                i.setDead();
        }

        if (!tickTracker.hasLearnTickExpired())
            return;

        WootMobName wootMobName = farmSetup.getWootMobName();
        EnumEnchantKey key = farmSetup.getEnchantKey();

        if (!Woot.lootRepository.isFull(wootMobName, key)) {
            if (spawnId == INVALID_SPAWN_ID)
                spawnId = Woot.tartarusManager.allocateSpawnBoxId();

            LogHelper.info("TartarusSchool: spawn mob " + wootMobName + "/" + key);
            Woot.tartarusManager.spawnInBox(world, spawnId, wootMobName, key);
        } else if (spawnId != INVALID_SPAWN_ID) {
            spawnId = Woot.tartarusManager.freeSpawnBoxId(spawnId);
        }

        tickTracker.resetLearnTickCount();
    }
}
