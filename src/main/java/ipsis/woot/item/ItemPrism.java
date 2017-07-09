package ipsis.woot.item;

import ipsis.Woot;
import ipsis.woot.init.ModItems;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.util.WootMob;
import ipsis.woot.util.WootMobBuilder;
import ipsis.woot.util.WootMobName;
import ipsis.woot.farmblocks.IFarmBlockController;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPrism extends ItemWoot {

    public static final String BASENAME = "prism2";

    public ItemPrism() {

        super(BASENAME);
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {
        ModelResourceLocation emptyModel = new ModelResourceLocation(getRegistryName() + "_empty", "inventory");
        ModelResourceLocation fullModel = new ModelResourceLocation(getRegistryName(), "inventory");

        ModelBakery.registerItemVariants(this, emptyModel, fullModel);
        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                if (isProgrammed(stack))
                    return fullModel;
                return emptyModel;
            }
        });
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {

        if (attacker.getEntityWorld().isRemote || !(attacker instanceof EntityPlayer))
            return false;

        if (isProgrammed(stack))
            return false;

        LogHelper.info("hitEntity: " + EntityList.getKey(target));
        LogHelper.info("hitEntity: " + EntityList.getEntityString(target));
        LogHelper.info("hitEntity: " + target.getName());

        WootMob wootMob = WootMobBuilder.create((EntityLiving)target);
        if (!wootMob.isValid())
            return false;

        if (!Woot.wootConfiguration.canCapture(wootMob.getWootMobName())) {
            LogHelper.info("hitEntity: cannot capture " + wootMob.getDisplayName());
            return false;
        }

        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound == null)
            nbtTagCompound = new NBTTagCompound();

        WootMobBuilder.writeToNBT(wootMob, nbtTagCompound);
        stack.setTagCompound(nbtTagCompound);
        return true;
    }

    public static boolean isPrism(ItemStack itemStack) {

        return itemStack.getItem() == ModItems.itemPrism;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote)
            return EnumActionResult.SUCCESS;

        ItemStack itemStack = player.getHeldItem(hand);
        if (!isProgrammed(itemStack) || !isFull(itemStack))
            return EnumActionResult.FAIL;

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof IFarmBlockController) {
            IFarmBlockController controller = (IFarmBlockController)te;

            if (controller.program(itemStack)) {
                if (!player.capabilities.isCreativeMode)
                    itemStack.shrink(1);
                return  EnumActionResult.SUCCESS;
            }
        }

        return EnumActionResult.FAIL;
    }

    public static boolean isProgrammed(ItemStack itemStack) {

        if (!isPrism(itemStack))
            return false;

        WootMob wootMob = WootMobBuilder.create(itemStack.getTagCompound());
        if (!wootMob.isValid())
            return false;

        return true;
    }

    public static boolean isFull(ItemStack itemStack) {

        if (!isPrism(itemStack))
            return false;

        WootMob wootMob = WootMobBuilder.create(itemStack.getTagCompound());
        if (!wootMob.isValid())
            return false;

        //TODO return wootMob.getDeathCount() == 1;
        return true;
    }

    /**
     * Tooltip
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        if (!isPrism(stack))
            return;

        if (!isProgrammed(stack)) {
            tooltip.add("Unprogrammed");
        } else {
            WootMob wootMob = WootMobBuilder.create(stack.getTagCompound());
            if (wootMob.isValid()) {
                tooltip.add(wootMob.getDisplayName());
                // TODO tooltip.add("Killed: " + wootMob.getDeathCount() + "/1");
                if (flagIn.isAdvanced())
                    tooltip.add(wootMob.getWootMobName().getName());
            }
        }
    }

    public static void incrementDeaths(ItemStack itemStack, int count) {

        if (!isPrism(itemStack))
            return;

        if (!isProgrammed(itemStack))
            return;

        WootMob wootMob = WootMobBuilder.create(itemStack.getTagCompound());
        if (wootMob == null)
            return;

        // TODO wootMob.incrementDeathCount(count);
        WootMobBuilder.writeToNBT(wootMob, itemStack.getTagCompound());
    }

    public static boolean isMob(ItemStack itemStack, WootMobName wootMobName) {

        if (!isPrism(itemStack))
            return false;

        if (!isProgrammed(itemStack))
            return false;

        WootMob wootMob = WootMobBuilder.create(itemStack.getTagCompound());
        if (wootMob == null)
            return false;

        if (!wootMob.getWootMobName().equals(wootMobName))
            return false;

        return true;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {

        return isProgrammed(stack) && isFull(stack);
    }
}
