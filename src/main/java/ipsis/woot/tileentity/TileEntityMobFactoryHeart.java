package ipsis.woot.tileentity;

import ipsis.Woot;
import ipsis.woot.block.BlockMobFactoryHeart;
import ipsis.woot.farming.*;
import ipsis.woot.farmstructure.*;
import ipsis.woot.configuration.EnumConfigKey;
import ipsis.woot.farmblocks.IFarmBlockMaster;
import ipsis.woot.loot.ILootLearner;
import ipsis.woot.loot.repository.ILootRepositoryLookup;
import ipsis.woot.loot.schools.TartarusSchool;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.power.calculation.BigIntegerCalculator;
import ipsis.woot.power.calculation.IPowerCalculator;
import ipsis.woot.tileentity.ui.FarmUIInfo;
import ipsis.woot.util.LootHelper;
import ipsis.woot.util.StringHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
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
    private ILootLearner lootLearner;
    private int storedXp = 0;


    public TileEntityMobFactoryHeart() {

        // NOTE: this is called without a world on startup
        tickTracker = new SimpleTickTracker();
        tickTracker.setLearnTickCount(
                Woot.wootConfiguration.getInteger(EnumConfigKey.LEARN_TICKS) + Woot.RANDOM.nextInt(11));
        tickTracker.setStructureTickCount(20);

        spawnRecipeConsumer = new SpawnRecipeConsumer();
        spawnRecipe = new SpawnRecipe();
        powerCalculator = new BigIntegerCalculator();
        recipeProgressTracker = new SimpleRecipeProgressTracker();
        lootLearner = new TartarusSchool();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
       super.writeToNBT(compound);

       if (farmStructure != null && farmStructure.isFormed())
           compound.setLong("wootConsumedPowerLong", recipeProgressTracker.getConsumedPower());

       compound.setInteger("storedXp", storedXp);
       return compound;
    }

    private long nbtConsumedPower = 0;
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if (compound.hasKey("wootConsumedPower"))
            nbtConsumedPower = compound.getInteger("wootConsumedPower");
        else if (compound.hasKey("wootConsumedPowerLong"))
            nbtConsumedPower = compound.getLong("wootConsumedPowerLong");

        storedXp = compound.getInteger("storedXp");

        if (storedXp < 0)
            storedXp = 0;
    }

    @Override
    public void invalidate() {

        super.invalidate();
        if (farmStructure != null && farmStructure.isFormed())
            farmStructure.fullDisconnect();
    }

    @Override
    public void onChunkUnload() {

        super.onChunkUnload();
        if (farmStructure != null && farmStructure.isFormed())
            farmStructure.fullDisconnect();
    }

    private boolean isPowered() {

        // Getting a redstone signal STOPS the machine
        if (hasWorld() && world.isBlockPowered(getPos()))
            return false;

        return true;
    }

    public int getRecipeProgress() {

        int progress = 0;
        if (farmStructure != null && farmStructure.isFormed())
            progress = recipeProgressTracker.getProgress();

        return progress;
    }

    public int getStoredPower() {

        int power = 0;
        if (farmStructure != null && farmStructure.isFormed() && farmSetup != null)
            power = farmSetup.getPowerStation().getEnergyStorage().getEnergyStored();

        return power;
    }

    public int getRunning() {

        int running = 0;
        if (farmStructure != null && farmStructure.isFormed() && isPowered())
            running = 1;

        return running;
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
                lootLearner.tick(tickTracker, getWorld(), getPos(), farmSetup);
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

        player.sendStatusMessage(new TextComponentString(StringHelper.localizeFormat("chat.woot.validate.validating", tier.getTranslated("info.woot.tier"))), false);

        EnumFacing facing = world.getBlockState(getPos()).getValue(BlockMobFactoryHeart.FACING);
        FarmScanner2 farmScanner = new FarmScanner2();
        ScannedFarm2 scannedFarm = farmScanner.scanFarm(world, getPos(), facing, tier);

        if (!scannedFarm.remote.hasPower())
            player.sendStatusMessage(new TextComponentString(StringHelper.localize("chat.woot.validate.nopower")), false);
        else
            player.sendStatusMessage(new TextComponentString(StringHelper.localizeFormat("chat.woot.validate.power",
                    scannedFarm.remote.getPowerPos().getX(),
                    scannedFarm.remote.getPowerPos().getY(),
                    scannedFarm.remote.getPowerPos().getZ())), false);

        if (!scannedFarm.remote.hasImport())
            player.sendStatusMessage(new TextComponentString(StringHelper.localize("chat.woot.validate.noimporter")), false);
        else
            player.sendStatusMessage(new TextComponentString(StringHelper.localizeFormat("chat.woot.validate.importer",
                scannedFarm.remote.getImportPos().getX(),
                scannedFarm.remote.getImportPos().getY(),
                scannedFarm.remote.getImportPos().getZ())), false);

        if (!scannedFarm.remote.hasExport())
            player.sendStatusMessage(new TextComponentString(StringHelper.localize("chat.woot.validate.noexporter")), false);
        else
            player.sendStatusMessage(new TextComponentString(StringHelper.localizeFormat("chat.woot.validate.exporter",
                scannedFarm.remote.getExportPos().getX(),
                scannedFarm.remote.getExportPos().getY(),
                scannedFarm.remote.getExportPos().getZ())), false);

        // All blocks in place, have remote and valid controller
        if (scannedFarm.isValidStructure() && scannedFarm.isValidCofiguration(world)) {
            player.sendStatusMessage(new TextComponentString(StringHelper.localizeFormat("chat.woot.validate.ok",
                    tier.getTranslated("info.woot.tier"))), false);
        } else {
            if (!scannedFarm.controller.isPresent()) {
                // Controller missing
                BlockPos pos = farmScanner.getControllerPos(world, getPos(), facing);
                player.sendStatusMessage(new TextComponentString(StringHelper.localizeFormat("chat.woot.validate.nocontroller",
                        pos.getX(), pos.getY(), pos.getZ())), false);
            } else {
                // Controller present but wrong
                if (!scannedFarm.controller.canCapture())
                    player.sendStatusMessage(new TextComponentString(StringHelper.localize("chat.woot.validate.invalidmob")), false);
                else if (!scannedFarm.controller.isTierValid(world, tier))
                    player.sendStatusMessage(new TextComponentString(StringHelper.localize("chat.woot.validate.invalidtier")), false);
            }

            for (FarmScanner2.BadFarmBlockInfo info : scannedFarm.getBadBlocks()) {
                ItemStack itemStack = new ItemStack(info.getCorrectBlock(), 1, info.getCorrectBlockMeta());

                if (info.getReason() == FarmScanner2.BadBlockReason.MISSING_BLOCK) {
                    player.sendStatusMessage(new TextComponentString(StringHelper.localizeFormat("chat.woot.validate.missing",
                            itemStack.getDisplayName(),
                            info.getPos().getX(), info.getPos().getY(), info.getPos().getZ())),
                            false);
                } else if (info.getReason() == FarmScanner2.BadBlockReason.WRONG_BLOCK || info.getReason() == FarmScanner2.BadBlockReason.WRONG_STRUCTURE_TYPE) {
                    ItemStack itemStack1 = new ItemStack(info.getInvalidBlock(), 1, info.getInvalidBlockMeta());
                    player.sendStatusMessage(new TextComponentString(StringHelper.localizeFormat("chat.woot.validate.incorrect",
                            itemStack.getDisplayName(),
                            info.getPos().getX(), info.getPos().getY(), info.getPos().getZ(),
                            itemStack1.getDisplayName())),
                            false);
                }
            }
        }
    }

    public void showGui(EntityPlayer player, World world, int x, int y, int z) {

        // Don't open the gui when the factory isn't formed as it is not valid
        if (farmStructure != null && farmStructure.isFormed())
            player.openGui(Woot.instance, 0, world, x, y, z);
    }

    /**
     * IFarmBlockMaster
     */
    @Override
    public void interruptFarmStructure() {

        if (farmStructure != null)
            farmStructure.setStructureDirty();
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

        info.mobName = farmSetup.getWootMob().getDisplayName();
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
            for (ItemStack itemStack : spawnRecipe.getItems())
                info.ingredientsItems.add(itemStack.copy());

            for (FluidStack fluidStack : spawnRecipe.getFluids())
                info.ingredientsFluids.add(fluidStack.copy());
        }

        // Say everything is okay
        info.setValid();
    }

    public boolean canInteractWith(EntityPlayer entityPlayer) {

        if (!farmStructure.isFormed())
            return false;

        return entityPlayer.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    // Client GUI sync only
    private FarmUIInfo guiInfoOnly;
    public void setGuiFarmInfo(FarmUIInfo info) {
        this.guiInfoOnly = info;
    }
    public FarmUIInfo getGuiFarmInfo() {
        return guiInfoOnly;
    }
    public int guiProgress;
    public int guiStoredPower;
    public int guiRunning;
}
