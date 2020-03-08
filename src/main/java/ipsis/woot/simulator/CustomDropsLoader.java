package ipsis.woot.simulator;


import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;


public class CustomDropsLoader {

    public static void load() {

        FakeMob dragon = new FakeMob("minecraft:ender_dragon");

        /**
         * Dragon egg
         */
        ItemStack itemStack = new ItemStack(Items.DRAGON_EGG);
        for (int i = 0; i < 4; i++)
            MobSimulator.getInstance().learnCustomDrop(new FakeMobKey(dragon, i), itemStack, 100.0F);

        /**
         * Example only
         *
         * DropRegistry.get().learnCustomDropStackSize(new FakeMobKey(dragon, 3), itemStack,
         *       new int[]{1,2,3,4},
         *       new float[]{0.25F, 0.25F, 0.25F, 0.25F});
         */

        /**
         * Dragons Breath
         */
        itemStack = new ItemStack(Items.DRAGON_BREATH);
        MobSimulator.getInstance().learnCustomDrop(new FakeMobKey(dragon, 0), itemStack,  2, 100.0F);
        MobSimulator.getInstance().learnCustomDrop(new FakeMobKey(dragon, 1), itemStack,  4, 100.0F);
        MobSimulator.getInstance().learnCustomDrop(new FakeMobKey(dragon, 2), itemStack,  6, 100.0F);
        MobSimulator.getInstance().learnCustomDrop(new FakeMobKey(dragon, 3), itemStack,  8, 100.0F);
    }


}
