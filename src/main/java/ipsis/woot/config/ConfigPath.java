package ipsis.woot.config;

public class ConfigPath {

    public static final class Anvil {
        public static final String CATEGORY = "anvil";
        public static final String PARTICLES_TAG = "particles";
    }

    public static final class Common {
        public static final String TANK_CAPACITY_TAG = "tankCapacity";
        public static final String TANK_RX_TAG = "tankRxPerTick";
        public static final String INTERNAL_TANK_CAPACITY_TAG = "internalTankCapacity";
        public static final String INPUT_TANK_CAPACITY_TAG = "inputTankCapacity";
        public static final String ENERGY_CAPACITY_TAG = "energyCapacity";
        public static final String ENERGY_RX_TAG = "energyRxPerTick";
        public static final String ENERGY_USE_PER_TICK_TAG = "energyUsePerTick";

        public static final String TANK_CAPACITY_COMMENT = "Tank capacity (mb)";
        public static final String TANK_RX_COMMENT = "Tank max transfer rate (mb/t per side)";
        public static final String INTERNAL_TANK_CAPACITY_COMMENT = "Internal tank capacity (mb)";
        public static final String INPUT_TANK_CAPACITY_COMMENT = "Input tank capacity (mb)";
        public static final String ENERGY_CAPACITY_COMMENT = "Energy capacity (mb)";
        public static final String ENERGY_RX_COMMENT = "Maximum energy that can be received (RF/t)";
        public static final String ENERGY_USE_PER_TICK_COMMENT = "Energy used per tick (RF)";
    }

    public static final class Commands {
        public static final String CATEGORY = "commands";
        public static final String COMMAND_LEVEL_SIM_LEARN = "simLearnLevel";
        public static final String COMMAND_LEVEL_SIM_DUMP = "simDumpLevel";
        public static final String COMMAND_LEVEL_SIM_FLUSH = "simFlushLevel";
        public static final String COMMAND_LEVEL_SIM_STATUS = "simStatusLevel";
        public static final String COMMAND_LEVEL_SIM_ROLL_DROPS = "simRollDropsLevel";
        public static final String COMMAND_LEVEL_GIVE= "giveControllerLevel";
    }

    public static final class Squeezer {
        public static final String CATEGORY = "squeezer";
        public static final String CATEGORY_DYE_SQUEEZER = "dye";
        public static final String CATEGORY_ENCHANT_SQUEEZER = "enchant";

        public static final String ENCH_SQUEEZER_LVL_1_ENCHANT_MB_TAG = "enchantAmountLevel1";
        public static final String ENCH_SQUEEZER_LVL_2_ENCHANT_MB_TAG = "enchantAmountLevel2";
        public static final String ENCH_SQUEEZER_LVL_3_ENCHANT_MB_TAG = "enchantAmountLevel3";
        public static final String ENCH_SQUEEZER_LVL_4_ENCHANT_MB_TAG = "enchantAmountLevel4";
        public static final String ENCH_SQUEEZER_LVL_5_ENCHANT_MB_TAG = "enchantAmountLevel5";
        public static final String ENCH_SQUEEZER_EXTRA_ENCHANT_MB_TAG = "enchantAmountPerLevel";

        public static final String ENCH_SQUEEZER_LVL_1_ENERGY_COST_TAG = "enchantEnergyLevel1";
        public static final String ENCH_SQUEEZER_LVL_2_ENERGY_COST_TAG = "enchantEnergyLevel2";
        public static final String ENCH_SQUEEZER_LVL_3_ENERGY_COST_TAG = "enchantEnergyLevel3";
        public static final String ENCH_SQUEEZER_LVL_4_ENERGY_COST_TAG = "enchantEnergyLevel4";
        public static final String ENCH_SQUEEZER_LVL_5_ENERGY_COST_TAG = "enchantEnergyLevel5";
        public static final String ENCH_SQUEEZER_EXTRA_ENERGY_COST_TAG = "enchantEnergyPerLevel";
    }

    public static final class Infuser {
        public static final String CATEGORY = "infuser";
    }

    public static final class Layout {
        public static final String CATEGORY = "layout";
        public static final String SIMPLE_TAG = "simple";
        public static final String RENDER_OPACITY_TAG = "renderOpacity";
        public static final String RENDER_SIZE_TAG = "renderSize";
    }

    public static final class FluidConvertor {
        public static final String CATEGORY = "fluid_convertor";
    }

