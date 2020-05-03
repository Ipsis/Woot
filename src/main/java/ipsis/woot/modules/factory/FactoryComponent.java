package ipsis.woot.modules.factory;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public enum FactoryComponent {

    FACTORY_A,
    FACTORY_B,
    FACTORY_C,
    FACTORY_D,
    FACTORY_E,
    FACTORY_CONNECT,
    FACTORY_CTR_BASE_PRI,
    FACTORY_CTR_BASE_SEC,
    FACTORY_UPGRADE,
    HEART,
    CAP_A,
    CAP_B,
    CAP_C,
    CAP_D,
    IMPORT,
    EXPORT,
    CONTROLLER,
    CELL;

    public static FactoryComponent[] VALUES = values();
    public String getName() { return name().toLowerCase(Locale.ROOT); }
    public String getTranslationKey() { return "block.woot." + getName(); }

    public static boolean isSameComponentFuzzy(FactoryComponent componentA, FactoryComponent componentB) {
        return componentA == componentB;
    }

    public Block getBlock() {
        if (this == FACTORY_A)
            return FactorySetup.FACTORY_A_BLOCK.get();
        else if (this == FACTORY_B)
            return FactorySetup.FACTORY_B_BLOCK.get();
        else if (this == FACTORY_C)
            return FactorySetup.FACTORY_C_BLOCK.get();
        else if (this == FACTORY_D)
            return FactorySetup.FACTORY_D_BLOCK.get();
        else if (this == FACTORY_E)
            return FactorySetup.FACTORY_E_BLOCK.get();
        else if (this == FACTORY_CONNECT)
            return FactorySetup.FACTORY_CONNECT_BLOCK.get();
        else if (this == FACTORY_CTR_BASE_PRI)
            return FactorySetup.FACTORY_CTR_BASE_PRI_BLOCK.get();
        else if (this == FACTORY_CTR_BASE_SEC)
            return FactorySetup.FACTORY_CTR_BASE_SEC_BLOCK.get();
        else if (this == FACTORY_UPGRADE)
            return FactorySetup.FACTORY_UPGRADE_BLOCK.get();
        else if (this == HEART)
            return FactorySetup.HEART_BLOCK.get();
        else if (this == CAP_A)
            return FactorySetup.CAP_A_BLOCK.get();
        else if (this == CAP_B)
            return FactorySetup.CAP_B_BLOCK.get();
        else if (this == CAP_C)
            return FactorySetup.CAP_C_BLOCK.get();
        else if (this == CAP_D)
            return FactorySetup.CAP_D_BLOCK.get();
        else if (this == IMPORT)
            return FactorySetup.IMPORT_BLOCK.get();
        else if (this == EXPORT)
            return FactorySetup.EXPORT_BLOCK.get();
        else if (this == CONTROLLER)
            return FactorySetup.CONTROLLER_BLOCK.get();
        else if (this == CELL)
            return FactorySetup.CELL_1_BLOCK.get();

        throw new IllegalArgumentException("FactoryComponent missing mapping");
    }

    public List<Block> getBlocks() {
        List<Block> stacks = new ArrayList<>();
        if (this == CELL) {
            stacks.add(FactorySetup.CELL_1_BLOCK.get());
            stacks.add(FactorySetup.CELL_2_BLOCK.get());
            stacks.add(FactorySetup.CELL_3_BLOCK.get());
            stacks.add(FactorySetup.CELL_4_BLOCK.get());
        } else {
            stacks.add(this.getBlock());
        }
        return stacks;
    }

    public ItemStack getItemStack() {
        if (this == FACTORY_A)
            return new ItemStack(FactorySetup.FACTORY_A_BLOCK.get());
        else if (this == FACTORY_B)
            return new ItemStack(FactorySetup.FACTORY_B_BLOCK.get());
        else if (this == FACTORY_C)
            return new ItemStack(FactorySetup.FACTORY_C_BLOCK.get());
        else if (this == FACTORY_D)
            return new ItemStack(FactorySetup.FACTORY_D_BLOCK.get());
        else if (this == FACTORY_E)
            return new ItemStack(FactorySetup.FACTORY_E_BLOCK.get());
        else if (this == FACTORY_CONNECT)
            return new ItemStack(FactorySetup.FACTORY_CONNECT_BLOCK.get());
        else if (this == FACTORY_CTR_BASE_PRI)
            return new ItemStack(FactorySetup.FACTORY_CTR_BASE_PRI_BLOCK.get());
        else if (this == FACTORY_CTR_BASE_SEC)
            return new ItemStack(FactorySetup.FACTORY_CTR_BASE_SEC_BLOCK.get());
        else if (this == FACTORY_UPGRADE)
            return new ItemStack(FactorySetup.FACTORY_UPGRADE_BLOCK.get());
        else if (this == HEART)
            return new ItemStack(FactorySetup.HEART_BLOCK.get());
        else if (this == CAP_A)
            return new ItemStack(FactorySetup.CAP_A_BLOCK.get());
        else if (this == CAP_B)
            return new ItemStack(FactorySetup.CAP_B_BLOCK.get());
        else if (this == CAP_C)
            return new ItemStack(FactorySetup.CAP_C_BLOCK.get());
        else if (this == CAP_D)
            return new ItemStack(FactorySetup.CAP_D_BLOCK.get());
        else if (this == IMPORT)
            return new ItemStack(FactorySetup.IMPORT_BLOCK.get());
        else if (this == EXPORT)
            return new ItemStack(FactorySetup.EXPORT_BLOCK.get());
        else if (this == CONTROLLER)
            return new ItemStack(FactorySetup.CONTROLLER_BLOCK.get());
        else if (this == CELL)
            return new ItemStack(FactorySetup.CELL_1_BLOCK.get());

        throw new IllegalArgumentException("FactoryComponent missing mapping");
    }

    public List<ItemStack> getStacks() {
        List<ItemStack> stacks = new ArrayList<>();
        if (this == CELL) {
            stacks.add(new ItemStack(FactorySetup.CELL_1_BLOCK.get()));
            stacks.add(new ItemStack(FactorySetup.CELL_2_BLOCK.get()));
            stacks.add(new ItemStack(FactorySetup.CELL_3_BLOCK.get()));
            stacks.add(new ItemStack(FactorySetup.CELL_4_BLOCK.get()));
        } else {
            stacks.add(this.getItemStack());
        }
        return stacks;
    }

    public BlockState getDefaultBlockState() {
        if (this == FACTORY_A)
            return FactorySetup.FACTORY_A_BLOCK.get().getDefaultState();
        else if (this == FACTORY_B)
            return FactorySetup.FACTORY_B_BLOCK.get().getDefaultState();
        else if (this == FACTORY_C)
            return FactorySetup.FACTORY_C_BLOCK.get().getDefaultState();
        else if (this == FACTORY_D)
            return FactorySetup.FACTORY_D_BLOCK.get().getDefaultState();
        else if (this == FACTORY_E)
            return FactorySetup.FACTORY_E_BLOCK.get().getDefaultState();
        else if (this == FACTORY_CONNECT)
            return FactorySetup.FACTORY_CONNECT_BLOCK.get().getDefaultState();
        else if (this == FACTORY_CTR_BASE_PRI)
            return FactorySetup.FACTORY_CTR_BASE_PRI_BLOCK.get().getDefaultState();
        else if (this == FACTORY_CTR_BASE_SEC)
            return FactorySetup.FACTORY_CTR_BASE_SEC_BLOCK.get().getDefaultState();
        else if (this == FACTORY_UPGRADE)
            return FactorySetup.FACTORY_UPGRADE_BLOCK.get().getDefaultState();
        else if (this == CAP_A)
            return FactorySetup.CAP_A_BLOCK.get().getDefaultState();
        else if (this == CAP_B)
            return FactorySetup.CAP_B_BLOCK.get().getDefaultState();
        else if (this == CAP_C)
            return FactorySetup.CAP_C_BLOCK.get().getDefaultState();
        else if (this == CAP_D)
            return FactorySetup.CAP_D_BLOCK.get().getDefaultState();
        else if (this == IMPORT)
            return FactorySetup.IMPORT_BLOCK.get().getDefaultState();
        else if (this == EXPORT)
            return FactorySetup.EXPORT_BLOCK.get().getDefaultState();
        else if (this == CONTROLLER)
            return FactorySetup.CONTROLLER_BLOCK.get().getDefaultState();
        else if (this == CELL)
            return FactorySetup.CELL_1_BLOCK.get().getDefaultState();

        return FactorySetup.HEART_BLOCK.get().getDefaultState();
    }
}
