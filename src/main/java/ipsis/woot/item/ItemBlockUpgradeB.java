package ipsis.woot.item;

import ipsis.woot.block.EnumVariantUpgradeB;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockUpgradeB extends ItemBlock {

    public ItemBlockUpgradeB(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {

        if (damage < 0 || damage >= EnumVariantUpgradeB.values().length)
            return 0;

        return damage;
    }

    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + EnumVariantUpgradeB.getFromMetadata(stack.getMetadata());
    }
}
