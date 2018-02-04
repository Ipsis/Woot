package ipsis.woot.block;

import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.plugins.top.ITOPInfoProvider;
import ipsis.woot.plugins.top.TOPUIInfoConvertors;
import ipsis.woot.tileentity.TileEntityMobFactoryController;
import ipsis.woot.farmblocks.IFarmBlockController;
import ipsis.woot.tileentity.ui.ControllerUIInfo;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class BlockMobFactoryController extends BlockWoot implements ITileEntityProvider, ITOPInfoProvider {

    public static final String BASENAME = "controller";

    public BlockMobFactoryController() {

        super(Material.ROCK, BASENAME);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMobFactoryController();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerBlock(ModBlocks.blockFactoryController, BASENAME);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityMobFactoryController)
            ((TileEntityMobFactoryController) te).onBlockAdded();
    }

    /**
     * NB:
     *
     * This is the update that TinkersConstruct does to allow the TE to be available when
     * parsing drops.
     * Something changed in vanilla, as from other code it looks like this is a new requirement
     */

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof IFarmBlockController && stack != null && stack.hasTagCompound())
            ((IFarmBlockController) te).readControllerFromNBT(stack.getTagCompound());
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityMobFactoryController) {
            TileEntityMobFactoryController tec = (TileEntityMobFactoryController)te;
            List<ItemStack> ret = new ArrayList<>();
            ret.add(tec.getDroppedItemStack());
            return ret;

        } else {
            return super.getDrops(world, pos, state, fortune);
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

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {

        TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof TileEntityMobFactoryController) {

            ControllerUIInfo info = new ControllerUIInfo();
            ((TileEntityMobFactoryController) te).getUIInfo(info);
            if (info.isValid)
                TOPUIInfoConvertors.controllerConvertor(info, mode, probeInfo, player, world, blockState, data);
        }
    }
}
