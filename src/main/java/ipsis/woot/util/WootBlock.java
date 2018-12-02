package ipsis.woot.util;

import ipsis.Woot;
import ipsis.woot.util.helpers.StringHelper;
import ipsis.woot.util.helpers.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Based off McJty's YouTubeModdingTutorial
 * https://github.com/McJty/YouTubeModdingTutorial/blob/master/src/main/java/mcjty/mymod/tools/GenericBlock.java
 */
public class WootBlock extends Block {

    public WootBlock(Material materialIn, String basename) {

        super(materialIn);
        setCreativeTab(Woot.tab);
        setTranslationKey(Woot.MODID + "." + basename);
        setRegistryName(basename);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                0,
                new ModelResourceLocation(getRegistryName(), ".inventory"));
    }

    private static final Pattern COMPILE = Pattern.compile("@", Pattern.LITERAL);
    protected void addInformationLocalized(List<String> tooltip, String key, Object... parameters) {
        String translated = StringHelper.localise(key, parameters);
        translated = COMPILE.matcher(translated).replaceAll("\u00a7");
        Collections.addAll(tooltip, StringUtils.split(translated, "\n"));
    }

    /**
     * Restorable block information
     * There is currently a Forge bug open to make this work without the modders doing it manually.
     * So this may eventually be simpler in 1.13
     */
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof IRestorableTileEntity) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();
            if (nbtTagCompound != null)
                ((IRestorableTileEntity)te).readRestorableFromNBT(nbtTagCompound);
        }
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        /**
         * By returning true on willHarvest delays deletion of the block until after getDrops
         */
        return willHarvest ? true : super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

        TileEntity te = world.getTileEntity(pos);
        if (te instanceof IRestorableTileEntity) {
            ItemStack itemStack = new ItemStack(Item.getItemFromBlock(this));
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            ((IRestorableTileEntity)te).writeRestorableToNBT(nbtTagCompound);

            itemStack.setTagCompound(nbtTagCompound);
            drops.add(itemStack);
        } else {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (WorldHelper.isClientWorld(worldIn))
            return true;

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof IGuiTile) {
            playerIn.openGui(Woot.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }

        return false;
    }
}
