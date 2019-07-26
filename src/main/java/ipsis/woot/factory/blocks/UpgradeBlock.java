package ipsis.woot.factory.blocks;

import ipsis.woot.Woot;
import ipsis.woot.factory.FactoryComponent;
import ipsis.woot.factory.items.UpgradeItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class UpgradeBlock extends FactoryBlock {

    public UpgradeBlock(FactoryComponent component) {
        super(component);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        if (!worldIn.isRemote) {
            ItemStack itemStack = player.getHeldItem(handIn);
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof UpgradeItem) {
                UpgradeItem upgradeItem = (UpgradeItem)itemStack.getItem();
                Woot.LOGGER.info("Add upgrade of type {}", upgradeItem.getFactoryUpgrade());
            }
        }

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
