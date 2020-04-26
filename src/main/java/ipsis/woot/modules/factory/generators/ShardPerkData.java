package ipsis.woot.modules.factory.generators;

import ipsis.woot.modules.generic.GenericItemType;
import ipsis.woot.modules.generic.GenericSetup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

public class ShardPerkData extends WeightedRandom.Item {

    public final GenericItemType type;

    public ShardPerkData(GenericItemType type, int weight) {
        super(weight);
        this.type = type;
    }

    public ItemStack getItemStack() {
        if (type == GenericItemType.BASIC_UP_SHARD)
            return new ItemStack(GenericSetup.T1_SHARD_ITEM.get());
        else if (type == GenericItemType.ADVANCED_UP_SHARD)
            return new ItemStack(GenericSetup.T2_SHARD_ITEM.get());
        else if (type == GenericItemType.ELITE_UP_SHARD)
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
