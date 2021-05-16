package ipsis.woot.modules.factory.blocks;

import ipsis.woot.Woot;
import ipsis.woot.base.WootBlockHFacing;
import ipsis.woot.datagen.Languages;
import ipsis.woot.modules.factory.FactoryModule;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class LayoutBlock extends WootBlockHFacing {

    public LayoutBlock() {
        super(FactoryModule.getDefaultBlockProperties());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new LayoutTileEntity();
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {

        if (world.isClientSide)
            return ActionResultType.SUCCESS;

        // Empty hand to change
        if (playerEntity.getMainHandItem().isEmpty()) {
            TileEntity te = world.getBlockEntity(blockPos);
            if (te instanceof LayoutTileEntity) {
                if (playerEntity.isCrouching())
                    ((LayoutTileEntity) te).nextDisplayLevel();
                else
                    ((LayoutTileEntity) te).nextFactoryTier();
            }
        }

        return ActionResultType.CONSUME;
    }

    public static void addTranslations(String id, Languages languages) {

        languages.add(FactoryModule.LAYOUT.get(), "Layout Guide");
        languages.add("info.woot." + id, "Shows layout of factory");
        languages.add("info.woot.sneak.0." + id, "Right click to change factory tiers");
        languages.add("info.woot.sneak.1." + id, "Sneak right click to change displayed y level");

        /* Guide entry */
        languages.add("guide.woot.tools." + id + ".name", "Layout Guide");
        languages.add("guide.woot.tools." + id + ".0",
                "The $(item)Layout Guide$(0) will show you where the blocks need to be placed for a factory. " +
                        "It can be placed on top of an existing Factory Heart or on its own.");
        languages.add("guide.woot.tools." + id + ".1",
                "$(li)Right clicking will step through the different tiers" +
                        "$(li)Sneak right click will step through the y-levels of the current tier");
    }

    public static void addRecipe(Block block, Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shaped(block)
                .pattern("grg")
                .pattern("ytb")
                .pattern("gwg")
                .define('g', Tags.Items.GLASS) .define('r', Tags.Items.DYES_RED)
                .define('y', Tags.Items.DYES_YELLOW) .define('b', Tags.Items.DYES_BLACK)
                .define('w', Tags.Items.DYES_WHITE) .define('t', Blocks.GLOWSTONE)
                .group(Woot.MODID)
                .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
                .save(consumer);
    }
}
