package ipsis.woot.enchantment;

import ipsis.Woot;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Reference;
import ipsis.woot.util.DebugSetup;
import ipsis.woot.util.SkullHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        return 3;
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

        if (world == null || itemStack.isEmpty())
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

    private static boolean hasDecapitated(int level) {

        if (level < 1)
            return false;

        float chance;
        if (level == 1)
            chance = (float)Woot.wootConfiguration.getInteger(EnumConfigKey.HEADHUNTER_1_CHANCE);
        else if (level == 2)
            chance = (float)Woot.wootConfiguration.getInteger(EnumConfigKey.HEADHUNTER_2_CHANCE);
        else if (level == 3)
            chance = (float)Woot.wootConfiguration.getInteger(EnumConfigKey.HEADHUNTER_3_CHANCE);
        else
            return false;

        float rolled = Woot.RANDOM.nextFloat();
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.DECAP, "EnchantmentHeadhunter:hasDecapitated", "rolled:" + rolled + " chance:" + (chance / 100.0F));

        return rolled <= chance / 100.0F;
    }

    private static Map<Enchantment, Integer> getEnchantMap(EntityPlayer entityPlayer) {

        if (entityPlayer == null)
            return Collections.emptyMap();

        ItemStack itemStack = entityPlayer.getHeldItemMainhand();
        if (itemStack.isEmpty())
            return Collections.emptyMap();

        return EnchantmentHelper.getEnchantments(itemStack);
    }

    private static int getHeadhunterLevel(Map<Enchantment, Integer> enchantmentIntegerMap) {

        for (Enchantment e : enchantmentIntegerMap.keySet())
            if (e instanceof EnchantmentHeadhunter)
                return enchantmentIntegerMap.get(e);

        return 0;
    }

    public static void handleLivingDrops(LivingDropsEvent e) {

        Woot.debugSetup.trace(DebugSetup.EnumDebugType.DECAP, "EnchantmentHeadhunter:handleLivingDrops", e);

        // Only handle kills made by a player
        if (!(e.getSource().getTrueSource() instanceof EntityPlayer))
            return;

        // Only handle mobs being killed
        if (!(e.getEntityLiving() instanceof EntityLiving))
            return;

        EntityPlayer entityPlayer = (EntityPlayer)e.getSource().getTrueSource();
        EntityLiving entityLiving = (EntityLiving)e.getEntityLiving();

        // Ignore fake player kills
        if (entityPlayer instanceof FakePlayer)
            return;

        // Ignore wither skeletons, only for use on other mobs
        if (entityLiving instanceof EntityWitherSkeleton)
            return;

        // Player must be holding a headhunter enchanted weapon
        int level = getHeadhunterLevel(getEnchantMap(entityPlayer));
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.DECAP, "EnchantmentHeadhunter:handleLivingDrops", "headhunterLevel:" + level);

        if (hasDecapitated(level) && !containsSkull(e.getDrops())) {

            ItemStack itemStack = SkullHelper.getSkull(entityLiving);
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.DECAP, "EnchantmentHeadhunter:handleLivingDrops", "skull:" + itemStack);
            if (!itemStack.isEmpty()) {
                EntityItem entityItem = createEntityItem(
                        e.getSource().getTrueSource().getEntityWorld(),
                        itemStack.copy(),
                        e.getEntityLiving().getPosition().getX(),
                        e.getEntityLiving().getPosition().getY(),
                        e.getEntityLiving().getPosition().getZ());

                if (entityItem != null)
                    e.getDrops().add(entityItem);
            }
        }
    }

}
