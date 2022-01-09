package ipsis.woot.modules.factory.generators;

import ipsis.woot.compat.reliquary.ReliquaryPlugin;
import ipsis.woot.modules.factory.FormedSetup;
import ipsis.woot.modules.factory.blocks.HeartTileEntity;
import ipsis.woot.modules.factory.items.XpShardBaseItem;
import ipsis.woot.modules.factory.perks.Perk;
import ipsis.woot.modules.generic.items.GenericItem;
import ipsis.woot.policy.PolicyConfiguration;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.simulator.spawning.SpawnController;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helper.RandomHelper;
import ipsis.woot.util.helper.StorageHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LootGeneration {

    static final Logger LOGGER = LogManager.getLogger();
    static final Random RANDOM = new Random();
    static final SkullGenerator SKULL_GENERATOR = new SkullGenerator();

    public static LootGeneration get() { return INSTANCE; }
    static LootGeneration INSTANCE;
    static { INSTANCE = new LootGeneration(); }

    public void loadFromConfig() {
        SKULL_GENERATOR.loadFromConfig(PolicyConfiguration.MOB_PERK_HEADLESS_SKULLS.get());
    }

    public void generate(HeartTileEntity heartTileEntity, FormedSetup setup) {

        /**
         * Get the output options
         */
        List<LazyOptional<IItemHandler>> itemHandlers = setup.getExportHandlers();
        List<LazyOptional<IFluidHandler>> fluidHandlers = setup.getExportFluidHandlers();

        int looting = setup.getLootingLevel();

        // Drops
        List<ItemStack> rolledDrops = new ArrayList<>();
        for (FakeMob mob : setup.getAllMobs()) {
            int mobCount = setup.getAllMobParams().get(mob).getMobCount(setup.getAllPerks().containsKey(Perk.Group.MASS), setup.hasMassExotic());
            //LOGGER.debug("generate: {} * {}", mob, mobCount);

            FakeMobKey fakeMobKey = new FakeMobKey(mob, looting);
            for (int i = 0; i < mobCount; i++)
                rolledDrops.addAll(MobSimulator.getInstance().getRolledDrops(fakeMobKey));

            List<ItemStack> charmStacks = new ArrayList<>();
            for (ItemStack itemStack : rolledDrops) {
                if (ReliquaryPlugin.isCharmFragment(itemStack)) {
                    itemStack.setCount(0);
                    ItemStack charmFragment = ReliquaryPlugin.getCharmFragment(fakeMobKey.getMob(), setup.getWorld());
                    if (!charmFragment.isEmpty())
                        charmStacks.add(charmFragment);
                }
            }

            rolledDrops.addAll(charmStacks);

            for (ItemStack itemStack : rolledDrops) {
                if (itemStack.isDamageable()) {
                    int dmg = RandomHelper.RANDOM.nextInt(itemStack.getMaxDamage() + 1);
                    dmg = MathHelper.clamp(dmg, 1, itemStack.getMaxDamage());
                    itemStack.setDamage(dmg);
                }
                if (itemStack.isEnchanted()) {
                    if (itemStack.hasTag())
                        itemStack.getTag().remove("ench");

                    float f = setup.getWorld().getDifficultyForLocation(heartTileEntity.getPos()).getClampedAdditionalDifficulty();
                    boolean allowTreasure = false;
                    EnchantmentHelper.addRandomEnchantment(RandomHelper.RANDOM, itemStack,
                            (int)(5.0F + f * (float)RandomHelper.RANDOM.nextInt(18)), allowTreasure);
                }
            }

            // Strip out all learned wool drops for vanilla sheep
            // Add custom drop wool drops
            if (fakeMobKey.getMob().isSheep()) {
                for (ItemStack itemStack : rolledDrops) {
                    if (WoolGenerator.isWoolDrop(itemStack))
                        itemStack.setCount(0);
                }

                for (int i = 0; i < mobCount; i++)
                    rolledDrops.add(WoolGenerator.getWoolDrop(fakeMobKey.getMob()));
            }
        }
        StorageHelper.insertItems(rolledDrops, itemHandlers);


        // Experience
        if (setup.getAllPerks().containsKey(Perk.Group.XP)) {
            int genXp = 0;
            for (FakeMob mob : setup.getAllMobs()) {
                int xpPercent = setup.getAllMobParams().get(mob).getPerkXpValue();
                int mobCount = setup.getAllMobParams().get(mob).getMobCount(setup.getAllPerks().containsKey(Perk.Group.MASS), setup.hasMassExotic());
                int x = (int) ((SpawnController.get().getMobExperience(mob, setup.getWorld()) / 100.0F) * xpPercent);
                genXp += (x * mobCount);
            }

            // Unused xp just thrown away
            List<ItemStack> shards = XpShardBaseItem.getShards(genXp);
            StorageHelper.insertItems(shards, itemHandlers);
        }

        // Shard gen
        if (setup.getAllPerks().containsKey(Perk.Group.TIER_SHARD)) {

            List<ShardPerkData> shards = new ArrayList<>();
            shards.add(new ShardPerkData(GenericItem.GenericItemType.BASIC_UP_SHARD, setup.getBasicShardWeight()));
            shards.add(new ShardPerkData(GenericItem.GenericItemType.ADVANCED_UP_SHARD, setup.getAdvancedShardWeight()));
            shards.add(new ShardPerkData(GenericItem.GenericItemType.ELITE_UP_SHARD, setup.getEliteShardWeight()));

            int rolls = setup.getPerkTierShardValue();
            List<ItemStack> dropShards = new ArrayList<>();

            //Woot.setup.getLogger().debug("Shard gen installed");
            //Woot.setup.getLogger().debug("Level:{} Drop:{} Basic:{} Advanced:{} Elite:{} Rolls:{}",
            //        setup.getAllPerks().get(PerkType.TIER_SHARD),
            //        setup.getShardDropChance(), setup.getBasicShardWeight(), setup.getAdvancedShardWeight(), setup.getEliteShardWeight(), rolls);

            for (int i = 0; i < rolls; i++) {
                if (RandomHelper.rollPercentage(setup.getShardDropChance(), "shardGen")) {
                    ShardPerkData chosenShard = WeightedRandom.getRandomItem(RANDOM, shards);
                    dropShards.add(chosenShard.getItemStack());
                }
            }

            // Unused shards just thrown away
            StorageHelper.insertItems(dropShards, itemHandlers);
        }

        // Skull gen
        if (setup.getAllPerks().containsKey(Perk.Group.HEADLESS)) {
            List<ItemStack> skulls = new ArrayList<>();
            List<FakeMob> countAdjustedMobParams = setup.getAllMobs().stream().map(m -> {
                int mobCount = setup.getAllMobParams().get(m).getMobCount(setup.getAllPerks().containsKey(Perk.Group.MASS), setup.hasMassExotic());
                return Collections.nCopies(mobCount, m);
            }).flatMap(List::stream).collect(Collectors.toList());
            countAdjustedMobParams.forEach(m -> skulls.add(SKULL_GENERATOR.getSkullDrop(m, setup.getAllMobParams().get(m).getPerkHeadlessValue())));
            StorageHelper.insertItems(skulls, itemHandlers);
        }

        // Industrial Foregoing
        if (setup.getAllPerks().containsKey(Perk.Group.SLAUGHTER) || setup.getAllPerks().containsKey(Perk.Group.CRUSHER) || setup.getAllPerks().containsKey(Perk.Group.LASER)) {
            IndustrialForegoingGenerator.GeneratedFluids fluids = IndustrialForegoingGenerator.getFluids(setup, setup.getWorld());
            if (setup.getAllPerks().containsKey(Perk.Group.SLAUGHTER) && !fluids.meat.isEmpty() && !fluids.pink.isEmpty()) {
                List<FluidStack> slaughterFluids = new ArrayList<>();
                slaughterFluids.add(fluids.meat);
                slaughterFluids.add(fluids.pink);
                StorageHelper.insertFluids(slaughterFluids, fluidHandlers);
            }
            if (setup.getAllPerks().containsKey(Perk.Group.CRUSHER) && !fluids.essence.isEmpty()) {
                List<FluidStack> crusherFluids = new ArrayList<>();
                crusherFluids.add(fluids.essence);
                StorageHelper.insertFluids(crusherFluids, fluidHandlers);
            }
            if (setup.getAllPerks().containsKey(Perk.Group.LASER) && !fluids.ether.isEmpty()) {
                List<FluidStack> etherFluids = new ArrayList<>();
                etherFluids.add(fluids.ether);
                StorageHelper.insertFluids(etherFluids, fluidHandlers);
            }
        }

    }
}
