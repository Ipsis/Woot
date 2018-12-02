package ipsis.woot.blocks;

import ipsis.woot.util.WootBlock;
import ipsis.woot.util.helpers.ProgrammedMobHelper;
import ipsis.woot.util.helpers.StringHelper;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockController extends WootBlock implements ITileEntityProvider {

    private static final String BASENAME = "controller";

    public BlockController() {
        super(Material.ROCK, BASENAME);
    }

    public static String getBasename() { return BASENAME; }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntityController();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        String entityName = ProgrammedMobHelper.getItemStackDisplayName(stack);
        if (entityName.equalsIgnoreCase(""))
            entityName = "info.woot.controller.empty";

        tooltip.add(StringHelper.localise(entityName));
    }
}
