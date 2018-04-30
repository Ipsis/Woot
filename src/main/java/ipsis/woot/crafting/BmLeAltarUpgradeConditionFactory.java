package ipsis.woot.crafting;

import com.google.gson.JsonObject;
import ipsis.Woot;
import ipsis.woot.configuration.EnumConfigKey;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;

public class BmLeAltarUpgradeConditionFactory implements IConditionFactory {

    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {

        return () -> Woot.wootConfiguration.getBoolean(EnumConfigKey.ALLOW_BM_LE_ALTAR);
    }
}
