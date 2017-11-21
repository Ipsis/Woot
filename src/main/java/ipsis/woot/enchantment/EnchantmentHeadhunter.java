package ipsis.woot.enchantment;

import ipsis.Woot;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Reference;
import ipsis.woot.util.SkullHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.List;

public class EnchantmentHeadhunter extends Enchantment {

    private static final String NAME = "headhunter";

    public EnchantmentHeadhunter() {

        super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND});
        setName(NAME);
        setRegistryName(NAME);
    }

    @Override
    public String getName() {
        return "enchant." + Reference.MOD_ID + "." + NAME;
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

    private static EntityItem createEntityItem(World world, ItemStack itemStack, double x, double y, double z) {

        if (world == null || itemStack == null)
            return null;

        EntityItem entityItem = new EntityItem(world, x, y, z, itemStack);
        entityItem.setDefaultPickupDelay();

        return entityItem;
    }

    /**
     * Returns true if there is ANY vanilla skull already in the drops
     */
    private static boolean containsSkull(List<EntityItem> drops) {

        boolean found = false;
        for (EntityItem i : drops) {
            if (i != null && i.getItem().getItem() == Items.SKULL) {
                found = true;
                break;
            }
        }

        return found;
    }

    private static final float DECAPITATE_CHANCE = 30.0F;
    private static boolean hasDecapitated() {

        return Woot.RANDOM.nextFloat() <= DECAPITATE_CHANCE / 100.0F;
    }

    public static void handleLivingDrops(LivingDropsEvent e) {

        Entity perp = e.getSource().getTrueSource();
        if (perp == null || perp instanceof  FakePlayer)
            return;

        EntityLivingBase whatDied = e.getEntityLiving();

        /* Ignore wither skeletons, only for use on other mobs */
        if (whatDied instanceof EntityWitherSkeleton)
            return;

        if (whatDied instanceof EntityPlayer || !(whatDied instanceof EntityLiving))
            return;

        if (hasDecapitated() && !containsSkull(e.getDrops())) {

            ItemStack itemStack = SkullHelper.getSkull((EntityLiving) e.getEntityLiving());
            if (!itemStack.isEmpty()) {
                EntityItem entityItem = createEntityItem(
                        e.getSource().getTrueSource().getEntityWorld(),
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
