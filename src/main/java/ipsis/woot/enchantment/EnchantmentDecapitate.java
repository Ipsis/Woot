package ipsis.woot.enchantment;

import ipsis.Woot;
import ipsis.woot.init.ModEnchantments;
import ipsis.woot.oss.LogHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.SkeletonType;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.List;

public class EnchantmentDecapitate extends Enchantment {

    private static final String NAME = "woot_decapitate";

    public EnchantmentDecapitate() {

        super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND});
        setName(NAME);
        setRegistryName(NAME);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 16;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 60;
    }

    public static boolean hasEnchantmentDecapitate(ItemStack itemStack) {

        if (ModEnchantments.DECAPITATE != null)
            return EnchantmentHelper.getEnchantmentLevel(ModEnchantments.DECAPITATE, itemStack) > 0;

        return false;
    }

    private static EntityItem createEntityItem(World world, ItemStack itemStack, double x, double y, double z) {

        if (world == null || itemStack == null)
            return null;

        EntityItem entityItem = new EntityItem(world, x, y, z, itemStack);
        entityItem.setDefaultPickupDelay();

        return entityItem;
    }

    /**
     * Returns true if there is ANY skull already in the drops
     */
    private static boolean containsSkull(List<EntityItem> drops) {

        boolean found = false;
        for (EntityItem i : drops) {
            if (i != null && i.getEntityItem().getItem() == Items.SKULL) {
                found = true;
                break;
            }
        }

        return found;
    }

    private static final float DECAPITATE_CHANCE = 20.0F;
    private static boolean hasDecapitated() {

        return Woot.RANDOM.nextFloat() <= DECAPITATE_CHANCE / 100.0F;
    }

    public static void handleLivingDrops(LivingDropsEvent e) {

        if (e.getSource().getEntity() == null)
            return;

        if (hasDecapitated() && !containsSkull(e.getDrops())) {

            ItemStack itemStack = Woot.headRegistry.getVanillaHead((EntityLiving)e.getEntityLiving());

            if (e.getEntityLiving() instanceof EntityCreeper) {
                itemStack = new ItemStack(Items.SKULL, 1, 4);
            } else if (e.getEntityLiving() instanceof EntityZombie) {
                itemStack = new ItemStack(Items.SKULL, 1, 2);
            } else if (e.getEntityLiving() instanceof EntitySkeleton) {
                EntitySkeleton entitySkeleton = (EntitySkeleton)e.getEntityLiving();
                if (entitySkeleton.getSkeletonType() == SkeletonType.NORMAL) {
                    itemStack = new ItemStack(Items.SKULL, 1, 0);
                }
            }

            // Ignore wither skeleton
            if (e.getEntityLiving() instanceof EntitySkeleton && ((EntitySkeleton) e.getEntityLiving()).getSkeletonType() == SkeletonType.WITHER)
                itemStack = null;

            if (itemStack != null) {

                EntityItem entityItem = createEntityItem(
                        e.getSource().getEntity().worldObj,
                        itemStack,
                        e.getEntityLiving().getPosition().getX(),
                        e.getEntityLiving().getPosition().getY(),
                        e.getEntityLiving().getPosition().getZ());

                if (entityItem != null)
                    e.getDrops().add(entityItem);
            }
        }
    }

}
