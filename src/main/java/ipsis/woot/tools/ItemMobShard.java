package ipsis.woot.tools;

import ipsis.woot.Woot;
import ipsis.woot.mod.ModItems;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.WootItem;
import ipsis.woot.util.helper.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemMobShard extends WootItem {

    public static final String BASENAME = "mobshard";
    public ItemMobShard() {
        super(new Item.Properties().group(Woot.TAB_WOOT), BASENAME);
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentString(StringHelper.translate("info.woot.mobshard")));
        tooltip.add(new TextComponentString(StringHelper.translate("info.woot.mobshard.0")));
        tooltip.add(new TextComponentString(StringHelper.translate("info.woot.mobshard.1")));

        if (isEntityProgrammed(stack)) {
            tooltip.add(new TextComponentString(StringHelper.translate("info.woot.mobshard.2")));
            if (isFullyProgrammed(stack))
                tooltip.add(new TextComponentString(
                        StringHelper.translate("info.woot.mobshard.state.0")));
            else
                tooltip.add(new TextComponentString(
                        StringHelper.translateFormat("info.woot.mobshard.state.1", getDeathCount(stack), 10)));
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {

        if (WorldHelper.isClientWorld(attacker.getEntityWorld()))
            return false;

        if (!(attacker instanceof EntityPlayer) && !(target instanceof EntityLiving))
            return false;

        EntityPlayer entityPlayer = (EntityPlayer)attacker;
        EntityLiving entityLiving = (EntityLiving)target;

        //@todo can capture mob

        if (isEntityProgrammed(stack)) {
            PlayerHelper.sendChatMessage(entityPlayer,
                    StringHelper.translate("chat.woot.mobshard.used"));
            return false;
        }

        FakeMobKey fakeMobKey = FakeMobKeyHelper.createFromEntity((EntityLiving)target);
        if (fakeMobKey.isValid()) {
            programEntity(stack, fakeMobKey);
            PlayerHelper.sendActionBarMessage(entityPlayer,
                    StringHelper.translateFormat("chat.woot.mobshard.success", StringHelper.translate(getItemStackDisplayName(stack))));
            return true;
        }

        return false;
    }

    public static boolean isMobShard(ItemStack itemStack) {
        return !itemStack.isEmpty() && itemStack.getItem() == ModItems.mobShardItem;
    }

    public static boolean isMobEqual(ItemStack itemStack, FakeMobKey fakeMobKey) {
        if (!isMobShard(itemStack))
            return false;

        FakeMobKey currFakeMobKey = getProgrammedEntity(itemStack);
        return currFakeMobKey.equals(fakeMobKey);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {

        String entityName = getItemStackDisplayName(stack);
        if (entityName.equalsIgnoreCase(""))
            entityName = "info.woot.mobshard.state.2";

        return new TextComponentString(StringHelper.translate(getTranslationKey(stack)) + " - " + StringHelper.translate(entityName));
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return isFullyProgrammed(stack);
    }

    /**
     * Programming
     */

    private static final String NBT_DEATH_COUNT = "deathCount";
    private static final int FULLY_PROGRAMMED_COUNT = 1;

    /**
     * return true if the entity is programmed AND is valid
     */
    public static boolean isEntityProgrammed(ItemStack itemStack) {

        if (itemStack == null || itemStack.isEmpty() || !itemStack.hasTag())
            return false;

        FakeMobKey fakeMobKey = getProgrammedEntity(itemStack);
        return fakeMobKey.isValid();
    }

    public static boolean isFullyProgrammed(ItemStack itemStack) {

        if (itemStack == null || itemStack.isEmpty())
            return false;

        return getDeathCount(itemStack) >= FULLY_PROGRAMMED_COUNT;
    }

    public static int getDeathCount(ItemStack itemStack) {

        if (itemStack == null || itemStack.isEmpty())
            return 0;

        if (!itemStack.hasTag() || !itemStack.getTag().hasKey(NBT_DEATH_COUNT))
            return 0;

        return itemStack.getTag().getInt(NBT_DEATH_COUNT);
    }

    public static @Nonnull
    FakeMobKey getProgrammedEntity(ItemStack itemStack) {

        if (itemStack == null || itemStack.isEmpty() || !itemStack.hasTag())
            return new FakeMobKey();

        return new FakeMobKey(itemStack.getTag());
    }

    public static void programEntity(ItemStack itemStack, FakeMobKey fakeMobKey) {

        if (itemStack == null || itemStack.isEmpty() || fakeMobKey == null || !fakeMobKey.isValid() || isEntityProgrammed(itemStack))
            return;

        if (!itemStack.hasTag())
            itemStack.setTag(new NBTTagCompound());

        FakeMobKey.writeToNBT(fakeMobKey, itemStack.getTag());
    }

    public static void incrementDeathCount(ItemStack itemStack, int delta) {

        if (itemStack == null || itemStack.isEmpty())
            return;

        if (!itemStack.hasTag())
            itemStack.setTag(new NBTTagCompound());

        NBTTagCompound nbtTagCompound = itemStack.getTag();
        int curr = 0;
        if (nbtTagCompound.hasKey(NBT_DEATH_COUNT))
            curr = nbtTagCompound.getInt(NBT_DEATH_COUNT);

        curr += delta;
        nbtTagCompound.setInt(NBT_DEATH_COUNT, MathHelper.clamp(curr, 0, FULLY_PROGRAMMED_COUNT));
    }

    public static @Nonnull String getItemStackDisplayName(ItemStack itemStack) {

        String displayName = "";
        if (isEntityProgrammed(itemStack)) {
            FakeMobKey fakeMobKey = getProgrammedEntity(itemStack);
            EntityType entityType = ForgeRegistries.ENTITIES.getValue(fakeMobKey.getResourceLocation());
            if (entityType != null)
                displayName =  entityType.getTranslationKey();
        }

        return displayName;

    }
}
