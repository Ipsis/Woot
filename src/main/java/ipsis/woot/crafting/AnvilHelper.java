package ipsis.woot.crafting;

import ipsis.Woot;
import ipsis.woot.util.DebugSetup;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnvilHelper {

    public static @Nonnull
    List<EntityItem> getItems(World world, BlockPos pos) {

        List<EntityItem> items = new ArrayList<>();
        List<EntityItem> entityItemList = world.getEntitiesWithinAABB(EntityItem.class,
                new AxisAlignedBB(pos, pos.add(2,2, 2)));

        for (EntityItem entityItem : entityItemList) {
            ItemStack itemStack = entityItem.getItem();
            if (itemStack.isEmpty() || entityItem.isDead)
                continue;

            items.add(entityItem);
        }

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.ANVIL_CRAFTING, "getItems", Arrays.toString(items.toArray()));
        return items;
    }

    public static boolean isAnvilHot(@Nonnull World world, @Nonnull BlockPos pos) {

        IBlockState iBlockState = world.getBlockState(pos.down());
        Block b = iBlockState.getBlock();
        return b == Blocks.MAGMA;
    }
}
