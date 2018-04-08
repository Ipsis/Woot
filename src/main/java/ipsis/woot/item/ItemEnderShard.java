package ipsis.woot.item;

import com.mojang.realmsclient.gui.ChatFormatting;
import ipsis.Woot;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.init.ModItems;
import ipsis.woot.util.StringHelper;
import ipsis.woot.util.WootMob;
import ipsis.woot.util.WootMobBuilder;
import ipsis.woot.util.WootMobName;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemEnderShard extends ItemWoot {

    public static final String BASENAME = "endershard";

    public ItemEnderShard() {

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

        WootMob wootMob = WootMobBuilder.create((EntityLiving)target);
        if (!wootMob.isValid())
            return false;

        if (!Woot.policyRepository.canCapture(wootMob.getWootMobName())) {
            ((EntityPlayer) attacker).sendStatusMessage(
                    new TextComponentString(StringHelper.localize("chat.woot.endershard.failure")), false);
            return false;
        }

        NBTTagCompound nbtTagCompound = stack.getTagCompound();
        if (nbtTagCompound == null)
            nbtTagCompound = new NBTTagCompound();

        WootMobBuilder.writeToNBT(wootMob, nbtTagCompound);
        stack.setTagCompound(nbtTagCompound);

        ((EntityPlayer) attacker).sendStatusMessage(
                new TextComponentString(StringHelper.localize("chat.woot.endershard.success")), false);

        return true;
    }

    public static boolean isEnderShard(ItemStack itemStack) {

        return itemStack.getItem() == ModItems.itemEnderShard;
    }

    public static boolean isProgrammed(ItemStack itemStack) {

        if (!isEnderShard(itemStack))
            return false;

        WootMob wootMob = WootMobBuilder.create(itemStack.getTagCompound());
        if (!wootMob.isValid())
            return false;

        return true;
    }

    public static boolean isFull(ItemStack itemStack) {

        if (!isProgrammed(itemStack))
            return false;

        WootMob wootMob = WootMobBuilder.create(itemStack.getTagCompound());
        if (!wootMob.isValid())
            return false;

        if (wootMob.getDeaths() < Woot.wootConfiguration.getInteger(wootMob.getWootMobName(), EnumConfigKey.KILL_COUNT))
            return false;

        return true;
    }

    /**
     * Tooltip
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        if (!isEnderShard(stack))
            return;

        if (isJEIEnderShard(stack)) {
            tooltip.add(ChatFormatting.BLUE + StringHelper.localize("info.woot.endershard.a.1"));
            return;
        }

        tooltip.add(StringHelper.getInfoText("info.woot.endershard.0"));
        tooltip.add(StringHelper.getInfoText("info.woot.endershard.1"));
        tooltip.add(StringHelper.getInfoText("info.woot.endershard.2"));
        tooltip.add(StringHelper.getInfoText("info.woot.endershard.3"));

        if (!isProgrammed(stack)) {
            String out = StringHelper.getInfoText("info.woot.endershard.a.0");
            tooltip.add(ChatFormatting.RED + out);
        } else {
            WootMob wootMob = WootMobBuilder.create(stack.getTagCompound());
            if (wootMob.isValid()) {
                String name = wootMob.getDisplayName();
                if (isFull(stack)) {
                    String out = StringHelper.localizeFormat("info.woot.endershard.b.1", name);
                    tooltip.add(ChatFormatting.BLUE + out);
                } else {
                    int deaths = wootMob.getDeaths();
                    deaths = MathHelper.clamp(deaths, 0, Woot.wootConfiguration.getInteger(wootMob.getWootMobName(), EnumConfigKey.KILL_COUNT));
                    String out = StringHelper.localizeFormat("info.woot.endershard.b.0",
                            name,
                            deaths,
                            Woot.wootConfiguration.getInteger(wootMob.getWootMobName(), EnumConfigKey.KILL_COUNT));
                    tooltip.add(ChatFormatting.RED + out);
                }

                if (flagIn.isAdvanced())
                    tooltip.add(wootMob.getWootMobName().getName());
            }
        }
    }

    public static void incrementDeaths(ItemStack itemStack, int count) {

        if (!isEnderShard(itemStack))
            return;

        if (!isProgrammed(itemStack))
            return;

        WootMob wootMob = WootMobBuilder.create(itemStack.getTagCompound());
        if (!wootMob.isValid())
            return;

        wootMob.incrementDeathCount(count);
        WootMobBuilder.writeToNBT(wootMob, itemStack.getTagCompound());
    }

    public static boolean isMob(ItemStack itemStack, WootMobName wootMobName) {

        if (!isEnderShard(itemStack))
            return false;

        if (!isProgrammed(itemStack))
            return false;

        WootMob wootMob = WootMobBuilder.create(itemStack.getTagCompound());
        if (!wootMob.isValid())
            return false;

        if (!wootMob.getWootMobName().equals(wootMobName))
            return false;

        return true;
    }

    public static boolean isJEIEnderShard(ItemStack itemStack) {

        if (itemStack.hasTagCompound() == false || itemStack.getTagCompound().hasKey("nbt_jei_shard") == false)
            return false;

        return true;
    }

    public static void setJEIEnderShared(ItemStack itemStack) {

        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger("nbt_jei_shard", 1);
        itemStack.setTagCompound(tagCompound);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {

        return isJEIEnderShard(stack) || (isProgrammed(stack) && isFull(stack));
    }
}
