package ipsis.woot.item;

import ipsis.oss.client.ModelHelper;
import ipsis.woot.init.ModItems;
import ipsis.woot.tileentity.TileEntityMobFactoryController;
import ipsis.woot.util.MobUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemPrism extends ItemWoot {

    public static final String BASENAME = "prism";

    public ItemPrism() {

        super(BASENAME);
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerItem(ModItems.itemPrism, BASENAME);
    }

    public static ItemStack getItemStack(String mobName) {

        ItemStack itemStack = new ItemStack(ModItems.itemPrism);
        setMobName(itemStack, mobName);
        return itemStack;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {

        if (attacker.worldObj.isRemote)
            return false;

        /* No spawning players! */
        if (hasMobName(stack) || target instanceof EntityPlayer)
            return false;

        String mobName = MobUtil.getMobName(target);
        // TODO blacklist

        setMobName(stack, mobName);
        return true;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote)
            return true;

        if (!hasMobName(stack))
            return false;

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityMobFactoryController) {
            if (((TileEntityMobFactoryController) te).getMobName().equals("")) {
                ((TileEntityMobFactoryController) te).setMobName(getMobName(stack));
                return true;
            }
        }

        return false;
    }

    /**
     * All we store in the prism is the name of the mob
     */
    static final String NBT_MOBNAME = "mobName";
    public static void setMobName(ItemStack itemStack, String mobName) {

        if (itemStack.getTagCompound() == null)
            itemStack.setTagCompound(new NBTTagCompound());

        itemStack.getTagCompound().setString(NBT_MOBNAME, mobName);
    }

    public static String getMobName(ItemStack itemStack) {

        if (itemStack.getTagCompound() == null)
            return "";

        return itemStack.getTagCompound().getString(NBT_MOBNAME);
    }

    static boolean hasMobName(ItemStack itemStack) {

        if (itemStack.getItem() != ModItems.itemPrism)
            return false;

        return !getMobName(itemStack).equals("");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

        if (stack != null)
            tooltip.add(String.format("Mob: %s", getMobName(stack)));
    }
}
