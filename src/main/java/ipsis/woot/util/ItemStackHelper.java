package ipsis.woot.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class ItemStackHelper {

    private static Random RANDOM = new Random();

    /* Straight from InventoryHelper in vanilla code */
    public static void spawnInWorld(World world, BlockPos pos, ItemStack itemStack) {

        float f = RANDOM.nextFloat() * 0.8F + 0.1F;
        float f1 = RANDOM.nextFloat() * 0.8F + 0.1F;
        float f2 = RANDOM.nextFloat() * 0.8F + 0.1F;

        while (itemStack.stackSize > 0) {
            int i = RANDOM.nextInt(21) + 10;

            if (i > itemStack.stackSize)
                i = itemStack.stackSize;

            itemStack.stackSize -= i;
            EntityItem entityitem = new EntityItem(world, pos.getX() + (double)f, pos.getY() + (double)f1, pos.getZ() + (double)f2,
                    new ItemStack(itemStack.getItem(), i, itemStack.getMetadata()));

            if (itemStack.hasTagCompound())
                entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());

            float f3 = 0.05F;
            entityitem.motionX = RANDOM.nextGaussian() * (double) f3;
            entityitem.motionY = RANDOM.nextGaussian() * (double) f3 + 0.20000000298023224D;
            entityitem.motionZ = RANDOM.nextGaussian() * (double) f3;
            world.spawnEntityInWorld(entityitem);
        }
    }
}
