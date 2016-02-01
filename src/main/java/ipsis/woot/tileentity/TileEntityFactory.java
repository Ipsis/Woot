package ipsis.woot.tileentity;

import ipsis.oss.LogHelper;
import ipsis.woot.util.DamageSourceWoot;
import ipsis.woot.util.FakePlayerUtil;
import ipsis.woot.util.ReflectionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
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
import java.util.Random;

public class TileEntityFactory extends TileEntity implements ITickable {

    static Random rand = new Random();

    public TileEntityFactory() {

        isRunning = true;
        spawnTicks = 20;
        countTicks = 0;
    }

    boolean isRunning;
    int spawnTicks;
    int countTicks;

    @Override
    public void update() {

        if (worldObj.isRemote)
            return;

        if (!isRunning)
            return;

        this.countTicks++;
        if (countTicks % spawnTicks == 0) {
            trySpawn();
        }

    }

    String mobID;
    void trySpawn() {

        ItemStack swordStack = new ItemStack(Items.iron_sword);
        swordStack.addEnchantment(Enchantment.looting, 1);

        FakePlayer fakePlayer = FakePlayerUtil.getFakePlayer((WorldServer)worldObj);
        if (fakePlayer != null) {

            LogHelper.info("trySpawn");
            Entity entity = EntityList.createEntityByName("Skeleton", worldObj);
            ((EntityLiving)entity).onInitialSpawn(worldObj.getDifficultyForLocation(this.getPos()), (IEntityLivingData)null);
            entity.setPosition(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
            LogHelper.info(entity);
            DamageSource cause = DamageSourceWoot.inSpawner;
            EntityDamageSource entityDamageSource = new EntityDamageSource("inSpawner", fakePlayer);
            ((EntityPlayer)fakePlayer).setCurrentItemOrArmor(0, swordStack);

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

        if (mobID == null)
            return;

    }

}
