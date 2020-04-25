package ipsis.woot.modules.factory.items;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactorySetup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
}
