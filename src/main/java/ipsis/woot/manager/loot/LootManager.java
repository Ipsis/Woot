package ipsis.woot.manager.loot;

import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.oss.LogHelper;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

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

    public void dump(ICommandSender sender, String wootName, EnumEnchantKey key) {

        LootTable e = lootMap.get(wootName);
        if (e == null) {
           sender.addChatMessage(new TextComponentString(String.format("Mob: %s/%s is empty", wootName, key)));
        } else {
            e.dump(sender, key);
        }

        List<ItemStack> out = getLoot(wootName, key, 1);
        LogHelper.info(out);
    }

    public List<ItemStack> getLoot(String wootName, EnumEnchantKey key, int numMobs) {

        List<ItemStack> loot = new ArrayList<ItemStack>();

        LootTable e = lootMap.get(wootName);
        if (e != null)
            e.getLoot(key, numMobs, loot);

        return loot;
    }

}
