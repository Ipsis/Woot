package ipsis.woot.modules.factory.items;

import ipsis.woot.Woot;
import ipsis.woot.advancements.Advancements;
import ipsis.woot.config.Config;
import ipsis.woot.config.ConfigOverride;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.policy.PolicyRegistry;
import ipsis.woot.policy.PolicyConfiguration;
import ipsis.woot.util.FakeMob;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Mob shard is used to hold the mob kills until the shard is turned into a mob controller.
 * A shard is full when its kill count hits a predefined level.
 */

public class MobShardItem extends Item {

    public MobShardItem() { super(new Item.Properties().maxStackSize(1).group(Woot.setup.getCreativeTab())); }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        if (attacker.getEntityWorld().isRemote || !(attacker instanceof PlayerEntity))
            return false;

        if (!(target instanceof MobEntity))
            return false;

        if (isProgrammed(stack))
            return false;

        // status messages for success
        FakeMob fakeMob = new FakeMob((MobEntity)target);
        if (!fakeMob.isValid())
            return false;

        if (!PolicyRegistry.get().canCaptureEntity(fakeMob.getResourceLocation()) || !canShardCaptureMob(fakeMob.getResourceLocation())) {
            ((PlayerEntity)attacker).sendStatusMessage(new TranslationTextComponent("chat.woot.mobshard.failure"), true);
            return false;
        }

        setProgrammedMob(stack, fakeMob);
        ((PlayerEntity)attacker).sendStatusMessage(new TranslationTextComponent("chat.woot.mobshard.success"), true);
        return true;

    }

    private static boolean canShardCaptureMob(ResourceLocation resourceLocation) {
        for (String s : PolicyConfiguration.SHARD_BLACKLIST_FULL_MOD.get())
            if (s.equalsIgnoreCase(resourceLocation.getNamespace()))
                return false;

        for (String s : PolicyConfiguration.SHARD_BLACKLIST_ENTITY.get())
            if (s.equalsIgnoreCase(resourceLocation.toString()))
                return false;
        return true;
    }

    /**
     * NBT
     */
    private static final String NBT_MOB = "Mob";
    private static final String NBT_KILLS = "Kills";

    public static boolean isProgrammed(ItemStack itemStack) {
        return getProgrammedMob(itemStack).isValid();
    }

    public static FakeMob getProgrammedMob(ItemStack itemStack) {
        FakeMob fakeMob = new FakeMob();
        CompoundNBT compoundNBT = itemStack.getOrCreateTag();
        if (itemStack.getTag().contains(NBT_MOB))
            fakeMob = new FakeMob(itemStack.getTag().getCompound(NBT_MOB));

        return fakeMob;
    }

    private void setProgrammedMob(ItemStack itemStack, FakeMob fakeMob) {
        CompoundNBT compoundNBT = itemStack.getOrCreateTag();
        CompoundNBT mobNbt = new CompoundNBT();
        FakeMob.writeToNBT(fakeMob, mobNbt);
        compoundNBT.put(NBT_MOB, mobNbt);
        compoundNBT.putInt(NBT_KILLS, 0);
    }

    private static boolean isMatchingMob(ItemStack itemStack, FakeMob fakeMob) {

        if (itemStack.getItem() != FactorySetup.MOB_SHARD_ITEM.get())
            return false;

        if (!isProgrammed(itemStack))
            return false;

        FakeMob programmedMob = getProgrammedMob(itemStack);
        if (!programmedMob.isValid())
            return false;

        return programmedMob.equals(fakeMob);
    }

    public static void handleKill(PlayerEntity playerEntity, FakeMob fakeMob) {
        ItemStack foundStack = ItemStack.EMPTY;

        // Hotbar only
        for (int i = 0; i <= 9; i++) {
            ItemStack itemStack = playerEntity.inventory.getStackInSlot(i);
            if (!itemStack.isEmpty() && isMatchingMob(itemStack, fakeMob)) {
                foundStack = itemStack;
                break;
            }
        }

        if (!foundStack.isEmpty()) {
            incrementKills(foundStack, 1);
            if (isFullyProgrammed(foundStack) && playerEntity instanceof ServerPlayerEntity)
                Advancements.MOB_CAPTURE_TRIGGER.trigger((ServerPlayerEntity) playerEntity, fakeMob);
        }
    }

    private static void incrementKills(ItemStack itemStack, int v) {

        if (itemStack.getItem() != FactorySetup.MOB_SHARD_ITEM.get())
            return;

        FakeMob fakeMob = getProgrammedMob(itemStack);
        if (!fakeMob.isValid())
            return;

        if (!PolicyRegistry.get().canCaptureEntity(fakeMob.getResourceLocation()) || !canShardCaptureMob(fakeMob.getResourceLocation()))
            return;

        int killCount = itemStack.getTag().getInt(NBT_KILLS);
        if (!isFull(itemStack)) {
            killCount += v;
            itemStack.getTag().putInt(NBT_KILLS, killCount);
        }
    }

    private static boolean isFull(ItemStack itemStack) {

        int killCount = itemStack.getTag().getInt(NBT_KILLS);
        FakeMob fakeMob = getProgrammedMob(itemStack);
        if (!fakeMob.isValid())
            return false;

        return killCount >= Config.OVERRIDE.getIntegerOrDefault(fakeMob, ConfigOverride.OverrideKey.SHARD_KILLS);
    }

    public static boolean isFullyProgrammed(ItemStack itemStack) {
        return isProgrammed(itemStack) && isFull(itemStack);
    }

    public static void setJEIEnderShard(ItemStack itemStack) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("nbt_jei_shard", 1);
        itemStack.setTag(nbt);
    }

    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return isFullyProgrammed(itemStack) || itemStack.getTag().contains("nbt_jei_shard");
    }

    /**
     * Tooltip
     */
    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TranslationTextComponent("info.woot.mobshard.0"));
        tooltip.add(new TranslationTextComponent("info.woot.mobshard.1"));
        tooltip.add(new TranslationTextComponent("info.woot.mobshard.2"));
        tooltip.add(new TranslationTextComponent("info.woot.mobshard.3"));

        FakeMob fakeMob = getProgrammedMob(stack);
        if (!fakeMob.isValid()) {
            tooltip.add(new TranslationTextComponent("info.woot.mobshard.a.0"));
            return;
        }

        EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(fakeMob.getResourceLocation());
        if (entityType != null)
            tooltip.add(new TranslationTextComponent(entityType.getTranslationKey()));
        if (fakeMob.hasTag())
            tooltip.add(new StringTextComponent("[" + fakeMob.getTag() + "]"));

        int killCount = stack.getTag().getInt(NBT_KILLS);
        if (isFull(stack)) {
            tooltip.add(new TranslationTextComponent("info.woot.mobshard.a.1"));
        } else {
            tooltip.add(new TranslationTextComponent("info.woot.mobshard.b.0",
                    killCount,
                    Config.OVERRIDE.getIntegerOrDefault(fakeMob, ConfigOverride.OverrideKey.SHARD_KILLS)));
        }
    }
}
