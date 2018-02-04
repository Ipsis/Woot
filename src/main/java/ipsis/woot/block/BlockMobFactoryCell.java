package ipsis.woot.block;

import ipsis.Woot;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.power.storage.IPowerStation;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityMobFactoryCell;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * The cell stores power, however it can only be used as part of the formed farm
 */
public class BlockMobFactoryCell extends BlockWoot implements ITileEntityProvider {

    public static final String BASENAME = "cell";

    public static final PropertyBool FORMED = PropertyBool.create("formed");
    public static final PropertyEnum<EnumCellTier> TIER = PropertyEnum.<EnumCellTier>create("tier", EnumCellTier.class);

    public BlockMobFactoryCell() {

        super(Material.ROCK, BASENAME);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TIER, EnumCellTier.TIER_I).withProperty(FORMED, false));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        TileEntityMobFactoryCell te =  new TileEntityMobFactoryCell();
        te.setTier(EnumCellTier.byMetadata(meta));
        return te;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { TIER, FORMED } );
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityMobFactoryCell)
            ((TileEntityMobFactoryCell) te).onBlockAdded();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

        if (worldIn.getTileEntity(pos) instanceof TileEntityMobFactoryCell) {
            TileEntityMobFactoryCell te = (TileEntityMobFactoryCell) worldIn.getTileEntity(pos);
            boolean formed = false;
            if (te != null)
                formed = te.isClientFormed();
            return state.withProperty(FORMED, formed);
        }

        return state;
    }

    @Override
    public int damageDropped(IBlockState state) {

        return state.getValue(TIER).getMetadata();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {

        for (EnumCellTier t : EnumCellTier.values())
            items.add(new ItemStack(this, 1, t.getMetadata()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        return this.getDefaultState().withProperty(TIER, EnumCellTier.byMetadata(meta)).withProperty(FORMED, false);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        return state.getValue(TIER).getMetadata();
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityMobFactoryCell) {
            List<ItemStack> drops = super.getDrops(world, pos, state, fortune);
            IPowerStation powerStation = ((TileEntityMobFactoryCell) te).getPowerStation();
            if (powerStation != null) {
                if (!drops.isEmpty()) {
                    NBTTagCompound compound = drops.get(0).getTagCompound();
                    if (compound == null) {
                        compound = new NBTTagCompound();
                        drops.get(0).setTagCompound(compound);
                    }

                    powerStation.writeToNBT(compound);
                }
            }
            return drops;

        }

        return super.getDrops(world, pos, state, fortune);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (stack.hasTagCompound() && !worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TileEntityMobFactoryCell) {
                ((TileEntityMobFactoryCell) te).getPowerStation().readFromNBT(stack.getTagCompound());
            }
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {

        // From TinkersConstruct to allow the TE exist while processing the getDrops
        this.onBlockDestroyedByPlayer(world, pos, state);
        if (willHarvest)
            this.harvestBlock(world, player, pos, state, world.getTileEntity(pos), player.getHeldItemMainhand());

        world.setBlockToAir(pos);
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        Item itemBlockVariants = Item.REGISTRY.getObject(new ResourceLocation(Reference.MOD_ID, BASENAME));

        for (int i = 0; i < EnumCellTier.VALUES.length; i++) {

            EnumCellTier e = EnumCellTier.VALUES[i];
            ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(
                    Reference.MOD_ID + ":" + BASENAME + "_" + e, "inventory");
            ModelLoader.setCustomModelResourceLocation(itemBlockVariants, i, itemModelResourceLocation);
        }
    }

    public enum EnumCellTier implements IStringSerializable {
        TIER_I("tier_i"),
        TIER_II("tier_ii"),
        TIER_III("tier_iii");

        String name;
        EnumCellTier(String name) {
            this.name = name;
        }

        public static EnumCellTier[] VALUES = { TIER_I, TIER_II, TIER_III };


        @Override
        public String getName() {

            return this.name;
        }

        int getMetadata() {

            return this.ordinal();
        }

        public static EnumCellTier byMetadata(int metadata) {

            if (metadata < 0 || metadata >= VALUES.length)
                return TIER_I;

            return VALUES[metadata];
        }

        public static int getMaxPower(EnumCellTier tier) {

            if (tier == TIER_I)
                return Woot.wootConfiguration.getInteger(EnumConfigKey.T1_POWER_MAX);
            else if (tier == TIER_II)
                return Woot.wootConfiguration.getInteger(EnumConfigKey.T2_POWER_MAX);

            return Woot.wootConfiguration.getInteger(EnumConfigKey.T3_POWER_MAX);
        }

        public static int getMaxTransfer(EnumCellTier tier) {

            if (tier == TIER_I)
                return Woot.wootConfiguration.getInteger(EnumConfigKey.T1_POWER_RX_TICK);
            else if (tier == TIER_II)
                return Woot.wootConfiguration.getInteger(EnumConfigKey.T2_POWER_RX_TICK);

            return Woot.wootConfiguration.getInteger(EnumConfigKey.T3_POWER_RX_TICK);
        }
    }
}
