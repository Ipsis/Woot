package ipsis.woot.item;

import ipsis.woot.block.BlockMobFactoryUpgrade;
import ipsis.woot.manager.EnumSpawnerUpgrade;
import ipsis.woot.util.UnlocalizedName;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockUpgrade extends ItemBlock {

    public ItemBlockUpgrade(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {

        if (damage < 0 || damage >= EnumSpawnerUpgrade.values().length)
            return 0;

        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return UnlocalizedName.getUnlocalizedNameBlock(BlockMobFactoryUpgrade.BASENAME) + "." + EnumSpawnerUpgrade.getFromMetadata(stack.getMetadata());
    }
}
