package ipsis.woot.manager.spawnreq;

import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Files;
import ipsis.woot.util.SerializationHelper;

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
        req.setKey(EnumEnchantKey.NO_ENCHANT);
        req.addItemStack("minecraft:egg", 4);
        req.addItemStack("minecraft:wool:4", 1);

        extraSpawnReqList.add(req);

        req = new ExtraSpawnReq();
        req.setWootName("Woot:none:Zombie");
        req.setKey(EnumEnchantKey.NO_ENCHANT);
        req.addItemStack("minecraft:apple", 66);
        req.addItemStack("minecraft:iron_ingot", 4);
        extraSpawnReqList.add(req);

        req = new ExtraSpawnReq();
        req.setWootName("Woot:none:Spider");
        req.setKey(EnumEnchantKey.LOOTING_I);
        req.addFluidStack("water", 1000);
        extraSpawnReqList.add(req);

        req = new ExtraSpawnReq();
        req.setWootName("Woot:none:Spider");
        req.setKey(EnumEnchantKey.NO_ENCHANT);
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
    }

    public ExtraSpawnReq getExtraSpawnReq(String wootName, EnumEnchantKey key) {

        for (ExtraSpawnReq c : extraSpawnReqList) {
            if (c.getWootName().equalsIgnoreCase(wootName) && c.getKey() == key)
                return c;
        }

        return null;
    }

}
