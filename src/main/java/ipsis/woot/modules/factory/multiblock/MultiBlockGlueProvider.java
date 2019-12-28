package ipsis.woot.modules.factory.multiblock;

import javax.annotation.Nonnull;

public interface MultiBlockGlueProvider {

    @Nonnull
    MultiBlockGlue getGlue();
}
