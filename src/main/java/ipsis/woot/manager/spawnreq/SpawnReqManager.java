package ipsis.woot.manager.spawnreq;

import ipsis.woot.manager.UpgradeManager;
import ipsis.woot.manager.UpgradeSetup;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Files;
import ipsis.woot.reference.Settings;
import ipsis.woot.util.SerializationHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SpawnReqManager {

    List<ExtraSpawnReq> extraSpawnReqList;

    public SpawnReqManager() {

        extraSpawnReqList = new ArrayList<ExtraSpawnReq>();
    }

    void fakeSetup() {

        ExtraSpawnReq req = new ExtraSpawnReq();
        req.setWootName("Woot:none:Sheep");
        req.addItemStack("minecraft:egg", 4);
        req.addItemStack("minecraft:wool:4", 1);

        extraSpawnReqList.add(req);

        req = new ExtraSpawnReq();
        req.setWootName("Woot:none:Zombie");
        req.addItemStack("minecraft:apple", 66);
        req.addItemStack("minecraft:iron_ingot", 4);
        extraSpawnReqList.add(req);

        req = new ExtraSpawnReq();
        req.setWootName("Woot:none:Spider");
        req.addFluidStack("water", 1000);
        extraSpawnReqList.add(req);

        req = new ExtraSpawnReq();
        req.setWootName("Woot:none:Spider");
        req.addFluidStack("liquid_sunshine", 1000);
        extraSpawnReqList.add(req);
        SerializationHelper.writeListToFile(extraSpawnReqList, Files.spawnReqFile);

    }

    public void loadFromJson() {

        try {
            extraSpawnReqList = SerializationHelper.readListFromFile(Files.spawnReqFile);
        } catch (FileNotFoundException e) {
            extraSpawnReqList = new ArrayList<ExtraSpawnReq>();
        }

        if (extraSpawnReqList == null)
            extraSpawnReqList = new ArrayList<ExtraSpawnReq>();

        for (ExtraSpawnReq req : extraSpawnReqList) {
            if (req.hasItems()) {
                LogHelper.info("Added extra req for " + req.getWootName() + "->" + req.getItems());
            } else if (req.hasFluids()) {
                FluidStack fluidStack = req.getFluid();
                if (fluidStack != null)
                    LogHelper.info("Added extra req for " + req.getWootName() + "->" + fluidStack.getUnlocalizedName() + "/" + fluidStack.amount + "mb");
            }
        }
    }

    private ExtraSpawnReq getExtraSpawnReq(String wootName) {

        for (ExtraSpawnReq c : extraSpawnReqList) {
            if (c.getWootName().equalsIgnoreCase(wootName)) {

                if (c.hasItems())
                    return c;
                else if (c.hasFluids())
                    return c;
                else
                    return null;
            }
        }

        return null;
    }

    public boolean hasExtraSpawnReq(String wootName) {

        return getExtraSpawnReq(wootName) != null;
    }

    public List<ItemStack> getItems(String wootName, UpgradeSetup upgradeSetup) {

        List<ItemStack> itemStackList = new ArrayList<>();
        ExtraSpawnReq req = getExtraSpawnReq(wootName);
        if (req == null)
            return itemStackList;

        if (!req.hasItems())
            return itemStackList;


        int mobCount = Settings.baseMobCount;
        if (upgradeSetup.hasMassUpgrade())
            mobCount = UpgradeManager.getSpawnerUpgrade(upgradeSetup.getMassUpgrade()).getMass();

        for (ItemStack itemStack : req.getItems()) {

            ItemStack i = itemStack.copy();
            if (req.getAllowEfficiency() && upgradeSetup.hasEfficiencyUpgrade()) {

                int needed = i.stackSize *= mobCount;
                int f = UpgradeManager.getSpawnerUpgrade(upgradeSetup.getEfficiencyUpgrade()).getEfficiency();
                int saving = (int)((needed / 100.0F) * f);
                i.stackSize = needed - saving;
                if (i.stackSize <= 0)
                    i.stackSize = 1;

            } else {
                i.stackSize *= mobCount;
            }
            itemStackList.add(i);
        }

        return itemStackList;
    }

    public FluidStack getFluid(String wootName, UpgradeSetup upgradeSetup) {

        ExtraSpawnReq req = getExtraSpawnReq(wootName);
        if (req == null)
            return null;

        if (!req.hasFluids())
            return null;


        int mobCount = Settings.baseMobCount;
        if (upgradeSetup.hasMassUpgrade())
            mobCount = UpgradeManager.getSpawnerUpgrade(upgradeSetup.getMassUpgrade()).getMass();

        FluidStack fluidStack = req.getFluid().copy();
        if (req.getAllowEfficiency() && upgradeSetup.hasEfficiencyUpgrade()) {

            int needed = fluidStack.amount * mobCount;
            int f = UpgradeManager.getSpawnerUpgrade(upgradeSetup.getEfficiencyUpgrade()).getEfficiency();
            int saving = (int)((needed / 100.0F) * f);
            fluidStack.amount = needed - saving;
            if (fluidStack.amount <= 0)
                fluidStack.amount = 1;
        } else {
            fluidStack.amount *= mobCount;
        }

        return fluidStack;
    }

}
