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
    private List<ItemStack> internalBlacklist;

    public LootTableManager() {

        lootMap = new HashMap<String, LootTable>();
        blacklist = new ArrayList<ItemStack>();

        internalBlacklist = new ArrayList<ItemStack>();
    }

    public void loadInternalBlacklist() {

        addToInternalBlacklist("eplus:scroll");
        addToInternalBlacklist("everlastingabilities:abilityTotem");
    }

    private void addToInternalBlacklist(String s) {

        ItemStack itemStack = ItemStackHelper.getItemStackFromName(s);
        if (itemStack != null) {
            LogHelper.warn("Loot blacklisted (Internal) " + s);
            internalBlacklist.add(itemStack);
        } else {
            LogHelper.warn("Unknown Loot in blacklist (Internal) " + s);
        }
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
            if (ItemStack.areItemsEqualIgnoreDurability(cmp, itemStack)) {
                return true;
            }
        }

        /**
         * Internal blacklist for NBT based drops
         */
        for (ItemStack cmp : internalBlacklist) {
            if (ItemStack.areItemsEqualIgnoreDurability(cmp, itemStack)) {
                return true;
            }
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

    public List<FullDropInfo> getFullDropInfo(String wootName, EnumEnchantKey key) {

        List<FullDropInfo> drops = null;

        LootTable e = lootMap.get(wootName);
        if (e != null)
            drops = e.getFullDropInfo(key);

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
        for (String mobName : lootMap.keySet())
            sb.append(mobName).append(" ");

        sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.mobs.summary", sb));
    }

    public void dumpDrops(ICommandSender sender, String wootName, boolean detail) {

        LootTable e = lootMap.get(wootName);
        if (e != null) {
            for (EnumEnchantKey key : EnumEnchantKey.values()) {
                String s = e.getDrops(key, detail);
                sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.loot.summary", wootName, key.getDisplayName(), s));
            }
        }
    }

    public void flushMob(ICommandSender sender, String wootName, EnumEnchantKey key) {

        LootTable e = lootMap.get(wootName);
        if (e != null) {
            e.flush(key);
            sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.flush.summary",
                    wootName, key.getDisplayName()));
        }
    }

    public void flushAllMobs(ICommandSender sender) {

        for (LootTable table : lootMap.values()) {
            table.flush(EnumEnchantKey.NO_ENCHANT);
            table.flush(EnumEnchantKey.LOOTING_I);
            table.flush(EnumEnchantKey.LOOTING_II);
            table.flush(EnumEnchantKey.LOOTING_III);
        }

        sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.flush.all.summary"));
    }

    public void dumpBlacklist(ICommandSender sender) {

        StringBuilder sb = new StringBuilder();
        for (ItemStack itemStack : blacklist)
            sb.append(String.format("%s ", itemStack.getDisplayName()));

        sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.blacklist.summary", sb.toString()));
    }

    public void dumpStatus(ICommandSender sender) {

        for (String mob : lootMap.keySet()) {
            StringBuilder sb = new StringBuilder();
            for (EnumEnchantKey key : EnumEnchantKey.values()) {
                sb.append(key.getDisplayName());
                if (lootMap.get(mob).isEmpty(key))
                    sb.append(":running ");
                else
                    sb.append(":stopped ");
            }
            sender.addChatMessage(new TextComponentTranslation(
                    "commands.Woot:woot.status.summary",
                    mob, sb.toString()));
        }
    }

    /**
     * Loot file handling
     */
    public void load() {

        LogHelper.info("LootTableManager: Load loot statistics from " + Files.getWootFileForDisplay());
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

        LogHelper.info("LootTableManager: Save loot statistics to " + Files.getWootFileForDisplay());
        SerializationHelper.writeHashMapToFile(lootMap, Files.lootFile);
    }
}
