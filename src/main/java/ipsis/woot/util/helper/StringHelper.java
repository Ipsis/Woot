package ipsis.woot.util.helper;


import net.minecraft.util.text.TranslationTextComponent;

public class StringHelper {

    public static TranslationTextComponent translate(String key, Object... args) {
        return new TranslationTextComponent(key, args);
    }
}
