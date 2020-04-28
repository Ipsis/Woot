package ipsis.woot.modules.factory.generators;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FormedSetup;
import ipsis.woot.modules.factory.PerkType;
import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.modules.factory.items.XpShardBaseItem;
import ipsis.woot.modules.generic.GenericItemType;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.simulator.spawning.SpawnController;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helper.RandomHelper;
import ipsis.woot.util.helper.StorageHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootGeneration {

    static final Logger LOGGER = LogManager.getLogger();
    static final Random RANDOM = new Random();

    public static LootGeneration get() { return INSTANCE; }
    static LootGeneration INSTANCE;
    static { INSTANCE = new LootGeneration(); }

    public void generate(HeartTileEntity heartTileEntity, FormedSetup setup) {

        /**
         * Get the output options
         */
        List<LazyOptional<IItemHandler>> itemHandlers = setup.getExportHandlers();
        /*
        for (Direction facing : Direction.values()) {
            if (!heartTileEntity.getWorld().isBlockLoaded(setup.getExportPos().offset(facing)))
                continue;

            TileEntity te = heartTileEntity.getWorld().getTileEntity(setup.getExportPos().offset(facing));
            if (!(te instanceof TileEntity))
                continue;

            itemHandlers.add(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite()));
        } */

        int looting = setup.getLootingLevel();

        // Drops
        List<ItemStack> rolledDrops = new ArrayList<>();
        for (FakeMob mob : setup.getAllMobs()) {
            int mobCount = setup.getAllMobParams().get(mob).getMobCount(setup.getAllPerks().containsKey(PerkType.MASS));
            LOGGER.debug("generate: {} * {}", mob, mobCount);

            FakeMobKey fakeMobKey = new FakeMobKey(mob, looting);
            for (int i = 0; i < mobCount; i++)
                rolledDrops.addAll(MobSimulator.getInstance().getRolledDrops(fakeMobKey));

        }
        StorageHelper.insertItems(rolledDrops, itemHandlers);


        // Experience
        if (setup.getAllPerks().containsKey(PerkType.XP)) {
            int genXp = 0;
            for (FakeMob mob : setup.getAllMobs()) {
                int xpPercent = setup.getAllMobParams().get(mob).perkXpValue;
                int mobCount = setup.getAllMobParams().get(mob).getMobCount(setup.getAllPerks().containsKey(PerkType.MASS));
                int x = (int) ((SpawnController.get().getMobExperience(mob, setup.getWorld()) / 100.0F) * xpPercent);
                genXp += (x * mobCount);
            }

            // Unused xp just thrown away
            List<ItemStack> shards = XpShardBaseItem.getShards(genXp);
            StorageHelper.insertItems(shards, itemHandlers);
        }

        // Shard gen
        if (setup.getAllPerks().containsKey(PerkType.TIER_SHARD)) {

            List<ShardPerkData> shards = new ArrayList<>();
            shards.add(new ShardPerkData(GenericItemType.BASIC_UP_SHARD, setup.getBasicShardWeight()));
            shards.add(new ShardPerkData(GenericItemType.ADVANCED_UP_SHARD, setup.getAdvancedShardWeight()));
            shards.add(new ShardPerkData(GenericItemType.ELITE_UP_SHARD, setup.getEliteShardWeight()));

            int rolls = setup.getPerkTierShardValue();
            List<ItemStack> dropShards = new ArrayList<>();

            Woot.setup.getLogger().debug("Shard gen installed");
            Woot.setup.getLogger().debug("Level:{} Drop:{} Basic:{} Advanced:{} Elite:{} Rolls:{}",
                    setup.getAllPerks().get(PerkType.TIER_SHARD),
                    setup.getShardDropChance(), setup.getBasicShardWeight(), setup.getAdvancedShardWeight(), setup.getEliteShardWeight(), rolls);

            for (int i = 0; i < rolls; i++) {
                if (RandomHelper.rollPercentage(setup.getShardDropChance())) {
                    ShardPerkData chosenShard = WeightedRandom.getRandomItem(RANDOM, shards);
                    dropShards.add(chosenShard.getItemStack());
                }
            }

            // Unused shards just thrown away
            StorageHelper.insertItems(dropShards, itemHandlers);
        }
    }
}
