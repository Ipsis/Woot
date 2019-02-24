package ipsis.woot.tools;

import ipsis.woot.Woot;
import ipsis.woot.util.WootItem;
import net.minecraft.item.Item;

public class ItemHammer extends WootItem {

    public static final String BASENAME = "hammer";
    public ItemHammer() {
        super(new Item.Properties().group(Woot.TAB_WOOT), BASENAME);
    }
}
