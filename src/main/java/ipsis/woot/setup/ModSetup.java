package ipsis.woot.setup;

import ipsis.woot.Woot;
import ipsis.woot.modules.factory.FactoryModule;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {

    protected ItemGroup itemGroup;

    public ModSetup() {

        itemGroup = new ItemGroup(Woot.MODID) {
            @Override
            public ItemStack makeIcon() {
                return new ItemStack(FactoryModule.HEART.get());
            }
        };
    }

    public ItemGroup getItemGroup() {
        return itemGroup;
    }

    public static Item.Properties createStandardProperties() {
        return new Item.Properties().tab(Woot.modSetup.getItemGroup());
    }

    public void init(FMLCommonSetupEvent e) {
    }
}
