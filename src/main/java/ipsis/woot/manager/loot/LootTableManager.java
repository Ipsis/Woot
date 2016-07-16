package ipsis.woot.manager.loot;

import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Files;
import ipsis.woot.util.ItemStackHelper;
import ipsis.woot.util.SerializationHelper;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LootTableManager {

    private HashMap<String, LootTable> lootMap;
    private List<ItemStack> blacklist;

    public LootTableManager() {

        lootMap = new HashMap<String, LootTable>();
        blacklist = new ArrayList<ItemStack>();
    }

    public void addToBlacklist(String s) {

        ItemStack itemStack = ItemStackHelper.getItemStackFromName(s);
        if (itemStack != null) {
            LogHelper.warn("Loot blacklisted " + s);
            blacklist.add(itemStack);
        } else {
            LogHelper.warn("Unknown Loot in blacklist " + s);
        }
    }

    public boolean isBlacklisted(ItemStack itemStack) {

        for (ItemStack cmp : blacklist) {
            if (ItemStack.areItemsEqualIgnoreDurability(cmp, itemStack))
                return true;
        }

        return false;
    }

    public void update(String wootName, EnumEnchantKey key, List<EntityItem> mobDropList, boolean updateCount) {

        LootTable e = lootMap.get(wootName);
        if (e == null) {
            e = new LootTable(wootName);
            lootMap.put(wootName, e);
        }

        e.update(key, mobDropList, updateCount);
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


    public void dumpDrops(ICommandSender sender, String wootName, EnumEnchantKey key, boolean detail) {

        LootTable e = lootMap.get(wootName);
        if (e != null) {
            String s = e.getDrops(key, detail);
            sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.dump.table.summary", s));
        }
    }

    public void flushMob(ICommandSender sender, String wootName, EnumEnchantKey key) {

        LootTable e = lootMap.get(wootName);
        if (e != null) {
            e.flush(key);
            sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.flush.summary", wootName, key.toString()));
        }
    }

    public void dumpBlacklist(ICommandSender sender) {

        StringBuilder sb = new StringBuilder();
        for (ItemStack itemStack : blacklist)
            sb.append(String.format("%s ", itemStack.getDisplayName()));

        sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.dump.blacklist.summary", sb.toString()));

    }

    /**
     * Loot file handling
     */
    public void load() {

        LogHelper.info("LootTableManager: Load loot statistics from " + Files.lootFile.toString());
        lootMap = null;
        try {
            lootMap = SerializationHelper.readHashMapFromFile(Files.lootFile);
        } catch (FileNotFoundException e) {
            /**
             * If it is no there then we start empty
             */
            lootMap = new HashMap<String, LootTable>();
        }
    }

    public void save() {

        LogHelper.info("LootTableManager: Save loot statistics to " + Files.lootFile.toString());
        SerializationHelper.writeHashMapToFile(lootMap, Files.lootFile);
    }
}
