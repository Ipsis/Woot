package ipsis.woot.block;

import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.plugins.top.ITOPInfoProvider;
import ipsis.woot.reference.Lang;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityMobFactoryController;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.tileentity.multiblock.MobFactoryMultiblockLogic;
import ipsis.woot.util.StringHelper;
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
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
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
        setRegistryName(Reference.MOD_ID_LOWER, BASENAME);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMobFactoryController();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerBlock(ModBlocks.blockController, BASENAME);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {

        TileEntityMobFactoryController te = (TileEntityMobFactoryController) worldIn.getTileEntity(pos);
        te.blockAdded();
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
        if (te instanceof TileEntityMobFactoryController && stack != null && stack.hasTagCompound())
            ((TileEntityMobFactoryController)te).readControllerFromNBT(stack.getTagCompound());
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityMobFactoryController) {
            TileEntityMobFactoryController tec = (TileEntityMobFactoryController)te;
            List<ItemStack> ret = new ArrayList<ItemStack>();
            ret.add(tec.getDroppedItemStack());
            return ret;

        } else {
            return super.getDrops(world, pos, state, fortune);
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {

        IBlockState iBlockState = world.getBlockState(pos);
        this.onBlockDestroyedByPlayer(world, pos, iBlockState);
        if (willHarvest) {
            // TODO need to check harvesting of blocks
            ItemStack itemstack1 = player.getHeldItemMainhand();
            ItemStack itemstack2 = itemstack1 == null ? null : itemstack1.copy();
            this.harvestBlock(world, player, pos, iBlockState, world.getTileEntity(pos), itemstack2);
        }

        world.setBlockToAir(pos);
        /**
         * BONI: Return false to prevent the above called functions to be called again
         * side effect of this is that no xp will be dropped, but it shouldn't anyway.
         */
        return false;
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {

        TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof TileEntityMobFactoryController) {
            TileEntityMobFactoryController controllerTE = (TileEntityMobFactoryController)te;

            if (!controllerTE.getDisplayName().equals((""))) {

                EnumMobFactoryTier t = MobFactoryMultiblockLogic.getTier(controllerTE.getXpValue());
                probeInfo.text(TextFormatting.GREEN + String.format("%s : %s XP", controllerTE.getDisplayName(), controllerTE.getXpValue()));
                probeInfo.text(TextFormatting.BLUE + String.format(StringHelper.localize(Lang.WAILA_CONTROLLER_TIER),
                        (t == EnumMobFactoryTier.TIER_ONE ? "I" : t == EnumMobFactoryTier.TIER_TWO ? "II" : "III")));
            }
        }

    }
}
