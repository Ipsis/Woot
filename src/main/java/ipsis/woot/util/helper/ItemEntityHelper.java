package ipsis.woot.util.helper;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemEntityHelper {

    public static @Nonnull List<ItemStack> convertToItemStacks(@Nonnull Collection<ItemEntity> itemEntityList) {
        List<ItemStack> itemStackList = new ArrayList<>(itemEntityList.size());

        for (ItemEntity itemEntity : itemEntityList) {
            ItemStack itemStack = itemEntity.getItem();
            itemStackList.add(itemStack);
        }

        return itemStackList;
    }
}
