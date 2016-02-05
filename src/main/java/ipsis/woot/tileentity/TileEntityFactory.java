package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.oss.LogHelper;
import ipsis.woot.manager.SpawnerManager;
import ipsis.woot.manager.SpawnerManager2;
import ipsis.woot.manager.SpawnerUpgrade;
import ipsis.woot.util.DamageSourceWoot;
import ipsis.woot.util.FakePlayerUtil;
import ipsis.woot.util.MobUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ITickable;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileEntityFactory extends TileEntity implements ITickable {

    static Random rand = new Random();

    public TileEntityFactory() {

        isRunning = true;
        spawnTicks = 200; /* vanilla is 200 -> 800 ticks */
        countTicks = 0;
        spawnCount = 4;
        //mobName = "Skeleton.wither";
        mobName = "WitherBoss";
        upgrades = new ArrayList<SpawnerUpgrade>();
        upgrades.add(SpawnerUpgrade.BASE);
    }

    boolean isRunning;
    int spawnTicks;
    int countTicks;
    int spawnCount;
    List<SpawnerUpgrade> upgrades;

    public List<SpawnerUpgrade> getUpgrades() {
        return upgrades;
    }

    @Override
    public void update() {

        if (worldObj.isRemote)
            return;

        if (!isRunning)
            return;

        this.countTicks++;
        if (countTicks % spawnTicks == 0) {
            trySpawn3();
        }

    }

    void trySpawn3() {

        SpawnerManager.EnchantKey enchantKey = SpawnerManager.EnchantKey.NO_ENCHANT;
        if (!Woot.spawnerManager2.isEmpty(mobName, enchantKey)) {
            List<ItemStack> dropList = Woot.spawnerManager2.getDrops(mobName, enchantKey);

            LogHelper.info("trySpawn3: " + dropList);
        }

        if (!Woot.spawnerManager2.isFull(mobName, enchantKey))
            Woot.spawnerManager2.spawn(mobName, enchantKey, this.worldObj, this.getPos());
    }

    void trySpawn2() {

        DamageSourceWoot damageSourceWoot = DamageSourceWoot.inSpawner;
        if (SpawnerManager.isFull(mobName, damageSourceWoot.getEnchantKey())) {
            /* Fill inventory from sampled drops */
            List<ItemStack> itemStackList = SpawnerManager.getSpawnerDrops(mobName, damageSourceWoot.getEnchantKey());
            LogHelper.info("Spawning from data " + mobName + "->" + itemStackList);
        } else {

            FakePlayer fakePlayer = FakePlayerUtil.getFakePlayer((WorldServer)worldObj);
            if (fakePlayer != null) {
                LogHelper.info("Spawning entity " + mobName);
                FakePlayerUtil.setLooting(fakePlayer, damageSourceWoot.getEnchantKey());
                String spawnMobName = MobUtil.getBaseMobName(mobName);
                Entity entity = EntityList.createEntityByName(spawnMobName, worldObj);
                if (MobUtil.isWitherSkeleton(mobName))
                    ((EntitySkeleton)entity).setSkeletonType(1);
                ((EntityLiving) entity).onInitialSpawn(worldObj.getDifficultyForLocation(this.getPos()), (IEntityLivingData) null);
                entity.setPosition(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());

                EntityDamageSource entityDamageSource = new EntityDamageSource(damageSourceWoot.getDamageType(), fakePlayer);

                try {
                /* Dev only version - change to access tranformer */
                    Field f = ReflectionHelper.findField(EntityLivingBase.class, "recentlyHit");
                    f.setInt(entity, 100);
                } catch (ReflectionHelper.UnableToFindFieldException e) {
                    LogHelper.info("No such field");
                } catch (IllegalAccessException e) {
                    LogHelper.info("No access");
                }

                ((EntityLivingBase) entity).onDeath(entityDamageSource);
            }
        }
    }

    String mobName;
    void trySpawn() {

        ItemStack swordStack = new ItemStack(Items.iron_sword);
        swordStack.addEnchantment(Enchantment.looting, 1);

        FakePlayer fakePlayer = FakePlayerUtil.getFakePlayer((WorldServer)worldObj);
        if (fakePlayer != null) {

            String mob = "Skeleton";
            if (SpawnerManager.isFull(mob, SpawnerManager.EnchantKey.LOOTING_I)) {
                List<ItemStack> itemStackList = SpawnerManager.getSpawnerDrops(mob, SpawnerManager.EnchantKey.LOOTING_I);
                LogHelper.info("Spawning from data " + itemStackList);
            } else {
                LogHelper.info("Spawning mob");
                Entity entity = EntityList.createEntityByName(mob, worldObj);
                ((EntityLiving) entity).onInitialSpawn(worldObj.getDifficultyForLocation(this.getPos()), (IEntityLivingData) null);
                entity.setPosition(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
                LogHelper.info(entity);
                DamageSource cause = DamageSourceWoot.inSpawner;
                EntityDamageSource entityDamageSource = new EntityDamageSource("inSpawner", fakePlayer);
                ((EntityPlayer) fakePlayer).setCurrentItemOrArmor(0, swordStack);

                /**
                 * RecentlyHit == field_70718_bc
                 * experienceValue == field_70728_aV
                 *
                 */

                try {
                /* Dev only version - change to access tranformer */
                    Field f = ReflectionHelper.findField(EntityLivingBase.class, "recentlyHit");
                    f.setInt(entity, 100);
                } catch (ReflectionHelper.UnableToFindFieldException e) {
                    LogHelper.info("No such field");
                } catch (IllegalAccessException e) {
                    LogHelper.info("No access");
                }

                ((EntityLivingBase) entity).onDeath(entityDamageSource);

                /**
                 * EntityLiving has a protected experienceLevel!
                 */

                /**
                 * Randomloot wont drop unless recentlyHit > 0!
                 */
            }
        }

        if (mobName == null)
            return;

    }

}
