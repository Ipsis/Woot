package ipsis.woot.loot;

import ipsis.woot.tileentity.ng.IFarmSetup;
import ipsis.woot.tileentity.ng.ITickTracker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public interface ILootLearner {

    void tick(ITickTracker tickTracker, World world, BlockPos origin, IFarmSetup farmSetup);
    void onLivingDropsEvent(LivingDropsEvent e);
}
