package ipsis.woot.block;

import ipsis.woot.init.ModBlocks;
import ipsis.woot.oss.client.ModelHelper;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityMobFactoryProxy;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMobFactoryProxy extends BlockWoot implements ITileEntityProvider {

    public static final String BASENAME = "proxy";

    public BlockMobFactoryProxy() {

        super(Material.ROCK, BASENAME);
        setRegistryName(Reference.MOD_ID_LOWER, BASENAME);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntityMobFactoryProxy();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

//        ModelHelper.registerBlock(ModBlocks.blockProxy, BASENAME);
    }
}
