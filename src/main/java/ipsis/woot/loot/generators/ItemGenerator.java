package ipsis.woot.loot.generators;

import ipsis.Woot;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.loot.repository.ILootRepositoryLookup;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.LootHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ItemGenerator implements ILootGenerator {

    private boolean shouldEnchant(DifficultyInstance difficultyInstance) {

        return Woot.RANDOM.nextFloat() < (0.25F * difficultyInstance.getClampedAdditionalDifficulty());
    }

    private void stripEnchant(ItemStack itemStack) {

        if (itemStack.hasTagCompound())
            itemStack.getTagCompound().removeTag("ench");
    }

    private void addEnchant(ItemStack itemStack, DifficultyInstance difficulty) {

        if (!itemStack.isItemEnchantable())
            return;

        float f = difficulty.getClampedAdditionalDifficulty();
        boolean allowTreasure = false;
        EnchantmentHelper.addRandomEnchantment(Woot.RANDOM, itemStack, (int)(5.0F + f * (float)Woot.RANDOM.nextInt(18)), allowTreasure);
    }

    private void damageItem(ItemStack itemStack) {

        if (!itemStack.isItemStackDamageable())
            return;

        int dmg = Woot.RANDOM.nextInt(itemStack.getMaxDamage() + 1);
        dmg = MathHelper.clamp(dmg, 1, itemStack.getMaxDamage());
        itemStack.setItemDamage(dmg);
    }

    private List<ItemStack> calculateDrops(List<ILootRepositoryLookup.LootItemStack> loot, DifficultyInstance difficulty) {

        boolean shouldEnchant = shouldEnchant(difficulty);
        List<ItemStack> drops = new ArrayList<>();

        for (ILootRepositoryLookup.LootItemStack drop : loot) {

            if (drop.itemStack.isEmpty())
                continue;

            int chance = Woot.RANDOM.nextInt(101);
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_ITEMS, "calculateDrops", drop + " rolled:" + chance);
            int stackSize = 0;
            for (int s : drop.sizes.keySet()) {
                if (chance <= drop.sizes.get(s) && s > stackSize)
                    stackSize = s;
            }

            if (stackSize == 0)
                continue;

            ItemStack itemStack = drop.itemStack.copy();
            itemStack.setCount(stackSize);
            if (itemStack.isItemStackDamageable())
                damageItem(itemStack);

            // Cycle existing enchantment otherwise add a random enchant to an un-enchanted item
            if (itemStack.isItemEnchanted()) {
                shouldEnchant = false;
                stripEnchant(itemStack);
                addEnchant(itemStack, difficulty);
            } else if (itemStack.isItemEnchantable() && shouldEnchant) {
                addEnchant(itemStack, difficulty);
            }

            drops.add(itemStack);
        }

        return drops;
    }

    public void generate(World world, @Nonnull List<IFluidHandler> fluidHandlerList, @Nonnull List<IItemHandler> itemHandlerList, @Nonnull IFarmSetup farmSetup, DifficultyInstance difficulty) {

        if (itemHandlerList.size() == 0)
            return;

        List<ILootRepositoryLookup.LootItemStack> loot =  LootHelper.getDrops(farmSetup.getWootMobName(), farmSetup.getEnchantKey());
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_ITEMS, "generate", farmSetup.getNumMobs() + "*" + farmSetup.getWootMobName());
        for (int i = 0; i < farmSetup.getNumMobs(); i++) {
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_ITEMS, "generate", "Generating loot for mob " + i);
            List<ItemStack> mobLoot = calculateDrops(loot, difficulty);

            for (IItemHandler hdlr : itemHandlerList) {
                for (ItemStack itemStack : mobLoot) {

                    if (itemStack.isEmpty())
                        continue;

                    boolean success = true;
                    while (success && !itemStack.isEmpty()) {

                        /**
                         * We try to insert 1 item and decrease itemStack.stackSize
                         * if it is successfull
                         * This is not very efficient
                         */
                        ItemStack result = ItemHandlerHelper.insertItem(hdlr, ItemHandlerHelper.copyStackWithSize(itemStack, 1), false);
                        if (result.isEmpty()) {
                            Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_ITEMS, "generate", "Placed itemstack of size 1 into inventory " + itemStack.getDisplayName());
                            itemStack.shrink(1);
                        } else {
                            success = false;
                        }
                    }
                }
            }

            mobLoot.clear();
        }
    }
}
