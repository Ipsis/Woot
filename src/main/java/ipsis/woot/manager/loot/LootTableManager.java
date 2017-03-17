package ipsis.woot.manager.loot;

import ipsis.Woot;
import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.manager.MobRegistry;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Files;
import ipsis.woot.reference.Settings;
import ipsis.woot.util.ItemStackHelper;
import ipsis.woot.util.SerializationHelper;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LootTableManager {

    private HashMap<String, LootTable> lootMap;
    private List<ItemStack> blacklist;
    private List<ItemStack> internalBlacklist;  // Specific items to block
    private List<String> internalModBlacklist;  // Complete mod items to block

    public LootTableManager() {

        lootMap = new HashMap<String, LootTable>();
        blacklist = new ArrayList<ItemStack>();

        internalBlacklist = new ArrayList<ItemStack>();
        internalModBlacklist = new ArrayList<String>();
    }

    public void loadInternalBlacklist() {

        addToInternalModBlacklist("eplus");
        addToInternalModBlacklist("everlastingabilities");
        addToInternalModBlacklist("cyberware");
    }

    private void addToInternalModBlacklist(String s) {

        internalModBlacklist.add(s);
        LogHelper.info("Blacklisted (Internal) all items from " + s);
    }

    private void addToInternalBlacklist(String s) {

        ItemStack itemStack = ItemStackHelper.getItemStackFromName(s);
        if (itemStack != null) {
            LogHelper.warn("Blacklisted (Internal) item " + s);
            internalBlacklist.add(itemStack);
        } else {
            LogHelper.warn("Unknown Loot in blacklist (Internal) " + s);
        }
    }

    public void addToBlacklist(String s) {

        ItemStack itemStack = ItemStackHelper.getItemStackFromName(s);
        if (itemStack != null) {
            LogHelper.warn("Blacklisted (User) item " + s);
            blacklist.add(itemStack);
        } else {
            LogHelper.warn("Unknown Loot in blacklist " + s);
        }
    }

    public boolean isBlacklisted(ItemStack itemStack) {

        /**
         * Mod blacklist
         */
        ResourceLocation first = itemStack.getItem().getRegistryName();
        if (first != null) {
            String firstMod = first.getResourceDomain();
            if (firstMod != null) {
                for (String s : internalModBlacklist) {
                    if (firstMod.equals(s))
                        return true;
                }
            }
        }

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

        if (MobRegistry.isEnderDragon(wootName))
            return handleEnderDragonDrops(key);

        List<ItemStack> drops = new ArrayList<ItemStack>();
        LootTable e = lootMap.get(wootName);
        if (e != null)
            e.getDrops(key, drops);

        return drops;
    }

    public List<FullDropInfo> getFullDropInfo(String wootName, EnumEnchantKey key) {

        if (MobRegistry.isEnderDragon(wootName))
            return getEnderDragonFullDrops(key);

        List<FullDropInfo> drops = null;

        LootTable e = lootMap.get(wootName);
        if (e != null)
            drops = e.getFullDropInfo(key);

        return drops;
    }

    public boolean isFull(String wootName, EnumEnchantKey key) {

        if (MobRegistry.isEnderDragon(wootName))
            return true;

        LootTable e = lootMap.get(wootName);
        if (e == null)
            return false;

        return e.isFull(key);
    }

    public boolean isEmpty(String wootName, EnumEnchantKey key) {

        if (MobRegistry.isEnderDragon(wootName))
            return false;

        LootTable e = lootMap.get(wootName);
        if (e == null)
            return true;

        return e.isEmpty(key);
    }

    /**
     * EnderDragon handling
     */
    private List<ItemStack> handleEnderDragonDrops(EnumEnchantKey key) {

        List<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(new ItemStack(Blocks.DRAGON_EGG));
        drops.add(new ItemStack(Items.DRAGON_BREATH, 4));
        return drops;
    }

    private List<FullDropInfo> getEnderDragonFullDrops(EnumEnchantKey key) {

        List<FullDropInfo> drops = new ArrayList<FullDropInfo>();
        drops.add(new FullDropInfo(new ItemStack(Blocks.DRAGON_EGG), 100.0F));
        drops.add(new FullDropInfo(new ItemStack(Items.DRAGON_BREATH), 100.0F));
        return drops;
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

        /**
         * If the lootmap for a mob/enchant is full then learning has completed
         * If the lootmap for a mob/enchant is not full, then learning has not completed, but does not mean it is running
         *     Learning only happens if a factory for that pair exists in the world
         */
        for (String mob : lootMap.keySet()) {
            StringBuilder sb = new StringBuilder();
            for (EnumEnchantKey key : EnumEnchantKey.values()) {
                int samples = lootMap.get(mob).getSamples(key);
                sb.append(" " + key.ordinal() + ":" + samples + "/" + Settings.sampleSize + " ");
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

        /**
         * Catch the case where the file exists but is empty and doesn't throw any exceptions
         */
        if (lootMap == null)
            lootMap = new HashMap<String, LootTable>();
    }

    public void save() {

        LogHelper.info("LootTableManager: Save loot statistics to " + Files.getWootFileForDisplay());
        SerializationHelper.writeHashMapToFile(lootMap, Files.lootFile);
    }
}
