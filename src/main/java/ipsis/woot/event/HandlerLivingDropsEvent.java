package ipsis.woot.event;

import ipsis.Woot;
import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Settings;
import ipsis.woot.util.FakePlayerPool;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerLivingDropsEvent {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingDropsEvent(LivingDropsEvent e) {

        /**
         * This is called for players and mobs
         */
        if (e.getEntity() instanceof EntityLiving) {

            DamageSource damageSource = e.getSource();
            if (damageSource != null) {

                if (FakePlayerPool.isOurFakePlayer(damageSource.getSourceOfDamage())) {
                    // Cancel the factory kills so we dont spawn any loot in the world
                    e.setCanceled(true);
                }

                /**
                 *  Killed in one of our spawners, but we dont care which one.
                 *  We only store the drop information.
                 */
                String mobID = Woot.mobRegistry.createWootName((EntityLiving) e.getEntity());
                EnumEnchantKey key = EnumEnchantKey.getEnchantKey(e.getLootingLevel());
                if (!Woot.spawnerManager.isFull(mobID, key))
                    Woot.spawnerManager.addDrops(mobID, key, e.getDrops());

                Woot.lootManager.handleDropInfo(mobID, key, e.getDrops());
                EntityPlayer entityPlayer = (EntityPlayer)e.getSource().getEntity();


            } else if (!Settings.strictFactorySpawns) {

                /**
                 * Damn you slimes
                 * For Slime only care about size == 1 as that drops the loot
                 * For MagmaCube only care about size != 1 as they can drop the loot
                 * Remember MagmaCube extends Slime hence the order
                 */
                if (e.getEntity() instanceof EntityMagmaCube) {

                    if (((EntityMagmaCube) e.getEntity()).isSmallSlime())
                        return;

                } else if (e.getEntity() instanceof EntitySlime) {

                    if (((EntitySlime) e.getEntity()).getSlimeSize() != 1)
                        return;
                }

                /**
                 * Convert the non-spawner kill into a damage source if possible
                 */
                String mobID = Woot.mobRegistry.createWootName((EntityLiving) e.getEntity());
                EnumEnchantKey key = EnumEnchantKey.getEnchantKey(e.getLootingLevel());
                if (!Woot.spawnerManager.isFull(mobID, key))
                    Woot.spawnerManager.addDrops(mobID, key, e.getDrops());

                Woot.lootManager.handleDropInfo(mobID, key, e.getDrops());
            }
        }
    }
}
