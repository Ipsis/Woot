package ipsis.woot.advancements;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import ipsis.woot.util.FakeMob;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class MobCaptureTrigger extends AbstractCriterionTrigger<MobCaptureTrigger.Instance> {

    private static final ResourceLocation ID = new ResourceLocation(Woot.MODID, "mobcapture");

    @Override
    public ResourceLocation getId() { return ID; }

    @Override
    protected MobCaptureTrigger.Instance createInstance(JsonObject json, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser) {
        JsonElement element = json.get("mob");
        FakeMob fakeMob = new FakeMob();
        if (element != null && !element.isJsonNull())
            fakeMob = new FakeMob(JSONUtils.getAsString(json, "mob"));

        return new Instance(entityPredicate, fakeMob);
    }

    public void trigger(ServerPlayerEntity player, FakeMob fakeMob) {
        this.trigger(player, instance -> instance.test(player, fakeMob));
    }

    public static class Instance extends CriterionInstance {
        private final FakeMob fakeMob;

        public Instance(EntityPredicate.AndPredicate player, FakeMob fakeMob) {
            super(MobCaptureTrigger.ID, player);
            this.fakeMob = fakeMob;
        }

        public static MobCaptureTrigger.Instance forMob(FakeMob fakeMob) {
            return new MobCaptureTrigger.Instance(EntityPredicate.AndPredicate.ANY, fakeMob);
        }

        public boolean test(ServerPlayerEntity playerEntity, FakeMob fakeMob) {
            return this.fakeMob.equals(fakeMob);
        }

        public JsonObject serialize(ConditionArraySerializer conditions) {
            JsonObject jsonObject = super.serializeToJson(conditions);
            jsonObject.addProperty("mob", fakeMob.toString());
            return jsonObject;
        }

    }


}
