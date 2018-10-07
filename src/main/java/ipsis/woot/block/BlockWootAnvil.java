package ipsis.woot.block;

import ipsis.Woot;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModItems;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.tileentity.TileEntityAnvil;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Based off BlockAnvil in vanilla - but without the damage
 */
public class BlockWootAnvil extends BlockWoot implements ITileEntityProvider {

    public static final String BASENAME = "anvil";
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected static final AxisAlignedBB X_AXIS_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.125D, 1.0D, 1.0D, 0.875D);
    protected static final AxisAlignedBB Z_AXIS_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.0D, 0.875D, 1.0D, 1.0D);

    public BlockWootAnvil() {

        super(Material.ANVIL, BASENAME);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public boolean isFullCube(IBlockState state) {

        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {

        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {

        EnumFacing enumFacing = placer.getHorizontalFacing().rotateY();
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(FACING, enumFacing);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

        EnumFacing enumFacing = state.getValue(FACING);
        return enumFacing.getAxis() == EnumFacing.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

        // Get the main block
        super.getDrops(drops, world, pos, state, fortune);

        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityAnvil) {
            // Add any items that were in the anvil
            TileEntityAnvil anvil = (TileEntityAnvil)te;
            ItemStack itemStack = anvil.getBaseItem();
            if (!itemStack.isEmpty())
                drops.add(itemStack.copy());
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
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

        return true;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        return state.getValue(FACING).getHorizontalIndex();
    }


    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.getBlock() != this ? state : state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {

        return EnumBlockRenderType.MODEL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerBlock(ModBlocks.blockAnvil, BASENAME);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntityAnvil();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TileEntityAnvil) {
                TileEntityAnvil anvil = (TileEntityAnvil)te;
                ItemStack playerItem = playerIn.getHeldItem(hand);
                if (anvil.getBaseItem().isEmpty()) {
                    if (Woot.anvilManager.isValidBaseItem(playerItem)) {
                        // From player hand to empty anvil
                        ItemStack baseItem = playerItem.copy();
                        baseItem.setCount(1);
                        anvil.setBaseItem(baseItem);

                        playerItem.setCount(playerItem.getCount() - 1);
                        if (playerItem.isEmpty()) {
                            playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, ItemStack.EMPTY);
                        } else {
                            playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, playerItem);
                        }
                        playerIn.openContainer.detectAndSendChanges();

                    }
                } else {
                    if (playerItem.getItem() == ModItems.itemYahHammer) {
                        anvil.tryCraft(playerIn);
                    } else {
                        // From anvil to player
                        ItemStack baseItem = anvil.getBaseItem();
                        anvil.setBaseItem(ItemStack.EMPTY);
                        if (!playerIn.inventory.addItemStackToInventory(baseItem)) {
                            EntityItem entityItem = new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), baseItem);
                            worldIn.spawnEntity(entityItem);
                        } else {
                            playerIn.openContainer.detectAndSendChanges();
                        }
                    }
                }
            }
        }

        return true;
    }
}
