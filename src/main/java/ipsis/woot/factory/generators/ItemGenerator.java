package ipsis.woot.factory.generators;

import ipsis.woot.Woot;
import ipsis.woot.config.PolicyConfig;
import ipsis.woot.drops.DropRegistry;
import ipsis.woot.drops.MobDropData;
import ipsis.woot.factory.multiblock.FactoryConfig;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helper.RandomHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ItemGenerator implements ILootGenerator {

    @Override
    public void generate(@Nonnull World world, @Nonnull BlockPos blockPos, @Nonnull FactoryConfig factoryConfig) {

        for (FakeMobKey fakeMobKey : factoryConfig.getValidMobs()) {
            MobDropData mobDropData = DropRegistry.DROP_REGISTRY.getMobDropData(fakeMobKey, factoryConfig.getLooting());
            if (mobDropData.getDrops().isEmpty())
                continue;

            int numMobs = factoryConfig.getNumMobs(fakeMobKey);
            for (int mob = 0; mob < numMobs; mob++) {
                List<ItemStack> drops = createDropList(mobDropData, world.getDifficultyForLocation(blockPos));
                if (drops.isEmpty())
                    continue;

                for (EnumFacing facing : EnumFacing.values()) {
                    TileEntity te = world.getTileEntity(factoryConfig.getExportPos().offset(facing));
                    if (te == null)
                        continue;

                    if (te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite())
                            .map(hndlr -> {
                                for (ItemStack itemStack : drops) {
                                    if (itemStack.isEmpty())
                                        continue;

                                    ItemStack resultStack = ItemHandlerHelper.insertItem(hndlr, itemStack.copy(), false);
                                    if (resultStack.isEmpty())
                                        itemStack.setCount(0);
                                    else
                                        itemStack.shrink(itemStack.getCount() - resultStack.getCount());
                                }
                                return true;
                            })
                            .orElse(false)
                    ) {
                    }
                }
            }
        }
    }

    private List<ItemStack> createDropList(MobDropData mobDropData, DifficultyInstance difficultyInstance) {

        List<ItemStack> drops = new ArrayList<>();
        for (MobDropData.DropData dropData : mobDropData.getDrops()) {
            ItemStack itemStack = dropData.getItemStack();
            if (itemStack.isEmpty())
                continue;

            if (!PolicyConfig.canDrop(itemStack))
                continue;

            if (!RandomHelper.willDrop(dropData.getChance()))
                continue;

            int dropStackSize = 1;
            itemStack.setCount(dropStackSize);
            drops.add(itemStack);
        }
        return drops;
    }
}
