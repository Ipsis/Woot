package ipsis.woot.manager.loot;

import ipsis.woot.manager.EnumEnchantKey;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LootTableManager {

    private HashMap<String, LootTable> lootMap;

    public void loadWorldDataFromJson(String json) { }
    public String saveWorldDataToJson() { return ""; }

    public LootTableManager() {

        lootMap = new HashMap<String, LootTable>();
    }

    public void update(String wootName, EnumEnchantKey key, List<EntityItem> mobDropList) {

        LootTable e = lootMap.get(wootName);
        if (e == null) {
            e = new LootTable(wootName);
            lootMap.put(wootName, e);
        }

        e.update(key, mobDropList);
    }

    public List<ItemStack> getDrops(String wootName, EnumEnchantKey key) {

        List<ItemStack> drops = new ArrayList<ItemStack>();
        LootTable e = lootMap.get(wootName);
        if (e != null)
            e.getDrops(key, drops);

        return drops;
    }

    public boolean isFull(String wootName, EnumEnchantKey key) {

        LootTable e = lootMap.get(wootName);
        if (e == null)
            return false;

        return e.isFull(key);
    }

    public boolean isEmpty(String wootName, EnumEnchantKey key) {

        LootTable e = lootMap.get(wootName);
        if (e == null)
            return true;

        return e.isEmpty(key);
    }

    /**
     * Commands
     */
    public void dumpMobs(ICommandSender sender) {

        StringBuilder sb = new StringBuilder();
        for (String mobName : lootMap.keySet()) {
            sb.append("[").append(mobName).append("]");
        }

        sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.dump.mobs.summary", sb));
    }

    public void dumpMobs(ICommandSender sender, String wootName) { }

    public void dumpDrops(ICommandSender sender, String wootName, EnumEnchantKey key) {

        LootTable e = lootMap.get(wootName);
        if (e != null) {
            String s = e.getDrops(key);
            sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.dump.mobs.summary", s));
        }
    }
}