    public static final class Simulation {
        public static final String CATEGORY = "simulation";
        public static final String TICKS_TAG = "tickRate";
        public static final String MOB_SAMPLE_SIZE_TAG = "mobSampleSize";
        public static final String TICKS_PER_SIM_TICK_TAG = "simTickRate";
        public static final String CELLS_PER_SIM_TICK_TAG = "cellsPerSimTick";
    }

    public static final class Policy {
        public static final String CATEGORY = "policy";
        public static final String CATEGORY_BLACKLIST = "blacklist";
        public static final String CATEGORY_MOB = "mob";
        public static final String CATEGORY_PERK = "perk";

        public static final String CAPTURE_BLACKLIST_FULL_MOD_TAG = "captureMod";
        public static final String CAPTURE_BLACKLIST_ENTITY_TAG = "captureEntity";
        public static final String LEARN_BLACKLIST_FULL_MOD_TAG = "learnMod";
        public static final String LEARN_BLACKLIST_ITEM_TAG = "learnItem";
        public static final String GENERATE_BLACKLIST_FULL_MOD_TAG = "generateMod";
        public static final String GENERATE_BLACKLIST_ITEM_TAG = "generateItem";
        public static final String SHARD_BLACKLIST_FULL_MOD_TAG = "shardMod";
        public static final String SHARD_BLACKLIST_ENTITY_TAG = "shardEntity";

        public static final String CUSTOM_DROPS_ONLY_TAG = "customDropsOnly";
        public static final String MOB_MASS_COUNT_TAG = "massCount";
        public static final String MOB_SPAWN_TICKS_TAG = "spawnTicks";
        public static final String MOB_HEALTH_TAG = "health";
        public static final String MOB_UNITS_PER_HEALTH_TAG = "unitsPerHealth";
        public static final String MOB_XP_TAG = "xp";
        public static final String MOB_TIER_TAG = "tier";
        public static final String MOB_SHARD_KILLS_TAG = "shardKills";
        public static final String MOB_FIXED_COST_TAG = "fixedCost";
        public static final String MOB_PERK_EFFICIENCY_1_TAG = "efficiency1";
        public static final String MOB_PERK_EFFICIENCY_2_TAG = "efficiency2";
        public static final String MOB_PERK_EFFICIENCY_3_TAG = "efficiency3";
        public static final String MOB_PERK_MASS_1_TAG = "mass1";
        public static final String MOB_PERK_MASS_2_TAG = "mass2";
        public static final String MOB_PERK_MASS_3_TAG = "mass3";
        public static final String MOB_PERK_RATE_1_TAG = "rate1";
        public static final String MOB_PERK_RATE_2_TAG = "rate2";
        public static final String MOB_PERK_RATE_3_TAG = "rate3";
        public static final String MOB_PERK_XP_1_TAG = "xp1";
        public static final String MOB_PERK_XP_2_TAG = "xp2";
        public static final String MOB_PERK_XP_3_TAG = "xp3";
        public static final String MOB_PERK_HEADLESS_1_TAG = "headless1";
        public static final String MOB_PERK_HEADLESS_2_TAG = "headless2";
        public static final String MOB_PERK_HEADLESS_3_TAG = "headless3";
        public static final String MOB_PERK_HEADLESS_SKULLS_TAG = "headlessSkulls";
    }

    public static final class Factory {
        public static final String CATEGORY = "factory";
        public static final String CATEGORY_GENERAL = "general";
        public static final String CATEGORY_BASE = "base";
        public static final String CATEGORY_CELL1 = "basicCell";
        public static final String CATEGORY_CELL2 = "advancedCell";
        public static final String CATEGORY_CELL3 = "premiumCell";
        public static final String CATEGORY_CELL4 = "ultimateCell";
        public static final String CATEGORY_PERKS = "perks";
        public static final String CATEGORY_EFFICIENCY = "efficiency";
        public static final String CATEGORY_MASS = "mass";
        public static final String CATEGORY_RATE = "rate";
        public static final String CATEGORY_TIER_SHARD = "tierShard";
        public static final String CATEGORY_HEADLESS = "headless";
        public static final String CATEGORY_SLAUGHTER = "slaughter";
        public static final String CATEGORY_CRUSHER = "crusher";
        public static final String CATEGORY_LASER = "laser";
        public static final String CATEGORY_FLAYED = "flayed";
        public static final String CATEGORY_XP = "xp";
        public static final String CATEGORY_EXOTIC = "exotic";

        public static final String TICK_ACCELERATION_TAG = "tickAcceleration";

