package ipsis.woot.simulator;


import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.HashMap;


public class CustomDropsLoader {

    public static void load() {

        /**
         * Ender Dragon
         */
        FakeMob dragon = FakeMob.getEnderDragon();
        // Guaranteed drop of 1 egg
        ItemStack itemStack = new ItemStack(Items.DRAGON_EGG);
        HashMap<Integer, Integer> stackSizes = new HashMap<>();
        for (int i = 0; i < 4; i++)
            MobSimulator.getInstance().learnCustomDrop(
                    new FakeMobKey(dragon, i), itemStack, 100.0F, stackSizes);
        // Guaranteed drop of dragon breath, with higher looting higher stack size
        stackSizes.clear();
        itemStack = new ItemStack(Items.DRAGON_BREATH);
        itemStack.setCount(2);
        MobSimulator.getInstance().learnCustomDrop(
                new FakeMobKey(dragon, 0), itemStack, 100.0F, stackSizes);
        itemStack.setCount(4);
        MobSimulator.getInstance().learnCustomDrop(
                new FakeMobKey(dragon, 1), itemStack, 100.0F, stackSizes);
        itemStack.setCount(6);
        MobSimulator.getInstance().learnCustomDrop(
                new FakeMobKey(dragon, 2), itemStack, 100.0F, stackSizes);
        itemStack.setCount(8);
        MobSimulator.getInstance().learnCustomDrop(
                new FakeMobKey(dragon, 3), itemStack, 100.0F, stackSizes);


        /**
         * Test only entries
         */
        FakeMob pig = new FakeMob("minecraft:pig");
        itemStack = new ItemStack(Items.DRAGON_EGG);
        stackSizes.clear();
        stackSizes.put(2, 80);
        stackSizes.put(3, 10);
        stackSizes.put(4, 5);
        stackSizes.put(6, 5);
        MobSimulator.getInstance().learnCustomDrop(
                new FakeMobKey(pig, 0), itemStack, 100.0F, stackSizes);
    }


}
