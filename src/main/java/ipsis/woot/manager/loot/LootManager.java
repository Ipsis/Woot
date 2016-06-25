package ipsis.woot.manager.loot;

import ipsis.woot.manager.EnumEnchantKey;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class LootManager {

    HashMap<String, LootTable> lootMap = new HashMap<String, LootTable>();
    public Random RAND = new Random();

    public void handleDropInfo(String wootName, EnumEnchantKey key, List<EntityItem> drops) {

        LootTable e = lootMap.get(wootName);
        if (e == null) {
            e = new LootTable(wootName);
            lootMap.put(wootName, e);
        }

        e.update(key, drops);
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
            return false;

        return e.isEmpty(key);
    }

    public List<ItemStack> getLoot(String wootName, EnumEnchantKey key) {

        List<ItemStack> loot = new ArrayList<ItemStack>();

        LootTable e = lootMap.get(wootName);
        if (e != null)
            e.getLoot(key, loot);

        return loot;
    }

    /**
     * Command interface
     */
    public void cmdDumpMobs(ICommandSender sender) {

        for (String mobName : lootMap.keySet())
            sender.addChatMessage(new TextComponentTranslation("commands.Woot:woot.dump.mobs.summary", mobName));
    }

    public void cmdDumpTable(ICommandSender sender, String wootName, EnumEnchantKey key) {

        LootTable e = lootMap.get(wootName);
        if (e == null) {
            sender.addChatMessage(new TextComponentString(String.format("Mob: %s/%s is empty", wootName, key)));
        } else {
            e.dump(sender, key);
        }

        List<ItemStack> out = getLoot(wootName, key);
    }

}
