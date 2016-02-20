package ipsis.woot.block;

import ipsis.oss.client.ModelHelper;
import ipsis.woot.init.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMobFactoryController extends BlockContainerWoot {

    public static final String BASENAME = "controller";

    public BlockMobFactoryController() {

        super(Material.rock, BASENAME);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerBlock(ModBlocks.blockController, BASENAME);
    }

    @Override
    public int getRenderType() {

        return 3;
    }
}
