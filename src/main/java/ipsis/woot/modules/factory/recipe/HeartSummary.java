package ipsis.woot.modules.factory.recipe;

import io.netty.buffer.ByteBuf;
import ipsis.woot.modules.factory.*;
import ipsis.woot.simulator.MobSimulator;
import ipsis.woot.simulator.SimulatedMobDropSummary;
import ipsis.woot.simulator.library.SimulatedMobDrop;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.NetworkHelper;
import ipsis.woot.util.oss.NetworkTools;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeartSummary {

    /**
     * Factory
     */
    Tier tier;
    Exotic exotic;
    int looting;
    int cellCapacity;
    int recipeTicks;
    int recipeFluid;
    NonNullList<FakeMob> mobs;
    NonNullList<PerkSetup> perks;
    NonNullList<HeartRecipe.Ingredient> ingredients;
    NonNullList<Drop> drops;

    private HeartSummary() {
        tier = Tier.TIER_1;
        mobs = NonNullList.create();
        exotic = Exotic.NONE;
        looting = 0;
        cellCapacity = 0;
        perks = NonNullList.create();
        recipeFluid = 0;
        recipeFluid = 0;
        ingredients = NonNullList.create();
        drops = NonNullList.create();
    }

    public HeartSummary(FormedSetup formedSetup, HeartRecipe recipe) {
        this();

        if (formedSetup != null && recipe != null) {
            tier = formedSetup.getTier();
            exotic = formedSetup.getExotic();
            looting = formedSetup.getLootingLevel();
            cellCapacity = formedSetup.getCellCapacity();
            recipeTicks = recipe.getNumTicks();
            recipeFluid = recipe.getNumUnits();

            formedSetup.getAllMobs().forEach(m -> mobs.add(new FakeMob(m)));

            for (Map.Entry<PerkType, Integer> entry : formedSetup.getAllPerks().entrySet()) {
                PerkSetup perkSetup = new PerkSetup();
                perkSetup.perk = Perk.getPerks(entry.getKey(), entry.getValue());
                perks.add(perkSetup);
            }

            recipe.getIngredients().forEach(i -> ingredients.add(i.copy()));

            // TODO drops need flattened
            formedSetup.getAllMobs().forEach(m -> {
                List<SimulatedMobDropSummary> mobDropSummaries = MobSimulator.getInstance().getDropSummary(m);
                mobDropSummaries.forEach(d -> {
                    Drop drop = new Drop();
                    drop.itemStack = d.itemStack.copy();
                    drop.dropChances.put(m, d.chanceToDrop[looting]);
                    drops.add(drop);
                });
            });
        }
    }

    public int getRecipeTicks() { return recipeTicks; }
    public int getRecipeFluid() { return recipeFluid; }
    public Tier getTier() { return tier; }
    public Exotic getExotic() { return exotic; }
    public NonNullList<FakeMob> getMobs() { return mobs; }
    public NonNullList<PerkSetup> getPerks() { return perks; }
    public NonNullList<Drop> getDrops() { return drops; }
    public NonNullList<HeartRecipe.Ingredient> getIngredients() { return ingredients; }

    /**
     * A perk and any matching mob specific value.
     */
    public static class PerkSetup {
        public Perk perk = Perk.EMPTY;
        public HashMap<FakeMob, Float> configs = new HashMap<>();
    }

    /**
     * A drop and its matching mob/looting drop rate
     */
    public static class Drop {
        public ItemStack itemStack = ItemStack.EMPTY;
        public HashMap<FakeMob, Float> dropChances = new HashMap<>();
    }

    public static HeartSummary fromBytes(ByteBuf buf) {

        HeartSummary heartSummary = new HeartSummary();

        heartSummary.tier = Tier.byIndex(buf.readInt());
        heartSummary.exotic = Exotic.getExotic(buf.readInt());
        heartSummary.looting = buf.readInt();
        heartSummary.cellCapacity = buf.readInt();
        heartSummary.recipeTicks = buf.readInt();
        heartSummary.recipeFluid = buf.readInt();

        int readCount = buf.readInt();
        for (int i = 0; i < readCount; i++)
            heartSummary.mobs.add(new FakeMob(NetworkTools.readString(buf)));

        readCount = buf.readInt();
        for (int i = 0; i < readCount; i++) {
            PerkSetup perkSetup = new PerkSetup();
            perkSetup.perk = Perk.getPerks(buf.readInt());
            int configCount = buf.readInt();
            for (int j = 0; j < configCount; j++) {
                FakeMob mob = new FakeMob(NetworkTools.readString(buf));
                perkSetup.configs.put(mob, buf.readFloat());
            }
        }

        readCount = buf.readInt();
        for (int i = 0; i < readCount; i++) {
            int amount = buf.readInt();
            int type = buf.readByte();
            if (type == 0) {
                ItemStack itemStack = NetworkTools.readItemStack(buf);
                HeartRecipe.ItemIngredient itemIngredient = new HeartRecipe.ItemIngredient(itemStack);
                itemIngredient.amount = amount;
                heartSummary.ingredients.add(itemIngredient);
            } else if (type == 1) {
                FluidStack fluidStack = NetworkHelper.readFluidStack(buf);
                HeartRecipe.FluidIngredient fluidIngredient = new HeartRecipe.FluidIngredient(fluidStack);
                fluidIngredient.amount = amount;
                heartSummary.ingredients.add(fluidIngredient);
            }
        }

        readCount = buf.readInt();
        for (int i = 0; i < readCount; i++) {
            Drop drop = new Drop();
            drop.itemStack = NetworkTools.readItemStack(buf);
            int chanceCount = buf.readInt();
            for (int j = 0; j < chanceCount; j++) {
                FakeMob mob = new FakeMob(NetworkTools.readString(buf));
                Float chance = buf.readFloat();
                drop.dropChances.put(mob, chance);
            }
            heartSummary.drops.add(drop);
        }

        return heartSummary;

    }

    public void toBytes(ByteBuf buf) {

        buf.writeInt(tier.ordinal());
        buf.writeInt(exotic.ordinal());
        buf.writeInt(looting);
        buf.writeInt(cellCapacity);
        buf.writeInt(recipeTicks);
        buf.writeInt(recipeFluid);

        // Mobs
        buf.writeInt(mobs.size());
        for (FakeMob fakeMob : mobs)
            NetworkTools.writeString(buf, fakeMob.toString());

        // Perks
        buf.writeInt(perks.size());
        for (PerkSetup perkSetup : perks) {
            buf.writeInt(perkSetup.perk.ordinal());
            buf.writeInt(perkSetup.configs.size());
            for (Map.Entry<FakeMob, Float> entry : perkSetup.configs.entrySet()) {
                NetworkTools.writeString(buf, entry.getKey().toString());
                buf.writeFloat(entry.getValue());
            }
        }

        // Ingredients
        buf.writeInt(ingredients.size());
        for (HeartRecipe.Ingredient ingredient : ingredients) {
            buf.writeInt(ingredient.getAmount());
            if (ingredient instanceof HeartRecipe.ItemIngredient) {
                buf.writeByte(0);
                NetworkTools.writeItemStack(buf, ((HeartRecipe.ItemIngredient) ingredient).getItemStack());
            } else if (ingredient instanceof HeartRecipe.FluidIngredient) {
                buf.writeByte(1);
                NetworkHelper.writeFluidStack(buf, ((HeartRecipe.FluidIngredient) ingredient).getFluidStack());
            }

        }

        // Drops
        buf.writeInt(drops.size());
        for (Drop drop : drops) {
            NetworkTools.writeItemStack(buf, drop.itemStack);
            buf.writeInt(drop.dropChances.size());
            for (Map.Entry<FakeMob, Float> entry : drop.dropChances.entrySet()) {
                NetworkTools.writeString(buf, entry.getKey().toString());
                buf.writeFloat(entry.getValue());
            }
        }

    }

}
