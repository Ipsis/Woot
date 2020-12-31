package ipsis.woot.modules.factory.items;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactorySetup;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * XP shards are created by the factory when killing mobs and the appropriate upgrade is present.
 * Each XP shard is equal to one experience.
 * 9 Shards can be combined into XP orbs/blocks with
 * The factory will create XP orbs/blocks and give XP shards as change
 */
public class XpShardBaseItem extends Item {

    public static final String SHARD_REGNAME = "xpshard";
    public static final String SPLINTER_REGNAME = "xpsplinter";

    private static final int STACK_SIZE = 64;
    public static final int SPLINTERS_IN_STACK = 9;
    private static final int SHARD_XP = 9;
    private static final int SPLINTER_XP = 1;

    final Variant variant;
    public XpShardBaseItem(Variant variant) {
        super(new Item.Properties().maxStackSize(STACK_SIZE).group(Woot.setup.getCreativeTab()));
        this.variant = variant;
    }

    public Variant getVariant() { return this.variant; }

    public enum Variant {
        SHARD,
        SPLINTER
    }

    public static ItemStack getItemStack(Variant variant) {
        if (variant == Variant.SHARD)
            return new ItemStack(FactorySetup.XP_SHARD_ITEM.get());
        return new ItemStack(FactorySetup.XP_SPLINTER_ITEM.get());
    }

    public static List<ItemStack> getShards(int xp) {
        List<ItemStack> shards = new ArrayList<>();

        int xpShards = xp / SPLINTERS_IN_STACK;
        int xpSplinters =  xp % SPLINTERS_IN_STACK;
        int fullStacks = xpShards / STACK_SIZE;
        int leftoverShard = xpShards % STACK_SIZE;

        Woot.setup.getLogger().debug("getShards: xp {} xpShards {} xpSplinters {} fullStacks {} leftoverShards {}",
                xp, xpShards, xpSplinters, fullStacks, leftoverShard);

        for (int i = 0; i < fullStacks; i++) {
            ItemStack itemStack = getItemStack(Variant.SHARD);
            itemStack.setCount(STACK_SIZE);
            shards.add(itemStack);
        }

        if (leftoverShard > 0) {
            ItemStack itemStack = getItemStack(Variant.SHARD);
            itemStack.setCount(leftoverShard);
            shards.add(itemStack);
        }

        if (xpSplinters > 0) {
            ItemStack itemStack = getItemStack(Variant.SPLINTER);
            itemStack.setCount(xpSplinters);
            shards.add(itemStack);
        }

        return shards;
    }

    private int getXp(ItemStack itemStack) {
        if (itemStack.getItem() == FactorySetup.XP_SHARD_ITEM.get())
            return SHARD_XP;
        else if (itemStack.getItem() == FactorySetup.XP_SPLINTER_ITEM.get())
            return SPLINTER_XP;
        return 0;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (worldIn.isRemote)
            return ActionResult.resultPass(playerIn.getHeldItem(handIn));

        ItemStack itemStack = playerIn.getHeldItem(handIn);
        if (itemStack.isEmpty())
            return ActionResult.resultPass(playerIn.getHeldItem(handIn));

        ItemStack advancementStack = itemStack.copy();

        worldIn.playSound(
                null,
                playerIn.getPosX(),
                playerIn.getPosY(),
                playerIn.getPosZ(),
                SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,
                SoundCategory.PLAYERS,
                0.2F,
                0.5F * ((random.nextFloat() - random.nextFloat()) * 0.7F + 1.8F));

        if (playerIn instanceof FakePlayer) {
            // Fake player can only use one at a time
            worldIn.addEntity(new ExperienceOrbEntity(
                            worldIn,
                            playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(),
                            1));
            itemStack.shrink(1);
        } else {
            int xp = 0;
            if (playerIn.isSneaking()) {
                // Consume the whole stack
                xp = getXp(itemStack) * itemStack.getCount();
                if (!playerIn.isCreative())
                    itemStack.setCount(0);
            } else {
                xp = getXp(itemStack);
                if (!playerIn.isCreative())
                    itemStack.shrink(1);
            }
            if (xp > 0) {
                playerIn.giveExperiencePoints(xp);
                if (playerIn instanceof ServerPlayerEntity)
                    CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) playerIn, advancementStack);
            }
        }
        return ActionResult.resultSuccess(itemStack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TranslationTextComponent("info.woot.shard.0"));
        tooltip.add(new TranslationTextComponent("info.woot.shard.1"));
    }
}
