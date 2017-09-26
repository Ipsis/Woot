package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.woot.block.BlockMobFactoryHeart;
import ipsis.woot.farming.*;
import ipsis.woot.farmstructure.*;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.farmblocks.IFarmBlockMaster;
import ipsis.woot.loot.repository.ILootRepositoryLookup;
import ipsis.woot.mock.MockSpawnRecipeConsumer;
import ipsis.woot.mock.MockSpawnRecipeRepository;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.power.calculation.Calculator;
import ipsis.woot.power.calculation.IPowerCalculator;
import ipsis.woot.tileentity.ui.FarmUIInfo;
import ipsis.woot.util.LootHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileEntityMobFactoryHeart extends TileEntity implements ITickable, IFarmBlockMaster, IMobFarm {

    private ITickTracker tickTracker;
    private IFarmStructure farmStructure;
    private IFarmSetup farmSetup;
    private PowerRecipe powerRecipe;
    private ISpawnRecipe spawnRecipe;
    private IPowerCalculator powerCalculator;
    private IRecipeProgressTracker recipeProgressTracker;
    private ISpawnRecipeConsumer spawnRecipeConsumer;
    private int storedXp = 0;


    public TileEntityMobFactoryHeart() {

        // NOTE: this is called without a world on startup
        tickTracker = new SimpleTickTracker();
        tickTracker.setLearnTickCount(
                Woot.wootConfiguration.getInteger(EnumConfigKey.LEARN_TICKS) + Woot.RANDOM.nextInt(11));
        tickTracker.setStructureTickCount(20);

        spawnRecipeConsumer = new SpawnRecipeConsumer();
        spawnRecipe = new SpawnRecipe();
        powerCalculator = new Calculator();
        recipeProgressTracker = new SimpleRecipeProgressTracker();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
       super.writeToNBT(compound);

       if (farmStructure != null && farmStructure.isFormed())
           compound.setInteger("wootConsumedPower", recipeProgressTracker.getConsumedPower());

       compound.setInteger("storedXp", storedXp);
       return compound;
    }

    private int nbtConsumedPower = 0;
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if (compound.hasKey("wootConsumedPower"))
            nbtConsumedPower = compound.getInteger("wootConsumedPower");

        storedXp = compound.getInteger("storedXp");
    }

    @Override
    public void invalidate() {

        super.invalidate();
        if (farmStructure != null && farmStructure.isFormed())
            farmStructure.fullDisconnect();
    }

    private boolean isPowered() {

        // Getting a redstone signal STOPS the machine
        if (hasWorld() && world.isBlockPowered(getPos()))
            return false;

        return true;
    }

    @Override
    public void update() {

        if (world.isRemote)
            return;

        // Cannot set this on create as the world may not be set
        if (farmStructure == null) {
            farmStructure = new FarmBuilder().setWorld(getWorld()).setPosition(getPos());
            farmStructure.setStructureDirty();
        }

        tickTracker.tick(world);
        farmStructure.tick(tickTracker);

        if (farmStructure.isFormed()) {
            if (farmStructure.hasChanged()) {
                farmSetup = farmStructure.createSetup();
                powerRecipe = powerCalculator.calculate(world, farmSetup);
                spawnRecipe = Woot.spawnRecipeRepository.get(farmSetup.getWootMobName());
                recipeProgressTracker.setPowerStation(farmSetup.getPowerStation());
                recipeProgressTracker.setPowerRecipe(powerRecipe);
                farmStructure.clearChanged();
                farmSetup.setStoredXp(storedXp);

                if (nbtConsumedPower != 0) {
                    recipeProgressTracker.setConsumedPower(nbtConsumedPower);
                    nbtConsumedPower = 0;
                }
            }

            if (isPowered()) {
                Woot.lootLearner.tick(tickTracker, getWorld(), getPos(), farmSetup);
                recipeProgressTracker.tick();
                if (recipeProgressTracker.isComplete()) {
                    if (spawnRecipeConsumer.consume(getWorld(), getPos(), farmSetup.getConnectedImportTanks(), farmSetup.getConnectedImportChests(), spawnRecipe, farmSetup.getNumMobs())) {
                        Woot.lootGeneration.generate(getWorld(), farmSetup.getConnectedExportTanks(), farmSetup.getConnectedExportChests(), farmSetup, world.getDifficultyForLocation(getPos()));
                        storedXp = farmSetup.getStoredXp();
                    }
                    recipeProgressTracker.reset();
                }
            }
        }
    }

    public void manualFarmScan(EntityPlayer player, EnumMobFactoryTier tier) {

        EnumFacing facing = world.getBlockState(getPos()).getValue(BlockMobFactoryHeart.FACING);
        IFarmScanner farmScanner = new FarmScanner();
        IFarmScanner.BadFarmInfo badFarmInfo = new IFarmScanner.BadFarmInfo();
        farmScanner.scanFarmNoStop(world, getPos(), facing, tier, badFarmInfo);
        for (IFarmScanner.BadFarmBlock badFarmBlock : badFarmInfo.badFarmBlocks) {
            ItemStack itemStack = new ItemStack(badFarmBlock.getCorrectBlock(), 1, badFarmBlock.getCorrectBlockMeta());
            player.sendStatusMessage(new TextComponentString(badFarmBlock.getPos() + "->" + itemStack.getDisplayName()), false);
        }

        if (!badFarmInfo.hasCell)
            player.sendStatusMessage(new TextComponentString("No power cell"), false);
        if (!badFarmInfo.hasImporter)
            player.sendStatusMessage(new TextComponentString("No importer"), false);
        if (!badFarmInfo.hasExporter)
            player.sendStatusMessage(new TextComponentString("No exporter"), false);
    }

    /**
     * IFarmBlockMaster
     */
    @Override
    public void interruptFarmStructure() {

        farmStructure.setStructureDirty();
    }

    @Override
    public void interruptFarmUpgrade() {

        farmStructure.setUpgradeDirty();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {

        if (farmStructure != null && farmStructure.isFormed())
            return capability == CapabilityEnergy.ENERGY;

        return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        if (capability == CapabilityEnergy.ENERGY && farmStructure != null && farmStructure.isFormed())
            return (T)farmSetup.getPowerStation().getEnergyStorage();

        return super.getCapability(capability, facing);
    }

    @Override
    public void getUIInfo(FarmUIInfo info) {

        if (!farmStructure.isFormed())
            return;

        info.wootMob = farmSetup.getWootMob();
        info.isRunning = isPowered();
        info.recipeTotalPower = powerRecipe.getTotalPower();
        info.recipeTotalTime = powerRecipe.getTicks();
        info.recipePowerPerTick = powerRecipe.getPowerPerTick();
        info.consumedPower = recipeProgressTracker.getConsumedPower();
        info.tier = farmSetup.getFarmTier();
        info.powerCapacity = farmSetup.getPowerStation().getEnergyStorage().getMaxEnergyStored();
        info.powerStored = farmSetup.getPowerStation().getEnergyStorage().getEnergyStored();
        info.mobCount = farmSetup.getNumMobs();

        List<ILootRepositoryLookup.LootItemStack> loot =  LootHelper.getDrops(farmSetup.getWootMobName(), farmSetup.getEnchantKey());
        for (ILootRepositoryLookup.LootItemStack lootItemStack : loot) {
            ItemStack itemStack = lootItemStack.itemStack.copy();
            itemStack.setCount(lootItemStack.dropChance);
            info.drops.add(itemStack);
        }

        if (spawnRecipe != null) {
            for (ItemStack itemStack : spawnRecipe.getItems()) {
                ItemStack ingredient = itemStack.copy();
                ingredient.setCount(ingredient.getCount() * farmSetup.getNumMobs());
                info.ingredients.add(itemStack.copy());
            }
        }

        // Say everything is okay
        info.setValid();
    }
}
