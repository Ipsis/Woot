package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.oss.LogHelper;
import ipsis.woot.block.BlockUpgrade;
import ipsis.woot.manager.SpawnerManager;
import ipsis.woot.manager.Upgrade;
import ipsis.woot.manager.UpgradeValidator;
import ipsis.woot.reference.Settings;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TileEntitySpawner extends TileEntity implements ITickable {

    int currLearnTicks;
    int currSpawnTicks;
    boolean isRunning;
    boolean isFormed;
    boolean first;
    String mobName;
    SpawnerManager.SpawnReq spawnReq;
    SpawnerManager.EnchantKey enchantKey;
    int consumedRf;

    HashMap<Upgrade.Group, Upgrade> upgradeMap = new HashMap<Upgrade.Group, Upgrade>();

    public TileEntitySpawner() {
        this.mobName = null;
        this.upgradeMap.clear();
        this.isFormed = false;
        this.isRunning = false;
        this.currLearnTicks = 0;
        this.currSpawnTicks = 0;
        this.consumedRf = 0;
        this.spawnReq = null;
        this.enchantKey = null;
        this.first = false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Spawning: ").append(mobName).append("\n");
        if (enchantKey != null)
            sb.append("Enchant:  ").append(enchantKey).append("\n");
        if (spawnReq != null)
            sb.append("Spawnreq: ").append(spawnReq);
        return sb.toString();
    }

    public void scanStructure() {
        isFormed = true;
        isRunning = true; /* TODO remove this */
    }

    public void scanUpgrades() {

        upgradeMap.clear();
        if (!isFormed)
            return;

        List<Upgrade> tmpUpgrades = new ArrayList<Upgrade>();

        for (EnumFacing f : EnumFacing.values()) {
            BlockPos pos = this.getPos().offset(f, 1);
            if (worldObj.isBlockLoaded(pos)) {
                Block b = worldObj.getBlockState(pos).getBlock();
                if (b != null && b instanceof BlockUpgrade) {
                    Upgrade.Type t = worldObj.getBlockState(this.getPos().offset(f, 1)).getValue(BlockUpgrade.VARIANT);
                    tmpUpgrades.add(Woot.spawnerManager.getUpgrade(t));
                }
            }
        }

        if (UpgradeValidator.isUpgradeValid(tmpUpgrades)) {
            for (Upgrade u : tmpUpgrades)
                addUpgrade(u);
        }

        enchantKey = getEnchantKey();

        mobName = "Skeleton";
        spawnReq = Woot.spawnerManager.getSpawnReq(mobName, getUpgrades(), Woot.spawnerManager.getXp(mobName, this));

        LogHelper.info(this);
    }

    public List<Upgrade> getUpgrades() {
        return new ArrayList<Upgrade>(upgradeMap.values());
    }

    public boolean addUpgrade(Upgrade upgrade) {

        if (!upgradeMap.containsKey(upgrade.getGroup())) {
            upgradeMap.put(upgrade.getGroup(), upgrade);
            return true;
        }
        return false;
    }

    SpawnerManager.EnchantKey getEnchantKey() {

        Upgrade upgrade = upgradeMap.get(Upgrade.Group.LOOTING);
        if (upgrade != null)
            return upgrade.getType().getEnchantKey();

        return SpawnerManager.EnchantKey.NO_ENCHANT;
    }

    void processPower() {

        int drawnRf = spawnReq.getRfPerTick() * 1;
        if (drawnRf == spawnReq.getRfPerTick()) {
            consumedRf += drawnRf;
        } else {
            if (Settings.strictPower)
                consumedRf = 0;
            else
                consumedRf += drawnRf;
        }
    }

    @Override
    public void update() {

        if (worldObj.isRemote)
            return;

        if (!first) {
            scanStructure();
            scanUpgrades();
            first = true;
        }

        if (!isRunning)
            return;

        if (!isFormed)
            return;

        currLearnTicks++;
        if (currLearnTicks >= Settings.learnTicks) {
            if (!Woot.spawnerManager.isFull(mobName, enchantKey)) {
                /* Not full so fake another spawn */
                Woot.spawnerManager.spawn(mobName, enchantKey, this.worldObj, this.getPos());
            }
            currLearnTicks = 0;
        }

        /* Do we have any info on this mob yet - should only happen once per mob */
        if (Woot.spawnerManager.isEmpty(mobName, enchantKey))
            return;

        processPower();

        currSpawnTicks++;
        if (currSpawnTicks == spawnReq.getSpawnTime()) {
           if (consumedRf >= spawnReq.getTotalRf()) {
               List<ItemStack> dropList = Woot.spawnerManager.getDrops(mobName, enchantKey);
               LogHelper.info("Generating drops: " + dropList);
               /* TODO can be cache the neighbor tes? */
               for (EnumFacing f : EnumFacing.values()) {
                   if (worldObj.isBlockLoaded(this.getPos().offset(f))) {
                       TileEntity te = worldObj.getTileEntity(this.getPos().offset(f));
                       if (te == null)
                           continue;

                       if (!te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite()))
                           continue;

                       IItemHandler capability = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, f.getOpposite());
                       for (int i = 0; i < dropList.size(); i++) {
                           ItemStack result = ItemHandlerHelper.insertItem(capability, ItemHandlerHelper.copyStackWithSize(dropList.get(i), 1), false);
                           if (result != null)
                               dropList.get(i).stackSize = result.stackSize;
                           else
                               dropList.get(i).stackSize = 0;
                       }
                   }
               }

               /**
                *  Everything else is thrown away
                */
           } else {
               LogHelper.info("Not enough power provided " + consumedRf + ":" + spawnReq.getTotalRf());
           }
            currSpawnTicks = 0;
            consumedRf = 0;
        }
    }
}
