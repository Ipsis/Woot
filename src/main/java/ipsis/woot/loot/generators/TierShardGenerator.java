package ipsis.woot.loot.generators;

import ipsis.Woot;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.init.ModItems;
import ipsis.woot.loot.LootGenerationFarmInfo;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.DebugSetup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;

public class TierShardGenerator implements ILootGenerator {

    @Override
    public void generate(World world, LootGenerationFarmInfo farmInfo) {

        if (farmInfo.itemHandlerList.size() == 0)
            return;

        ItemStack tier2Shard = ItemStack.EMPTY;
        ItemStack tier3Shard = ItemStack.EMPTY;
        ItemStack tier4Shard = ItemStack.EMPTY;

        int rolled = Woot.RANDOM.nextInt(101);
        int chance = Woot.wootConfiguration.getInteger(EnumConfigKey.T2_SHARD_GEN);

        if (rolled <= chance) {
            tier2Shard = new ItemStack(ModItems.itemShard, 1, 4);
        }

        rolled = Woot.RANDOM.nextInt(101);
        chance = Woot.wootConfiguration.getInteger(EnumConfigKey.T3_SHARD_GEN);
        if (rolled <= chance) {
            tier3Shard = new ItemStack(ModItems.itemShard, 1, 5);
        }

        rolled = Woot.RANDOM.nextInt(101);
        chance = Woot.wootConfiguration.getInteger(EnumConfigKey.T4_SHARD_GEN);
        if (rolled <= chance) {
            tier4Shard = new ItemStack(ModItems.itemShard, 1, 6);
        }

        List<ItemStack> drops = new ArrayList<>();
        if (Woot.wootConfiguration.getBoolean(EnumConfigKey.ALLOW_SHARD_RECIPES)) {
            if (farmInfo.farmSetup.getFarmTier() == EnumMobFactoryTier.TIER_ONE) {
                drops.add(tier2Shard);
            } else if (farmInfo.farmSetup.getFarmTier() == EnumMobFactoryTier.TIER_TWO) {
                drops.add(tier2Shard);
                drops.add(tier3Shard);
            } else if (farmInfo.farmSetup.getFarmTier() == EnumMobFactoryTier.TIER_THREE || farmInfo.farmSetup.getFarmTier() == EnumMobFactoryTier.TIER_FOUR) {
                drops.add(tier2Shard);
                drops.add(tier3Shard);
                drops.add(tier4Shard);
            }
        }


        for (IItemHandler hdlr : farmInfo.itemHandlerList) {
            for (ItemStack itemStack : drops) {

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
    }
}
