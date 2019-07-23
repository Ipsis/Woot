package ipsis.woot.util;

import net.minecraft.item.ItemUseContext;

import java.util.List;

public interface WootDebug {

    List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext);
}
