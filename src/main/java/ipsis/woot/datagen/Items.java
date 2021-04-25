package ipsis.woot.datagen;


import ipsis.woot.Woot;
import ipsis.woot.modules.anvil.AnvilSetup;
import ipsis.woot.modules.debug.DebugSetup;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.fluidconvertor.FluidConvertorSetup;
import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.modules.layout.LayoutSetup;
import ipsis.woot.modules.oracle.OracleSetup;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import ipsis.woot.util.oss.BaseItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Items extends BaseItemModelProvider {

    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Woot.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        itemHandheld(AnvilSetup.HAMMER_ITEM.get(), "item/hammer");
        itemGenerated(AnvilSetup.PLATE_DIE_ITEM.get(), "item/plate_die");
        itemGenerated(AnvilSetup.SHARD_DIE_ITEM.get(), "item/shard_die");
        itemGenerated(AnvilSetup.DYE_DIE_ITEM.get(), "item/dye_die");
        parentedBlock(AnvilSetup.ANVIL_BLOCK.get(), "block/anvil");

        parentedBlock(FactorySetup.HEART_BLOCK.get(), "block/heart");
        parentedBlock(FactorySetup.CONTROLLER_BLOCK.get(), "block/controller");
        parentedBlock(FactorySetup.FACTORY_A_BLOCK.get(), "block/factory_a");
        parentedBlock(FactorySetup.FACTORY_B_BLOCK.get(), "block/factory_b");
        parentedBlock(FactorySetup.FACTORY_C_BLOCK.get(), "block/factory_c");
        parentedBlock(FactorySetup.FACTORY_D_BLOCK.get(), "block/factory_d");
        parentedBlock(FactorySetup.FACTORY_E_BLOCK.get(), "block/factory_e");
        parentedBlock(FactorySetup.CAP_A_BLOCK.get(), "block/cap_a");
        parentedBlock(FactorySetup.CAP_B_BLOCK.get(), "block/cap_b");
        parentedBlock(FactorySetup.CAP_C_BLOCK.get(), "block/cap_c");
        parentedBlock(FactorySetup.CAP_D_BLOCK.get(), "block/cap_d");
        parentedBlock(FactorySetup.FACTORY_CONNECT_BLOCK.get(), "block/factory_connect");
        parentedBlock(FactorySetup.FACTORY_CTR_BASE_PRI_BLOCK.get(), "block/factory_ctr_base_pri");
        parentedBlock(FactorySetup.FACTORY_CTR_BASE_SEC_BLOCK.get(), "block/factory_ctr_base_sec");
        parentedBlock(FactorySetup.IMPORT_BLOCK.get(), "block/import");
        parentedBlock(FactorySetup.EXPORT_BLOCK.get(), "block/export");
        parentedBlock(FactorySetup.FACTORY_UPGRADE_BLOCK.get(), "block/factory_upgrade");
        parentedBlock(FactorySetup.CELL_1_BLOCK.get(), "block/cell_1");
        parentedBlock(FactorySetup.CELL_2_BLOCK.get(), "block/cell_2");
        parentedBlock(FactorySetup.CELL_3_BLOCK.get(), "block/cell_3");
        parentedBlock(FactorySetup.CELL_4_BLOCK.get(), "block/cell_4");
        parentedBlock(FactorySetup.EXOTIC_A_BLOCK.get(), "block/exotic_a");
        parentedBlock(FactorySetup.EXOTIC_B_BLOCK.get(), "block/exotic_b");
        parentedBlock(FactorySetup.EXOTIC_C_BLOCK.get(), "block/exotic_c");
        parentedBlock(FactorySetup.EXOTIC_D_BLOCK.get(), "block/exotic_d");
        parentedBlock(FactorySetup.EXOTIC_E_BLOCK.get(), "block/exotic_e");
        itemGenerated(FactorySetup.EFFICIENCY_1_ITEM.get(), "item/efficiency_1");
        itemGenerated(FactorySetup.EFFICIENCY_2_ITEM.get(), "item/efficiency_2");
        itemGenerated(FactorySetup.EFFICIENCY_3_ITEM.get(), "item/efficiency_3");
        itemGenerated(FactorySetup.LOOTING_1_ITEM.get(), "item/looting_1");
        itemGenerated(FactorySetup.LOOTING_2_ITEM.get(), "item/looting_2");
        itemGenerated(FactorySetup.LOOTING_3_ITEM.get(), "item/looting_3");
        itemGenerated(FactorySetup.MASS_1_ITEM.get(), "item/mass_1");
        itemGenerated(FactorySetup.MASS_2_ITEM.get(), "item/mass_2");
        itemGenerated(FactorySetup.MASS_3_ITEM.get(), "item/mass_3");
        itemGenerated(FactorySetup.RATE_1_ITEM.get(), "item/rate_1");
        itemGenerated(FactorySetup.RATE_2_ITEM.get(), "item/rate_2");
        itemGenerated(FactorySetup.RATE_3_ITEM.get(), "item/rate_3");
        itemGenerated(FactorySetup.TIER_SHARD_1_ITEM.get(), "item/tier_shard_1");
        itemGenerated(FactorySetup.TIER_SHARD_2_ITEM.get(), "item/tier_shard_2");
        itemGenerated(FactorySetup.TIER_SHARD_3_ITEM.get(), "item/tier_shard_3");
        itemGenerated(FactorySetup.XP_1_ITEM.get(), "item/xp_1");
        itemGenerated(FactorySetup.XP_2_ITEM.get(), "item/xp_2");
        itemGenerated(FactorySetup.XP_3_ITEM.get(), "item/xp_3");
        itemGenerated(FactorySetup.HEADLESS_1_ITEM.get(), "item/headless_1");
        itemGenerated(FactorySetup.HEADLESS_2_ITEM.get(), "item/headless_2");
        itemGenerated(FactorySetup.HEADLESS_3_ITEM.get(), "item/headless_3");
        itemGenerated(FactorySetup.SLAUGHTER_1_ITEM.get(), "item/slaughter_1");
        itemGenerated(FactorySetup.SLAUGHTER_2_ITEM.get(), "item/slaughter_2");
        itemGenerated(FactorySetup.SLAUGHTER_3_ITEM.get(), "item/slaughter_3");
        itemGenerated(FactorySetup.CRUSHER_1_ITEM.get(), "item/crusher_1");
        itemGenerated(FactorySetup.CRUSHER_2_ITEM.get(), "item/crusher_2");
        itemGenerated(FactorySetup.CRUSHER_3_ITEM.get(), "item/crusher_3");
        itemGenerated(FactorySetup.LASER_1_ITEM.get(), "item/laser_1");
        itemGenerated(FactorySetup.LASER_2_ITEM.get(), "item/laser_2");
        itemGenerated(FactorySetup.LASER_3_ITEM.get(), "item/laser_3");
        itemGenerated(FactorySetup.FLAYED_1_ITEM.get(), "item/flayed_1");
        itemGenerated(FactorySetup.FLAYED_2_ITEM.get(), "item/flayed_2");
        itemGenerated(FactorySetup.FLAYED_3_ITEM.get(), "item/flayed_3");
        itemGenerated(FactorySetup.MOB_SHARD_ITEM.get(), "item/mobshard");
        itemGenerated(FactorySetup.XP_SHARD_ITEM.get(), "item/xpshard");
        itemGenerated(FactorySetup.XP_SPLINTER_ITEM.get(), "item/xpsplinter");

        parentedBlock(FluidConvertorSetup.FLUID_CONVERTOR_BLOCK.get(), "block/fluidconvertor");

        itemGenerated(GenericSetup.SI_INGOT_ITEM.get(), "item/si_ingot");
        itemGenerated(GenericSetup.SI_DUST_ITEM.get(), "item/si_dust");
        itemGenerated(GenericSetup.SI_PLATE_ITEM.get(), "item/si_plate");
        itemGenerated(GenericSetup.PRISM_ITEM.get(), "item/prism");
        itemGenerated(GenericSetup.ENCH_PLATE_1.get(), "item/ench_plate_1");
        itemGenerated(GenericSetup.ENCH_PLATE_2.get(), "item/ench_plate_2");
        itemGenerated(GenericSetup.ENCH_PLATE_3.get(), "item/ench_plate_3");
        itemGenerated(GenericSetup.T1_SHARD_ITEM.get(), "item/t1shard");
        itemGenerated(GenericSetup.T2_SHARD_ITEM.get(), "item/t2shard");
        itemGenerated(GenericSetup.T3_SHARD_ITEM.get(), "item/t3shard");
        itemGenerated(GenericSetup.MACHINE_CASING_ITEM.get(), "item/machine_casing");

        parentedBlock(InfuserSetup.INFUSER_BLOCK.get(), "block/infuser");
        itemGenerated(InfuserSetup.WHITE_DYE_PLATE_ITEM.get(), "item/dyeplate");
        itemGenerated(InfuserSetup.ORANGE_DYE_PLATE_ITEM.get(), "item/dyeplate");
        itemGenerated(InfuserSetup.MAGENTA_DYE_PLATE_ITEM.get(), "item/dyeplate");
        itemGenerated(InfuserSetup.LIGHT_BLUE_DYE_PLATE_ITEM.get(), "item/dyeplate");
        itemGenerated(InfuserSetup.YELLOW_DYE_PLATE_ITEM.get(), "item/dyeplate");
        itemGenerated(InfuserSetup.LIME_DYE_PLATE_ITEM.get(), "item/dyeplate");
        itemGenerated(InfuserSetup.PINK_DYE_PLATE_ITEM.get(), "item/dyeplate");
        itemGenerated(InfuserSetup.GRAY_DYE_PLATE_ITEM.get(), "item/dyeplate");
        itemGenerated(InfuserSetup.LIGHT_GRAY_DYE_PLATE_ITEM.get(), "item/dyeplate");
        itemGenerated(InfuserSetup.CYAN_DYE_PLATE_ITEM.get(), "item/dyeplate");
        itemGenerated(InfuserSetup.PURPLE_DYE_PLATE_ITEM.get(), "item/dyeplate");
        itemGenerated(InfuserSetup.BLUE_DYE_PLATE_ITEM.get(), "item/dyeplate");
        itemGenerated(InfuserSetup.BROWN_DYE_PLATE_ITEM.get(), "item/dyeplate");
        itemGenerated(InfuserSetup.GREEN_DYE_PLATE_ITEM.get(), "item/dyeplate");
        itemGenerated(InfuserSetup.RED_DYE_PLATE_ITEM.get(), "item/dyeplate");
        itemGenerated(InfuserSetup.BLACK_DYE_PLATE_ITEM.get(), "item/dyeplate");

        itemGenerated(InfuserSetup.WHITE_DYE_CASING_ITEM.get(), "item/dyecasing");
        itemGenerated(InfuserSetup.ORANGE_DYE_CASING_ITEM.get(), "item/dyecasing");
        itemGenerated(InfuserSetup.MAGENTA_DYE_CASING_ITEM.get(), "item/dyecasing");
        itemGenerated(InfuserSetup.LIGHT_BLUE_DYE_CASING_ITEM.get(), "item/dyecasing");
        itemGenerated(InfuserSetup.YELLOW_DYE_CASING_ITEM.get(), "item/dyecasing");
        itemGenerated(InfuserSetup.LIME_DYE_CASING_ITEM.get(), "item/dyecasing");
        itemGenerated(InfuserSetup.PINK_DYE_CASING_ITEM.get(), "item/dyecasing");
        itemGenerated(InfuserSetup.GRAY_DYE_CASING_ITEM.get(), "item/dyecasing");
        itemGenerated(InfuserSetup.LIGHT_GRAY_DYE_CASING_ITEM.get(), "item/dyecasing");
        itemGenerated(InfuserSetup.CYAN_DYE_CASING_ITEM.get(), "item/dyecasing");
        itemGenerated(InfuserSetup.PURPLE_DYE_CASING_ITEM.get(), "item/dyecasing");
        itemGenerated(InfuserSetup.BLUE_DYE_CASING_ITEM.get(), "item/dyecasing");
        itemGenerated(InfuserSetup.BROWN_DYE_CASING_ITEM.get(), "item/dyecasing");
        itemGenerated(InfuserSetup.GREEN_DYE_CASING_ITEM.get(), "item/dyecasing");
        itemGenerated(InfuserSetup.RED_DYE_CASING_ITEM.get(), "item/dyecasing");
        itemGenerated(InfuserSetup.BLACK_DYE_CASING_ITEM.get(), "item/dyecasing");

        parentedBlock(LayoutSetup.LAYOUT_BLOCK.get(), "block/layout");
        itemHandheld(LayoutSetup.INTERN_ITEM.get(), "item/intern");

        parentedBlock(OracleSetup.ORACLE_BLOCK.get(), "block/oracle");

        parentedBlock(SqueezerSetup.SQUEEZER_BLOCK.get(), "block/squeezer");
        parentedBlock(SqueezerSetup.ENCHANT_SQUEEZER_BLOCK.get(), "block/enchsqueezer");

        itemHandheld(DebugSetup.DEBUG_ITEM.get(), "item/debug");
        parentedBlock(DebugSetup.CREATIVE_CONATUS_BLOCK.get(), "block/creative_conatus");
        parentedBlock(DebugSetup.CREATIVE_POWER_BLOCK.get(), "block/creative_power");
        parentedBlock(DebugSetup.DEBUG_TANK_BLOCK.get(), "block/debug_tank");
    }

    @Override
    public String getName() {
        return "Woot Item Models";
    }
}
