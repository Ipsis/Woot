package ipsis.woot.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldHelper {

    public static void updateClient(World world, BlockPos pos) {

        if (world != null) {
            IBlockState iblockstate = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, iblockstate, iblockstate, 4);
        }
    }

    public static void updateNeighbors(World world, BlockPos pos, Block b) {

        if (world != null) {
            IBlockState iBlockState = world.getBlockState(pos);
            world.notifyNeighborsOfStateChange(pos, b, false);
        }
    }

    public static void spawnInWorld(World world, BlockPos pos, ItemStack itemStack) {

        if (itemStack.getCount() > itemStack.getMaxStackSize()) {
            for (int i = 0; i < itemStack.getCount(); i++) {
                ItemStack outStack = itemStack.copy();
                outStack.setCount(1);
                EntityItem out = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, outStack);
                world.spawnEntity(out);
            }
        } else {
            ItemStack outStack = itemStack.copy();
            EntityItem out = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, outStack);
            world.spawnEntity(out);
        }
    }
}
