package ipsis.woot.loot.generators;

import ipsis.Woot;
import ipsis.woot.farmstructure.IFarmSetup;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumFarmUpgrade;
import ipsis.woot.util.SkullHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.List;

public class DecapitationGenerator implements ILootGenerator {

    /**
     * ILootGenerator
     */
    public void generate(World world, @Nonnull List<IFluidHandler> fluidHandlerList, @Nonnull List<IItemHandler> itemHandlerList, @Nonnull IFarmSetup farmSetup, DifficultyInstance difficulty) {

        if (itemHandlerList.size() == 0)
            return;

        if (!farmSetup.hasUpgrade(EnumFarmUpgrade.DECAPITATE))
            return;

        ItemStack skull = SkullHelper.getSkull(farmSetup.getWootMobName());
        if (skull.isEmpty()) {
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_HEADS, "generate", "No heads for " + farmSetup.getWootMobName());
            return;
        }

        ItemStack itemStack = skull.copy();
        itemStack.setCount(0);
        int decap = Woot.wootConfiguration.getInteger(farmSetup.getWootMobName(), ConfigKeyHelper.getDecapParam(farmSetup.getUpgradeLevel(EnumFarmUpgrade.DECAPITATE)));
        float chance = decap / 100.0F;

        for (int m = 0; m < farmSetup.getNumMobs(); m++) {

            float v = Woot.RANDOM.nextFloat();
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_HEADS, "generate", decap + " " + v + " <= " + chance);
            if (v <= chance)
                itemStack.setCount(itemStack.getCount() + 1);
        }

        for (IItemHandler hdlr : itemHandlerList) {
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
                    itemStack.shrink(1);
                    Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_HEADS, "generate", "placed skull in itemhandler with slots " + hdlr.getSlots());
                } else {
                    success = false;
                }
            }
        }

    }
}
