package ipsis.woot.util.helper;


import ipsis.woot.util.FakeMob;
import net.minecraft.entity.EntityType;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

public class StringHelper {

    public static TranslationTextComponent translate(String key, Object... args) {
        return new TranslationTextComponent(key, args);
    }

    public static TranslationTextComponent translate(FakeMob fakeMob) {
        EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(fakeMob.getResourceLocation());
        if (entityType == null)
            return new TranslationTextComponent("misc.woot.unknown_entity");

        TranslationTextComponent t = translate(entityType.getTranslationKey());
        if (fakeMob.hasTag())
            t = translate("misc.woot.tagged_mob", t.getUnformattedComponentText(), fakeMob.getTag());

        return t;
    }
}
