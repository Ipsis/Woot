package ipsis.woot.factory.blocks;

import ipsis.woot.factory.FactoryComponent;
import ipsis.woot.factory.FactoryComponentProvider;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.WootBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ControllerBlock extends WootBlock implements FactoryComponentProvider {

    public ControllerBlock() {
        super(Properties.create(Material.IRON) .sound(SoundType.METAL), "controller");
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ControllerTileEntity();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (stack.hasTag()) {
            CompoundNBT compoundNBT = stack.getTag();
            FakeMob fakeMob = new FakeMob(compoundNBT);
            tooltip.add(new StringTextComponent(fakeMob.getName()));
        }
    }

    /**
     * FactoryComponentProvider
     */
    @Override
    public FactoryComponent getFactoryComponent() {
        return FactoryComponent.CONTROLLER;
    }
}
