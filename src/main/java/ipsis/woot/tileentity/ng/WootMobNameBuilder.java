package ipsis.woot.tileentity.ng;

import ipsis.woot.reference.Reference;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WootMobNameBuilder {

    public static @Nonnull WootMobName create(@Nonnull EntityLiving entityLiving) {

        WootMobName wootMobName = new WootMobName();

        ResourceLocation resourceLocation = EntityList.getKey(entityLiving);
        if (resourceLocation != null)
            wootMobName = new WootMobName(resourceLocation.toString(), "none");

        return wootMobName;
    }

    public static @Nonnull WootMobName create(String name) {

        WootMobName wootMobName = new WootMobName();

        Pattern pattern = Pattern.compile("(\\w*):(\\w*):(.*)");
        Matcher matcher = pattern.matcher(name);

        if (matcher.find() && matcher.groupCount() == 3 && matcher.group(1).equalsIgnoreCase(Reference.MOD_ID))
            wootMobName = new WootMobName(matcher.group(2), matcher.group(3));

        return wootMobName;
    }
}
