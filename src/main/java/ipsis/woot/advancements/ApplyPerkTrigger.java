package ipsis.woot.advancements;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import ipsis.woot.modules.factory.perks.Perk;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class ApplyPerkTrigger extends AbstractCriterionTrigger<ApplyPerkTrigger.Instance>  {

    private static final ResourceLocation ID = new ResourceLocation(Woot.MODID, "applyperk");

    @Override
    public ResourceLocation getId() { return ID; }

    @Override
    protected ApplyPerkTrigger.Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser) {
        JsonElement element = json.get("perk");
        Perk perk = Perk.EMPTY;
        if (element != null && !element.isJsonNull())
            perk = Perk.byIndex(JSONUtils.getInt(json, "perk"));

        return new Instance(entityPredicate, perk);
    }

    public void trigger(ServerPlayerEntity playerEntity, Perk perk) {
        this.triggerListeners(playerEntity, instance -> instance.test(playerEntity, perk));
    }

    public static class Instance extends CriterionInstance {
        private final Perk perk;

        public Instance(EntityPredicate.AndPredicate player, Perk perk) {
            super(ApplyPerkTrigger.ID, player);
            this.perk = perk;
        }

        public static ApplyPerkTrigger.Instance forPerk(Perk perk) {
            return new ApplyPerkTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND, perk);
        }

        public boolean test(ServerPlayerEntity playerEntity, Perk perk) {
            return this.perk == perk;
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditions) {
            JsonObject jsonObject = super.serialize(conditions);
            jsonObject.addProperty("perk", perk.ordinal());
            return jsonObject;
        }
    }
}
