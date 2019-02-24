package ipsis.woot.debug;

import net.minecraft.item.ItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public interface IWootDebug {

    List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext);
}
