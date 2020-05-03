package ipsis.woot.modules.factory.generators;

import ipsis.woot.modules.generic.GenericSetup;
import ipsis.woot.modules.generic.items.GenericItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

public class ShardPerkData extends WeightedRandom.Item {

    public final GenericItem.GenericItemType type;

    public ShardPerkData(GenericItem.GenericItemType type, int weight) {
        super(weight);
        this.type = type;
    }

    public ItemStack getItemStack() {
        if (type == GenericItem.GenericItemType.BASIC_UP_SHARD)
            return new ItemStack(GenericSetup.T1_SHARD_ITEM.get());
        else if (type == GenericItem.GenericItemType.ADVANCED_UP_SHARD)
            return new ItemStack(GenericSetup.T2_SHARD_ITEM.get());
        else if (type == GenericItem.GenericItemType.ELITE_UP_SHARD)
            return new ItemStack(GenericSetup.T3_SHARD_ITEM.get());

        return ItemStack.EMPTY;
    }

    @Override
    public String toString() {
        return "ShardPerkData{" +
                "type=" + type +
                ", itemWeight=" + itemWeight +
                '}';
    }
}
