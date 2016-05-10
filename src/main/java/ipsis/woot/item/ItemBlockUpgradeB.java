package ipsis.woot.item;

import ipsis.woot.block.BlockMobFactoryUpgradeB;
import ipsis.woot.block.EnumVariantUpgradeB;
import ipsis.woot.util.UnlocalizedName;
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

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return UnlocalizedName.getUnlocalizedNameBlock(BlockMobFactoryUpgradeB.BASENAME) + "." + EnumVariantUpgradeB.getFromMetadata(stack.getMetadata());
    }
}
