package ipsis.woot.factory;

import ipsis.woot.debug.IWootDebug;
import ipsis.woot.factory.layout.FactoryBlock;
import ipsis.woot.factory.layout.IFactoryBlockProvider;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.WootBlock;
import ipsis.woot.util.helper.FakeMobKeyHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;

public class BlockController extends WootBlock implements IWootDebug, IFactoryBlockProvider {

    public static final String BASENAME = "factory_controller";
    public BlockController() {
        super(Block.Properties.create(Material.ROCK), BASENAME);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return new TileEntityController();
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> BlockController");
        TileEntity te =itemUseContext.getWorld().getTileEntity(itemUseContext.getPos());
        if (te instanceof IWootDebug)
            ((IWootDebug)te).getDebugText(debug, itemUseContext);
        return debug;
    }

    /**
     * IFactoryBlockProvider
     */
    @Override
    public FactoryBlock getFactoryBlock() {
        return FactoryBlock.CONTROLLER;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TextComponentString("block info!"));
        if (stack.hasTag()) {
            NBTTagCompound nbtTagCompound = stack.getTag();
            NBTTagList tagList = nbtTagCompound.getList("keys", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < tagList.size(); i++) {
                NBTTagCompound tags = tagList.getCompound(i);
                FakeMobKey fakeMobKey = FakeMobKeyHelper.createFromNBT(tags);
                tooltip.add(new TextComponentString(fakeMobKey.getName()));
            }
        }
    }
}
