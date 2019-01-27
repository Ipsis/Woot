package ipsis.woot.power;

import ipsis.Woot;
import ipsis.woot.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEffort extends BlockFluidClassic {

    public static final ResourceLocation EFFORT = new ResourceLocation(Woot.MODID, "effort");

    public BlockEffort() {
        super(ModFluids.effort, Material.WATER);
        setCreativeTab(Woot.tab);
        setTranslationKey(Woot.MODID + ".effort");
        setRegistryName(EFFORT);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelResourceLocation mrl = new ModelResourceLocation(EFFORT, "fluid");

        StateMapperBase state = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
                return mrl;
            }
        };

        ModelLoader.setCustomStateMapper(this, state);
        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                0,
                new ModelResourceLocation(EFFORT, "inventory"));
    }


}
