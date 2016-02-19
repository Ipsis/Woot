package ipsis.woot.block;

import ipsis.woot.init.ModBlocks;
import ipsis.woot.manager.EnumSpawnerUpgrade;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityMobFactory;
import ipsis.woot.tileentity.TileEntityMobFactoryUpgrade;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockUpgrade extends BlockContainerWoot {

    public static final String BASENAME = "upgrade";

    public static final PropertyEnum<EnumSpawnerUpgrade> VARIANT = PropertyEnum.<EnumSpawnerUpgrade>create("variant", EnumSpawnerUpgrade.class);
    public BlockUpgrade() {

        super(Material.rock, BASENAME);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumSpawnerUpgrade.RATE_I));
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] {VARIANT});
    }

    @Override
    public int damageDropped(IBlockState state) {

        return state.getValue(VARIANT).getMetadata();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {

        for (EnumSpawnerUpgrade u : EnumSpawnerUpgrade.values())
            list.add(new ItemStack(itemIn, 1, u.getMetadata()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        return this.getDefaultState().withProperty(VARIANT, EnumSpawnerUpgrade.getFromMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        return state.getValue(VARIANT).getMetadata();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ResourceLocation[] locations = new ResourceLocation[EnumSpawnerUpgrade.values().length];
        for (int i = 0; i < EnumSpawnerUpgrade.values().length; i++)
            locations[i] = new ResourceLocation(Reference.MOD_NAME_LOWER + ":" + BASENAME + "_" + EnumSpawnerUpgrade.getFromMetadata(i));

        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.blockUpgrade), locations);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntityMobFactoryUpgrade();
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {

        TileEntityMobFactoryUpgrade te = (TileEntityMobFactoryUpgrade) worldIn.getTileEntity(pos);
        te.blockAdded();
    }
}
