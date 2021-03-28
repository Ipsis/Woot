package ipsis.woot.modules.factory;

import ipsis.woot.Woot;
import ipsis.woot.config.Config;
import ipsis.woot.config.ConfigOverride;
import ipsis.woot.modules.factory.blocks.*;
import ipsis.woot.modules.factory.layout.Layout;
import ipsis.woot.modules.factory.layout.PatternBlock;
import ipsis.woot.modules.factory.perks.Perk;
import ipsis.woot.simulator.spawning.SpawnController;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.helper.MathHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.*;

/**
 * Fully formed factory and its configuration
 * There will always be at least one mob which is in the primary slot
 */
public class FormedSetup {

    private Tier tier = Tier.TIER_1;
    private List<FakeMob> controllerMobs = new ArrayList<>();
    private HashMap<Perk.Group, Integer> perks = new HashMap<>();
    private HashMap<FakeMob, MobParam> mobParams = new HashMap<>();
    private World world;
    private BlockPos importPos = BlockPos.ZERO;
    private BlockPos exportPos = BlockPos.ZERO;
    private BlockPos cellPos = BlockPos.ZERO;
    private int cellCapacity = 0;
    private int cellType = 0;
    private double shardDropChance = 0.0F;
    private int[] shardDropWeights = new int[]{ 0, 0, 0 };
    private int perkTierShardValue = 0;
    private Exotic exotic = Exotic.NONE;
    private Boolean perkCapped = false;

    private FormedSetup() {}
    private FormedSetup(World world, Tier tier) {
        this.world = world;
        this.tier = tier;
    }

