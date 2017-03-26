package ipsis.woot.manager.loot;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LootEnderDragon {

    private List<DragonDrop> vanillaDropList;
    private List<ItemStack> moddedDropList;

    public LootEnderDragon() {

        vanillaDropList = new ArrayList<DragonDrop>();
        moddedDropList = new ArrayList<ItemStack>();
        loadVanillaDrops();
    }

    private void loadVanillaDrops() {

        vanillaDropList.add(new DragonDrop(new ItemStack(Blocks.DRAGON_EGG), 100.0F));
        vanillaDropList.add(new DragonDrop(new ItemStack(Items.DRAGON_BREATH, 4), 50.0F));
    }

    private void loadModdedDrops() {

    }

    public List<ItemStack> getDrops() {

        List<ItemStack> drops = new ArrayList<ItemStack>();
        for (DragonDrop i : vanillaDropList)
            drops.add(i.itemStack.copy());

        for (ItemStack i : moddedDropList)
            drops.add(i.copy());
        return drops;
    }

    public List<FullDropInfo> getFullDropInfo() {

       List<FullDropInfo> drops = new ArrayList<FullDropInfo>();
       for (DragonDrop i : vanillaDropList)
           drops.add(new FullDropInfo(i.itemStack.copy(), i.chance));
       return drops;
    }

    static class DragonDrop {

        float chance;
        ItemStack itemStack;

        public DragonDrop(ItemStack itemStack, float chance) {
            this.chance = chance;
            this.itemStack = itemStack.copy();
        }
    }
}
