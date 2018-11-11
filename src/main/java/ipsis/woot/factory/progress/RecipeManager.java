package ipsis.woot.factory.progress;

import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class RecipeManager {

    public FactoryRecipe getFactoryRecipe(FakeMobKey fakeMobKey, int looting) {

        // FE based recipe
        FactoryRecipe factoryRecipe = new FactoryRecipe();
        setPowerBasedRecipe(factoryRecipe);
        return factoryRecipe;
    }

    private void setPowerBasedRecipe(FactoryRecipe factoryRecipe) {

        factoryRecipe.setProgress(new MockRecipeUnitProvider(), new PowerRecipe());
    }

    public static class FactoryRecipe {

        public IRecipeUnitProvider iRecipeUnitProvider;
        public IProgessRecipe iProgessRecipe;
        List<FluidStack> spawnFluids = new ArrayList<>();
        List<ItemStack> spawnItems = new ArrayList<>();

        public boolean hasSpawnFluids() { return !spawnFluids.isEmpty(); }
        public boolean hasSpawnItems() { return !spawnItems.isEmpty(); }

        public void setProgress(IRecipeUnitProvider iRecipeUnitProvider, IProgessRecipe iProgessRecipe) {
            this.iRecipeUnitProvider = iRecipeUnitProvider;
            this.iProgessRecipe = iProgessRecipe;
        }
    }

}
