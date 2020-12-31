package ipsis.woot.advancements;

import net.minecraft.advancements.CriteriaTriggers;

public class Advancements {

    public static MobCaptureTrigger MOB_CAPTURE_TRIGGER;
    public static TierValidateTrigger TIER_VALIDATE_TRIGGER;
    public static ApplyPerkTrigger APPLY_PERK_TRIGGER;

    public static void init() {
        MOB_CAPTURE_TRIGGER = CriteriaTriggers.register(new MobCaptureTrigger());
        TIER_VALIDATE_TRIGGER = CriteriaTriggers.register(new TierValidateTrigger());
        APPLY_PERK_TRIGGER = CriteriaTriggers.register(new ApplyPerkTrigger());
    }
}
