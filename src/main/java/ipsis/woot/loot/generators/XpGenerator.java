package ipsis.woot.loot.generators;

import ipsis.Woot;
import ipsis.woot.init.ModItems;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumFarmUpgrade;
import ipsis.woot.farmstructure.IFarmSetup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.List;

public class XpGenerator implements ILootGenerator {

    private static final int XP_CHUNKS = 16;

    public void generate(World world, @Nonnull List<IFluidHandler> fluidHandlerList, @Nonnull List<IItemHandler> itemHandlerList, @Nonnull IFarmSetup farmSetup) {

        if (itemHandlerList.size() == 0)
            return;

        if (!farmSetup.hasUpgrade(EnumFarmUpgrade.XP))
            return;

        int storedXp = farmSetup.getStoredXp();

        int deathXp = Woot.wootConfiguration.getDeathCost(world, farmSetup.getWootMobName());
        deathXp *= farmSetup.getNumMobs();
        int totalXp = storedXp + deathXp;

        int generate = totalXp / XP_CHUNKS;

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_XP, "generate",
                "storedXp:" + storedXp + " deathXp:" + deathXp + " totalXp:" + totalXp + " generate:" + generate);

        if (generate != 0) {
            ItemStack itemStack = new ItemStack(ModItems.itemXpShard, generate);
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
                        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_XP, "generate", "placed xp in itemhandler with slots " + hdlr.getSlots());
                    } else {
                        success = false;
                    }
                }
            }
        }

        farmSetup.setStoredXp(totalXp - (generate * XP_CHUNKS));
    }
}
