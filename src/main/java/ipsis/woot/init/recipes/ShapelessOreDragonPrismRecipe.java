package ipsis.woot.init.recipes;

//import ipsis.woot.item.ItemEnderShard;
//import ipsis.woot.reference.Reference;
//import net.minecraft.init.Blocks;
//import net.minecraft.inventory.InventoryCrafting;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraftforge.oredict.RecipeSorter;
//import net.minecraftforge.oredict.ShapelessOreRecipe;

//import java.util.ArrayList;
//import java.util.Iterator;

//public class ShapelessOreDragonPrismRecipe extends ShapelessOreRecipe {
//
//    static {
//        RecipeSorter.register(Reference.MOD_ID + ":prism", ShapelessOreDragonPrismRecipe.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
//    }
//
//    public ShapelessOreDragonPrismRecipe(ItemStack result, Object... recipe) {
//        super(result, recipe);
//    }
//
//    @Override
//    public ItemStack getCraftingResult(InventoryCrafting var1) {
//
//        ItemStack prism = output.copy();
//
//        ArrayList<Object> required = new ArrayList<Object>(input);
//        Iterator<Object> req = required.iterator();
//        while (req.hasNext()) {
//
//            Object next = req.next();
//            if (next instanceof ItemStack) {
//                if (((ItemStack) next).getItem() == Item.getItemFromBlock(Blocks.DRAGON_EGG)) {
//                    ItemEnderShard.setAsEnderDragon(prism);
//                }
//            }
//        }
//
//        return prism;
//    }
//}
