package ipsis.woot.fluids;

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

public class BlockPureDye extends BlockFluidClassic {

    public static final ResourceLocation PUREDYE = new ResourceLocation(Woot.MODID, "puredye");

    public BlockPureDye() {
        super(ModFluids.pureDye, Material.WATER);
        setCreativeTab(Woot.tab);
        setTranslationKey(Woot.MODID + ".puredye");
        setRegistryName(PUREDYE);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelResourceLocation mrl = new ModelResourceLocation(PUREDYE, "fluid");

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
                new ModelResourceLocation(PUREDYE, "inventory"));
    }
}
