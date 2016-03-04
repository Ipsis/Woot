package ipsis.woot.block;

import java.util.List;

public interface ITooltipInfo {

    void getTooltip(List<String> toolTip, boolean showAdvanced, int meta, boolean detail);
}
