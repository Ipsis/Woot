package ipsis.woot.datagen;

import ipsis.woot.Woot;
import ipsis.woot.modules.debug.DebugSetup;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.perks.Perk;
import ipsis.woot.modules.factory.blocks.UpgradeBlock;
import ipsis.woot.modules.fluidconvertor.FluidConvertorSetup;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.modules.layout.LayoutSetup;
import ipsis.woot.modules.oracle.OracleSetup;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import ipsis.woot.util.oss.BaseBlockStateProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Blocks extends BaseBlockStateProvider {

    public Blocks(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Woot.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {


        // Controller
        ModelFile controller0 = models().cubeBottomTop("controller", modLoc("block/controller"),
                modLoc("block/factory"), modLoc("block/factory"));
        BlockModelBuilder controller1 = models().withExistingParent("controller_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/controller"));
        VariantBlockStateBuilder builder = getVariantBuilder(FactorySetup.CONTROLLER_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(controller0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(controller1).addModel();

        // Cap A
        ModelFile cap0 = models().cubeBottomTop("cap_a", modLoc("block/cap_a"),
                modLoc("block/factory"), modLoc("block/factory"));
        BlockModelBuilder cap1 = models().withExistingParent("cap_a_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/cap_a"));
        builder = getVariantBuilder(FactorySetup.CAP_A_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(cap0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(cap1).addModel();

        // Cap B
        cap0 = models().cubeBottomTop("cap_b", modLoc("block/cap_b"),
                modLoc("block/factory"), modLoc("block/factory"));
        cap1 = models().withExistingParent("cap_b_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/cap_b"));
        builder = getVariantBuilder(FactorySetup.CAP_B_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(cap0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(cap1).addModel();

        // Cap C
        cap0 = models().cubeBottomTop("cap_c", modLoc("block/cap_c"),
                modLoc("block/factory"), modLoc("block/factory"));
        cap1 = models().withExistingParent("cap_c_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/cap_c"));
        builder = getVariantBuilder(FactorySetup.CAP_C_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(cap0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(cap1).addModel();

        // Cap D
        cap0 = models().cubeBottomTop("cap_d", modLoc("block/cap_d"),
                modLoc("block/factory"), modLoc("block/factory"));
        cap1 = models().withExistingParent("cap_d_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/cap_d"));
        builder = getVariantBuilder(FactorySetup.CAP_D_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(cap0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(cap1).addModel();

        // Factory A
        ModelFile factory0 = models().cubeBottomTop("factory_a", modLoc("block/factory_a"),
                modLoc("block/factory"), modLoc("block/factory"));
        BlockModelBuilder factory1 = models().withExistingParent("factory_a_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/factory_a"));
        builder = getVariantBuilder(FactorySetup.FACTORY_A_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(factory0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(factory1).addModel();

        // Factory B
        factory0 = models().cubeBottomTop("factory_b", modLoc("block/factory_b"),
                modLoc("block/factory"), modLoc("block/factory"));
        factory1 = models().withExistingParent("factory_b_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/factory_b"));
        builder = getVariantBuilder(FactorySetup.FACTORY_B_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(factory0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(factory1).addModel();

        // Factory C
        factory0 = models().cubeBottomTop("factory_c", modLoc("block/factory_c"),
                modLoc("block/factory"), modLoc("block/factory"));
        factory1 = models().withExistingParent("factory_c_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/factory_c"));
        builder = getVariantBuilder(FactorySetup.FACTORY_C_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(factory0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(factory1).addModel();

        // Factory D
        factory0 = models().cubeBottomTop("factory_d", modLoc("block/factory_d"),
                modLoc("block/factory"), modLoc("block/factory"));
        factory1 = models().withExistingParent("factory_d_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/factory_d"));
        builder = getVariantBuilder(FactorySetup.FACTORY_D_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(factory0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(factory1).addModel();

        // Factory E
        factory0 = models().cubeBottomTop("factory_e", modLoc("block/factory_e"),
                modLoc("block/factory"), modLoc("block/factory"));
        factory1 = models().withExistingParent("factory_e_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/factory_e"));
        builder = getVariantBuilder(FactorySetup.FACTORY_E_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(factory0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(factory1).addModel();

        // Factory Connector
        factory0 = models().cubeBottomTop("factory_connect", modLoc("block/factory_connect"),
                modLoc("block/factory"), modLoc("block/factory"));
        factory1 = models().withExistingParent("factory_connect_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/factory_connect"));
        builder = getVariantBuilder(FactorySetup.FACTORY_CONNECT_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(factory0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(factory1).addModel();

        // Factory Controller Base Pri
        factory0 = models().cubeBottomTop("factory_ctr_base_pri", modLoc("block/factory_ctr_base_pri"),
                modLoc("block/factory"), modLoc("block/factory"));
        factory1 = models().withExistingParent("factory_ctr_base_pri_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/factory_ctr_base_pri"));
        builder = getVariantBuilder(FactorySetup.FACTORY_CTR_BASE_PRI_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(factory0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(factory1).addModel();

        // Factory Controller Base Sec
        factory0 = models().cubeBottomTop("factory_ctr_base_sec", modLoc("block/factory_ctr_base_sec"),
                modLoc("block/factory"), modLoc("block/factory"));
        factory1 = models().withExistingParent("factory_ctr_base_sec_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/factory_ctr_base_sec"));
        builder = getVariantBuilder(FactorySetup.FACTORY_CTR_BASE_SEC_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(factory0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(factory1).addModel();

        // Factory Import
        factory0 = models().cubeBottomTop("import", modLoc("block/import"),
                modLoc("block/factory"), modLoc("block/factory"));
        factory1 = models().withExistingParent("import_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/import"));
        builder = getVariantBuilder(FactorySetup.IMPORT_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(factory0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(factory1).addModel();

        // Factory Export
        factory0 = models().cubeBottomTop("export", modLoc("block/export"),
                modLoc("block/factory"), modLoc("block/factory"));
        factory1 = models().withExistingParent("export_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/export"));
        builder = getVariantBuilder(FactorySetup.EXPORT_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(factory0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(factory1).addModel();


        // Cell 1
        ModelFile cell0 = models().cubeAll("cell_1", modLoc("block/cell_1"));
        BlockModelBuilder cell1 = models().withExistingParent("cell_1_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/cell_1"));
        builder = getVariantBuilder(FactorySetup.CELL_1_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(cell0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(cell0).addModel();

        // Cell 2
        cell0 = models().cubeAll("cell_2", modLoc("block/cell_2"));
        cell1 = models().withExistingParent("cell_2_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/cell_2"));
        builder = getVariantBuilder(FactorySetup.CELL_2_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(cell0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(cell0).addModel();

        // Cell 3
        cell0 = models().cubeAll("cell_3", modLoc("block/cell_3"));
        cell1 = models().withExistingParent("cell_3_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/cell_3"));
        builder = getVariantBuilder(FactorySetup.CELL_3_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(cell0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(cell0).addModel();

        // Cell 4
        cell0 = models().cubeAll("cell_4", modLoc("block/cell_4"));
        cell1 = models().withExistingParent("cell_4_alone", modLoc("block/small_cube_all"))
                .texture("all", modLoc("block/cell_4"));
        builder = getVariantBuilder(FactorySetup.CELL_4_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .modelForState().modelFile(cell0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .modelForState().modelFile(cell0).addModel();

        /*
        sideOnlyBlock(FactorySetup.FACTORY_UPGRADE_BLOCK.get(), "factory_upgrade", "block/factory_upgrade");
        */

        // Upgrade block
        ModelFile upgrade0 = models().cubeAll("factory_upgrade", modLoc("block/factory_upgrade"));
        builder = getVariantBuilder(FactorySetup.FACTORY_UPGRADE_BLOCK.get());
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.EMPTY)
                .modelForState().modelFile(upgrade0).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.EMPTY)
                .modelForState().modelFile(upgrade0).addModel();

        ModelFile upgrade1 = models().cubeAll("factory_upgrade_efficiency_1", modLoc("item/efficiency_1"));
        ModelFile upgrade2 = models().cubeAll("factory_upgrade_efficiency_2", modLoc("item/efficiency_2"));
        ModelFile upgrade3 = models().cubeAll("factory_upgrade_efficiency_3", modLoc("item/efficiency_3"));
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.EFFICIENCY_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.EFFICIENCY_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.EFFICIENCY_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.EFFICIENCY_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.EFFICIENCY_3)
                .modelForState().modelFile(upgrade3).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.EFFICIENCY_3)
                .modelForState().modelFile(upgrade3).addModel();

        upgrade1 = models().cubeAll("factory_upgrade_looting_1", modLoc("item/looting_1"));
        upgrade2 = models().cubeAll("factory_upgrade_looting_2", modLoc("item/looting_2"));
        upgrade3 = models().cubeAll("factory_upgrade_looting_3", modLoc("item/looting_3"));
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.LOOTING_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.LOOTING_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.LOOTING_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.LOOTING_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.LOOTING_3)
                .modelForState().modelFile(upgrade3).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.LOOTING_3)
                .modelForState().modelFile(upgrade3).addModel();

        upgrade1 = models().cubeAll("factory_upgrade_mass_1", modLoc("item/mass_1"));
        upgrade2 = models().cubeAll("factory_upgrade_mass_2", modLoc("item/mass_2"));
        upgrade3 = models().cubeAll("factory_upgrade_mass_3", modLoc("item/mass_3"));
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.MASS_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.MASS_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.MASS_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.MASS_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.MASS_3)
                .modelForState().modelFile(upgrade3).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.MASS_3)
                .modelForState().modelFile(upgrade3).addModel();

        upgrade1 = models().cubeAll("factory_upgrade_rate_1", modLoc("item/rate_1"));
        upgrade2 = models().cubeAll("factory_upgrade_rate_2", modLoc("item/rate_2"));
        upgrade3 = models().cubeAll("factory_upgrade_rate_3", modLoc("item/rate_3"));
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.RATE_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.RATE_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.RATE_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.RATE_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.RATE_3)
                .modelForState().modelFile(upgrade3).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.RATE_3)
                .modelForState().modelFile(upgrade3).addModel();

        upgrade1 = models().cubeAll("factory_upgrade_tier_shard_1", modLoc("item/tier_shard_1"));
        upgrade2 = models().cubeAll("factory_upgrade_tier_shard_2", modLoc("item/tier_shard_2"));
        upgrade3 = models().cubeAll("factory_upgrade_tier_shard_3", modLoc("item/tier_shard_3"));
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.TIER_SHARD_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.TIER_SHARD_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.TIER_SHARD_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.TIER_SHARD_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.TIER_SHARD_3)
                .modelForState().modelFile(upgrade3).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.TIER_SHARD_3)
                .modelForState().modelFile(upgrade3).addModel();

        upgrade1 = models().cubeAll("factory_upgrade_xp_1", modLoc("item/xp_1"));
        upgrade2 = models().cubeAll("factory_upgrade_xp_2", modLoc("item/xp_2"));
        upgrade3 = models().cubeAll("factory_upgrade_xp_3", modLoc("item/xp_3"));
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.XP_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.XP_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.XP_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.XP_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.XP_3)
                .modelForState().modelFile(upgrade3).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.XP_3)
                .modelForState().modelFile(upgrade3).addModel();

        upgrade1 = models().cubeAll("factory_upgrade_headless_1", modLoc("item/headless_1"));
        upgrade2 = models().cubeAll("factory_upgrade_headless_2", modLoc("item/headless_2"));
        upgrade3 = models().cubeAll("factory_upgrade_headless_3", modLoc("item/headless_3"));
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.HEADLESS_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.HEADLESS_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.HEADLESS_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.HEADLESS_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.HEADLESS_3)
                .modelForState().modelFile(upgrade3).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.HEADLESS_3)
                .modelForState().modelFile(upgrade3).addModel();

        upgrade1 = models().cubeAll("factory_upgrade_slaughter_1", modLoc("item/slaughter_1"));
        upgrade2 = models().cubeAll("factory_upgrade_slaughter_2", modLoc("item/slaughter_2"));
        upgrade3 = models().cubeAll("factory_upgrade_slaughter_3", modLoc("item/slaughter_3"));
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.SLAUGHTER_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.SLAUGHTER_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.SLAUGHTER_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.SLAUGHTER_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.SLAUGHTER_3)
                .modelForState().modelFile(upgrade3).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.SLAUGHTER_3)
                .modelForState().modelFile(upgrade3).addModel();

        upgrade1 = models().cubeAll("factory_upgrade_crusher_1", modLoc("item/crusher_1"));
        upgrade2 = models().cubeAll("factory_upgrade_crusher_2", modLoc("item/crusher_2"));
        upgrade3 = models().cubeAll("factory_upgrade_crusher_3", modLoc("item/crusher_3"));
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.CRUSHER_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.CRUSHER_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.CRUSHER_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.CRUSHER_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.CRUSHER_3)
                .modelForState().modelFile(upgrade3).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.CRUSHER_3)
                .modelForState().modelFile(upgrade3).addModel();

        upgrade1 = models().cubeAll("factory_upgrade_laser_1", modLoc("item/laser_1"));
        upgrade2 = models().cubeAll("factory_upgrade_laser_2", modLoc("item/laser_2"));
        upgrade3 = models().cubeAll("factory_upgrade_laser_3", modLoc("item/laser_3"));
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.LASER_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.LASER_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.LASER_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.LASER_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.LASER_3)
                .modelForState().modelFile(upgrade3).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.LASER_3)
                .modelForState().modelFile(upgrade3).addModel();

        upgrade1 = models().cubeAll("factory_upgrade_flayed_1", modLoc("item/flayed_1"));
        upgrade2 = models().cubeAll("factory_upgrade_flayed_2", modLoc("item/flayed_2"));
        upgrade3 = models().cubeAll("factory_upgrade_flayed_3", modLoc("item/flayed_3"));
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.FLAYED_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.FLAYED_1)
                .modelForState().modelFile(upgrade1).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.FLAYED_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.FLAYED_2)
                .modelForState().modelFile(upgrade2).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, false)
                .with(UpgradeBlock.UPGRADE, Perk.FLAYED_3)
                .modelForState().modelFile(upgrade3).addModel();
        builder.partialState().with(BlockStateProperties.ATTACHED, true)
                .with(UpgradeBlock.UPGRADE, Perk.FLAYED_3)
                .modelForState().modelFile(upgrade3).addModel();

        machineBlock(FactorySetup.HEART_BLOCK.get(), "heart", "block/heart");
        machineBlock(FluidConvertorSetup.FLUID_CONVERTOR_BLOCK.get(), "fluidconvertor", "block/fluidconvertor");
        machineBlock(InfuserSetup.INFUSER_BLOCK.get(), "infuser", "block/infuser");
        machineBlock(LayoutSetup.LAYOUT_BLOCK.get(), "layout", "block/layout");
        sideOnlyBlock(OracleSetup.ORACLE_BLOCK.get(), "oracle", "block/oracle");
        machineBlock(SqueezerSetup.SQUEEZER_BLOCK.get(), "squeezer", "block/squeezer");
        machineBlock(SqueezerSetup.ENCHANT_SQUEEZER_BLOCK.get(), "enchsqueezer", "block/enchsqueezer");
        singleTextureBlock(DebugSetup.CREATIVE_CONATUS_BLOCK.get(), "creative_conatus", "block/creative_conatus");
        singleTextureBlock(DebugSetup.CREATIVE_POWER_BLOCK.get(), "creative_power", "block/creative_power");
        singleTextureBlock(DebugSetup.DEBUG_TANK_BLOCK.get(), "debug_tank", "block/debug_tank");
        singleTextureBlock(FactorySetup.EXOTIC_A_BLOCK.get(), "exotic_a", "block/exotic_a");
        singleTextureBlock(FactorySetup.EXOTIC_B_BLOCK.get(), "exotic_b", "block/exotic_b");
        singleTextureBlock(FactorySetup.EXOTIC_C_BLOCK.get(), "exotic_c", "block/exotic_c");
        singleTextureBlock(FactorySetup.EXOTIC_D_BLOCK.get(), "exotic_d", "block/exotic_d");
        singleTextureBlock(FactorySetup.EXOTIC_E_BLOCK.get(), "exotic_e", "block/exotic_e");
    }
}
