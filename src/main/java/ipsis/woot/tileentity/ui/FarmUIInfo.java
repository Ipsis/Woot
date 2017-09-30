package ipsis.woot.tileentity.ui;

import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.util.WootMob;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FarmUIInfo {

    public EnumMobFactoryTier tier;
    public WootMob wootMob;
    public boolean isRunning;
    public int recipeTotalPower;
    public int recipeTotalTime;
    public int recipePowerPerTick;
    public int consumedPower;
    public boolean isValid = false;
    public int powerStored;
    public int powerCapacity;
    public int mobCount;

    // Filtered list of drops with the stackSize == drop chance
    public List<ItemStack> drops = new ArrayList<>();

    // List of ingredients to spawn
    public List<ItemStack> ingredients = new ArrayList<>();

    public void setValid() {

        this.isValid = true;
    }

    public boolean isValid() {

        return this.isValid;
    }
}
