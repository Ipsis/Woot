package ipsis.woot.block;

import ipsis.Woot;
import ipsis.woot.util.UnlocalizedName;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockContainerWoot extends BlockContainer {

    public BlockContainerWoot(Material m, String name) {
        super(m);

        setCreativeTab(Woot.tabWoot);
        setHardness(1.5F);
        setUnlocalizedName(name);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
    }

    @Override
    public String getUnlocalizedName() {
        return UnlocalizedName.getUnlocalizedNameBlock(super.getUnlocalizedName());
    }
}
