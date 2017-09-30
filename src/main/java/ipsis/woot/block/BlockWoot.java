package ipsis.woot.block;

import ipsis.Woot;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockWoot extends Block {

    public BlockWoot(Material m, String name) {

        super(m);
        setCreativeTab(Woot.tabWoot);
        setHardness(1.5F);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {

    }
}
