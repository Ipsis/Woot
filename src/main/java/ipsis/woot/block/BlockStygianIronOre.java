package ipsis.woot.block;

import ipsis.woot.init.ModBlocks;
import ipsis.woot.oss.client.ModelHelper;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStygianIronOre extends BlockWoot {

    public static final String BASENAME = "stygianironore";

    public BlockStygianIronOre() {

        super(Material.ROCK, BASENAME);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {

        ModelHelper.registerBlock(ModBlocks.blockStygianIronOre, BASENAME);
    }
}
