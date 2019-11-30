package ipsis.woot.shards;

import ipsis.woot.common.configuration.Config;
import ipsis.woot.common.configuration.Policy;
import ipsis.woot.mod.ModItems;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.WootItem;
import ipsis.woot.util.helper.PlayerHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
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

public class MobShardItem extends WootItem {

    public static final String REGNAME = "mobshard";

    public MobShardItem() {
        super(new Item.Properties().maxStackSize(1), REGNAME);
    }

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

        if (!Policy.get().canCaptureEntity(fakeMob.getResourceLocation()) || !canShardCaptureMob(fakeMob.getResourceLocation())) {
            PlayerHelper.sendActionBarMessage((PlayerEntity)attacker,
                    new TranslationTextComponent("chat.woot.mobshard.failure").getFormattedText());
            return false;
        }

        setProgrammedMob(stack, fakeMob);
        PlayerHelper.sendActionBarMessage((PlayerEntity)attacker,
                new TranslationTextComponent("chat.woot.mobshard.success").getFormattedText());
        return true;

    }

    private static boolean canShardCaptureMob(ResourceLocation resourceLocation) {
        for (String s : Config.COMMON.SHARD_BLACKLIST_FULL_MOD.get())
            if (s.equalsIgnoreCase(resourceLocation.getNamespace()))
                return false;

        for (String s : Config.COMMON.SHARD_BLACKLIST_ENTITY.get())
            if (s.equalsIgnoreCase(resourceLocation.toString()))
                return false;
        return true;
    }

    /**
     * NBT
     */
    private static final String NBT_MOB = "mob";
    private static final String NBT_KILLS = "kills";

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

        if (itemStack.getItem() != ModItems.MOB_SHARD_ITEM)
            return false;

        if (!isProgrammed(itemStack))
            return false;

        FakeMob programmedMob = getProgrammedMob(itemStack);
        if (!programmedMob.isValid())
            return false;

        return programmedMob.getEntityKey().equals(fakeMob.getEntityKey());
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

        if (!foundStack.isEmpty())
            incrementKills(foundStack, 1);
    }

    private static void incrementKills(ItemStack itemStack, int v) {

        if (itemStack.getItem() != ModItems.MOB_SHARD_ITEM)
            return;

        FakeMob fakeMob = getProgrammedMob(itemStack);
        if (!fakeMob.isValid())
            return;

        if (!Policy.get().canCaptureEntity(fakeMob.getResourceLocation()) || !canShardCaptureMob(fakeMob.getResourceLocation()))
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

        return killCount >= Config.getMobShardKills(fakeMob);
    }

    public static boolean isFullyProgrammed(ItemStack itemStack) {
        return isProgrammed(itemStack) && isFull(itemStack);
    }

    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return isFullyProgrammed(itemStack);
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

        int killCount = stack.getTag().getInt(NBT_KILLS);
        if (isFull(stack)) {
            tooltip.add(new TranslationTextComponent("info.woot.mobshard.a.1"));
        } else {
            tooltip.add(new TranslationTextComponent("info.woot.mobshard.b.0",
                    killCount, Config.getMobShardKills(fakeMob)));
        }
    }
}
