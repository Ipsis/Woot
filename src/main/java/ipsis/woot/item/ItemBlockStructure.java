package ipsis.woot.item;

import ipsis.woot.multiblock.EnumMobFactoryModule;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockStructure extends ItemBlock {

    public ItemBlockStructure(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {

        if (damage < 0 || damage >= EnumMobFactoryModule.VALUES.length)
            return 0;

        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + EnumMobFactoryModule.byMetadata(stack.getMetadata());
    }
}
