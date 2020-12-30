package ipsis.woot.advancements;

import net.minecraft.advancements.CriteriaTriggers;

public class Advancements {

    public static MobCaptureTrigger MOB_CAPTURE_TRIGGER;

    public static void init() {
        MOB_CAPTURE_TRIGGER = CriteriaTriggers.register(new MobCaptureTrigger());
    }
}
