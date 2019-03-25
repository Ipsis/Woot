package ipsis.woot.event;

import ipsis.woot.tools.ItemMobShard;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helper.FakeMobKeyHelper;
import ipsis.woot.util.helper.ProgrammedMobHelper;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class LivingDeathEventHandler {

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDropsEvent event) {

        if (WorldHelper.isClientWorld(event.getEntity().world))
            return;

        // Only player kills
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer))
            return;

        if (event.getEntityLiving() == null)
            return;


        EntityPlayer entityPlayer = (EntityPlayer)event.getSource().getTrueSource();
        EntityLivingBase entityLivingBase = event.getEntityLiving();

        // ignore fake player kills
        if (entityPlayer instanceof FakePlayer)
            return;

        /**
         * EntityPlayer extends EntityLivingBase
         * EntityLiving extends EntityLivingBase
         *
         * We only care about EntityLiving so strip out any possible
         * player on player kills
         */
        if (!(entityLivingBase instanceof EntityLiving))
            return;

        // filter out possible extra death events from things like the EnderDragon
        if (ignoreDeathEvent(event.getEntity()))
            return;

        FakeMobKey fakeMobKey = FakeMobKeyHelper.createFromEntity((EntityLiving)entityLivingBase);
        if (fakeMobKey.isValid()) {
            ItemStack itemStack = findShard(entityPlayer, fakeMobKey);
            if (!itemStack.isEmpty())
                ProgrammedMobHelper.incrementDeathCount(itemStack, 1);
        }
    }

    private ItemStack findShard(EntityPlayer entityPlayer, FakeMobKey fakeMobKey) {

        ItemStack foundStack = ItemStack.EMPTY;

        /**
         * Hotbar only
         */
        for (int i = 0; i < InventoryPlayer.getHotbarSize(); i++) {
            ItemStack itemStack = entityPlayer.inventory.getStackInSlot(i);
            if (!ItemMobShard.isMobEqual(itemStack, fakeMobKey))
                continue;

            foundStack = itemStack;
            break;
        }

        return foundStack;
    }

    /**
     * Cache of recent kills to avoid multiple death events from the same mob.
     * eg. the EnderDragon
     */
    private final int MAX_UUID_CACHE_SIZE = 10;
    private List<String> uuidList = new ArrayList<>();
    private boolean ignoreDeathEvent(Entity entity) {

        String uuid = entity.getCachedUniqueIdString();
        if (uuidList.contains(uuid))
            return true;

        uuidList.add(uuid);
        if (uuidList.size() > MAX_UUID_CACHE_SIZE)
            uuidList.remove(0);

        return false;
    }
}
