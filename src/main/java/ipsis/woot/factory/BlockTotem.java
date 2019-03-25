package ipsis.woot.factory;

import ipsis.woot.Woot;
import ipsis.woot.debug.IWootDebug;
import ipsis.woot.factory.layout.FactoryBlock;
import ipsis.woot.util.WootBlock;
import ipsis.woot.util.helper.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockTotem extends WootBlock implements IWootDebug {

    private final String basename;
    private final FactoryBlock factoryBlock;

    public BlockTotem(FactoryBlock factoryBlock) {
        super(Block.Properties.create(Material.ROCK), factoryBlock.getName());
        this.factoryBlock = factoryBlock;
        this.basename = factoryBlock.getName();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        TileEntityTotem tileEntityTotem = new TileEntityTotem();
        if (factoryBlock == FactoryBlock.TOTEM_1)
            tileEntityTotem.setLevel(1);
        else if (factoryBlock == FactoryBlock.TOTEM_2)
            tileEntityTotem.setLevel(2);
        else
            tileEntityTotem.setLevel(3);
        return tileEntityTotem;
    }

    /**
     * IWootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add(String.format("====> BlockTotem {}", factoryBlock.getName()));
        TileEntity te =itemUseContext.getWorld().getTileEntity(itemUseContext.getPos());
        if (te instanceof IWootDebug)
            ((IWootDebug)te).getDebugText(debug, itemUseContext);
        return debug;
    }

    /**
     * IFactoryBlockProvider
     */
    public FactoryBlock getFactoryBlock() { return this.factoryBlock; }

    /**
     * Operations
     */
    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

        if (WorldHelper.isClientWorld(worldIn))
            return super.onBlockActivated(state, worldIn, pos, player, hand, side, hitX, hitY, hitZ);

        ItemStack itemStack = player.getHeldItem(hand);
        if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemUpgrade) {
            ItemUpgrade itemUpgrade = (ItemUpgrade)itemStack.getItem();
            Woot.LOGGER.info("Trying to apply " + itemUpgrade);
        }

        return super.onBlockActivated(state, worldIn, pos, player, hand, side, hitX, hitY, hitZ);
    }
}
