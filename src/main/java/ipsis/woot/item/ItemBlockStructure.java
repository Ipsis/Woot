package ipsis.woot.item;

import ipsis.woot.multiblock.EnumMobFactoryModule;
import ipsis.woot.util.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

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

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        EnumMobFactoryModule module = EnumMobFactoryModule.byMetadata(stack.getMetadata());

        String name = "info.woot.structure." + module.getName();
        tooltip.add(StringHelper.localize(name));
    }
}
