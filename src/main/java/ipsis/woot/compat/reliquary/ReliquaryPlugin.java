package ipsis.woot.compat.reliquary;

import ipsis.woot.Woot;
import ipsis.woot.util.FakeMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

import java.util.concurrent.atomic.AtomicBoolean;

public class ReliquaryPlugin {

    @ObjectHolder("xreliquary:mob_charm_fragment")
    private static Item MOB_CHARM_FRAGMENT = null;

    private static boolean isValidMonster(FakeMob fakeMob, World world) {
        AtomicBoolean valid = new AtomicBoolean(false);
        EntityType.byKey(fakeMob.toString()).filter((e) -> {
            Entity entity = e.create(world);
            return entity != null && entity.isNonBoss() && e.getClassification() == EntityClassification.MONSTER;
        }).ifPresent(p -> {
            valid.set(true);
        });

        return valid.get();
    }

    public static ItemStack getCharmFragment(FakeMob fakeMob, World world) {

        if (!isValidMonster(fakeMob, world))
            return ItemStack.EMPTY;

        if (MOB_CHARM_FRAGMENT == null)
            return ItemStack.EMPTY;

        // data: {id: "xreliquary:mob_charm_fragment", Count: 2b, tag: {entity: "minecraft:phantom"}}

        ResourceLocation rs = fakeMob.getResourceLocation();
        String registryName = rs.toString();
        ItemStack itemStack = new ItemStack(MOB_CHARM_FRAGMENT);
        CompoundNBT nbt = itemStack.getTag();
        if (nbt == null)
            nbt = new CompoundNBT();

        nbt.putString("entity", registryName);
        itemStack.setTag(nbt);
        return itemStack;
    }


    public static boolean isCharmFragment(ItemStack itemStack) {

        if (MOB_CHARM_FRAGMENT == null)
            return false;

        return itemStack.getItem() == MOB_CHARM_FRAGMENT;
    }
}
