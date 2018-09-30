package ipsis.woot.items;

import ipsis.Woot;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.FakeMobKeyFactory;
import ipsis.woot.util.helpers.LogHelper;
import ipsis.woot.util.helpers.PlayerHelper;
import ipsis.woot.util.helpers.ProgrammedMobHelper;
import ipsis.woot.util.helpers.StringHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemEnderShard extends Item {

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    public ItemEnderShard() {
        setRegistryName("endershard");
        setUnlocalizedName(Woot.MODID + ".endershard");
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {

        if (attacker.getEntityWorld().isRemote || !(attacker instanceof EntityPlayer))
            return false;

        if (!(target instanceof EntityLiving))
            return false;

        EntityLiving entityLiving = (EntityLiving)target;
        ResourceLocation resourceLocation = EntityList.getKey(entityLiving);
        if (resourceLocation == null)
            return false;

        if (!Woot.POLICY_MANAGER.canCaptureMob(entityLiving)) {
            PlayerHelper.sendActionBarMessage((EntityPlayer) attacker, StringHelper.localise("chat.woot.endershard.nocapture"));
            return false;
        }

        if (ProgrammedMobHelper.isEntityProgrammed(stack)) {
            PlayerHelper.sendActionBarMessage((EntityPlayer)attacker, StringHelper.localise("chat.woot.endershard.inuse"));
            return false;
        }

        FakeMobKey fakeMobKey = FakeMobKeyFactory.createFromEntity(entityLiving);
        if (fakeMobKey.isValid()) {
            ProgrammedMobHelper.programEntity(stack, fakeMobKey);
            PlayerHelper.sendActionBarMessage((EntityPlayer)attacker, StringHelper.localise("chat.woot.endershard.inuse"));
            return true;
        }

        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        if (ProgrammedMobHelper.isEntityProgrammed(stack))
            tooltip.add(StringHelper.localise("info.woot.endershard.state", ProgrammedMobHelper.getDeathCount(stack), 1));
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {

        String entityName = "info.woot.endershard.empty";
        if (ProgrammedMobHelper.isEntityProgrammed(stack)) {
            FakeMobKey fakeMobKey = ProgrammedMobHelper.getProgrammedEntity(stack);
            EntityEntry entityEntry = ForgeRegistries.ENTITIES.getValue(fakeMobKey.getResourceLocation());
            if (entityEntry != null)
                entityName = "entity." + entityEntry.getName() + ".name";
        }

        return super.getItemStackDisplayName(stack) + " - " + StringHelper.localise(entityName);
    }
}
