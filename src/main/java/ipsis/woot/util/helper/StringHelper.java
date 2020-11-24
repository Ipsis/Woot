package ipsis.woot.util.helper;


import net.minecraft.util.text.LanguageMap;
import ipsis.woot.util.FakeMob;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class StringHelper {

    public static String translate(String key) {
        return LanguageMap.getInstance().func_230503_a_(key);
    }

    public static String translateFormat(String key, Object... format) {
        return String.format(LanguageMap.getInstance().func_230503_a_(key), format);
    }

    public static String translate(FakeMob fakeMob) {
        EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(fakeMob.getResourceLocation());
        if (entityType == null)
            return translate("misc.woot.unknown_entity");

        String mob = translate(entityType.getTranslationKey());

        if (fakeMob.hasTag())
            return translateFormat("misc.woot.tagged_mob", mob, fakeMob.getTag());

        return mob;
    }
}
