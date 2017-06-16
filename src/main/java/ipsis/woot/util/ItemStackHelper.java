package ipsis.woot.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Random;

public class ItemStackHelper {

    private static Random RANDOM = new Random();

    /* Straight from InventoryHelper in vanilla code */
    public static void spawnInWorld(World world, BlockPos pos, ItemStack itemStack) {

        float f = RANDOM.nextFloat() * 0.8F + 0.1F;
        float f1 = RANDOM.nextFloat() * 0.8F + 0.1F;
        float f2 = RANDOM.nextFloat() * 0.8F + 0.1F;

        while (!itemStack.isEmpty()) {
            int i = RANDOM.nextInt(21) + 10;

            if (i > itemStack.getCount())
                i = itemStack.getCount();

            itemStack.shrink(i);
            EntityItem entityitem = new EntityItem(world, pos.getX() + (double)f, pos.getY() + (double)f1, pos.getZ() + (double)f2,
                    new ItemStack(itemStack.getItem(), i, itemStack.getMetadata()));

            if (itemStack.hasTagCompound())
                entityitem.getItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());

            float f3 = 0.05F;
            entityitem.motionX = RANDOM.nextGaussian() * (double) f3;
            entityitem.motionY = RANDOM.nextGaussian() * (double) f3 + 0.20000000298023224D;
            entityitem.motionZ = RANDOM.nextGaussian() * (double) f3;
            world.spawnEntity(entityitem);
        }
    }

    public static ResourceLocation getItemName(Item item) {

        if (item == null)
            return null;

        return ForgeRegistries.ITEMS.getKey(item);
    }

    public static String getItemStackName(ItemStack itemStack) {

        if (itemStack == null)
            return null;

        Item item = itemStack.getItem();
        if (item == null)
            return null;

        ResourceLocation resourceLocation = getItemName(item);
        if (resourceLocation == null)
            return null;

        String ret;
        if (!itemStack.isItemStackDamageable()) {
            int dmg = itemStack.getItemDamage();
            if (dmg != OreDictionary.WILDCARD_VALUE)
                ret = String.format("%s:%d", resourceLocation.toString(), dmg);
            else
                ret = resourceLocation.toString();
        } else  {
            ret = resourceLocation.toString();
        }

        return ret;
    }

    /**
     * Splits a mod:item:meta or mod:item into a itemstack - I hope
     */
    public static ItemStack getItemStackFromName(String name) {

        if (name == null)
            return null;

        String[] parts = name.split(":");
        if (parts.length != 2 && parts.length != 3)
            return null;

        String itemName = String.format("%s:%s", parts[0], parts[1]);
        Item item = Item.getByNameOrId(itemName);

        if (item == null)
            return null;

        int meta = 0;
        if (parts.length == 3) {
            try {
                meta = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return null;
            }
            if (meta < 0 || meta > OreDictionary.WILDCARD_VALUE)
                return null;
        }

        return new ItemStack(item, 1, meta);
    }

}
