package ipsis.woot.items;

import ipsis.woot.util.IDebug;
import ipsis.woot.util.WootItem;
import ipsis.woot.util.helpers.PlayerHelper;
import ipsis.woot.util.helpers.WorldHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ItemDebug extends WootItem {

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    public ItemDebug() {
        super("debug");
        setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

        ItemStack itemStack = player.getHeldItem(hand);
        if (WorldHelper.isServerWorld(world)) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof IDebug) {
                List<String> debug = new ArrayList<>();
                ((IDebug)te).getDebugText(debug);
                for (String s : debug)
                    PlayerHelper.sendChatMessage(player, s);
            }
        }

        return EnumActionResult.PASS;
    }
}
