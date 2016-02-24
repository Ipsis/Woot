package ipsis.woot.block;

import ipsis.oss.client.ModelHelper;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.reference.Lang;
import ipsis.woot.reference.Settings;
import ipsis.woot.tileentity.TileEntityMobFactory;
import ipsis.woot.util.StringHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockMobFactory extends BlockContainerWoot implements ITooltipInfo {

    public static final String BASENAME = "factory";

    public BlockMobFactory() {

        super(Material.rock, BASENAME);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntityMobFactory();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null && te instanceof TileEntityMobFactory) {
            ((TileEntityMobFactory)te).setFacing(placer.getHorizontalFacing().getOpposite());
            worldIn.markBlockForUpdate(pos);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerBlock(ModBlocks.blockFactory, BASENAME);
    }

    @Override
    public int getRenderType() {

        return 3;
    }

    @Override
    public void getTooltip(List<String> toolTip, boolean showAdvanced, int meta, boolean detail) {

        toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_FACTORY_COST), "I", Settings.tierIRF));
        toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_FACTORY_COST), "II", Settings.tierIIRF));
        toolTip.add(String.format(StringHelper.localize(Lang.TOOLTIP_FACTORY_COST), "III", Settings.tierIIIRF));
    }
}
