package ipsis.woot.factory.blocks;

import ipsis.woot.factory.*;
import ipsis.woot.mod.ModBlocks;
import ipsis.woot.simulation.MobSimulator;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class HeartTileEntity extends TileEntity implements ITickableTileEntity {

    /**
     * Layout will not exist until after the first update call
     * Setup will only exist if the layout is formed
     */
    private Layout layout;
    private Setup setup;
    private Recipe recipe;

    public HeartTileEntity() {
        super(ModBlocks.HEART_BLOCK_TILE);
    }

    @Override
    public void tick() {
       if (world.isRemote)
           return;

       if (layout == null) {
           layout = new Layout();
           recipe = new Recipe(1000, 2000);
           FakeMobKey fakeMobKey = new FakeMobKey(new FakeMob("minecraft:zombie"), 1);
           setup = new Setup(fakeMobKey.getMob(), Tier.TIER_1);
           MobSimulator.get().learn(fakeMobKey);
       }

       /*
       layout.tick();

       if (layout.isFormed()) {
           if (layout.hasChanged()) {
               layout.clearChanged();
           }
       } */

       if (world.isBlockPowered(pos))
           return;


       tickRecipe();
       if (consumedUnits >= recipe.getNumUnits()) {
           // get and process the ingredients
           consumedUnits = 0;
       }
    }

    /**
     * Recipe handling
     */
    private int consumedUnits = 0;
    private void tickRecipe() {
        consumedUnits += recipe.getUnitsPerTick();
    }
}
