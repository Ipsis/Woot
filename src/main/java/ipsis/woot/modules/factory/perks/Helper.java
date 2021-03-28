package ipsis.woot.modules.factory.perks;

import ipsis.woot.modules.factory.FactoryConfiguration;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Helper {

    public static Perk getPerk(Perk.Group group, int level) {
        level = MathHelper.clamp(level, 1,3) - 1;
        EnumSet<Perk> perks = Perk.getPerksByGroup(group);
        return perks.toArray(new Perk[0])[level];
    }

    public static TranslationTextComponent getTooltip(Perk perk) {
        return new TranslationTextComponent("info.woot.perk." + Perk.getGroup(perk).getLowerCaseName());
    }

    public static List<TranslationTextComponent> getTooltip(Perk perk, int level) {
        Perk.Group group = Perk.getGroup(perk);
        List<TranslationTextComponent> tooltips = new ArrayList<>();
        level = MathHelper.clamp(level, 1, 3);
        String tag = "info.woot.perk." + group.getLowerCaseName() + ".0";
        ForgeConfigSpec.IntValue intValue = FactoryConfiguration.getPerkIntValue(group, level);
        if (intValue != null)
            tooltips.add(new TranslationTextComponent(tag, intValue.get()));
        return tooltips;
    }
}
