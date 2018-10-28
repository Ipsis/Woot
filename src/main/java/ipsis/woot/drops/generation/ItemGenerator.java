package ipsis.woot.drops.generation;

import ipsis.Woot;
import ipsis.woot.drops.MobDropData;
import ipsis.woot.util.Debug;
import ipsis.woot.util.helpers.ConnectedCapHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemGenerator implements IGenerator {

    private static final Random RANDOM = new Random();

    /**
     * IGenerator
     */
    @Override
    public void generate(World world, LootGenerator.Setup setup) {

        if (setup.itemHandlers.isEmpty())
            return;

        if (Woot.debugging.isEnabled(Debug.Group.GEN_ITEMS))
            Woot.debugging.trace(Debug.Group.GEN_ITEMS, "ItemGenerator:generate for {} looting {}", setup.fakeMobKey, setup.looting);

        MobDropData mobDropData = Woot.DROP_MANAGER.getMobDropData(setup.fakeMobKey, setup.looting);
        if (mobDropData.getDrops().isEmpty()) {
            if (Woot.debugging.isEnabled(Debug.Group.GEN_ITEMS))
                Woot.debugging.trace(Debug.Group.GEN_ITEMS, "ItemGenerator:generate no drops");

            return;
        }

        for (int mob = 0; mob < setup.numMobs; mob++) {

            if (Woot.debugging.isEnabled(Debug.Group.GEN_ITEMS))
                Woot.debugging.trace(Debug.Group.GEN_ITEMS, "ItemGenerator:generate mob {} of {}", mob, setup.numMobs);

            List<ItemStack> drops = getActualDrops(mobDropData, setup.difficulty);
            for (ItemStack itemStack : drops) {

                if (itemStack.isEmpty())
                    continue;

                for (ConnectedCapHelper.ConnectedItemHandler connectedItemHandler : setup.itemHandlers) {

                    int size = itemStack.getCount();
                    ItemStack resultStack = ItemHandlerHelper.insertItem(connectedItemHandler.iItemHandler, ItemHandlerHelper.copyStackWithSize(itemStack, size), false);
                    size = size - resultStack.getCount();

                    if (Woot.debugging.isEnabled(Debug.Group.GEN_ITEMS)) {
                        Woot.debugging.trace(Debug.Group.GEN_ITEMS, "ItemGenerator:generate place {} {}", itemStack.getCount(), itemStack.getDisplayName());
                        Woot.debugging.trace(Debug.Group.GEN_ITEMS, "ItemGenerator:generate placed {} leaving {}", resultStack.getCount(), size);
                    }

                    itemStack.setCount(size);
                }

            }
        }
    }

    private List<ItemStack> getActualDrops(MobDropData mobDropData, DifficultyInstance difficulty) {

        List<ItemStack> drops = new ArrayList<>();

        for (MobDropData.DropData dropData : mobDropData.getDrops()) {

            ItemStack itemStack = dropData.getItemStack();
            if (itemStack.isEmpty())
                continue;

            if (!Woot.POLICY_MANAGER.canDropItem(itemStack)) {
                if (Woot.debugging.isEnabled(Debug.Group.GEN_ITEMS))
                    Woot.debugging.trace(Debug.Group.GEN_ITEMS, "ItemGenerator:getActualDrops policy removed {}", itemStack.getDisplayName());

                continue;
            }

            // Rolls between 0.0 >= val < 1.0
            float rolled = RANDOM.nextFloat() * 100.0F;
            if (Woot.debugging.isEnabled(Debug.Group.GEN_ITEMS)) {
                Woot.debugging.trace(Debug.Group.GEN_ITEMS, "ItemGenerator:getActualDrops rolled {}", rolled);
                Woot.debugging.trace(Debug.Group.GEN_ITEMS, "ItemGenerator:getActualDrops drop {}", dropData.getDropChance());
            }

            if (dropData.getDropChance() == 100.0F || rolled <= dropData.getDropChance()) {
                Woot.debugging.trace(Debug.Group.GEN_ITEMS, "ItemGenerator:getActualDrops success");

                int dropStackSize = 1;
                {
                    // TODO https://stackoverflow.com/questions/6409652/random-weighted-selection-in-java/30362366
                }

                Woot.debugging.trace(Debug.Group.GEN_ITEMS, "ItemGenerator:getActualDrops dropping {}", dropStackSize);
                itemStack.setCount(dropStackSize);
                drops.add(itemStack);

            } else {
                Woot.debugging.trace(Debug.Group.GEN_ITEMS, "ItemGenerator:getActualDrops failed");
            }
        }

        return drops;
    }
}
