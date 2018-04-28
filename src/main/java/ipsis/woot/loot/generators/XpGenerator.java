package ipsis.woot.loot.generators;

import ipsis.Woot;
import ipsis.woot.init.ModItems;
import ipsis.woot.loot.LootGenerationFarmInfo;
import ipsis.woot.util.ConfigKeyHelper;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.EnumFarmUpgrade;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class XpGenerator implements ILootGenerator {

    private static final int XP_CHUNKS = 16;

    @Override
    public void generate(World world, LootGenerationFarmInfo farmInfo) {

        if (farmInfo == null)
            return;

        if (farmInfo.itemHandlerList.size() == 0)
            return;

        if (!farmInfo.farmSetup.hasUpgrade(EnumFarmUpgrade.XP))
            return;

        int storedXp = farmInfo.farmSetup.getStoredXp();
        // This is to catch older factories
        storedXp %= XP_CHUNKS;

        int deathXp = Woot.wootConfiguration.getDeathCost(world, farmInfo.farmSetup.getWootMobName());
        float increase = Woot.wootConfiguration.getInteger(farmInfo.farmSetup.getWootMobName(), ConfigKeyHelper.getXpParam(farmInfo.farmSetup.getUpgradeLevel(EnumFarmUpgrade.XP)));
        deathXp *= farmInfo.farmSetup.getNumMobs();
        int totalXp = storedXp + deathXp;
        float extraXp = ((increase - 100.0F) / 100.0F) * totalXp;
        totalXp += (int)extraXp;

        int generate = totalXp / XP_CHUNKS;

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.GEN_XP, "generate",
                "storedXp:" + storedXp + " deathXp:" + deathXp + " totalXp:" + totalXp + " generate:" + generate);

        if (generate != 0) {
            ItemStack itemStack = new ItemStack(ModItems.itemXpShard, generate);
            for (IItemHandler hdlr : farmInfo.itemHandlerList) {
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

        storedXp = totalXp - (generate * XP_CHUNKS);
        storedXp %= XP_CHUNKS;
        farmInfo.farmSetup.setStoredXp(storedXp);
    }
}
