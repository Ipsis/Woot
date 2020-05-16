package ipsis.woot.datagen;

import ipsis.woot.Woot;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.modules.debug.DebugSetup;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.fluidconvertor.FluidConvertorSetup;
import ipsis.woot.modules.infuser.InfuserSetup;
import ipsis.woot.modules.layout.LayoutSetup;
import ipsis.woot.modules.oracle.OracleSetup;
import ipsis.woot.modules.squeezer.SqueezerSetup;
import ipsis.woot.util.oss.BaseBlockStateProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;

public class Blocks extends BaseBlockStateProvider {

    public Blocks(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Woot.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        machineBlock(FactorySetup.HEART_BLOCK.get(), "heart", "block/heart");
        sideOnlyBlock(FactorySetup.CONTROLLER_BLOCK.get(), "controller", "block/controller");

        sideOnlyBlock(FactorySetup.FACTORY_A_BLOCK.get(), "factory_a", "block/factory_a");
        sideOnlyBlock(FactorySetup.FACTORY_B_BLOCK.get(), "factory_b", "block/factory_b");
        sideOnlyBlock(FactorySetup.FACTORY_C_BLOCK.get(), "factory_c", "block/factory_c");
        sideOnlyBlock(FactorySetup.FACTORY_D_BLOCK.get(), "factory_d", "block/factory_d");
        sideOnlyBlock(FactorySetup.FACTORY_E_BLOCK.get(), "factory_e", "block/factory_e");
        sideOnlyBlock(FactorySetup.CAP_A_BLOCK.get(), "cap_a", "block/cap_a");
        sideOnlyBlock(FactorySetup.CAP_B_BLOCK.get(), "cap_b", "block/cap_b");
        sideOnlyBlock(FactorySetup.CAP_C_BLOCK.get(), "cap_c", "block/cap_c");
        sideOnlyBlock(FactorySetup.CAP_D_BLOCK.get(), "cap_d", "block/cap_d");
        sideOnlyBlock(FactorySetup.FACTORY_CONNECT_BLOCK.get(), "factory_connect", "block/factory_connect");
        sideOnlyBlock(FactorySetup.FACTORY_CTR_BASE_PRI_BLOCK.get(), "factory_ctr_base_pri", "block/factory_ctr_base_pri");
        sideOnlyBlock(FactorySetup.FACTORY_CTR_BASE_SEC_BLOCK.get(), "factory_ctr_base_sec", "block/factory_ctr_base_sec");
        sideOnlyBlock(FactorySetup.IMPORT_BLOCK.get(), "import", "block/import");
        sideOnlyBlock(FactorySetup.EXPORT_BLOCK.get(), "export", "block/export");
        sideOnlyBlock(FactorySetup.FACTORY_UPGRADE_BLOCK.get(), "factory_upgrade", "block/factory_upgrade");
        sideOnlyBlock(FactorySetup.CELL_1_BLOCK.get(), "cell_1", "block/cell_1");
        sideOnlyBlock(FactorySetup.CELL_2_BLOCK.get(), "cell_2", "block/cell_2");
        sideOnlyBlock(FactorySetup.CELL_3_BLOCK.get(), "cell_3", "block/cell_3");
        sideOnlyBlock(FactorySetup.CELL_4_BLOCK.get(), "cell_4", "block/cell_4");

        machineBlock(FluidConvertorSetup.FLUID_CONVERTOR_BLOCK.get(), "fluidconvertor", "block/fluidconvertor");
        machineBlock(InfuserSetup.INFUSER_BLOCK.get(), "infuser", "block/infuser");
        machineBlock(LayoutSetup.LAYOUT_BLOCK.get(), "layout", "block/layout");
        sideOnlyBlock(OracleSetup.ORACLE_BLOCK.get(), "oracle", "block/oracle");
        machineBlock(SqueezerSetup.SQUEEZER_BLOCK.get(), "squeezer", "block/squeezer");
        machineBlock(SqueezerSetup.ENCHANT_SQUEEZER_BLOCK.get(), "enchsqueezer", "block/enchsqueezer");
        singleTextureBlock(ModBlocks.TICK_CONVERTER_BLOCK, "tick_conv", "block/tick_conv");
        singleTextureBlock(DebugSetup.CREATIVE_POWER_BLOCK.get(), "creative_power", "block/creative_power");
    }
}
