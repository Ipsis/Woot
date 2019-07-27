package ipsis.woot.util.helper;

import ipsis.woot.Woot;
import ipsis.woot.loot.MobDrop;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MobDropHelper {

    static final Random RANDOM = new Random();

    public static List<ItemStack> getDrops(List<MobDrop> mobDrops) {
        List<ItemStack> drops = new ArrayList<>();
        for (MobDrop drop : mobDrops) {
            float roll = RANDOM.nextFloat();
            float dropChance = drop.getDropChance() / 100.0F; // stored as 0.0->100.0

            Woot.LOGGER.info("getDrops: {} {} {}", drop.getDroppedItem(), dropChance, roll);
            if (dropChance == 1.0F || dropChance < roll)
                drops.add(new ItemStack(drop.getDroppedItem().getItem(), 1));
        }
        return drops;
    }
}
