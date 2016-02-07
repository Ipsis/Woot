package ipsis.woot.block;

import ipsis.oss.LogHelper;
import ipsis.oss.client.ModelHelper;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.tileentity.TileEntitySpawner;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockFactory extends BlockContainerWoot {

    public static final String BASENAME = "factory";

    public BlockFactory() {

        super(Material.rock, BASENAME);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntitySpawner();
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {

        ItemStack itemStack = playerIn.getCurrentEquippedItem();
        if (itemStack != null)  {
            if (itemStack.getItem() == Items.spawn_egg) {
                String mobName = ItemMonsterPlacer.getEntityName(itemStack);
                TileEntitySpawner te = (TileEntitySpawner)worldIn.getTileEntity(pos);
                te.setMobName(mobName);
            }
        }

        return true;
    }

    @Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {

        if (!worldIn.isRemote) {
            if (neighborBlock instanceof BlockUpgrade) {
                TileEntity te = worldIn.getTileEntity(pos);
                if (te instanceof TileEntitySpawner)
                    ((TileEntitySpawner) te).scanUpgrades();
            }
        }
    }
}
