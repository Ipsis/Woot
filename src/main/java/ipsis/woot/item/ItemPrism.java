package ipsis.woot.item;

import ipsis.Woot;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.init.ModItems;
import ipsis.woot.manager.MobRegistry;
import ipsis.woot.reference.Lang;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityMobFactoryController;
import ipsis.woot.tileentity.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.StringHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemPrism extends ItemWoot {

    public static final String BASENAME = "prism";

    public ItemPrism() {

        super(BASENAME);
        setMaxStackSize(1);
        setRegistryName(Reference.MOD_ID, BASENAME);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerItem(ModItems.itemPrism, BASENAME);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {

        if (attacker.getEntityWorld().isRemote)
            return false;

        if (!(attacker instanceof EntityPlayer))
            return false;

        if (hasMobName(stack))
            return false;

        String wootName = Woot.mobRegistry.onEntityLiving((EntityLiving)target);
        if (!Woot.mobRegistry.isValidMobName(wootName))
            return false;
        String displayName = Woot.mobRegistry.getDisplayName(wootName);

        if (!Woot.mobRegistry.isPrismValid(wootName)) {
            ((EntityPlayer) attacker).sendStatusMessage(
                    new TextComponentString(String.format(
                            StringHelper.localize(Lang.CHAT_PRISM_INVALID), displayName, wootName)), false);
            return false;
        }

        setMobName(stack, wootName, displayName, ((EntityLiving) target).experienceValue);
        ((EntityPlayer)attacker).sendStatusMessage(
                new TextComponentString(String.format(
                        StringHelper.localize(Lang.CHAT_PRISM_PROGRAM), displayName, wootName)), false);

        return true;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        ItemStack stack = player.getHeldItem(hand);

        if (worldIn.isRemote)
            return EnumActionResult.SUCCESS;

        if (!hasMobName(stack))
            return EnumActionResult.FAIL;

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityMobFactoryController) {
            if (!Woot.mobRegistry.isValidMobName(((TileEntityMobFactoryController) te).getMobName())) {
                ((TileEntityMobFactoryController) te).setMobName(getMobName(stack), getDisplayName(stack), getXp(stack));
                if (!player.capabilities.isCreativeMode)
                    stack.shrink(1);
                return EnumActionResult.SUCCESS;
            }
        }

        return EnumActionResult.FAIL;
    }

    /**
     * All we store in the prism is the name of the mob
     */
    static final String NBT_MOBNAME = "mobName";
    static final String NBT_DISPLAYNAME = "displayName";
    static final String NBT_XP_VALUE = "mobXpCost";

    public static void setAsEnderDragon(ItemStack stack) {

        String name = I18n.translateToLocal("entity.EnderDragon.name");
        setMobName(stack, MobRegistry.ENDER_DRAGON, name, Woot.mobRegistry.getSpawnXp(MobRegistry.ENDER_DRAGON));
    }

    public static void setMobName(ItemStack itemStack, String mobName, String displayName, int xp) {

        if (xp <= 0)
            xp = MobRegistry.MobInfo.MIN_XP_VALUE;

        if (itemStack.getTagCompound() == null)
            itemStack.setTagCompound(new NBTTagCompound());

        itemStack.getTagCompound().setString(NBT_MOBNAME, mobName);
        itemStack.getTagCompound().setString(NBT_DISPLAYNAME, displayName);
        itemStack.getTagCompound().setInteger(NBT_XP_VALUE, xp);
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

    public static int getXp(ItemStack itemStack) {

        if (itemStack.getTagCompound() == null)
            return 1;

        return itemStack.getTagCompound().getInteger(NBT_XP_VALUE);
    }

    static boolean hasMobName(ItemStack itemStack) {

        if (itemStack.getItem() != ModItems.itemPrism)
            return false;

        return Woot.mobRegistry.isValidMobName(getMobName(itemStack));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

        tooltip.add(StringHelper.localize(Lang.TAG_TOOLTIP + BASENAME + ".0"));
        tooltip.add(StringHelper.localize(Lang.TAG_TOOLTIP + BASENAME + ".1"));
        if (stack != null && hasMobName(stack) && Woot.mobRegistry.isValidMobName(getMobName(stack))) {
            String displayName = getDisplayName(stack);
            if (!displayName.equals(""))
                tooltip.add(TextFormatting.GREEN + String.format("Mob: %s", StringHelper.localize(displayName)));

            int xp = Woot.mobRegistry.getSpawnXp(getMobName(stack));
            EnumMobFactoryTier t = Woot.tierMapper.getTierForEntity(getMobName(stack), xp);
            tooltip.add(TextFormatting.BLUE + t.getTranslated(Lang.WAILA_FACTORY_TIER));
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        
        return hasMobName(stack);
    }

    public static ItemStack getItemStack(String wootName, int xp) {

        if (!Woot.mobRegistry.isValidMobName(wootName))
            return null;

        ItemStack itemStack = new ItemStack(ModItems.itemPrism);
        setMobName(itemStack, wootName, wootName, xp);
        return itemStack;
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {

        subItems.add(new ItemStack(itemIn, 1));

        /**
         * Dragon
         */
        ItemStack dragon = new ItemStack(itemIn, 1);
        setAsEnderDragon(dragon);
        subItems.add(dragon);

    }
}
