package ipsis.woot.items;

import ipsis.woot.util.helpers.ProgrammedMobHelper;
import ipsis.woot.util.helpers.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockController extends ItemBlock {

    public ItemBlockController(Block b) {
        super(b);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {

        String entityName = ProgrammedMobHelper.getItemStackDisplayName(stack);
        if (entityName.equalsIgnoreCase(""))
            entityName = "info.woot.controller.empty";

        return super.getItemStackDisplayName(stack) + " - " + StringHelper.localise(entityName);

    }
}
