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
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
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
        List<LazyOptional<IItemHandler>> itemHandlers = new ArrayList<>();
        for (Direction facing : Direction.values()) {
            if (!heartTileEntity.getWorld().isBlockLoaded(setup.getExportPos().offset(facing)))
                continue;

            TileEntity te = heartTileEntity.getWorld().getTileEntity(setup.getExportPos().offset(facing));
            if (!(te instanceof TileEntity))
                continue;

            itemHandlers.add(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite()));
        }

        int looting = setup.getLootingLevel();

        for (FakeMob mob : setup.getAllMobs()) {
            int mobCount = setup.getAllMobParams().get(mob).getMobCount(setup.getAllPerks().containsKey(PerkType.MASS));
            LOGGER.debug("generate: {} * {}", mob, mobCount);
            for (int i = 0; i < mobCount; i++) {
                List<ItemStack> rolledDrops = MobSimulator.getInstance().getRolledDrops(new FakeMobKey(mob, looting));
                Iterator<LazyOptional<IItemHandler>> iter = itemHandlers.iterator();
                while (iter.hasNext()) {
                    LazyOptional<IItemHandler> hdlr = iter.next();
                    hdlr.ifPresent(h -> {
                        LOGGER.debug("generate: try drop into {} ", h);
                        for (ItemStack itemStack : rolledDrops) {
                            if (itemStack.isEmpty())
                                continue;

                            ItemStack result = ItemHandlerHelper.insertItem(h, itemStack.copy(), false);
                            if (result.isEmpty())
                                itemStack.setCount(0);
                            else
                                itemStack.shrink(itemStack.getCount() - result.getCount());
                        }
                    });
                }
            }
        }

        // Experience
        if (setup.getAllPerks().containsKey(PerkType.XP)) {
            int genXp = 0;
            for (FakeMob mob : setup.getAllMobs()) {
                int xpPercent = setup.getAllMobParams().get(mob).perkXpValue;
                int mobCount = setup.getAllMobParams().get(mob).getMobCount(setup.getAllPerks().containsKey(PerkType.MASS));
                int x = (int) ((SpawnController.get().getMobExperience(mob, setup.getWorld()) / 100.0F) * xpPercent);
                genXp += (x * mobCount);
            }

            List<ItemStack> shards = XpShardBaseItem.getShards(genXp);
            Iterator<LazyOptional<IItemHandler>> iter = itemHandlers.iterator();
            while (iter.hasNext()) {
                LazyOptional<IItemHandler> hdlr = iter.next();
                hdlr.ifPresent(h -> {
                    for (ItemStack itemStack : shards) {
                        if (itemStack.isEmpty())
                            continue;

                        ItemStack result = ItemHandlerHelper.insertItem(h, itemStack.copy(), false);
                        if (result.isEmpty())
                            itemStack.setCount(0);
                        else
                            itemStack.shrink(itemStack.getCount() - result.getCount());
                    }
                });
            }
        }

        // Shard gen
        if (setup.getAllPerks().containsKey(PerkType.TIER_SHARD)) {

            List<ShardPerkData> shards = new ArrayList<>();
            shards.add(new ShardPerkData(GenericItemType.BASIC_UP_SHARD, setup.getBasicShardWeight()));
            shards.add(new ShardPerkData(GenericItemType.ADVANCED_UP_SHARD, setup.getAdvancedShardWeight()));
            shards.add(new ShardPerkData(GenericItemType.ELITE_UP_SHARD, setup.getEliteShardWeight()));

            int rolls = 1;
            List<ItemStack> dropShards = new ArrayList<>();
            if (setup.getAllPerks().get(PerkType.TIER_SHARD) == 1)
                rolls = 1;
            else if (setup.getAllPerks().get(PerkType.TIER_SHARD) == 2)
                rolls = 2;
            else if (setup.getAllPerks().get(PerkType.TIER_SHARD) == 3)
                rolls = 3;

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

            Iterator<LazyOptional<IItemHandler>> iter = itemHandlers.iterator();
            while (iter.hasNext()) {
                LazyOptional<IItemHandler> hdlr = iter.next();
                hdlr.ifPresent(h -> {
                    for (ItemStack itemStack : dropShards) {
                        if (itemStack.isEmpty())
                            continue;

                        ItemStack result = ItemHandlerHelper.insertItem(h, itemStack.copy(), false);
                        if (result.isEmpty())
                            itemStack.setCount(0);
                        else
                            itemStack.shrink(itemStack.getCount() - result.getCount());
                    }
                });
            }
        }
    }
}
