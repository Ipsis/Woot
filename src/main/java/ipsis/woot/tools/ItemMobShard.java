package ipsis.woot.tools;

import ipsis.woot.Woot;
import ipsis.woot.mod.ModItems;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.WootItem;
import ipsis.woot.util.helper.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMobShard extends WootItem {

    public static final String BASENAME = "mobshard";
    public ItemMobShard() {
        super(new Item.Properties().group(Woot.TAB_WOOT), BASENAME);
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentString(StringHelper.translate("info.woot.mobshard")));
        tooltip.add(new TextComponentString(StringHelper.translate("info.woot.mobshard.0")));
        tooltip.add(new TextComponentString(StringHelper.translate("info.woot.mobshard.1")));

        if (ProgrammedMobHelper.isEntityProgrammed(stack)) {
            tooltip.add(new TextComponentString(StringHelper.translate("info.woot.mobshard.2")));
            if (ProgrammedMobHelper.isFullyProgrammed(stack))
                tooltip.add(new TextComponentString(
                        StringHelper.translate("info.woot.mobshard.state.0")));
            else
                tooltip.add(new TextComponentString(
                        StringHelper.translateFormat("info.woot.mobshard.state.1", ProgrammedMobHelper.getDeathCount(stack), 10)));
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {

        if (WorldHelper.isClientWorld(attacker.getEntityWorld()))
            return false;

        if (!(attacker instanceof EntityPlayer) && !(target instanceof EntityLiving))
            return false;

        EntityPlayer entityPlayer = (EntityPlayer)attacker;
        EntityLiving entityLiving = (EntityLiving)target;

        //@todo can capture mob

        if (ProgrammedMobHelper.isEntityProgrammed(stack)) {
            PlayerHelper.sendChatMessage(entityPlayer,
                    StringHelper.translate("chat.woot.mobshard.used"));
            return false;
        }

        FakeMobKey fakeMobKey = FakeMobKeyHelper.createFromEntity((EntityLiving)target);
        if (fakeMobKey.isValid()) {
            ProgrammedMobHelper.programEntity(stack, fakeMobKey);
            PlayerHelper.sendActionBarMessage(entityPlayer,
                    StringHelper.translateFormat("chat.woot.mobshard.success", StringHelper.translate(ProgrammedMobHelper.getItemStackDisplayName(stack))));
            return true;
        }

        return false;
    }

    public static boolean isMobShard(ItemStack itemStack) {
        return !itemStack.isEmpty() && itemStack.getItem() == ModItems.mobShardItem;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {

        String entityName = ProgrammedMobHelper.getItemStackDisplayName(stack);
        if (entityName.equalsIgnoreCase(""))
            entityName = "info.woot.mobshard.state.2";

        return new TextComponentString(StringHelper.translate(getTranslationKey(stack)) + " - " + StringHelper.translate(entityName));
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return ProgrammedMobHelper.isFullyProgrammed(stack);
    }
}
