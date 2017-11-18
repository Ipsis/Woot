package ipsis.woot.item;

import ipsis.woot.block.BlockMobFactoryCell;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;


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

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        if (stack.hasTagCompound()) {
            BlockMobFactoryCell.EnumCellTier tier = BlockMobFactoryCell.EnumCellTier.byMetadata(stack.getMetadata());
            tooltip.add("Energy: " + stack.getTagCompound().getInteger("Energy") + "/" + BlockMobFactoryCell.EnumCellTier.getMaxPower(tier) + " RF");
            tooltip.add("Transfer: " + BlockMobFactoryCell.EnumCellTier.getMaxTransfer(tier) + "RF/tick");
            tooltip.add("Tier: " + tier.getName() + " " + stack.getMetadata());

        } else {
            BlockMobFactoryCell.EnumCellTier tier = BlockMobFactoryCell.EnumCellTier.byMetadata(stack.getMetadata());
            tooltip.add("Energy: " + BlockMobFactoryCell.EnumCellTier.getMaxPower(tier) + " RF");
            tooltip.add("Transfer: " + BlockMobFactoryCell.EnumCellTier.getMaxTransfer(tier) + "RF/tick");
        }
    }
}