        public static final String TIER_1_MAX_HEALTH_TAG = "t1MaxHealth";
        public static final String TIER_2_MAX_HEALTH_TAG = "t2MaxHealth";
        public static final String TIER_3_MAX_HEALTH_TAG = "t3MaxHealth";
        public static final String TIER_4_MAX_HEALTH_TAG = "t4MaxHealth";
        public static final String TIER_5_MAX_HEALTH_TAG = "t5MaxHealth";

        public static final String TIER_SHARD_T1_DROP_TAG = "t1DropChance";
        public static final String TIER_SHARD_T2_DROP_TAG = "t2DropChance";
        public static final String TIER_SHARD_T3_DROP_TAG = "t3DropChance";
        public static final String TIER_SHARD_T4_DROP_TAG = "t4DropChance";
        public static final String TIER_SHARD_T5_DROP_TAG = "t5DropChance";
        public static final String TIER_SHARD_T1_WEIGHTS_TAG = "t1DropWeights";
        public static final String TIER_SHARD_T2_WEIGHTS_TAG = "t2DropWeights";
        public static final String TIER_SHARD_T3_WEIGHTS_TAG = "t3DropWeights";
        public static final String TIER_SHARD_T4_WEIGHTS_TAG = "t4DropWeights";
        public static final String TIER_SHARD_T5_WEIGHTS_TAG = "t5DropWeights";

        public static final String TIER_SHARD_L1_ROLLS_TAG = "l1Rolls";
        public static final String TIER_SHARD_L2_ROLLS_TAG = "l2Rolls";
        public static final String TIER_SHARD_L3_ROLLS_TAG = "l3Rolls";

        public static final String EXOTIC_A_PERCENTAGE = "exoticAPercentage";
        public static final String EXOTIC_B_PERCENTAGE = "exoticBPercentage";
        public static final String EXOTIC_C_PERCENTAGE = "exoticCPercentage";
        public static final String EXOTIC_D_TICKS = "exoticDTicks";
        public static final String EXOTIC_E_COUNT = "exoticECount";
        public static final String EXOTIC_TAG = "exotic";

        // These support config override
        public static final String MASS_COUNT_TAG = "massCount";
        public static final String SPAWN_TICKS_TAG = "spawnTicks";
        public static final String HEALTH_TAG = "health";
        public static final String XP_TAG = "xp";
        public static final String MB_PER_HEALTH_TAG = "mbPerHealth";
        public static final String FIXED_TIER_TAG = "fixedTier";
        public static final String SHARD_KILLS_TAG = "shardKills";
        public static final String FIXED_COST_TAG = "fixedCost";
        public static final String EFFICIENCY_1_TAG = "l1Reduction";
        public static final String EFFICIENCY_2_TAG = "l2Reduction";
        public static final String EFFICIENCY_3_TAG = "l3Reduction";
        public static final String MASS_1_TAG = "l1MobCount";
        public static final String MASS_2_TAG = "l2MobCount";
        public static final String MASS_3_TAG = "l3MobCount";
        public static final String RATE_1_TAG = "l1Reduction";
        public static final String RATE_2_TAG = "l2Reduction";
        public static final String RATE_3_TAG = "l3Reduction";
        public static final String XP_1_TAG = "l1Generate";
        public static final String XP_2_TAG = "l2Generate";
        public static final String XP_3_TAG = "l3Generate";
        public static final String HEADLESS_1_TAG = "l1HeadlessChance";
        public static final String HEADLESS_2_TAG = "l2HeadlessChance";
        public static final String HEADLESS_3_TAG = "l3HeadlessChance";
        public static final String SLAUGHTER_1_TAG = "l1SlaughterAmount";
        public static final String SLAUGHTER_2_TAG = "l2SlaughterAmount";
        public static final String SLAUGHTER_3_TAG = "l3SlaughterAmount";
        public static final String CRUSHER_1_TAG = "l1CrusherAmount";
        public static final String CRUSHER_2_TAG = "l2CrusherAmount";
        public static final String CRUSHER_3_TAG = "l3CrusherAmount";
        public static final String LASER_1_TAG = "l1LaserAmount";
        public static final String LASER_2_TAG = "l2LaserAmount";
        public static final String LASER_3_TAG = "l3LaserAmount";
        public static final String FLAYED_1_TAG = "l1FlayedAmount";
        public static final String FLAYED_2_TAG = "l2FlayedAmount";
        public static final String FLAYED_3_TAG = "l3FlayedAmount";

    }
}
