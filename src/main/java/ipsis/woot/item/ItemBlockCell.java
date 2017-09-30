package ipsis.woot.item;

import ipsis.woot.block.BlockMobFactoryCell;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;


public class ItemBlockCell extends ItemBlock {

    public ItemBlockCell(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
    }

    @Override
    public int getMetadata(int damage) {

        if (damage < 0 || damage >= BlockMobFactoryCell.EnumCellTier.VALUES.length)
            return 0;

        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + BlockMobFactoryCell.EnumCellTier.byMetadata(stack.getMetadata()).getName();
    }
}
