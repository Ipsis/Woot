package ipsis.woot.util.helper;


import net.minecraft.util.text.LanguageMap;
import ipsis.woot.util.FakeMob;
import net.minecraft.entity.EntityType;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

public class StringHelper {

    public static String translate(String key) {
        return LanguageMap.getInstance().func_230503_a_(key);
    }

    public static String translateFormat(String key, Object... format) {
        return String.format(LanguageMap.getInstance().func_230503_a_(key), format);
    }

    public static TranslationTextComponent translate(FakeMob fakeMob) {
        EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(fakeMob.getResourceLocation());
        if (entityType == null)
            return new TranslationTextComponent("misc.woot.unknown_entity");

        TranslationTextComponent t = new TranslationTextComponent(entityType.getTranslationKey());
        if (fakeMob.hasTag())
            t = new TranslationTextComponent("misc.woot.tagged_mob", t.getUnformattedComponentText(), fakeMob.getTag());

        return t;
    }
}
