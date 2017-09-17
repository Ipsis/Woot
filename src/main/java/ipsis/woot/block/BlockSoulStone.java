package ipsis.woot.block;

import ipsis.woot.init.ModBlocks;
import ipsis.woot.oss.client.ModelHelper;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSoulStone extends BlockWoot {

    public static final String BASENAME = "soulstone";

    public BlockSoulStone() {

        super(Material.ROCK, BASENAME);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerBlock(ModBlocks.blockSoulStone, BASENAME);
    }
}