    public List<FakeMob> getAllMobs() { return Collections.unmodifiableList(controllerMobs); }
    public Map<FakeMob, MobParam> getAllMobParams() { return Collections.unmodifiableMap(mobParams); }
    public Map<Perk.Group, Integer> getAllPerks() { return Collections.unmodifiableMap(perks); }
    public LazyOptional<IFluidHandler> getCellFluidHandler() {
        if (world != null) {
            TileEntity te = world.getTileEntity(cellPos);
            return te instanceof TileEntity ? te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) : LazyOptional.empty();
        }
        return LazyOptional.empty();
    }

    public boolean isPerkCapped() { return this.perkCapped; }
    public Exotic getExotic() { return this.exotic; }
    public boolean hasFluidIngredientExotic() { return this.exotic == Exotic.EXOTIC_A; }
    public boolean hasItemIngredientExotic() { return this.exotic == Exotic.EXOTIC_B; }
    public boolean hasConatusExotic() { return this.exotic == Exotic.EXOTIC_C; }
    public boolean hasSpawnTimExotic() { return this.exotic == Exotic.EXOTIC_D; }
    public boolean hasMassExotic() { return this.exotic == Exotic.EXOTIC_E; }

    public double getShardDropChance() { return this.shardDropChance; }
    public int getBasicShardWeight() { return shardDropWeights[0]; }
    public int getAdvancedShardWeight() { return shardDropWeights[1]; }
    public int getEliteShardWeight() { return shardDropWeights[2]; }
    public int getPerkTierShardValue() { return perkTierShardValue; }

    public BlockPos getImportPos() { return this.importPos; }
    public BlockPos getExportPos() { return this.exportPos; }
    public World getWorld() { return this.world; }

    public Tier getTier() { return this.tier; }
    public int getCellCapacity() { return this.cellCapacity; }
    public int getCellType() { return this.cellType; }
    public int getCellFluidAmount() {
        LazyOptional<IFluidHandler> hdlr = getCellFluidHandler();
        if (hdlr.isPresent()) {
            IFluidHandler iFluidHandler = hdlr.orElseThrow(NullPointerException::new);
            return iFluidHandler.getFluidInTank(0).getAmount();
        }
        return 0;
    }
    public int getLootingLevel() { return MathHelper.clampLooting(perks.getOrDefault(Perk.Group.LOOTING, 0)); }

    public List<LazyOptional<IItemHandler>> getImportHandlers() {
        List<LazyOptional<IItemHandler>> handlers = new ArrayList<>();
        for (Direction facing : Direction.values()) {
            if (!world.isBlockLoaded(importPos.offset(facing)))
                continue;
            TileEntity te = world.getTileEntity(importPos.offset(facing));
            if (!(te instanceof TileEntity))
                continue;

            handlers.add(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite()));
        }
        return handlers;
    }

    public List<LazyOptional<IFluidHandler>> getImportFluidHandlers() {
        List<LazyOptional<IFluidHandler>> handlers = new ArrayList<>();
        for (Direction facing : Direction.values()) {
            if (!world.isBlockLoaded(importPos.offset(facing)))
                continue;
            TileEntity te = world.getTileEntity(importPos.offset(facing));
            if (!(te instanceof TileEntity))
                continue;

            handlers.add(te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite()));
        }
        return handlers;
    }

    public List<LazyOptional<IFluidHandler>> getExportFluidHandlers() {
        List<LazyOptional<IFluidHandler>> handlers = new ArrayList<>();
        for (Direction facing : Direction.values()) {
            if (!world.isBlockLoaded(exportPos.offset(facing)))
                continue;
            TileEntity te = world.getTileEntity(exportPos.offset(facing));
            if (!(te instanceof TileEntity))
                continue;

            handlers.add(te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite()));
        }
        return handlers;
    }

    public List<LazyOptional<IItemHandler>> getExportHandlers() {
        List<LazyOptional<IItemHandler>> handlers = new ArrayList<>();
        for (Direction facing : Direction.values()) {
            if (!world.isBlockLoaded(exportPos.offset(facing)))
                continue;
            TileEntity te = world.getTileEntity(exportPos.offset(facing));
            if (!(te instanceof TileEntity))
                continue;

            handlers.add(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite()));
        }
        return handlers;
    }

    public int getMaxSpawnTime() {
        int max = 0;
        for (MobParam mobParam : mobParams.values()) {
            if (mobParam.baseSpawnTicks > max)
                max = mobParam.baseSpawnTicks;
        }
        return max;
    }

    public int getMinRateValue() {

        boolean hasPerk = false;
        int min = Integer.MAX_VALUE;
        for (MobParam mobParam : mobParams.values()) {
            if (mobParam.hasPerkRateValue() && mobParam.getPerkRateValue() < min) {
                min = mobParam.getPerkRateValue();
                hasPerk = true;
            }
        }

        // If there is no perk present then the rate reduction will be 0
        // Otherwise it is the smalled reduction across all the mobs
        return hasPerk ? min : 0;
    }

    private void setupMobParams() {
        for (FakeMob fakeMob : controllerMobs) {
            MobParam param = new MobParam();
            param.baseSpawnTicks = Config.OVERRIDE.getIntegerOrDefault(fakeMob, ConfigOverride.OverrideKey.SPAWN_TICKS);
            param.baseMassCount = Config.OVERRIDE.getIntegerOrDefault(fakeMob, ConfigOverride.OverrideKey.MASS_COUNT);

            if (Config.OVERRIDE.hasOverride(fakeMob, ConfigOverride.OverrideKey.FIXED_COST)) {
                param.baseFluidCost = Config.OVERRIDE.getInteger(fakeMob, ConfigOverride.OverrideKey.FIXED_COST);
            } else {
                int healthPoints = SpawnController.get().getMobHealth(fakeMob, world);
                int unitsPerHealthPoint = Config.OVERRIDE.getIntegerOrDefault(fakeMob, ConfigOverride.OverrideKey.UNITS_PER_HEALTH);
                param.baseFluidCost = unitsPerHealthPoint * healthPoints;
            }

            // Efficiency
            if (perks.containsKey(Perk.Group.EFFICIENCY)) {
                int perkLevel = perks.getOrDefault(Perk.Group.EFFICIENCY, 0);
                if (perkLevel > 0)
                    param.setPerkEfficiencyValue(Config.OVERRIDE.getIntegerOrDefault(fakeMob,
                            Config.OVERRIDE.getKeyByPerk(Perk.Group.EFFICIENCY, perkLevel)));
            }

            // Mass
            if (perks.containsKey(Perk.Group.MASS)) {
                int perkLevel = perks.getOrDefault(Perk.Group.MASS, 0);
                if (perkLevel > 0)
                    param.setPerkMassValue(Config.OVERRIDE.getIntegerOrDefault(fakeMob,
                            Config.OVERRIDE.getKeyByPerk(Perk.Group.MASS, perkLevel)));
            }

            // Rate
            if (perks.containsKey(Perk.Group.RATE)) {
                int perkLevel = perks.getOrDefault(Perk.Group.RATE, 0);
                if (perkLevel > 0)
                    param.setPerkRateValue(Config.OVERRIDE.getIntegerOrDefault(fakeMob,
                            Config.OVERRIDE.getKeyByPerk(Perk.Group.RATE, perkLevel)));
            }

            // Xp
            if (perks.containsKey(Perk.Group.XP)) {
                int perkLevel = perks.getOrDefault(Perk.Group.XP, 0);
                if (perkLevel > 0)
                    param.setPerkXpValue(Config.OVERRIDE.getIntegerOrDefault(fakeMob,
                            Config.OVERRIDE.getKeyByPerk(Perk.Group.XP, perkLevel)));
            }

            // Headless
            if (perks.containsKey(Perk.Group.HEADLESS)) {
                int perkLevel = perks.getOrDefault(Perk.Group.HEADLESS, 0);
                if (perkLevel > 0)
                    param.setPerkHeadlessValue(Config.OVERRIDE.getIntegerOrDefault(fakeMob,
                            Config.OVERRIDE.getKeyByPerk(Perk.Group.HEADLESS, perkLevel)));
            }

            mobParams.put(fakeMob, param);
        }
    }

    @Override
    public String toString() {
        return "FormedSetup{" +
                "tier=" + tier +
                ", controllerMobs=" + controllerMobs +
                ", perks=" + perks +
                ", world=" + world +
                ", importPos=" + importPos +
                ", exportPos=" + exportPos +
                ", cellPos=" + cellPos +
                ", cellCapacity=" + cellCapacity +
                ", exotic=" + exotic +
                '}';
    }

    public static FormedSetup createFromValidLayout(World world, Layout layout) {
        FormedSetup formedSetup = new FormedSetup(world, layout.getAbsolutePattern().getTier());

        // Mobs are already validated
        for (FakeMob fakeMob : layout.getAbsolutePattern().getMobs())
            formedSetup.controllerMobs.add(new FakeMob(fakeMob));

        for (PatternBlock pb : layout.getAbsolutePattern().getBlocks()) {
            if (pb.getFactoryComponent() == FactoryComponent.FACTORY_UPGRADE) {
                TileEntity te = world.getTileEntity(pb.getBlockPos());
                if (te instanceof UpgradeTileEntity) {
                    Perk perk = ((UpgradeTileEntity) te).getUpgrade(world.getBlockState(pb.getBlockPos()));
                    if (perk != Perk.EMPTY) {
                        Perk.Group group = Perk.getGroup(perk);
                        int perkLevel = Perk.getLevel(perk);
                        /**
                         * Tier 1,2 - level 1 upgrades only
                         * Tier 3 - level 1,2 upgrades only
                         * Tier 4+ - all upgrades
                         * You can still apply them but they don't get the same level if not a high enough tier
                         */
                        if ((formedSetup.tier == Tier.TIER_1 || formedSetup.tier == Tier.TIER_2) && perkLevel > 1) {
                            perkLevel = 1;
                            formedSetup.perkCapped = true;
                        } else if (formedSetup.tier == Tier.TIER_3 && perkLevel > 2) {
                            perkLevel = 2;
                            formedSetup.perkCapped = true;
                        }

                        Woot.setup.getLogger().debug("createFromValidLayout: adding perk {}/{}", group, perkLevel);
                        formedSetup.perks.put(group, perkLevel);

                        if (group == Perk.Group.TIER_SHARD) {
                            if (perkLevel == 1)
                                formedSetup.perkTierShardValue = FactoryConfiguration.TIER_SHARD_1.get();
                            else if (perkLevel == 2)
                                formedSetup.perkTierShardValue = FactoryConfiguration.TIER_SHARD_2.get();
                            else if (perkLevel == 3)
                                formedSetup.perkTierShardValue = FactoryConfiguration.TIER_SHARD_3.get();

                            if (formedSetup.getTier() == Tier.TIER_1) {
                                formedSetup.shardDropChance = FactoryConfiguration.T1_FARM_DROP_CHANCE.get();
                                formedSetup.shardDropWeights[0] = FactoryConfiguration.T1_FARM_DROP_SHARD_WEIGHTS.get().get(0);
                                formedSetup.shardDropWeights[1] = FactoryConfiguration.T1_FARM_DROP_SHARD_WEIGHTS.get().get(1);
                                formedSetup.shardDropWeights[2] = FactoryConfiguration.T1_FARM_DROP_SHARD_WEIGHTS.get().get(2);
                            } else if (formedSetup.getTier() == Tier.TIER_2) {
                                formedSetup.shardDropChance = FactoryConfiguration.T2_FARM_DROP_CHANCE.get();
                                formedSetup.shardDropWeights[0] = FactoryConfiguration.T2_FARM_DROP_SHARD_WEIGHTS.get().get(0);
                                formedSetup.shardDropWeights[1] = FactoryConfiguration.T2_FARM_DROP_SHARD_WEIGHTS.get().get(1);
                                formedSetup.shardDropWeights[2] = FactoryConfiguration.T2_FARM_DROP_SHARD_WEIGHTS.get().get(2);
                            } else if (formedSetup.getTier() == Tier.TIER_3) {
                                formedSetup.shardDropChance = FactoryConfiguration.T3_FARM_DROP_CHANCE.get();
                                formedSetup.shardDropWeights[0] = FactoryConfiguration.T3_FARM_DROP_SHARD_WEIGHTS.get().get(0);
                                formedSetup.shardDropWeights[1] = FactoryConfiguration.T3_FARM_DROP_SHARD_WEIGHTS.get().get(1);
                                formedSetup.shardDropWeights[2] = FactoryConfiguration.T3_FARM_DROP_SHARD_WEIGHTS.get().get(2);
                            } else if (formedSetup.getTier() == Tier.TIER_4) {
                                formedSetup.shardDropChance = FactoryConfiguration.T4_FARM_DROP_CHANCE.get();
                                formedSetup.shardDropWeights[0] = FactoryConfiguration.T4_FARM_DROP_SHARD_WEIGHTS.get().get(0);
                                formedSetup.shardDropWeights[1] = FactoryConfiguration.T4_FARM_DROP_SHARD_WEIGHTS.get().get(1);
                                formedSetup.shardDropWeights[2] = FactoryConfiguration.T4_FARM_DROP_SHARD_WEIGHTS.get().get(2);
                            } else if (formedSetup.getTier() == Tier.TIER_5) {
                                formedSetup.shardDropChance = FactoryConfiguration.T5_FARM_DROP_CHANCE.get();
                                formedSetup.shardDropWeights[0] = FactoryConfiguration.T5_FARM_DROP_SHARD_WEIGHTS.get().get(0);
                                formedSetup.shardDropWeights[1] = FactoryConfiguration.T5_FARM_DROP_SHARD_WEIGHTS.get().get(1);
                                formedSetup.shardDropWeights[2] = FactoryConfiguration.T5_FARM_DROP_SHARD_WEIGHTS.get().get(2);
                            }

                        }
                    }
                }
            } else if (pb.getFactoryComponent() == FactoryComponent.CELL) {
                formedSetup.cellPos = new BlockPos(pb.getBlockPos());
                TileEntity te = world.getTileEntity(pb.getBlockPos());
                if (te instanceof CellTileEntityBase) {
                    formedSetup.cellCapacity = ((CellTileEntityBase) te).getCapacity();
                    if (te instanceof Cell1TileEntity)
                        formedSetup.cellType = 0;
                    else if (te instanceof Cell2TileEntity)
                        formedSetup.cellType = 1;
                    else if (te instanceof Cell3TileEntity)
                        formedSetup.cellType = 2;
                    else if (te instanceof Cell4TileEntity)
                        formedSetup.cellType = 3;
                }
            } else if (pb.getFactoryComponent() == FactoryComponent.IMPORT) {
                formedSetup.importPos = new BlockPos(pb.getBlockPos());
            } else if (pb.getFactoryComponent() == FactoryComponent.EXPORT) {
                formedSetup.exportPos = new BlockPos(pb.getBlockPos());
            }
        }

        formedSetup.exotic = layout.getAbsolutePattern().getExotic();
        formedSetup.setupMobParams();
        return formedSetup;
    }
}
