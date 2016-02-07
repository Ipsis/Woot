package ipsis.woot.block;

import ipsis.oss.client.ModelHelper;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.manager.Upgrade;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockUpgrade extends BlockWoot {

    public static final String BASENAME = "upgrade";

    public static final PropertyEnum<Upgrade.Type> VARIANT = PropertyEnum.<Upgrade.Type>create("variant", Upgrade.Type.class);
    public BlockUpgrade() {

        super(Material.rock, BASENAME);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, Upgrade.Type.RATE_I));
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] {VARIANT});
    }

    @Override
    public int damageDropped(IBlockState state) {

        return ((Upgrade.Type)state.getValue(VARIANT)).getMetadata();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {

        for (Upgrade.Type t : Upgrade.Type.values())
            list.add(new ItemStack(itemIn, 1, t.getMetadata()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        return this.getDefaultState().withProperty(VARIANT, Upgrade.Type.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        return ((Upgrade.Type)state.getValue(VARIANT)).getMetadata();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerBlock(ModBlocks.blockUpgrade, BASENAME);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

        worldIn.notifyNeighborsOfStateChange(pos, this);
        super.breakBlock(worldIn, pos, state);
    }
}
