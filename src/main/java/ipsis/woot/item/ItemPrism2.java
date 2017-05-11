package ipsis.woot.item;

import ipsis.woot.init.ModItems;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.reference.Reference;
import ipsis.woot.util.PrismMobInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemPrism2 extends ItemWoot {

    public static final String BASENAME = "prism2";

    public ItemPrism2() {

        super(BASENAME);
        setMaxStackSize(1);
        setRegistryName(Reference.MOD_ID, BASENAME);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {
        ModelHelper.registerItem(ModItems.itemPrism2, BASENAME);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {

        LogHelper.info("hitEntity: " + EntityList.getKey(target));
        LogHelper.info("hitEntity: " + EntityList.getEntityString(target));
        LogHelper.info("hitEntity: " + target.getName());

        PrismMobInfo prismMobInfo = PrismMobInfo.createFromEntity((EntityLiving)target);

        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    /**
     * Tooltip
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

        PrismMobInfo prismMobInfo = PrismMobInfo.createFromNbt(stack.getTagCompound());
        if (prismMobInfo != null) {
            tooltip.add(prismMobInfo.getEntityKey());
            tooltip.add(prismMobInfo.getEntityName());
        } else {
            tooltip.add("Empty");
        }
    }
}
