package ipsis.woot.blocks;

import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.WootBlock;
import ipsis.woot.util.helpers.ProgrammedMobHelper;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockController extends WootBlock implements ITileEntityProvider {

    private static final String BASENAME = "controller";

    public BlockController() {
        super(Material.ROCK, BASENAME);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

        if (stack.isEmpty())
            return;

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityController) {
            TileEntityController controller = (TileEntityController)te;
            FakeMobKey fakeMobKey = ProgrammedMobHelper.getProgrammedEntity(stack);
            if (fakeMobKey.isValid())
                controller.setFakeMobKey(fakeMobKey);
        }
    }

    public static String getBasename() { return BASENAME; }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntityController();
    }
}
