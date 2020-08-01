package ipsis.woot.modules.factory.items;

import ipsis.woot.config.Config;
import ipsis.woot.mod.ModNBT;
import ipsis.woot.modules.factory.Tier;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class ControllerBlockItem extends BlockItem {

    public ControllerBlockItem(Block block, Item.Properties builder) {
        super(block, builder);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        CompoundNBT compoundNBT = stack.getChildTag("BlockEntityTag");
        if (compoundNBT != null && compoundNBT.contains(ModNBT.Controller.MOB_TAG)) {
            FakeMob fakeMob = new FakeMob(compoundNBT.getCompound(ModNBT.Controller.MOB_TAG));
            if (fakeMob.isValid()) {
                EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(fakeMob.getResourceLocation());
                if (entityType != null)
                    tooltip.add(new TranslationTextComponent(entityType.getTranslationKey()));
                if (fakeMob.hasTag())
                    tooltip.add(new StringTextComponent("[" + fakeMob.getTag() + "]"));

                tooltip.add(new TranslationTextComponent("info.woot.controller.0"));
            }
        }
    }
}
