package ipsis.woot.tileentity.ui;

import ipsis.woot.multiblock.EnumMobFactoryTier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class FarmUIInfo {

    public EnumMobFactoryTier tier;
    public String mobName;
    public boolean isRunning;
    public long recipeTotalPower;
    public int recipeTotalTime;
    public int recipePowerPerTick;
    public long consumedPower;
    public boolean isValid = false;
    public int powerStored;
    public int powerCapacity;
    public int mobCount;
    public boolean missingIngredients = false;
    public UpgradeUIInfo upgradeUIInfo = new UpgradeUIInfo();

    // Filtered list of drops with the stackSize == drop chance
    public List<ItemStack> drops = new ArrayList<>();

    // List of ingredients to spawn
    public List<ItemStack> ingredientsItems = new ArrayList<>();

    // List of fluids to spawn
    public List<FluidStack> ingredientsFluids = new ArrayList<>();

    public void setValid() {

        this.isValid = true;
    }

    public boolean isValid() {

        return this.isValid;
    }
}
