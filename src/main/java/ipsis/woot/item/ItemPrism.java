package ipsis.woot.item;

import ipsis.Woot;
import ipsis.oss.LogHelper;
import ipsis.oss.client.ModelHelper;
import ipsis.woot.init.ModItems;
import ipsis.woot.manager.MobManager;
import ipsis.woot.tileentity.TileEntityMobFactoryController;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
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

    public static ItemStack getItemStack(String mobName, String displayName) {

        ItemStack itemStack = new ItemStack(ModItems.itemPrism);
        setMobName(itemStack, mobName, displayName);
        return itemStack;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {

        if (attacker.worldObj.isRemote)
            return false;

        if (hasMobName(stack))
            return false;

        if (Woot.mobManager.isBlacklisted(target))
            return false;

        String mobName = Woot.mobManager.getMobName(target);
        String displayName = target.getName();

        LogHelper.info("Try capture " + mobName + ":" + displayName);

        if (MobManager.isValidMobName(mobName)) {
            setMobName(stack, mobName, displayName);
            return true;
        }

        return false;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote)
            return true;

        if (!hasMobName(stack))
            return false;

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityMobFactoryController) {
            if (!MobManager.isValidMobName(((TileEntityMobFactoryController) te).getMobName())) {
                ((TileEntityMobFactoryController) te).setMobName(getMobName(stack), getDisplayName(stack));
                return true;
            }
        }

        return false;
    }

    /**
     * All we store in the prism is the name of the mob
     */
    static final String NBT_MOBNAME = "mobName";
    static final String NBT_DISPLAYNAME = "displayName";
    public static void setMobName(ItemStack itemStack, String mobName, String displayName) {

        if (itemStack.getTagCompound() == null)
            itemStack.setTagCompound(new NBTTagCompound());

        itemStack.getTagCompound().setString(NBT_MOBNAME, mobName);
        itemStack.getTagCompound().setString(NBT_DISPLAYNAME, displayName);
    }

    public static String getMobName(ItemStack itemStack) {

        if (itemStack.getTagCompound() == null)
            return "";

        return itemStack.getTagCompound().getString(NBT_MOBNAME);
    }

    public static String getDisplayName(ItemStack itemStack) {

        if (itemStack.getTagCompound() == null)
            return "";

        return itemStack.getTagCompound().getString(NBT_DISPLAYNAME);
    }

    static boolean hasMobName(ItemStack itemStack) {

        if (itemStack.getItem() != ModItems.itemPrism)
            return false;

        return MobManager.isValidMobName(getMobName(itemStack));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

        if (stack != null && hasMobName(stack) && MobManager.isValidMobName(getMobName(stack))) {
            String displayName = getDisplayName(stack);
            if (!displayName.equals(""))
                tooltip.add(String.format("Mob: %s", StatCollector.translateToLocal(displayName)));
        }
    }
}
