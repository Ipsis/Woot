package ipsis.woot.shards;

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
 * The first time the level is hit, then the shard is full.
 * Changing the predefined levels does NOT make previously full shards empty again.
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

        // TODO check policy canCapture
        /*
        PlayerHelper.sendActionBarMessage((PlayerEntity)attacker,
                new TranslationTextComponent("chat.woot.mobshard.failure").getFormattedText());
         */
        // status messages for success
        FakeMob fakeMob = new FakeMob((MobEntity)target);
        if (!fakeMob.isValid())
            return false;

        setProgrammedMob(stack, fakeMob);
        PlayerHelper.sendActionBarMessage((PlayerEntity)attacker,
                new TranslationTextComponent("chat.woot.mobshard.success").getFormattedText());
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

    private void incrementKills(ItemStack itemStack, int v) {

        FakeMob fakeMob = getProgrammedMob(itemStack);
        if (!fakeMob.isValid())
            return;

        int killCount = itemStack.getTag().getInt(NBT_KILLS);
        if (!isFull(itemStack)) {
            killCount += v;
            itemStack.getTag().putInt(NBT_KILLS, killCount);
        }
    }

    private boolean isFull(ItemStack itemStack) {

        int killCount = itemStack.getTag().getInt(NBT_KILLS);
        return killCount >= 5;
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

        // TODO show the mob, kill count, full etc
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
                    killCount, 5));
        }
    }
}
